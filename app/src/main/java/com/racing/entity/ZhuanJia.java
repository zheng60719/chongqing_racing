package com.racing.entity;

import com.racing.root.BaseEntity;

import java.util.List;

/**
 * Created by k41 on 2017/12/21.
 */

public class ZhuanJia extends BaseEntity {
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    private int id;
    private String nicheng;
    private String tx;
    private List<Level> list_level;

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

    public List<Level> getList_level() {
        return list_level;
    }

    public void setList_level(List<Level> list_level) {
        this.list_level = list_level;
    }
}
