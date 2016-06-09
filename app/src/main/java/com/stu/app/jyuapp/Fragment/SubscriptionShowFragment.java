package com.stu.app.jyuapp.Fragment;


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

import com.google.gson.Gson;
import com.nightonke.boommenu.BoomMenuButton;
import com.stu.app.jyuapp.Adapter.subscriptionshow_RecyclerViewAdapter;
import com.stu.app.jyuapp.Domain.SubscriptionContent;
import com.stu.app.jyuapp.EventOBJ.RequestChangeBoomBtStatus;
import com.stu.app.jyuapp.EventOBJ.RequestSubscriptionContent;
import com.stu.app.jyuapp.R;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import static com.stu.app.jyuapp.EventOBJ.RequestChangeBoomBtStatus.BoomMenuStatus.BOOM_INVISIBLE;
import static com.stu.app.jyuapp.EventOBJ.RequestChangeBoomBtStatus.BoomMenuStatus.BOOM_NOTIFY;
import static com.stu.app.jyuapp.EventOBJ.RequestChangeBoomBtStatus.BoomMenuStatus.BOOM_VISIBLE;

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
    private final int INIT_DATA = 0x101;
    private final int UPDATE_DATA = 0x102;
    private subscriptionshow_RecyclerViewAdapter adapter = null;
    private BoomMenuButton boomMenuButton;

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
        // Required empty public constructor
    }

    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case INIT_DATA:
                    List<SubscriptionContent.Totalitem> list = (List<SubscriptionContent.Totalitem>) msg.obj;
                    adapter = new subscriptionshow_RecyclerViewAdapter(getContext(), list, R.layout.fragment_subscription_show_item);
                    rv_subscription_show.setAdapter(adapter);
                    break;
                case UPDATE_DATA:
                    break;
            }

        }
    };
//    private static int lastState=0;
private  boolean lastState=false;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             final Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_subscription_show, container, false);
        rv_subscription_show = (RecyclerView) view.findViewById(R.id.rv_subscription_show);
        linearLayoutManager = new LinearLayoutManager(getContext());
        srl_subscription = (SwipeRefreshLayout) view.findViewById(R.id.srl_subscription);
        srl_subscription.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                srl_subscription.setRefreshing(false);
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
                if ((dy>0)&&(!lastState)){
                    lastState=true;
                    EventBus.getDefault().post(BOOM_INVISIBLE);
                }else if ((dy<0)&&(lastState)){
                    EventBus.getDefault().post(BOOM_VISIBLE);
                    lastState=false;
                }

            }
        });
        rv_subscription_show.setLayoutManager(linearLayoutManager);
        return view;
    }
//
//    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
//    public void receivertest(RequestTest test) {
//        Log.i("20160608test", "enter bus:::" + test.getTeststr());
//        Gson gson = new Gson();
//        SubscriptionContent subscriptionContent = gson.fromJson(test.getTeststr(), SubscriptionContent.class);
//        Log.i("20160608test", "pubdate::in bus" + subscriptionContent.getTotalitem().get(0).getPubDate());
//
//    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void receiverBoomMenuNotify(RequestChangeBoomBtStatus.BoomMenuStatus boomMenuStatus){
        if (boomMenuStatus== BOOM_NOTIFY){
            lastState=false;
        }
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void receiverSubscriptionUrl(RequestSubscriptionContent content) {
        String JsonStr = content.getSubscriptioncontent();
        Gson gson = new Gson();
        SubscriptionContent subscriptionContent = gson.fromJson(JsonStr, SubscriptionContent.class);
        List<SubscriptionContent.Totalitem> list = subscriptionContent.getTotalitem();
        if (adapter == null) {
            Message msg = mHandler.obtainMessage();
            msg.what = INIT_DATA;
            msg.obj = list;
            mHandler.sendMessage(msg);
        } else {
            Message msg = mHandler.obtainMessage();
            msg.what = UPDATE_DATA;
            msg.obj = list;
            mHandler.sendMessage(msg);

        }
    }
}
