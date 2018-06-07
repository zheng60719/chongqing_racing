package com.racing.widget;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;
import android.support.v7.app.AlertDialog;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.lottery.R;


/**
 * @author will on 2017/5/19 19:53
 * @email pengweiqiang64@163.com
 * @description 选择列表Dialog
 * @Version
 */

public class ListViewDialog extends AlertDialog {
    private Context mContext;
    private ListView listView;

    public ListViewDialog(@NonNull Context context, String title ) {
        super(context);
        this.mContext = context;
        // 设置弹窗的布局界面
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_pop_layout_supply_select, null);
        listView = (ListView) view.findViewById(R.id.listview_supplypop);
        setCanceledOnTouchOutside(true);
        setView(view);

        Activity activity = (Activity)context;
        WindowManager m = activity.getWindowManager();
        Display d = m.getDefaultDisplay();  //为获取屏幕宽、高
        WindowManager.LayoutParams p = getWindow().getAttributes();  //获取对话框当前的参数值
        p.height = (int) (d.getHeight() * 0.3);   //高度设置为屏幕的0.3
        p.width = (int) (d.getWidth() * 0.5);    //宽度设置为屏幕的0.5
        getWindow().setAttributes(p);     //设置生效
        setTitle(title);
    }

    public ListViewDialog(@NonNull Context context, @StyleRes int themeResId) {
        super(context, themeResId);
    }

    public ListViewDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    public void show(String title){
        setTitle(title);
        this.show();
    }

    public void setOnItemOnclickListener(AdapterView.OnItemClickListener onItemOnclickListener){
        listView.setOnItemClickListener(onItemOnclickListener);
    }

    public void setListAdapter(BaseAdapter baseAdapter){
        listView.setAdapter(baseAdapter);
    }


}
