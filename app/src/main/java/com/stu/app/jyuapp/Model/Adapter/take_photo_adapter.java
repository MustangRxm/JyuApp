package com.stu.app.jyuapp.Model.Adapter;

import android.content.Context;
import android.view.View;

import com.bumptech.glide.Glide;
import com.stu.app.jyuapp.Model.Domain.TakePhotoItem;
import com.stu.app.jyuapp.Model.ViewHolder.BaseViewHolder;
import com.stu.app.jyuapp.Model.ViewHolder.take_photo_ViewHolder;

import java.util.List;

/**
 * @author Jack
 * @time 2016/5/30 0030 21:04
 * @des TODO
 */

public class take_photo_adapter extends BaseRecyclerViewAdapter {
    private List<TakePhotoItem> list;
    private int resource;
    private Context context;
    public take_photo_adapter(Context context, List list, int resource) {
        super(context, list, resource);
        this.context=context;
        this.list = list;
        this.resource = resource;
    }

    @Override
    protected BaseViewHolder getViewHolder(View view) {


        return new take_photo_ViewHolder(view);
    }

    @Override
    public void onBindViewHolder_(BaseViewHolder holder, int position) {
        holder.getView().setTag(position);
        take_photo_ViewHolder viewholder = (take_photo_ViewHolder) holder;
//        viewholder.mSimpleDraweeView.setImageURI(Uri.parse((String) list.get(position)));
//        viewholder.mSimpleDraweeView.setImageBitmap(list.get(position).getSmallIMG());
        Glide.with(context).load(list.get(position)).thumbnail(0.1f).into(viewholder.mImageView);
    }

//    @Override
//    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {}
}
