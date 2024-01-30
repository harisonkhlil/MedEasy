package com.maven.appointment;

import com.jfinal.kit.Ret;
import com.jfinal.plugin.activerecord.Page;
import com.maven.common.controller.AppBaseController;
import com.maven.common.model.Appointment;
import com.maven.common.model.User;
import com.maven.common.util.Constant;
import com.maven.doctorManage.UserService;
import com.maven.patientCase.CaseService;

import java.util.Date;

/**
 * app端预约挂号
 */
public class AppAppointmentController extends AppBaseController {
    private static AppointmentService src = AppointmentService.me;
    /**
     * 根据科室id查询所有医生
     */
    public void getDoctorList(){
        Integer department = this.getInt("department");
        this.renderJson(Ret.ok().set("values", UserService.me.findDoctorByDepartment(department)));
    }

    /**
     * 根据医生id和日期按照时间段分组获取预约信息
     */
    public void getTimeList(){
        Date date = this.getParaToDate("date");
        Integer doctorId = this.getInt("doctor");
        this.renderJson(src.getTimeList(doctorId,date));
    }

    /**
     * 提交挂号
     */
    public void appointment(){
        Integer department = this.getInt("department");
        Integer doctorId = this.getInt("doctor");
        boolean isSelf = this.getBoolean("isSelf",true);
        Integer patientId = this.getInt("patient");
        Date date = this.getDate("date");
        Integer time = this.getInt("time");
        User userLogin = getUserLogin();
        this.renderJson(src.appointment(department,doctorId,isSelf,patientId,date,time,userLogin));
    }

    /**
     * 根据病人id查询他的其他就诊人列表,仅返回其他就诊人的id和名字
     */
    public void relationPatientSel(){
        this.renderJson(Ret.ok().set("values",com.maven.user.UserService.me.findRelationPatientSel(this.getUserLogin())));
    }

    /**
     * 我的预约记录
     */
    public void myAppointmentList(){
        Integer pageSize = this.getInt("pageSize", Constant.DEFAULT_PAGE_SIZE);
        Integer pageNumber = this.getInt("pageNumber",Constant.DEFAULT_PAGE_NUMBER);
        Page<Appointment> page = src.myAppointmentList(pageSize,pageNumber,this.getUserLogin().getRelation());
        this.renderJson(Ret.ok().set("rows",page.getList()).set("total",page.getTotalRow()));
    }

    /**
     * 根据病人id查询历史病例，支持分页
     */
    public void caseList(){
        Integer pageSize = this.getInt("pageSize", Constant.DEFAULT_PAGE_SIZE);
        Integer pageNumber = this.getInt("pageNumber",Constant.DEFAULT_PAGE_NUMBER);
        this.renderJson(CaseService.me.findForPatient(getUserLogin().getRelation(),true,pageSize,pageNumber));
    }
}






















