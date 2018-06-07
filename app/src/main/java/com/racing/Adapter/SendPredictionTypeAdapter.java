package com.racing.Adapter;

import android.content.Context;
import android.widget.TextView;

import com.lottery.R;
import com.racing.entity.SendNumType;

import java.util.List;

/**
 * Created by k41 on 2017/12/16.
 */

public class SendPredictionTypeAdapter extends CommonLvAdapter<SendNumType> {
    private Context mContext;
    private List<SendNumType> dataList;

    public SendPredictionTypeAdapter(Context mContext, List<SendNumType> dataList, int resId) {
        super(mContext, dataList, resId);
        this.dataList = dataList;
        this.mContext = mContext;
    }

    @Override
    public void setData(ViewHolder holder, int position) {
        TextView text = (TextView) holder.findView(R.id.spinner_content);
        text.setText(dataList.get(position).getNumTypeName());
    }
}
