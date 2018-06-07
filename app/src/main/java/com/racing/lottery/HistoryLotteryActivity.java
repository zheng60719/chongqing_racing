package com.racing.lottery;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by Administrator on 2017/09/24.
 * 历史开奖
 */
public class HistoryLotteryActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView tv_header_title;
    public LoadMoreRecyclerView recyclerView;
    public String data;
    public TextView tv_left;
    //存储数据
    public List<HistoryLottery> list;
    //apter
    public HistoryLotteryAdapter historyLotteryAdapter;
    private TextView tv_qishu, tv_shijian;
    private String qishu;
    private int shijian;
    private LoadingDialog loadingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.history_lottery);
        InitView();
        tv_header_title.setText("历史开奖");
        if (loadingDialog == null) {
            loadingDialog = new LoadingDialog(HistoryLotteryActivity.this, R.style.LoadingDialog);
        }
        loadingDialog.show();
        getQishuAndTime();
        //获取当前时间
        SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String date = sDateFormat.format(new java.util.Date());
        //获取数据
        getData(date);
    }

    private void InitView() {
        tv_header_title = (TextView) findViewById(R.id.tv_header_title);
        recyclerView = (LoadMoreRecyclerView) findViewById(R.id.recycler_history_hottery);
        tv_left = (TextView) findViewById(R.id.tv_left);
        tv_left.setOnClickListener(this);
        tv_qishu = (TextView) findViewById(R.id.tv_qishu);
        tv_shijian = (TextView) findViewById(R.id.tv_shijian);
    }


    //handler
    public Handler myHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 200:
                    if (loadingDialog != null) {
                        loadingDialog.cancel();
                    }
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(HistoryLotteryActivity.this);
                    recyclerView.setLayoutManager(linearLayoutManager);
                    recyclerView.setHasFixedSize(true);
                    recyclerView.setAutoLoadMoreEnable(false);
                    historyLotteryAdapter = new HistoryLotteryAdapter(HistoryLotteryActivity.this, list, R.layout.history_lottery_item);
                    recyclerView.setAdapter(historyLotteryAdapter);
                    break;
                case 300:
                    tv_qishu.setText(qishu);
                    if (shijian < 0) {
                        tv_shijian.setText("开奖中");
                    } else {
                        tv_shijian.setText(DateUtil.timeParse(shijian+180) + "");
                    }
                    break;
            }
        }
    };

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_left:
                HistoryLotteryActivity.this.finish();
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
                MyToast.getToast(HistoryLotteryActivity.this, "请求失败");
            }

            @Override
            public void onResponse(String response, int id) {
                try {
                    //数据解析
                    JSONObject jsonObj = new JSONObject(response).getJSONObject("next");
                    qishu = jsonObj.getString("qishus");
                    shijian = jsonObj.getInt("shijian_chazhi");
                    Message message = new Message();
                    message.what = 300;
                    myHandler.sendMessage(message);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * 历史开奖
     */
    public void getData(String shijian) {
        //创建网络处理的对象
        OkHttpUtils
                .post()
                .url("http://www.zse6.com/index.php/mobile/cqsscapi/get_lishi")
                .addParams("shijian", shijian)
                .build().execute(new StringCallback() {
            @Override
            public void onError(okhttp3.Call call, Exception e, int id) {
                MyToast.getToast(HistoryLotteryActivity.this, "请求失败");
            }

            @Override
            public void onResponse(String response, int id) {
                try {
                    //数据解析
                    JSONArray jsonObj = new JSONObject(response).getJSONArray("data");
                    list = new ArrayList<>();
                    for (int i = 0; i < jsonObj.length(); i++) {
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
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
