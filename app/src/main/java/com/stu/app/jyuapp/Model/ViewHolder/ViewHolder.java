package com.stu.app.jyuapp.Model.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


/**
 * @author Jack
 * @time 2016/5/4 0004 22:24
 * @des TODO
 */
public class ViewHolder extends RecyclerView.ViewHolder {
    public View getView() {
        return view;
    }

    View view;
    public TextView tv;
    public ImageView iv;

    public ViewHolder(View itemView) {
        super(itemView);
        view =itemView;
//        tv = (TextView) itemView.findViewById(R.id.tv_item);
//        iv = (ImageView) itemView.findViewById(R.id.iv_item);
    }

    public TextView getTv() {
        return tv;
    }

    public ImageView getIv() {
        return iv;
    }



    public View ViewHolder(View itemView) {
//        super(itemView);
        view = itemView;
//        tv = (TextView) itemView.findViewById(R.id.tv_item);
//        iv = (ImageView) itemView.findViewById(R.id.iv_item);
        return view;
    }

}

