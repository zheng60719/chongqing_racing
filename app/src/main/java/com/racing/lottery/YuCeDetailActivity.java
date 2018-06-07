package com.racing.lottery;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.lottery.R;
import com.racing.app.AppSp;
import com.racing.entity.UserBean;
import com.racing.utils.GlideCircleTransform;
import com.racing.utils.ToastUtil;
import com.racing.view.LoadingDialog;
import com.racing.view.MyToast;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * Created by Administrator on 2017/09/24.
 * 历史开奖
 */
public class YuCeDetailActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView tv_header_title;
    public TextView tv_left;
    private LoadingDialog loadingDialog;
    private ImageView img_user;
    private TextView tv_name, tv_fensi, tv_fanan, tv_ok, tv_beizhu, tv_qishu, tv_type2, tv_time;
    private TextView btn_guanzhu;
    private TextView tv_title;
    private String img_user_url = null;
    private String name = null, fensi = null, fanan = null, ok = null, beizhu = null, title = null;
    private Long shijian = null;
    private String qishu = null;
    private UserBean userBean;
    private AppSp app;
    private int id;
    //预测期数
    private TextView tv_yuce_qishu;
    private String yuce_qishu;
    //杀码类型
    private TextView tv_ma;
    private int numtype;
    //预测类型
    private TextView tv_yuce_type;
    private int yece_type;
    //预测位置
    private TextView tv_yuce_weizhi;
    private int yuce_weizhi;
    //发布时间
    private TextView tv_yuce_time;
    private Long yuce_time;
    //预测内容
    private TextView tv_1;
    private String tv_neirong;
    private TextView tv_zhankai;
    //数据
    private String yuce;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.yuce_detail);
        InitView();
        tv_header_title.setText("预测详情");
        if (loadingDialog == null) {
            loadingDialog = new LoadingDialog(YuCeDetailActivity.this, R.style.LoadingDialog);
        }
        loadingDialog.show();
        app = new AppSp();
        userBean = (UserBean) app.getObjectFromShare(YuCeDetailActivity.this, "user");
        id = getIntent().getIntExtra("id", -1);
        tv_name.setText(getIntent().getStringExtra("name"));
        if (getIntent().getStringExtra("fensi") != null) {
            tv_fensi.setText("粉丝数：" + getIntent().getStringExtra("fensi"));
        } else {
            tv_fensi.setText("粉丝数：0");
        }
        tv_fanan.setText(getIntent().getStringExtra("fanan"));
        tv_ok.setText(getIntent().getStringExtra("ok"));
        tv_beizhu.setText(getIntent().getStringExtra("beizhu"));
        img_user_url = getIntent().getStringExtra("tx");
        Glide.with(YuCeDetailActivity.this).load(img_user_url).centerCrop().placeholder(R.drawable.img_user_tx)
                .transform(new GlideCircleTransform(YuCeDetailActivity.this))
                .diskCacheStrategy(DiskCacheStrategy.SOURCE).into(img_user);
        getDate(id, userBean.getId(), "cqssc");
    }

    private void getDate(int id, int uid, String caizhong) {
        Log.i("id", id + "");
        OkHttpUtils
                .post()
                .url("http://www.zse6.com/index.php/mobile/ajax/yuce_info")
                .addParams("id", id + "")
                .addParams("uid", uid + "")
                .addParams("caizhong", caizhong + "")
                .build().execute(new StringCallback() {
            @Override
            public void onError(okhttp3.Call call, Exception e, int id) {
                MyToast.getToast(YuCeDetailActivity.this, "请求失败");
            }

            @Override
            public void onResponse(String response, int id) {
                try {
                    JSONObject json_ing = new JSONObject(response);
                    yuce = json_ing.getString("yuce");
                    if (yuce != null && !yuce.equals("null")) {
                        JSONObject jsonObj = new JSONObject(yuce);
                        yuce_qishu = jsonObj.getString("qishu");
                        numtype = jsonObj.getInt("numtype");
                        yece_type = jsonObj.getInt("type");
                        yuce_weizhi = jsonObj.getInt("weizhi");
                        yuce_time = jsonObj.getLong("shijian");
                        tv_neirong = jsonObj.getString("yuceinfo");
                    } else {
                        Message message = new Message();
                        message.what = 300;
                        myHandler.sendMessage(message);
                    }
                    int ing = json_ing.getInt("ing");
                    if (ing == 1) {
                        Message message = new Message();
                        message.what = 100;
                        myHandler.sendMessage(message);
                    } else {
                        Message message = new Message();
                        message.what = 300;
                        myHandler.sendMessage(message);
                    }
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
        tv_title = (TextView) findViewById(R.id.tv_title);
        tv_qishu = (TextView) findViewById(R.id.tv_qishu);
        tv_type2 = (TextView) findViewById(R.id.tv_type2);
        //
        tv_yuce_qishu = (TextView) findViewById(R.id.tv_yuce_qishu);
        //杀码类型
        tv_ma = (TextView) findViewById(R.id.tv_ma);
        //预测类型
        tv_yuce_type = (TextView) findViewById(R.id.tv_yuce_type);
        //预测位置
        tv_yuce_weizhi = (TextView) findViewById(R.id.tv_yuce_weizhi);
        //预测时间
        tv_yuce_time = (TextView) findViewById(R.id.tv_yuce_time);
        //预测内容
        tv_1 = (TextView) findViewById(R.id.tv_1);
        //展开
        tv_zhankai = (TextView) findViewById(R.id.tv_zhankai);
        tv_zhankai.setOnClickListener(this);

    }


    //handler
    public Handler myHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 200:
                    Glide.with(YuCeDetailActivity.this).load(img_user_url).centerCrop().placeholder(R.color.white)
                            .transform(new GlideCircleTransform(YuCeDetailActivity.this))
                            .diskCacheStrategy(DiskCacheStrategy.SOURCE).into(img_user);
                    tv_name.setText(name);
                    tv_fensi.setText(fensi);
                    tv_fanan.setText(getIntent().getStringExtra("fanan"));
                    tv_ok.setText(ok);
                    tv_beizhu.setText(beizhu);
                    tv_title.setText(title);
                    tv_qishu.setText("预测期数:" + "期");
                    tv_type2.setText("");
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    //前面的lSysTime是秒数，先乘1000得到毫秒数，再转为java.util.Date类型
                    Date dt = new Date(shijian * 1000);
                    String sDateTime = sdf.format(dt);
                    tv_time.setText(sDateTime);
                    if (loadingDialog != null) {
                        loadingDialog.cancel();
                    }
                    break;
                case 300:
                    ToastUtil.showToast(YuCeDetailActivity.this, "无数据");
                    if (loadingDialog != null) {
                        loadingDialog.cancel();
                    }
                    break;
                case 1000:
                    ToastUtil.showToast(YuCeDetailActivity.this,"关注成功");
                    break;
                case 100:
                    if (yuce != null && !yuce.equals("null")) {
                        tv_yuce_qishu.setText("预测期数: " + yuce_qishu);
                        if (numtype == 1) {
                            tv_ma.setText("定码");
                        } else if (numtype == 0) {
                            tv_ma.setText("杀码");
                        }
                        if (yece_type == 1) {
                            tv_yuce_type.setText("预测类型：号码定位胆");
                        } else if (yece_type == 2) {
                            tv_yuce_type.setText("预测类型：大小系列");
                        } else if (yece_type == 3) {
                            tv_yuce_type.setText("预测类型：单双系列");
                        } else if (yece_type == 4) {
                            tv_yuce_type.setText("预测类型：龙虎系列");
                        }
                        if (yece_type == 1) {
                            if (yuce_weizhi == 1) {
                                tv_yuce_weizhi.setText("预测位置：冠军");
                            } else if (yuce_weizhi == 2) {
                                tv_yuce_weizhi.setText("预测位置：亚军");
                            } else if (yuce_weizhi == 3) {
                                tv_yuce_weizhi.setText("预测位置：季军");
                            } else if (yuce_weizhi == 4) {
                                tv_yuce_weizhi.setText("预测位置：第四名");
                            } else if (yuce_weizhi == 5) {
                                tv_yuce_weizhi.setText("预测位置：第五名");
                            } else if (yuce_weizhi == 6) {
                                tv_yuce_weizhi.setText("预测位置：第六名");
                            } else if (yuce_weizhi == 7) {
                                tv_yuce_weizhi.setText("预测位置：第七名");
                            } else if (yuce_weizhi == 8) {
                                tv_yuce_weizhi.setText("预测位置：第八名");
                            } else if (yuce_weizhi == 9) {
                                tv_yuce_weizhi.setText("预测位置：第九名");
                            } else if (yuce_weizhi == 10) {
                                tv_yuce_weizhi.setText("预测位置：第十名");
                            }
                        } else if (yece_type == 2) {
                            if (yuce_weizhi == 1) {
                                tv_yuce_weizhi.setText("预测位置：冠军");
                            } else if (yuce_weizhi == 2) {
                                tv_yuce_weizhi.setText("预测位置：亚军");
                            } else if (yuce_weizhi == 3) {
                                tv_yuce_weizhi.setText("预测位置：季军");
                            } else if (yuce_weizhi == 4) {
                                tv_yuce_weizhi.setText("预测位置：第四名");
                            } else if (yuce_weizhi == 5) {
                                tv_yuce_weizhi.setText("预测位置：第五名");
                            } else if (yuce_weizhi == 6) {
                                tv_yuce_weizhi.setText("预测位置：第六名");
                            } else if (yuce_weizhi == 7) {
                                tv_yuce_weizhi.setText("预测位置：第七名");
                            } else if (yuce_weizhi == 8) {
                                tv_yuce_weizhi.setText("预测位置：第八名");
                            } else if (yuce_weizhi == 9) {
                                tv_yuce_weizhi.setText("预测位置：第九名");
                            } else if (yuce_weizhi == 10) {
                                tv_yuce_weizhi.setText("预测位置：第十名");
                            }
                        } else if (yece_type == 3) {
                            if (yuce_weizhi == 1) {
                                tv_yuce_weizhi.setText("预测位置：冠军");
                            } else if (yuce_weizhi == 2) {
                                tv_yuce_weizhi.setText("预测位置：亚军");
                            } else if (yuce_weizhi == 3) {
                                tv_yuce_weizhi.setText("预测位置：季军");
                            } else if (yuce_weizhi == 4) {
                                tv_yuce_weizhi.setText("预测位置：第四名");
                            } else if (yuce_weizhi == 5) {
                                tv_yuce_weizhi.setText("预测位置：第五名");
                            } else if (yuce_weizhi == 6) {
                                tv_yuce_weizhi.setText("预测位置：第六名");
                            } else if (yuce_weizhi == 7) {
                                tv_yuce_weizhi.setText("预测位置：第七名");
                            } else if (yuce_weizhi == 8) {
                                tv_yuce_weizhi.setText("预测位置：第八名");
                            } else if (yuce_weizhi == 9) {
                                tv_yuce_weizhi.setText("预测位置：第九名");
                            } else if (yuce_weizhi == 10) {
                                tv_yuce_weizhi.setText("预测位置：第十名");
                            }
                        } else if (yece_type == 4) {
                            if (yuce_weizhi == 1) {
                                tv_yuce_weizhi.setText("预测位置：冠军");
                            } else if (yuce_weizhi == 2) {
                                tv_yuce_weizhi.setText("预测位置：亚军");
                            } else if (yuce_weizhi == 3) {
                                tv_yuce_weizhi.setText("预测位置：季军");
                            } else if (yuce_weizhi == 4) {
                                tv_yuce_weizhi.setText("预测位置：第四名");
                            } else if (yuce_weizhi == 5) {
                                tv_yuce_weizhi.setText("预测位置：第五名");
                            } else if (yuce_weizhi == 6) {
                                tv_yuce_weizhi.setText("预测位置：第六名");
                            } else if (yuce_weizhi == 7) {
                                tv_yuce_weizhi.setText("预测位置：第七名");
                            } else if (yuce_weizhi == 8) {
                                tv_yuce_weizhi.setText("预测位置：第八名");
                            } else if (yuce_weizhi == 9) {
                                tv_yuce_weizhi.setText("预测位置：第九名");
                            } else if (yuce_weizhi == 10) {
                                tv_yuce_weizhi.setText("预测位置：第十名");
                            }
                        }
                        if (yuce_time != null) {
                            SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                            //前面的lSysTime是秒数，先乘1000得到毫秒数，再转为java.util.Date类型
                            java.util.Date dt1 = new Date(yuce_time * 1000);
                            String sDateTime1 = sdf1.format(dt1);  //得到精确到秒的表示：08/31/2006 21:08:00
                            tv_yuce_time.setText("发布时间：" + sDateTime1);
                        }
                        tv_1.setText(tv_neirong);
                    }
                    if (loadingDialog != null) {
                        loadingDialog.cancel();
                    }
                    break;
            }
        }
    };

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_left:
                YuCeDetailActivity.this.finish();
                break;
            case R.id.btn_guanzhu:
                //关注
                getFollow(userBean.getId(), getIntent().getIntExtra("id", -1));
                break;
            case R.id.tv_time:
                //预测详情

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
                MyToast.getToast(YuCeDetailActivity.this, "请求失败");
            }

            @Override
            public void onResponse(String response, int id) {
                try {
                    JSONObject json_ing = new JSONObject(response);
                    int ing = json_ing.getInt("ing");
                    if (ing == 1) {
                        Message message = new Message();
                        message.what = 1000;
                        myHandler.sendMessage(message);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

}
