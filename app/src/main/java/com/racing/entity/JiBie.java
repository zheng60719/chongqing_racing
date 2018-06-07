package com.racing.entity;

import com.racing.root.BaseEntity;

/**
 * Created by k41 on 2018/1/18.
 */

public class JiBie extends BaseEntity {
    private int id;
    private String name;
    private String logo;
    private String quanxian;

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

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getQuanxian() {
        return quanxian;
    }

    public void setQuanxian(String quanxian) {
        this.quanxian = quanxian;
    }

    public int getWxid() {
        return wxid;
    }

    public void setWxid(int wxid) {
        this.wxid = wxid;
    }

    private int wxid;
}
