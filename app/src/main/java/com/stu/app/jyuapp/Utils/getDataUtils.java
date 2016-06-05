package com.stu.app.jyuapp.Utils;

import android.content.Context;
import android.util.Log;

import com.stu.app.jyuapp.Domain.JYU_Important_News;
import com.stu.app.jyuapp.Domain.SubscriptionFind;
import com.stu.app.jyuapp.EventOBJ.RequestNewsData;
import com.stu.app.jyuapp.EventOBJ.RequestSubscriptionFind;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;

/**
 * @author Jack
 * @time 2016/5/23 0023 17:29
 * @des TODO
 */

public class getDataUtils {
    public static synchronized void getSubcriptionFindData(final Context mcontext){
        BmobQuery<SubscriptionFind> query_userSub = new BmobQuery<SubscriptionFind>();
        query_userSub.findObjects(mcontext, new FindListener<SubscriptionFind>() {
            @Override
            public void onSuccess(List<SubscriptionFind> list) {
                Log.i("20160604","request sub find success size is ::"+list.size());
                EventBus.getDefault().postSticky(new RequestSubscriptionFind(list));
            }

            @Override
            public void onError(int i, String s) {

            }
        });
    }

    public static synchronized void getNewsData(final Context mcontext, final String Year_month) {

        //先从网络加载数据,
        if (NetWorkUtils.isOpenNetWork(mcontext)) {

            Log.i("20160601", "enter network loader");
            BmobQuery<JYU_Important_News> query_News = new BmobQuery<JYU_Important_News>();
            query_News.addWhereContains("Date", Year_month + "-");
            query_News.order("-Date");//字符前面有个-,就是降序,否则默认字符就是升序
            query_News.findObjects(mcontext, new FindListener<JYU_Important_News>() {
                @Override
                public void onSuccess(List<JYU_Important_News> mlist) {
                    Log.i("20160601", "enter network loader mlist size::"+mlist.size());
                    CacheUtils cacheUtils = new CacheUtils(mcontext);
                    cacheUtils.saveJsonToCacheFile(mlist, Year_month);
                    RequestNewsData data = new RequestNewsData(Year_month,mlist);

                    EventBus.getDefault().postSticky(data);
//                    EventBus.getDefault().postSticky(mlist);
                    cacheUtils = null;
                }

                @Override
                public void onError(int i, String s) {
                    //                CacheUtils cacheUtils = new CacheUtils(mcontext);
                    //                List<JYU_Important_News> list  = cacheUtils.getJsonStr();
                    //                EventBus.getDefault().postSticky(list);
                    //弹提示框，请检查网络
                }
            });

        } else {
            Log.i("20160601", "enter no internet loader");
            //如果没有，去硬盘
            CacheUtils cacheUtils = new CacheUtils(mcontext);
            //        final String year =TimeUtils.getServerTime(mcontext,"yy");
            List<JYU_Important_News> list = cacheUtils.getJsonStr(Year_month);
            if (list != null) {
                RequestNewsData data = new RequestNewsData(Year_month,list);
                EventBus.getDefault().postSticky(data);
//                EventBus.getDefault().postSticky(list);
                cacheUtils = null;
                list = null;
                //        return;
            }
        }

    }

}
