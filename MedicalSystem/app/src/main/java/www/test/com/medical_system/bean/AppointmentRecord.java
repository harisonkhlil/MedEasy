package www.test.com.medical_system.bean;

/**
 * 预约记录
 */
public class AppointmentRecord {
    private int id;                     //主键
    private int patient_id;             //病人id
    private boolean is_self;            //是否自己（1是0否）
    private int real_patient_id;        //实际看病者的id
    private String real_patient_name;      //实际看病者的姓名
    private String date;                //预约日期
    private int department;             //预约科室
    private String department_text;     //预约科室名称
    private int doctor_id;              //预约医生id
    private String doctor_name;            //预约医生姓名
    private int status;                 //是否已看病(1是0否）
    private String status_text;            //是否已看病
    private int time_enum;              //预约开始时间
    private String time_text;              //预约开始时间

    @Override
    public String toString() {
        return department_text+"/"+doctor_name+"("+date.substring(0,10)+"  "+time_text+")  -  "+real_patient_name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPatient_id() {
        return patient_id;
    }

    public void setPatient_id(int patient_id) {
        this.patient_id = patient_id;
    }

    public boolean isIs_self() {
        return is_self;
    }

    public void setIs_self(boolean is_self) {
        this.is_self = is_self;
    }

    public int getReal_patient_id() {
        return real_patient_id;
    }

    public void setReal_patient_id(int real_patient_id) {
        this.real_patient_id = real_patient_id;
    }

    public String getReal_patient_name() {
        return real_patient_name;
    }

    public void setReal_patient_name(String real_patient_name) {
        this.real_patient_name = real_patient_name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getDepartment() {
        return department;
    }

    public void setDepartment(int department) {
        this.department = department;
    }

    public String getDepartment_text() {
        return department_text;
    }

    public void setDepartment_text(String department_text) {
        this.department_text = department_text;
    }

    public int getDoctor_id() {
        return doctor_id;
    }

    public void setDoctor_id(int doctor_id) {
        this.doctor_id = doctor_id;
    }

    public String getDoctor_name() {
        return doctor_name;
    }

    public void setDoctor_name(String doctor_name) {
        this.doctor_name = doctor_name;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getStatus_text() {
        return status_text;
    }

    public void setStatus_text(String status_text) {
        this.status_text = status_text;
    }

    public int getTime_enum() {
        return time_enum;
    }

    public void setTime_enum(int time_enum) {
        this.time_enum = time_enum;
    }

    public String getTime_text() {
        return time_text;
    }

    public void setTime_text(String time_text) {
        this.time_text = time_text;
    }
}






























