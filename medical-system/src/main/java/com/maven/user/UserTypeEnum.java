package com.maven.user;

/**
 * 用户类型
 */
public enum UserTypeEnum {
    ADMIN(0,"管理员"),
    DOCTOR(1,"医生"),
    PATIENT(2,"病人")
    ;

    private Integer value;      //数据库里存储的值
    private String text;        //显示内容

    UserTypeEnum(Integer value, String text) {
        this.value = value;
        this.text = text;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public static UserTypeEnum getByValue(Integer value){
        if(value == null){
            return null;
        }
        for(UserTypeEnum v:UserTypeEnum.values()){
            if(v.getValue().equals(value)){
                return v;
            }
        }
        return null;
    }
}
