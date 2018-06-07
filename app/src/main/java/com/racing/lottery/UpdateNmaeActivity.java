package com.racing.lottery;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.lottery.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.display.CircleBitmapDisplayer;
import com.racing.app.AppSp;
import com.racing.entity.UserBean;
import com.racing.view.LoadingDialog;
import com.racing.view.MyToast;
import com.racing.widget.ClearEditText;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONObject;

/**
 * Created by k41 on 2017/10/13.
 * 修改名字
 */

public class UpdateNmaeActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView tv_header_title;
    public TextView tv_left;
    private Button btn_update_name;
    private ClearEditText edit_name;
    private String ing;
    private String string_msg;
    private LoadingDialog loadingDialog;
    private ClearEditText edit_beizhu;
    private ClearEditText edit_nicheng;
    private UserBean userBean;
    private AppSp appSp;
    private String user;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.update_name);
        InitView();
        tv_header_title.setText("修改资料");
        appSp = new AppSp();
        userBean = (UserBean) appSp.getObjectFromShare(UpdateNmaeActivity.this,"user");
    }

    private void InitView() {
        tv_header_title = (TextView) findViewById(R.id.tv_header_title);
        tv_left = (TextView) findViewById(R.id.tv_left);
        tv_left.setOnClickListener(this);
        btn_update_name = (Button) findViewById(R.id.btn_update_name);
        btn_update_name.setOnClickListener(this);
        edit_name = (ClearEditText) findViewById(R.id.edit_name);
        edit_beizhu = (ClearEditText) findViewById(R.id.edit_beizhu);
        edit_nicheng = (ClearEditText) findViewById(R.id.edit_nicheng);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_left:
                UpdateNmaeActivity.this.finish();
                break;
            case R.id.btn_update_name:
                String name = edit_name.getText().toString().trim();
                String nicheng = edit_nicheng.getText().toString().trim();
                String beizhu = edit_beizhu.getText().toString().trim();
                if (loadingDialog == null) {
                    loadingDialog = new LoadingDialog(UpdateNmaeActivity.this, R.style.LoadingDialog);
                }
                loadingDialog.show();
                UpdateName(userBean.getId()+"",nicheng,name,beizhu);
                break;
        }
    }
    //修改头像
    public void UpdateName(String id,String nicheng,String xingming,String beizhu) {
        //创建网络处理的对象
        OkHttpUtils
                .post()
                .url("http://www.zse6.com/index.php/mobile/ajax/gai_tx")
                .addParams("id", id)
                .addParams("xingming",xingming)
                .addParams("nicheng",nicheng)
                .addParams("beizhu",beizhu)
                .build().execute(new StringCallback() {
            @Override
            public void onError(okhttp3.Call call, Exception e, int id) {
                MyToast.getToast(UpdateNmaeActivity.this,"请求失败");
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
                        Toast.makeText(UpdateNmaeActivity.this,"修改资料成功",Toast.LENGTH_LONG).show();
                        //获取会员资料
                        getUserDate(userBean.getId()+"");
                        finish();
                    }else if (ing.equals("0")){
                        Toast.makeText(UpdateNmaeActivity.this,string_msg,Toast.LENGTH_LONG).show();
                    }
                    break;
                case 400:
                    if (ing.equals("1")) {
                        try {
                            JSONObject jsonObject = new JSONObject(user);
                            UserBean userBean1 = new UserBean();
                            userBean1.setId(jsonObject.getInt("id"));
                            userBean1.setName(jsonObject.getString("name"));
                            userBean1.setMobile(jsonObject.getString("mobile"));
                            userBean1.setTx(jsonObject.getString("tx"));
                            userBean1.setPsd(jsonObject.getString("psd"));
                            userBean1.setShijian(jsonObject.getString("shijian"));
                            userBean1.setNicheng(jsonObject.getString("nicheng"));
                            userBean1.setMoney(jsonObject.getString("money"));
                            userBean1.setBeizhu(jsonObject.getString("beizhu"));
                            appSp.setObjectToShare(UpdateNmaeActivity.this,userBean1,"user");
                            DisplayImageOptions options = new DisplayImageOptions.Builder()
                                    .cacheInMemory(true)
                                    .cacheOnDisk(true)
                                    .displayer(new CircleBitmapDisplayer())
                                    .build();
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }else if (ing.equals("0")){
                        Toast.makeText(UpdateNmaeActivity.this,string_msg,Toast.LENGTH_LONG).show();
                    }
                    break;
            }
        }
    };

    public void getUserDate(String id) {
        //创建网络处理的对象
        OkHttpUtils
                .post()
                .url("http://www.zse6.com/index.php/mobile/ajax/user_info")
                .addParams("id", id)
                .build().execute(new StringCallback() {
            @Override
            public void onError(okhttp3.Call call, Exception e, int id) {
                MyToast.getToast(UpdateNmaeActivity.this, "请求失败");
            }

            @Override
            public void onResponse(String response, int id) {
                try {
                    //数据解析
                    JSONObject jsonObj = new JSONObject(response.toString());
                    ing = jsonObj.getString("ing");
                    string_msg = jsonObj.getString("msg");
                    user = jsonObj.getString("data");
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
