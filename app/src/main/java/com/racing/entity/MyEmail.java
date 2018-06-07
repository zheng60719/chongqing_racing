package com.racing.entity;

import com.racing.root.BaseEntity;

/**
 * Created by k41 on 2018/1/2.
 */

public class MyEmail extends BaseEntity {
    private int id;
    private String type;
    private String is_sms;
    private String send_time;
    private String info;
    private String kanlist;
    private String reman;
    private String title;
    private String shijian;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getIs_sms() {
        return is_sms;
    }

    public void setIs_sms(String is_sms) {
        this.is_sms = is_sms;
    }

    public String getSend_time() {
        return send_time;
    }

    public void setSend_time(String send_time) {
        this.send_time = send_time;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getKanlist() {
        return kanlist;
    }

    public void setKanlist(String kanlist) {
        this.kanlist = kanlist;
    }

    public String getReman() {
        return reman;
    }

    public void setReman(String reman) {
        this.reman = reman;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getShijian() {
        return shijian;
    }

    public void setShijian(String shijian) {
        this.shijian = shijian;
    }
}
