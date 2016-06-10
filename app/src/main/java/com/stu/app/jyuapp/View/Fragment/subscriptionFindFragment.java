package com.stu.app.jyuapp.View.Fragment;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.stu.app.jyuapp.View.Activity.SignInActivity;
import com.stu.app.jyuapp.Model.Adapter.subscriptionfind_RecyclerViewAdapter;
import com.stu.app.jyuapp.Model.Domain.JyuUser;
import com.stu.app.jyuapp.Model.Domain.SubscriptionFind;
import com.stu.app.jyuapp.Model.EventOBJ.RequestChangeBoomBtStatus;
import com.stu.app.jyuapp.Model.EventOBJ.RequestSubscriptionFind;
import com.stu.app.jyuapp.Model.EventOBJ.UpdateUserSub;
import com.stu.app.jyuapp.R;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.UpdateListener;

import static cn.bmob.v3.BmobUser.getCurrentUser;
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
public class subscriptionFindFragment extends Fragment {
    private List<SubscriptionFind> mSubscriptionFindList;
    private subscriptionfind_RecyclerViewAdapter adapter;
    private RecyclerView rv_subscriptionfind;
    private LinearLayoutManager linearLayoutManager;

    public subscriptionFindFragment() {
        // Required empty public constructor
    }

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
    private  boolean lastState=false;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        JyuUser user =BmobUser.getCurrentUser(getContext(), JyuUser.class);
        if ( user== null) {
            View SignInView = inflater.inflate(R.layout.fragment_please_signin, container, false);
            Button bt_please_signin = (Button) SignInView.findViewById(R.id.bt_please_signin);
            bt_please_signin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(getContext(), SignInActivity.class));
                }
            });
            return SignInView;
        } else {
            View view = inflater.inflate(R.layout.fragment_subscription_find, container, false);
            rv_subscriptionfind = (RecyclerView) view.findViewById(R.id.rv_subscriptionfind);
            linearLayoutManager = new LinearLayoutManager(getContext());
            rv_subscriptionfind.setLayoutManager(linearLayoutManager);
            rv_subscriptionfind.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                    super.onScrollStateChanged(recyclerView, newState);
                }
                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);
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
            return view;
        }
    }


    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    if (rv_subscriptionfind != null) {
                        if (adapter == null) {
                            Log.i("20160604", "adapter is empty");
                            adapter = new subscriptionfind_RecyclerViewAdapter(getContext(), mSubscriptionFindList, R.layout.fragment_subscription_find_item);
                            rv_subscriptionfind.setAdapter(adapter);
                        } else {
                            Log.i("20160604", "adapter no empty");
                            adapter.notifyDataSetChanged();
                        }
                    }
                    break;
                case 1:

                    break;
            }

        }
    };
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void receiverBoomMenuNotify(RequestChangeBoomBtStatus.BoomMenuStatus boomMenuStatus){
        if (boomMenuStatus== BOOM_NOTIFY){
            lastState=false;
        }
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void receiverSubList(RequestSubscriptionFind requestSubscriptionFind) {
        List<SubscriptionFind> list = requestSubscriptionFind.getSubscriptionFindList();
        Log.i("20160604", "receiver sub find success size is ::" + list.size());
        if (mSubscriptionFindList == null) {
            mSubscriptionFindList = list;
            mHandler.sendEmptyMessage(0);
        } else {
            mSubscriptionFindList = list;
            mHandler.sendEmptyMessage(1);
        }

    }

    @Subscribe(sticky = true, threadMode = ThreadMode.ASYNC)
    public void UpdateUserSub(UpdateUserSub updateUserSub) {
        JyuUser jyuUser = getCurrentUser(getContext(), JyuUser.class);
        jyuUser.setSubscription(updateUserSub.getMapList());
        jyuUser.update(getContext(), new UpdateListener() {
            @Override
            public void onSuccess() {
                Log.i("20160605", "update sub date success");
            }

            @Override
            public void onFailure(int i, String s) {
                Log.i("20160605", "update sub date fail::" + s);
            }
        });
    }

}
