package com.racing.lottery;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.lottery.R;
import com.racing.view.MyToast;

/**
 * Created by k41 on 2017/10/14.
 */

public class FindPwdActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView tv_header_title;
    public TextView tv_left;
    private TextView btn_xiayibu;
    private EditText et_phone;
    private String phone;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.find_pwd);
        InitView();
        tv_header_title.setText("忘记密码");
    }

    private void InitView() {
        tv_header_title = (TextView) findViewById(R.id.tv_header_title);
        tv_left = (TextView) findViewById(R.id.tv_left);
        tv_left.setOnClickListener(this);
        btn_xiayibu = (TextView) findViewById(R.id.btn_xiayibu);
        btn_xiayibu.setOnClickListener(this);
        et_phone = (EditText) findViewById(R.id.et_phone);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_left:
                FindPwdActivity.this.finish();
                break;
            case R.id.btn_xiayibu:
                //下一步
                phone = et_phone.getText().toString().trim();
                if (phone.length()>0&&phone!=null){
                    Intent intent_find_pwd = new Intent();
                    Bundle bundle = new Bundle();
                    intent_find_pwd.setClass(FindPwdActivity.this,Find1PwdActivity.class);
                    bundle.putString("phone",phone);
                    intent_find_pwd.putExtras(bundle);
                    startActivity(intent_find_pwd);
                    finish();
                }else{
                    MyToast.getToast(FindPwdActivity.this,"请输入手机号");
                }
        }
    }
}
