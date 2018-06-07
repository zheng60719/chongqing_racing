package com.racing.entity;


import com.racing.root.BaseEntity;

/**
 * Created by k41 on 2017/9/29.
 */

public class Pk10 extends BaseEntity {
    private int id;
    private String title;
    private String info;

    public String getIs_tuijian() {
        return is_tuijian;
    }

    public void setIs_tuijian(String is_tuijian) {
        this.is_tuijian = is_tuijian;
    }

    public Long getShijian() {
        return shijian;
    }

    public void setShijian(Long shijian) {
        this.shijian = shijian;
    }

    public int getCatid() {
        return catid;
    }

    public void setCatid(int catid) {
        this.catid = catid;
    }

    public int getIs_show() {
        return is_show;
    }

    public void setIs_show(int is_show) {
        this.is_show = is_show;
    }

    public String getMan() {
        return man;
    }

    public void setMan(String man) {
        this.man = man;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getGuanjianzi() {
        return guanjianzi;
    }

    public void setGuanjianzi(String guanjianzi) {
        this.guanjianzi = guanjianzi;
    }

    public String getMiaoshu() {
        return miaoshu;
    }

    public void setMiaoshu(String miaoshu) {
        this.miaoshu = miaoshu;
    }

    private String is_tuijian;
    private Long shijian;
    private int catid;
    private int is_show;
    private String man;
    private String img;
    private String guanjianzi;
    private String miaoshu;
    private String num;
    private String ip;
    private String xitong;

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getXitong() {
        return xitong;
    }

    public void setXitong(String xitong) {
        this.xitong = xitong;
    }

    public String getLiulanqi() {
        return liulanqi;
    }

    public void setLiulanqi(String liulanqi) {
        this.liulanqi = liulanqi;
    }

    public String getGuanlian() {
        return guanlian;
    }

    public void setGuanlian(String guanlian) {
        this.guanlian = guanlian;
    }

    public String getIng() {
        return ing;
    }

    public void setIng(String ing) {
        this.ing = ing;
    }

    public String getWxid() {
        return wxid;
    }

    public void setWxid(String wxid) {
        this.wxid = wxid;
    }

    private String liulanqi;
    private String guanlian;
    private String ing;
    private String wxid;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}
