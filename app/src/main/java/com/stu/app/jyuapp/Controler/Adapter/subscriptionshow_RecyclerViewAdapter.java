package com.stu.app.jyuapp.Controler.Adapter;


import android.content.Context;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.stu.app.jyuapp.Model.Domain.SubscriptionContent;
import com.stu.app.jyuapp.Model.ViewHolder.BaseViewHolder;
import com.stu.app.jyuapp.Model.ViewHolder.SubscriptionShow_ViewHolder;

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
        viewHolder.tv_subshow_item_channel_name.setText("选自 " + item.getChannel_title());

        viewHolder.tv_subshow_item_title.setText(item.getTitle());
        long publicDate = (long) item.getTimeID();//秒为单位
        long currentTime = System.currentTimeMillis();
        long currentSecTime = currentTime / 1000;
        long DeltaTime = currentSecTime - publicDate;
        int TextData = 0;
        if (DeltaTime < 60) {

            //            TextData = (int) (DeltaTime/(60*60));
            viewHolder.tv_subshow_item_pubdate.setText("刚刚");
        } else if (DeltaTime < (60 * 60) && DeltaTime > (60)) {
            //大于1分钟
            TextData = (int) (DeltaTime / (60));
            viewHolder.tv_subshow_item_pubdate.setText(TextData + "分钟前");
        } else if (DeltaTime < (60 * 60 * 24) && DeltaTime > (60 * 60)) {
            //大于1个小时
            TextData = (int) (DeltaTime / (60 * 60));
            viewHolder.tv_subshow_item_pubdate.setText(TextData + "小时前");
        } else if (DeltaTime < (365 * 24 * 60 * 60) && DeltaTime > (60 * 60 * 24)) {
            //大于1天
            TextData = (int) (DeltaTime / (60 * 60 * 24));
            viewHolder.tv_subshow_item_pubdate.setText(TextData + "天前");
        } else if (DeltaTime > (365 * 60 * 60 * 24)) {
            //大于1天
            TextData = (int) (DeltaTime / (60 * 60 * 24));
            viewHolder.tv_subshow_item_pubdate.setText(TextData + "年前");
        }

        viewHolder.ll_subshow_collect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext,"抱歉，相关功能正在完善中",Toast.LENGTH_LONG).show();
            }
        });
        viewHolder.ll_subshow_good.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext,"抱歉，相关功能正在完善中",Toast.LENGTH_LONG).show();
            }
        });
        viewHolder.ll_subshow_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext,"抱歉，相关功能正在完善中",Toast.LENGTH_LONG).show();
            }
        });
    }

}
