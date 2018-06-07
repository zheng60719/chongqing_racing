package com.racing.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.widget.LinearLayout;

import com.lottery.R;
import com.racing.entity.CanKao;
import com.racing.root.CommonRecyclerAdapter;
import com.racing.root.RecyclerViewHolder;

import java.util.List;

/**
 * Created by k41 on 2017/9/30.
 */

public class ReferenceResourcesAdapter extends CommonRecyclerAdapter<CanKao> {
    private Context mContext;
    private List<CanKao> dataList;
    public ReferenceResourcesAdapter(Context mContext, List<CanKao> dataList, int resId) {
        super(mContext, dataList, resId);
        this.mContext = mContext;
        this.dataList = dataList;
    }
    @Override
    public void setData(RecyclerViewHolder holder, int position) {
        CanKao canKao = dataList.get(position);
        LinearLayout linearLayout = holder.findView(R.id.linearLayout);
        if (position%2==1){
            linearLayout.setBackgroundColor(Color.parseColor("#FFFFFF"));
        }else{
            linearLayout.setBackgroundColor(Color.parseColor("#d9d9d9"));
        }
        holder.setTextView(R.id.tv_qishu,canKao.getQishu()+"期  免费参考");
            holder.setTextView(R.id.tv_11,"第一球");
            holder.setTextView(R.id.tv_44,"第二球");
            holder.setTextView(R.id.tv_77,"第三球");
            holder.setTextView(R.id.tv_1010,"第四球");
            holder.setTextView(R.id.tv_1313,"第五球");
        holder.setTextView(R.id.tv_22,canKao.getList().get(0).getDanshuang());
        holder.setTextView(R.id.tv_33,canKao.getList().get(0).getDaxiao());
        holder.setTextView(R.id.tv_new1,canKao.getList().get(0).getNum());
        holder.setTextView(R.id.tv_55,canKao.getList().get(1).getNum());
        holder.setTextView(R.id.tv_66,canKao.getList().get(1).getDaxiao());
        holder.setTextView(R.id.tv_new2,canKao.getList().get(1).getDanshuang());
        holder.setTextView(R.id.tv_88,canKao.getList().get(2).getNum());
        holder.setTextView(R.id.tv_99,canKao.getList().get(2).getDaxiao());
        holder.setTextView(R.id.tv_new3,canKao.getList().get(2).getDanshuang());
        holder.setTextView(R.id.tv_1111,canKao.getList().get(3).getDanshuang());
        holder.setTextView(R.id.tv_1212,canKao.getList().get(3).getDaxiao());
        holder.setTextView(R.id.tv_new4,canKao.getList().get(3).getNum());
        holder.setTextView(R.id.tv_1414,canKao.getList().get(4).getDanshuang());
        holder.setTextView(R.id.tv_1515,canKao.getList().get(4).getDaxiao());
        holder.setTextView(R.id.tv_new5,canKao.getList().get(4).getNum());
    }
}
