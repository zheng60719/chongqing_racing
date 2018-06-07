package com.racing.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.lottery.R;
import com.racing.lottery.HistoricalStatisticsActivity;
import com.racing.lottery.HistoryLotteryActivity;
import com.racing.lottery.HotColdActivity;
import com.racing.lottery.LiveLotteryActivity;
import com.racing.lottery.Pk10Activity;
import com.racing.lottery.ReferenceResourcesActivity;
import com.racing.lottery.TodayNumberActivity;
import com.racing.lottery.TrendActivity;


/**
 * Created by Administrator on 2017/2/21.
 */
public class ZhuYeFragment extends Fragment implements View.OnClickListener {
    private ImageView btn1,btn2,btn3,btn4,btn5,btn6,btn7,btn8;
    private View rootView;
    private TextView tv_header_title,tv_left;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.shouye, null);
        InitView(rootView);
        return rootView;
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        tv_header_title.setText("重庆时时彩");
        tv_left.setVisibility(View.GONE);
    }

    public void InitView(View rootView) {
        btn1 = (ImageView) rootView.findViewById(R.id.btn1);
        btn1.setOnClickListener(this);
        btn2 = (ImageView) rootView.findViewById(R.id.btn2);
        btn2.setOnClickListener(this);
        btn3 = (ImageView)rootView.findViewById(R.id.btn3);
        btn3.setOnClickListener(this);
        btn4 = (ImageView) rootView.findViewById(R.id.btn4);
        btn4.setOnClickListener(this);
        btn5 = (ImageView) rootView.findViewById(R.id.btn5);
        btn5.setOnClickListener(this);
        btn6 = (ImageView) rootView.findViewById(R.id.btn6);
        btn6.setOnClickListener(this);
        btn7 = (ImageView) rootView.findViewById(R.id.btn7);
        btn7.setOnClickListener(this);
        btn8 = (ImageView) rootView.findViewById(R.id.btn8);
        btn8.setOnClickListener(this);
        tv_header_title = (TextView) rootView.findViewById(R.id.tv_header_title);
        tv_left = (TextView) rootView.findViewById(R.id.tv_left);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn1:
                //开奖直播
                Intent intent_live_lottery = new Intent(getActivity(), LiveLotteryActivity.class);
                startActivity(intent_live_lottery);
                break;
            case R.id.btn2:
                //历史开奖
                Intent intent_history_lottery = new Intent(getActivity(), HistoryLotteryActivity.class);
                startActivity(intent_history_lottery);
                break;
            case R.id.btn3:
                //PK10技巧
                Intent intent_pk10 = new Intent(getActivity(), Pk10Activity.class);
                startActivity(intent_pk10);
                break;
            case R.id.btn4:
                //历史统计
                Intent intent_hs = new Intent(getActivity(), HistoricalStatisticsActivity.class);
                startActivity(intent_hs);
                break;
            case R.id.btn5:
                //路珠走势
                Intent intent_trend = new Intent(getActivity(), TrendActivity.class);
                startActivity(intent_trend);
                break;
            case R.id.btn6:
                //冷热分析
                Intent intent_hot_cold = new Intent(getActivity(), HotColdActivity.class);
                startActivity(intent_hot_cold);
                break;
            case R.id.btn7:
                //免费参考
                Intent intent_rr = new Intent(getActivity(), ReferenceResourcesActivity.class);
                startActivity(intent_rr);
                break;
            case R.id.btn8:
                //今日号码
                Intent intent_today = new Intent(getActivity(), TodayNumberActivity.class);
                startActivity(intent_today);
                break;
        }
    }
}
