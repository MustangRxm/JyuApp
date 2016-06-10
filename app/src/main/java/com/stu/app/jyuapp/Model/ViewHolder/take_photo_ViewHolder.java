package com.stu.app.jyuapp.Model.ViewHolder;

import android.view.View;
import android.widget.ImageView;

import com.stu.app.jyuapp.R;

/**
 * @author Jack
 * @time 2016/5/30 0030 21:04
 * @des TODO
 */

public class take_photo_ViewHolder extends BaseViewHolder {
//    public SimpleDraweeView mSimpleDraweeView;
    public ImageView mImageView;
    public take_photo_ViewHolder(View itemView) {
        super(itemView);
    }

    @Override
    public void bindView(View itemView) {
//        mSimpleDraweeView = (SimpleDraweeView) itemView.findViewById(R.id.sdv_take_phone);
        mImageView = (ImageView) itemView.findViewById(R.id.iv_take_photo);
        //        itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Log.i("fortheviewholder","testtest enter view holder");
//            }
//        });
    }
}
