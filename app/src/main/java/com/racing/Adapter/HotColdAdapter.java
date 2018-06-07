package com.racing.Adapter;

import android.content.Context;
import com.racing.entity.HotCold;
import com.racing.root.CommonRecyclerAdapter;
import com.racing.root.RecyclerViewHolder;

import java.util.List;


/**
 * Created by k41 on 2017/9/26.
 */

public class HotColdAdapter extends CommonRecyclerAdapter<HotCold> {
    private Context mContext;
    private List<HotCold> dataList;
    public HotColdAdapter(Context mContext, List<HotCold> dataList, int resId) {
        super(mContext, dataList, resId);
        this.mContext = mContext;
        this.dataList = dataList;
    }
    @Override
    public void setData(RecyclerViewHolder holder, final int position) {
        HotCold hotCold = getItem(position);
    }
}
