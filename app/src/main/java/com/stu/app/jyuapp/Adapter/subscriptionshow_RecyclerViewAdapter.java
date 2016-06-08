package com.stu.app.jyuapp.Adapter;


import android.content.Context;
import android.view.View;

import com.bumptech.glide.Glide;
import com.stu.app.jyuapp.Domain.SubscriptionContent;
import com.stu.app.jyuapp.ViewHolder.BaseViewHolder;
import com.stu.app.jyuapp.ViewHolder.SubscriptionShow_ViewHolder;

import java.util.List;

/**
 * @author Jack
 * @time 2016/5/18 0018 15:25
 * @des TODO
 */
public class subscriptionshow_RecyclerViewAdapter extends BaseRecyclerViewAdapter {
    List<SubscriptionContent.Totalitem> list_sourse;
    private Context mContext;

    public subscriptionshow_RecyclerViewAdapter(Context context, List<SubscriptionContent.Totalitem> list, int resource) {
        super(context, list, resource);
        list_sourse = list;
        this.mContext = context;
    }



    @Override
    protected BaseViewHolder getViewHolder(View view) {
        return new SubscriptionShow_ViewHolder(view);
    }

    @Override
    public void onBindViewHolder_(BaseViewHolder holder, final int position) {
        SubscriptionShow_ViewHolder viewHolder = (SubscriptionShow_ViewHolder) holder;
        final SubscriptionContent.Totalitem item = list_sourse.get(position);
        viewHolder.setObj(item);
        holder.getView().setTag(position);
        Glide.with(mContext).load(item.getChannel_icon()).into(viewHolder.iv_subshow_item_channel_img);
        viewHolder.tv_subshow_item_channel_name.setText("选自 "+item.getChannel_title());
        viewHolder.tv_subshow_item_title.setText(item.getTitle());
        viewHolder.tv_subshow_item_pubdate.setText(item.getTimeID()+"");
    }

}
