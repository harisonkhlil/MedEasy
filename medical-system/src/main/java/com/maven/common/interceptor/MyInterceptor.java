package com.maven.common.interceptor;

import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;
import com.jfinal.core.Controller;
import com.maven.common.auth.NeedUserType;
import com.maven.common.controller.AdminBaseController;
import com.maven.common.controller.AppBaseController;
import com.maven.common.model.User;
import com.maven.common.util.Constant;
import com.maven.common.util.RetUtils;
import com.maven.user.UserTypeEnum;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpSession;
import java.lang.reflect.Method;

/**
 * 拦截非登录用户对需要登录才能访问的资源的访问
 */
public class MyInterceptor implements Interceptor {

    @Override
    public void intercept(Invocation inv) {
        Controller controller = inv.getController();
        HttpSession session;
        //先对app端的请求进行拦截
        if(controller instanceof AppBaseController){
            session = controller.getSession(true);
            String token = controller.getPara(Constant.TOKEN_KEY);
            String sessionToken = (String) session.getAttribute(Constant.TOKEN_KEY);
            if(StringUtils.isBlank(token)||StringUtils.isBlank(sessionToken)||!token.equals(sessionToken)){
                controller.renderJson(RetUtils.fail("您未登录或登录超时").set("code",Constant.UNLOGIN_CODE));
                return;
            }
        }else {
            session = controller.getSession();
        }
        User user = (User) session.getAttribute(Constant.LOGIN_USER_KEY);
        if(user==null){
            if(controller instanceof AdminBaseController){
                controller.forwardAction("/login");
                controller.setAttr("msg","您未登录或登录超时");
                return;
            }
            controller.renderJson(RetUtils.fail("您未登录或登录超时").set("code",Constant.UNLOGIN_CODE));
            return;
        }
        if(!checkPermit(controller, inv.getMethod(), user)){
            controller.renderJson(RetUtils.fail("您没有权限访问"));
            return;
        }
        controller.setAttr("userName",user.getName());
        controller.setAttr("userType",user.getUserType());
        inv.invoke();
    }

    /**
     * 根据用户类型判断是否有权限访问
     */
    private Boolean checkPermit(Controller controller, Method method, User user){
        //类注解
        NeedUserType classPermit = controller.getClass().getAnnotation(NeedUserType.class);
        //方法注解
        NeedUserType methodPermit = method.getAnnotation(NeedUserType.class);
        //如果类注解和方法注解都不需要用户类型判断，直接返回true
        if(classPermit==null && methodPermit==null){
            return true;
        }
        //获取用户类型
        UserTypeEnum userTypeEnum = UserTypeEnum.getByValue(user.getUserType());
        if(userTypeEnum == null){
            return false;
        }
        if(classPermit!=null){
            if(!checkPermit(classPermit,userTypeEnum)){
                return false;
            }
        }
        if(methodPermit!=null){
            if(!checkPermit(methodPermit,userTypeEnum)){
                return false;
            }
        }
        return true;
    }

    /**
     * 根据需要的权限和用户类型判断是否有权限
     */
    private boolean checkPermit(NeedUserType needPermissions,UserTypeEnum userTypeEnum){
        UserTypeEnum[] values = needPermissions.value();
        for(UserTypeEnum value:values){
            if(userTypeEnum.equals(value)){
                return true;
            }
        }
        return false;
    }
}


















