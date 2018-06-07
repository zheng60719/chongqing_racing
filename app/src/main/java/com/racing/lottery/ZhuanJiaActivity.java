package com.racing.lottery;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.TextView;

import com.lottery.R;
import com.racing.Adapter.NewZhuanJiaAdapter;
import com.racing.Adapter.ZhuanJiaAdapter;
import com.racing.entity.JiBie;
import com.racing.entity.Level;
import com.racing.entity.NewZhuanJiaFenxi;
import com.racing.entity.ZhuanJiaFenxi;
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
 * 历史开奖
 */
public class ZhuanJiaActivity extends AppCompatActivity implements View.OnClickListener, NewZhuanJiaAdapter.ZhuanJiaOnClick {
    private TextView tv_header_title;
    public LoadMoreRecyclerView recycler_zhuanjia_list;
    public TextView tv_left;
    private LoadingDialog loadingDialog;
    private NewZhuanJiaAdapter newZhuanJiaAdapter;
    private List<NewZhuanJiaFenxi> list_newzhuanjiafenxi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.zhuanjia);
        InitView();
        tv_header_title.setText("专家列表");
        if (loadingDialog == null) {
            loadingDialog = new LoadingDialog(ZhuanJiaActivity.this, R.style.LoadingDialog);
        }
        loadingDialog.show();
        getData();
    }

    private void InitView() {
        tv_header_title = (TextView) findViewById(R.id.tv_header_title);
        recycler_zhuanjia_list = (LoadMoreRecyclerView) findViewById(R.id.recycler_zhuanjia_list);
        tv_left = (TextView) findViewById(R.id.tv_left);
        tv_left.setOnClickListener(this);
    }


    //handler
    public Handler myHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 200:
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ZhuanJiaActivity.this);
                    recycler_zhuanjia_list.setLayoutManager(linearLayoutManager);
                    recycler_zhuanjia_list.setHasFixedSize(true);
                    recycler_zhuanjia_list.setAutoLoadMoreEnable(false);
                    newZhuanJiaAdapter = new NewZhuanJiaAdapter(ZhuanJiaActivity.this, list_newzhuanjiafenxi, R.layout.fenxi_item);
                    recycler_zhuanjia_list.setAdapter(newZhuanJiaAdapter);
                    if (loadingDialog != null) {
                        loadingDialog.cancel();
                    }
                    break;
                case 300:
                    break;
            }
        }
    };

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_left:
                ZhuanJiaActivity.this.finish();
                break;
        }
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
                MyToast.getToast(ZhuanJiaActivity.this, "请求失败");
            }

            @Override
            public void onResponse(String response, int id) {
                try {
                    list_newzhuanjiafenxi = new ArrayList<>();
                    JSONArray jsonObj = new JSONObject(response).getJSONArray("data");
                    for (int i = 0; i < jsonObj.length(); i++) {
                        JSONObject o = (JSONObject) jsonObj.get(i);
                        NewZhuanJiaFenxi fenxi = new NewZhuanJiaFenxi();
                        String jibie_str = o.getString("jibie");
                        fenxi.setNicheng(o.getString("nicheng"));
                        fenxi.setId(o.getInt("id"));
                        fenxi.setTx(o.getString("tx"));
                        fenxi.setMobile(o.getString("mobile"));
                        fenxi.setXingming(o.getString("xingming"));
                        fenxi.setBeizhu(o.getString("beizhu"));
                        fenxi.setAll_yuce_num(o.getString("all_yuce_num"));
                        fenxi.setZaishou_yuce_num(o.getInt("zaishou_yuce_num"));
                        fenxi.setYuce_ok_num(o.getInt("yuce_ok_num"));
                        fenxi.setFensi_num(o.getInt("fensi_num"));
                        List<JiBie> list_jiBie = new ArrayList<>();
                        JiBie jiBie = new JiBie();
                        JSONObject jsonObject = new JSONObject(jibie_str);
                        String logo = jsonObject.getString("logo");
                        jiBie.setLogo(logo);
                        list_jiBie.add(jiBie);
                        fenxi.setList_jibie(list_jiBie);
                        list_newzhuanjiafenxi.add(fenxi);
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
    public void onItemClick(int position) {
        //
        Intent intent = new Intent(ZhuanJiaActivity.this, ZhuanJiaDetailActivity.class);
        intent.putExtra("id", list_newzhuanjiafenxi.get(position).getId());
        intent.putExtra("name", list_newzhuanjiafenxi.get(position).getXingming());
        intent.putExtra("fensi", list_newzhuanjiafenxi.get(position).getFensi_num());
        intent.putExtra("fanan", list_newzhuanjiafenxi.get(position).getZaishou_yuce_num());
        intent.putExtra("ok", list_newzhuanjiafenxi.get(position).getYuce_ok_num());
        startActivity(intent);
    }
}
