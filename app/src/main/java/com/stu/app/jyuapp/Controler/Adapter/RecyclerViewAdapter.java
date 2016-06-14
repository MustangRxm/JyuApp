package com.stu.app.jyuapp.Controler.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.stu.app.jyuapp.Model.Domain.AppItem;
import com.stu.app.jyuapp.Model.ViewHolder.ViewHolder;

import java.util.List;

/**
 * @author Jack
 * @time 2016/5/5 0005 2:39
 * @des TODO
 */
public class RecyclerViewAdapter extends RecyclerView.Adapter<ViewHolder> implements View.OnClickListener {
    private Context mcontext;
    private List<AppItem>  mList;
    private OnRecyclerViewItemClickListener mOnRecyclerViewItemClickListener=null;
    private int resource;


    public RecyclerViewAdapter(Context mcontext, List<AppItem> list, int resource) {
        this.mcontext = mcontext;
        mList = list;
        this.resource=resource;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //               View view = View.inflate(getApplicationContext(),R.layout.rv_item,parent);
        View view = LayoutInflater.from(mcontext).inflate(resource, parent, false);
        view.setOnClickListener(this);
        ViewHolder v = new ViewHolder(view);
        return v;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        AppItem item = mList.get(position);
        holder.getView().setTag(position);
        holder.getIv().setImageResource(item.getImageView());
        holder.getTv().setText(item.getTextView());
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    @Override
    public void onClick(View view) {
        if (mOnRecyclerViewItemClickListener!=null){
            mOnRecyclerViewItemClickListener.onItemClick(view, (Integer) view.getTag());
        }

    }
    public  interface OnRecyclerViewItemClickListener{
        void onItemClick(View view, int position);
    }
    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener){
        mOnRecyclerViewItemClickListener=listener;
    }
}

