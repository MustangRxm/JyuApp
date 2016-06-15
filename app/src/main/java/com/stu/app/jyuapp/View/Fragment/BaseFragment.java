package com.stu.app.jyuapp.View.Fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cundong.recyclerview.HeaderAndFooterRecyclerViewAdapter;
import com.cundong.recyclerview.RecyclerViewUtils;
import com.stu.app.jyuapp.Controler.Adapter.BaseRecyclerViewAdapter;
import com.stu.app.jyuapp.Controler.Adapter.sch_news_App_RecyclerViewAdapter;
import com.stu.app.jyuapp.Controler.Adapter.subscriptionfind_RecyclerViewAdapter;
import com.stu.app.jyuapp.Controler.Adapter.subscriptionshow_RecyclerViewAdapter;
import com.stu.app.jyuapp.Model.Domain.JyuNews;
import com.stu.app.jyuapp.Model.Domain.JyuSubscription;
import com.stu.app.jyuapp.Model.Domain.SubscriptionFind;
import com.stu.app.jyuapp.Model.EventOBJ.BaseRequestEvent;
import com.stu.app.jyuapp.Model.EventOBJ.RequestChangeBoomBtStatus;
import com.stu.app.jyuapp.Model.EventOBJ.RequestNewsData;
import com.stu.app.jyuapp.Model.EventOBJ.RequestSubscriptionContent;
import com.stu.app.jyuapp.Model.EventOBJ.RequestSubscriptionFind;
import com.stu.app.jyuapp.R;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import static com.stu.app.jyuapp.Model.EventOBJ.RequestChangeBoomBtStatus.BoomMenuStatus.BOOM_INVISIBLE;
import static com.stu.app.jyuapp.Model.EventOBJ.RequestChangeBoomBtStatus.BoomMenuStatus.BOOM_NOTIFY;
import static com.stu.app.jyuapp.Model.EventOBJ.RequestChangeBoomBtStatus.BoomMenuStatus.BOOM_VISIBLE;

/**
 * Created by m1 on 16-6-15.
 */

public abstract class BaseFragment<T, K extends BaseRequestEvent> extends Fragment {
    private final int INIT_RECYCLERVIEW_DATA = 0x201;
    private SwipeRefreshLayout swipereFreshlayout;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private BaseRecyclerViewAdapter adapter;
    private Boolean AdapterInitFLAG = false;
    private List<T> listSource = null;
    private View HeaderView = null;
    private View FootView = null;
    private boolean lastState = false;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    public void InitRecyclerView() {
        layoutManager = getLayoutManager();
        //        adapter = getAdapter();
        adapter.setOnItemClickListener(new BaseRecyclerViewAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                onRecyclerViewItemCLickListener(view, position);
            }
        });
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                onRecyclerViewScrollStateChanged(recyclerView, newState);
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
                onRecyclerViewScrolling(recyclerView, dx, dy);
            }
        });
        HeaderView = addRecyclerHeader();
        FootView = addRecyclerFooter();
        recyclerView.setLayoutManager(layoutManager);
        HeaderAndFooterRecyclerViewAdapter headerAndFooterRecyclerViewAdapter = new HeaderAndFooterRecyclerViewAdapter(adapter);
        recyclerView.setAdapter(headerAndFooterRecyclerViewAdapter);
        if (HeaderView != null)
            RecyclerViewUtils.setHeaderView(recyclerView, HeaderView);
        if (FootView != null)
            RecyclerViewUtils.setHeaderView(recyclerView, FootView);

    }
    protected abstract void onRefreshDoingSomething();
    protected abstract void onRecyclerViewScrolling(RecyclerView recyclerView, int dx, int dy);

    protected abstract void onRecyclerViewScrollStateChanged(RecyclerView recyclerView, int newState);

    protected abstract void onRecyclerViewItemCLickListener(View view, int position);

    protected abstract RecyclerView.LayoutManager getLayoutManager();

    protected abstract View addRecyclerFooter();

    protected abstract View addRecyclerHeader();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.base_fragment, container, false);

        swipereFreshlayout = (SwipeRefreshLayout) view.findViewById(R.id.swiperefreshlayout);
        swipereFreshlayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                onRefreshDoingSomething();
            }
        });
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerview);
        return view;
    }



    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case INIT_RECYCLERVIEW_DATA:
                    if (AdapterInitFLAG) {
                        InitRecyclerView();
                    }
                    break;

            }
        }
    };

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void receiverBoomMenuNotify(RequestChangeBoomBtStatus.BoomMenuStatus boomMenuStatus) {
        if (boomMenuStatus == BOOM_NOTIFY) {
            lastState = false;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void MainEventReceiver(K event) {
        if (event instanceof RequestNewsData) {
            List<JyuNews> newsDataList = event.getObjList();
            if (adapter == null) {
                this.adapter = new sch_news_App_RecyclerViewAdapter(getContext(), newsDataList, R.layout.sch_news_cardview_item);
                InitRecyclerView();
            } else {
                this.adapter.notifyDataSetChanged();
                swipereFreshlayout.setRefreshing(false);
            }
        }
        if (event instanceof RequestSubscriptionFind){
            List<SubscriptionFind> subFindList = event.getObjList();
            if (adapter == null) {
                this.adapter = new subscriptionfind_RecyclerViewAdapter(getContext(), subFindList, R.layout.fragment_subscription_find_item);
                InitRecyclerView();
            } else {
                this.adapter.notifyDataSetChanged();
                swipereFreshlayout.setRefreshing(false);
            }
        }
        if (event instanceof RequestSubscriptionContent){
            List<JyuSubscription> subList = event.getObjList();
            if (adapter == null) {
                this.adapter = new subscriptionshow_RecyclerViewAdapter(getContext(), subList, R.layout.fragment_subscription_show_item);
                InitRecyclerView();
            } else {
                this.adapter.notifyDataSetChanged();
                swipereFreshlayout.setRefreshing(false);
            }
        }

    }
}
