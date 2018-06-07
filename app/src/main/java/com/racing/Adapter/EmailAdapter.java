package com.racing.Adapter;

import android.content.Context;

import com.lottery.R;
import com.racing.entity.MyEmail;
import com.racing.root.CommonRecyclerAdapter;
import com.racing.root.RecyclerViewHolder;

import java.util.List;


/**
 * Created by k41 on 2017/9/26.
 */

public class EmailAdapter extends CommonRecyclerAdapter<MyEmail> {
    private Context mContext;
    private List<MyEmail> dataList;

    public EmailAdapter(Context mContext, List<MyEmail> dataList, int resId) {
        super(mContext, dataList, resId);
        this.mContext = mContext;
        this.dataList = dataList;
    }

    @Override
    public void setData(RecyclerViewHolder holder, final int position) {
        MyEmail item = getItem(position);
        holder.setTextView(R.id.tv_title, item.getTitle());
        holder.setTextView(R.id.tv_info, item.getInfo());
        holder.setTextView(R.id.tv_time, item.getShijian());
    }
}
