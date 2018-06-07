package com.racing.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.widget.LinearLayout;

import com.lottery.R;
import com.racing.entity.TodayNumber;
import com.racing.root.CommonRecyclerAdapter;
import com.racing.root.RecyclerViewHolder;

import java.util.List;


/**
 * Created by k41 on 2017/9/29.
 */

public class TodayNumberAdapter  extends CommonRecyclerAdapter<TodayNumber> {
    private Context mContext;
    private List<TodayNumber> dataList;
    public TodayNumberAdapter(Context mContext, List<TodayNumber> dataList, int resId) {
        super(mContext, dataList, resId);
        this.mContext = mContext;
        this.dataList = dataList;
    }
    @Override
    public void setData(RecyclerViewHolder holder,int position) {
        LinearLayout linearLayout = holder.findView(R.id.ll_bg);
        if (position%2==1){
            linearLayout.setBackgroundColor(Color.parseColor("#FFFFFF"));
        }else{
            linearLayout.setBackgroundColor(Color.parseColor("#d9d9d9"));
        }
        TodayNumber todayNumber = dataList.get(position);
        //号码
        holder.setTextView(R.id.tv_1,position+1+"");
        //总开1
        holder.setTextView(R.id.tv_2,todayNumber.getData().get(0).getZongkai());
//        //未开1
        holder.setTextView(R.id.tv_3,todayNumber.getData().get(0).getWeikai());
//        holder.setTextView(R.id.tv_4,todayNumber.getData().get(position).get(1).getZongkai());
//        holder.setTextView(R.id.tv_5,todayNumber.getData().get(position).get(1).getWeikai());
//        holder.setTextView(R.id.tv_6,todayNumber.getData().get(position).get(2).getZongkai());
//        holder.setTextView(R.id.tv_7,todayNumber.getData().get(position).get(2).getWeikai());
//        holder.setTextView(R.id.tv_8,todayNumber.getData().get(position).get(3).getZongkai());
//        holder.setTextView(R.id.tv_9,todayNumber.getData().get(position).get(3).getWeikai());
//        holder.setTextView(R.id.tv_10,todayNumber.getData().get(position).get(4).getZongkai());
//        holder.setTextView(R.id.tv_11,todayNumber.getData().get(position).get(4).getWeikai());
//        holder.setTextView(R.id.tv_12,todayNumber.getData().get(position).get(5).getZongkai());
//        holder.setTextView(R.id.tv_13,todayNumber.getData().get(position).get(5).getWeikai());
    }
}

