package com.racing.entity;


import com.racing.root.BaseEntity;

/**
 * Created by k41 on 2017/9/29.
 */

public class TodayNumberItem extends BaseEntity {
    private String zongkai;
    private String weikai;

    public String getZongkai() {
        return zongkai;
    }

    public void setZongkai(String zongkai) {
        this.zongkai = zongkai;
    }

    public String getWeikai() {
        return weikai;
    }

    public void setWeikai(String weikai) {
        this.weikai = weikai;
    }
}
