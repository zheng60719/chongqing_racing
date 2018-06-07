package com.racing.lottery;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.lottery.R;
import com.racing.Adapter.ForecastAdapter;
import com.racing.Adapter.NowForecastAdapter;
import com.racing.app.AppSp;
import com.racing.entity.LiShi;
import com.racing.entity.Now;
import com.racing.entity.UserBean;
import com.racing.utils.GlideCircleTransform;
import com.racing.utils.ToastUtil;
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
 * Created by Administrator on 2017/09/24.
 * 专家详情
 */
public class ZhuanJiaDetailActivity extends AppCompatActivity implements View.OnClickListener, ForecastAdapter.ForecastOnItemClick, NowForecastAdapter.ForecastOnItemClick {
    private TextView tv_header_title;
    public TextView tv_left;
    private LoadingDialog loadingDialog;
    private ImageView img_user;
    private TextView tv_name, tv_fensi, tv_fanan, tv_ok, tv_beizhu;
    private TextView btn_guanzhu;
    private String img_user_url = null;
    private String name = null, fensi = null, fanan = null, ok = null, beizhu = null, title = null;
    private Long shijian = null;
    private int type = -1;
    private AppSp appSp;
    private UserBean userBean;
    private ForecastAdapter forecastAdapter;
    private NowForecastAdapter nowForecastAdapter;

