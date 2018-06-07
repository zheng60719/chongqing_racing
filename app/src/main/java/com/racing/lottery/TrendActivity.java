package com.racing.lottery;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.lottery.R;
import com.racing.fragment.LuZhuOneFragment;
import com.racing.fragment.LuZhuThreeFragment;
import com.racing.fragment.LuZhuTwoFragment;


/**
 * Created by Administrator on 2017/09/24.
 * 路珠走势
 */
public class TrendActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView tv_header_title;
    public TextView tv_left;
    private RadioButton one;
    private RadioButton two;
    private RadioButton three;
    public static FragmentManager fm;
    private RadioGroup rgBottomBar;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_zoushi);
        InitView();
        tv_header_title.setText("路珠走势");
        initListener();
    }

    private void InitView() {
        rgBottomBar = (RadioGroup) findViewById(R.id.rgBottomBar);
        tv_header_title = (TextView) findViewById(R.id.tv_header_title);
        tv_left = (TextView) findViewById(R.id.tv_left);
        tv_left.setOnClickListener(this);
        one = (RadioButton) findViewById(R.id.one);
        two = (RadioButton) findViewById(R.id.two);
        three = (RadioButton) findViewById(R.id.three);
        fm = getSupportFragmentManager();
        initFragment(new LuZhuOneFragment());
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_left:
                TrendActivity.this.finish();
                break;
        }
    }


    // 初始化Fragment(FragmentActivity中呼叫)
    public static void initFragment(Fragment f) {
        changeFragment(f, true);
    }

    private static void changeFragment(Fragment f, boolean init) {
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.frame, f);
        if (!init)
            ft.addToBackStack(null);
        ft.commit();
    }
    public void initListener() {
        one.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeFragment(new LuZhuOneFragment());
            }
        });
        two.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeFragment(new LuZhuTwoFragment());
            }
        });
        three.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeFragment(new LuZhuThreeFragment());
            }
        });
    }
    // 切換Fragment
    public static void changeFragment(Fragment f) {
        changeFragment(f, false);
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN){
                TrendActivity.this.finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
