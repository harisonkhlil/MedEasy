package com.maven.login;

import com.jfinal.aop.Aop;
import com.jfinal.aop.Before;
import com.jfinal.kit.Ret;
import com.jfinal.plugin.activerecord.SqlPara;
import com.jfinal.plugin.activerecord.tx.Tx;
import com.maven.common.model.Doctor;
import com.maven.common.model.Patient;
import com.maven.common.model.User;
import com.maven.common.util.RetUtils;
import com.maven.user.SexEnum;
import com.maven.user.UserService;
import com.maven.user.UserTypeEnum;

/**
 * 登录验证service
 */
public class LoginService {
    private User dao = User.dao;
    public static final LoginService me = Aop.get(LoginService.class);

    /**
     * app端的登录验证
     */
    public User appLogin(String account,String password){
        SqlPara sqlPara = new SqlPara();
        sqlPara.setSql("select * from t_user where account = ? and password = ? and user_type = ?")
                .addPara(account).addPara(password).addPara(UserTypeEnum.PATIENT.getValue());
        User user = dao.findFirst(sqlPara);
        return user;
    }

    /**
     * 病人注册
     */
    @Before(Tx.class)
    public Ret register(Patient patient,String account,String password){
        if(patient==null||patient.getBirthday()==null||patient.getIdNum()==null||patient.getName()==null||patient.getPhone()==null||account==null||password==null){
            return RetUtils.parameterFail();
        }
        SexEnum sexEnum = SexEnum.getByValue(patient.getSex());
        if(sexEnum == null){
            return RetUtils.parameterFail();
        }
        Ret ret = UserService.me.checkAccount(account);
        if(ret.isFail()){
            return ret;
        }
        if(!patient.save()){
            return RetUtils.fail("注册失败");
        }
        User user = new User();
        user.setName(patient.getName());
        user.setSex(patient.getSex());
        user.setAccount(account);
        user.setPassword(password);
        user.setUserType(UserTypeEnum.PATIENT.getValue());
        user.setRelation(patient.getId());
        return user.save()?RetUtils.ok("注册成功"):RetUtils.fail("注册失败");
    }

    /**
     * 后台用户的登录验证
     */
    public User login(String account,String password){
        SqlPara sqlPara = new SqlPara();
        sqlPara.setSql("select * from t_user where account = ? and password = ? and user_type != ?")
                .addPara(account).addPara(password).addPara(UserTypeEnum.PATIENT.getValue());
        User user = dao.findFirst(sqlPara);
        if(user!=null){
            switch (UserTypeEnum.getByValue(user.getUserType())){
                case DOCTOR:
                    Doctor doctor = Doctor.dao.findById(user.getRelation());
                    user.put("relation_info",doctor);
                    break;
                default:
                    user.put("relation_info",null);
                    break;
            }
        }
        return user;
    }
}





















