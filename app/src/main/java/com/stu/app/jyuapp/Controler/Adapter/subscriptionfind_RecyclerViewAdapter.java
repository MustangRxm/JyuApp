package com.stu.app.jyuapp.Controler.Adapter;


import android.content.Context;
import android.view.View;
import android.widget.CompoundButton;

import com.avos.avoscloud.AVUser;
import com.bumptech.glide.Glide;
import com.stu.app.jyuapp.Model.Domain.JyuUser;
import com.stu.app.jyuapp.Model.Domain.SubscriptionFind;
import com.stu.app.jyuapp.Model.EventOBJ.UpdateUserSub;
import com.stu.app.jyuapp.Model.ViewHolder.BaseViewHolder;
import com.stu.app.jyuapp.Model.ViewHolder.SubscriptionFind_ViewHolder;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;


/**
 * @author Jack
 * @time 2016/5/18 0018 15:25
 * @des TODO
 */
public class subscriptionfind_RecyclerViewAdapter extends BaseRecyclerViewAdapter {
    List<SubscriptionFind> list_sourse;
    private Context mContext;
    private JyuUser user;
    public subscriptionfind_RecyclerViewAdapter(Context context, List<SubscriptionFind> list, int resource) {
        super(context, list, resource);
        list_sourse = list;
        this.mContext = context;
    }

    @Override
    protected BaseViewHolder getViewHolder(View view) {
        return new SubscriptionFind_ViewHolder(view);
    }

    @Override
    public void onBindViewHolder_(BaseViewHolder holder, final int position) {
        SubscriptionFind_ViewHolder viewHolder = (SubscriptionFind_ViewHolder) holder;
        final SubscriptionFind item = list_sourse.get(position);
        viewHolder.setObj(item);
        holder.getView().setTag(position);
        Glide.with(mContext).load(item.getHead_portraitSrc()).into(viewHolder.iv_subfind_item_img);
        viewHolder.tv_subfind_item_name.setText(item.getSubName());
        final String itemID = item.getObjectId();
        user = AVUser.getCurrentUser(JyuUser.class);
        if (user!=null) {
            List<String> sublist = user.getSubscription();
            if (sublist != null) {
                if (sublist.contains(itemID)) {
                    //选中就是已订阅，未选中是订阅，默认是未选中
                    viewHolder.tb_subfind_item_sub.setChecked(true);
                } else {
                    viewHolder.tb_subfind_item_sub.setChecked(false);
                }
            }
        }
        viewHolder.tb_subfind_item_sub.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (user != null){
                    List<String> sublist = user.getSubscription();
                if (sublist != null) {
                    if (isChecked) {
                        if (!sublist.contains(itemID)) {
                            sublist.add(itemID);
                        }
                    } else {
                        if (sublist.contains(itemID)) {
                            sublist.remove(itemID);
                        }
                    }
                    EventBus.getDefault().postSticky(new UpdateUserSub(sublist));

                } else {
                    sublist = new ArrayList<String>();
                    sublist.add(itemID);
                    EventBus.getDefault().postSticky(new UpdateUserSub(sublist));
                }
//                if (isChecked) {
//                } else {
//                }
            }
        }
        });
    }

}
