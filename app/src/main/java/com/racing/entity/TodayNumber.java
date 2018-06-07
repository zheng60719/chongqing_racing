package com.racing.entity;


import com.racing.root.BaseEntity;

import java.util.List;
import java.util.Map;

/**
 * Created by k41 on 2017/9/29.
 */

public class TodayNumber extends BaseEntity {
    String ing;
    String msg;
    Map<String, TodayNumberItem> data;

    public String getIng() {
        return ing;
    }

    public void setIng(String ing) {
        this.ing = ing;
    }

    public Map<String, TodayNumberItem> getData() {
        return data;
    }

    public void setData(Map<String, TodayNumberItem> data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

}
