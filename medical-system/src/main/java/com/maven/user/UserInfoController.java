package com.maven.user;

import com.jfinal.core.ActionKey;
import com.jfinal.kit.Ret;
import com.maven.common.auth.NeedUserType;
import com.maven.common.controller.AdminBaseController;
import com.maven.common.model.User;

/**
 * 用户相关
 */
public class UserInfoController extends AdminBaseController {
    private static UserService src = UserService.me;

    /**
     * 转向修改密码页面
     */
    @ActionKey("/modifyPassword")
    @NeedUserType({UserTypeEnum.DOCTOR,UserTypeEnum.ADMIN})
    public void modifyPassword(){
        this.render("modifyPassword.html");
    }

    /**
     * 修改后台用户的密码
     */
    @NeedUserType({UserTypeEnum.DOCTOR,UserTypeEnum.ADMIN})
    public void doModifyPassword(){
        User user = getUserLogin();
        String oldPassword = this.getPara("old_password");
        String password = this.getPara("password");
        String confirmPassword = this.getPara("confirm_password");
        Ret ret = src.modifyPassword(user,oldPassword,password,confirmPassword);
        if(ret.isOk()){
            this.getSession().invalidate();
        }
        this.renderJson(ret);
    }
}

















