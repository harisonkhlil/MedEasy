package com.maven.common.util;

import com.maven.common.bean.AdminMenu;
import com.maven.user.UserTypeEnum;

import java.util.ArrayList;
import java.util.List;

/**
 * 后台菜单工具类
 */
public class AdminMenuKit {

    /*管理员菜单列表*/
    private List<AdminMenu> adminMenuList = new ArrayList<>();
    /*医生菜单列表*/
    private List<AdminMenu> doctorMenuList = new ArrayList<>();

    public AdminMenuKit() {
        this.adminMenuList();
        this.doctorMenuList();
    }

    private void adminMenuList(){
        adminMenuList.add(new AdminMenu("医生管理","/doctorManage","ti-user"));
    }

    private void doctorMenuList(){
        doctorMenuList.add(new AdminMenu("问诊","/appointment","ti-bell"));
    }

    /*根据用户类型获取菜单列表*/
    public List<AdminMenu> getMenuList(Integer userType){
        UserTypeEnum userTypeEnum = UserTypeEnum.getByValue(userType);
        if(userTypeEnum == null){
            return new ArrayList<>();
        }
        switch (userTypeEnum){
            case ADMIN:
                return adminMenuList;
            case DOCTOR:
                return doctorMenuList;
            default:
                return new ArrayList<>();
        }
    }
}














