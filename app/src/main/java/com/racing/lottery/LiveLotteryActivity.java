package com.racing.lottery;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.lottery.R;
import com.racing.Adapter.HistoryLotteryAdapter;
import com.racing.entity.HistoryLottery;
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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2017/09/24.
 * 开奖直播
 */
public class LiveLotteryActivity extends AppCompatActivity implements View.OnClickListener {
    private URL url;
    private HttpURLConnection conn;
    private URL url1;
    private TextView tv_header_title;
    private InputStream is;
    private OutputStream os;
    private TextView tv_left;
    private HttpURLConnection conn1;
    private WebView webview;
    private String num1,num2,num3,num4,num5,qishu1;
    private Long shijian1;
    private TextView tv_num1,tv_num2,tv_num3,tv_num4,tv_num5,tv_qishu1,tv_shijian1;
    public LoadMoreRecyclerView recycler_liveLottery;
    //存储数据
    public List<HistoryLottery> list;
    //adapter
    public HistoryLotteryAdapter historyLotteryAdapter;
    private LoadingDialog loadingDialog;

    private boolean isBoolean = true;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.live_lottery);
        InitView();
        tv_header_title.setText("开奖直播");
        if (loadingDialog == null) {
            loadingDialog = new LoadingDialog(LiveLotteryActivity.this, R.style.LoadingDialog);
        }
        loadingDialog.show();
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    while (isBoolean) {
                        getQishuAndTime();
//                        Thread.sleep(500);
//                    }
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//        }).start();
        //获取当前时间
        SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String date = sDateFormat.format(new java.util.Date());
        //获取数据
        getData(date);
        WebSettings webSettings = webview.getSettings();
        // 让WebView能够执行javaScript
        webSettings.setJavaScriptEnabled(true);
        // 设置可以被显示的屏幕控制
        webSettings.setDisplayZoomControls(false);
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);
        webview.setWebViewClient(new WebViewClient() {

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {

                return false;
            }

        });
        webview.loadUrl("http://www.zse6.com/index.php/Home/Cqssc/tv");
    }

    private void InitView() {
        recycler_liveLottery = (LoadMoreRecyclerView) findViewById(R.id.recycler_liveLottery);
        webview = (WebView) findViewById(R.id.webview);
        tv_header_title = (TextView) findViewById(R.id.tv_header_title);
        tv_left = (TextView) findViewById(R.id.tv_left);
        tv_left.setOnClickListener(this);
        tv_num1 = (TextView) findViewById(R.id.tv_num1);
        tv_num2 = (TextView) findViewById(R.id.tv_num2);
        tv_num3 = (TextView) findViewById(R.id.tv_num3);
        tv_num4 = (TextView) findViewById(R.id.tv_num4);
        tv_num5 = (TextView) findViewById(R.id.tv_num5);
        tv_qishu1 = (TextView) findViewById(R.id.tv_jieguo);
        tv_shijian1 = (TextView) findViewById(R.id.tv_shijian1);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_left:
                LiveLotteryActivity.this.finish();
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
                MyToast.getToast(LiveLotteryActivity.this,"请求失败");
            }

            @Override
            public void onResponse(String response, int id) {
                try {
                    //数据解析
                    JSONObject jsonObj1 = new JSONObject(response).getJSONObject("now");
                    JSONObject jsonObj = new JSONObject(response).getJSONObject("next");
                    num1 = jsonObj1.getString("num1");
                    num2 = jsonObj1.getString("num2");
                    num3 = jsonObj1.getString("num3");
                    num4 = jsonObj1.getString("num4");
                    num5 = jsonObj1.getString("num5");
                    qishu1 = jsonObj.getString("qishus");
                    shijian1 = jsonObj.getLong("shijian");
                    Message message = new Message();
                    message.what = 300;
                    myHandler.sendMessage(message);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
    }

    public Handler myHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 200:
                    if (loadingDialog != null) {
                        loadingDialog.cancel();
                    }
                  LinearLayoutManager linearLayoutManager = new LinearLayoutManager(LiveLotteryActivity.this);
                    recycler_liveLottery.setLayoutManager(linearLayoutManager);
                    recycler_liveLottery.setHasFixedSize(true);
                    recycler_liveLottery.setAutoLoadMoreEnable(false);
                    historyLotteryAdapter = new HistoryLotteryAdapter(LiveLotteryActivity.this,list,R.layout.history_lottery_item);
                    recycler_liveLottery.setAdapter(historyLotteryAdapter);
                    break;
                case 100:
                    break;
                case 300:
                    tv_num1.setText(num1);
                    tv_num2.setText(num2);
                    tv_num3.setText(num3);
                    tv_num4.setText(num4);
                    tv_num5.setText(num5);
                    tv_qishu1.setText(qishu1);
                    //获取当前时间戳
                    long timeStamp = System.currentTimeMillis()/1000;
                    long sj =timeStamp -shijian1;
                    MyCount mc = new MyCount(sj/6,1000);
                    mc.start();
                    break;
            }
        }
    };

    /*定义一个倒计时的内部类*/
    class MyCount extends CountDownTimer {
        public MyCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            tv_shijian1.setText(millisUntilFinished / 1000 + "秒");
        }

        @Override
        public void onFinish() {
            tv_shijian1.setText("开奖中");
            getQishuAndTime();
        }
    }

    /**
     * 历史开奖
     */
    public void getData(String shijian) {
        //创建网络处理的对象
        OkHttpUtils
                .post()
                .url("http://www.zse6.com/index.php/mobile/cqsscapi/get_lishi")
                .addParams("shijian",shijian)
                .build().execute(new StringCallback() {
            @Override
            public void onError(okhttp3.Call call, Exception e, int id) {
                MyToast.getToast(LiveLotteryActivity.this,"请求失败");
            }

            @Override
            public void onResponse(String response, int id) {
                try {
                    //数据解析
                    JSONArray jsonObj = new JSONObject(response).getJSONArray("data");
                    list = new ArrayList<>();
                    for (int i = 0;i<jsonObj.length();i++){
                        JSONObject o = (JSONObject) jsonObj.get(i);
                        HistoryLottery historyLottery = new HistoryLottery();
                        historyLottery.setQishu(o.getInt("qishu"));
                        historyLottery.setNum1(o.getInt("num1"));
                        historyLottery.setNum2(o.getInt("num2"));
                        historyLottery.setNum3(o.getInt("num3"));
                        historyLottery.setNum4(o.getInt("num4"));
                        historyLottery.setNum5(o.getInt("num5"));
                        historyLottery.setQiansan(o.getString("qiansan"));
                        historyLottery.setZhongsan(o.getString("zhongsan"));
                        historyLottery.setHousan(o.getString("housan"));
                        historyLottery.setLonghu(o.getString("longhu"));
                        historyLottery.setZonghe(o.getString("zonghe"));
                        historyLottery.setZonghedanshuang(o.getString("zonghedanshuang"));
                        historyLottery.setZonghedaxiao(o.getString("zonghedaxiao"));
                        historyLottery.setShijian(o.getLong("shijian"));
                        list.add(historyLottery);
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        isBoolean = false;
    }
}
