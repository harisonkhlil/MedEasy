package com.maven.user;

import com.maven.common.bean.ValueTextIntBean;

import java.util.ArrayList;
import java.util.List;

/**
 * 科室
 */
public enum DepartmentEnum {
    INTERNAL_MEDICINE(1, "内科"),
    SURGERY(2, "外科"),
    PEDIATRICS(3,"儿科"),
    DERMATOLOGICAL(4, "皮肤科"),
    TCM_WITH_WESTERN_MEDICINE(5, "中西医结合科"),
    OPHTHALMOLOGY(6, "眼科"),
    ENT(7, "耳鼻喉科"),
    DENTAL(8, "口腔科"),
    EMERGENCY(9, "急诊科"),
    CLINICAL_NUTRITION(10, "临床营养科")
    ;

    private Integer value;      //数据库里存储的值
    private String text;        //显示内容

    DepartmentEnum(Integer value, String text) {
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

    public static DepartmentEnum getByValue(Integer value){
        if(value == null){
            return null;
        }
        for(DepartmentEnum v: DepartmentEnum.values()){
            if(v.getValue().equals(value)){
                return v;
            }
        }
        return null;
    }

    /*以list形式提供当前枚举的所有属性项*/
    public static List<ValueTextIntBean> getValues(){
        List<ValueTextIntBean> list = new ArrayList<>();
        for(DepartmentEnum d: DepartmentEnum.values()){
            list.add(new ValueTextIntBean(d.value,d.text));
        }
        return list;
    }
}





















