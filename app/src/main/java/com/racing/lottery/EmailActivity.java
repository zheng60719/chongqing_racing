package com.racing.lottery;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lottery.R;
import com.racing.Adapter.EmailAdapter;
import com.racing.app.AppSp;
import com.racing.entity.MyEmail;
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
 * Created by k41 on 2018/1/2.
 * 我的站内信
 */

public class EmailActivity extends AppCompatActivity implements View.OnClickListener, LoadMoreRecyclerView.LoadMoreListener, SwipeRefreshLayout.OnRefreshListener {
    private TextView tv_header_title;
    public TextView tv_left;
    public LoadMoreRecyclerView recyclerView;
    public EmailAdapter emailAdapter;
    //hotcat
    private LoadingDialog loadingDialog;
    private AppSp appSp;
    //UserBean
    private UserBean userBean;
    private List<MyEmail> list;
    private SwipeRefreshLayout swipeRefreshLayout;
    private int currpage = 1;
    private RelativeLayout rl_no_date;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.email);
        InitView();
        tv_header_title.setText("站内信");
        if (loadingDialog == null) {
            loadingDialog = new LoadingDialog(EmailActivity.this, R.style.LoadingDialog);
        }
        loadingDialog.show();
        appSp = new AppSp();
        userBean = (UserBean) appSp.getObjectFromShare(EmailActivity.this, "user");
        getData(userBean.getId(), 1);
        initRecyclerViewListener();
    }

    /**
     * init recyclerView
     */
    private void initRecyclerViewListener() {
        //下拉刷新
        swipeRefreshLayout.setOnRefreshListener(this);
        //下拉加载更多
        recyclerView.setLoadMoreListener(this);
    }

    private void InitView() {
        tv_header_title = (TextView) findViewById(R.id.tv_header_title);
        tv_left = (TextView) findViewById(R.id.tv_left);
        tv_left.setOnClickListener(this);
        recyclerView = (LoadMoreRecyclerView) findViewById(R.id.recycler_email);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swiperefresh);
        rl_no_date = (RelativeLayout) findViewById(R.id.rl_no_date);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_left:
                EmailActivity.this.finish();
                break;
        }
    }

    private void getData(int uid, int page) {
        OkHttpUtils
                .post()
                .url("http://www.zse6.com/index.php/mobile/ajax/my_xinjian")
                .addParams("uid", uid + "")
                .addParams("page", page + "")
                .build().execute(new StringCallback() {
            @Override
            public void onError(okhttp3.Call call, Exception e, int id) {
                MyToast.getToast(EmailActivity.this, "请求失败");
                e.printStackTrace();
            }

            @Override
            public void onResponse(String response, int id) {
                try {
                    JSONArray jsonObj = new JSONObject(response).getJSONArray("data");
                    list = new ArrayList<>();
                    if (currpage==1) {
                        list.clear();
                        for (int i = 0; i < jsonObj.length(); i++) {
                            JSONObject o = (JSONObject) jsonObj.get(i);
                            MyEmail myEmail = new MyEmail();
                            myEmail.setId(o.getInt("id"));
                            myEmail.setTitle(o.getString("title"));
                            myEmail.setType(o.getString("type"));
                            myEmail.setIs_sms(o.getString("is_sms"));
                            myEmail.setKanlist(o.getString("kanlist"));
                            myEmail.setReman(o.getString("reman"));
                            myEmail.setShijian(o.getString("shijian"));
                            myEmail.setSend_time(o.getString("send_time"));
                            myEmail.setInfo(o.getString("info"));
                            list.add(myEmail);
                        }
                    }else{
                        for (int i = 0; i < jsonObj.length(); i++) {
                            JSONObject o = (JSONObject) jsonObj.get(i);
                            MyEmail myEmail = new MyEmail();
                            myEmail.setId(o.getInt("id"));
                            myEmail.setTitle(o.getString("title"));
                            myEmail.setType(o.getString("type"));
                            myEmail.setIs_sms(o.getString("is_sms"));
                            myEmail.setKanlist(o.getString("kanlist"));
                            myEmail.setReman(o.getString("reman"));
                            myEmail.setShijian(o.getString("shijian"));
                            myEmail.setSend_time(o.getString("send_time"));
                            myEmail.setInfo(o.getString("info"));
                            list.add(myEmail);
                        }
                    }
                    Message message = new Message();
                    message.what = 200;
                    myHandler.sendMessage(message);

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
                    if (swipeRefreshLayout.isRefreshing()) {
                        swipeRefreshLayout.setRefreshing(false);
                    }
                    if (list.size()>0) {
                        rl_no_date.setVisibility(View.GONE);
                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(EmailActivity.this);
                        recyclerView.setLayoutManager(linearLayoutManager);
                        recyclerView.setHasFixedSize(true);
                        recyclerView.setAutoLoadMoreEnable(false);
                        emailAdapter = new EmailAdapter(EmailActivity.this, list, R.layout.my_email_item);
                        recyclerView.setAdapter(emailAdapter);
                    }else{
                        rl_no_date.setVisibility(View.VISIBLE);
                    }
                    if (loadingDialog != null) {
                        loadingDialog.cancel();
                    }
                    break;
                case 100:
                    break;
            }
        }
    };

    @Override
    public void onRefresh() {
        currpage = 1;
        list.clear();
        if (loadingDialog == null) {
            loadingDialog = new LoadingDialog(EmailActivity.this, R.style.LoadingDialog);
        }
        loadingDialog.show();
        getData(userBean.getId(), currpage);
    }

    @Override
    public void onLoadMore() {
        currpage++;
        list.clear();
        if (loadingDialog == null) {
            loadingDialog = new LoadingDialog(EmailActivity.this, R.style.LoadingDialog);
        }
        loadingDialog.show();
        getData(userBean.getId(), currpage);
    }
}