    private LoadMoreRecyclerView recycler_forecast;
    private LoadMoreRecyclerView recycler_now_forecast;
    private List<LiShi> list_lishi = new ArrayList<>();
    private List<Now> list_now = new ArrayList<>();
    private RelativeLayout rl_no_date;
    private String qishu;
    private TextView tv_zhankai;
    private String now_msg = null;
    //够没查看详情
    private int now_position = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.zhuanjia_detail);
        InitView();
        tv_header_title.setText("专家详情");
        if (loadingDialog == null) {
            loadingDialog = new LoadingDialog(ZhuanJiaDetailActivity.this, R.style.LoadingDialog);
        }
        loadingDialog.show();
        getQishuAndTime();
        appSp = new AppSp();
        userBean = (UserBean) appSp.getObjectFromShare(ZhuanJiaDetailActivity.this, "user");
    }

    //查询时间期数
    public void getQishuAndTime() {
        //创建网络处理的对象
        OkHttpUtils
                .post()
                .url("http://www.zse6.com/index.php/mobile/pk10api/get_api")
                .build().execute(new StringCallback() {
            @Override
            public void onError(okhttp3.Call call, Exception e, int id) {
                MyToast.getToast(ZhuanJiaDetailActivity.this, "请求失败");
            }

            @Override
            public void onResponse(String response, int id) {
                try {
                    JSONObject jsonObj = new JSONObject(response).getJSONObject("next");
                    qishu = jsonObj.getString("qishu");
                    Message message = new Message();
                    message.what = 300;
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
        img_user = (ImageView) findViewById(R.id.img_user);
        tv_name = (TextView) findViewById(R.id.tv_name);
        tv_fensi = (TextView) findViewById(R.id.tv_fensi);
        btn_guanzhu = (TextView) findViewById(R.id.btn_guanzhu);
        btn_guanzhu.setOnClickListener(this);
        tv_fanan = (TextView) findViewById(R.id.tv_fanan);
        tv_ok = (TextView) findViewById(R.id.tv_ok);
        tv_beizhu = (TextView) findViewById(R.id.tv_beizhu);
        rl_no_date = (RelativeLayout) findViewById(R.id.rl_no_date);
        recycler_forecast = (LoadMoreRecyclerView) findViewById(R.id.recycler_forecast);
        recycler_now_forecast = (LoadMoreRecyclerView) findViewById(R.id.recycler_now_forecast);
        tv_zhankai = (TextView) findViewById(R.id.tv_zhankai);
        tv_zhankai.setOnClickListener(this);
    }


    //handler
    public Handler myHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 200:
                    Glide.with(ZhuanJiaDetailActivity.this).load(img_user_url).centerCrop().placeholder(R.drawable.img_user_tx)
                            .transform(new GlideCircleTransform(ZhuanJiaDetailActivity.this))
                            .diskCacheStrategy(DiskCacheStrategy.SOURCE).into(img_user);
                    tv_name.setText(name);
                    if (fensi != null && !fensi.isEmpty()) {
                        tv_fensi.setText("粉丝数：" + fensi);
                    } else {
                        tv_fensi.setText("粉丝数：0");
                    }
                    tv_fanan.setText(getIntent().getStringExtra("fanan"));
                    tv_ok.setText(ok);
                    tv_beizhu.setText(beizhu);
                    if (list_lishi.size() > 0) {
                        rl_no_date.setVisibility(View.GONE);
                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ZhuanJiaDetailActivity.this);
                        recycler_forecast.setLayoutManager(linearLayoutManager);
                        recycler_forecast.setHasFixedSize(true);
                        recycler_forecast.setAutoLoadMoreEnable(false);
                        forecastAdapter = new ForecastAdapter(ZhuanJiaDetailActivity.this, list_lishi, R.layout.my_forecast_item, qishu);
                        recycler_forecast.setAdapter(forecastAdapter);
                    } else {
                        rl_no_date.setVisibility(View.VISIBLE);
                    }
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ZhuanJiaDetailActivity.this);
                    recycler_now_forecast.setLayoutManager(linearLayoutManager);
                    recycler_now_forecast.setHasFixedSize(true);
                    recycler_now_forecast.setAutoLoadMoreEnable(false);
                    nowForecastAdapter = new NowForecastAdapter(ZhuanJiaDetailActivity.this, list_now, R.layout.my_forecast_item, qishu);
                    recycler_now_forecast.setAdapter(nowForecastAdapter);
                    if (loadingDialog != null) {
                        loadingDialog.cancel();
                    }
                    break;
                case 100:
                    Toast.makeText(ZhuanJiaDetailActivity.this, "增加关注成功!", Toast.LENGTH_LONG).show();
                    if (loadingDialog != null) {
                        loadingDialog.cancel();
                    }
                    break;
                case 300:
                    getData(getIntent().getIntExtra("id", -1));
                    break;
                case 1000:
                    ToastUtil.showToast(ZhuanJiaDetailActivity.this, now_msg + "");
                    if (loadingDialog != null) {
                        loadingDialog.cancel();
                    }
                    break;
                case 2000:
                    Intent intent_yuce_detail = new Intent();
                    intent_yuce_detail.setClass(ZhuanJiaDetailActivity.this, YuCeDetailActivity.class);
                    intent_yuce_detail.putExtra("id", list_now.get(now_position).getId());
                    intent_yuce_detail.putExtra("name", name);
                    intent_yuce_detail.putExtra("fensi", fensi);
                    intent_yuce_detail.putExtra("fanan", fanan);
                    intent_yuce_detail.putExtra("ok", ok);
                    intent_yuce_detail.putExtra("beizhu", beizhu);
                    intent_yuce_detail.putExtra("tx", img_user_url);
                    startActivity(intent_yuce_detail);
                    break;
                case 3000:
                    ToastUtil.showToast(ZhuanJiaDetailActivity.this, "余额不足");
                    break;
                case 4000:
                    Intent intent_yuce_detail1 = new Intent();
                    intent_yuce_detail1.setClass(ZhuanJiaDetailActivity.this, YuCeDetailActivity.class);
                    intent_yuce_detail1.putExtra("id", list_now.get(now_position).getId());
                    intent_yuce_detail1.putExtra("name", name);
                    intent_yuce_detail1.putExtra("fensi", fensi);
                    intent_yuce_detail1.putExtra("fanan", fanan);
                    intent_yuce_detail1.putExtra("ok", ok);
                    intent_yuce_detail1.putExtra("beizhu", beizhu);
                    intent_yuce_detail1.putExtra("tx", img_user_url);
                    startActivity(intent_yuce_detail1);
                    break;
            }
        }
    };

    /**
     * 获取数据
     */
    private void getData(int id) {
        //创建网络处理的对象
        OkHttpUtils
                .post()
                .url("http://www.zse6.com/index.php/mobile/ajax/zhuanjia_info")
                .addParams("id", id + "")
                .build().execute(new StringCallback() {
            @Override
            public void onError(okhttp3.Call call, Exception e, int id) {
                MyToast.getToast(ZhuanJiaDetailActivity.this, "请求失败");
            }

            @Override
            public void onResponse(String response, int id) {
                try {
                    JSONObject jsonObj = new JSONObject(response).getJSONObject("user");
                    JSONObject jsonObj_shuju = new JSONObject(response).getJSONObject("shuju");
                    img_user_url = jsonObj.getString("tx");
                    if (!jsonObj.isNull("name")) {
                        name = jsonObj.getString("name");
                    }
                    if (name.equals("")) {
                        if (!jsonObj.isNull("nicheng")) {
                            name = jsonObj.getString("nicheng");
                        }
                    }
                    if (name.equals("")) {
                        if (!jsonObj.isNull("xingming")) {
                            name = jsonObj.getString("xingming");
                        }
                    }
                    if (!jsonObj.isNull("beizhu")) {
                        beizhu = jsonObj.getString("beizhu");
                    }
                    if (jsonObj_shuju.isNull("fensi")) {
                        fensi = jsonObj_shuju.getString("fensi");
                    }
                    if (jsonObj_shuju.isNull("zhong")) {
                        ok = jsonObj_shuju.getString("zhong");
                    }
                    //
                    JSONObject jsonObj_pk10 = new JSONObject(response).getJSONObject("pk10");
                    JSONArray jsonArray = jsonObj_pk10.getJSONArray("lishi");
                    JSONArray jsonArray_now = jsonObj_pk10.getJSONArray("now");
                    for (int i = 0; i < jsonArray_now.length(); i++) {
                        Now now = new Now();
                        JSONObject o = (JSONObject) jsonArray_now.get(i);
                        now.setId(o.getInt("id"));
                        now.setType(o.getInt("type"));
                        now.setQishu(o.getString("qishu"));
                        now.setYuceinfo(o.getString("yuceinfo"));
                        now.setShijian(o.getLong("shijian"));
                        now.setMoney(o.getLong("money"));
                        now.setWeizhi(o.getInt("weizhi"));
                        now.setQishu_name(o.getString("qishu_name"));
                        now.setUid(o.getString("uid"));
                        now.setIs_ok(o.getInt("is_ok"));
                        now.setNumtype(o.getString("numtype"));
                        now.setTitle(o.getString("title"));
                        now.setIng(o.getInt("ing"));
                        now.setNum1(o.getInt("num1"));
                        now.setNum2(o.getInt("num2"));
                        now.setNum3(o.getInt("num3"));
                        now.setNum4(o.getInt("num4"));
                        now.setNum5(o.getInt("num5"));
                        now.setQishu_last(o.getString("qishu_last"));
                        now.setIs_auto(o.getInt("is_auto"));
                        list_now.add(now);
                    }
                    for (int i = 0; i < jsonArray.length(); i++) {
                        LiShi liShi = new LiShi();
                        JSONObject o = (JSONObject) jsonArray.get(i);
                        liShi.setId(o.getInt("id"));
                        liShi.setType(o.getInt("type"));
                        liShi.setQishu(o.getString("qishu"));
                        liShi.setYuceinfo(o.getString("yuceinfo"));
                        liShi.setShijian(o.getLong("shijian"));
                        liShi.setMoney(o.getLong("money"));
                        liShi.setWeizhi(o.getInt("weizhi"));
                        liShi.setQishu_name(o.getString("qishu_name"));
                        liShi.setUid(o.getString("uid"));
                        liShi.setIs_ok(o.getInt("is_ok"));
                        liShi.setNumtype(o.getString("numtype"));
                        liShi.setTitle(o.getString("title"));
                        liShi.setIng(o.getInt("ing"));
                        liShi.setNum1(o.getInt("num1"));
                        liShi.setNum2(o.getInt("num2"));
                        liShi.setNum3(o.getInt("num3"));
                        liShi.setNum4(o.getInt("num4"));
                        liShi.setNum5(o.getInt("num5"));
                        liShi.setQishu_last(o.getString("qishu_last"));
                        liShi.setIs_auto(o.getInt("is_auto"));
                        list_lishi.add(liShi);
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

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_left:
                ZhuanJiaDetailActivity.this.finish();
                break;
            case R.id.btn_guanzhu:
                //关注
                getFollow(userBean.getId(), getIntent().getIntExtra("id", -1));
                break;
            case R.id.tv_time:

                break;
            case R.id.tv_zhankai:
                tv_beizhu.setMaxLines(100);
                break;
        }
    }

    /**
     * 关注专家
     */
    private void getFollow(int uid, int manid) {
        OkHttpUtils
                .post()
                .url("http://www.zse6.com/index.php/mobile/ajax/guanzhu")
                .addParams("uid", uid + "")
                .addParams("manid", manid + "")
                .build().execute(new StringCallback() {
            @Override
            public void onError(okhttp3.Call call, Exception e, int id) {
                MyToast.getToast(ZhuanJiaDetailActivity.this, "请求失败");
            }

            @Override
            public void onResponse(String response, int id) {
                try {
                    JSONObject json_ing = new JSONObject(response);
                    int ing = json_ing.getInt("ing");
                    if (ing == 1) {
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

    @Override
    public void onItemClick(int position) {
        Intent intent_yuce_detail = new Intent();
        intent_yuce_detail.setClass(ZhuanJiaDetailActivity.this, YuCeDetailActivity.class);
        intent_yuce_detail.putExtra("id", list_lishi.get(position).getId());
        intent_yuce_detail.putExtra("name", name);
        intent_yuce_detail.putExtra("fensi", fensi);
        intent_yuce_detail.putExtra("fanan", fanan);
        intent_yuce_detail.putExtra("ok", ok);
        intent_yuce_detail.putExtra("beizhu", beizhu);
        intent_yuce_detail.putExtra("tx", img_user_url);
        startActivity(intent_yuce_detail);
    }

    @Override
    public void onNowItemClick(final int position) {
        //点击是否需要购买
        now_position = position;
        final AlertDialog.Builder builder = new AlertDialog.Builder(ZhuanJiaDetailActivity.this);
        builder.setTitle("提示信息");
        builder.setMessage("您确定要购买一个预测方案?");
        builder.setNegativeButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                getBuy(userBean.getId(), list_now.get(position).getId());
            }
        });
        builder.setPositiveButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
               builder.create().cancel();
            }
        });
        builder.show();

    }

    //购买一个预测方案接口
    private void getBuy(int uid, int id) {
        OkHttpUtils
                .post()
                .url("http://www.zse6.com/index.php/mobile/ajax/buy")
                .addParams("uid", uid + "")
                .addParams("id", id + "")
                .build().execute(new StringCallback() {
            @Override
            public void onError(okhttp3.Call call, Exception e, int id) {
                MyToast.getToast(ZhuanJiaDetailActivity.this, "请求失败");
            }

            @Override
            public void onResponse(String response, int id) {
                try {
                    JSONObject json_ing = new JSONObject(response);
                    int ing = json_ing.getInt("ing");
                    now_msg = json_ing.getString("msg");
                    if (ing == -2) {
                        Message message = new Message();
                        message.what = 1000;
                        myHandler.sendMessage(message);
                    } else if (ing == 1) {
                        Message message = new Message();
                        message.what = 2000;
                        myHandler.sendMessage(message);
                    } else if (ing == -1) {
                        Message message = new Message();
                        message.what = 3000;
                        myHandler.sendMessage(message);
                    } else if (ing == 0) {
                        Message message = new Message();
                        message.what = 4000;
                        myHandler.sendMessage(message);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
