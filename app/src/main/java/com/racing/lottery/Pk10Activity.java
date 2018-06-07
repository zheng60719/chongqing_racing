package com.racing.lottery;

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
import com.racing.Adapter.Pk10Adapter;
import com.racing.entity.HistoryLottery;
import com.racing.entity.Pk10;
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
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by Administrator on 2017/09/24.
 * Pk10技巧
 */
public class  Pk10Activity extends AppCompatActivity implements View.OnClickListener,LoadMoreRecyclerView.LoadMoreListener,SwipeRefreshLayout.OnRefreshListener {
    private HttpURLConnection conn;
    private InputStream is;
    private OutputStream os;
    private TextView tv_header_title;
    private TextView tv_left;
    //OkHttpClient
    public String data;
    private URL url;
    public LoadMoreRecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private Pk10Adapter pk10Adapter;
    private List<Pk10> list;
    private String qishu;
    private long shijian;
    private TextView tv_qishu,tv_shijian;
    private LoadingDialog loadingDialog;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pk_10);
        InitView();
        tv_header_title.setText("技巧文章");
        if (loadingDialog == null) {
            loadingDialog = new LoadingDialog(Pk10Activity.this, R.style.LoadingDialog);
        }
        loadingDialog.show();
        initRecyclerViewListener();
        getQishuAndTime();
        //获取数据
        getData("31");
    }

    private void InitView() {
        tv_header_title = (TextView) findViewById(R.id.tv_header_title);
        tv_left = (TextView) findViewById(R.id.tv_left);
        tv_left.setOnClickListener(this);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swiperefresh);
        recyclerView  = (LoadMoreRecyclerView) findViewById(R.id.recycler_pk);
        tv_qishu = (TextView) findViewById(R.id.tv_qishu);
        tv_shijian = (TextView) findViewById(R.id.tv_shijian);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_left:
                Pk10Activity.this.finish();
                break;
        }
    }

    public void getData(String id) {
        //创建网络处理的对象
        OkHttpUtils
                .post()
                .url("http://www.zse6.com/index.php/mobile/cqsscapi/get_pk")
                .addParams("id", id)
                .build().execute(new StringCallback() {
            @Override
            public void onError(okhttp3.Call call, Exception e, int id) {
                MyToast.getToast(Pk10Activity.this, "请求失败");
            }

            @Override
            public void onResponse(String response, int id) {
                try {
                    //数据解析
                    JSONArray jsonObj = new JSONObject(response).getJSONArray("data");
                    list = new ArrayList<>();
                    for (int i = 0;i<jsonObj.length();i++){
                        JSONObject o = (JSONObject) jsonObj.get(i);
                        Pk10 pk10 = new Pk10();
                        pk10.setTitle(o.getString("title"));
                        pk10.setShijian(o.getLong("shijian"));
                        list.add(pk10);
                    }
                    Message message = new Message();
                    message.what = 200;
                    myHandler.sendMessage(message);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        String urlDate="http://www.zse6.com/index.php/mobile/cqsscapi/get_api";
        try {
            url=new URL(urlDate);
            try {
                //打开服务器
                conn=(HttpURLConnection) url.openConnection();
                //设置输入输出流
                conn.setDoOutput(true);
                conn.setDoInput(true);
                //设置请求的方法为Post
                conn.setRequestMethod("POST");
                //Post方式不能缓存数据，则需要手动设置使用缓存的值为false
                conn.setUseCaches(false);
                //连接数据库
                conn.connect();
                /**写入参数**/
                os=conn.getOutputStream();
                //封装写给服务器的数据（这里是要传递的参数）
                DataOutputStream dos=new DataOutputStream(os);
                //写方法：name是key值不能变，编码方式使用UTF-8可以用中文
                dos.writeBytes("id="+ URLEncoder.encode(id, "UTF-8"));
                //关闭外包装流
                dos.close();
                /**读服务器数据**/
                is=conn.getInputStream();
                BufferedReader br=new BufferedReader(new InputStreamReader(is));
                String line=null;
                StringBuffer sb=new StringBuffer();
                while((line=br.readLine())!=null){
                    sb.append(line);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    //handler
    public Handler myHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 200:
                    if (loadingDialog != null) {
                        loadingDialog.cancel();
                    }
                    if (swipeRefreshLayout.isRefreshing()) {
                        swipeRefreshLayout.setRefreshing(false);
                    }
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(Pk10Activity.this);
                    recyclerView.setLayoutManager(linearLayoutManager);
                    recyclerView.setHasFixedSize(true);
                    recyclerView.setAutoLoadMoreEnable(false);
                    pk10Adapter = new Pk10Adapter(Pk10Activity.this,list,R.layout.pk_10_item);
                    recyclerView.setAdapter(pk10Adapter);
                    break;
                case 100:
                    MyToast.getToast(Pk10Activity.this,"请求失败");
                    break;
                case 300:
                     tv_qishu.setText(qishu);
                    if (shijian<0){
                        tv_shijian.setText("开奖中");
                    }else {
                        tv_shijian.setText(DateUtil.timeParse(shijian)+"");
                    }
                    break;
            }
        }
    };
    /**
     * init recyclerView
     */
    private void initRecyclerViewListener() {
        //下拉刷新
        swipeRefreshLayout.setOnRefreshListener(this);
        //下拉加载更多
        recyclerView.setLoadMoreListener(this);
    }
    @Override
    public void onRefresh() {
                getQishuAndTime();
                list.clear();
                getData("31");
    }

    @Override
    public void onLoadMore() {
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
                MyToast.getToast(Pk10Activity.this,"请求失败");
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
}
