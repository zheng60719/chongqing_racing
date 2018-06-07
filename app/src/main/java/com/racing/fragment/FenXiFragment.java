package com.racing.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.lottery.R;
import com.racing.Adapter.NewFenAdapter;
import com.racing.entity.BannerInfo;
import com.racing.entity.JiBie;
import com.racing.entity.Level;
import com.racing.entity.NewFenXi;
import com.racing.entity.NewZhuanJiaFenxi;
import com.racing.entity.User;
import com.racing.entity.ZhuanJia;
import com.racing.lottery.YuCeDetailActivity;
import com.racing.lottery.ZhuanJiaActivity;
import com.racing.lottery.ZhuanJiaDetailActivity;
import com.racing.utils.BannerImageLoader;
import com.racing.utils.GlideCircleTransform;
import com.racing.view.LoadingDialog;
import com.racing.view.MyToast;
import com.racing.widget.LoadMoreRecyclerView;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator
 * 专家分析
 */
public class FenXiFragment extends Fragment implements View.OnClickListener {
    private View rootView;
    private TextView tv_header_title, tv_left;

    private LoadMoreRecyclerView recycler_fenxi;
    //data
    private List<NewFenXi> list;
    private List<NewZhuanJiaFenxi> list_zhuanjia;
    //
    private LoadingDialog loadingDialog;
    //头部view
    private Banner banner;//广告栏
    private ImageView img_vip1, img_vip2, img_vip3, img_vip4;
    private ImageView img_user1, img_user2, img_user3, img_user4;
    private TextView tv_fenan1, tv_fenan2, tv_fenan3, tv_fenan4;
    private TextView btn_more;
    private LoadMoreRecyclerView recycler_fenxi1;

    //存储轮播图
    private List<BannerInfo> list_banner_info;
    private List<String> list_banner;

