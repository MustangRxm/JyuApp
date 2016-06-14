package com.stu.app.jyuapp.Controler.Adapter;


import android.content.Context;
import android.view.View;
import android.widget.CompoundButton;

import com.bumptech.glide.Glide;
import com.stu.app.jyuapp.Model.Domain.JyuUser;
import com.stu.app.jyuapp.Model.Domain.SubscriptionFind;
import com.stu.app.jyuapp.Model.EventOBJ.UpdateUserSub;
import com.stu.app.jyuapp.R;
import com.stu.app.jyuapp.Model.ViewHolder.BaseViewHolder;
import com.stu.app.jyuapp.Model.ViewHolder.SubscriptionFind_ViewHolder;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobUser;

/**
 * @author Jack
 * @time 2016/5/18 0018 15:25
 * @des TODO
 */
public class subscriptionfind_RecyclerViewAdapter extends BaseRecyclerViewAdapter {
    List<SubscriptionFind> list_sourse;
    private Context mContext;

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
        BmobUser user_ = BmobUser.getCurrentUser(mContext, JyuUser.class);
        //         List<Map<String, Boolean>> sublist = user.getSubscription();
        if (user_!=null) {
          JyuUser  user = (JyuUser) user_;
            List<String> sublist = user.getSubscription();
            if (sublist != null) {
                //            Map<String, Boolean> submap = sublist.get(0);
                if (sublist.contains(itemID)) {
                    //选中就是已订阅，未选中是订阅，默认是未选中
                    //                if (submap.get(itemID)) {
                    viewHolder.tb_subfind_item_sub.setChecked(true);
                    viewHolder.tb_subfind_item_sub.setBackground(mContext.getResources().getDrawable(R.drawable.bg_bt_raise));
                } else {
                    viewHolder.tb_subfind_item_sub.setChecked(false);
                    viewHolder.tb_subfind_item_sub.setBackground(mContext.getResources().getDrawable(R.drawable.bg_bt_raise_color));

                }
            }//else 默认状态，即未选中
            //        }
        }
        viewHolder.tb_subfind_item_sub.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                BmobUser user_ = BmobUser.getCurrentUser(mContext, JyuUser.class);
                if (user_ != null){
                    JyuUser user = (JyuUser) user_;
                    //                 List<Map<String, Boolean>> sublist = user.getSubscription();
                    List<String> sublist = user.getSubscription();
                if (sublist != null) {

                    //                    Map<String, Boolean> map = sublist.get(0);
                    if (isChecked) {
                        if (!sublist.contains(itemID)) {
                            sublist.add(itemID);
                        }
                    } else {
                        if (sublist.contains(itemID)) {
                            sublist.remove(itemID);
                        }
                    }
                    //                    map.put(item.getObjectId(),isChecked);
                    //                    sublist.set(0,map);
                    EventBus.getDefault().postSticky(new UpdateUserSub(sublist));

                } else {
                    //                    sublist = new ArrayList<Map<String, Boolean>>();
                    //                    HashMap<String,Boolean> map = new HashMap<String, Boolean>();
                    //                    map.put(item.getObjectId(),isChecked);
                    //                    sublist.add(0,map);
                    sublist = new ArrayList<String>();
                    sublist.add(itemID);
                    EventBus.getDefault().postSticky(new UpdateUserSub(sublist));
                }
                if (isChecked) {
                    buttonView.setBackground(mContext.getResources().getDrawable(R.drawable.bg_bt_raise));
                } else {
                    buttonView.setBackground(mContext.getResources().getDrawable(R.drawable.bg_bt_raise_color));
                }
            }
        }
        });
    }

}
