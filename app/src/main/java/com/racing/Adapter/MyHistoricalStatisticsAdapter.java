package com.racing.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.lottery.R;
import com.racing.entity.HistoricalStatistics;
import com.racing.entity.HistoricalStatisticsItem;

import java.util.List;
import java.util.Map;


/**
 * Created by Administrator on 2017/1/4.
 */
public class MyHistoricalStatisticsAdapter extends BaseAdapter{
    public Context context;
    public List<HistoricalStatistics> historicalStatisticss ;
    public int type;
    public MyHistoricalStatisticsAdapter(Context context, List<HistoricalStatistics> historicalStatisticss, int type){
        this.context = context;
        this.historicalStatisticss = historicalStatisticss ;
        this.type = type;
    }
    @Override
    public int getCount() {
        if(historicalStatisticss ==null){
            return 0;
        }
        return historicalStatisticss.size();
    }

    @Override
    public HistoricalStatistics getItem(int position) {
        if(historicalStatisticss !=null){
            return historicalStatisticss.get(position);
        }
        return null;
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
                    R.layout.historicalstatistics_item, null);
            holder.tv_1 = (TextView) convertView.findViewById(R.id.hs_tv1);
            holder.tv_2 = (TextView) convertView.findViewById(R.id.hs_tv2);
            holder.tv_3 = (TextView) convertView.findViewById(R.id.hs_tv3);
            holder.tv_4 = (TextView) convertView.findViewById(R.id.hs_tv4);
            holder.tv_5 = (TextView) convertView.findViewById(R.id.hs_tv5);
            holder.tv_6 = (TextView) convertView.findViewById(R.id.hs_tv6);
            holder.tv_7 = (TextView) convertView.findViewById(R.id.hs_tv7);
            holder.tv_8 = (TextView) convertView.findViewById(R.id.hs_tv8);
            holder.tv_shijian = (TextView) convertView.findViewById(R.id.tv_shijian);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        HistoricalStatistics historicalStatistics=getItem(position);
        Map<String, HistoricalStatisticsItem> map = historicalStatistics.getMap();
        holder.tv_shijian.setText(historicalStatistics.getDay());
        if (type==1){
            if(map!=null){
                    if (map.get("1")!=null) {
                        holder.tv_1.setText(map.get("1").getDa());
                        holder.tv_2.setText(map.get("1").getXiao());
                        holder.tv_3.setText(map.get("1").getDan());
                        holder.tv_4.setText(map.get("1").getShuang());
                    }
                        if (map.get("2") != null) {
                            holder.tv_5.setText(map.get("2").getDa());
                            holder.tv_6.setText(map.get("2").getXiao());
                            holder.tv_7.setText(map.get("2").getDan());
                            holder.tv_8.setText(map.get("2").getShuang());
                        }
            }
        }else if (type==2){
            if(map!=null){
                if(map.get("1")!=null){
                    holder.tv_1.setText(map.get("1").getDa());
                    holder.tv_2.setText(map.get("1").getXiao());
                    holder.tv_3.setText(map.get("1").getDan());
                    holder.tv_4.setText(map.get("1").getShuang());
                }
                if(map.get("2")!=null){
                    holder.tv_5.setText(map.get("2").getDa());
                    holder.tv_6.setText(map.get("2").getXiao());
                    holder.tv_7.setText(map.get("2").getDan());
                    holder.tv_8.setText(map.get("2").getShuang());
                }
            }
        }
        return convertView;
    }
    class ViewHolder{
        TextView tv_1,tv_2,tv_3,tv_4,tv_5,tv_6,tv_shijian,tv_7,tv_8;
    }
}
