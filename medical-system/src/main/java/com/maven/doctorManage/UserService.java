package com.maven.doctorManage;

import com.jfinal.aop.Aop;
import com.jfinal.aop.Before;
import com.jfinal.kit.Ret;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.SqlPara;
import com.jfinal.plugin.activerecord.tx.Tx;
import com.maven.common.model.Doctor;
import com.maven.common.model.User;
import com.maven.common.util.RetUtils;
import com.maven.user.DepartmentEnum;
import com.maven.user.SexEnum;
import com.maven.user.UserTypeEnum;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

/**
 * 医生用户相关的service操作
 */
public class UserService {
    private User dao = User.dao;
    public static final UserService me = Aop.get(UserService.class);

    /*保存医生，分别往doctor和user表里存入数据*/
    @Before(Tx.class)
    public Ret saveDoctor(User user,Integer department,String introduction){
        DepartmentEnum departmentEnum = DepartmentEnum.getByValue(department);
        SexEnum sexEnum = SexEnum.getByValue(user.getSex());
        if(StringUtils.isBlank(user.getAccount())||StringUtils.isBlank(user.getPassword())||departmentEnum==null
                ||StringUtils.isBlank(user.getName())||sexEnum==null){
            return RetUtils.parameterFail();
        }
        Ret ret = checkAccount(user.getAccount());
        if(ret.isFail()){
            return ret;
        }
        Doctor doctor = new Doctor();
        doctor.setDepartment(department);
        doctor.setName(user.getName());
        doctor.setSex(user.getSex());
        doctor.setIntroduction(introduction);
        doctor.save();
        user.setRelation(doctor.getId());
        user.setUserType(UserTypeEnum.DOCTOR.getValue());
        return RetUtils.save(user.save());
    }

    /*判断帐号是否已存在*/
    public Ret checkAccount(String account){
        SqlPara sqlPara = new SqlPara();
        sqlPara.setSql("select * from t_user where account = ?").addPara(account);
        User user = dao.findFirst(sqlPara);
        if(user!=null){
            return RetUtils.fail("该帐号已存在");
        }
        return Ret.ok();
    }

    /*查询医生列表，带分页功能*/
    public Ret list(Integer pageNumber,Integer pageSize){
        Page<User> page = dao.paginate(pageNumber,pageSize,
                "select u.id,u.account,u.name,u.sex,u.relation,d.department,d.introduction",
                "from t_user u left join t_doctor d on u.relation=d.id "+
                " where u.user_type=?",UserTypeEnum.DOCTOR.getValue());
        page.getList().forEach(e -> {
            DepartmentEnum departmentEnum = DepartmentEnum.getByValue(e.getInt("department"));
            e.put("departmen_text",departmentEnum==null?"":departmentEnum.getText());
            SexEnum sexEnum = SexEnum.getByValue(e.getInt("sex"));
            e.put("sex_text",sexEnum==null?"":sexEnum.getText());
        });
        return Ret.ok().set("rows",page.getList()).set("total",page.getTotalRow());
    }

    /*修改医生，分别更新doctor和user表的数据*/
    @Before(Tx.class)
    public Ret updateDoctor(User user,Integer department,String introduction){
        DepartmentEnum departmentEnum = DepartmentEnum.getByValue(department);
        SexEnum sexEnum = SexEnum.getByValue(user.getSex());
        if(user.getId()==null||departmentEnum==null||StringUtils.isBlank(user.getName())||sexEnum==null){
            return RetUtils.parameterFail();
        }
        User user1 = dao.findById(user.getId());
        if(user1==null||!user1.getUserType().equals(UserTypeEnum.DOCTOR.getValue())){
            return RetUtils.fail("无此用户");
        }
        Doctor doctor = Doctor.dao.findById(user1.getRelation());
        if(doctor==null){
            return RetUtils.fail("无此用户");
        }
        doctor.setDepartment(department);
        doctor.setName(user.getName());
        doctor.setSex(user.getSex());
        doctor.setIntroduction(introduction);
        doctor.update();
        user1.setName(user.getName());
        user1.setSex(user.getSex());
        return RetUtils.save(user1.update());
    }

    /*删除一个医生*/
    @Before(Tx.class)
    public Ret deleteDoctor(Integer id){
        if(id==null){
            return RetUtils.parameterFail();
        }
        User user = dao.findById(id);
        if(user==null||!user.getUserType().equals(UserTypeEnum.DOCTOR.getValue())){
            return RetUtils.fail("无此用户");
        }
        Doctor doctor = Doctor.dao.findById(user.getRelation());
        if(doctor==null){
            return RetUtils.fail("无此用户");
        }
        doctor.delete();
        return RetUtils.save(user.delete());
    }

    /*根据科室id查询所有医生*/
    public List<Doctor> findDoctorByDepartment(Integer department){
        return Doctor.dao.find("select id,name from t_doctor where department = ?",department);
    }
}



































