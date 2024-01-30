package com.maven.appointment;

import com.jfinal.aop.Aop;
import com.jfinal.kit.PropKit;
import com.jfinal.kit.Ret;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.SqlPara;
import com.maven.common.model.*;
import com.maven.common.util.RetUtils;
import com.maven.user.DepartmentEnum;
import com.maven.user.SexEnum;
import com.maven.user.UserService;
import org.apache.commons.lang3.StringUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 预约相关的service
 */
public class AppointmentService {
    public static final AppointmentService me = Aop.get(AppointmentService.class);
    private Appointment dao = Appointment.dao;
    private UserService userService = UserService.me;

    /**
     * 根据医生id和日期获取可以预约的列表
     */
    public Ret getTimeList(Integer doctorId, Date date){
        if(doctorId==null||date==null){
            return RetUtils.parameterFail();
        }
        LocalDate now = LocalDate.now();
        LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        if(localDate.compareTo(now)<=0){
            return RetUtils.fail("请至少提前一天预约");
        }
        return Ret.ok().set("values",getTimeListBeans(doctorId, localDate));
    }

    /**
     * 根据医生id和日期按照时间段分组获取预约信息
     */
    private List<TimeListBean> getTimeListBeans(Integer doctorId, LocalDate localDate){
        //获取某医生某天的已预约列表
        List<Appointment> appointments = dao.find("select * from t_appointment where Date(date)=? and doctor_id=?",
                localDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")),doctorId);
        //根据时间段分组，计算出每个时间段的预约数，放到Map里
        Map<Integer,Long> map = appointments.stream().collect(Collectors.groupingBy(Appointment::getTimeEnum,Collectors.counting()));
        //分组后的详细列表放到那一天的工作时间安排中,即使这一天某个时间段没有预约，也有一条数据
        List<TimeListBean> list = new ArrayList<>();
        //遍历工作时间段，往里面补充数据
        for(TimeEnum timeEnum : TimeEnum.values()){
            Integer total = PropKit.getInt("timeLimit");    //每个时段一个医生限制预约多少人
            Integer reservedNum = 0;                        //该时间段已预约的病人数
            if(map.containsKey(timeEnum.getValue())){
                reservedNum = map.get(timeEnum.getValue()).intValue();
            }
            list.add(new TimeListBean(timeEnum.getValue(),timeEnum.getText(),total,reservedNum,total - reservedNum));
        }
        return list;
    }

    /**
     * 提交挂号
     */
    public Ret appointment(Integer department, Integer doctorId, boolean isSelf, Integer patientId, Date date, Integer time,User userLogin){
        DepartmentEnum departmentEnum = DepartmentEnum.getByValue(department);
        TimeEnum timeEnum = TimeEnum.getByValue(time);
        if(departmentEnum==null||doctorId==null||(!isSelf&&patientId==null)||date==null||timeEnum==null){
            return RetUtils.parameterFail();
        }
        //是否有这个医生
        Doctor doctor = Doctor.dao.findById(doctorId);
        if(doctor==null||!doctor.getDepartment().equals(department)){
            return RetUtils.fail("无此医生");
        }
        //必须提前至少一天预约
        LocalDate now = LocalDate.now();
        LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        if(localDate.compareTo(now)<=0){
            return RetUtils.fail("请至少提前一天预约");
        }
        //判断该时间段是否已预约满
        TimeListBean timeListBean = getTimeListBeans(doctorId,localDate).stream().filter(e -> e.getTimeValue().equals(timeEnum.getValue())).findFirst().get();
        if(timeListBean.getRemainingNum().compareTo(0)<=0){
            return RetUtils.fail("该时间段已预约满，请选择其他时段");
        }
        Appointment appointment = new Appointment();
        appointment.setPatientId(userLogin.getRelation());
        appointment.setIsSelf(isSelf);
        appointment.setDate(date);
        appointment.setDepartment(department);
        appointment.setDoctorId(doctorId);
        appointment.setDoctorName(doctor.getName());
        appointment.setTimeEnum(timeEnum.getValue());
        appointment.setStatus(0);
        if(isSelf){
            appointment.setRealPatientId(userLogin.getRelation());
            appointment.setRealPatientName(userLogin.getName());
        }else {
            RelationPatient patient = RelationPatient.dao.findById(patientId);
            if(patient==null||!patient.getPatientId().equals(userLogin.getRelation())){
                return RetUtils.fail("就诊人不存在");
            }
            appointment.setRealPatientId(patientId);
            appointment.setRealPatientName(patient.getName());
        }
        boolean b = appointment.save();
        return b?RetUtils.ok("预约成功"):RetUtils.fail("预约失败");
    }

    /*我的预约记录*/
    public Page<Appointment> myAppointmentList(Integer pageSize,Integer pageNumber,Integer patientId){
        Page<Appointment> page = dao.paginate(pageNumber,pageSize,"select *","from t_appointment where patient_id = ?",patientId);
        for(Appointment appointment : page.getList()){
            DepartmentEnum departmentEnum = DepartmentEnum.getByValue(appointment.getDepartment());
            appointment.put("department_text",departmentEnum==null?"":departmentEnum.getText());
            TimeEnum timeEnum = TimeEnum.getByValue(appointment.getTimeEnum());
            appointment.put("time_text",timeEnum==null?"":timeEnum.getText());
            StatusEnum statusEnum = StatusEnum.getByValue(appointment.getStatus());
            appointment.put("status_text",statusEnum==null?"":statusEnum.getText());
        }
        return page;
    }

    /**
     * 当前医生今天已经预约的病人列表，支持翻页
     */
    public Ret list(Integer pageSize,Integer pageNumber,String name,User userLogin){
        //今天，比如2020-12-16
        String now = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        SqlPara sqlPara = new SqlPara();
        StringBuffer sb = new StringBuffer("");
        //根据医生id和今天查询已经预约的病人列表
        sb.append("select * from t_appointment where doctor_id=? and date >= str_to_date(?,'%Y-%m-%d') ");
        sqlPara.addPara(userLogin.getRelation());
        sqlPara.addPara(now);
        //病人姓名模糊查询条件
        if(StringUtils.isNotBlank(name)){
            sb.append(" and real_patient_name like ? ");
            sqlPara.addPara("%"+name+"%");
        }
        sb.append(" order by status,time_enum");
        sqlPara.setSql(sb.toString());
        Page<Appointment> page = dao.paginate(pageNumber,pageSize,sqlPara);
        List<Appointment> list = page.getList();
        //取出所有给自己看病的病人id列表
        List<Integer> patientIds = list.stream().filter(e -> e.getIsSelf()).map(Appointment::getRealPatientId).collect(Collectors.toList());
        //取出所有给家属看病的家属id列表
        List<Integer> relationPatientIds = list.stream().filter(e -> !e.getIsSelf()).map(Appointment::getRealPatientId).collect(Collectors.toList());
        //所有给自己看病的病人列表
        Map<Integer, Patient> patientMap = userService.findPatientByIds(patientIds).stream().collect(Collectors.toMap(Patient::getId,e ->e,(o,n)->n));
        //所有给家属看病的家属列表
        Map<Integer, RelationPatient> relationPatientMap = userService.findRelationPatientByIds(relationPatientIds).stream()
                .collect(Collectors.toMap(RelationPatient::getId,e ->e,(o,n)->n));
        //遍历list，放入性别、身份证、年龄、科室、看病进度、时间
        list.forEach(e->{
            //给自己看病的病人
            if(e.getIsSelf()){
                Patient patient = patientMap.containsKey(e.getRealPatientId())?patientMap.get(e.getRealPatientId()):null;
                if(patient==null){
                    e.put("sex","");
                    e.put("id_num","");
                    e.put("age","");
                }else {
                    SexEnum sexEnum = SexEnum.getByValue(patient.getSex());
                    e.put("sex",sexEnum.getText());
                    e.put("id_num",patient.getIdNum());
                    e.put("age",userService.getAge(patient.getBirthday()));
                }
            }else{
                 //给家属看病的
                RelationPatient relationPatient = relationPatientMap.containsKey(e.getRealPatientId())?relationPatientMap.get(e.getRealPatientId()):null;
                if(relationPatient==null){
                    e.put("sex","");
                    e.put("id_num","");
                    e.put("age","");
                }else {
                    SexEnum sexEnum = SexEnum.getByValue(relationPatient.getSex());
                    e.put("sex",sexEnum.getText());
                    e.put("id_num",relationPatient.getIdNum());
                    e.put("age",userService.getAge(relationPatient.getBirthday()));
                }
            }
            //科室、看病进度、时间
            DepartmentEnum departmentEnum = DepartmentEnum.getByValue(e.getDepartment());
            e.put("department_text",departmentEnum==null?"":departmentEnum.getText());
            TimeEnum timeEnum = TimeEnum.getByValue(e.getTimeEnum());
            e.put("time_text",timeEnum==null?"":timeEnum.getText());
            StatusEnum statusEnum = StatusEnum.getByValue(e.getStatus());
            e.put("status_text",statusEnum==null?"":statusEnum.getText());
        });
        return Ret.ok().set("rows",page.getList()).set("total",page.getTotalRow());
    }
}



























