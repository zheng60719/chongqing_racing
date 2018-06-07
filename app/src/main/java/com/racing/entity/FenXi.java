package com.racing.entity;


import com.racing.root.BaseEntity;

import java.util.List;

/**
 * Created by k41 on 2017/9/27.
 */

public class FenXi extends BaseEntity {
    private String id;
    private String type;
    private String qishu;
    private String yuceinfo;
    private String shijian;
    private String money;
    private String weizhi;
    private String qishu_name;
    private String uid;
    private String is_ok;
    private String numtype;
    private String title;
    private String ing;
    private String num1;
    private String num2;
    private String num3;
    private String num4;
    private String num5;
    private String qishu_last;
    private String is_auto;
    private List<User> list_user;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getQishu() {
        return qishu;
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

    public String getShijian() {
        return shijian;
    }

    public void setShijian(String shijian) {
        this.shijian = shijian;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getWeizhi() {
        return weizhi;
    }

    public void setWeizhi(String weizhi) {
        this.weizhi = weizhi;
    }

    public String getQishu_name() {
        return qishu_name;
    }

    public void setQishu_name(String qishu_name) {
        this.qishu_name = qishu_name;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getIs_ok() {
        return is_ok;
    }

    public void setIs_ok(String is_ok) {
        this.is_ok = is_ok;
    }

    public String getNumtype() {
        return numtype;
    }

    public void setNumtype(String numtype) {
        this.numtype = numtype;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIng() {
        return ing;
    }

    public void setIng(String ing) {
        this.ing = ing;
    }

    public String getNum1() {
        return num1;
    }

    public void setNum1(String num1) {
        this.num1 = num1;
    }

    public String getNum2() {
        return num2;
    }

    public void setNum2(String num2) {
        this.num2 = num2;
    }

    public String getNum3() {
        return num3;
    }

    public void setNum3(String num3) {
        this.num3 = num3;
    }

    public String getNum4() {
        return num4;
    }

    public void setNum4(String num4) {
        this.num4 = num4;
    }

    public String getNum5() {
        return num5;
    }

    public void setNum5(String num5) {
        this.num5 = num5;
    }

    public String getQishu_last() {
        return qishu_last;
    }

    public void setQishu_last(String qishu_last) {
        this.qishu_last = qishu_last;
    }

    public String getIs_auto() {
        return is_auto;
    }

    public void setIs_auto(String is_auto) {
        this.is_auto = is_auto;
    }

    public List<User> getList_user() {
        return list_user;
    }

    public void setList_user(List<User> list_user) {
        this.list_user = list_user;
    }

    public List<Level> getList_level() {
        return list_level;
    }

    public void setList_level(List<Level> list_level) {
        this.list_level = list_level;
    }

    private List<Level> list_level;
}
