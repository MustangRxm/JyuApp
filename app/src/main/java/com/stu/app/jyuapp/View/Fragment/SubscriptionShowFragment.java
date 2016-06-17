package com.stu.app.jyuapp.View.Fragment;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.avos.avoscloud.AVException;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.stu.app.jyuapp.Controler.Adapter.AdViewPagerAdapter;
import com.stu.app.jyuapp.Controler.Adapter.BaseRecyclerViewAdapter;
import com.stu.app.jyuapp.Controler.Adapter.subscriptionshow_RecyclerViewAdapter;
import com.stu.app.jyuapp.Controler.Request.RequestAD;
import com.stu.app.jyuapp.Controler.Request.RequestSubContent;
import com.stu.app.jyuapp.Model.Domain.JyuSubscription;
import com.stu.app.jyuapp.Model.Domain.advertising;
import com.stu.app.jyuapp.Model.EventOBJ.RequestChangeBoomBtStatus;
import com.stu.app.jyuapp.R;
import com.stu.app.jyuapp.View.Activity.WebsiteContent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import me.relex.circleindicator.CircleIndicator;

import static com.stu.app.jyuapp.Model.EventOBJ.RequestChangeBoomBtStatus.BoomMenuStatus.BOOM_INVISIBLE;
import static com.stu.app.jyuapp.Model.EventOBJ.RequestChangeBoomBtStatus.BoomMenuStatus.BOOM_NOTIFY;
import static com.stu.app.jyuapp.Model.EventOBJ.RequestChangeBoomBtStatus.BoomMenuStatus.BOOM_VISIBLE;



public class SubscriptionShowFragment extends BaseFragment {
    private XRecyclerView rv_subscription_show;
    private LinearLayoutManager linearLayoutManager;
    private subscriptionshow_RecyclerViewAdapter adapter = null;
    private View adVP;
    private ViewPager ADviewPager;
    private AdViewPagerAdapter ADadapter;
    private List<advertising> ADlistSource;
    private CircleIndicator indicator;
    private int REQUEST_ITEM_COUNT = 0;
    private List<JyuSubscription> list_SubScriptionShowSource = null;

    public SubscriptionShowFragment() {
    }

    private boolean lastState = false;
//    private int PageNum = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             final Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_subscription_show, container, false);

        InitView(view);
        InitData();
        InitEvent();

        rv_subscription_show.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                //1.往下滚，消失
                //2.往上滚，显示
                if ((dy > 0) && (!lastState)) {
                    lastState = true;
                    EventBus.getDefault().post(BOOM_INVISIBLE);
                } else if ((dy < 0) && (lastState)) {
                    EventBus.getDefault().post(BOOM_VISIBLE);
                    lastState = false;
                }
            }
        });
        rv_subscription_show.setLayoutManager(linearLayoutManager);
        return view;
    }

    private void InitEvent() {
        rv_subscription_show.addHeaderView(adVP);
        rv_subscription_show.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                REQUEST_ITEM_COUNT=0;
                RequestSubContent mRequestSubContent = new RequestSubContent(getContext(),REQUEST_ITEM_COUNT);
                mRequestSubContent.setOnRequestListener(new RequestSubContent.OnRequestSuccessListener() {
                    @Override
                    public void done(List<JyuSubscription> list, AVException e) {
                        list_SubScriptionShowSource.removeAll(list_SubScriptionShowSource);
                        list_SubScriptionShowSource.addAll(list);
                        REQUEST_ITEM_COUNT+=list.size();
                            adapter.notifyDataSetChanged();
                        rv_subscription_show.refreshComplete();
                    }
                });
                mRequestSubContent.RequestNewsData();
            }

            @Override
            public void onLoadMore() {
                RequestSubContent mRequestSubContent = new RequestSubContent(getContext(),REQUEST_ITEM_COUNT);
                mRequestSubContent.setOnRequestListener(new RequestSubContent.OnRequestSuccessListener() {
                    @Override
                    public void done(List<JyuSubscription> list, AVException e) {
                        list_SubScriptionShowSource.addAll(list);
                        REQUEST_ITEM_COUNT+=list.size();
                        adapter.notifyDataSetChanged();
                        rv_subscription_show.loadMoreComplete();
                    }
                });
                mRequestSubContent.RequestNewsData();
            }
        });
    }

    private void InitData() {
        RequestSubContent mRequestSubContent = new RequestSubContent(getContext(),REQUEST_ITEM_COUNT);
        mRequestSubContent.setOnRequestListener(new RequestSubContent.OnRequestSuccessListener() {
            @Override
            public void done(List<JyuSubscription> list, AVException e) {
                list_SubScriptionShowSource = list;
                REQUEST_ITEM_COUNT+=list.size();
                adapter = new subscriptionshow_RecyclerViewAdapter(getContext(), list_SubScriptionShowSource, R.layout.fragment_subscription_show_item);
                adapter.setOnItemClickListener(new BaseRecyclerViewAdapter.OnRecyclerViewItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        Intent intent = new Intent(getContext(), WebsiteContent.class);
                        list_SubScriptionShowSource.get(position).getRoot_link();
                        intent.putExtra("url", list_SubScriptionShowSource.get(position).getLink());
                        startActivity(intent);
                    }
                });
                rv_subscription_show.setAdapter(adapter);
            }
        });
        mRequestSubContent.RequestNewsData();

            new RequestAD(getContext()) {
            @Override
            public void RequestSuccess(List<advertising> list) {
                Log.i("20160617","list size");
                ADlistSource = list;
//            ADlistSource = new ArrayList<>();
            ADadapter = new AdViewPagerAdapter(getContext(),adVP, list);
            ADviewPager.setAdapter(ADadapter);
            indicator.setViewPager(ADviewPager);
            ADadapter.registerDataSetObserver(indicator.getDataSetObserver());
                mHandler.sendEmptyMessageDelayed(0,2000);
//                ADviewPager.setCurrentItem();
            }
        }.RequestADData();
//
    }
    private Boolean VP_FLAG=true;
    Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if (msg.what==0) {
                mHandler.removeMessages(0);
                int currentNum =ADviewPager.getCurrentItem();
                int totalNum = ADlistSource.size();
//                Log.i("20160617","currentNum: "+currentNum+"  totalNUM:::  "+ totalNum);
                if (currentNum+1==totalNum){
                    VP_FLAG=false;
                }
                if (currentNum==0){
                    VP_FLAG=true;
                }

                if (VP_FLAG){
                    currentNum++;
                }
                else {
                    currentNum--;
                }
                ADviewPager.setCurrentItem(currentNum);
                mHandler.sendEmptyMessageDelayed(0,4000);
            }

        }
    };

    private void InitView(View view) {
        rv_subscription_show = (XRecyclerView) view.findViewById(R.id.recyclerview);
        linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rv_subscription_show.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
        rv_subscription_show.setLoadingMoreProgressStyle(ProgressStyle.BallRotate);
        adVP = View.inflate(getContext(), R.layout.fragment_subscription_show_ad_viewpager, null);
        indicator = (CircleIndicator) adVP.findViewById(R.id.ci_subscription_show_ad);
        ADviewPager = (ViewPager) adVP.findViewById(R.id.vp_subscription_show_ad);



    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void receiverBoomMenuNotify(RequestChangeBoomBtStatus.BoomMenuStatus boomMenuStatus) {
        if (boomMenuStatus == BOOM_NOTIFY) {
            lastState = false;
        }
    }
}
