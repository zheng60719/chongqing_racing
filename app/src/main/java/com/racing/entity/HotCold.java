package com.racing.entity;

import com.racing.root.BaseEntity;

import java.util.List;
import java.util.Map;

/**
 * Created by k41 on 2017/10/11.
 */

public class HotCold extends BaseEntity {
      private Map<String,List<HotColdItem>> list_re;
      private Map<String,List<HotColdItem>> list_wen;
      private Map<String,List<HotColdItem>> list_leng;

    public Map<String, List<HotColdItem>> getList_re() {
        return list_re;
    }

    public void setList_re(Map<String, List<HotColdItem>> list_re) {
        this.list_re = list_re;
    }

    public Map<String, List<HotColdItem>> getList_wen() {
        return list_wen;
    }

    public void setList_wen(Map<String, List<HotColdItem>> list_wen) {
        this.list_wen = list_wen;
    }

    public Map<String, List<HotColdItem>> getList_leng() {
        return list_leng;
    }

    public void setList_leng(Map<String, List<HotColdItem>> list_leng) {
        this.list_leng = list_leng;
    }
}
