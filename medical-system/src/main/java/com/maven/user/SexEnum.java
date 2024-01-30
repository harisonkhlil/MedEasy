package com.maven.user;

import com.maven.common.bean.ValueTextIntBean;

import java.util.ArrayList;
import java.util.List;

/**
 * 性别
 */
public enum SexEnum {
    MALE(1,"男"),
    FEMALE(0,"女")
    ;

    private Integer value;      //数据库里存储的值
    private String text;        //显示内容

    SexEnum(Integer value, String text) {
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

    public static SexEnum getByValue(Integer value){
        if(value == null){
            return null;
        }
        for(SexEnum v: SexEnum.values()){
            if(v.getValue().equals(value)){
                return v;
            }
        }
        return null;
    }

    /*以list形式提供当前枚举的所有属性项*/
    public static List<ValueTextIntBean> getValues(){
        List<ValueTextIntBean> list = new ArrayList<>();
        for(SexEnum sexEnum:SexEnum.values()){
            list.add(new ValueTextIntBean(sexEnum.value,sexEnum.text));
        }
        return list;
    }
}





















