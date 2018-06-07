package com.racing.entity;


import com.racing.root.BaseEntity;

import java.util.List;

/**
 * Created by k41 on 2017/9/27.
 */

public class ZhuanJiaFenxi extends BaseEntity {
    private String nicheng;
    private String tx;
    private String mobile;
    private String xingming;
    private String beizhu;
    private String jibie;
    private String zaishou_yuce_num;
    private String all_yuce_num;
    private String yuce_ok_num;
    private String fensi_num;
    private List<Level> list_level;
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNicheng() {
        return nicheng;
    }

    public void setNicheng(String nicheng) {
        this.nicheng = nicheng;
    }

    public String getTx() {
        return tx;
    }

    public void setTx(String tx) {
        this.tx = tx;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getXingming() {
        return xingming;
    }

    public void setXingming(String xingming) {
        this.xingming = xingming;
    }

    public String getBeizhu() {
        return beizhu;
    }

    public void setBeizhu(String beizhu) {
        this.beizhu = beizhu;
    }

    public String getJibie() {
        return jibie;
    }

    public void setJibie(String jibie) {
        this.jibie = jibie;
    }

    public String getZaishou_yuce_num() {
        return zaishou_yuce_num;
    }

    public void setZaishou_yuce_num(String zaishou_yuce_num) {
        this.zaishou_yuce_num = zaishou_yuce_num;
    }

    public String getAll_yuce_num() {
        return all_yuce_num;
    }

    public void setAll_yuce_num(String all_yuce_num) {
        this.all_yuce_num = all_yuce_num;
    }

    public String getYuce_ok_num() {
        return yuce_ok_num;
    }

    public void setYuce_ok_num(String yuce_ok_num) {
        this.yuce_ok_num = yuce_ok_num;
    }

    public String getFensi_num() {
        return fensi_num;
    }

    public void setFensi_num(String fensi_num) {
        this.fensi_num = fensi_num;
    }

    public List<Level> getList_level() {
        return list_level;
    }

    public void setList_level(List<Level> list_level) {
        this.list_level = list_level;
    }
}
