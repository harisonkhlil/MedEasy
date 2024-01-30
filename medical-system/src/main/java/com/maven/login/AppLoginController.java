package com.maven.login;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jfinal.aop.Clear;
import com.jfinal.kit.Ret;
import com.maven.common.controller.AppBaseController;
import com.maven.common.interceptor.MyInterceptor;
import com.maven.common.model.Patient;
import com.maven.common.model.User;
import com.maven.common.util.Constant;
import com.maven.common.util.RetUtils;
import com.maven.user.UserService;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpSession;
import java.util.UUID;

/**
 * app端登录相关
 */
@Clear(MyInterceptor.class)
public class AppLoginController extends AppBaseController {
    public static LoginService src = LoginService.me;
    public static UserService userService = UserService.me;

    /**
     * 登录验证
     */
    public void doLogin() {
        String account = this.getPara("userName");
        String password = this.getPara("password");
        System.out.println(account + "---" + password);

        //判断帐号密码是否为空
        if (StringUtils.isBlank(account) || StringUtils.isBlank(password)) {
            this.renderJson(RetUtils.fail("帐号密码有误！"));
            return;
        }

        //判断帐号密码是否正确
        User user = src.appLogin(account, password);
        if (user == null) {
            this.renderJson(RetUtils.fail("帐号密码有误！"));
            return;
        }
        String token = UUID.randomUUID().toString();
        HttpSession session = this.getSession(true);
        session.setAttribute(Constant.TOKEN_KEY, token);
        session.setAttribute(Constant.LOGIN_USER_KEY, user);

        this.renderJson(RetUtils.ok("登录成功").set("token", token).set("userId", user.getId())
                .set("account", user.getAccount()));
    }

    /**
     * 检测帐号是否存在
     */
    public void checkAccount() {
        String account = this.getPara("account");
        if (StringUtils.isBlank(account)) {
            this.renderJson(RetUtils.parameterFail());
            return;
        }
        this.renderJson(userService.checkAccount(account));
    }

    /**
     * 病人注册
     */
    public void register() {
        Patient patient = this.getModel(Patient.class, "", true);
        String account = this.getPara("account");
        String password = this.getPara("password");
        this.renderJson(src.register(patient, account, password));
    }

}















