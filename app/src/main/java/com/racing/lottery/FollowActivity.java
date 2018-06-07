package com.racing.lottery;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lottery.R;
import com.racing.Adapter.FollowAdapter;
import com.racing.app.AppSp;
import com.racing.entity.FenXi;
import com.racing.entity.Level;
import com.racing.entity.User;
import com.racing.entity.UserBean;
import com.racing.view.LoadingDialog;
import com.racing.view.MyToast;
import com.racing.widget.LoadMoreRecyclerView;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by k41 on 2017/12/21.
 */

public class FollowActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView tv_header_title;
    public TextView tv_left;
    //hotcat
    private LoadingDialog loadingDialog;
    private LoadMoreRecyclerView recycler_follow;
    private FollowAdapter adapter;
    private List<FenXi> list_fenxi;
    //UserBean
    private UserBean userBean;
    private AppSp appSp;
    private RelativeLayout rl_no_date;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.follow);
        InitView();
        tv_header_title.setText("我的关注");
        if (loadingDialog == null) {
            loadingDialog = new LoadingDialog(FollowActivity.this, R.style.LoadingDialog);
        }
        loadingDialog.show();
        appSp = new AppSp();
        userBean = (UserBean) appSp.getObjectFromShare(FollowActivity.this, "user");
        //获取数据
        getData(userBean.getId(),"cqssc");
    }

    private void getData(int uid,String model) {
        OkHttpUtils
                .post()
                .url("http://www.zse6.com/index.php/mobile/ajax/my_guanzhu")
                .addParams("uid",uid+"")
                .addParams("model",model)
                .build().execute(new StringCallback() {
            @Override
            public void onError(okhttp3.Call call, Exception e, int id) {
                MyToast.getToast(FollowActivity.this, "请求失败");
            }

            @Override
            public void onResponse(String response, int id) {
                try {
                    list_fenxi = new ArrayList<>();
                    JSONObject json_start = new JSONObject(response);
                    int ing = json_start.getInt("ing");
                    if (ing==1) {
                        JSONArray jsonObj = new JSONObject(response).getJSONArray("data");

                        for (int i = 0; i < jsonObj.length(); i++) {
                            JSONObject o = (JSONObject) jsonObj.get(i);
                            FenXi fenxi = new FenXi();
                            String user = o.getString("user");
                            JSONObject json_level = new JSONObject(user);
                            String level_str = json_level.getString("level");
                            JSONObject json_user = new JSONObject(user);

                            String beizhu = json_user.getString("beizhu");
                            String nicheng = json_user.getString("nicheng");
                            List<User> list_user = new ArrayList<>();
                            List<Level> list_level = new ArrayList<>();
                            String tx = json_user.getString("tx");
                            String fensi = json_user.getString("fensi_num");
                            String zaishou = json_user.getString("zaishou_yuce_num");
                            String yuce_ok = json_user.getString("yuce_ok_num");
                            Level level = new Level();
                            JSONObject jsonObject = new JSONObject(level_str);
                            String logo = jsonObject.getString("logo");
                            level.setLogo(logo);
                            list_level.add(level);
                            User user1 = new User();
                            user1.setTx(tx);
                            user1.setFensi_num(fensi);
                            user1.setZaishou_yuce_num(zaishou);
                            user1.setYuce_ok_num(yuce_ok);
                            user1.setBeizhu(beizhu);
                            user1.setNicheng(nicheng);
                            list_user.add(user1);
                            fenxi.setList_user(list_user);
                            fenxi.setList_level(list_level);
                            list_fenxi.add(fenxi);
                        }
                        Message message = new Message();
                        message.what = 200;
                        myHandler.sendMessage(message);
                    }else{
                        Message message = new Message();
                        message.what = 100;
                        myHandler.sendMessage(message);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
    public Handler myHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 200:
                    if(list_fenxi.size()>0) {
                        rl_no_date.setVisibility(View.GONE);
                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(FollowActivity.this);
                        recycler_follow.setLayoutManager(linearLayoutManager);
                        recycler_follow.setHasFixedSize(true);
                        recycler_follow.setAutoLoadMoreEnable(false);
                        adapter = new FollowAdapter(FollowActivity.this, list_fenxi, R.layout.fenxi_item);
                        recycler_follow.setAdapter(adapter);
                    }else{
                        rl_no_date.setVisibility(View.VISIBLE);
                    }
                    if (loadingDialog != null) {
                        loadingDialog.cancel();
                    }
                    break;
                case 100:
                    Toast.makeText(FollowActivity.this,"您还没有关注",Toast.LENGTH_LONG).show();
                    if (loadingDialog != null) {
                        loadingDialog.cancel();
                    }
                    break;
                case 300:
                    break;
            }
        }
    };
    private void InitView() {
        tv_header_title = (TextView) findViewById(R.id.tv_header_title);
        tv_left = (TextView) findViewById(R.id.tv_left);
        tv_left.setOnClickListener(this);
        recycler_follow = (LoadMoreRecyclerView) findViewById(R.id.recycler_follow);
        rl_no_date = (RelativeLayout) findViewById(R.id.rl_no_date);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_left:
                //关闭当前页面
                FollowActivity.this.finish();
                break;
        }
    }
}
