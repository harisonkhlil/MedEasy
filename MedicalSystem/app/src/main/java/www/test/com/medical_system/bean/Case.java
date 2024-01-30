package www.test.com.medical_system.bean;

/**
 * 就诊记录
 */
public class Case {
    private int id;                         //主键
    private int patient_id;                 //病人id
    private boolean is_self;                //是否自己（1是0否）
    private int real_patient_id;            //实际看病者的id
    private String real_patient_name;       //实际看病者的姓名
    private int department;                 //就诊科室id
    private String department_text;         //就诊科室
    private int doctor_id;                  //就诊医生id
    private String doctor_name;             //就诊医生姓名
    private String date;                    //就诊时间
    private String content;                 //病例内容

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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
