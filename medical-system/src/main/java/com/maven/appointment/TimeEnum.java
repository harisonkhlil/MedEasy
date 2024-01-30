package com.maven.appointment;

import com.maven.common.bean.ValueTextIntBean;

import java.util.ArrayList;
import java.util.List;

/**
 * 医生的工作时间
 */
public enum TimeEnum {
    NINE(9,"09:00~10:00"),
    TEN(10,"10:00~11:00"),
    ELEVEN(11,"11:00~12:00"),
    FOURTEEN(14,"14:00~15:00"),
    FIFTEEN(15,"15:00~16:00"),
    SIXTEEN(16,"16:00~17:00"),
    SEVENTEEN(17,"17:00~18:00")
    ;

    private Integer value;      //数据库里存储的值
    private String text;        //显示内容

    TimeEnum(Integer value, String text) {
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

    public static TimeEnum getByValue(Integer value){
        if(value == null){
            return null;
        }
        for(TimeEnum v: TimeEnum.values()){
            if(v.getValue().equals(value)){
                return v;
            }
        }
        return null;
    }

    /*以list形式提供当前枚举的所有属性项*/
    public static List<ValueTextIntBean> getValues(){
        List<ValueTextIntBean> list = new ArrayList<>();
        for(TimeEnum timeEnum: TimeEnum.values()){
            list.add(new ValueTextIntBean(timeEnum.value,timeEnum.text));
        }
        return list;
    }
}





















