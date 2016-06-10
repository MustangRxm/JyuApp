package com.stu.app.jyuapp.Model.ViewHolder;


import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.stu.app.jyuapp.R;

/**
 * @author Jack
 * @time 2016/5/18 0018 16:01
 * @des TODO
 */
public class SubscriptionFind_ViewHolder extends BaseViewHolder {
    public ImageView iv_subfind_item_img;
    public TextView tv_subfind_item_name;
    public TextView tv_subfind_item_sharenum;
    public ToggleButton tb_subfind_item_sub;

    public SubscriptionFind_ViewHolder(View itemView) {
        super(itemView);
    }

    @Override
    public void bindView(View itemView) {
        iv_subfind_item_img = (ImageView) itemView.findViewById(R.id.iv_subfind_item_img);
        tv_subfind_item_name = (TextView) itemView.findViewById(R.id.tv_subfind_item_name);
        tv_subfind_item_sharenum = (TextView) itemView.findViewById(R.id.tv_subfind_item_sharenum);
        tb_subfind_item_sub = (ToggleButton) itemView.findViewById(R.id.tb_subfind_item_sub);

    }
}
