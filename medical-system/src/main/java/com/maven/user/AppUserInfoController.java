package com.maven.user;

import com.jfinal.kit.Ret;
import com.maven.common.controller.AppBaseController;
import com.maven.common.model.RelationPatient;
import com.maven.common.model.User;
import com.maven.common.util.Constant;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * 前端用户管理相关的controller
 */
public class AppUserInfoController extends AppBaseController {
    private static UserService me = UserService.me;

    /**
     * 新增其他就诊人保存
     */
    public void saveRelationPatient(){
        RelationPatient patient = this.getModel(RelationPatient.class,"",true);
        this.renderJson(me.saveRelationPatient(patient,this.getUserLogin()));
    }

    /**
     * 根据病人id查询他的其他就诊人列表，支持分页
     */
    public void relationPatientList(){
        Integer pageSize = this.getInt("pageSize", Constant.DEFAULT_PAGE_SIZE);
        Integer pageNumber = this.getInt("pageNumber",Constant.DEFAULT_PAGE_NUMBER);
        this.renderJson(me.findRelationPatients(pageNumber,pageSize,this.getUserLogin().getRelation()));
    }

    /**
     * 删除一个其他就诊人
     */
    public void deleteRelationPatient(){
        Integer id = this.getInt("id");
        this.renderJson(me.deleteRelationPatient(id,this.getUserLogin()));
    }

    /**
     * 修改密码
     */
    public void modifyPassword(){
        String oldPassword = this.getPara("oldPassword");
        String password = this.getPara("password");
        String confirmPassword = this.getPara("confirmPassword");
        Ret ret = me.modifyPassword(this.getUserLogin(),oldPassword,password,confirmPassword);
        //修改密码成功后需要清除session
        if(ret.isOk()){
            HttpSession session = this.getSession(true);
            session.invalidate();
        }
        this.renderJson(ret);
    }
}















