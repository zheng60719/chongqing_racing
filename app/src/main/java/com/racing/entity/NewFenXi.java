package com.racing.entity;

import com.racing.root.BaseEntity;

import java.util.List;

/**
 * Created by k41 on 2017/12/21.
 */

public class NewFenXi extends BaseEntity {
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    private int id;
    private String money;
    private String title;
    private Long shijian;
    private String qishu_last;
    private Integer type;
    private List<Level> list_level;
    private List<User> list_user;

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    public Long getShijian() {
        return shijian;
    }

    public void setShijian(Long shijian) {
        this.shijian = shijian;
    }

    public String getQishu_last() {
        return qishu_last;
    }

    public void setQishu_last(String qishu_last) {
        this.qishu_last = qishu_last;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public List<Level> getList_level() {
        return list_level;
    }

    public void setList_level(List<Level> list_level) {
        this.list_level = list_level;
    }

    public List<User> getList_user() {
        return list_user;
    }

    public void setList_user(List<User> list_user) {
        this.list_user = list_user;
    }
}
