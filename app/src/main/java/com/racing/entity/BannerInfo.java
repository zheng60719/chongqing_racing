package com.racing.entity;

import com.racing.root.BaseEntity;

/**
 * Created by k41 on 2017/12/25.
 */

public class BannerInfo extends BaseEntity {
    private int id;
    private int bid;
    private String img;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getBid() {
        return bid;
    }

    public void setBid(int bid) {
        this.bid = bid;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
}
