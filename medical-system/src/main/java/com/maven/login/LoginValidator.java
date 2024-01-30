package com.maven.login;

import com.jfinal.core.Controller;
import com.jfinal.validate.Validator;

/**
 * 登录前的验证
 */
public class LoginValidator extends Validator {

    /**
     * Use validateXxx method to validate the parameters of this action.
     *
     * @param c
     */
    @Override
    protected void validate(Controller c) {
        setShortCircuit(true);
        validateCaptcha("captcha","captchaMsg","验证码不正确");
    }

    /**
     * Handle the validate error.
     * Example:<br>
     * controller.keepPara();<br>
     * controller.render("register.html");
     *
     * @param c
     */
    @Override
    protected void handleError(Controller c) {
        c.renderJson();
    }
}
