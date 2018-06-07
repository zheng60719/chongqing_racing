package com.racing.lottery;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lottery.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.display.CircleBitmapDisplayer;
import com.racing.app.AppSp;
import com.racing.entity.UserBean;
import com.racing.view.MyToast;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONObject;


/**
 * Created by k41 on 2017/10/13.
 */

public class MyDataActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView tv_header_title;
    public TextView tv_left;
    private TextView tv_name,tv_beizhu;
    private ImageView img_tx;
    private UserBean userBean;
    private AppSp appSp;
    private RelativeLayout update_tx,update_name,update_beizhu;
    private Button btn_tx,btn_ziliao;
    private String ing;
    private String string_msg;
    private String user;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.my_data);
        InitView();
        tv_header_title.setText("我的资料");
        data();
    }
    @Override
    protected void onResume() {
        super.onResume();
        data();
    }
    private void InitView() {
        tv_header_title = (TextView) findViewById(R.id.tv_header_title);
        tv_left = (TextView) findViewById(R.id.tv_left);
        tv_left.setOnClickListener(this);
        img_tx = (ImageView) findViewById(R.id.img_tx);
        tv_name = (TextView) findViewById(R.id.tv_name);
        tv_beizhu = (TextView) findViewById(R.id.tv_beizhu);
        update_tx = (RelativeLayout) findViewById(R.id.update_tx);
        update_name = (RelativeLayout) findViewById(R.id.update_name);
        update_beizhu = (RelativeLayout) findViewById(R.id.update_beizhu);
        btn_tx = (Button) findViewById(R.id.btn_tx);
        btn_tx.setOnClickListener(this);
        btn_ziliao = (Button) findViewById(R.id.btn_ziliao);
        btn_ziliao.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_left:
                MyDataActivity.this.finish();
                break;
            case R.id.update_beizhu:
                Intent intent_update_beizhu = new Intent();
                intent_update_beizhu.setClass(MyDataActivity.this,UpdateBeiZhuActivity.class);
                startActivity(intent_update_beizhu);
                //修改备注
                break;
            case R.id.update_name:
                //修改名字
                Intent intent_update_name = new Intent();
                intent_update_name.setClass(MyDataActivity.this,UpdateNmaeActivity.class);
                startActivity(intent_update_name);
                break;
            case R.id.update_tx:
                //修改头像
                Intent intent_update_tx = new Intent();
                intent_update_tx.setClass(MyDataActivity.this,UpdateTxActivity.class);
                startActivity(intent_update_tx);
                break;
            case R.id.btn_tx:
                Intent intent_update_tx1 = new Intent();
                intent_update_tx1.setClass(MyDataActivity.this,UpdateTxActivity.class);
                startActivity(intent_update_tx1);
                break;
            case R.id.btn_ziliao:
                Intent intent_update_ziliao = new Intent();
                intent_update_ziliao.setClass(MyDataActivity.this,UpdateNmaeActivity.class);
                startActivity(intent_update_ziliao);
                break;

        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        //获取会员资料
        getUserDate(userBean.getId()+"");
    }
    public void getUserDate(String id) {
        //创建网络处理的对象
        OkHttpUtils
                .post()
                .url("http://www.zse6.com/index.php/mobile/ajax/user_info")
                .addParams("id", id)
                .build().execute(new StringCallback() {
            @Override
            public void onError(okhttp3.Call call, Exception e, int id) {
                MyToast.getToast(MyDataActivity.this,"请求失败");
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
                    message.what = 300;
                    myHandler.sendMessage(message);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
    }
    public void data(){
        appSp = new AppSp();
        userBean = (UserBean) appSp.getObjectFromShare(MyDataActivity.this,"user");
        tv_name.setText(userBean.getNicheng());
        tv_beizhu.setText(userBean.getBeizhu());
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .displayer(new CircleBitmapDisplayer())
                .build();
        ImageLoader imageLoader = ImageLoader.getInstance();
        imageLoader.init(ImageLoaderConfiguration.createDefault(MyDataActivity.this));
        if (userBean.getTx()!=null&&userBean.getTx().length()>0) {
            ImageLoader.getInstance().displayImage("http://www.zse6.com"+userBean.getTx(), img_tx, options);
        }else {
            String imageUri = "assets://img_user_tx.png";
            ImageLoader.getInstance().displayImage(imageUri, img_tx, options);
        }
    }
    //handler
    public Handler myHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 300:
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
                            appSp.setObjectToShare(MyDataActivity.this,userBean1,"user");
                            tv_name.setText(jsonObject.getString("nicheng"));
                            tv_beizhu.setText(jsonObject.getString("beizhu"));
                            DisplayImageOptions options = new DisplayImageOptions.Builder()
                                    .cacheInMemory(true)
                                    .cacheOnDisk(true)
                                    .displayer(new CircleBitmapDisplayer())
                                    .build();
                            ImageLoader imageLoader = ImageLoader.getInstance();
                            imageLoader.init(ImageLoaderConfiguration.createDefault(MyDataActivity.this));
                            if (jsonObject.getString("tx")!=null&&jsonObject.getString("tx").length()>0) {
                                ImageLoader.getInstance().displayImage("http://www.zse6.com"+jsonObject.getString("tx"), img_tx, options);
                            }else{
                                String imageUri = "assets://img_user_tx.png";
                                ImageLoader.getInstance().displayImage(imageUri, img_tx, options);
                            }
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }else if (ing.equals("0")){
                        Toast.makeText(MyDataActivity.this,string_msg,Toast.LENGTH_LONG).show();
                    }
                    break;
            }
        }
    };



}
