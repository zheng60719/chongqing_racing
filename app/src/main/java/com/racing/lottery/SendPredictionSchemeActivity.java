package com.racing.lottery;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lottery.R;
import com.racing.Adapter.SendPredictionTypeAdapter;
import com.racing.app.AppSp;
import com.racing.entity.SendNumType;
import com.racing.entity.UserBean;
import com.racing.utils.ToastUtil;
import com.racing.view.LoadingDialog;
import com.racing.view.MyToast;
import com.racing.widget.ListViewDialog;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by k41 on 2017/12/22.
 * 发布预测方案
 */

public class SendPredictionSchemeActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView tv_header_title;
    public TextView tv_left;
    private LoadingDialog loadingDialog;
    //标题，金币数量
    private EditText et_title, et_money_num;
    //发布
    private Button btn_fabu;
    //预测类型
    private int type = 1;
    private int numType = -1;
    //adapter
    private SendPredictionTypeAdapter sendPredictionTypeAdapter;
    //类型选择
    private ListViewDialog listViewDialog_type;
    private TextView tv_type, tv_num_type1, tv_num_type2;
    //类型
    private List<SendNumType> list_type = new ArrayList<>();
    //期数
    private TextView tv_qishu1, tv_qishu2, tv_qishu3;
    //定位号码
    private TextView tv1, tv2, tv3, tv4, tv5, tv6, tv7, tv8, tv9, tv10;
    //预测号码
    private TextView tv_1, tv_2, tv_3, tv_4, tv_5, tv_6, tv_7, tv_8, tv_9, tv_10;

    private Long qishu;
    private boolean isTrue = true;
    //预测期数
    private String yuCeQiShu = null;
    private int a1 = 1, a2 = 1, a3 = 1, a4 = 1, a5 = 1, a6 = 1, a7 = 1, a8 = 1, a9 = 1, a10 = 1;
    private Map<String, String> map = new HashMap<>();
    private AppSp appSp;
    //UserBean
    private UserBean userBean;
    private int weizhi1 = -1;
    private String message = null;
    private LinearLayout ll_number_yuce;
    //大小预测
    private LinearLayout ll_daxiao_yuce;
    //大小
    private TextView tv_daxiao1, tv_daxiao2;
    private String daxiao, danshuang;
    //单双
    private TextView tv_danshuang1, tv_danshuang2;
    //单双预测
    private LinearLayout ll_danshuang_yuce;
    //龙虎预测
    private LinearLayout ll_longhu_yuce, ll_dingweihaoma;
    private TextView tv_longhu1, tv_longhu2;
    private String longhu;
    //龙虎位置
    private LinearLayout ll_longhu_weizhi;
    private TextView tv_longhu_1, tv_longhu_2, tv_longhu_3, tv_longhu_4, tv_longhu_5;

    private String he;

    private LinearLayout ll_chongqi_haoma_weizhi;
    //号码位置
    private TextView chongqi_weizhi1, chongqi_weizhi2, chongqi_weizhi3, chongqi_weizhi4, chongqi_weizhi5;
    private int chongqing_weizhi = -1;
    //龙虎位置
    private LinearLayout ll_chongqi_longhu_weizhi;
    private int longhu_weizhi = -1;
    private TextView chongqi_longhu_weizhi1;
    private int aa = 1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.send_prediction_scheme);
        InitView();
        tv_header_title.setText("发布预测");
        if (loadingDialog == null) {
            loadingDialog = new LoadingDialog(SendPredictionSchemeActivity.this, R.style.LoadingDialog);
        }
        loadingDialog.show();
        //获取期数
        getQishuAndTime();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    flash_work();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        appSp = new AppSp();
        userBean = (UserBean) appSp.getObjectFromShare(SendPredictionSchemeActivity.this, "user");
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
                MyToast.getToast(SendPredictionSchemeActivity.this, "请求失败");
            }

            @Override
            public void onResponse(String response, int id) {
                try {
                    //数据解析
                    JSONObject jsonObj = new JSONObject(response).getJSONObject("next");
                    qishu = jsonObj.getLong("qishus");
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
        et_title = (EditText) findViewById(R.id.et_title);
        et_money_num = (EditText) findViewById(R.id.et_money_num);
        btn_fabu = (Button) findViewById(R.id.btn_fabu);
        btn_fabu.setOnClickListener(this);
        tv_type = (TextView) findViewById(R.id.tv_type);
        tv_type.setOnClickListener(this);
        tv_num_type1 = (TextView) findViewById(R.id.tv_type1);
        tv_num_type2 = (TextView) findViewById(R.id.tv_type2);
        tv_num_type1.setOnClickListener(this);
        tv_num_type2.setOnClickListener(this);
        ll_daxiao_yuce = (LinearLayout) findViewById(R.id.ll_daxiao_yuce);
        tv_qishu1 = (TextView) findViewById(R.id.tv_qishu1);
        tv_qishu2 = (TextView) findViewById(R.id.tv_qishu2);
        tv_qishu3 = (TextView) findViewById(R.id.tv_qishu3);
        ll_number_yuce = (LinearLayout) findViewById(R.id.ll_number_yuce);
        tv_qishu1.setOnClickListener(this);
        tv_qishu2.setOnClickListener(this);
        tv_qishu3.setOnClickListener(this);
        tv1 = (TextView) findViewById(R.id.tv1);
        tv2 = (TextView) findViewById(R.id.tv2);
        tv3 = (TextView) findViewById(R.id.tv3);
        tv4 = (TextView) findViewById(R.id.tv4);
        tv5 = (TextView) findViewById(R.id.tv5);
        tv6 = (TextView) findViewById(R.id.tv6);
        tv7 = (TextView) findViewById(R.id.tv7);
        tv8 = (TextView) findViewById(R.id.tv8);
        tv9 = (TextView) findViewById(R.id.tv9);
        tv10 = (TextView) findViewById(R.id.tv10);
        tv1.setOnClickListener(this);
        tv2.setOnClickListener(this);
        tv3.setOnClickListener(this);
        tv4.setOnClickListener(this);
        tv5.setOnClickListener(this);
        tv6.setOnClickListener(this);
        tv7.setOnClickListener(this);
        tv8.setOnClickListener(this);
        tv9.setOnClickListener(this);
        tv10.setOnClickListener(this);
        tv_1 = (TextView) findViewById(R.id.tv_1);
        tv_2 = (TextView) findViewById(R.id.tv_2);
        tv_3 = (TextView) findViewById(R.id.tv_3);
        tv_4 = (TextView) findViewById(R.id.tv_4);
        tv_5 = (TextView) findViewById(R.id.tv_5);
        tv_6 = (TextView) findViewById(R.id.tv_6);
        tv_7 = (TextView) findViewById(R.id.tv_7);
        tv_8 = (TextView) findViewById(R.id.tv_8);
        tv_9 = (TextView) findViewById(R.id.tv_9);
        tv_10 = (TextView) findViewById(R.id.tv_10);
        tv_1.setOnClickListener(this);
        tv_2.setOnClickListener(this);
        tv_3.setOnClickListener(this);
        tv_4.setOnClickListener(this);
        tv_5.setOnClickListener(this);
        tv_6.setOnClickListener(this);
        tv_7.setOnClickListener(this);
        tv_8.setOnClickListener(this);
        tv_9.setOnClickListener(this);
        tv_10.setOnClickListener(this);
        //大小
        tv_daxiao1 = (TextView) findViewById(R.id.tv_daxiao1);
        tv_daxiao2 = (TextView) findViewById(R.id.tv_daxiao2);
        tv_daxiao1.setOnClickListener(this);
        tv_daxiao2.setOnClickListener(this);
        //单双
        tv_danshuang1 = (TextView) findViewById(R.id.tv_danshuang1);
        tv_danshuang2 = (TextView) findViewById(R.id.tv_danshuang2);
        tv_danshuang1.setOnClickListener(this);
        tv_danshuang2.setOnClickListener(this);
        ll_danshuang_yuce = (LinearLayout) findViewById(R.id.ll_danshuang_yuce);
        //龙虎
        ll_longhu_yuce = (LinearLayout) findViewById(R.id.ll_longhu_yuce);
        tv_longhu1 = (TextView) findViewById(R.id.tv_longhu1);
        tv_longhu2 = (TextView) findViewById(R.id.tv_longhu2);
        tv_longhu1.setOnClickListener(this);
        tv_longhu2.setOnClickListener(this);
        ll_dingweihaoma = (LinearLayout) findViewById(R.id.ll_dingweihaoma);
        //龙虎位置
        ll_longhu_weizhi = (LinearLayout) findViewById(R.id.ll_longhu_weizhi);
        tv_longhu_1 = (TextView) findViewById(R.id.tv_longhu_1);
        tv_longhu_2 = (TextView) findViewById(R.id.tv_longhu_2);
        tv_longhu_3 = (TextView) findViewById(R.id.tv_longhu_3);
        tv_longhu_4 = (TextView) findViewById(R.id.tv_longhu_4);
        tv_longhu_5 = (TextView) findViewById(R.id.tv_longhu_5);
        tv_longhu_1.setOnClickListener(this);
        tv_longhu_2.setOnClickListener(this);
        tv_longhu_3.setOnClickListener(this);
        tv_longhu_4.setOnClickListener(this);
        tv_longhu_5.setOnClickListener(this);
        //号码位置
        ll_chongqi_haoma_weizhi = (LinearLayout) findViewById(R.id.ll_chongqi_haoma_weizhi);
        //号码位置
        chongqi_weizhi1 = (TextView) findViewById(R.id.chongqi_weizhi1);
        chongqi_weizhi1.setOnClickListener(this);
        chongqi_weizhi2 = (TextView) findViewById(R.id.chongqi_weizhi2);
        chongqi_weizhi2.setOnClickListener(this);
        chongqi_weizhi3 = (TextView) findViewById(R.id.chongqi_weizhi3);
        chongqi_weizhi3.setOnClickListener(this);
        chongqi_weizhi4 = (TextView) findViewById(R.id.chongqi_weizhi4);
        chongqi_weizhi4.setOnClickListener(this);
        chongqi_weizhi5 = (TextView) findViewById(R.id.chongqi_weizhi5);
        chongqi_weizhi5.setOnClickListener(this);
        //龙虎位置
        ll_chongqi_longhu_weizhi = (LinearLayout) findViewById(R.id.ll_chongqi_longhu_weizhi);
        chongqi_longhu_weizhi1 = (TextView) findViewById(R.id.chongqi_longhu_weizhi1);
        chongqi_longhu_weizhi1.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_left:
                SendPredictionSchemeActivity.this.finish();
                isTrue = false;
                break;
            case R.id.btn_fabu:
                //发布
                if (type == 1) {
                    Set keys = map.keySet();
                    Iterator iterator = keys.iterator();
                    String da = "(";
                    while (iterator.hasNext()) {
                        Object key = iterator.next();
                        Object value = map.get(key);
                        da += value + ",";
                    }

                    if (et_title.getText().toString().trim() != null && et_title.getText().toString().length() > 0) {
                        if (numType != -1) {
                            if (yuCeQiShu != null) {
                                if (weizhi1 != -1) {
                                    if (da.length() > 1) {
                                        if (et_money_num.getText().toString().trim() != null && et_money_num.getText().toString().trim().length() > 0) {
                                            SendPredictionScheme(userBean.getId(), et_title.getText().toString().trim(), type + "", numType, yuCeQiShu, weizhi1, da + ")", et_money_num.getText().toString().trim());
                                        } else {
                                            ToastUtil.showToast(SendPredictionSchemeActivity.this, "请你输入查看此预测金币数量");
                                        }
                                    } else {
                                        ToastUtil.showToast(SendPredictionSchemeActivity.this, "请你选择预测号码");
                                    }
                                } else {
                                    ToastUtil.showToast(SendPredictionSchemeActivity.this, "请你选择定位号码");
                                }
                            } else {
                                ToastUtil.showToast(SendPredictionSchemeActivity.this, "请你选择预测期数");
                            }
                        } else {
                            ToastUtil.showToast(SendPredictionSchemeActivity.this, "选择号码类型");
                        }
                    } else {
                        ToastUtil.showToast(SendPredictionSchemeActivity.this, "请输入标题");
                    }
                } else if (type == 2) {
                    if (et_title.getText().toString().trim() != null && et_title.getText().toString().length() > 0) {
                        if (numType != -1) {
                            if (yuCeQiShu != null) {
                                if (chongqing_weizhi != -1) {
                                    if (daxiao != null && !daxiao.isEmpty()) {
                                        if (et_money_num.getText().toString().trim() != null && et_money_num.getText().toString().trim().length() > 0) {
                                            SendPredictionSchemeDaXiao(userBean.getId(), et_title.getText().toString().trim(), type + "", numType, yuCeQiShu, chongqing_weizhi, daxiao, et_money_num.getText().toString().trim());
                                        } else {
                                            ToastUtil.showToast(SendPredictionSchemeActivity.this, "请你输入查看此预测金币数量");
                                        }
                                    } else {
                                        ToastUtil.showToast(SendPredictionSchemeActivity.this, "请你选择预测大小");
                                    }
                                } else {
                                    ToastUtil.showToast(SendPredictionSchemeActivity.this, "请你选择号码位置");
                                }
                            } else {
                                ToastUtil.showToast(SendPredictionSchemeActivity.this, "请你选择预测期数");
                            }
                        } else {
                            ToastUtil.showToast(SendPredictionSchemeActivity.this, "选择号码类型");
                        }
                    } else {
                        ToastUtil.showToast(SendPredictionSchemeActivity.this, "请输入标题");
                    }
                } else if (type == 3) {
                    if (et_title.getText().toString().trim() != null && et_title.getText().toString().length() > 0) {
                        if (numType != -1) {
                            if (yuCeQiShu != null) {
                                if (chongqing_weizhi != -1) {
                                    if (danshuang != null && !danshuang.isEmpty()) {
                                        if (et_money_num.getText().toString().trim() != null && et_money_num.getText().toString().trim().length() > 0) {
                                            SendPredictionSchemeDanShuang(userBean.getId(), et_title.getText().toString().trim(), type + "", numType, yuCeQiShu, chongqing_weizhi, danshuang, et_money_num.getText().toString().trim());
                                        } else {
                                            ToastUtil.showToast(SendPredictionSchemeActivity.this, "请你输入查看此预测金币数量");
                                        }
                                    } else {
                                        ToastUtil.showToast(SendPredictionSchemeActivity.this, "请你选择预测大小");
                                    }
                                } else {
                                    ToastUtil.showToast(SendPredictionSchemeActivity.this, "请你选择号码位置");
                                }
                            } else {
                                ToastUtil.showToast(SendPredictionSchemeActivity.this, "请你选择预测期数");
                            }
                        } else {
                            ToastUtil.showToast(SendPredictionSchemeActivity.this, "选择号码类型");
                        }
                    } else {
                        ToastUtil.showToast(SendPredictionSchemeActivity.this, "请输入标题");
                    }
                } else if (type == 4) {
                    if (et_title.getText().toString().trim() != null && et_title.getText().toString().length() > 0) {
                        if (numType != -1) {
                            if (yuCeQiShu != null) {
                                if (longhu_weizhi != -1) {
                                    if (longhu != null && !longhu.isEmpty()) {
                                        if (et_money_num.getText().toString().trim() != null && et_money_num.getText().toString().trim().length() > 0) {
                                            SendPredictionLongHu(userBean.getId(), et_title.getText().toString().trim(), type + "", numType, yuCeQiShu, longhu_weizhi, longhu, et_money_num.getText().toString().trim());
                                        } else {
                                            ToastUtil.showToast(SendPredictionSchemeActivity.this, "请你输入查看此预测金币数量");
                                        }
                                    } else {
                                        ToastUtil.showToast(SendPredictionSchemeActivity.this, "请你选择龙虎位置");
                                    }
                                } else {
                                    ToastUtil.showToast(SendPredictionSchemeActivity.this, "请你选择龙虎位置");
                                }
                            } else {
                                ToastUtil.showToast(SendPredictionSchemeActivity.this, "请你选择预测期数");
                            }
                        } else {
                            ToastUtil.showToast(SendPredictionSchemeActivity.this, "选择号码类型");
                        }
                    } else {
                        ToastUtil.showToast(SendPredictionSchemeActivity.this, "请输入标题");
                    }
                }
                break;
            case R.id.tv_type:
                //选择类型
                if (list_type.size() <= 0) {
                    SendNumType sendNumType = new SendNumType();
                    sendNumType.setNumType(1);
                    sendNumType.setNumTypeName("号码定位胆");
                    list_type.add(sendNumType);

                    SendNumType sendNumType1 = new SendNumType();
                    sendNumType1.setNumType(2);
                    sendNumType1.setNumTypeName("大小系列");
                    list_type.add(sendNumType1);

                    SendNumType sendNumType2 = new SendNumType();
                    sendNumType2.setNumType(3);
                    sendNumType2.setNumTypeName("单双系列");
                    list_type.add(sendNumType2);

                    SendNumType sendNumType3 = new SendNumType();
                    sendNumType3.setNumType(4);
                    sendNumType3.setNumTypeName("龙虎系列");
                    list_type.add(sendNumType3);

                }
                sendPredictionTypeAdapter = new SendPredictionTypeAdapter(SendPredictionSchemeActivity.this, list_type, R.layout.spinner_dropdown_item);
                listViewDialog_type = new ListViewDialog(SendPredictionSchemeActivity.this, "预测类型");
                listViewDialog_type.setListAdapter(sendPredictionTypeAdapter);
                listViewDialog_type.show();
                listViewDialog_type.setOnItemOnclickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        type = list_type.get(position).getNumType();
                        tv_type.setText(list_type.get(position).getNumTypeName());
                        if (type == 1) {
                            ll_number_yuce.setVisibility(View.VISIBLE);
                            ll_dingweihaoma.setVisibility(View.VISIBLE);
                            ll_longhu_weizhi.setVisibility(View.GONE);
                            ll_chongqi_haoma_weizhi.setVisibility(View.GONE);
                            ll_chongqi_longhu_weizhi.setVisibility(View.GONE);
                            ll_longhu_yuce.setVisibility(View.GONE);
                        } else if (type == 2) {
                            ll_daxiao_yuce.setVisibility(View.VISIBLE);
                            ll_number_yuce.setVisibility(View.GONE);
                            ll_dingweihaoma.setVisibility(View.GONE);
                            ll_longhu_weizhi.setVisibility(View.GONE);
                            ll_chongqi_haoma_weizhi.setVisibility(View.VISIBLE);
                            ll_number_yuce.setVisibility(View.GONE);
                            ll_chongqi_longhu_weizhi.setVisibility(View.GONE);
                            ll_longhu_yuce.setVisibility(View.GONE);
                        } else if (type == 3) {
                            ll_danshuang_yuce.setVisibility(View.VISIBLE);
                            ll_number_yuce.setVisibility(View.GONE);
                            ll_dingweihaoma.setVisibility(View.GONE);
                            ll_longhu_weizhi.setVisibility(View.GONE);
                            ll_daxiao_yuce.setVisibility(View.GONE);
                            ll_chongqi_haoma_weizhi.setVisibility(View.VISIBLE);
                            ll_chongqi_longhu_weizhi.setVisibility(View.GONE);
                            ll_longhu_yuce.setVisibility(View.GONE);
                        } else if (type == 4) {
                            ll_daxiao_yuce.setVisibility(View.GONE);
                            ll_number_yuce.setVisibility(View.GONE);
                            ll_dingweihaoma.setVisibility(View.GONE);
                            ll_longhu_weizhi.setVisibility(View.GONE);
                            ll_chongqi_haoma_weizhi.setVisibility(View.GONE);
                            ll_chongqi_longhu_weizhi.setVisibility(View.VISIBLE);
                            ll_longhu_yuce.setVisibility(View.VISIBLE);
                        }
                        listViewDialog_type.dismiss();
                    }
                });
                break;
            case R.id.tv_longhu_1:
                weizhi1 = 1;
                tv_longhu_1.setBackgroundResource(R.drawable.btn_number_type1);
                tv_longhu_1.setTextColor(Color.parseColor("#FF0000"));
                tv_longhu_2.setBackgroundResource(R.drawable.btn_number_type);
                tv_longhu_2.setTextColor(Color.parseColor("#000000"));
                tv_longhu_3.setBackgroundResource(R.drawable.btn_number_type);
                tv_longhu_3.setTextColor(Color.parseColor("#000000"));
                tv_longhu_4.setBackgroundResource(R.drawable.btn_number_type);
                tv_longhu_4.setTextColor(Color.parseColor("#000000"));
                tv_longhu_5.setBackgroundResource(R.drawable.btn_number_type);
                tv_longhu_5.setTextColor(Color.parseColor("#000000"));
                break;
            case R.id.tv_longhu_2:
                weizhi1 = 2;
                tv_longhu_2.setBackgroundResource(R.drawable.btn_number_type1);
                tv_longhu_2.setTextColor(Color.parseColor("#FF0000"));
                tv_longhu_1.setBackgroundResource(R.drawable.btn_number_type);
                tv_longhu_1.setTextColor(Color.parseColor("#000000"));
                tv_longhu_3.setBackgroundResource(R.drawable.btn_number_type);
                tv_longhu_3.setTextColor(Color.parseColor("#000000"));
                tv_longhu_4.setBackgroundResource(R.drawable.btn_number_type);
                tv_longhu_4.setTextColor(Color.parseColor("#000000"));
                tv_longhu_5.setBackgroundResource(R.drawable.btn_number_type);
                tv_longhu_5.setTextColor(Color.parseColor("#000000"));
                break;
            case R.id.tv_longhu_3:
                weizhi1 = 3;
                tv_longhu_3.setBackgroundResource(R.drawable.btn_number_type1);
                tv_longhu_3.setTextColor(Color.parseColor("#FF0000"));
                tv_longhu_1.setBackgroundResource(R.drawable.btn_number_type);
                tv_longhu_1.setTextColor(Color.parseColor("#000000"));
                tv_longhu_2.setBackgroundResource(R.drawable.btn_number_type);
                tv_longhu_2.setTextColor(Color.parseColor("#000000"));
                tv_longhu_4.setBackgroundResource(R.drawable.btn_number_type);
                tv_longhu_4.setTextColor(Color.parseColor("#000000"));
                tv_longhu_5.setBackgroundResource(R.drawable.btn_number_type);
                tv_longhu_5.setTextColor(Color.parseColor("#000000"));
                break;
            case R.id.tv_longhu_4:
                weizhi1 = 4;
                tv_longhu_4.setBackgroundResource(R.drawable.btn_number_type1);
                tv_longhu_4.setTextColor(Color.parseColor("#FF0000"));
                tv_longhu_1.setBackgroundResource(R.drawable.btn_number_type);
                tv_longhu_1.setTextColor(Color.parseColor("#000000"));
                tv_longhu_2.setBackgroundResource(R.drawable.btn_number_type);
                tv_longhu_2.setTextColor(Color.parseColor("#000000"));
                tv_longhu_3.setBackgroundResource(R.drawable.btn_number_type);
                tv_longhu_3.setTextColor(Color.parseColor("#000000"));
                tv_longhu_5.setBackgroundResource(R.drawable.btn_number_type);
                tv_longhu_5.setTextColor(Color.parseColor("#000000"));
                break;

            case R.id.tv_longhu_5:
                weizhi1 = 5;
                tv_longhu_5.setBackgroundResource(R.drawable.btn_number_type1);
                tv_longhu_5.setTextColor(Color.parseColor("#FF0000"));
                tv_longhu_1.setBackgroundResource(R.drawable.btn_number_type);
                tv_longhu_1.setTextColor(Color.parseColor("#000000"));
                tv_longhu_2.setBackgroundResource(R.drawable.btn_number_type);
                tv_longhu_2.setTextColor(Color.parseColor("#000000"));
                tv_longhu_3.setBackgroundResource(R.drawable.btn_number_type);
                tv_longhu_3.setTextColor(Color.parseColor("#000000"));
                tv_longhu_4.setBackgroundResource(R.drawable.btn_number_type);
                tv_longhu_4.setTextColor(Color.parseColor("#000000"));
                break;
            case R.id.tv_type1:
                numType = 1;
                tv_num_type1.setBackgroundResource(R.drawable.btn_number_type1);
                tv_num_type1.setTextColor(Color.parseColor("#FF0000"));
                tv_num_type2.setBackgroundResource(R.drawable.btn_number_type);
                tv_num_type2.setTextColor(Color.parseColor("#000000"));
                break;
            case R.id.tv_type2:
                numType = 0;
                tv_num_type2.setBackgroundResource(R.drawable.btn_number_type1);
                tv_num_type2.setTextColor(Color.parseColor("#FF0000"));
                tv_num_type1.setBackgroundResource(R.drawable.btn_number_type);
                tv_num_type1.setTextColor(Color.parseColor("#000000"));
                break;
            case R.id.tv_qishu1:
                //期数1
                yuCeQiShu = tv_qishu1.getText().toString();
                tv_qishu1.setBackgroundResource(R.drawable.btn_number_type1);
                tv_qishu1.setTextColor(Color.parseColor("#FF0000"));
                tv_qishu2.setBackgroundResource(R.drawable.btn_number_type);
                tv_qishu2.setTextColor(Color.parseColor("#000000"));
                tv_qishu3.setBackgroundResource(R.drawable.btn_number_type);
                tv_qishu3.setTextColor(Color.parseColor("#000000"));
                break;
            case R.id.tv_qishu2:
                //期数2
                yuCeQiShu = tv_qishu2.getText().toString();
                tv_qishu2.setBackgroundResource(R.drawable.btn_number_type1);
                tv_qishu2.setTextColor(Color.parseColor("#FF0000"));
                tv_qishu1.setBackgroundResource(R.drawable.btn_number_type);
                tv_qishu1.setTextColor(Color.parseColor("#000000"));
                tv_qishu3.setBackgroundResource(R.drawable.btn_number_type);
                tv_qishu3.setTextColor(Color.parseColor("#000000"));
                break;
            case R.id.tv_qishu3:
                //期数3
                yuCeQiShu = tv_qishu3.getText().toString();
                tv_qishu3.setBackgroundResource(R.drawable.btn_number_type1);
                tv_qishu3.setTextColor(Color.parseColor("#FF0000"));
                tv_qishu2.setBackgroundResource(R.drawable.btn_number_type);
                tv_qishu2.setTextColor(Color.parseColor("#000000"));
                tv_qishu1.setBackgroundResource(R.drawable.btn_number_type);
                tv_qishu1.setTextColor(Color.parseColor("#000000"));
                break;
            //---------定位号码------------
            case R.id.tv1:
                weizhi1 = 1;
                tv1.setBackgroundResource(R.drawable.btn_number_type1);
                tv1.setTextColor(Color.parseColor("#FF0000"));
                tv2.setBackgroundResource(R.drawable.btn_number_type);
                tv2.setTextColor(Color.parseColor("#000000"));
                tv3.setBackgroundResource(R.drawable.btn_number_type);
                tv3.setTextColor(Color.parseColor("#000000"));
                tv4.setBackgroundResource(R.drawable.btn_number_type);
                tv4.setTextColor(Color.parseColor("#000000"));
                tv5.setBackgroundResource(R.drawable.btn_number_type);
                tv5.setTextColor(Color.parseColor("#000000"));
                tv6.setBackgroundResource(R.drawable.btn_number_type);
                tv6.setTextColor(Color.parseColor("#000000"));
                tv7.setBackgroundResource(R.drawable.btn_number_type);
                tv7.setTextColor(Color.parseColor("#000000"));
                tv8.setBackgroundResource(R.drawable.btn_number_type);
                tv8.setTextColor(Color.parseColor("#000000"));
                tv9.setBackgroundResource(R.drawable.btn_number_type);
                tv9.setTextColor(Color.parseColor("#000000"));
                tv10.setBackgroundResource(R.drawable.btn_number_type);
                tv10.setTextColor(Color.parseColor("#000000"));
                break;
            case R.id.tv2:
                weizhi1 = 2;
                tv2.setBackgroundResource(R.drawable.btn_number_type1);
                tv2.setTextColor(Color.parseColor("#FF0000"));
                tv1.setBackgroundResource(R.drawable.btn_number_type);
                tv1.setTextColor(Color.parseColor("#000000"));
                tv3.setBackgroundResource(R.drawable.btn_number_type);
                tv3.setTextColor(Color.parseColor("#000000"));
                tv4.setBackgroundResource(R.drawable.btn_number_type);
                tv4.setTextColor(Color.parseColor("#000000"));
                tv5.setBackgroundResource(R.drawable.btn_number_type);
                tv5.setTextColor(Color.parseColor("#000000"));
                tv6.setBackgroundResource(R.drawable.btn_number_type);
                tv6.setTextColor(Color.parseColor("#000000"));
                tv7.setBackgroundResource(R.drawable.btn_number_type);
                tv7.setTextColor(Color.parseColor("#000000"));
                tv8.setBackgroundResource(R.drawable.btn_number_type);
                tv8.setTextColor(Color.parseColor("#000000"));
                tv9.setBackgroundResource(R.drawable.btn_number_type);
                tv9.setTextColor(Color.parseColor("#000000"));
                tv10.setBackgroundResource(R.drawable.btn_number_type);
                tv10.setTextColor(Color.parseColor("#000000"));
                break;
            case R.id.tv3:
                weizhi1 = 3;
                tv3.setBackgroundResource(R.drawable.btn_number_type1);
                tv3.setTextColor(Color.parseColor("#FF0000"));
                tv2.setBackgroundResource(R.drawable.btn_number_type);
                tv2.setTextColor(Color.parseColor("#000000"));
                tv1.setBackgroundResource(R.drawable.btn_number_type);
                tv1.setTextColor(Color.parseColor("#000000"));
                tv4.setBackgroundResource(R.drawable.btn_number_type);
                tv4.setTextColor(Color.parseColor("#000000"));
                tv5.setBackgroundResource(R.drawable.btn_number_type);
                tv5.setTextColor(Color.parseColor("#000000"));
                tv6.setBackgroundResource(R.drawable.btn_number_type);
                tv6.setTextColor(Color.parseColor("#000000"));
                tv7.setBackgroundResource(R.drawable.btn_number_type);
                tv7.setTextColor(Color.parseColor("#000000"));
                tv8.setBackgroundResource(R.drawable.btn_number_type);
                tv8.setTextColor(Color.parseColor("#000000"));
                tv9.setBackgroundResource(R.drawable.btn_number_type);
                tv9.setTextColor(Color.parseColor("#000000"));
                tv10.setBackgroundResource(R.drawable.btn_number_type);
                tv10.setTextColor(Color.parseColor("#000000"));
                break;
            case R.id.tv4:
                weizhi1 = 4;
                tv4.setBackgroundResource(R.drawable.btn_number_type1);
                tv4.setTextColor(Color.parseColor("#FF0000"));
                tv2.setBackgroundResource(R.drawable.btn_number_type);
                tv2.setTextColor(Color.parseColor("#000000"));
                tv3.setBackgroundResource(R.drawable.btn_number_type);
                tv3.setTextColor(Color.parseColor("#000000"));
                tv1.setBackgroundResource(R.drawable.btn_number_type);
                tv1.setTextColor(Color.parseColor("#000000"));
                tv5.setBackgroundResource(R.drawable.btn_number_type);
                tv5.setTextColor(Color.parseColor("#000000"));
                tv6.setBackgroundResource(R.drawable.btn_number_type);
                tv6.setTextColor(Color.parseColor("#000000"));
                tv7.setBackgroundResource(R.drawable.btn_number_type);
                tv7.setTextColor(Color.parseColor("#000000"));
                tv8.setBackgroundResource(R.drawable.btn_number_type);
                tv8.setTextColor(Color.parseColor("#000000"));
                tv9.setBackgroundResource(R.drawable.btn_number_type);
                tv9.setTextColor(Color.parseColor("#000000"));
                tv10.setBackgroundResource(R.drawable.btn_number_type);
                tv10.setTextColor(Color.parseColor("#000000"));
                break;
            case R.id.tv5:
                weizhi1 = 5;
                tv5.setBackgroundResource(R.drawable.btn_number_type1);
                tv5.setTextColor(Color.parseColor("#FF0000"));
                tv2.setBackgroundResource(R.drawable.btn_number_type);
                tv2.setTextColor(Color.parseColor("#000000"));
                tv3.setBackgroundResource(R.drawable.btn_number_type);
                tv3.setTextColor(Color.parseColor("#000000"));
                tv4.setBackgroundResource(R.drawable.btn_number_type);
                tv4.setTextColor(Color.parseColor("#000000"));
                tv1.setBackgroundResource(R.drawable.btn_number_type);
                tv1.setTextColor(Color.parseColor("#000000"));
                tv6.setBackgroundResource(R.drawable.btn_number_type);
                tv6.setTextColor(Color.parseColor("#000000"));
                tv7.setBackgroundResource(R.drawable.btn_number_type);
                tv7.setTextColor(Color.parseColor("#000000"));
                tv8.setBackgroundResource(R.drawable.btn_number_type);
                tv8.setTextColor(Color.parseColor("#000000"));
                tv9.setBackgroundResource(R.drawable.btn_number_type);
                tv9.setTextColor(Color.parseColor("#000000"));
                tv10.setBackgroundResource(R.drawable.btn_number_type);
                tv10.setTextColor(Color.parseColor("#000000"));
                break;
            case R.id.tv6:
                weizhi1 = 6;
                tv6.setBackgroundResource(R.drawable.btn_number_type1);
                tv6.setTextColor(Color.parseColor("#FF0000"));
                tv2.setBackgroundResource(R.drawable.btn_number_type);
                tv2.setTextColor(Color.parseColor("#000000"));
                tv3.setBackgroundResource(R.drawable.btn_number_type);
                tv3.setTextColor(Color.parseColor("#000000"));
                tv4.setBackgroundResource(R.drawable.btn_number_type);
                tv4.setTextColor(Color.parseColor("#000000"));
                tv5.setBackgroundResource(R.drawable.btn_number_type);
                tv5.setTextColor(Color.parseColor("#000000"));
                tv1.setBackgroundResource(R.drawable.btn_number_type);
                tv1.setTextColor(Color.parseColor("#000000"));
                tv7.setBackgroundResource(R.drawable.btn_number_type);
                tv7.setTextColor(Color.parseColor("#000000"));
                tv8.setBackgroundResource(R.drawable.btn_number_type);
                tv8.setTextColor(Color.parseColor("#000000"));
                tv9.setBackgroundResource(R.drawable.btn_number_type);
                tv9.setTextColor(Color.parseColor("#000000"));
                tv10.setBackgroundResource(R.drawable.btn_number_type);
                tv10.setTextColor(Color.parseColor("#000000"));
                break;
            case R.id.tv7:
                weizhi1 = 7;
                tv7.setBackgroundResource(R.drawable.btn_number_type1);
                tv7.setTextColor(Color.parseColor("#FF0000"));
                tv2.setBackgroundResource(R.drawable.btn_number_type);
                tv2.setTextColor(Color.parseColor("#000000"));
                tv3.setBackgroundResource(R.drawable.btn_number_type);
                tv3.setTextColor(Color.parseColor("#000000"));
                tv4.setBackgroundResource(R.drawable.btn_number_type);
                tv4.setTextColor(Color.parseColor("#000000"));
                tv5.setBackgroundResource(R.drawable.btn_number_type);
                tv5.setTextColor(Color.parseColor("#000000"));
                tv6.setBackgroundResource(R.drawable.btn_number_type);
                tv6.setTextColor(Color.parseColor("#000000"));
                tv1.setBackgroundResource(R.drawable.btn_number_type);
                tv1.setTextColor(Color.parseColor("#000000"));
                tv8.setBackgroundResource(R.drawable.btn_number_type);
                tv8.setTextColor(Color.parseColor("#000000"));
                tv9.setBackgroundResource(R.drawable.btn_number_type);
                tv9.setTextColor(Color.parseColor("#000000"));
                tv10.setBackgroundResource(R.drawable.btn_number_type);
                tv10.setTextColor(Color.parseColor("#000000"));
                break;
            case R.id.tv8:
                weizhi1 = 8;
                tv8.setBackgroundResource(R.drawable.btn_number_type1);
                tv8.setTextColor(Color.parseColor("#FF0000"));
                tv2.setBackgroundResource(R.drawable.btn_number_type);
                tv2.setTextColor(Color.parseColor("#000000"));
                tv3.setBackgroundResource(R.drawable.btn_number_type);
                tv3.setTextColor(Color.parseColor("#000000"));
                tv4.setBackgroundResource(R.drawable.btn_number_type);
                tv4.setTextColor(Color.parseColor("#000000"));
                tv5.setBackgroundResource(R.drawable.btn_number_type);
                tv5.setTextColor(Color.parseColor("#000000"));
                tv6.setBackgroundResource(R.drawable.btn_number_type);
                tv6.setTextColor(Color.parseColor("#000000"));
                tv7.setBackgroundResource(R.drawable.btn_number_type);
                tv7.setTextColor(Color.parseColor("#000000"));
                tv1.setBackgroundResource(R.drawable.btn_number_type);
                tv1.setTextColor(Color.parseColor("#000000"));
                tv9.setBackgroundResource(R.drawable.btn_number_type);
                tv9.setTextColor(Color.parseColor("#000000"));
                tv10.setBackgroundResource(R.drawable.btn_number_type);
                tv10.setTextColor(Color.parseColor("#000000"));
                break;
            case R.id.tv9:
                weizhi1 = 9;
                tv9.setBackgroundResource(R.drawable.btn_number_type1);
                tv9.setTextColor(Color.parseColor("#FF0000"));
                tv2.setBackgroundResource(R.drawable.btn_number_type);
                tv2.setTextColor(Color.parseColor("#000000"));
                tv3.setBackgroundResource(R.drawable.btn_number_type);
                tv3.setTextColor(Color.parseColor("#000000"));
                tv4.setBackgroundResource(R.drawable.btn_number_type);
                tv4.setTextColor(Color.parseColor("#000000"));
                tv5.setBackgroundResource(R.drawable.btn_number_type);
                tv5.setTextColor(Color.parseColor("#000000"));
                tv6.setBackgroundResource(R.drawable.btn_number_type);
                tv6.setTextColor(Color.parseColor("#000000"));
                tv7.setBackgroundResource(R.drawable.btn_number_type);
                tv7.setTextColor(Color.parseColor("#000000"));
                tv8.setBackgroundResource(R.drawable.btn_number_type);
                tv8.setTextColor(Color.parseColor("#000000"));
                tv1.setBackgroundResource(R.drawable.btn_number_type);
                tv1.setTextColor(Color.parseColor("#000000"));
                tv10.setBackgroundResource(R.drawable.btn_number_type);
                tv10.setTextColor(Color.parseColor("#000000"));
                break;
            case R.id.tv10:
                weizhi1 = 10;
                tv10.setBackgroundResource(R.drawable.btn_number_type1);
                tv10.setTextColor(Color.parseColor("#FF0000"));
                tv2.setBackgroundResource(R.drawable.btn_number_type);
                tv2.setTextColor(Color.parseColor("#000000"));
                tv3.setBackgroundResource(R.drawable.btn_number_type);
                tv3.setTextColor(Color.parseColor("#000000"));
                tv4.setBackgroundResource(R.drawable.btn_number_type);
                tv4.setTextColor(Color.parseColor("#000000"));
                tv5.setBackgroundResource(R.drawable.btn_number_type);
                tv5.setTextColor(Color.parseColor("#000000"));
                tv6.setBackgroundResource(R.drawable.btn_number_type);
                tv6.setTextColor(Color.parseColor("#000000"));
                tv7.setBackgroundResource(R.drawable.btn_number_type);
                tv7.setTextColor(Color.parseColor("#000000"));
                tv8.setBackgroundResource(R.drawable.btn_number_type);
                tv8.setTextColor(Color.parseColor("#000000"));
                tv9.setBackgroundResource(R.drawable.btn_number_type);
                tv9.setTextColor(Color.parseColor("#000000"));
                tv1.setBackgroundResource(R.drawable.btn_number_type);
                tv1.setTextColor(Color.parseColor("#000000"));
                break;
            //---------预测号码------------
            case R.id.tv_1:
                if (map.size() < 5) {
                    if (a1 == 1) {
                        map.put("1", "01");
                        tv_1.setBackgroundResource(R.drawable.btn_number_type1);
                        tv_1.setTextColor(Color.parseColor("#FF0000"));
                        a1 = 2;
                    } else if (a1 == 2) {
                        map.remove("1");
                        tv_1.setBackgroundResource(R.drawable.btn_number_type);
                        tv_1.setTextColor(Color.parseColor("#000000"));
                        a1 = 1;
                    }
                } else {
                    ToastUtil.showToast(SendPredictionSchemeActivity.this, "最多选择5个");
                }
                break;
            case R.id.tv_2:
                if (map.size() < 5) {
                    if (a2 == 1) {
                        map.put("2", "02");
                        tv_2.setBackgroundResource(R.drawable.btn_number_type1);
                        tv_2.setTextColor(Color.parseColor("#FF0000"));
                        a2 = 2;
                    } else if (a2 == 2) {
                        map.remove("2");
                        tv_2.setBackgroundResource(R.drawable.btn_number_type);
                        tv_2.setTextColor(Color.parseColor("#000000"));
                        a2 = 1;
                    }
                } else {
                    ToastUtil.showToast(SendPredictionSchemeActivity.this, "最多选择5个");
                }
                break;
            case R.id.tv_3:
                if (map.size() < 5) {
                    if (a3 == 1) {
                        map.put("3", "03");
                        tv_3.setBackgroundResource(R.drawable.btn_number_type1);
                        tv_3.setTextColor(Color.parseColor("#FF0000"));
                        a3 = 2;
                    } else if (a3 == 2) {
                        map.remove("3");
                        tv_3.setBackgroundResource(R.drawable.btn_number_type);
                        tv_3.setTextColor(Color.parseColor("#000000"));
                        a3 = 1;
                    }
                } else {
                    ToastUtil.showToast(SendPredictionSchemeActivity.this, "最多选择5个");
                }
                break;
            case R.id.tv_4:
                if (map.size() < 5) {
                    if (a4 == 1) {
                        map.put("4", "04");
                        tv_4.setBackgroundResource(R.drawable.btn_number_type1);
                        tv_4.setTextColor(Color.parseColor("#FF0000"));
                        a4 = 2;
                    } else if (a4 == 2) {
                        map.remove("4");
                        tv_4.setBackgroundResource(R.drawable.btn_number_type);
                        tv_4.setTextColor(Color.parseColor("#000000"));
                        a4 = 1;
                    }
                } else {
                    ToastUtil.showToast(SendPredictionSchemeActivity.this, "最多选择5个");
                }
                break;
            case R.id.tv_5:
                if (map.size() < 5) {
                    if (a5 == 1) {
                        map.put("5", "05");
                        tv_5.setBackgroundResource(R.drawable.btn_number_type1);
                        tv_5.setTextColor(Color.parseColor("#FF0000"));
                        a5 = 2;
                    } else if (a5 == 2) {
                        map.remove("5");
                        tv_5.setBackgroundResource(R.drawable.btn_number_type);
                        tv_5.setTextColor(Color.parseColor("#000000"));
                        a5 = 1;
                    }
                } else {
                    ToastUtil.showToast(SendPredictionSchemeActivity.this, "最多选择5个");
                }
                break;
            case R.id.tv_6:
                if (map.size() < 5) {
                    if (a6 == 1) {
                        map.put("6", "06");
                        tv_6.setBackgroundResource(R.drawable.btn_number_type1);
                        tv_6.setTextColor(Color.parseColor("#FF0000"));
                        a6 = 2;
                    } else if (a6 == 2) {
                        map.remove("6");
                        tv_6.setBackgroundResource(R.drawable.btn_number_type);
                        tv_6.setTextColor(Color.parseColor("#000000"));
                        a6 = 1;
                    }
                } else {
                    ToastUtil.showToast(SendPredictionSchemeActivity.this, "最多选择5个");
                }
                break;
            case R.id.tv_7:
                if (map.size() < 5) {
                    if (a7 == 1) {
                        map.put("7", "07");
                        tv_7.setBackgroundResource(R.drawable.btn_number_type1);
                        tv_7.setTextColor(Color.parseColor("#FF0000"));
                        a7 = 2;
                    } else if (a7 == 2) {
                        map.remove("7");
                        tv_7.setBackgroundResource(R.drawable.btn_number_type);
                        tv_7.setTextColor(Color.parseColor("#000000"));
                        a7 = 1;
                    }
                } else {
                    ToastUtil.showToast(SendPredictionSchemeActivity.this, "最多选择5个");
                }
                break;
            case R.id.tv_8:
                if (map.size() < 5) {
                    if (a8 == 1) {
                        map.put("8", "08");
                        tv_8.setBackgroundResource(R.drawable.btn_number_type1);
                        tv_8.setTextColor(Color.parseColor("#FF0000"));
                        a8 = 2;
                    } else if (a8 == 2) {
                        map.remove("8");
                        tv_8.setBackgroundResource(R.drawable.btn_number_type);
                        tv_8.setTextColor(Color.parseColor("#000000"));
                        a8 = 1;
                    }
                } else {
                    ToastUtil.showToast(SendPredictionSchemeActivity.this, "最多选择5个");
                }
                break;
            case R.id.tv_9:
                if (map.size() < 5) {
                    if (a9 == 1) {
                        map.put("9", "09");
                        tv_9.setBackgroundResource(R.drawable.btn_number_type1);
                        tv_9.setTextColor(Color.parseColor("#FF0000"));
                        a9 = 2;
                    } else if (a9 == 2) {
                        map.remove("9");
                        tv_9.setBackgroundResource(R.drawable.btn_number_type);
                        tv_9.setTextColor(Color.parseColor("#000000"));
                        a9 = 1;
                    }
                } else {
                    ToastUtil.showToast(SendPredictionSchemeActivity.this, "最多选择5个");
                }
                break;
            case R.id.tv_10:
                if (map.size() < 5) {
                    if (a10 == 1) {
                        map.put("10", "10");
                        tv_10.setBackgroundResource(R.drawable.btn_number_type1);
                        tv_10.setTextColor(Color.parseColor("#FF0000"));
                        a10 = 2;
                    } else if (a1 == 2) {
                        map.remove("10");
                        tv_10.setBackgroundResource(R.drawable.btn_number_type);
                        tv_10.setTextColor(Color.parseColor("#000000"));
                        a10 = 1;
                    }
                } else {
                    ToastUtil.showToast(SendPredictionSchemeActivity.this, "最多选择5个");
                }
                break;
            case R.id.tv_daxiao1:
                daxiao = "大";
                tv_daxiao1.setBackgroundResource(R.drawable.btn_number_type1);
                tv_daxiao1.setTextColor(Color.parseColor("#FF0000"));
                tv_daxiao2.setBackgroundResource(R.drawable.btn_number_type);
                tv_daxiao2.setTextColor(Color.parseColor("#000000"));
                break;
            case R.id.tv_daxiao2:
                daxiao = "小";
                tv_daxiao2.setBackgroundResource(R.drawable.btn_number_type1);
                tv_daxiao2.setTextColor(Color.parseColor("#FF0000"));
                tv_daxiao1.setBackgroundResource(R.drawable.btn_number_type);
                tv_daxiao1.setTextColor(Color.parseColor("#000000"));
                break;
            case R.id.tv_danshuang1:
                danshuang = "单";
                tv_danshuang1.setBackgroundResource(R.drawable.btn_number_type1);
                tv_danshuang1.setTextColor(Color.parseColor("#FF0000"));
                tv_danshuang2.setBackgroundResource(R.drawable.btn_number_type);
                tv_danshuang2.setTextColor(Color.parseColor("#000000"));
                break;
            case R.id.tv_danshuang2:
                danshuang = "双";
                tv_danshuang2.setBackgroundResource(R.drawable.btn_number_type1);
                tv_danshuang2.setTextColor(Color.parseColor("#FF0000"));
                tv_danshuang1.setBackgroundResource(R.drawable.btn_number_type);
                tv_danshuang1.setTextColor(Color.parseColor("#000000"));
                break;
            case R.id.tv_longhu1:
                longhu = "龙";
                tv_longhu1.setBackgroundResource(R.drawable.btn_number_type1);
                tv_longhu1.setTextColor(Color.parseColor("#FF0000"));
                tv_longhu2.setBackgroundResource(R.drawable.btn_number_type);
                tv_longhu2.setTextColor(Color.parseColor("#000000"));
                break;
            case R.id.tv_longhu2:
                longhu = "虎";
                tv_longhu2.setBackgroundResource(R.drawable.btn_number_type1);
                tv_longhu2.setTextColor(Color.parseColor("#FF0000"));
                tv_longhu1.setBackgroundResource(R.drawable.btn_number_type);
                tv_longhu1.setTextColor(Color.parseColor("#000000"));
                break;
            //号码位置
            case R.id.chongqi_weizhi1:
                chongqing_weizhi = 1;
                chongqi_weizhi1.setBackgroundResource(R.drawable.btn_number_type1);
                chongqi_weizhi1.setTextColor(Color.parseColor("#FF0000"));
                chongqi_weizhi2.setBackgroundResource(R.drawable.btn_number_type);
                chongqi_weizhi2.setTextColor(Color.parseColor("#000000"));
                chongqi_weizhi3.setBackgroundResource(R.drawable.btn_number_type);
                chongqi_weizhi3.setTextColor(Color.parseColor("#000000"));
                chongqi_weizhi4.setBackgroundResource(R.drawable.btn_number_type);
                chongqi_weizhi4.setTextColor(Color.parseColor("#000000"));
                chongqi_weizhi5.setBackgroundResource(R.drawable.btn_number_type);
                chongqi_weizhi5.setTextColor(Color.parseColor("#000000"));
                break;
            case R.id.chongqi_weizhi2:
                chongqing_weizhi = 2;
                chongqi_weizhi2.setBackgroundResource(R.drawable.btn_number_type1);
                chongqi_weizhi2.setTextColor(Color.parseColor("#FF0000"));
                chongqi_weizhi1.setBackgroundResource(R.drawable.btn_number_type);
                chongqi_weizhi1.setTextColor(Color.parseColor("#000000"));
                chongqi_weizhi3.setBackgroundResource(R.drawable.btn_number_type);
                chongqi_weizhi3.setTextColor(Color.parseColor("#000000"));
                chongqi_weizhi4.setBackgroundResource(R.drawable.btn_number_type);
                chongqi_weizhi4.setTextColor(Color.parseColor("#000000"));
                chongqi_weizhi5.setBackgroundResource(R.drawable.btn_number_type);
                chongqi_weizhi5.setTextColor(Color.parseColor("#000000"));
                break;
            case R.id.chongqi_weizhi3:
                chongqing_weizhi = 3;
                chongqi_weizhi3.setBackgroundResource(R.drawable.btn_number_type1);
                chongqi_weizhi3.setTextColor(Color.parseColor("#FF0000"));
                chongqi_weizhi2.setBackgroundResource(R.drawable.btn_number_type);
                chongqi_weizhi2.setTextColor(Color.parseColor("#000000"));
                chongqi_weizhi1.setBackgroundResource(R.drawable.btn_number_type);
                chongqi_weizhi1.setTextColor(Color.parseColor("#000000"));
                chongqi_weizhi4.setBackgroundResource(R.drawable.btn_number_type);
                chongqi_weizhi4.setTextColor(Color.parseColor("#000000"));
                chongqi_weizhi5.setBackgroundResource(R.drawable.btn_number_type);
                chongqi_weizhi5.setTextColor(Color.parseColor("#000000"));
                break;
            case R.id.chongqi_weizhi4:
                chongqing_weizhi = 4;
                chongqi_weizhi4.setBackgroundResource(R.drawable.btn_number_type1);
                chongqi_weizhi4.setTextColor(Color.parseColor("#FF0000"));
                chongqi_weizhi2.setBackgroundResource(R.drawable.btn_number_type);
                chongqi_weizhi2.setTextColor(Color.parseColor("#000000"));
                chongqi_weizhi3.setBackgroundResource(R.drawable.btn_number_type);
                chongqi_weizhi3.setTextColor(Color.parseColor("#000000"));
                chongqi_weizhi1.setBackgroundResource(R.drawable.btn_number_type);
                chongqi_weizhi1.setTextColor(Color.parseColor("#000000"));
                chongqi_weizhi5.setBackgroundResource(R.drawable.btn_number_type);
                chongqi_weizhi5.setTextColor(Color.parseColor("#000000"));
                break;
            case R.id.chongqi_weizhi5:
                chongqing_weizhi = 5;
                chongqi_weizhi5.setBackgroundResource(R.drawable.btn_number_type1);
                chongqi_weizhi5.setTextColor(Color.parseColor("#FF0000"));
                chongqi_weizhi2.setBackgroundResource(R.drawable.btn_number_type);
                chongqi_weizhi2.setTextColor(Color.parseColor("#000000"));
                chongqi_weizhi3.setBackgroundResource(R.drawable.btn_number_type);
                chongqi_weizhi3.setTextColor(Color.parseColor("#000000"));
                chongqi_weizhi4.setBackgroundResource(R.drawable.btn_number_type);
                chongqi_weizhi4.setTextColor(Color.parseColor("#000000"));
                chongqi_weizhi1.setBackgroundResource(R.drawable.btn_number_type);
                chongqi_weizhi1.setTextColor(Color.parseColor("#000000"));
                break;
            case R.id.chongqi_longhu_weizhi1:
                longhu_weizhi = 1;
                chongqi_longhu_weizhi1.setBackgroundResource(R.drawable.btn_number_type1);
                chongqi_longhu_weizhi1.setTextColor(Color.parseColor("#FF0000"));
                break;
        }
    }

    public void flash_work() throws InterruptedException {
        while (isTrue) {
            Thread.sleep(5000);
            getQishuAndTime();
        }
    }

    //预测号码
    public void SendPredictionScheme(int uid, String title, String type, int numtype, String qishu_date, int weizhi, String num, String money) {
        OkHttpUtils
                .post()
                .url("http://www.zse6.com/index.php/mobile/ajax/fa_yuce_cqssc")
                .addParams("uid", uid + "")
                .addParams("title", title)
                .addParams("type", type)
                .addParams("numtype", numtype + "")
                .addParams("qishu", qishu_date)
                .addParams("weizhi", weizhi + "")
                .addParams("num", num)
                .addParams("money", money)
                .build().execute(new StringCallback() {
            @Override
            public void onError(okhttp3.Call call, Exception e, int id) {
                MyToast.getToast(SendPredictionSchemeActivity.this, "请求失败");
                e.printStackTrace();
            }

            @Override
            public void onResponse(String response, int id) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int ing = jsonObject.getInt("ing");
                    message = jsonObject.getString("msg");
                    Message message = new Message();
                    message.what = 100;
                    myHandler.sendMessage(message);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    //预测值和
    public void SendPredictionLongHu(int uid, String title, String type, int numtype, String qishu_date, int lhweizhi, String longhu, String money) {
        OkHttpUtils
                .post()
                .url("http://www.zse6.com/index.php/mobile/ajax/fa_yuce_cqssc")
                .addParams("uid", uid + "")
                .addParams("title", title)
                .addParams("type", type)
                .addParams("numtype", numtype + "")
                .addParams("qishu", qishu_date)
                .addParams("lhweizhi", lhweizhi + "")
                .addParams("longhu", longhu)
                .addParams("money", money)
                .build().execute(new StringCallback() {
            @Override
            public void onError(okhttp3.Call call, Exception e, int id) {
                MyToast.getToast(SendPredictionSchemeActivity.this, "请求失败");
                e.printStackTrace();
            }

            @Override
            public void onResponse(String response, int id) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int ing = jsonObject.getInt("ing");
                    message = jsonObject.getString("msg");
                    Message message = new Message();
                    message.what = 100;
                    myHandler.sendMessage(message);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }


    //预测大小
    public void SendPredictionSchemeDaXiao(int uid, String title, String type, int numtype, String qishu_date, int weizhi, String daxiao, String money) {
        OkHttpUtils
                .post()
                .url("http://www.zse6.com/index.php/mobile/ajax/fa_yuce_cqssc")
                .addParams("uid", uid + "")
                .addParams("title", title)
                .addParams("type", type)
                .addParams("numtype", numtype + "")
                .addParams("qishu", qishu_date)
                .addParams("weizhi", weizhi + "")
                .addParams("daxiao", daxiao)
                .addParams("money", money)
                .build().execute(new StringCallback() {
            @Override
            public void onError(okhttp3.Call call, Exception e, int id) {
                MyToast.getToast(SendPredictionSchemeActivity.this, "请求失败");
                e.printStackTrace();
            }

            @Override
            public void onResponse(String response, int id) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int ing = jsonObject.getInt("ing");
                    message = jsonObject.getString("msg");
                    Message message = new Message();
                    message.what = 100;
                    myHandler.sendMessage(message);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }


    //预测单双
    public void SendPredictionSchemeDanShuang(int uid, String title, String type, int numtype, String qishu_date, int weizhi, String danshuang, String money) {
        OkHttpUtils
                .post()
                .url("http://www.zse6.com/index.php/mobile/ajax/fa_yuce_cqssc")
                .addParams("uid", uid + "")
                .addParams("title", title)
                .addParams("type", type)
                .addParams("numtype", numtype + "")
                .addParams("qishu", qishu_date)
                .addParams("weizhi", weizhi + "")
                .addParams("danshuang", danshuang)
                .addParams("money", money)
                .build().execute(new StringCallback() {
            @Override
            public void onError(okhttp3.Call call, Exception e, int id) {
                MyToast.getToast(SendPredictionSchemeActivity.this, "请求失败");
                e.printStackTrace();
            }

            @Override
            public void onResponse(String response, int id) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int ing = jsonObject.getInt("ing");
                    message = jsonObject.getString("msg");
                    Message message = new Message();
                    message.what = 100;
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
                    ToastUtil.showToast(SendPredictionSchemeActivity.this, message);
                    break;
                case 100:
                    Toast.makeText(SendPredictionSchemeActivity.this, message, Toast.LENGTH_LONG).show();
                    if (message.equals("发布成功")) {
                        finish();
                    }
                    if (loadingDialog != null) {
                        loadingDialog.cancel();
                    }
                    break;
                case 300:
                    tv_qishu1.setText(qishu + 1 + "");
                    tv_qishu2.setText(qishu + 2 + "");
                    tv_qishu3.setText(qishu + 3 + "");
                    if (loadingDialog != null) {
                        loadingDialog.cancel();
                    }
                    break;
            }
        }
    };

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            isTrue = false;
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
