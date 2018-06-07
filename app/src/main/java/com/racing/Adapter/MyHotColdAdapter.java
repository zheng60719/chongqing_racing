package com.racing.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.lottery.R;
import com.racing.entity.HotCold;
import com.racing.entity.HotColdItem;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/1/4.
 */
public class MyHotColdAdapter extends BaseAdapter{
    public Context context;
    public List<HotCold> list ;
    public MyHotColdAdapter(Context context, List<HotCold> list ){
        this.context = context;
        this.list = list ;
    }
    @Override
    public int getCount() {

        return list.size();
    }

    @Override
    public HotCold getItem(int position) {
        return list.get(position);
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
                    R.layout.hot_cold_item, null);
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
            holder.tv_12 = (TextView) convertView.findViewById(R.id.tv_12);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        HotCold hotCold = list.get(position);
        Map<String, List<HotColdItem>> list_re = hotCold.getList_re();
        Map<String, List<HotColdItem>> list_wen = hotCold.getList_wen();
        if (position==0){
            holder.tv_1.setText("万");
        }else if (position==1){
            holder.tv_1.setText("千");
        }else if (position==2){
            holder.tv_1.setText("百");
        }else if (position==3){
            holder.tv_1.setText("十");
        }else if (position==4){
            holder.tv_1.setText("个");
        }
        if (list_re!=null) {
            if (position==0) {
                holder.tv_2.setText(list.get(0).getList_re().get("1").get(0).getNum1());
                TextViewBg(list.get(0).getList_re().get("1").get(0).getNum1(),holder.tv_2);
                holder.tv_3.setText(list.get(0).getList_re().get("1").get(1).getNum1());
                TextViewBg(list.get(0).getList_re().get("1").get(1).getNum1(),holder.tv_3);
                holder.tv_4.setText(list.get(0).getList_re().get("1").get(2).getNum1());
                TextViewBg(list.get(0).getList_re().get("1").get(2).getNum1(),holder.tv_4);
                holder.tv_5.setText(list.get(0).getList_re().get("1").get(3).getNum1());
                TextViewBg(list.get(0).getList_re().get("1").get(3).getNum1(),holder.tv_5);
                //热
                holder.tv_6.setText(list.get(0).getList_wen().get("1").get(0).getNum1());
                TextViewBg(list.get(0).getList_wen().get("1").get(0).getNum1(),holder.tv_6);
                holder.tv_7.setText(list.get(0).getList_wen().get("1").get(1).getNum1());
                TextViewBg(list.get(0).getList_wen().get("1").get(1).getNum1(),holder.tv_7);
                holder.tv_8.setText(list.get(0).getList_wen().get("1").get(2).getNum1());
                TextViewBg(list.get(0).getList_wen().get("1").get(2).getNum1(),holder.tv_8);
                holder.tv_9.setText(list.get(0).getList_wen().get("1").get(3).getNum1());
                TextViewBg(list.get(0).getList_wen().get("1").get(3).getNum1(),holder.tv_9);
                holder.tv_10.setText(list.get(0).getList_wen().get("1").get(4).getNum1());
                TextViewBg(list.get(0).getList_wen().get("1").get(4).getNum1(),holder.tv_10);
                holder.tv_11.setText(list.get(0).getList_wen().get("1").get(5).getNum1());
                TextViewBg(list.get(0).getList_wen().get("1").get(5).getNum1(),holder.tv_11);
            }
            if (position==1){
                holder.tv_2.setText(list.get(1).getList_re().get("2").get(0).getNum1());
                TextViewBg(list.get(1).getList_re().get("2").get(0).getNum1(),holder.tv_2);
                holder.tv_3.setText(list.get(1).getList_re().get("2").get(1).getNum1());
                TextViewBg(list.get(1).getList_re().get("2").get(1).getNum1(),holder.tv_3);
                holder.tv_4.setText(list.get(1).getList_re().get("2").get(2).getNum1());
                TextViewBg(list.get(1).getList_re().get("2").get(2).getNum1(),holder.tv_4);
                holder.tv_5.setText(list.get(1).getList_re().get("2").get(3).getNum1());
                TextViewBg(list.get(1).getList_re().get("2").get(3).getNum1(),holder.tv_5);
                //温
                holder.tv_6.setText(list.get(position).getList_wen().get("2").get(0).getNum1());
                TextViewBg(list.get(position).getList_wen().get("2").get(0).getNum1(),holder.tv_6);
                holder.tv_7.setText(list.get(position).getList_wen().get("2").get(1).getNum1());
                TextViewBg(list.get(position).getList_wen().get("2").get(1).getNum1(),holder.tv_7);
                holder.tv_8.setText(list.get(position).getList_wen().get("2").get(2).getNum1());
                TextViewBg(list.get(position).getList_wen().get("2").get(2).getNum1(),holder.tv_8);
                holder.tv_9.setText(list.get(position).getList_wen().get("2").get(3).getNum1());
                TextViewBg(list.get(position).getList_wen().get("2").get(3).getNum1(),holder.tv_9);
                holder.tv_10.setText(list.get(position).getList_wen().get("2").get(4).getNum1());
                TextViewBg(list.get(position).getList_wen().get("2").get(4).getNum1(),holder.tv_10);
                holder.tv_11.setText(list.get(position).getList_wen().get("2").get(5).getNum1());
                TextViewBg(list.get(position).getList_wen().get("2").get(5).getNum1(),holder.tv_11);
            }
            if (position==2){
                holder.tv_2.setText(list.get(2).getList_re().get("3").get(0).getNum1());
                TextViewBg(list.get(2).getList_re().get("3").get(0).getNum1(),holder.tv_2);
                holder.tv_3.setText(list.get(2).getList_re().get("3").get(1).getNum1());
                TextViewBg(list.get(2).getList_re().get("3").get(1).getNum1(),holder.tv_3);
                holder.tv_4.setText(list.get(2).getList_re().get("3").get(2).getNum1());
                TextViewBg(list.get(2).getList_re().get("3").get(2).getNum1(),holder.tv_4);
                holder.tv_5.setText(list.get(2).getList_re().get("3").get(3).getNum1());
                TextViewBg(list.get(2).getList_re().get("3").get(3).getNum1(),holder.tv_5);
                //温
                holder.tv_6.setText(list.get(position).getList_wen().get("3").get(0).getNum1());
                TextViewBg(list.get(position).getList_wen().get("3").get(0).getNum1(),holder.tv_6);
                holder.tv_7.setText(list.get(position).getList_wen().get("3").get(1).getNum1());
                TextViewBg(list.get(position).getList_wen().get("3").get(1).getNum1(),holder.tv_7);
                holder.tv_8.setText(list.get(position).getList_wen().get("3").get(2).getNum1());
                TextViewBg(list.get(position).getList_wen().get("3").get(2).getNum1(),holder.tv_8);
                holder.tv_9.setText(list.get(position).getList_wen().get("3").get(3).getNum1());
                TextViewBg(list.get(position).getList_wen().get("3").get(3).getNum1(),holder.tv_9);
                holder.tv_10.setText(list.get(position).getList_wen().get("3").get(4).getNum1());
                TextViewBg(list.get(position).getList_wen().get("3").get(4).getNum1(),holder.tv_10);
                holder.tv_11.setText(list.get(position).getList_wen().get("3").get(5).getNum1());
                TextViewBg(list.get(position).getList_wen().get("3").get(5).getNum1(),holder.tv_11);
            }
            if (position==3){
                holder.tv_2.setText(list.get(3).getList_re().get("4").get(0).getNum1());
                TextViewBg(list.get(3).getList_re().get("4").get(0).getNum1(),holder.tv_2);
                holder.tv_3.setText(list.get(3).getList_re().get("4").get(1).getNum1());
                TextViewBg(list.get(3).getList_re().get("4").get(1).getNum1(),holder.tv_3);
                holder.tv_4.setText(list.get(3).getList_re().get("4").get(2).getNum1());
                TextViewBg(list.get(3).getList_re().get("4").get(2).getNum1(),holder.tv_4);
                holder.tv_5.setText(list.get(3).getList_re().get("4").get(3).getNum1());
                TextViewBg(list.get(3).getList_re().get("4").get(3).getNum1(),holder.tv_5);
                //温
                holder.tv_6.setText(list.get(position).getList_wen().get("4").get(0).getNum1());
                TextViewBg(list.get(position).getList_wen().get("4").get(0).getNum1(),holder.tv_6);
                holder.tv_7.setText(list.get(position).getList_wen().get("4").get(1).getNum1());
                TextViewBg(list.get(position).getList_wen().get("4").get(1).getNum1(),holder.tv_7);
                holder.tv_8.setText(list.get(position).getList_wen().get("4").get(2).getNum1());
                TextViewBg(list.get(position).getList_wen().get("4").get(2).getNum1(),holder.tv_8);
                holder.tv_9.setText(list.get(position).getList_wen().get("4").get(3).getNum1());
                TextViewBg(list.get(position).getList_wen().get("4").get(3).getNum1(),holder.tv_9);
                holder.tv_10.setText(list.get(position).getList_wen().get("4").get(4).getNum1());
                TextViewBg(list.get(position).getList_wen().get("4").get(4).getNum1(),holder.tv_10);
                holder.tv_11.setText(list.get(position).getList_wen().get("4").get(5).getNum1());
                TextViewBg(list.get(position).getList_wen().get("4").get(5).getNum1(),holder.tv_11);
            }
            if (position==4){
                holder.tv_2.setText(list.get(4).getList_re().get("5").get(0).getNum1());
                TextViewBg(list.get(4).getList_re().get("5").get(0).getNum1(),holder.tv_2);
                holder.tv_3.setText(list.get(4).getList_re().get("5").get(1).getNum1());
                TextViewBg(list.get(4).getList_re().get("5").get(1).getNum1(),holder.tv_3);
                holder.tv_4.setText(list.get(4).getList_re().get("5").get(2).getNum1());
                TextViewBg(list.get(4).getList_re().get("5").get(2).getNum1(),holder.tv_4);
                holder.tv_5.setText(list.get(4).getList_re().get("5").get(3).getNum1());
                TextViewBg(list.get(4).getList_re().get("5").get(3).getNum1(),holder.tv_5);
                //温
                holder.tv_6.setText(list.get(position).getList_wen().get("5").get(0).getNum1());
                TextViewBg(list.get(position).getList_wen().get("5").get(0).getNum1(),holder.tv_6);
                holder.tv_7.setText(list.get(position).getList_wen().get("5").get(1).getNum1());
                TextViewBg(list.get(position).getList_wen().get("5").get(1).getNum1(),holder.tv_7);
                holder.tv_8.setText(list.get(position).getList_wen().get("5").get(2).getNum1());
                TextViewBg(list.get(position).getList_wen().get("5").get(2).getNum1(),holder.tv_8);
                holder.tv_9.setText(list.get(position).getList_wen().get("5").get(3).getNum1());
                TextViewBg(list.get(position).getList_wen().get("5").get(3).getNum1(),holder.tv_9);
                holder.tv_10.setText(list.get(position).getList_wen().get("5").get(4).getNum1());
                TextViewBg(list.get(position).getList_wen().get("5").get(4).getNum1(),holder.tv_10);
                holder.tv_11.setText(list.get(position).getList_wen().get("5").get(5).getNum1());
                TextViewBg(list.get(position).getList_wen().get("5").get(5).getNum1(),holder.tv_11);
            }
            if (position==5){
                holder.tv_2.setText(list.get(5).getList_re().get("6").get(0).getNum1());
                TextViewBg(list.get(5).getList_re().get("6").get(0).getNum1(),holder.tv_2);
                holder.tv_3.setText(list.get(5).getList_re().get("6").get(1).getNum1());
                TextViewBg(list.get(5).getList_re().get("6").get(1).getNum1(),holder.tv_3);
                holder.tv_4.setText(list.get(5).getList_re().get("6").get(2).getNum1());
                TextViewBg(list.get(5).getList_re().get("6").get(2).getNum1(),holder.tv_4);
                holder.tv_5.setText(list.get(5).getList_re().get("6").get(3).getNum1());
                TextViewBg(list.get(5).getList_re().get("6").get(3).getNum1(),holder.tv_5);
                //温
                holder.tv_6.setText(list.get(position).getList_wen().get("6").get(0).getNum1());
                TextViewBg(list.get(position).getList_wen().get("6").get(0).getNum1(),holder.tv_6);
                holder.tv_7.setText(list.get(position).getList_wen().get("6").get(1).getNum1());
                TextViewBg(list.get(position).getList_wen().get("6").get(1).getNum1(),holder.tv_7);
                holder.tv_8.setText(list.get(position).getList_wen().get("6").get(2).getNum1());
                TextViewBg(list.get(position).getList_wen().get("6").get(2).getNum1(),holder.tv_8);
                holder.tv_9.setText(list.get(position).getList_wen().get("6").get(3).getNum1());
                TextViewBg(list.get(position).getList_wen().get("6").get(3).getNum1(),holder.tv_9);
                holder.tv_10.setText(list.get(position).getList_wen().get("6").get(4).getNum1());
                TextViewBg(list.get(position).getList_wen().get("6").get(4).getNum1(),holder.tv_10);
                holder.tv_11.setText(list.get(position).getList_wen().get("6").get(5).getNum1());
                TextViewBg(list.get(position).getList_wen().get("6").get(5).getNum1(),holder.tv_11);
            }
        }
        return convertView;
    }
    class ViewHolder{
        TextView tv_1,tv_2,tv_3,tv_4,tv_6,tv_7,tv_5,tv_8,tv_9,tv_10,tv_11,tv_12;
    }
    //设置每个模块的颜色
    public void TextViewBg(String num,TextView textView){
        if (num.equals("1")){
            textView.setBackgroundColor(Color.parseColor("#FFD700"));
        }else if (num.equals("2")){
            textView.setBackgroundColor(Color.parseColor("#4169E1"));
        }else if (num.equals("3")){
            textView.setBackgroundColor(Color.parseColor("#22292c"));
        }else if (num.equals("4")){
            textView.setBackgroundColor(Color.parseColor("#FF8C00"));
        }else if (num.equals("5")){
            textView.setBackgroundColor(Color.parseColor("#40E0D0"));
        }else if (num.equals("6")){
            textView.setBackgroundColor(Color.parseColor("#0000FF"));
        }else if (num.equals("7")){
            textView.setBackgroundColor(Color.parseColor("#bbbbbb"));
        }else if (num.equals("8")){
            textView.setBackgroundColor(Color.parseColor("#FF4500"));
        }else if (num.equals("9")){
            textView.setBackgroundColor(Color.parseColor("#A0522D"));
        }else if (num.equals("10")){
            textView.setBackgroundColor(Color.parseColor("#66CDAA"));
        }
    }
}
