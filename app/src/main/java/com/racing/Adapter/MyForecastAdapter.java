package com.racing.Adapter;

import android.content.Context;

import com.lottery.R;
import com.racing.entity.MyForecast;
import com.racing.root.CommonRecyclerAdapter;
import com.racing.root.RecyclerViewHolder;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


/**
 * Created by k41 on 2017/9/26.
 */

public class MyForecastAdapter extends CommonRecyclerAdapter<MyForecast> {
    private Context mContext;
    private List<MyForecast> dataList;
    private String qishu;

    public MyForecastAdapter(Context mContext, List<MyForecast> dataList, int resId, String qishu) {
        super(mContext, dataList, resId);
        this.mContext = mContext;
        this.dataList = dataList;
        this.qishu = qishu;
    }

    @Override
    public void setData(RecyclerViewHolder holder, final int position) {
        MyForecast item = getItem(position);
        holder.setTextView(R.id.tv_qishu, item.getQishu());
        holder.setTextView(R.id.tv_title, item.getTitle());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //前面的lSysTime是秒数，先乘1000得到毫秒数，再转为java.util.Date类型
        Date dt = new Date(item.getShijian() * 1000);
        String sDateTime = sdf.format(dt);
        holder.setTextView(R.id.tv_time, sDateTime);
        String qishu1 = item.getQishu();
        if (qishu1 != null) {
            if (Long.parseLong(qishu1) < Long.parseLong(qishu)) {
                if (item.getIs_ok() == 0) {
                    holder.setTextView(R.id.tv_state, "未中奖");
                } else {
                    holder.setTextView(R.id.tv_state, "已中奖");
                }
            } else {
                holder.setTextView(R.id.tv_state, "未开奖");
            }
        }
        holder.setTextView(R.id.tv_content, item.getYuceinfo());
        if (item.getType() == 1) {
            if (item.getWeizhi() == 1) {
                holder.setTextView(R.id.tv_num_type, "号码定位胆 " + " 冠军");
            } else if (item.getWeizhi() == 2) {
                holder.setTextView(R.id.tv_num_type, "号码定位胆 " + " 亚军");
            } else if (item.getWeizhi() == 3) {
                holder.setTextView(R.id.tv_num_type, "号码定位胆 " + " 季军军");
            } else if (item.getWeizhi() == 4) {
                holder.setTextView(R.id.tv_num_type, "号码定位胆 " + " 第四名");
            } else if (item.getWeizhi() == 5) {
                holder.setTextView(R.id.tv_num_type, "号码定位胆 " + " 第五名");
            } else if (item.getWeizhi() == 6) {
                holder.setTextView(R.id.tv_num_type, "号码定位胆 " + " 第六名");
            } else if (item.getWeizhi() == 7) {
                holder.setTextView(R.id.tv_num_type, "号码定位胆 " + " 第七名");
            } else if (item.getWeizhi() == 8) {
                holder.setTextView(R.id.tv_num_type, "号码定位胆 " + " 第八名");
            } else if (item.getWeizhi() == 9) {
                holder.setTextView(R.id.tv_num_type, "号码定位胆 " + " 第九名");
            } else if (item.getWeizhi() == 10) {
                holder.setTextView(R.id.tv_num_type, "号码定位胆 " + " 第十名");
            }
        } else if (item.getType() == 2) {
            holder.setTextView(R.id.tv_num_type, "大小系列 ");
        } else if (item.getType() == 3) {
            holder.setTextView(R.id.tv_num_type, "单双系列 ");
        } else if (item.getType() == 4) {
            holder.setTextView(R.id.tv_num_type, "龙虎系列 ");
        }
    }
}
