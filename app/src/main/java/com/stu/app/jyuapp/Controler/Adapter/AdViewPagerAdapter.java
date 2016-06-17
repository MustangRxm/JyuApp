package com.stu.app.jyuapp.Controler.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.stu.app.jyuapp.Model.Domain.advertising;
import com.stu.app.jyuapp.R;
import com.stu.app.jyuapp.View.Activity.WebsiteContent;

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
    private List<advertising> list_source;
    private Context mContext;
    private View RootVP;

//    public <T> AdViewPagerAdapter(Context context, List<T> list_source) {
//
//    }

    public AdViewPagerAdapter(Context context, View adVP, List<advertising> list) {
        this.list_source = list;
        mContext = context;
        RootVP = adVP;
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
        final advertising ad =list_source.get(position);
        View view = View.inflate(mContext, R.layout.fragment_subscription_show_ad_viewpager_content, null);
        TextView tvtitle = (TextView) RootVP.findViewById(R.id.tv_title_ad);
        LinearLayout linearLayout = (LinearLayout) view.findViewById(R.id.ll_subshow_ad_content);
       ImageView imageView = (ImageView)(view.findViewById(R.id.iv_subshow_ad_vp_content));
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(mContext,"相关功能暂未开通",Toast.LENGTH_LONG).show();
                Intent intent = new Intent(mContext, WebsiteContent.class);
                intent.putExtra("url",ad.getlink());
                mContext.startActivity(intent);
            }
        });
        Glide.with(mContext).load(ad.gethead_img()).into(imageView);
        tvtitle.setText(ad.gettitle());
        container.addView(view);
        return view;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }
}
