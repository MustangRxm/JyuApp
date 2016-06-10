package com.stu.app.jyuapp.Model.ViewHolder;


import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.stu.app.jyuapp.R;

/**
 * @author Jack
 * @time 2016/5/18 0018 16:01
 * @des TODO
 */
public class SubscriptionShow_ViewHolder extends BaseViewHolder {
    public ImageView iv_subshow_item_channel_img;
    public TextView tv_subshow_item_channel_name;
    public TextView tv_subshow_item_title;
    public TextView tv_subshow_item_pubdate;
    public LinearLayout ll_subshow_share;
    public LinearLayout ll_subshow_collect;
    public LinearLayout ll_subshow_good;
    public SubscriptionShow_ViewHolder(View itemView) {
        super(itemView);
    }

    @Override
    public void bindView(View itemView) {
        iv_subshow_item_channel_img = (ImageView) itemView.findViewById(R.id.iv_subshow_item_channel_img);
        tv_subshow_item_channel_name = (TextView) itemView.findViewById(R.id.tv_subshow_item_channel_name);
        tv_subshow_item_title = (TextView) itemView.findViewById(R.id.tv_subshow_item_title);
        tv_subshow_item_pubdate = (TextView) itemView.findViewById(R.id.tv_subshow_item_pubdate);
        ll_subshow_share = (LinearLayout) itemView.findViewById(R.id.ll_subshow_share);
        ll_subshow_collect = (LinearLayout) itemView.findViewById(R.id.ll_subshow_collect);
        ll_subshow_good = (LinearLayout) itemView.findViewById(R.id.ll_subshow_good);

    }
}
