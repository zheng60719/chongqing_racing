package com.racing.entity;

import com.racing.root.BaseEntity;

/**
 * Created by k41 on 2017/12/22.
 */

public class SendNumType extends BaseEntity {
    private int numType;
    private String numTypeName;

    public int getNumType() {
        return numType;
    }

    public void setNumType(int numType) {
        this.numType = numType;
    }

    public String getNumTypeName() {
        return numTypeName;
    }

    public void setNumTypeName(String numTypeName) {
        this.numTypeName = numTypeName;
    }
}
