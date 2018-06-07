package com.racing.lottery;

import android.os.Bundle;
import android.view.View;

import com.lottery.R;


public class MainActivity extends BaseActivity implements View.OnClickListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        InitView();
    }

    private void InitView() {
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            //会员绑定
        }
}

}
