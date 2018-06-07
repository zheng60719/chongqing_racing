package com.racing.entity;

import com.racing.root.BaseEntity;

/**
 * Created by k41 on 2017/10/11.
 */

public class HistoricalStatisticsItem extends BaseEntity {
    private String da;
    private String shuang;
    private String dan;
    private String xiao;
    private String hs_long;
    private String hs_hu;

    public String getDa() {
        return da;
    }

    public void setDa(String da) {
        this.da = da;
    }

    public String getShuang() {
        return shuang;
    }

    public void setShuang(String shuang) {
        this.shuang = shuang;
    }

    public String getDan() {
        return dan;
    }

    public void setDan(String dan) {
        this.dan = dan;
    }

    public String getXiao() {
        return xiao;
    }

    public void setXiao(String xiao) {
        this.xiao = xiao;
    }

    public String getHs_long() {
        return hs_long;
    }

    public void setHs_long(String hs_long) {
        this.hs_long = hs_long;
    }

    public String getHs_hu() {
        return hs_hu;
    }

    public void setHs_hu(String hs_hu) {
        this.hs_hu = hs_hu;
    }
}
