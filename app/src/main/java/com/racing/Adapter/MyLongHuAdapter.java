package com.racing.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import com.lottery.R;

import java.util.List;

/**
 * Created by Administrator on 2017/1/4.
 */
public class MyLongHuAdapter extends BaseAdapter{
    public Context context;
    public List<List<String>> list_daxiao;
    public MyLongHuAdapter(Context context, List<List<String>> list_daxiao){
        this.context = context;
        this.list_daxiao = list_daxiao;
    }
    @Override
    public int getCount() {
        return list_daxiao.size();
    }


    @Override
    public List<String> getItem(int position) {

        return list_daxiao.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(
                    R.layout.luzhu_one_item_one, null);
            holder.tv_1 = (TextView) convertView.findViewById(R.id.tv1);
            holder.tv_2 = (TextView) convertView.findViewById(R.id.tv2);
            holder.tv_3 = (TextView) convertView.findViewById(R.id.tv3);
            holder.tv_4 = (TextView) convertView.findViewById(R.id.tv4);
            holder.tv_5 = (TextView) convertView.findViewById(R.id.tv5);
            holder.tv_6 = (TextView) convertView.findViewById(R.id.tv6);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        List<String> list_data = getItem(position);
        if (position%2==0) {
            if (list_data.size()==1) {
                holder.tv_1.setText(list_data.get(0));
                holder.tv_1.setBackgroundResource(R.drawable.bg_live_number);
            }
            if (list_data.size()==2) {
                holder.tv_1.setText(list_data.get(0));
                holder.tv_2.setText(list_data.get(1));
                holder.tv_1.setBackgroundResource(R.drawable.bg_live_number);
                holder.tv_2.setBackgroundResource(R.drawable.bg_live_number);
            }
            if (list_data.size()==3) {
                holder.tv_1.setText(list_data.get(0));
                holder.tv_2.setText(list_data.get(1));
                holder.tv_3.setText(list_data.get(2));
                holder.tv_1.setBackgroundResource(R.drawable.bg_live_number);
                holder.tv_2.setBackgroundResource(R.drawable.bg_live_number);
                holder.tv_3.setBackgroundResource(R.drawable.bg_live_number);
            }
            if (list_data.size()==4) {
                holder.tv_1.setText(list_data.get(0));
                holder.tv_2.setText(list_data.get(1));
                holder.tv_3.setText(list_data.get(2));
                holder.tv_4.setText(list_data.get(3));
                holder.tv_1.setBackgroundResource(R.drawable.bg_live_number);
                holder.tv_2.setBackgroundResource(R.drawable.bg_live_number);
                holder.tv_3.setBackgroundResource(R.drawable.bg_live_number);
                holder.tv_4.setBackgroundResource(R.drawable.bg_live_number);
            }
            if (list_data.size() >= 5) {
                holder.tv_1.setText(list_data.get(0));
                holder.tv_2.setText(list_data.get(1));
                holder.tv_3.setText(list_data.get(2));
                holder.tv_4.setText(list_data.get(3));
                holder.tv_5.setText(list_data.get(4));
                holder.tv_1.setBackgroundResource(R.drawable.bg_live_number);
                holder.tv_2.setBackgroundResource(R.drawable.bg_live_number);
                holder.tv_3.setBackgroundResource(R.drawable.bg_live_number);
                holder.tv_4.setBackgroundResource(R.drawable.bg_live_number);
                holder.tv_5.setBackgroundResource(R.drawable.bg_live_number);
            }
            holder.tv_6.setText(list_data.size() + "");
        }else{
            if (list_data.size() == 1) {
                holder.tv_1.setText(list_data.get(0));
                holder.tv_1.setBackgroundResource(R.drawable.bg_live_number_other);
            }
            if (list_data.size() == 2) {
                holder.tv_1.setText(list_data.get(0));
                holder.tv_2.setText(list_data.get(1));
                holder.tv_1.setBackgroundResource(R.drawable.bg_live_number_other);
                holder.tv_2.setBackgroundResource(R.drawable.bg_live_number_other);
            }
            if (list_data.size() == 3) {
                holder.tv_1.setText(list_data.get(0));
                holder.tv_2.setText(list_data.get(1));
                holder.tv_3.setText(list_data.get(2));
                holder.tv_1.setBackgroundResource(R.drawable.bg_live_number_other);
                holder.tv_2.setBackgroundResource(R.drawable.bg_live_number_other);
                holder.tv_3.setBackgroundResource(R.drawable.bg_live_number_other);
            }
            if (list_data.size() == 4) {
                holder.tv_1.setText(list_data.get(0));
                holder.tv_2.setText(list_data.get(1));
                holder.tv_3.setText(list_data.get(2));
                holder.tv_4.setText(list_data.get(3));
                holder.tv_1.setBackgroundResource(R.drawable.bg_live_number_other);
                holder.tv_2.setBackgroundResource(R.drawable.bg_live_number_other);
                holder.tv_3.setBackgroundResource(R.drawable.bg_live_number_other);
                holder.tv_4.setBackgroundResource(R.drawable.bg_live_number_other);
            }
            if (list_data.size() >= 5) {
                holder.tv_1.setText(list_data.get(0));
                holder.tv_2.setText(list_data.get(1));
                holder.tv_3.setText(list_data.get(2));
                holder.tv_4.setText(list_data.get(3));
                holder.tv_5.setText(list_data.get(4));
                holder.tv_1.setBackgroundResource(R.drawable.bg_live_number_other);
                holder.tv_2.setBackgroundResource(R.drawable.bg_live_number_other);
                holder.tv_3.setBackgroundResource(R.drawable.bg_live_number_other);
                holder.tv_4.setBackgroundResource(R.drawable.bg_live_number_other);
                holder.tv_5.setBackgroundResource(R.drawable.bg_live_number_other);
            }
            holder.tv_6.setText(list_data.size() + "");
        }
        return convertView;
    }
    class ViewHolder{
        TextView tv_1,tv_2,tv_3,tv_4,tv_5,tv_6;
    }
}
