package com.stu.app.jyuapp.Model.Adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.stu.app.jyuapp.R;

import java.util.List;

/**
 * @author Jack
 * @time 2016/5/6 0006 18:19
 * @des TODO
 */

/**
 * 把Fragment加到一个集合中当作item
 * FragmentStatePagerAdapter 是PagerAdapter的一个子类
 */
public class AdViewPagerAdapter extends PagerAdapter {
    private List<String> list_source;
    private Context mContext;

    public <T> AdViewPagerAdapter(Context context, List<T> list_source) {
        this.list_source = (List<String>) list_source;
        mContext = context;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        return this.list_source != null ? list_source.size() : 0;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        //        View view = list_source.get(position);
        View view = View.inflate(mContext, R.layout.fragment_subscription_show_ad_viewpager_content, null);

        LinearLayout linearLayout = (LinearLayout) view.findViewById(R.id.ll_subshow_ad_content);
//        linearLayout.setBackground(new ColorDrawable(Color.BLACK));
//        Glide.with(mContext).load(list_source.get(position)).into()
       ImageView imageView = (ImageView)(view.findViewById(R.id.iv_subshow_ad_vp_content));
//        imageView.setBackground(new ColorDrawable(Color.BLACK));
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext,"相关功能暂未开通",Toast.LENGTH_LONG).show();
            }
        });
        Glide.with(mContext).load(list_source.get(position)).into(imageView);

        container.addView(view);
        return view;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }
}
