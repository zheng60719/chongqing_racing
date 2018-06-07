package com.racing.lottery;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.TextView;

import com.lottery.R;
import com.racing.Adapter.ReferenceResourcesAdapter;
import com.racing.entity.CanKao;
import com.racing.entity.ErJi;
import com.racing.utils.DateUtil;
import com.racing.view.MyToast;
import com.racing.widget.LoadMoreRecyclerView;
import com.racing.widget.LoadingDialog;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by Administrator on 2017/09/24.
 * 免费参考
 */
public class ReferenceResourcesActivity extends AppCompatActivity implements View.OnClickListener,LoadMoreRecyclerView.LoadMoreListener,SwipeRefreshLayout.OnRefreshListener {
    private String qishu;
    private Long shijian;
    private TextView tv_qishu,tv_shijian;
    //免费参考
    private TextView tv_header_title;
    //返回
    private TextView tv_left;
    //刷新
    private SwipeRefreshLayout swiperefresh;
    //RecyclerView
    private LoadMoreRecyclerView recycler_ReferenceResources;
    //参考
    private List<CanKao> list;
    //adapter
    private ReferenceResourcesAdapter adapter;
    private LoadingDialog loadingDialog;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reference_resources);
        InitView();
        tv_header_title.setText("免费参考");
        if (loadingDialog == null) {
            loadingDialog = new LoadingDialog(ReferenceResourcesActivity.this, R.style.LoadingDialog);
        }
        loadingDialog.show();
        initRecyclerViewListener();
        getQishuAndTime();
        //获取免费参考的数据
        getData();
    }

    private void InitView() {
        tv_header_title = (TextView) findViewById(R.id.tv_header_title);
        tv_left = (TextView) findViewById(R.id.tv_left);
        tv_left.setOnClickListener(this);
        swiperefresh = (SwipeRefreshLayout) findViewById(R.id.swiperefresh);
        recycler_ReferenceResources = (LoadMoreRecyclerView) findViewById(R.id.recycler_ReferenceResources);
        tv_qishu = (TextView) findViewById(R.id.tv_qishu);
        tv_shijian = (TextView) findViewById(R.id.tv_shijian);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_left:
                //返回
                ReferenceResourcesActivity.this.finish();
                break;
        }
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
                MyToast.getToast(ReferenceResourcesActivity.this,"请求失败");
            }

            @Override
            public void onResponse(String response, int id) {
                try {
                    //数据解析
                    JSONObject jsonObj = new JSONObject(response).getJSONObject("next");
                    qishu = jsonObj.getString("qishus");
                    shijian = jsonObj.getLong("shijian_chazhi");
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
                case 200:
                    if (swiperefresh.isRefreshing()) {
                        swiperefresh.setRefreshing(false);
                    }
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ReferenceResourcesActivity.this);
                    recycler_ReferenceResources.setLayoutManager(linearLayoutManager);
                    recycler_ReferenceResources.setHasFixedSize(true);
                    recycler_ReferenceResources.setAutoLoadMoreEnable(false);
                    adapter = new ReferenceResourcesAdapter(ReferenceResourcesActivity.this,list,R.layout.reference_resources_item);
                    recycler_ReferenceResources.setAdapter(adapter);
                    if (loadingDialog != null) {
                        loadingDialog.cancel();
                    }
                    break;
                case 100:
                    MyToast.getToast(ReferenceResourcesActivity.this,"请求失败");
                    break;
                case 300:
                    tv_qishu.setText(qishu);
                    if (shijian<0){
                        tv_shijian.setText("开奖中");
                        tv_shijian.setTextColor(Color.parseColor("#FF8C00"));
                    }else{
                        tv_shijian.setText(DateUtil.timeParse(shijian)+"");
                    }
                    break;
            }
        }
    };


    /**
     * 获取免费参考数据
     */
    public void getData() {
        //创建网络处理的对象
        OkHttpUtils
                .post()
                .url("http://www.zse6.com/index.php/mobile/cqsscapi/get_cankao")
                .build().execute(new StringCallback() {
            @Override
            public void onError(okhttp3.Call call, Exception e, int id) {
                MyToast.getToast(ReferenceResourcesActivity.this,"请求失败");
            }

            @Override
            public void onResponse(String response, int id) {
                try {
                    //数据解析
                    JSONArray jsonObj = new JSONObject(response).getJSONArray("html");
                    list = new ArrayList<>();
                    for (int i = 0;i<jsonObj.length();i++){
                        JSONObject o = (JSONObject) jsonObj.get(i);
                        CanKao canKao = new CanKao();
                        canKao.setQishu(o.getString("qishu"));
                        String erji = o.getString("erji");
                        JSONArray jsonArray = new JSONArray(erji);
                        List<ErJi> list_erji = new ArrayList<>();
                        for (int j=0;j<jsonArray.length();j++){
                            ErJi erJi = new ErJi();
                            JSONObject jsonObject = (JSONObject) jsonArray.get(j);
                            erJi.setNum(jsonObject.getString("num"));
                            erJi.setDaxiao(jsonObject.getString("daxiao"));
                            erJi.setWeizhi(jsonObject.getString("weizhi"));
                            erJi.setDanshuang(jsonObject.getString("danshuang"));
                            list_erji.add(erJi);
                            canKao.setList(list_erji);
                        }
                        list.add(canKao);
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
    /**
     * init recyclerView
     */
    private void initRecyclerViewListener() {
        //下拉刷新
        swiperefresh.setOnRefreshListener(this);
        //下拉加载更多
        recycler_ReferenceResources.setLoadMoreListener(this);
    }
    @Override
    public void onRefresh() {
        //下拉刷新
        new  Thread(new Runnable() {
            @Override
            public void run() {
                getQishuAndTime();
            }
        }).start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                list.clear();
                getData();
            }
        }).start();
    }

    @Override
    public void onLoadMore() {
        //上拉加载
    }
}
