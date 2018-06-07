package com.racing.lottery;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lottery.R;
import com.racing.Adapter.IntegralAdapter;
import com.racing.app.AppSp;
import com.racing.entity.Integral;
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
 * Created by k41 on 2017/10/13.
 */

public class IntegralActivity extends AppCompatActivity implements View.OnClickListener,LoadMoreRecyclerView.LoadMoreListener,SwipeRefreshLayout.OnRefreshListener {
    private TextView tv_header_title;
    public TextView tv_left;
    public LoadMoreRecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private LoadingDialog loadingDialog;
    private UserBean userBean;
    private AppSp appSp;
    private List<Integral> list;
    private IntegralAdapter adapter;
    private int page = 1;
    private RelativeLayout rl_no_date;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.integral);
        InitView();
        tv_header_title.setText("积分记录");
        if (loadingDialog == null) {
            loadingDialog = new LoadingDialog(IntegralActivity.this, R.style.LoadingDialog);
        }
        initRecyclerViewListener();
        loadingDialog.show();
        appSp = new AppSp();
        userBean = (UserBean) appSp.getObjectFromShare(IntegralActivity.this, "user");
        getData(userBean.getId()+"",page);
    }
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
        recyclerView  = (LoadMoreRecyclerView) findViewById(R.id.recycler_integral);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swiperefresh);
        rl_no_date = (RelativeLayout) findViewById(R.id.rl_no_date);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_left:
                IntegralActivity.this.finish();
                break;
        }
    }
    public void getData(String id,int page) {
        OkHttpUtils
                .post()
                .url("http://www.zse6.com/index.php/mobile/ajax/my_jifen_list")
                .addParams("uid", id)
                .addParams("page", page+"")
                .build().execute(new StringCallback() {
            @Override
            public void onError(okhttp3.Call call, Exception e, int id) {
                MyToast.getToast(IntegralActivity.this,"请求失败");
            }

            @Override
            public void onResponse(String response, int id) {
                try {
                    JSONArray jsonObj = new JSONObject(response).getJSONArray("data");
                    list = new ArrayList<>();
                    for (int i = 0;i<jsonObj.length();i++){
                        JSONObject o = (JSONObject) jsonObj.get(i);
                        Integral integral = new Integral();
                        integral.setId(o.getInt("id"));
                        integral.setShijian(o.getString("shijian"));
                        integral.setYid(o.getString("yid"));
                        integral.setBeizhu(o.getString("beizhu"));
                        integral.setKey(o.getString("key"));
                        integral.setMoney(o.getString("money"));
                        integral.setUid(o.getString("uid"));
                        list.add(integral);
                    }
                    Message message = new Message();
                    message.what = 200;
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
                case 200:
                    if (swipeRefreshLayout.isRefreshing()) {
                        swipeRefreshLayout.setRefreshing(false);
                    }
                    if (list.size()>0) {
                        rl_no_date.setVisibility(View.GONE);
                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(IntegralActivity.this);
                        recyclerView.setLayoutManager(linearLayoutManager);
                        recyclerView.setHasFixedSize(true);
                        recyclerView.setAutoLoadMoreEnable(false);
                        adapter = new IntegralAdapter(IntegralActivity.this, list, R.layout.integral_item);
                        recyclerView.setAdapter(adapter);
                    }else{
                        rl_no_date.setVisibility(View.VISIBLE);
                    }
                    if (loadingDialog != null) {
                        loadingDialog.cancel();
                    }
                    break;
            }
        }
    };

    @Override
    public void onRefresh() {
        //下拉刷新
        list.clear();
        page=1;
        getData(userBean.getId()+"",page);
    }

    @Override
    public void onLoadMore() {
        //上拉加载
        page++;
        list.addAll(list);
        getData(userBean.getId()+"",page);
    }
}
