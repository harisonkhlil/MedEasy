package com.maven.common.bean;

/**
 * 把enum里面的value和text都放到这个Bean里面
 */
public class ValueTextIntBean {
    private int value;
    private String text;

    public ValueTextIntBean(int value, String text) {
        this.value = value;
        this.text = text;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
