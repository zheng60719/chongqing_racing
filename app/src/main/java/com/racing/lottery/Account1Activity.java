package com.racing.lottery;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.lottery.R;
import com.racing.fragment.FenXiFragment;
import com.racing.fragment.JiHuaFragment;
import com.racing.fragment.MenFragment;
import com.racing.fragment.ZhuYeFragment;
import com.racing.view.MyToast;


/**
 * Created by Administrator on 2017/2/16.
 */
public class Account1Activity extends BaseActivity{
    private RadioGroup rgBottomBar;
    private RadioButton radioMain;
    private RadioButton radioMy;
    private RadioButton radioMore;
    private RadioButton radioMen;
    public static FragmentManager fm;
    private Long exitTime = 1L;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_communityt);
        initView();
        initListener();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }


    public void initView() {
        rgBottomBar = (RadioGroup) findViewById(R.id.rgBottomBar);
        radioMain = (RadioButton) findViewById(R.id.radioMain);
        radioMy = (RadioButton) findViewById(R.id.radioMy);
        radioMore = (RadioButton) findViewById(R.id.radioMore);
        radioMen = (RadioButton) findViewById(R.id.radioMen);
        fm = getSupportFragmentManager();
        initFragment(new ZhuYeFragment());
    }

    // 切換Fragment
    public static void changeFragment(Fragment f) {
        changeFragment(f, false);
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
        radioMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeFragment(new ZhuYeFragment());
            }
        });
        radioMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeFragment(new JiHuaFragment());
            }
        });
        radioMy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeFragment(new FenXiFragment());
            }
        });
        radioMen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeFragment(new MenFragment());
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN){
            if((System.currentTimeMillis()-exitTime) > 2000){
                MyToast.getToast(getApplicationContext(), "再按一次退出北京赛车").show();
                exitTime = System.currentTimeMillis();
            } else {
                finish();
                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
