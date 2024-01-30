package www.test.com.medical_system.utils;

/**
 * 和后台的接口地址
 */
public class Constants {
    /*根地址*/
//    public static String BASE_URL = "http://172.20.100.96";
    public static String BASE_URL = "http://192.168.6.216";
    /*登录验证*/
    public static String LOGIN = "/appLogin/doLogin";
    /*验证账号是否已存在*/
    public static String CHECK_ACCOUNT = "/appLogin/checkAccount";
    /*病人注册*/
    public static String REGISTER = "/appLogin/register";
    /*根据科室id查询所有医生*/
    public static String DOCTOR_LIST = "/appAppointment/getDoctorList";
    /*根据医生id和日期按照时间段分组获取预约信息*/
    public static String GET_DOCTOR_TIME_LIST = "/appAppointment/getTimeList";
    /*提交挂号*/
    public static String MAKE_APPOINTMENT = "/appAppointment/appointment";
    /*根据病人id查询他的其他就诊人列表,仅返回其他就诊人的id和名字*/
    public static String OTHERS_LIST_4_ORDER = "/appAppointment/relationPatientSel";
    /*我的预约记录*/
    public static String APPOINTMENT_RECORD = "/appAppointment/myAppointmentList";
    /*根据病人id查询历史病例，支持分页*/
    public static String CASE_LIST = "/appAppointment/caseList";

    /*新增其他就诊人保存*/
    public static String SAVE_OTHERS = "/appUserInfo/saveRelationPatient";
    /*其他就诊人列表*/
    public static String OTHERS_LIST = "/appUserInfo/relationPatientList";
    /*删除一个其他就诊人*/
    public static String DELETE_OTHERS = "/appUserInfo/deleteRelationPatient";
    /*修改密码*/
    public static String UPDATE_PWD = "/appUserInfo/modifyPassword";

}
