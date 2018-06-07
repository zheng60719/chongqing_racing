package com.racing.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.lottery.R;
import com.racing.entity.NewFenXi;
import com.racing.root.CommonRecyclerAdapter;
import com.racing.root.RecyclerViewHolder;
import com.racing.utils.GlideCircleTransform;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by k41 on 2017/12/21.
 */

public class NewFenAdapter extends CommonRecyclerAdapter<NewFenXi> {
    private Context mContext;
    private List<NewFenXi> dataList1;

    public NewFenAdapter(Context mContext, List<NewFenXi> dataList, int resId) {
        super(mContext, dataList, resId);
        this.mContext = mContext;
        this.dataList1 = dataList;
    }

    @Override
    public void setData(RecyclerViewHolder holder, final int position) {
        NewFenXi newFenXi = dataList1.get(position);
        ImageView img_main = holder.findView(R.id.img_user);
        ImageView img_vip = holder.findView(R.id.img_vip);
        TextView tv_zhuanjia = holder.findView(R.id.tv_zhuanjia);
        tv_zhuanjia.setText(newFenXi.getList_user().get(0).getNicheng());
        TextView tv_fenan = holder.findView(R.id.tv_fenan);
        tv_fenan.setText("粉丝(" + newFenXi.getList_user().get(0).getFensi_num() + ")、在售方案(" + newFenXi.getList_user().get(0).getZaishou_yuce_num() + ")、猜中方案(" + newFenXi.getList_user().get(0).getYuce_ok_num() + ")");
        TextView beizhu = holder.findView(R.id.tv_beizhu);
        beizhu.setText("简介：" + newFenXi.getList_user().get(0).getBeizhu());
        holder.setTextView(R.id.tv_qishu,"预测期数:"+newFenXi.getQishu_last());
        holder.setTextView(R.id.tv_jifen,newFenXi.getMoney()+"积分");
        if (newFenXi.getType()==1){
            holder.setTextView(R.id.tv_type2,"号码定位胆");
        }else if (newFenXi.getType()==2){
            holder.setTextView(R.id.tv_type2,"大小系列");
        }else if (newFenXi.getType()==3){
            holder.setTextView(R.id.tv_type2,"单双系列");
        }else if (newFenXi.getType()==4){
            holder.setTextView(R.id.tv_type2,"质和系列");
        }else if (newFenXi.getType()==5){
            holder.setTextView(R.id.tv_type2,"龙虎系列");
        }else if (newFenXi.getType()==6){
            holder.setTextView(R.id.tv_type2,"冠亚和系列");
        }

        SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //前面的lSysTime是秒数，先乘1000得到毫秒数，再转为java.util.Date类型
        Date dt = new Date(newFenXi.getShijian()*1000);
        String sDateTime = sdf.format(dt);  //得到精确到秒的表示：08/31/2006 21:08:00
        holder.setTextView(R.id.tv_time,sDateTime);
        holder.setTextView(R.id.tv_title,newFenXi.getTitle());
        try {
            Glide.with(mContext).load(newFenXi.getList_user().get(0).getTx()).centerCrop().placeholder(R.drawable.img_user_tx)
                    .transform(new GlideCircleTransform(mContext))
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE).into(img_main)
                    ;
            Glide.with(mContext).load(newFenXi.getList_level().get(0).getLogo()).placeholder(R.drawable.img_user_tx).//加载中显示的图片
                    error(R.drawable.img_user_tx)//加载失败时显示的图片
                    .into(img_vip);
        } catch (Exception e) {
            e.printStackTrace();
        }
        holder.setOnClickListener(R.id.onItem, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent_dateil = new Intent();
                intent_dateil.setAction("com.detail");
                intent_dateil.putExtra("pos", position);
                //携带自定义的一些数据
                mContext.sendBroadcast(intent_dateil);
            }
        });
    }
}
