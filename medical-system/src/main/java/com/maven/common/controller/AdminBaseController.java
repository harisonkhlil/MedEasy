package com.maven.common.controller;

import com.jfinal.core.Controller;
import com.maven.common.model.User;
import com.maven.common.util.Constant;

/**
 * 后台congtroller父类
 */
public class AdminBaseController extends Controller {
    /**
     * 获取用户session
     */
    protected User getUserLogin(){
        User user = this.getSessionAttr(Constant.LOGIN_USER_KEY);
        return user;
    }
}
