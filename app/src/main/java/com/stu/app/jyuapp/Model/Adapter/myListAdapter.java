package com.stu.app.jyuapp.Model.Adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * @author Jack
 * @time 2016/5/7 0007 23:18
 * @des TODO
 */
public class myListAdapter extends BaseAdapter {
    private List<String> list;
    private Context mContext;
    public myListAdapter(Context context, List<String> list) {
        mContext=context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view==null){
//            view = View.inflate(mContext, R.layout.item2_list_subitem,null);
        }
//       TextView tv =  (TextView)view.findViewById(R.id.tv_item2);
//        tv.setText(list.get(i));

        return view;
    }
}
