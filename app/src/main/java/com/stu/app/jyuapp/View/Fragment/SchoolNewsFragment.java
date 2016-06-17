package com.stu.app.jyuapp.View.Fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.avos.avoscloud.AVException;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.stu.app.jyuapp.Controler.Adapter.BaseRecyclerViewAdapter;
import com.stu.app.jyuapp.Controler.Adapter.sch_news_App_RecyclerViewAdapter;
import com.stu.app.jyuapp.Controler.Request.RequestNews;
import com.stu.app.jyuapp.Model.Domain.JyuNews;
import com.stu.app.jyuapp.Model.EventOBJ.RequestChangeBoomBtStatus;
import com.stu.app.jyuapp.R;
import com.stu.app.jyuapp.View.Activity.News_Entity_Activity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import static com.stu.app.jyuapp.Model.EventOBJ.RequestChangeBoomBtStatus.BoomMenuStatus.BOOM_INVISIBLE;
import static com.stu.app.jyuapp.Model.EventOBJ.RequestChangeBoomBtStatus.BoomMenuStatus.BOOM_NOTIFY;
import static com.stu.app.jyuapp.Model.EventOBJ.RequestChangeBoomBtStatus.BoomMenuStatus.BOOM_VISIBLE;

/**
 * 底部使用导航栏,分3部分,直接爬综合要闻,校园公告,校园动态,如果设置了学院,再加个学院动态要闻
 * A simple {@link Fragment} subclass.
 */
public class SchoolNewsFragment extends BaseFragment {
    private sch_news_App_RecyclerViewAdapter sch_news_Rv_Adapter;
    private XRecyclerView rv_sch_news_app;
    private LinearLayoutManager linearLayoutManager;
    private List<JyuNews> list_sources;
    private int REQUEST_ITEM_COUNT = 0;
    private boolean lastState = false;



    public SchoolNewsFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_school_news, container, false);
        initView(view);
        initData();
        initEvent();
        return view;
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void receiverBoomMenuNotify(RequestChangeBoomBtStatus.BoomMenuStatus boomMenuStatus) {
        if (boomMenuStatus == BOOM_NOTIFY) {
            lastState = false;
        }
    }

    private void initEvent() {
        rv_sch_news_app.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                REQUEST_ITEM_COUNT = 0;
                RequestNews mRequestNews = new RequestNews(getContext(), REQUEST_ITEM_COUNT);
                mRequestNews.setOnRequestListener(new RequestNews.OnRequestSuccessListener() {
                    @Override
                    public void done(List<JyuNews> list, AVException e) {
                        if (e == null) {
                            REQUEST_ITEM_COUNT += list.size();
                            if (list_sources != null) {
                                list_sources.removeAll(list_sources);
                                list_sources.addAll(list);
                                sch_news_Rv_Adapter.notifyDataSetChanged();
                                rv_sch_news_app.refreshComplete();
                            }
                        }
                    }
                });
                mRequestNews.RequestNewsData();

            }

            @Override
            public void onLoadMore() {
                RequestNews mRequestNews1 = new RequestNews(getContext(), REQUEST_ITEM_COUNT);
                mRequestNews1.setOnRequestListener(new RequestNews.OnRequestSuccessListener() {
                    @Override
                    public void done(List<JyuNews> list, AVException e) {
                        if (e == null) {
                            REQUEST_ITEM_COUNT += list.size();
                            try {
                                list_sources.addAll(list);
                            } catch (Exception a) {
                            } finally {
                                sch_news_Rv_Adapter.notifyDataSetChanged();
                                rv_sch_news_app.loadMoreComplete();
                            }

                        }
                    }
                });
                mRequestNews1.RequestNewsData();
            }
        });
        rv_sch_news_app.addOnScrollListener(new RecyclerView.OnScrollListener() {

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
    }

    private void initView(View view) {
        rv_sch_news_app = (XRecyclerView) view.findViewById(R.id.recyclerview);
        linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rv_sch_news_app.setLayoutManager(linearLayoutManager);
        rv_sch_news_app.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
        rv_sch_news_app.setLoadingMoreProgressStyle(ProgressStyle.BallRotate);
    }

    private void initData() {
        RequestNews requestNews = new RequestNews(getContext(), REQUEST_ITEM_COUNT);
        requestNews.setOnRequestListener(new RequestNews.OnRequestSuccessListener() {
            @Override
            public void done(List<JyuNews> list, AVException e) {
                if (e == null) {
                    REQUEST_ITEM_COUNT += list.size();
                    list_sources = list;
                    sch_news_Rv_Adapter = new sch_news_App_RecyclerViewAdapter(getContext(), list_sources, R.layout.sch_news_cardview_item);
                    sch_news_Rv_Adapter.setOnItemClickListener(new BaseRecyclerViewAdapter.OnRecyclerViewItemClickListener() {
                        @Override
                        public void onItemClick(View view, int position) {
                            Intent intent = new Intent(getContext(), News_Entity_Activity.class);
                            EventBus.getDefault().postSticky(list_sources.get(position));
                            startActivity(intent);
                        }
                    });
                    rv_sch_news_app.setAdapter(sch_news_Rv_Adapter);
                }
            }
        });
        requestNews.RequestNewsData();
    }
}
