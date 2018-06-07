package com.racing.Adapter;

import android.content.Context;

import com.lottery.R;
import com.racing.entity.Pk10;
import com.racing.root.CommonRecyclerAdapter;
import com.racing.root.RecyclerViewHolder;
import com.racing.utils.DateUtil;

import java.util.List;


/**
 * Created by k41 on 2017/9/26.
 */

public class Pk10Adapter extends CommonRecyclerAdapter<Pk10> {
    private Context mContext;
    private List<Pk10> dataList;
    public Pk10Adapter(Context mContext, List<Pk10> dataList, int resId) {
        super(mContext, dataList, resId);
        this.mContext = mContext;
        this.dataList = dataList;
    }
    @Override
    public void setData(RecyclerViewHolder holder,int position) {
        Pk10 pk10 = getItem(position);
        holder.setTextView(R.id.tv_title, pk10.getTitle());
        String s = DateUtil.formatTime2String(pk10.getShijian());
        String[] split = s.split(" ");
        String[] split1 = split[0].split("-");
        holder.setTextView(R.id.tv_time, split1[1]+"-"+split1[2]);
    }
}
