package com.maven.doctorManage;

import com.jfinal.kit.Ret;
import com.maven.common.auth.NeedUserType;
import com.maven.common.controller.AdminBaseController;
import com.maven.common.model.User;
import com.maven.common.util.RetUtils;
import com.maven.user.DepartmentEnum;
import com.maven.user.SexEnum;
import com.maven.user.UserTypeEnum;

/**
 * 管理医生
 */
@NeedUserType(UserTypeEnum.ADMIN)
public class DoctorManageController extends AdminBaseController {
    private static UserService src = UserService.me;
    /**
     * 默认页转向医生管理列表页面
     */
    public void index(){
        this.setAttr("sexEnum", SexEnum.getValues());
        this.setAttr("departmentEnum", DepartmentEnum.getValues());
        this.render("doctorManage.html");
    }

    /*查询医生列表，带分页功能*/
    public void list(){
        Integer pageSize = this.getParaToInt("limit",10);
        Integer pageNumber = this.getParaToInt("offset",0)/pageSize+1;
        this.renderJson(src.list(pageNumber,pageSize));
    }

    /**
     * 判断帐号是否已存在
     */
    public void checkAccount(){
        String account = this.getPara("account");
        this.renderJson(src.checkAccount(account));
    }

    /**
     * 新增医生
     */
    public void save(){
        User model = this.getModel(User.class,"",true);
        Integer department = this.getInt("department");
        String introduction = this.getPara("introduction");
        Ret ret = src.saveDoctor(model,department,introduction);
        this.renderJson(ret);
    }

    /**
     * 修改医生
     */
    public void update(){
        User model = this.getModel(User.class,"",true);
        Integer department = this.getInt("department");
        String introduction = this.getPara("introduction");
        Ret ret = src.updateDoctor(model,department,introduction);
        this.renderJson(ret);
    }

    /**
     * 删除一个医生
     */
    public void delete(){
        Integer id = this.getInt("id");
        this.renderJson(src.deleteDoctor(id));
    }
}



















