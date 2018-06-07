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
import com.racing.view.LoadingDialog;
import com.racing.view.MyToast;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONObject;


/**
 * Created by k41 on 2017/10/14.
 */

public class Find1PwdActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView tv_header_title;
    public TextView tv_left;
    private EditText et_phone;
    private String phone;
    private TextView tv_phone;
    private TextView btn_find_pwd;
    private Button btn_yzm;
    private String ing;
    private String string_msg;
    private String ph;
    private LoadingDialog loadingDialog;
    private EditText et_yzm;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.find_pwd1);
        InitView();
        tv_header_title.setText("找回密码");
        Bundle bundle = getIntent().getExtras();
        ph = bundle.getString("phone");
        tv_phone.setText(ph);
    }

    private void InitView() {
        tv_phone = (TextView) findViewById(R.id.tv_phone);
        tv_header_title = (TextView) findViewById(R.id.tv_header_title);
        tv_left = (TextView) findViewById(R.id.tv_left);
        tv_left.setOnClickListener(this);
        et_phone = (EditText) findViewById(R.id.et_phone);
        btn_find_pwd = (TextView) findViewById(R.id.btn_find_pwd);
        btn_find_pwd.setOnClickListener(this);
        btn_yzm = (Button) findViewById(R.id.btn_yzm);
        btn_yzm.setOnClickListener(this);
        et_yzm = (EditText) findViewById(R.id.et_yzm);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_left:
                Find1PwdActivity.this.finish();
                break;
            case R.id.btn_find_pwd:
                //找回密码
                String yzm = et_yzm.getText().toString().trim();
                if (yzm.length()>0&&yzm!=null){
                    if (loadingDialog == null) {
                        loadingDialog = new LoadingDialog(Find1PwdActivity.this, R.style.LoadingDialog);
                    }
                    loadingDialog.show();
                    Find_Pwd(ph,yzm);
                }else{
                    MyToast.getToast(Find1PwdActivity.this,"请输入验证码");
                }
                break;
            case R.id.btn_yzm:
                //发送验证码
                if (loadingDialog == null) {
                    loadingDialog = new LoadingDialog(Find1PwdActivity.this, R.style.LoadingDialog);
                }
                loadingDialog.show();
                SendYzm(ph);
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
                MyToast.getToast(Find1PwdActivity.this,"请求失败");
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
                        Toast.makeText(Find1PwdActivity.this,"获取验证码成功",Toast.LENGTH_LONG).show();
                    }else if (ing.equals("0")){
                        Toast.makeText(Find1PwdActivity.this,string_msg,Toast.LENGTH_LONG).show();
                    }
                    break;
                case 400:
                    if (loadingDialog != null) {
                        loadingDialog.cancel();
                    }
                    if (ing.equals("1")) {
                        Toast.makeText(Find1PwdActivity.this,"新密码已发送到您的手机上，请注意查收",Toast.LENGTH_LONG).show();
                        finish();
                    }else if (ing.equals("0")){
                        Toast.makeText(Find1PwdActivity.this,string_msg,Toast.LENGTH_LONG).show();
                    }
                    break;
            }
        }
    };
    //发送验证码
    public void Find_Pwd(String mobile,String code) {
        //创建网络处理的对象
        OkHttpUtils
                .post()
                .url("http://www.zse6.com/index.php/mobile/ajax/findmima")
                .addParams("mobile", mobile)
                .addParams("code",code)
                .build().execute(new StringCallback() {
            @Override
            public void onError(okhttp3.Call call, Exception e, int id) {
                MyToast.getToast(Find1PwdActivity.this,"请求失败");
            }

            @Override
            public void onResponse(String response, int id) {
                try {
                    //数据解析
                    JSONObject jsonObj = new JSONObject(response.toString());
                    ing = jsonObj.getString("ing");
                    string_msg = jsonObj.getString("msg");
                    Message message = new Message();
                    message.what = 400;
                    myHandler.sendMessage(message);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
    }
}
