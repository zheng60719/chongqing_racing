package com.racing.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lottery.R;
import com.racing.Adapter.MyQianHouAdapter;
import com.racing.utils.DateUtil;
import com.racing.view.LoadingDialog;
import com.racing.view.MyToast;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


/**
 * Created by Administrator
 * 专家分析
 */
public class LuZhuThreeFragment extends Fragment {
    private View rootView;
    private TextView tv_qishu, tv_shijian;
    private String qishu;
    private long shijian;
    //list
    private String date;
    private List<String> list_da;
    private List<String> list_xiao;
    private List<String> list_da1;
    private List<String> list_xiao1;
    private List<String> list_da2;
    private List<String> list_xiao2;
    private List<String> list_da3;
    private List<String> list_xiao3;
    private List<String> list_da4;
    private List<String> list_xiao4;
    private List<String> list_da5;
    private List<String> list_xiao5;
    private List<String> list_da6;
    private List<String> list_xiao6;
    private List<String> list_da7;
    private List<String> list_xiao7;
    private List<String> list_da8;
    private List<String> list_xiao8;
    private List<String> list_da9;
    private List<String> list_xiao9;
    private TextView tv_1, tv_2;
    private GridView gridview,gridview2;
    private MyQianHouAdapter adapter;
    private MyQianHouAdapter adapter2;
    private LoadingDialog loadingDialog;
    private List<String> list_qianhou1;
    private List<String> list_qianhou2;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.luzhu_three, null);
        InitView(rootView);
        return rootView;
    }

    private void InitView(View rootView) {
        tv_qishu = (TextView) rootView.findViewById(R.id.tv_qishu);
        tv_shijian = (TextView) rootView.findViewById(R.id.tv_shijian);
        tv_1 = (TextView) rootView.findViewById(R.id.tv_1);
        tv_2 = (TextView) rootView.findViewById(R.id.tv_2);
        gridview = (GridView) rootView.findViewById(R.id.gridview);
        gridview2 = (GridView) rootView.findViewById(R.id.gridview2);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (loadingDialog == null) {
            loadingDialog = new LoadingDialog(getActivity(), R.style.LoadingDialog);
        }
        loadingDialog.show();
        getQishuAndTime();
        //获取数据
        getData();
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
                MyToast.getToast(getActivity(),"请求失败");
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
    //handler
    public Handler myHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 200:
                    tv_1.setText("大小总和今日累计 大(" + list_da.size() + ")" + " 小(" + list_xiao.size() + ")");
                    tv_2.setText("单双总和今日累计 单(" + list_da1.size() + ")" + " 双("+ list_xiao1.size() + ")");
                    int size = list_qianhou1.size();
                    int count = 1;
                    List<List<String>>  list_data = new ArrayList<>();
                    for (int i = 0; i < list_qianhou1.size(); i++) {
                        if (i!=list_qianhou1.size()-1) {
                            if (!list_qianhou1.get(i).equals(list_qianhou1.get(i+1))) {
                                count++;
                            }
                        }
                    }
                    for (int i = 0; i < count; i++) {
                        list_data.add(new ArrayList<String>());
                    }
                    count=0;
                    for (int i = 0; i < list_qianhou1.size(); i++) {
                        if (i!=list_qianhou1.size()-1) {
                            if (!list_qianhou1.get(i).equals(list_qianhou1.get(i+1))) {
                                list_data.get(count).add(list_qianhou1.get(i));
                                count++;
                            }else{
                                list_data.get(count).add(list_qianhou1.get(i));
                            }
                        }else{
                            list_data.get(count).add(list_qianhou1.get(i));
                        }
                    }
                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                            list_data.size() *68, LinearLayout.LayoutParams.FILL_PARENT);
                    gridview.setLayoutParams(params); // 设置GirdView布局参数,横向布局的关键
                    gridview.setColumnWidth(68); // 设置列表项宽
                    gridview.setHorizontalSpacing(0); // 设置列表项水平间距
                    gridview.setStretchMode(GridView.NO_STRETCH);
                    gridview.setNumColumns(size); // 设置列数量=列表集合数
                    adapter = new MyQianHouAdapter(getActivity(), list_data);
                    gridview.setAdapter(adapter);

                    /**
                     *2
                     */
                    int size1 = list_qianhou2.size();
                    int count1 = 1;
                    List<List<String>>  list_data1 = new ArrayList<>();
                    for (int i = 0; i < list_qianhou2.size(); i++) {
                        if (i!=list_qianhou2.size()-1) {
                            if (!list_qianhou2.get(i).equals(list_qianhou2.get(i+1))) {
                                count1++;
                            }
                        }
                    }
                    for (int i = 0; i < count1; i++) {
                        list_data1.add(new ArrayList<String>());
                    }
                    count1=0;
                    for (int i = 0; i < list_qianhou2.size(); i++) {
                        if (i!=list_qianhou2.size()-1) {
                            if (!list_qianhou2.get(i).equals(list_qianhou2.get(i+1))) {
                                list_data1.get(count1).add(list_qianhou2.get(i));
                                count1++;
                            }else{
                                list_data1.get(count1).add(list_qianhou2.get(i));
                            }
                        }else{
                            list_data1.get(count1).add(list_qianhou2.get(i));
                        }
                    }
                    LinearLayout.LayoutParams params2 = new LinearLayout.LayoutParams(
                            list_data1.size() * 68, LinearLayout.LayoutParams.FILL_PARENT);
                    gridview2.setLayoutParams(params2); // 设置GirdView布局参数,横向布局的关键
                    gridview2.setColumnWidth(68); // 设置列表项宽
                    gridview2.setHorizontalSpacing(0); // 设置列表项水平间距
                    gridview2.setStretchMode(GridView.NO_STRETCH);
                    gridview2.setNumColumns(size1); // 设置列数量=列表集合数
                    adapter2 = new MyQianHouAdapter(getActivity(), list_data1);
                    gridview2.setAdapter(adapter2);


                    if (loadingDialog != null) {
                        loadingDialog.cancel();
                    }
                    break;
                case 100:
                    MyToast.getToast(getActivity(), "请求失败");
                    break;
                case 300:
                    tv_qishu.setText(qishu);
                    if (shijian<0) {
                        tv_shijian.setText("开奖中");
                    }else{
                        tv_shijian.setText(DateUtil.timeParse(shijian)+"");
                    }
                    break;
            }
        }
    };

    /**
     * 历史开奖
     */
    public void getData() {
        //创建网络处理的对象
        OkHttpUtils
                .post()
                .url("http://www.zse6.com/index.php/mobile/cqsscapi/get_zonghe_luzhu")
                .build().execute(new StringCallback() {
            @Override
            public void onError(okhttp3.Call call, Exception e, int id) {
                MyToast.getToast(getActivity(), "请求失败");
            }

            @Override
            public void onResponse(String response, int id) {
                date = response;
                try {
                    //数据解析
                    JSONObject jsonObject = new JSONObject(date);
                    JSONObject jsonObject1 = jsonObject.getJSONObject("data");
                    String dsdx = jsonObject1.getString("zonghe");
//                    JSONObject jsonObject1 = jsonArray.toJSONObject(jsonArray);
//                    JSONArray jsonArray1 = jsonObject1.getJSONArray("zonghe");
                    JSONArray jsonObj = new JSONArray(dsdx);
                    list_da = new ArrayList<String>();
                    list_xiao = new ArrayList<String>();
                    list_da1 = new ArrayList<String>();
                    list_xiao1 = new ArrayList<String>();
                    list_qianhou1 = new ArrayList<String>();
                    list_qianhou2 = new ArrayList<String>();
                    for (int i = 0; i < jsonObj.length(); i++) {
                        JSONObject o = (JSONObject) jsonObj.get(i);
                        if (o.getString("daxiao").equals("大")) {
                            list_da.add(o.getString("daxiao"));
                        } else {
                            list_xiao.add(o.getString("daxiao"));
                        }
                        if (o.getString("danshuang").equals("单")) {
                            list_da1.add(o.getString("danshuang"));
                        } else {
                            list_xiao1.add(o.getString("danshuang"));
                        }
                        list_qianhou1.add(o.getString("daxiao"));
                        list_qianhou2.add(o.getString("danshuang"));
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
