package com.racing.lottery;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.lottery.R;
import com.racing.app.AppSp;
import com.racing.entity.UserBean;
import com.racing.view.MyToast;
import com.racing.widget.LoadingDialog;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by Administrator on 2017/09/24.
 * 开奖直播
 */
public class LoginActivity extends AppCompatActivity implements View.OnClickListener{
    private TextView tv_header_title;
    public TextView tv_left;
    public EditText et_phone,et_pwd;
    public Button btn_login;
    public TextView tv_reg,tv_pwd;
    private String ing;
    private String string_msg;
    private LoadingDialog loadingDialog;
    private String user;
    private Long exitTime = 1L;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.login);
        InitView();
        tv_header_title.setText("登录");
    }

    private void InitView() {
        tv_header_title = (TextView) findViewById(R.id.tv_header_title);
        tv_left = (TextView) findViewById(R.id.tv_left);
        tv_left.setOnClickListener(this);
        tv_left.setVisibility(View.GONE);
        et_phone = (EditText) findViewById(R.id.et_phone);
        et_pwd = (EditText) findViewById(R.id.et_pwd);
        btn_login = (Button) findViewById(R.id.btn_login);
        btn_login.setOnClickListener(this);
        tv_reg = (TextView) findViewById(R.id.tv_reg);
        tv_reg.setOnClickListener(this);
        tv_pwd = (TextView) findViewById(R.id.tv_pwd);
        tv_pwd.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_left:
                LoginActivity.this.finish();
                break;
            case R.id.btn_login:
                 //登录
                        String phone = et_phone.getText().toString().trim();
                        String pwd = et_pwd.getText().toString().trim();
                        if (et_phone!=null&&et_phone.length()>0){
                            if (et_pwd!=null&&et_pwd.length()>0){
                                if (loadingDialog == null) {
                                    loadingDialog = new LoadingDialog(LoginActivity.this, R.style.LoadingDialog);
                                }
                                loadingDialog.show();
                                Login(phone,pwd);
                            }else{
                                Message message = new Message();
                                message.what = 100;
                                myHandler.sendMessage(message);
                            }
                        }else{
                            Message message = new Message();
                            message.what = 500;
                            myHandler.sendMessage(message);
                        }
                break;
            case R.id.tv_reg:
                //注册
                Intent intent_reg = new Intent();
                intent_reg.setClass(LoginActivity.this,RegisterActivity.class);
                startActivity(intent_reg);
                break;
            case R.id.tv_pwd:
                //忘记密码
                Intent intent_find_pwd = new Intent();
                intent_find_pwd.setClass(LoginActivity.this,FindPwdActivity.class);
                startActivity(intent_find_pwd);
                break;

        }
    }
    //查询时间期数
    public void Login(String phone,String pwd) {
        //创建网络处理的对象
        OkHttpUtils
                .post()
                .url("http://www.zse6.com/index.php/mobile/ajax/logins")
                .addParams("zhanghu", phone)
                .addParams("mima", pwd)
                .build().execute(new StringCallback() {
            @Override
            public void onError(okhttp3.Call call, Exception e, int id) {
                MyToast.getToast(LoginActivity.this,"请求失败");
            }

            @Override
            public void onResponse(String response, int id) {
                try {
                    //数据解析
                    JSONObject jsonObj = new JSONObject(response);
                    ing = jsonObj.getString("ing");
                    string_msg = jsonObj.getString("msg");
                    user = jsonObj.getString("user");
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
                        try {
                            JSONObject jsonObject = new JSONObject(user);
                            UserBean userBean = new UserBean();
                            userBean.setId(jsonObject.getInt("id"));
                            userBean.setName(jsonObject.getString("name"));
                            userBean.setMobile(jsonObject.getString("mobile"));
                            userBean.setTx(jsonObject.getString("tx"));
                            AppSp appSp = new AppSp();
                            appSp.setObjectToShare(LoginActivity.this,userBean,"user");
                            Intent intent_find_pwd = new Intent();
                            intent_find_pwd.setClass(LoginActivity.this, Account1Activity.class);
                            startActivity(intent_find_pwd);
                            finish();
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }else if (ing.equals("0")){
                        Toast.makeText(LoginActivity.this,string_msg,Toast.LENGTH_LONG).show();
                    }
                    break;
                case 100:
                    Toast.makeText(LoginActivity.this,"请输入密码",Toast.LENGTH_LONG).show();
                    break;
                case 500:
                    Toast.makeText(LoginActivity.this,"请输入电话",Toast.LENGTH_LONG).show();
                    break;
            }
        }
    };

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN){
            if((System.currentTimeMillis()-exitTime) > 2000){
                MyToast.getToast(getApplicationContext(), "再按一次退出重庆时时彩").show();
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
