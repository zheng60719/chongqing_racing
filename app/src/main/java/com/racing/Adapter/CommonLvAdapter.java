package com.racing.Adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * Created by zcs on 2017/5/10.
 */

public abstract class CommonLvAdapter<T> extends BaseAdapter {
    private Context mContext;
    protected List<T> dataList;
    private int resId;

    public CommonLvAdapter(Context mContext, List<T> dataList, int resId) {
        this.mContext = mContext;
        this.dataList = dataList;
        this.resId = resId;
    }

    @Override
    public int getCount() {
        if(dataList == null)
            return 0;
        return dataList.size();
    }

    @Override
    public T getItem(int i) {
        return dataList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder = ViewHolder.getHolder(mContext,resId,view);
        setData(viewHolder,i);
        return viewHolder.getView();
    }
    public abstract void setData(ViewHolder holder, int position);
}
