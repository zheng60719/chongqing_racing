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
import com.racing.Adapter.MyForecastAdapter;
import com.racing.app.AppSp;
import com.racing.entity.MyForecast;
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
 * Created by k41 on 2017/12/22.
 * 我的预测
 */

public class MyForecastActivity extends AppCompatActivity implements View.OnClickListener, LoadMoreRecyclerView.LoadMoreListener, SwipeRefreshLayout.OnRefreshListener {
    private TextView tv_header_title;
    public TextView tv_left;
    //hotcat
    private LoadingDialog loadingDialog;
    private LoadMoreRecyclerView recycler_my_forecast;
    private AppSp appSp;
    //UserBean
    private UserBean userBean;
    //MyForecast
    private List<MyForecast> list_my_forecast;
    //adapter
    private MyForecastAdapter adapter;
    private String qishu;
    private SwipeRefreshLayout swipeRefreshLayout;
    private int currpage = 1;
    private RelativeLayout rl_no_date;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.my_forecast);
        InitView();
        tv_header_title.setText("我的预测");
        if (loadingDialog == null) {
            loadingDialog = new LoadingDialog(MyForecastActivity.this, R.style.LoadingDialog);
        }
        loadingDialog.show();
        getQishuAndTime();
        appSp = new AppSp();
        userBean = (UserBean) appSp.getObjectFromShare(MyForecastActivity.this, "user");
        //获取当前的期数
        initRecyclerViewListener();
    }

    //查询时间期数
    public void getQishuAndTime() {
        //创建网络处理的对象
        OkHttpUtils
                .post()
                .url("http://www.zse6.com/index.php/mobile/cqsscapi/get_api")
                .build().execute(new StringCallback() {
            @Override
            public void onError(okhttp3.Call call, Exception e, int id) {
                MyToast.getToast(MyForecastActivity.this,"请求失败");
            }

            @Override
            public void onResponse(String response, int id) {
                try {
                    //数据解析
                    JSONObject jsonObj = new JSONObject(response).getJSONObject("next");
                    qishu = jsonObj.getString("qishus");
                    Message message = new Message();
                    message.what = 300;
                    myHandler.sendMessage(message);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
    }

    private void getData(int uid, String key, int page) {
        OkHttpUtils
                .post()
                .url("http://www.zse6.com/index.php/mobile/ajax/my_yuce")
                .addParams("uid", uid + "")
                .addParams("key", key)
                .addParams("page", page + "")
                .build().execute(new StringCallback() {
            @Override
            public void onError(okhttp3.Call call, Exception e, int id) {
                MyToast.getToast(MyForecastActivity.this, "请求失败");
                e.printStackTrace();
            }

            @Override
            public void onResponse(String response, int id) {
                try {
                    JSONArray jsonObj = new JSONObject(response).getJSONArray("data");
                    list_my_forecast = new ArrayList<>();
                    if (currpage == 1) {
                        list_my_forecast.clear();
                        for (int i = 0; i < jsonObj.length(); i++) {
                            JSONObject o = (JSONObject) jsonObj.get(i);
                            MyForecast myForecast = new MyForecast();
                            myForecast.setId(o.getInt("id"));
                            myForecast.setType(o.getInt("type"));
                            myForecast.setQishu(o.getString("qishu"));
                            myForecast.setWeizhi(o.getInt("weizhi"));
                            myForecast.setYuceinfo(o.getString("yuceinfo"));
                            myForecast.setIs_ok(o.getInt("is_auto"));
                            myForecast.setTitle(o.getString("title"));
                            myForecast.setShijian(o.getLong("shijian"));
                            list_my_forecast.add(myForecast);
                        }
                    } else {
                        for (int i = 0; i < jsonObj.length(); i++) {
                            JSONObject o = (JSONObject) jsonObj.get(i);
                            MyForecast myForecast = new MyForecast();
                            myForecast.setId(o.getInt("id"));
                            myForecast.setType(o.getInt("type"));
                            myForecast.setQishu(o.getString("qishu"));
                            myForecast.setWeizhi(o.getInt("weizhi"));
                            myForecast.setYuceinfo(o.getString("yuceinfo"));
                            myForecast.setIs_ok(o.getInt("is_auto"));
                            myForecast.setTitle(o.getString("title"));
                            myForecast.setShijian(o.getLong("shijian"));
                            list_my_forecast.add(myForecast);
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

    private void InitView() {
        tv_header_title = (TextView) findViewById(R.id.tv_header_title);
        tv_left = (TextView) findViewById(R.id.tv_left);
        tv_left.setOnClickListener(this);
        recycler_my_forecast = (LoadMoreRecyclerView) findViewById(R.id.recycler_my_forecast);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swiperefresh);
        rl_no_date = (RelativeLayout) findViewById(R.id.rl_no_date);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_left:
                MyForecastActivity.this.finish();
                break;
        }
    }

    public Handler myHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 200:
                    if (swipeRefreshLayout.isRefreshing()) {
                        swipeRefreshLayout.setRefreshing(false);
                    }
                    if (list_my_forecast.size()>0) {
                        rl_no_date.setVisibility(View.GONE);
                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(MyForecastActivity.this);
                        recycler_my_forecast.setLayoutManager(linearLayoutManager);
                        recycler_my_forecast.setHasFixedSize(true);
                        recycler_my_forecast.setAutoLoadMoreEnable(false);
                        adapter = new MyForecastAdapter(MyForecastActivity.this, list_my_forecast, R.layout.my_forecast_item, qishu);
                        recycler_my_forecast.setAdapter(adapter);
                    }else{
                        rl_no_date.setVisibility(View.VISIBLE);
                    }
                    if (loadingDialog != null) {
                        loadingDialog.cancel();
                    }
                    break;
                case 100:
                    break;
                case 300:
//                    getData(userBean.getId(), "pk10", 1);
                    getData(userBean.getId(), "cqssc", currpage);
                    break;
            }
        }
    };

    @Override
    public void onRefresh() {
        currpage = 1;
        list_my_forecast.clear();
        if (loadingDialog == null) {
            loadingDialog = new LoadingDialog(MyForecastActivity.this, R.style.LoadingDialog);
        }
        loadingDialog.show();
        getData(userBean.getId(), "cqssc", currpage);
    }

    /**
     * init recyclerView
     */
    private void initRecyclerViewListener() {
        //下拉刷新
        swipeRefreshLayout.setOnRefreshListener(this);
        //下拉加载更多
        recycler_my_forecast.setLoadMoreListener(this);
    }

    @Override
    public void onLoadMore() {
        currpage++;
        if (loadingDialog == null) {
            loadingDialog = new LoadingDialog(MyForecastActivity.this, R.style.LoadingDialog);
        }
        loadingDialog.show();
        getData(userBean.getId(), "cqssc", currpage);
    }
}
