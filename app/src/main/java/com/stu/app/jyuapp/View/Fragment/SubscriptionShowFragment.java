package com.stu.app.jyuapp.View.Fragment;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.avos.avoscloud.AVUser;
import com.cundong.recyclerview.HeaderAndFooterRecyclerViewAdapter;
import com.cundong.recyclerview.RecyclerViewUtils;
import com.nightonke.boommenu.BoomMenuButton;
import com.stu.app.jyuapp.Controler.Adapter.AdViewPagerAdapter;
import com.stu.app.jyuapp.Controler.Adapter.BaseRecyclerViewAdapter;
import com.stu.app.jyuapp.Controler.Adapter.subscriptionshow_RecyclerViewAdapter;
import com.stu.app.jyuapp.Controler.Utils.getDataUtils;
import com.stu.app.jyuapp.Model.Domain.JyuSubscription;
import com.stu.app.jyuapp.Model.Domain.JyuUser;
import com.stu.app.jyuapp.Model.EventOBJ.RequestChangeBoomBtStatus;
import com.stu.app.jyuapp.Model.EventOBJ.RequestSubscriptionChoice;
import com.stu.app.jyuapp.Model.EventOBJ.RequestSubscriptionContent;
import com.stu.app.jyuapp.Model.EventOBJ.RequestVPdata;
import com.stu.app.jyuapp.R;
import com.stu.app.jyuapp.View.Activity.WebsiteContent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import me.relex.circleindicator.CircleIndicator;

import static com.stu.app.jyuapp.Model.EventOBJ.RequestChangeBoomBtStatus.BoomMenuStatus.BOOM_INVISIBLE;
import static com.stu.app.jyuapp.Model.EventOBJ.RequestChangeBoomBtStatus.BoomMenuStatus.BOOM_NOTIFY;
import static com.stu.app.jyuapp.Model.EventOBJ.RequestChangeBoomBtStatus.BoomMenuStatus.BOOM_VISIBLE;

//import com.stu.app.jyuapp.Model.EventOBJ.RequestSubscriptionChoice;

/**
 * A simple {@link Fragment} subclass.
 */

/*方案1
* 1.获取用户订阅表
* 2.将表中xml下载
* 3.domain解析
* 4.按pushdata排序
* 5.show
*方案2
* 客户端
* 1.获取用户订阅表
* 2.将用户的id发给服务端请求订阅数据
* 3.flask生成json返回
* 服务器
* 1.接收一个订阅id列表
* 2.查询bmob,找链接，处理xml，抓item,排序,返回
*
*
*
*
* **/

public class SubscriptionShowFragment extends Fragment {
    private RecyclerView rv_subscription_show;
    private LinearLayoutManager linearLayoutManager;
    private SwipeRefreshLayout srl_subscription;
    private final int INIT_RV_DATA = 0x101;
    private final int UPDATE_RV_DATA = 0x102;
    private final int INIT_VP_DATA = 0x104;
    private final int UPDATE_VP_DATA = 0x108;
    private subscriptionshow_RecyclerViewAdapter adapter = null;
    private BoomMenuButton boomMenuButton;
    private View adVP;
    private ViewPager ADviewPager;
    private AdViewPagerAdapter ADadapter;
    private List<String> ADlistSource;
    private CircleIndicator indicator;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    public SubscriptionShowFragment() {
    }

