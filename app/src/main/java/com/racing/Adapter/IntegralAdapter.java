package com.racing.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.widget.LinearLayout;

import com.lottery.R;
import com.racing.entity.Integral;
import com.racing.root.CommonRecyclerAdapter;
import com.racing.root.RecyclerViewHolder;

import java.util.List;


/**
 * Created by k41 on 2017/9/26.
 */

public class IntegralAdapter extends CommonRecyclerAdapter<Integral> {
    private Context mContext;
    private List<Integral> dataList;
    public IntegralAdapter(Context mContext, List<Integral> dataList, int resId) {
        super(mContext, dataList, resId);
        this.mContext = mContext;
        this.dataList = dataList;
    }
    @Override
    public void setData(RecyclerViewHolder holder, int position) {
        Integral integral = getItem(position);
        if (position%2==0) {
            holder.setTextView(R.id.tv_key, integral.getKey());
            holder.setTextView(R.id.tv_time, integral.getShijian());
            holder.setTextView(R.id.tv_money, integral.getMoney());
            LinearLayout linearLayout = holder.findView(R.id.layout);
            linearLayout.setBackgroundColor(Color.parseColor("#FFFFFF"));
        }else{
            holder.setTextView(R.id.tv_key, integral.getKey());
            holder.setTextView(R.id.tv_time, integral.getShijian());
            holder.setTextView(R.id.tv_money, integral.getMoney());
            LinearLayout linearLayout = holder.findView(R.id.layout);
            linearLayout.setBackgroundColor(Color.parseColor("#f3f3f3"));
        }
    }
}
