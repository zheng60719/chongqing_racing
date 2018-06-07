package com.racing.entity;

import com.racing.root.BaseEntity;

/**
 * Created by k41 on 2017/12/24.
 */

public class MyForecast extends BaseEntity {
    private int id;
    private int type;
    private String qishu;
    private String yuceinfo;
    private int is_ok;
    private String title;
    private Long shijian;

    public Long getShijian() {
        return shijian;
    }

    public void setShijian(Long shijian) {
        this.shijian = shijian;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getIs_ok() {
        return is_ok;
    }

    public void setIs_ok(int is_ok) {
        this.is_ok = is_ok;
    }


    public String getQishu() {
        return qishu;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public void setQishu(String qishu) {
        this.qishu = qishu;

    }

    public String getYuceinfo() {
        return yuceinfo;
    }

    public void setYuceinfo(String yuceinfo) {
        this.yuceinfo = yuceinfo;
    }


    public int getWeizhi() {
        return weizhi;
    }

    public void setWeizhi(int weizhi) {
        this.weizhi = weizhi;
    }

    private int weizhi;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
