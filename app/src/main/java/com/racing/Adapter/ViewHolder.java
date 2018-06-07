package com.racing.Adapter;

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;

/**
 * Created by zcs on 2017/5/10.
 */

public class ViewHolder {
    private View convertView;
    private SparseArray<View> sparseArray;

    public ViewHolder(Context mContext, int resId) {
        convertView = LayoutInflater.from(mContext).inflate(resId, null);
        convertView.setTag(this);
        sparseArray = new SparseArray<>();
    }

    public View getView() {
        return convertView;
    }

    public static ViewHolder getHolder(Context mContext, int resId, View view) {
        ViewHolder holder = null;
        if (view == null) {
            holder = new ViewHolder(mContext, resId);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        return holder;
    }

    public View findView(int id) {
        View view = sparseArray.get(id);
        if (view == null) {
            view = convertView.findViewById(id);
            sparseArray.append(id, view);
        }
        return view;
    }
}
