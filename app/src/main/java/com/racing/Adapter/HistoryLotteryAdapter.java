package com.racing.Adapter;

import android.content.Context;

import com.lottery.R;
import com.racing.entity.HistoryLottery;
import com.racing.root.CommonRecyclerAdapter;
import com.racing.root.RecyclerViewHolder;

import java.util.List;


/**
 * Created by k41 on 2017/9/26.
 */

public class HistoryLotteryAdapter extends CommonRecyclerAdapter<HistoryLottery> {
    private Context mContext;
    private List<HistoryLottery> dataList;
    public HistoryLotteryAdapter(Context mContext,List<HistoryLottery> dataList, int resId) {
        super(mContext, dataList, resId);
        this.mContext = mContext;
        this.dataList = dataList;
    }
    @Override
    public void setData(RecyclerViewHolder holder,int position) {
        HistoryLottery historyLottery = getItem(position);
        holder.setTextView(R.id.tv_1, historyLottery.getQishu()+"");
        holder.setTextView(R.id.tv_2,historyLottery.getNum1()+"");
        holder.setTextView(R.id.tv_3,historyLottery.getNum2()+"");
        holder.setTextView(R.id.tv_4,historyLottery.getNum3()+"");
        holder.setTextView(R.id.tv_5,historyLottery.getNum4()+"");
        holder.setTextView(R.id.tv_6,historyLottery.getNum5()+"");
        holder.setTextView(R.id.tv_7,historyLottery.getQiansan());
        holder.setTextView(R.id.tv_8,historyLottery.getZhongsan());
        holder.setTextView(R.id.tv_9,historyLottery.getHousan());
        holder.setTextView(R.id.tv_10,historyLottery.getLonghu());
        holder.setTextView(R.id.tv_11,historyLottery.getZonghe());
        holder.setTextView(R.id.tv_12,historyLottery.getZonghedanshuang());
        holder.setTextView(R.id.tv_13,historyLottery.getZonghedaxiao());
    }
}
