package com.racing.lottery;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.lottery.R;
import com.racing.Adapter.MyHistoricalStatisticsAdapter;
import com.racing.entity.HistoricalStatistics;
import com.racing.entity.HistoricalStatisticsItem;
import com.racing.utils.DateUtil;
import com.racing.view.MyToast;
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
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/09/24.
 * 历史统计
 */
public class HistoricalStatisticsActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView tv_header_title;
    private TextView tv_left;
    private String qishu;
    private Long shijian;
    private TextView tv_qishu,tv_shijian;
    private HttpURLConnection conn1;
    private InputStream is;
    private OutputStream os;
    private URL url;
    private HttpURLConnection conn;
    private URL url1;
    private List<HistoricalStatistics> list;
    private String num1,num2,num3,num4,num5,qishu1;
    private Long shijian1;
    private TextView tv_num1,tv_num2,tv_num3,tv_num4,tv_num5,tv_qishu1,tv_shijian1;
    private ListView recycler_history_statistics;
    private List<HistoricalStatistics> historicalStatisticses;
    private LoadingDialog loadingDialog;
    private MyHistoricalStatisticsAdapter adapter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.historicalstatistics);
        InitView();
        tv_header_title.setText("历史统计");
        if (loadingDialog == null) {
            loadingDialog = new LoadingDialog(HistoricalStatisticsActivity.this, R.style.LoadingDialog);
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
        recycler_history_statistics = (ListView) findViewById(R.id.recycler_history_statistics);
        tv_header_title = (TextView) findViewById(R.id.tv_header_title);
        tv_left = (TextView) findViewById(R.id.tv_left);
        tv_left.setOnClickListener(this);
        tv_qishu = (TextView) findViewById(R.id.tv_qishu);
        tv_shijian = (TextView) findViewById(R.id.tv_shijian);
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
                HistoricalStatisticsActivity.this.finish();
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
                MyToast.getToast(HistoricalStatisticsActivity.this,"请求失败");
            }

            @Override
            public void onResponse(String response, int id) {
                try {
                    //数据解析
                    JSONObject jsonObj = new JSONObject(response).getJSONObject("next");
                    qishu = jsonObj.getString("qishus");
                    shijian = jsonObj.getLong("shijian_chazhi");
                    JSONObject jsonObj1 = new JSONObject(response).getJSONObject("now");
                    num1 = jsonObj1.getString("num1");
                    num2 = jsonObj1.getString("num2");
                    num3 = jsonObj1.getString("num3");
                    num4 = jsonObj1.getString("num4");
                    num5 = jsonObj1.getString("num5");
                    qishu1 = jsonObj1.getString("qishus");
                    shijian1 = jsonObj1.getLong("shijian");
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
                    adapter = new MyHistoricalStatisticsAdapter(HistoricalStatisticsActivity.this,historicalStatisticses,1);
                    recycler_history_statistics.setAdapter(adapter);
                    if (loadingDialog != null) {
                        loadingDialog.cancel();
                    }
                    break;
                case 100:
                    MyToast.getToast(HistoricalStatisticsActivity.this,"请求失败");
                    break;
                case 300:
                    tv_qishu.setText(qishu);
                    if (shijian<0){
                        tv_shijian.setText("开奖中");
                        tv_shijian.setTextColor(Color.parseColor("#FF8C00"));
                    }else{
                        tv_shijian.setText(DateUtil.timeParse(shijian)+"");
                    }
                    tv_num1.setText(num1);
                    tv_num2.setText(num2);
                    tv_num3.setText(num3);
                    tv_num4.setText(num4);
                    tv_num5.setText(num5);
                    tv_qishu1.setText(qishu+"开奖结果");
                    SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    //前面的lSysTime是秒数，先乘1000得到毫秒数，再转为java.util.Date类型
                    java.util.Date dt = new Date(shijian1*1000);
                    String sDateTime = sdf.format(dt);  //得到精确到秒的表示：08/31/2006 21:08:00
                    tv_shijian1.setText(sDateTime);
                    break;
            }
        }
    };
    public void getData(String shijian) {
        //创建网络处理的对象
        OkHttpUtils
                .post()
                .url("http://www.zse6.com/index.php/mobile/cqsscapi/get_lishi_tongji")
                .addParams("shijian",shijian)
                .build().execute(new StringCallback() {
            @Override
            public void onError(okhttp3.Call call, Exception e, int id) {
                MyToast.getToast(HistoricalStatisticsActivity.this, "请求失败");
            }

            @Override
            public void onResponse(String response, int id) {
                try {
                    //数据解析
                    historicalStatisticses = jsonTo(response.toString());
                    Message message = new Message();
                    message.what = 200;
                    myHandler.sendMessage(message);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }


    //解析数据
    public List<HistoricalStatistics> jsonTo(String json) {
        List<HistoricalStatistics> list1 = null;
        try {
            JSONArray jsonArray = new JSONObject(json).getJSONArray("data");
            list1 = new ArrayList<>();
            for (int i = 0; i < jsonArray.length(); i++) {
                HistoricalStatistics data1 = new HistoricalStatistics();
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                data1.setDay(jsonObject.getString("day"));
                data1.setGyh_da(jsonObject.getString("zonghe_da"));
                data1.setGyh_xiao(jsonObject.getString("zonghe_xiao"));
                data1.setGyh_dan(jsonObject.getString("zonghe_dan"));
                data1.setGyh_shuang(jsonObject.getString("zonghe_shuang"));
                Iterator<String> keys = jsonObject.keys();
                Map<String, HistoricalStatisticsItem> map = new HashMap<>();
                while (keys.hasNext()) {
                    String key1 = keys.next();
                    Object o = null;
                    if (isNumeric(key1)) {
                        JSONObject jsonObject2 = jsonObject.getJSONObject(key1);
                        HistoricalStatisticsItem data2 = new HistoricalStatisticsItem();
                        data2.setDa(jsonObject2.optString("da"));
                        data2.setDan(jsonObject2.optString("dan"));
                        data2.setShuang(jsonObject2.optString("shuang"));
                        data2.setXiao(jsonObject2.optString("xiao"));
                        map.put(key1, data2);
                    }
                    if (map!=null) {
                        data1.setMap(map);
                    }
                }
                list1.add(data1);
                }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list1;
    }

    public static boolean isNumeric(String str) {
        for (int i = 0; i < str.length(); i++) {
            if (!Character.isDigit(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }

}
