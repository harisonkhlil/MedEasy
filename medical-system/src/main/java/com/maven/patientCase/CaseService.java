package com.maven.patientCase;

import com.jfinal.aop.Aop;
import com.jfinal.aop.Before;
import com.jfinal.kit.Ret;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.tx.Tx;
import com.maven.appointment.StatusEnum;
import com.maven.common.model.Appointment;
import com.maven.common.model.Case;
import com.maven.common.model.User;
import com.maven.common.util.RetUtils;
import com.maven.user.DepartmentEnum;
import org.apache.commons.lang3.StringUtils;

import java.util.Date;
import java.util.List;

/**
 * 病例相关的service
 */
public class CaseService {
    public static final CaseService me = Aop.get(CaseService.class);
    private Case dao = Case.dao;

    /**
     * 新增病例
     */
    @Before(Tx.class)
    public Ret saveForAppointment(Integer id, String content, User userLogin){
        //参数不够
        if(id==null|| StringUtils.isBlank(content)){
            return RetUtils.parameterFail();
        }
        //找到对应的预约记录
        Appointment appointment = Appointment.dao.findById(id);
        if(appointment==null||!appointment.getDoctorId().equals(userLogin.getRelation())){
            return RetUtils.fail("无此预约记录");
        }
        //更新就诊状态为已就诊
        appointment.setStatus(StatusEnum.COMPLETE.getValue());
        if(!appointment.update()){
            return RetUtils.saveFail();
        }
        Case patientCase = new Case();
        patientCase.setPatientId(appointment.getPatientId());
        patientCase.setIsSelf(appointment.getIsSelf());
        patientCase.setRealPatientId(appointment.getRealPatientId());
        patientCase.setRealPatientName(appointment.getRealPatientName());
        patientCase.setDepartment(appointment.getDepartment());
        patientCase.setDoctorId(appointment.getDoctorId());
        patientCase.setDoctorName(appointment.getDoctorName());
        patientCase.setDate(new Date());
        patientCase.setContent(content);
        return RetUtils.save(patientCase.save());
    }

    /**
     * 根据就诊记录id查询历史病例，支持分页
     * id:当前就诊记录的主键
     */
    public Ret findByAppointment(Integer id,Integer pageSize,Integer pageNumber,User userLogin){
        if(id==null){
            return RetUtils.parameterFail();
        }
        //获取就诊记录
        Appointment appointment = Appointment.dao.findById(id);
        if(!userLogin.getRelation().equals(appointment.getDoctorId())){
            return RetUtils.fail("无此预约记录");
        }
        return findForPatient(appointment.getRealPatientId(),appointment.getIsSelf(),pageSize,pageNumber);
    }

    /**
     * 根据病人id查询历史病例，支持分页
     */
    public Ret findForPatient(Integer patientId,boolean isSelf,Integer pageSize,Integer pageNumber){
        if(patientId==null){
            return RetUtils.parameterFail();
        }
        int isSelfInt = isSelf?1:0;
        Page<Case> page = dao.paginate(pageNumber,pageSize,"select *","from t_case where patient_id = ? and is_self=? order by date",patientId,isSelfInt);
        List<Case> list = page.getList();
        list.forEach(e ->{
            DepartmentEnum departmentEnum = DepartmentEnum.getByValue(e.getDepartment());
            e.put("department_text",departmentEnum==null?"":departmentEnum.getText());
        });
        return Ret.ok().set("rows",list).set("total",page.getTotalRow());
    }

}















