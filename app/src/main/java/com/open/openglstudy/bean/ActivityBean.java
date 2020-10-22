package com.open.openglstudy.bean;

/**
 * Created by liuml on 2020/10/22 15:46
 */
public class ActivityBean {
    private int index;
    private String describe;
    private Class clazz;


    public ActivityBean(int index, String describe, Class clazz) {
        this.index = index;
        this.clazz = clazz;
        this.describe = describe;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public Class getClazz() {
        return clazz;
    }

    public void setClazz(Class clazz) {
        this.clazz = clazz;
    }
}
