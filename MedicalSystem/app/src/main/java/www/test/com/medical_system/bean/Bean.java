package www.test.com.medical_system.bean;

/**
 * 大杂烩bean
 */
public class Bean {
    /*id*/
    private int id;
    /*名字*/
    private String name;
    /*身份证*/
    private String id_num;
    /*手机*/
    private String phone;
    /*性别*/
    private int sex;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId_num() {
        return id_num;
    }

    public void setId_num(String id_num) {
        this.id_num = id_num;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    @Override
    public String toString() {
        return name;
    }
}
