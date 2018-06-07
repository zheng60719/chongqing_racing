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
import com.racing.app.AppSp;
import com.racing.entity.UserBean;
import com.racing.view.LoadingDialog;
import com.racing.view.MyToast;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONObject;


/**
 * Created by k41 on 2017/10/14.
 */

public class UpdatePwdActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView tv_header_title;
    public TextView tv_left;
    public EditText et_pwd1,et_pwd2;
    public Button btn_tijiao;
    private LoadingDialog loadingDialog;
    private String pwd1,pwd2;
    private String ing;
    private String string_msg;
    private UserBean userBean;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.update_pwd);
        InitView();
        tv_header_title.setText("修改密码");
        AppSp appSp = new AppSp();
        userBean = (UserBean) appSp.getObjectFromShare(UpdatePwdActivity.this,"user");
    }

    private void InitView() {
        tv_header_title = (TextView) findViewById(R.id.tv_header_title);
        tv_left = (TextView) findViewById(R.id.tv_left);
        tv_left.setOnClickListener(this);
        btn_tijiao = (Button) findViewById(R.id.btn_tijiao);
        btn_tijiao.setOnClickListener(this);
        et_pwd1 = (EditText) findViewById(R.id.et_pwd1);
        et_pwd2 = (EditText) findViewById(R.id.et_pwd2);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_left:
                UpdatePwdActivity.this.finish();
                break;
            case R.id.btn_tijiao:
                //提交
                pwd1 = et_pwd1.getText().toString().trim();
                pwd2 = et_pwd2.getText().toString().trim();
                if (pwd1!=null&&pwd1.length()>0){
                    if (pwd2!=null&&pwd2.length()>0){
                        if (loadingDialog == null) {
                            loadingDialog = new LoadingDialog(UpdatePwdActivity.this, R.style.LoadingDialog);
                        }
                        loadingDialog.show();

                        UpdatePwd(userBean.getId()+"",pwd1,pwd2);
                    }else{
                       MyToast.getToast(UpdatePwdActivity.this,"旧密码不能为空");
                    }
                }else{
                    MyToast.getToast(UpdatePwdActivity.this,"新密码不能为空");
                }
                break;
        }
    }

    public Handler myHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 300:
                    if (loadingDialog != null) {
                        loadingDialog.cancel();
                    }
                    if (ing.equals("1")) {
                        Toast.makeText(UpdatePwdActivity.this,"修改密码成功",Toast.LENGTH_LONG).show();
                        finish();
                    }else if (ing.equals("0")){
                        Toast.makeText(UpdatePwdActivity.this,string_msg,Toast.LENGTH_LONG).show();
                    }
                    break;
                case 100:
                    Toast.makeText(UpdatePwdActivity.this,"请输入旧密码",Toast.LENGTH_LONG).show();
                    break;
                case 500:
                    Toast.makeText(UpdatePwdActivity.this,"请输入新密码",Toast.LENGTH_LONG).show();
                    break;
            }
        }
    };

    //修改密码
    public void UpdatePwd(String userid,String pwd,String pwd3) {
        //创建网络处理的对象
        OkHttpUtils
                .post()
                .url("http://www.zse6.com/index.php/mobile/ajax/gaimima")
                .addParams("userid",userid)
                .addParams("mima1", pwd)
                .addParams("mima2", pwd3)
                .build().execute(new StringCallback() {
            @Override
            public void onError(okhttp3.Call call, Exception e, int id) {
                MyToast.getToast(UpdatePwdActivity.this,"请求失败");
            }

            @Override
            public void onResponse(String response, int id) {
                try {
                    //数据解析
                    JSONObject jsonObj = new JSONObject(response);
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
}
