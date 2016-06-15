package com.stu.app.jyuapp.Controler.Adapter;


import android.content.Context;
import android.view.View;

import com.bumptech.glide.Glide;
import com.stu.app.jyuapp.Model.Domain.JyuNews;
import com.stu.app.jyuapp.Model.ViewHolder.BaseViewHolder;
import com.stu.app.jyuapp.Model.ViewHolder.sch_news_ViewHolder;
import com.stu.app.jyuapp.R;

import java.util.List;

/**
 * @author Jack
 * @time 2016/5/18 0018 15:25
 * @des TODO
 */
public class sch_news_App_RecyclerViewAdapter extends BaseRecyclerViewAdapter {
    List<JyuNews> list_sourse;
    private Context mContext;

    public sch_news_App_RecyclerViewAdapter(Context context, List<JyuNews> list, int resource) {
        super(context, list, resource);
        list_sourse = list;
        this.mContext = context;
    }

    @Override
    protected BaseViewHolder getViewHolder(View view) {
        return new sch_news_ViewHolder(view);
    }

    @Override
    public void onBindViewHolder_(BaseViewHolder holder, int position) {
        JyuNews item = list_sourse.get(position);
        holder.getView().setTag(position);
        List<String> list_img = item.getNewsImage();
//        String list_img = item.getNewsImage();

        sch_news_ViewHolder mholder = (sch_news_ViewHolder) holder;
        mholder.setObj(item);
        if (list_img.size() == 0) {
            Glide.with(mContext).load(R.mipmap.jyu_icon).into(mholder.iv_sch_news_cardview_item);
        } else {
            Glide.with(mContext).load(list_img.get(0)).into(mholder.iv_sch_news_cardview_item);
        }
        String[] Titles = item.getRootTitle().split(" ");
        mholder.tv_sch_news_title_cardview_item.setText(Titles[0]);
        mholder.tv_sch_news_date_cardview_item.setText(item.getDate());
        mholder.tv_sch_news_from.setText(item.getFrom());
    }

}