    //
    private LinearLayoutManager linearLayoutManager;
    private NewFenAdapter newFenAdapter;
    private int position = -1;
    //定义广播接收器
    private MyBroadcastReceive myBroadCast;
    //定义意图过滤器
    private IntentFilter filter;
    private LinearLayout btn_zhuanjia_detail1, btn_zhuanjia_detail2, btn_zhuanjia_detail3, btn_zhuanjia_detail4;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.header_small_big, null);
        InitView(rootView);
        return rootView;
    }

    private void InitView(View rootView) {
        tv_header_title = (TextView) rootView.findViewById(R.id.tv_header_title);
        recycler_fenxi = (LoadMoreRecyclerView) rootView.findViewById(R.id.recycler_fenxi);
        tv_left = (TextView) rootView.findViewById(R.id.tv_left);
        //广告栏
        banner = (Banner) rootView.findViewById(R.id.banner);
        tv_fenan1 = (TextView) rootView.findViewById(R.id.tv_fenan1);
        tv_fenan2 = (TextView) rootView.findViewById(R.id.tv_fenan2);
        tv_fenan3 = (TextView) rootView.findViewById(R.id.tv_fenan3);
        tv_fenan4 = (TextView) rootView.findViewById(R.id.tv_fenan4);
        img_user1 = (ImageView) rootView.findViewById(R.id.img_user1);
        img_user2 = (ImageView) rootView.findViewById(R.id.img_user2);
        img_user3 = (ImageView) rootView.findViewById(R.id.img_user3);
        img_user4 = (ImageView) rootView.findViewById(R.id.img_user4);
        img_vip1 = (ImageView) rootView.findViewById(R.id.img_vip1);
        img_vip2 = (ImageView) rootView.findViewById(R.id.img_vip2);
        img_vip3 = (ImageView) rootView.findViewById(R.id.img_vip3);
        img_vip4 = (ImageView) rootView.findViewById(R.id.img_vip4);

        btn_more = (TextView) rootView.findViewById(R.id.btn_more);
        btn_more.setOnClickListener(this);
        recycler_fenxi1 = (LoadMoreRecyclerView) rootView.findViewById(R.id.recycler_fenxi1);
        btn_zhuanjia_detail1 = (LinearLayout) rootView.findViewById(R.id.btn_zhuanjia_detail1);
        btn_zhuanjia_detail2 = (LinearLayout) rootView.findViewById(R.id.btn_zhuanjia_detail2);
        btn_zhuanjia_detail3 = (LinearLayout) rootView.findViewById(R.id.btn_zhuanjia_detail3);
        btn_zhuanjia_detail4 = (LinearLayout) rootView.findViewById(R.id.btn_zhuanjia_detail4);
        btn_zhuanjia_detail1.setOnClickListener(this);
        btn_zhuanjia_detail2.setOnClickListener(this);
        btn_zhuanjia_detail3.setOnClickListener(this);
        btn_zhuanjia_detail4.setOnClickListener(this);
    }

    /**
     * init recyclerView
     */

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        tv_header_title.setText("专家分析");
        if (loadingDialog == null) {
            loadingDialog = new LoadingDialog(getActivity(), R.style.LoadingDialog);
        }
        loadingDialog.show();
        getData();
        getDataZhuanjia();
        getBanngerData();
        tv_left.setVisibility(View.GONE);
        myBroadCast = new MyBroadcastReceive();
        //实例化意图过滤器
        filter = new IntentFilter();
        //为过滤器添加一个定义的广播，当然这里也可以填系统广播
        filter.addAction("com.detail");
        //注册广播
        getActivity().registerReceiver(myBroadCast, filter);
    }

    /**
     * 获取轮播图的图片数据
     */
    private void getDataZhuanjia() {
        OkHttpUtils
                .post()
                .url("http://www.zse6.com/index.php/mobile/cqsscapi/yuce_list")
                .build().execute(new StringCallback() {
            @Override
            public void onError(okhttp3.Call call, Exception e, int id) {
                MyToast.getToast(getActivity(), "请求失败");
            }

            @Override
            public void onResponse(String response, int id) {
                try {
                    JSONArray jsonObj = new JSONObject(response).getJSONArray("data");
                    list = new ArrayList<>();
                    for (int i = 0; i < jsonObj.length(); i++) {
                        JSONObject o = (JSONObject) jsonObj.get(i);
                        NewFenXi fenxi = new NewFenXi();
                        fenxi.setId(o.getInt("id"));
                        String user = o.getString("user");
                        JSONObject json_level = new JSONObject(user);
                        String level_str = json_level.getString("level");
                        JSONObject json_user = new JSONObject(user);
                        fenxi.setMoney(o.getString("money"));
                        fenxi.setTitle(o.getString("title"));
                        fenxi.setShijian(o.getLong("shijian"));
                        fenxi.setQishu_last(o.getString("qishu_last"));
                        fenxi.setType(o.getInt("type"));
                        String beizhu = json_user.getString("beizhu");
                        String nicheng = json_user.getString("nicheng");
                        List<User> list_user = new ArrayList<>();
                        List<Level> list_level = new ArrayList<>();
                        String tx = json_user.getString("tx");
                        String fensi = json_user.getString("fensi_num");
                        String zaishou = json_user.getString("zaishou_yuce_num");
                        String yuce_ok = json_user.getString("yuce_ok_num");
                        String xingming = json_user.getString("xingming");
                        Level level = new Level();
                        JSONObject jsonObject = new JSONObject(level_str);
                        String logo = jsonObject.getString("logo");
                        level.setLogo(logo);
                        list_level.add(level);
                        User user1 = new User();
                        user1.setTx(tx);
                        user1.setFensi_num(fensi);
                        user1.setXingming(xingming);
                        user1.setZaishou_yuce_num(zaishou);
                        user1.setYuce_ok_num(yuce_ok);
                        user1.setBeizhu(beizhu);
                        user1.setNicheng(nicheng);
                        list_user.add(user1);
                        fenxi.setList_user(list_user);
                        fenxi.setList_level(list_level);
                        list.add(fenxi);
                    }
                    Message message = new Message();
                    message.what = 500;
                    myHandler.sendMessage(message);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void getBanngerData() {
        OkHttpUtils
                .post()
                .url("http://www.zse6.com/index.php/mobile/ajax/banner_list/id/6")
                .build().execute(new StringCallback() {
            @Override
            public void onError(okhttp3.Call call, Exception e, int id) {
                MyToast.getToast(getActivity(), "请求失败");
            }

            @Override
            public void onResponse(String response, int id) {
                try {
                    JSONArray jsonObj = new JSONObject(response).getJSONArray("data");
                    list_banner_info = new ArrayList<>();
                    list_banner = new ArrayList<>();
                    list_banner.clear();
                    for (int i = 0; i < jsonObj.length(); i++) {
                        JSONObject o = (JSONObject) jsonObj.get(i);
                        BannerInfo banner_list = new BannerInfo();
                        banner_list.setId(o.getInt("id"));
                        banner_list.setBid(o.getInt("bid"));
                        banner_list.setImg(o.getString("img"));
                        list_banner_info.add(banner_list);
                        list_banner.add(o.getString("img"));
                    }
                    Message message = new Message();
                    message.what = 600;
                    myHandler.sendMessage(message);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * 展示广告栏
     */
    private void showBannerView() {

    }

    /**
     * 获取数据
     */
    private void getData() {
        //创建网络处理的对象
        OkHttpUtils
                .post()
                .url("http://www.zse6.com/index.php/mobile/cqsscapi/zhuanjia_list")
                .build().execute(new StringCallback() {
            @Override
            public void onError(okhttp3.Call call, Exception e, int id) {
                MyToast.getToast(getActivity(), "请求失败");
            }

            @Override
            public void onResponse(String response, int id) {
                try {
                    JSONArray jsonObj = new JSONObject(response).getJSONArray("data");
                    list_zhuanjia = new ArrayList<>();
                    for (int i = 0; i < jsonObj.length(); i++) {
                        JSONObject o = (JSONObject) jsonObj.get(i);
                        NewZhuanJiaFenxi newZhuanJiaFenxi = new NewZhuanJiaFenxi();
                        newZhuanJiaFenxi.setId(o.getInt("id"));
                        String jibie_str = o.getString("jibie");
                        List<JiBie> list_jiBie = new ArrayList<>();
                        JiBie jiBie = new JiBie();
                        JSONObject jsonObject = new JSONObject(jibie_str);
                        String logo = jsonObject.getString("logo");
                        jiBie.setLogo(logo);
                        list_jiBie.add(jiBie);
                        newZhuanJiaFenxi.setList_jibie(list_jiBie);
                        newZhuanJiaFenxi.setTx(o.getString("tx"));
                        newZhuanJiaFenxi.setId(o.getInt("id"));
                        newZhuanJiaFenxi.setNicheng(o.getString("nicheng"));
                        list_zhuanjia.add(newZhuanJiaFenxi);
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

    public Handler myHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 200:
                    try {
                        Glide.with(getActivity()).load(list_zhuanjia.get(0).getList_jibie().get(0).getLogo()).placeholder(new ColorDrawable(getActivity().getResources().getColor(R.color.color_content_bg))).//加载中显示的图片
                                error(R.drawable.img_user_tx)//加载失败时显示的图片
                                .into(img_vip1);
                        Glide.with(getActivity()).load(list_zhuanjia.get(1).getList_jibie().get(0).getLogo()).placeholder(new ColorDrawable(getActivity().getResources().getColor(R.color.color_content_bg))).//加载中显示的图片
                                error(R.drawable.img_user_tx)//加载失败时显示的图片
                                .into(img_vip2);
                        Glide.with(getActivity()).load(list_zhuanjia.get(2).getList_jibie().get(0).getLogo()).placeholder(new ColorDrawable(getActivity().getResources().getColor(R.color.color_content_bg))).//加载中显示的图片
                                error(R.drawable.img_user_tx)//加载失败时显示的图片
                                .into(img_vip3);
                        Glide.with(getActivity()).load(list_zhuanjia.get(3).getList_jibie().get(0).getLogo()).placeholder(new ColorDrawable(getActivity().getResources().getColor(R.color.color_content_bg))).//加载中显示的图片
                                error(R.drawable.img_user_tx)//加载失败时显示的图片
                                .into(img_vip4);

                        Glide.with(getActivity()).load(list_zhuanjia.get(0).getTx()).centerCrop().placeholder(R.drawable.img_user_tx)
                                .transform(new GlideCircleTransform(getActivity()))
                                .diskCacheStrategy(DiskCacheStrategy.SOURCE).into(img_user1);
                        Glide.with(getActivity()).load(list_zhuanjia.get(1).getTx()).centerCrop().placeholder(R.drawable.img_user_tx)
                                .transform(new GlideCircleTransform(getActivity()))
                                .diskCacheStrategy(DiskCacheStrategy.SOURCE).into(img_user2);
                        Glide.with(getActivity()).load(list_zhuanjia.get(2).getTx()).centerCrop().placeholder(R.drawable.img_user_tx)
                                .transform(new GlideCircleTransform(getActivity()))
                                .diskCacheStrategy(DiskCacheStrategy.SOURCE).into(img_user3);
                        Glide.with(getActivity()).load(list_zhuanjia.get(3).getTx()).centerCrop().placeholder(R.drawable.img_user_tx)
                                .transform(new GlideCircleTransform(getActivity()))
                                .diskCacheStrategy(DiskCacheStrategy.SOURCE).into(img_user4);
                        tv_fenan1.setText(list_zhuanjia.get(0).getNicheng());
                        tv_fenan2.setText(list_zhuanjia.get(1).getNicheng());
                        tv_fenan3.setText(list_zhuanjia.get(2).getNicheng());
                        tv_fenan4.setText(list_zhuanjia.get(3).getNicheng());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    if (loadingDialog != null) {
                        loadingDialog.cancel();
                    }
                    break;
                case 100:
                    break;
                case 600:
                    if (list_banner_info == null) {
                        List<String> imageUrls = new ArrayList<>();
                        imageUrls.add("file:///android_asset/logo.png");
                        banner.setImages(imageUrls).setBannerStyle(BannerConfig.NOT_INDICATOR).setImageLoader(new BannerImageLoader())
                                .setBannerAnimation(Transformer.Tablet).start();
                        banner.setFocusable(true);
                        banner.setFocusableInTouchMode(true);
                        banner.requestFocus();
                        banner.requestFocusFromTouch();
                    } else {
                        banner.setImages(list_banner).setBannerStyle(BannerConfig.NOT_INDICATOR).setImageLoader(new BannerImageLoader())
                                .setBannerAnimation(Transformer.Tablet).start();
                        banner.setFocusable(true);
                        banner.setFocusableInTouchMode(true);
                        banner.requestFocus();
                        banner.requestFocusFromTouch();
                    }
                    break;
                case 500:
                    linearLayoutManager = new LinearLayoutManager(getActivity());
                    recycler_fenxi1.setLayoutManager(linearLayoutManager);
                    recycler_fenxi1.setHasFixedSize(true);
                    recycler_fenxi1.setAutoLoadMoreEnable(false);
                    newFenAdapter = new NewFenAdapter(getActivity(), list, R.layout.fenxi_item2);
                    recycler_fenxi1.setAdapter(newFenAdapter);
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
            case R.id.btn_more:
                Intent intent = new Intent(getActivity(), ZhuanJiaActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_zhuanjia_detail1:
                Intent intent_zhuanjia_detail1 = new Intent(getActivity(), ZhuanJiaDetailActivity.class);
                intent_zhuanjia_detail1.putExtra("id", list_zhuanjia.get(0).getId());
                startActivity(intent_zhuanjia_detail1);
                break;
            case R.id.btn_zhuanjia_detail2:
                Intent intent_zhuanjia_detail2 = new Intent(getActivity(), ZhuanJiaDetailActivity.class);
                intent_zhuanjia_detail2.putExtra("id", list_zhuanjia.get(1).getId());
                startActivity(intent_zhuanjia_detail2);
                break;
            case R.id.btn_zhuanjia_detail3:
                Intent intent_zhuanjia_detail3 = new Intent(getActivity(), ZhuanJiaDetailActivity.class);
                intent_zhuanjia_detail3.putExtra("id", list_zhuanjia.get(2).getId());
                startActivity(intent_zhuanjia_detail3);
                break;
            case R.id.btn_zhuanjia_detail4:
                Intent intent_zhuanjia_detail4 = new Intent(getActivity(), ZhuanJiaDetailActivity.class);
                intent_zhuanjia_detail4.putExtra("id", list_zhuanjia.get(3).getId());
                startActivity(intent_zhuanjia_detail4);
                break;
        }
    }

    //广播接收器
    public class MyBroadcastReceive extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals("com.detail")) {
                position = intent.getExtras().getInt("pos", -1);
                Intent intent_record = new Intent(getActivity(), YuCeDetailActivity.class);
                intent_record.putExtra("id", list.get(position).getId());
                intent_record.putExtra("name", list.get(position).getList_user().get(0).getNicheng());
                intent_record.putExtra("fensi", list.get(position).getList_user().get(0).getFensi_num());
                intent_record.putExtra("fanan", list.get(position).getList_user().get(0).getZaishou_yuce_num());
                intent_record.putExtra("ok", list.get(position).getList_user().get(0).getYuce_ok_num());
                intent_record.putExtra("beizhu", list.get(position).getList_user().get(0).getBeizhu());
                intent_record.putExtra("tx", list.get(position).getList_user().get(0).getTx());
                startActivity(intent_record);
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //取消注册事件
        getActivity().unregisterReceiver(myBroadCast);
    }
}
