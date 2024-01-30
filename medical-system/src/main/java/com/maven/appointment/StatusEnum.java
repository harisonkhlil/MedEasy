package com.maven.appointment;

import com.maven.common.bean.ValueTextIntBean;

import java.util.ArrayList;
import java.util.List;

/**
 * 预约状态
 */
public enum StatusEnum {
    WAITING(0,"已预约/待问诊"),
    COMPLETE(1,"已就诊")
    ;

    private Integer value;      //数据库里存储的值
    private String text;        //显示内容

    StatusEnum(Integer value, String text) {
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

    public static StatusEnum getByValue(Integer value){
        if(value == null){
            return null;
        }
        for(StatusEnum v: StatusEnum.values()){
            if(v.getValue().equals(value)){
                return v;
            }
        }
        return null;
    }

    /*以list形式提供当前枚举的所有属性项*/
    public static List<ValueTextIntBean> getValues(){
        List<ValueTextIntBean> list = new ArrayList<>();
        for(StatusEnum statusEnum: StatusEnum.values()){
            list.add(new ValueTextIntBean(statusEnum.value,statusEnum.text));
        }
        return list;
    }
}





















