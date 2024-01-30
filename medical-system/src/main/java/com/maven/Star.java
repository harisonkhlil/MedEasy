package com.maven;

import com.jfinal.config.*;
import com.jfinal.kit.Prop;
import com.jfinal.kit.PropKit;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.druid.DruidPlugin;
import com.jfinal.server.undertow.UndertowServer;
import com.jfinal.template.Engine;
import com.maven.appointment.AppAppointmentController;
import com.maven.appointment.AppointmentController;
import com.maven.common.interceptor.MyInterceptor;
import com.maven.common.model._MappingKit;
import com.maven.common.util.AdminMenuKit;
import com.maven.doctorManage.DoctorManageController;
import com.maven.login.AppLoginController;
import com.maven.login.IndexController;
import com.maven.login.LoginController;
import com.maven.user.AppUserInfoController;
import com.maven.user.UserInfoController;

/**
 * 本 demo 仅表达最为粗浅的 jfinal 用法，更为有价值的实用的企业级用法
 * 详见 JFinal 俱乐部: http://jfinal.com/club
 * <p>
 * API 引导式配置
 */
public class Star extends JFinalConfig {
    static Prop p;

    /**
     * 启动入口，运行此 main 方法可以启动项目，此 main 方法可以放置在任意的 Class 类定义中，不一定要放于此
     */
    public static void main(String[] args) {
        UndertowServer.start(Star.class,80,true);
    }

    /**
     * 加载系统配置
     */
    public static void loadConfig(){
        if(p == null){
            //useFirstFound使用参数中最先发现的一个，找到了就不继续找了
            p = PropKit.useFirstFound("config.txt");
        }
    }

    /**
     * 配置常量
     */
    @Override
    public void configConstant(Constants me) {
        loadConfig();
        me.setDevMode(p.getBoolean("devMode",true));
        //支持Controller、Interceptor（拦截器）、Validator之中使用@Inject注入业务层，自动实现AOP
        me.setInjectDependency(true);
        //支持对超类中的属性进行注入
        me.setInjectSuperClass(true);
    }

    /**
     * 配置路由
     */
    @Override
    public void configRoute(Routes me) {
        //前端app登录相关
        me.add("/appLogin", AppLoginController.class);
        //前端app预约挂号相关
        me.add("/appAppointment", AppAppointmentController.class);
        //前端用户管理相关
        me.add("/appUserInfo", AppUserInfoController.class);

        //后端登录相关
        me.add("/login", LoginController.class,"/login");
        //后端登录相关
        me.add("/index", IndexController.class,"/_admin/index");
        //后端用户相关
        me.add("/userInfo", UserInfoController.class,"_admin/userInfo");
        //管理医生相关
        me.add("/doctorManage", DoctorManageController.class,"_admin/doctorManage");
        //后端预约挂号相关
        me.add("/appointment", AppointmentController.class,"_admin/appointment");

    }

    /**
     * 前后端公共的方法或页面
     */
    @Override
    public void configEngine(Engine me) {
        me.addSharedMethod(new AdminMenuKit());
        me.addSharedFunction("/base/base.html");
    }

    /**
     * 配置插件
     */
    @Override
    public void configPlugin(Plugins me) {
        //配置druid数据库连接池插件
        DruidPlugin druidPlugin = createDruidPlugin();
        me.add(druidPlugin);
        //配置ActiveRecord插件
        ActiveRecordPlugin arp = new ActiveRecordPlugin(druidPlugin);
        //所有的映射在_MappingKit中自动化完成
        _MappingKit.mapping(arp);
        me.add(arp);
    }

    //配置druid数据库连接池插件
    public static DruidPlugin createDruidPlugin() {
        loadConfig();
        return new DruidPlugin(p.get("jdbcUrl").trim(),p.get("user").trim(),p.get("password").trim());
    }


    /**
     * 配置全局拦截器
     */
    @Override
    public void configInterceptor(Interceptors me) {
        me.add(new MyInterceptor());
    }

    /**
     * 配置处理器
     */
    @Override
    public void configHandler(Handlers me) {

    }

}
