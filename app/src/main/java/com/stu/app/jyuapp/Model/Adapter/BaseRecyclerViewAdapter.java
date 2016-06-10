package com.stu.app.jyuapp.Model.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.stu.app.jyuapp.Model.ViewHolder.BaseViewHolder;

import java.util.List;

/**
 * @author Jack
 * @time 2016/5/5 0005 2:39
 * @des TODO
 */
public abstract class BaseRecyclerViewAdapter extends RecyclerView.Adapter<BaseViewHolder> implements View.OnClickListener {
    private Context mcontext;
    public List mList;
    private OnRecyclerViewItemClickListener mOnRecyclerViewItemClickListener = null;
    private int resource;

    public BaseRecyclerViewAdapter(Context context, List list, int resource) {
        //        this.mcontext = mcontext;
        this.mcontext = context;
        mList = list;
        this.resource = resource;
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //               View view = View.inflate(getApplicationContext(),R.layout.rv_item,parent);
        BaseViewHolder v = null;
        View view = LayoutInflater.from(mcontext).inflate(resource, parent, false);
        view.setOnClickListener(this);
        v = getViewHolder(view);
        return v;
    }


    protected abstract BaseViewHolder getViewHolder(View view);

    public abstract void onBindViewHolder_(BaseViewHolder holder, int position);

    public void onBindViewHolder(BaseViewHolder holder, int position) {
        onBindViewHolder_(holder, position);
    }

    public int getItemCount() {
        return mList.size() ;
    }

    @Override
    public void onClick(View view) {
        if (mOnRecyclerViewItemClickListener != null) {
            mOnRecyclerViewItemClickListener.onItemClick(view, (Integer) view.getTag());
        }

    }

    public interface OnRecyclerViewItemClickListener {
        void onItemClick(View view, int position);
    }

    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
        mOnRecyclerViewItemClickListener = listener;
    }
}

