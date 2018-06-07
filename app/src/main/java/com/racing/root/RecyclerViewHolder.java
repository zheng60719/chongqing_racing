package com.racing.root;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

/**
 * Created by zcs on 2017/5/9.
 */

public class RecyclerViewHolder extends RecyclerView.ViewHolder {
    private SparseArray<View> sparseArray;
    private Context context;

    public RecyclerViewHolder(Context context, View itemView) {
        super(itemView);
        this.context = context;
        sparseArray = new SparseArray<>();
    }

    /**
     * 通过填写的itemId来获取具体的View的对象
     *
     * @param itemId R.id.***
     * @param <T>    必须是View的子类
     * @return
     */
    public <T extends View> T findView(int itemId) {
        View mView = sparseArray.get(itemId);
        if (mView == null) {
            //实例化具体的View类型
            mView = itemView.findViewById(itemId);
            sparseArray.put(itemId, mView);
        }
        return (T) mView;
    }

    /**
     * 设置TextView
     *
     * @param itemId
     * @param text
     */
    public void setTextView(int itemId, String text) {
        TextView tv = findView(itemId);
        tv.setText(text);
    }

    /**
     * 设置TextView颜色
     *
     * @param itemId
     * @param color
     */
    public void setTextView(int itemId, int color) {
        TextView tv = findView(itemId);
        tv.setTextColor(color);
    }

    /**
     * 设置TextView显隐
     * @param itemId
     * @param num
     */
    public void setVisibility(int itemId, int num) {
        TextView tv = findView(itemId);
        if(num==1){
            tv.setVisibility(View.VISIBLE);
        }else{
            tv.setVisibility(View.GONE);
        }

    }

    /**
     * 加载ImageView
     *
     * @param itemId
     * @param path
     */
    public void setImageView(int itemId, String path) {
        ImageView img = findView(itemId);
        Glide.with(context).load(path).into(img);
    }

    public void setImageView(int itemId, String path, int placeColor, int errorColor) {
        ImageView img = findView(itemId);
        Glide.with(context).load(path).placeholder(new ColorDrawable(context.getResources().getColor(placeColor))).//加载中显示的图片
                error(new ColorDrawable(context.getResources().getColor(errorColor)))//加载失败时显示的图片
                .centerCrop()
                .into(img);
    }

    public void setOnClickListener(int viewId, View.OnClickListener listener) {
        View view = findView(viewId);
        view.setOnClickListener(listener);
    }
}
