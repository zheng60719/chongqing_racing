package com.racing.app;

import android.app.Application;

import com.racing.entity.UserBean;

/**
 * Created by k41 on 2017/10/17.
 */

public class App extends Application {
    private static App instance;
    private UserBean userBean;
    public static synchronized App getInstance() {
        return instance;
    }
    @Override
    public void onCreate() {
        instance = this;
    }
    public void setUserInstance(UserBean userBean) {
        this.userBean = userBean;
    }
}
