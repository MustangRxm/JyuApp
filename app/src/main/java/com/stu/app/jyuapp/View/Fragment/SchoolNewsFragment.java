package com.stu.app.jyuapp.View.Fragment;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.stu.app.jyuapp.Controler.Adapter.BaseRecyclerViewAdapter;
import com.stu.app.jyuapp.Controler.Adapter.sch_news_App_RecyclerViewAdapter;
import com.stu.app.jyuapp.Controler.Utils.getDataUtils;
import com.stu.app.jyuapp.Model.Domain.JyuNews;
import com.stu.app.jyuapp.Model.EventOBJ.RequestDate;
import com.stu.app.jyuapp.Model.EventOBJ.RequestChangeBoomBtStatus;
import com.stu.app.jyuapp.Model.EventOBJ.RequestNewsData;
import com.stu.app.jyuapp.R;
import com.stu.app.jyuapp.View.Activity.News_Entity_Activity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import static android.support.v7.widget.RecyclerView.SCROLL_STATE_IDLE;
import static com.stu.app.jyuapp.Model.EventOBJ.RequestChangeBoomBtStatus.BoomMenuStatus.BOOM_INVISIBLE;
import static com.stu.app.jyuapp.Model.EventOBJ.RequestChangeBoomBtStatus.BoomMenuStatus.BOOM_NOTIFY;
import static com.stu.app.jyuapp.Model.EventOBJ.RequestChangeBoomBtStatus.BoomMenuStatus.BOOM_VISIBLE;

/**
 * 底部使用导航栏,分3部分,直接爬综合要闻,校园公告,校园动态,如果设置了学院,再加个学院动态要闻
 * A simple {@link Fragment} subclass.
 */
public class SchoolNewsFragment extends Fragment {
    private sch_news_App_RecyclerViewAdapter sch_news_Rv_Adapter;
    private List<JyuNews> mList = new ArrayList<JyuNews>();
    private Context mcontext;
    private LinearLayoutManager  linearLayoutManager;
    private String year_month;

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

    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    if (sch_news_Rv_Adapter == null) {
                        sch_news_Rv_Adapter = new sch_news_App_RecyclerViewAdapter(getContext(), mList, R.layout.sch_news_cardview_item);
                        sch_news_Rv_Adapter.setOnItemClickListener(new BaseRecyclerViewAdapter.OnRecyclerViewItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {
                                Intent intent = new Intent(getContext(), News_Entity_Activity.class);
                                EventBus.getDefault().postSticky(mList.get(position));
                                startActivity(intent);
                            }
                        });
                        rv_sch_news_app.setAdapter(sch_news_Rv_Adapter);
                    } else {
                        sch_news_Rv_Adapter.notifyDataSetChanged();
                        srl_sch_news_app.setRefreshing(false);
                    }
                    break;
            }
        }
    };

    public SchoolNewsFragment() {
    }

    List<JyuNews> list_sources;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mcontext = getContext();
            View view = inflater.inflate(R.layout.fragment_school_news, container, false);
            bindView(view);
            initData();
            initView();
            initEvent();
            return view;
    }

    private synchronized void TimeSub(String year_month) {
        String[] times = this.year_month.split("-");
        int year = Integer.parseInt(times[0]);
        int mon = Integer.parseInt(times[1]);
        if (mon > 1) {
            mon = mon - 1;
        } else if (mon == 1) {
                year = year - 1;
            mon = 12;
        }
        if (mon < 10) {
            this.year_month = year + "-0" + mon;
        } else {
            this.year_month = year + "-" + mon;
        }
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void receiverNewsData(RequestNewsData data) {
        year_month = (String) data.getObjT();
        List nd = data.getObjList();
        if (nd.size() == 0) {
            TimeSub(year_month);
            EventBus.getDefault().post(new RequestDate(year_month));
        } else {
            this.mList.addAll(nd);
            mHandler.sendEmptyMessage(1);
        }
    }

    @Subscribe(threadMode = ThreadMode.ASYNC)
    public void RequestNewsAgain(RequestDate requestDate) {
//        getDataUtils gdu = new getDataUtils(getContext());
        getDataUtils.getNewsData(getContext(), (String) requestDate.getObjT());

    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void receiverBoomMenuNotify(RequestChangeBoomBtStatus.BoomMenuStatus boomMenuStatus){
        if (boomMenuStatus== BOOM_NOTIFY){
            lastState=false;
        }
    }
    private int lastVisibleItem;
    private  boolean lastState=false;
    private void initEvent() {
        srl_sch_news_app.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                srl_sch_news_app.setRefreshing(false);
            }

        });
        rv_sch_news_app.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == SCROLL_STATE_IDLE && lastVisibleItem + 1 == sch_news_Rv_Adapter.getItemCount()) {
                    //update mList
                    srl_sch_news_app.setRefreshing(true);
                    TimeSub(year_month);
                    EventBus.getDefault().post(new RequestDate(year_month));
                }
            }

            private int findMax(int[] lastPositions) {
                int max = lastPositions[0];
                for (int value : lastPositions) {
                    if (value > max) {
                        max = value;
                    }
                }
                return max;
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                lastVisibleItem =linearLayoutManager.findLastVisibleItemPosition();
                    //1.往下滚，消失
                    //2.往上滚，显示
                    if ((dy>0)&&(!lastState)){
                        lastState=true;
                        EventBus.getDefault().post(BOOM_INVISIBLE);
                    }else if ((dy<0)&&(lastState)){
                        EventBus.getDefault().post(BOOM_VISIBLE);
                        lastState=false;
                    }

            }
        });
    }

    private void initView() {
        linearLayoutManager = new LinearLayoutManager(getContext());
        rv_sch_news_app.setLayoutManager(linearLayoutManager);
    }

    private void initData() {
    }

    private RecyclerView rv_sch_news_app;
    private SwipeRefreshLayout srl_sch_news_app;

    private void bindView(View view) {
        rv_sch_news_app = (RecyclerView) view.findViewById(R.id.rv_sch_news_app);
        srl_sch_news_app = (SwipeRefreshLayout) view.findViewById(R.id.srl_sch_news_app);
    }


}
