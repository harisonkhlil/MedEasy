package com.maven.common.controller;

import com.jfinal.core.Controller;
import com.maven.common.model.User;
import com.maven.common.util.Constant;

/**
 * app端的controller基类
 */
public abstract class AppBaseController extends Controller {
    /**
     * 获取用户session
     */
    protected User getUserLogin(){
        User user = this.getSessionAttr(Constant.LOGIN_USER_KEY);
        return user;
    }
}
