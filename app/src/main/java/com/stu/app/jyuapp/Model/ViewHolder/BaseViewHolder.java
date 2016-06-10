package com.stu.app.jyuapp.Model.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * @author Jack
 * @time 2016/5/4 0004 22:24
 * @des TODO
 */
public abstract class BaseViewHolder extends RecyclerView.ViewHolder {
    public View getView() {
        return view;
    }
    View view;
//    int position;

    public Object getObj() {
        return obj;
    }

    public void setObj(Object obj) {
        this.obj = obj;
    }




    Object obj;
    public BaseViewHolder(View itemView) {
        super(itemView);
        view =itemView;
        bindView(itemView);
    }
    public abstract void bindView(View itemView);



}

