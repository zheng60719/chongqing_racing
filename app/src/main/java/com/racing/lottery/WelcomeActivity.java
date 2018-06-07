package com.racing.lottery;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;

import com.lottery.R;
import com.racing.app.AppSp;
import com.racing.entity.UserBean;


/**
 * Created by Administrator on 2017/3/19.
 */
public class WelcomeActivity extends Activity{
    private UserBean userBean;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /**
         * 去掉标题
         */
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.welcome);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    Thread.sleep(1000);
                    Intent intent = new Intent();
                    AppSp appSp = new AppSp();
                    userBean = (UserBean) appSp.getObjectFromShare(WelcomeActivity.this,"user");
                    if (userBean==null) {
                        intent.setClass(WelcomeActivity.this, LoginActivity.class);
                    }else{
                        intent.setClass(WelcomeActivity.this, Account1Activity.class);
                    }
                    startActivity(intent);
                    WelcomeActivity.this.finish();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();

    }
}
