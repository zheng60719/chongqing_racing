package com.racing.lottery;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.lottery.R;
import com.racing.view.MyToast;
import com.racing.widget.LoadingDialog;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONObject;

/**
 * Created by k41 on 2017/10/14.
 */

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView tv_header_title;
    public TextView tv_left;
    private EditText et_phone;
    private EditText et_yzm;
    private Button btn_yzm;
    private EditText et_pwd;
    private Button btn_register;
    private String phone,pwd,yzm;
    private String ing;
    private String string_msg;
    private LoadingDialog loadingDialog;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.register);
        InitView();
        tv_header_title.setText("注册");
    }

    private void InitView() {
        tv_header_title = (TextView) findViewById(R.id.tv_header_title);
        tv_left = (TextView) findViewById(R.id.tv_left);
        tv_left.setOnClickListener(this);
        et_phone = (EditText) findViewById(R.id.et_phone);
        et_yzm = (EditText) findViewById(R.id.et_yzm);
        btn_yzm = (Button) findViewById(R.id.btn_yzm);
        et_pwd = (EditText) findViewById(R.id.et_pwd);
        btn_register = (Button) findViewById(R.id.btn_register);
        btn_register.setOnClickListener(this);
        btn_yzm.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_left:
                RegisterActivity.this.finish();
                break;
            case R.id.btn_register:
                //注册
                phone = et_phone.getText().toString().trim();
                pwd = et_pwd.getText().toString().trim();
                yzm = et_yzm.getText().toString().trim();
                et_pwd = (EditText) findViewById(R.id.et_phone);
                et_yzm = (EditText) findViewById(R.id.et_yzm);
                if (phone.length()>0&&phone!=null){
                    if (yzm.length()>0&&yzm!=null){
                        if (pwd.length()>0&&pwd!=null){
                                    if (loadingDialog == null) {
                                        loadingDialog = new LoadingDialog(RegisterActivity.this, R.style.LoadingDialog);
                                    }
                                    loadingDialog.show();
                                    register(phone,yzm,pwd);
                        }else{
                            Message message = new Message();
                            message.what = 3000;
                            myHandler.sendMessage(message);
                        }
                    }else{
                        Message message = new Message();
                        message.what = 2000;
                        myHandler.sendMessage(message);
                    }
                }else{
                    Message message = new Message();
                    message.what = 1000;
                    myHandler.sendMessage(message);
                }
                break;
            case R.id.btn_yzm:
                //验证码
                phone = et_phone.getText().toString().trim();
                if (phone.length()>0&&phone!=null){
                            if (loadingDialog == null) {
                                loadingDialog = new LoadingDialog(RegisterActivity.this, R.style.LoadingDialog);
                            }
                            loadingDialog.show();
                            SendYzm(phone);
                }else{
                    Message message = new Message();
                    message.what = 1000;
                    myHandler.sendMessage(message);
                }
                break;
        }
    }

    //发送验证码
    public void SendYzm(String phone) {
        //创建网络处理的对象
        OkHttpUtils
                .post()
                .url("http://www.zse6.com/index.php/mobile/ajax/fayanzhengma")
                .addParams("mobile", phone)
                .build().execute(new StringCallback() {
            @Override
            public void onError(okhttp3.Call call, Exception e, int id) {
                MyToast.getToast(RegisterActivity.this,"请求失败");
            }

            @Override
            public void onResponse(String response, int id) {
                try {
                    //数据解析
                    JSONObject jsonObj = new JSONObject(response.toString());
                    ing = jsonObj.getString("ing");
                    string_msg = jsonObj.getString("msg");
                    Message message = new Message();
                    message.what = 300;
                    myHandler.sendMessage(message);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
    }

    //handler
    public Handler myHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 300:
                    if (loadingDialog != null) {
                        loadingDialog.cancel();
                    }
                    if (ing.equals("1")) {
                        Toast.makeText(RegisterActivity.this,"获取验证码成功",Toast.LENGTH_LONG).show();
                    }else if (ing.equals("0")){
                        Toast.makeText(RegisterActivity.this,string_msg,Toast.LENGTH_LONG).show();
                    }
                    break;
                case 400:
                    if (loadingDialog != null) {
                        loadingDialog.cancel();
                    }
                    if (ing.equals("1")) {
                        Toast.makeText(RegisterActivity.this,"注册成功",Toast.LENGTH_LONG).show();
                        finish();
                    }else if (ing.equals("0")){
                        Toast.makeText(RegisterActivity.this,string_msg,Toast.LENGTH_LONG).show();
                    }
                    break;
                case 1000:
                    Toast.makeText(RegisterActivity.this,"请输入手机号码",Toast.LENGTH_LONG).show();
                    break;
                case 2000:
                    Toast.makeText(RegisterActivity.this,"请输入验证码",Toast.LENGTH_LONG).show();
                    break;
                case 3000:
                    Toast.makeText(RegisterActivity.this,"请输入密码",Toast.LENGTH_LONG).show();
                    break;
            }
        }
    };

    //    //积分商品分类查询
    public void register(String phone,String yzm,String pwd) {
        OkHttpUtils
                .post()
                .url("http://www.zse6.com/index.php/mobile/ajax/zhuce")
                .addParams("zhanghu", phone)
                .addParams("mima1", pwd)
                .addParams("mima2", pwd)
                .addParams("yanzhengma", yzm)
                .build().execute(new StringCallback() {
            @Override
            public void onError(okhttp3.Call call, Exception e, int id) {
                MyToast.getToast(RegisterActivity.this, "请求失败");
            }

            @Override
            public void onResponse(String response, int id) {
                try {
                    JSONObject jsonObj = new JSONObject(response.toString());
                    ing = jsonObj.getString("ing");
                    string_msg = jsonObj.getString("msg");
                    Message message = new Message();
                    message.what = 400;
                    myHandler.sendMessage(message);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
