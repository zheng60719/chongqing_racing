package com.racing.lottery;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.lottery.R;
import com.racing.Adapter.MyTodayNumberAdapter;
import com.racing.entity.TodayNumber;
import com.racing.entity.TodayNumberItem;
import com.racing.utils.DateUtil;
import com.racing.view.MyToast;
import com.racing.widget.LoadingDialog;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/09/24.
 * 今日号码
 */
public class TodayNumberActivity extends AppCompatActivity {
    private TextView tv_header_title;
    private TextView tv_left;
    private String qishu;
    private Long shijian;
    private TextView tv_qishu, tv_shijian;
    //recycler
    private ListView recycler_number;
    //adapter
    private MyTodayNumberAdapter adapter;
    private TodayNumber todayNumber;
    private LoadingDialog loadingDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.today_number);
        InitView();
        tv_header_title.setText("今日号码");
        if (loadingDialog == null) {
            loadingDialog = new LoadingDialog(TodayNumberActivity.this, R.style.LoadingDialog);
        }
        loadingDialog.show();
        getQishuAndTime();
        //获取数据
        getData();
    }

    private void InitView() {
        tv_header_title = (TextView) findViewById(R.id.tv_header_title);
        tv_left = (TextView) findViewById(R.id.tv_left);
        tv_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TodayNumberActivity.this.finish();
            }
        });
        tv_qishu = (TextView) findViewById(R.id.tv_qishu);
        tv_shijian = (TextView) findViewById(R.id.tv_shijian);
        recycler_number = (ListView) findViewById(R.id.recycler_number);
    }

    //handler
    public Handler myHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 200:
                    adapter = new MyTodayNumberAdapter(TodayNumberActivity.this, todayNumber);
                    recycler_number.setAdapter(adapter);
                    if (loadingDialog != null) {
                        loadingDialog.cancel();
                    }
                    break;
                case 300:
                    tv_qishu.setText(qishu);
                    if (shijian < 0) {
                        tv_shijian.setText("开奖中");
                        tv_shijian.setTextColor(Color.parseColor("#FF8C00"));
                    } else {
                        tv_shijian.setText(DateUtil.timeParse(shijian) + "");
                    }
                    break;
            }
        }
    };

    //查询时间期数
    public void getQishuAndTime() {
        //创建网络处理的对象
        OkHttpUtils
                .post()
                .url("http://www.zse6.com/index.php/mobile/cqsscapi/get_api")
                .build().execute(new StringCallback() {
            @Override
            public void onError(okhttp3.Call call, Exception e, int id) {
                MyToast.getToast(TodayNumberActivity.this, "请求失败");
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
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * 请求今日号码数据
     */
    public void getData() {
        //创建网络处理的对象
        OkHttpUtils
                .post()
                .url("http://www.zse6.com/index.php/mobile/cqsscapi/get_jinri")
                .build().execute(new StringCallback() {
            @Override
            public void onError(okhttp3.Call call, Exception e, int id) {
                MyToast.getToast(TodayNumberActivity.this, "请求失败");
            }

            @Override
            public void onResponse(String response, int id) {
                try {
                    //数据解析
                    todayNumber = jsonTo(response);
                    Message message = new Message();
                    message.what = 200;
                    myHandler.sendMessage(message);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public TodayNumber jsonTo(String json) {
        TodayNumber msg = new TodayNumber();
        try {
            JSONObject jsonObject = new JSONObject(json);
            msg.setIng(jsonObject.optString("ing"));
            msg.setMsg(jsonObject.optString("msg"));
            JSONArray jsonArray = jsonObject.getJSONArray("data");
            JSONObject jsonObject10 = jsonArray.getJSONObject(1);
            Map<String,TodayNumberItem> mapMap = new HashMap<>();
            Iterator<String> keys = jsonObject10.keys();
            while (keys.hasNext()) {
                String key1 = keys.next();
                JSONObject jsonObject2 = jsonObject10.getJSONObject(key1);
                TodayNumberItem data = new TodayNumberItem();
                data.setWeikai(jsonObject2.optString("weikai"));
                data.setZongkai(jsonObject2.optString("zongkai"));
                mapMap.put(key1, data);
            }
            msg.setData(mapMap);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return msg;
    }

}
