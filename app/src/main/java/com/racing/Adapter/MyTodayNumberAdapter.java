package com.racing.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.lottery.R;
import com.racing.entity.TodayNumber;
import com.racing.entity.TodayNumberItem;

import java.util.Map;

/**
 * Created by Administrator on 2017/1/4.
 */
public class MyTodayNumberAdapter extends BaseAdapter{
    public Context context;
    public TodayNumber todayNumber ;
    public MyTodayNumberAdapter(Context context,TodayNumber todayNumber ){
        this.context = context;
        this.todayNumber = todayNumber ;
    }
    @Override
    public int getCount() {
        if(todayNumber ==null||todayNumber.getData()==null){
            return 0;
        }
        return todayNumber.getData().size();
    }

    @Override
    public TodayNumberItem getItem(int position) {
        TodayNumberItem  map=null;
        if(todayNumber !=null&&todayNumber.getData()!=null&&todayNumber.getData().size()>position){
//            map=todayNumber.getData().get((position+1)+"");
            map = todayNumber.getData().get((position+1)+"");
        }
        return map;
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
                    R.layout.today_number_item, null);
            holder.tv_1 = (TextView) convertView.findViewById(R.id.tv_1);
            holder.tv_2 = (TextView) convertView.findViewById(R.id.tv_2);
            holder.tv_3 = (TextView) convertView.findViewById(R.id.tv_3);
            holder.tv_4 = (TextView) convertView.findViewById(R.id.tv_4);
            holder.tv_5 = (TextView) convertView.findViewById(R.id.tv_5);
            holder.tv_6 = (TextView) convertView.findViewById(R.id.tv_6);
            holder.tv_7 = (TextView) convertView.findViewById(R.id.tv_7);
            holder.tv_8 = (TextView) convertView.findViewById(R.id.tv_8);
            holder.tv_9 = (TextView) convertView.findViewById(R.id.tv_9);
            holder.tv_10 = (TextView) convertView.findViewById(R.id.tv_10);
            holder.tv_11 = (TextView) convertView.findViewById(R.id.tv_11);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        TodayNumberItem map= getItem(position);
        holder.tv_1.setText(position+1+"");
        if(map!=null) {
            if (map!= null) {
                holder.tv_2.setText(map.getZongkai());
                holder.tv_3.setText(map.getWeikai());
            }
            if (map!= null) {
                holder.tv_4.setText(map.getZongkai());
                holder.tv_5.setText(map.getWeikai());
            }
            if (map != null) {
                holder.tv_6.setText(map.getZongkai());
                holder.tv_7.setText(map.getWeikai());
            }
            if (map!= null) {
                holder.tv_8.setText(map.getZongkai());
                holder.tv_9.setText(map.getWeikai());
            }
            if (map!= null) {
                holder.tv_10.setText(map.getZongkai());
                holder.tv_11.setText(map.getWeikai());
            }
        }
        return convertView;
    }
    class ViewHolder{
        TextView tv_1,tv_2,tv_3,tv_4,tv_5,tv_6,tv_7,tv_8,tv_9,tv_10,tv_11;
    }
}
