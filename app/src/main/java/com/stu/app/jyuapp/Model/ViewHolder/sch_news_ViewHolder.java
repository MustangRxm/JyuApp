package com.stu.app.jyuapp.Model.ViewHolder;


import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.stu.app.jyuapp.R;

/**
 * @author Jack
 * @time 2016/5/18 0018 16:01
 * @des TODO
 */
public class sch_news_ViewHolder extends BaseViewHolder {
//    public SimpleDraweeView sv_sch_news_cardview_item;
        public ImageView iv_sch_news_cardview_item;
    public TextView tv_sch_news_title_cardview_item;
    public TextView tv_sch_news_date_cardview_item;
    public TextView  tv_sch_news_from;

    public sch_news_ViewHolder(View itemView) {
        super(itemView);
    }

    @Override
    public void bindView(View itemView) {
        iv_sch_news_cardview_item = (ImageView) itemView.findViewById(R.id.iv_sch_news_cardview_item);
        tv_sch_news_from = (TextView) itemView.findViewById(R.id.tv_sch_news_from);
        tv_sch_news_title_cardview_item = (TextView) itemView.findViewById(R.id.tv_sch_news_title_cardview_item);
        tv_sch_news_date_cardview_item = (TextView) itemView.findViewById(R.id.tv_sch_news_date_cardview_item);

    }
}
