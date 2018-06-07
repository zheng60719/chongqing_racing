package com.racing.entity;

import com.racing.root.BaseEntity;

import java.util.List;
import java.util.Map;

/**
 * Created by k41 on 2017/10/11.
 */

public class HistoricalStatistics extends BaseEntity {
    public Map<String, HistoricalStatisticsItem> map;
    public String day;
    public String gyh_da;

    public Map<String, HistoricalStatisticsItem> getMap() {
        return map;
    }

    public void setMap(Map<String, HistoricalStatisticsItem> map) {
        this.map = map;
    }

    public String gyh_xiao;
    public String gyh_dan;
    public String gyh_shuang;


    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getGyh_da() {
        return gyh_da;
    }

    public void setGyh_da(String gyh_da) {
        this.gyh_da = gyh_da;
    }

    public String getGyh_xiao() {
        return gyh_xiao;
    }

    public void setGyh_xiao(String gyh_xiao) {
        this.gyh_xiao = gyh_xiao;
    }

    public String getGyh_dan() {
        return gyh_dan;
    }

    public void setGyh_dan(String gyh_dan) {
        this.gyh_dan = gyh_dan;
    }

    public String getGyh_shuang() {
        return gyh_shuang;
    }

    public void setGyh_shuang(String gyh_shuang) {
        this.gyh_shuang = gyh_shuang;
    }

}
