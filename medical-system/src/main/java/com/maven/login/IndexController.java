package com.maven.login;

import com.maven.common.auth.NeedUserType;
import com.maven.common.controller.AdminBaseController;
import com.maven.user.UserTypeEnum;

/**
 * 后台首页
 */
public class IndexController extends AdminBaseController {

    /*后台首页*/
    public void index(){
        this.render("index.html");
    }
}
