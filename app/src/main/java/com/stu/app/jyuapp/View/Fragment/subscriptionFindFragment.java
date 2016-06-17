package com.stu.app.jyuapp.View.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.stu.app.jyuapp.Controler.Adapter.subscriptionfind_RecyclerViewAdapter;
import com.stu.app.jyuapp.Controler.Request.RequestSubFind;
import com.stu.app.jyuapp.Model.Domain.JyuUser;
import com.stu.app.jyuapp.Model.Domain.SubscriptionFind;
import com.stu.app.jyuapp.Model.EventOBJ.RequestChangeBoomBtStatus;
import com.stu.app.jyuapp.Model.EventOBJ.RequestUpdateUserSub;
import com.stu.app.jyuapp.R;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import static com.avos.avoscloud.AVUser.getCurrentUser;
import static com.stu.app.jyuapp.Model.EventOBJ.RequestChangeBoomBtStatus.BoomMenuStatus.BOOM_INVISIBLE;
import static com.stu.app.jyuapp.Model.EventOBJ.RequestChangeBoomBtStatus.BoomMenuStatus.BOOM_NOTIFY;
import static com.stu.app.jyuapp.Model.EventOBJ.RequestChangeBoomBtStatus.BoomMenuStatus.BOOM_VISIBLE;

/**
 * A simple {@link Fragment} subclass.
 */
/*
* 先为用户维护一个List<map<订阅，boolean>>,将list中的订阅为true的所有内容[塞入]一个新的list中，再按时间[排序]，
*
* 使用一个serverNotifyDataChange来更新服务器数据
* {
*   //新建list
*   //将用户订阅的内容筛入新的list
*   //按时间排序
*   //返回给客户
* }
*
* 1.从服务器获取可订阅数据
* 2.显示
*   2.1.查看当前用户的订阅表，如果已订阅，改button，如果未订阅，正常显示
* 3.订阅事件
*   3.1.如果某个订阅item被用户订阅，update用户个人订阅表,更新当前界面
* */
public class subscriptionFindFragment extends BaseFragment {

    private List<SubscriptionFind> mSubscriptionFindList;
    private subscriptionfind_RecyclerViewAdapter adapter;
    private XRecyclerView rv_subscriptionfind;
    private LinearLayoutManager linearLayoutManager;

    public subscriptionFindFragment() {
    }
//

    private boolean lastState = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_subscription_find, container, false);
        InitView(view);
        InitData();
        InitEvent();

        return view;
    }

    private void InitEvent() {
        rv_subscriptionfind.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                new RequestSubFind(getContext()) {
                    @Override
                    public void RequestSuccess(List<SubscriptionFind> list) {
                        if (list.size() == 0) {
                            Toast.makeText(getContext(), "没有更多数据了", Toast.LENGTH_SHORT).show();
                        } else {
                            mSubscriptionFindList.removeAll(mSubscriptionFindList);
                            mSubscriptionFindList.addAll(list);
                            adapter.notifyDataSetChanged();
                        }
                        rv_subscriptionfind.refreshComplete();
                    }

                }.RequestSubFindData();
            }

            @Override
            public void onLoadMore() {
                rv_subscriptionfind.loadMoreComplete();
            }
        });
        rv_subscriptionfind.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
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

    private void InitView(View view) {
        rv_subscriptionfind = (XRecyclerView) view.findViewById(R.id.receclerview);
        linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rv_subscriptionfind.setLayoutManager(linearLayoutManager);
        rv_subscriptionfind.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
        rv_subscriptionfind.setLoadingMoreProgressStyle(ProgressStyle.BallRotate);
    }

    private void InitData() {
        new RequestSubFind(getContext()) {
            public void RequestSuccess(List<SubscriptionFind> list) {
                mSubscriptionFindList = list;
                adapter = new subscriptionfind_RecyclerViewAdapter(getContext(), list, R.layout.fragment_subscription_find_item);
                rv_subscriptionfind.setAdapter(adapter);
            }
        }.RequestSubFindData();
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void receiverBoomMenuNotify(RequestChangeBoomBtStatus.BoomMenuStatus boomMenuStatus) {
        if (boomMenuStatus == BOOM_NOTIFY) {
            lastState = false;
        }
    }


    @Subscribe(sticky = true, threadMode = ThreadMode.ASYNC)
    public void UpdateUserSub(RequestUpdateUserSub requestUpdateUserSub) {
        JyuUser jyuUser = getCurrentUser(JyuUser.class);
        jyuUser.setSubscription(requestUpdateUserSub.getObjList());
        jyuUser.saveInBackground();
    }
}
