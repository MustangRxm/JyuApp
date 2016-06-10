package com.stu.app.jyuapp.Model.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * @author Jack
 * @time 2016/5/6 0006 18:19
 * @des TODO
 */

/**
 * 把Fragment加到一个集合中当作item
 *FragmentStatePagerAdapter 是PagerAdapter的一个子类
 *
 *
 * */
public class HomeViewPagerAdapter extends FragmentPagerAdapter {
    private List<Fragment> fragments;
//    private List<String> tittles;
    public HomeViewPagerAdapter(FragmentManager fm, List<Fragment> fragments) {
        super(fm);
        this.fragments=fragments;
    }

//    @Override
//    public Object instantiateItem(ViewGroup container, int position) {
//        container.addView(fragments.get(position));
//return fragments.get(position);
////        return super.instantiateItem(container, position);
//    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

//    @Override
//    public CharSequence getPageTitle(int position) {
//        if (tittles==null){return null;}
//
//        return tittles.get(position);
//    }
}
