package com.stu.app.jyuapp.Controler.Adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * @author Jack
 * @time 2016/6/3 0003 0:20
 * @des TODO
 */

public class MainPagerAdapter extends PagerAdapter {
    private Context mContext;
    private List<View> mList;
    public MainPagerAdapter(Context context,List<View> source) {
        mContext = context;
        mList = source;

    }

    @Override
    public int getCount() {
        return mList!=null? mList.size():0;
    }

    @Override
    public boolean isViewFromObject(final View view, final Object object) {
        return view.equals(object);
    }

    @Override
    public void destroyItem(final View container, final int position, final Object object) {
        ((ViewPager) container).removeView((View) object);
    }

    @Override
    public Object instantiateItem(final ViewGroup container, final int position) {
//        final View view = LayoutInflater.from(
//                mContext).inflate(R.layout.item_vp, null, false);
        //为Rv填充数据
        //                final TextView txtPage = (TextView) view.findViewById(R.id.txt_vp_item_page);
        //                txtPage.setText(String.format("Page #%d", position));
       View view = mList.get(position);
        container.addView(view);
//        return view;
        return view;
    }
}
