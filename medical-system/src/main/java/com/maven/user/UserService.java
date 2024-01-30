package com.maven.user;

import com.jfinal.aop.Aop;
import com.jfinal.kit.Ret;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.SqlPara;
import com.maven.common.model.Patient;
import com.maven.common.model.RelationPatient;
import com.maven.common.model.User;
import com.maven.common.util.RetUtils;
import com.maven.login.LoginService;
import com.sun.org.apache.bcel.internal.generic.RET;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 用户相关的service操作
 */
public class UserService {
    private User dao = User.dao;
    public static final UserService me = Aop.get(UserService.class);

    /**
     * 检测帐号是否存在
     */
    public Ret checkAccount(String account){
        SqlPara sqlPara = new SqlPara();
        sqlPara.setSql("select * from t_user where account = ?")
                .addPara(account);
        User user = dao.findFirst(sqlPara);
        if(user!=null){
            return RetUtils.fail("该帐号已存在");
        }
        return Ret.ok();
    }

    /**
     * 修改密码
     */
    public Ret modifyPassword(User user,String oldPassword,String password,String confirmPassword){
        if(StringUtils.isBlank(oldPassword)||StringUtils.isBlank(password)||StringUtils.isBlank(confirmPassword)){
            return RetUtils.fail("密码不能为空");
        }
        if(!password.equals(confirmPassword)){
            return RetUtils.fail("两次密码输入不一致");
        }
        if(user == null){
            return RetUtils.fail("无此用户");
        }
        if(!user.getPassword().equals(oldPassword)){
            return RetUtils.fail("旧密码错误");
        }
        if(user.getPassword().equals(password)){
            return RetUtils.fail("新密码不能与旧密码一样");
        }
        user.setPassword(password);
        boolean flag = user.update();
        return flag?RetUtils.ok("修改成功，请重新登录"):RetUtils.fail("密码修改失败");
    }

    /*新增其他就诊人保存*/
    public Ret saveRelationPatient(RelationPatient patient,User userLogin){
        if(patient==null||patient.getBirthday()==null||patient.getIdNum()==null||patient.getName()==null||patient.getPhone()==null){
            return RetUtils.parameterFail();
        }
        SexEnum sexEnum = SexEnum.getByValue(patient.getSex());
        if(sexEnum == null){
            return RetUtils.parameterFail();
        }
        patient.setPatientId(userLogin.getRelation());
        return RetUtils.save(patient.save());
    }

    /*根据病人id查询他的其他就诊人列表，支持分页*/
    public Ret findRelationPatients(Integer pageNumber,Integer pageSize,Integer patientId){
        Page<RelationPatient> patientPage = RelationPatient.dao.paginate(pageNumber,pageSize,
                "select *","from t_relation_patient where patient_id = ?",patientId);
        return Ret.ok().set("rows",patientPage.getList()).set("total",patientPage.getTotalRow());
    }

    /*根据病人id查询他的其他就诊人列表,仅返回其他就诊人的id和名字*/
    public List<RelationPatient> findRelationPatientSel(User userLogin){
        List<RelationPatient> list = RelationPatient.dao.find("select id,name from t_relation_patient where patient_id = ?",userLogin.getRelation());
        return list;
    }

    /*删除一个其他就诊人*/
    public Ret deleteRelationPatient(Integer id,User userLogin){
        if(id == null){
            return RetUtils.parameterFail();
        }
        RelationPatient patient = RelationPatient.dao.findById(id);
        if(patient==null||!patient.getPatientId().equals(userLogin.getRelation())){
            return RetUtils.fail("没有这个其他就诊人");
        }
        return RetUtils.delete(patient.delete());
    }

    /*根据病人id列表查询病人详情列表*/
    public List<Patient> findPatientByIds(List<Integer> ids){
        if(CollectionUtils.isEmpty(ids)){
            return new ArrayList<>();
        }
        return Patient.dao.find("select * from t_patient where id in (?)",StringUtils.join(ids,","));
    }

    /*根据病人家属id列表查询看病的那个病人家属详情列表*/
    public List<RelationPatient> findRelationPatientByIds(List<Integer> ids){
        if(CollectionUtils.isEmpty(ids)){
            return new ArrayList<>();
        }
        return RelationPatient.dao.find("select * from t_relation_patient where id in (?)",StringUtils.join(ids,","));
    }

    /*根据生日计算年龄*/
    public int getAge(Date birthday){
        DateFormat df = new SimpleDateFormat("yyyy");
        int year = Integer.parseInt(df.format(birthday));
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        int curYear = c.get(Calendar.YEAR);
        return curYear - year;
    }
}





























