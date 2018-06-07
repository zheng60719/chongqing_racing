package com.racing.Adapter;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.lottery.R;
import com.racing.entity.ZhuanJiaFenxi;
import com.racing.root.CommonRecyclerAdapter;
import com.racing.root.RecyclerViewHolder;
import com.racing.utils.GlideCircleTransform;

import java.util.List;


/**
 * Created by k41 on 2017/9/26.
 */

public class ZhuanJiaAdapter extends CommonRecyclerAdapter<ZhuanJiaFenxi> {
    private Context mContext;
    private List<ZhuanJiaFenxi> dataList;
    private ZhuanJiaOnClick zhuanJiaOnClick;

    public ZhuanJiaAdapter(Context mContext, List<ZhuanJiaFenxi> dataList, int resId) {
        super(mContext, dataList, resId);
        this.mContext = mContext;
        this.dataList = dataList;
        this.zhuanJiaOnClick = (ZhuanJiaOnClick) mContext;
    }

    @Override
    public void setData(RecyclerViewHolder holder, final int position) {
        ZhuanJiaFenxi fenXi = getItem(position);
        ImageView img_main = holder.findView(R.id.img_user);
        ImageView img_vip = holder.findView(R.id.img_vip);
        TextView tv_zhuanjia = holder.findView(R.id.tv_zhuanjia);
        tv_zhuanjia.setText(fenXi.getNicheng());
        TextView tv_fenan = holder.findView(R.id.tv_fenan);
        tv_fenan.setText("粉丝(" + fenXi.getFensi_num() + ")、在售方案(" + fenXi.getZaishou_yuce_num() + ")、猜中方案(" + fenXi.getYuce_ok_num() + ")");
        TextView beizhu = holder.findView(R.id.tv_beizhu);
        beizhu.setText("简介："+fenXi.getBeizhu());
        try {
            Glide.with(mContext).load(fenXi.getTx()).centerCrop().placeholder(R.drawable.img_user_tx)
                    .transform(new GlideCircleTransform(mContext))
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE).into(img_main);
            Glide.with(mContext).load(fenXi.getList_level().get(0).getLogo()).placeholder(new ColorDrawable(mContext.getResources().getColor(R.color.color_content_bg))).//加载中显示的图片
                    error(new ColorDrawable(mContext.getResources().getColor(R.color.color_content_bg)))//加载失败时显示的图片
                    .into(img_vip);
        } catch (Exception e) {
            e.printStackTrace();
        }
        holder.setOnClickListener(R.id.onItem, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                zhuanJiaOnClick.onItemClick(position);
            }
        });
    }
    public interface ZhuanJiaOnClick{
        void onItemClick(int position);
    }
}