    private List<JyuSubscription> list_SubScriptionShowSource = null;
    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case INIT_RV_DATA:
                    list_SubScriptionShowSource = (List<JyuSubscription>) msg.obj;
                    adapter = new subscriptionshow_RecyclerViewAdapter(getContext(), list_SubScriptionShowSource, R.layout.fragment_subscription_show_item);
                    HeaderAndFooterRecyclerViewAdapter headerAndFooterRecyclerViewAdapter = new HeaderAndFooterRecyclerViewAdapter(adapter);
                    rv_subscription_show.setAdapter(headerAndFooterRecyclerViewAdapter);
                    RecyclerViewUtils.setHeaderView(rv_subscription_show, adVP);
                    adapter.setOnItemClickListener(new BaseRecyclerViewAdapter.OnRecyclerViewItemClickListener() {
                        @Override
                        public void onItemClick(View view, int position) {
                            Intent intent = new Intent(getContext(), WebsiteContent.class);
                            list_SubScriptionShowSource.get(position).getRoot_link();
                            intent.putExtra("url", list_SubScriptionShowSource.get(position).getLink());
                            startActivity(intent);
                        }
                    });
                    srl_subscription.setRefreshing(false);
                    break;
                case UPDATE_RV_DATA:
                    list_SubScriptionShowSource.removeAll(list_SubScriptionShowSource);
                    list_SubScriptionShowSource.addAll((List<JyuSubscription>) msg.obj);
                    adapter.notifyDataSetChanged();
                    srl_subscription.setRefreshing(false);
                    break;
                case UPDATE_VP_DATA:
                    if (ADlistSource != null) {
                        ADlistSource.removeAll(ADlistSource);
                        ADlistSource.addAll((List<String>) msg.obj);
                    }
                    ADadapter.notifyDataSetChanged();
                    srl_subscription.setRefreshing(false);
                    break;
            }

        }
    };
    private boolean lastState = false;
    private int PageNum = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             final Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_subscription_show, container, false);
        rv_subscription_show = (RecyclerView) view.findViewById(R.id.rv_subscription_show);
        adVP = View.inflate(getContext(), R.layout.fragment_subscription_show_ad_viewpager, null);
        indicator = (CircleIndicator) adVP.findViewById(R.id.ci_subscription_show_ad);
        ADviewPager = (ViewPager) adVP.findViewById(R.id.vp_subscription_show_ad);
        ADlistSource = new ArrayList<>();
        ADadapter = new AdViewPagerAdapter(getContext(), ADlistSource);
        ADviewPager.setAdapter(ADadapter);
        indicator.setViewPager(ADviewPager);
        ADadapter.registerDataSetObserver(indicator.getDataSetObserver());
        linearLayoutManager = new LinearLayoutManager(getContext());
        srl_subscription = (SwipeRefreshLayout) view.findViewById(R.id.srl_subscription);
        srl_subscription.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (AVUser.getCurrentUser(JyuUser.class) != null) {
                    getDataUtils.getUserSubcriptionContent(getContext(),0);
                    PageNum=0;
                    getDataUtils.getsubshowVPdata();
                } else {
                    srl_subscription.setRefreshing(false);
                }
            }

        });
        rv_subscription_show.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

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

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void receiverBoomMenuNotify(RequestChangeBoomBtStatus.BoomMenuStatus boomMenuStatus) {
        if (boomMenuStatus == BOOM_NOTIFY) {
            lastState = false;
        }
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void receiverSubscriptionUrl(RequestSubscriptionContent content) {
        List<JyuSubscription> list = content.getSubscriptioncontent();
        if (adapter == null) {
            Message msg = mHandler.obtainMessage();
            msg.what = INIT_RV_DATA;
            msg.obj = list;
            mHandler.sendMessage(msg);
        } else {
            Message msg = mHandler.obtainMessage();
            msg.what = UPDATE_RV_DATA;
            msg.obj = list;
            mHandler.sendMessage(msg);

        }
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void receiverSubChoice(RequestSubscriptionChoice choice) {
        List<JyuSubscription> list = choice.getSubscriptionchoice();
        Message msg = mHandler.obtainMessage();
        msg.what = INIT_RV_DATA;
        msg.obj = list;
        mHandler.sendMessage(msg);
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void receiverVPdata(RequestVPdata requestVPdata) {
        List<String> vpdata = requestVPdata.getVpdata();
        Message msg = mHandler.obtainMessage();
        msg.what = UPDATE_VP_DATA;
        msg.obj = vpdata;
        mHandler.sendMessage(msg);

    }
}
