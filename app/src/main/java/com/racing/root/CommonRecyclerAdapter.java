package com.racing.root;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by zcs on 2017/5/9.
 */

public abstract class CommonRecyclerAdapter<T> extends RecyclerView.Adapter<RecyclerViewHolder> implements View.OnClickListener {
    private Context mContext;
    private List<T> dataList;
    private int resId;
    private LayoutInflater mInflater = null;
    protected OnItemViewClickListener onItemViewClickListener;

    public CommonRecyclerAdapter(Context mContext, List<T> dataList, int resId) {
        this.mContext = mContext;
        this.dataList = dataList;
        this.resId = resId;
        mInflater = LayoutInflater.from(mContext);
    }

    public void notifyDataList(List<T> list) {
        dataList.addAll(list);
    }

    public void setDataList(List<T> list) {
        this.dataList = list;
    }

    public void setOnItemViewClickListener(OnItemViewClickListener listener) {
        this.onItemViewClickListener = listener;
    }

    public T getItem(int position) {
        return dataList.get(position);
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(resId, parent, false);
        view.setOnClickListener(this);
        return new RecyclerViewHolder(mContext, view);
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {
        holder.itemView.setTag(position);
//        if (position != 0) {
            setData(holder, position);
//        }
    }

    @Override
    public int getItemCount() {
        if(dataList==null){
            return 0;
        }
        return dataList.size();
    }

    public abstract void setData(RecyclerViewHolder holder, int position);

    public interface OnItemViewClickListener {
        //设置item中某个view的点击事件
        void onItemViewClick(RecyclerViewHolder view, int position);
    }

    @Override
    public void onClick(View v) {
        if (onItemViewClickListener != null) {
            //注意这里使用getTag方法获取position
            onItemViewClickListener.onItemViewClick(null, (int) v.getTag());
        }
    }
}
