package com.stu.app.jyuapp.Controler.Utils;

import android.content.Context;
import android.widget.Toast;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.FindCallback;
import com.stu.app.jyuapp.Model.Domain.JyuNews;
import com.stu.app.jyuapp.Model.Domain.JyuSubscription;
import com.stu.app.jyuapp.Model.Domain.JyuUser;
import com.stu.app.jyuapp.Model.Domain.SubscriptionFind;
import com.stu.app.jyuapp.Model.EventOBJ.RequestNewsData;
import com.stu.app.jyuapp.Model.EventOBJ.RequestSubscriptionChoice;
import com.stu.app.jyuapp.Model.EventOBJ.RequestSubscriptionContent;
import com.stu.app.jyuapp.Model.EventOBJ.RequestSubscriptionFind;
import com.stu.app.jyuapp.Model.EventOBJ.RequestVPdata;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author Jack
 * @time 2016/5/23 0023 17:29
 * @des TODO
 */

public class getDataUtils {
    //    private Context mContext;

    /**
     * @param1 url
     * @param2 userID
     **/
    public synchronized static void getsubshowVPdata() {
        List<String> listsource = new ArrayList<>();
        listsource.add("http://www.adaymag.com/wp-content/uploads/2014/08/adaymag-aday-2014-v3-1-regular.png");
        listsource.add("http://7xrn7f.com1.z0.glb.clouddn.com/16-6-7/25192638.jpg");
        listsource.add("http://ww4.sinaimg.cn/large/74311666jw1f4jpgux3cgj203k03kglj.jpg");
        EventBus.getDefault().postSticky(new RequestVPdata(listsource));
    }

    public static void getRandomSubscriptionData() {
        AVQuery<JyuSubscription> query_sub = AVObject.getQuery(JyuSubscription.class);
        query_sub.orderByDescending("pubDate").limit(100).findInBackground(new FindCallback<JyuSubscription>() {
            @Override
            public void done(List<JyuSubscription> list, AVException e) {
                if (e == null) {
                    List<JyuSubscription> list_ran = new ArrayList<JyuSubscription>();
                    //随机发送40条
                    Random rand = new Random();
                    for (int i = 0; i < 39; i++)
                        list_ran.add(list.remove(rand.nextInt(list.size())));
                    EventBus.getDefault().postSticky(new RequestSubscriptionChoice(list_ran));
                }

            }
        });
    }

    public synchronized static void getUserSubcriptionContent(Context mContext,int PageNum) {
        JyuUser user = AVUser.getCurrentUser(JyuUser.class);
        if (user == null) {
            //把精选的数据送过去
            //从订阅库中查找最新的100条数据，再随机显示,不要太复杂
            getRandomSubscriptionData();
        } else {
            List<String> subList = user.getSubscription();
            if ((subList.size()) != 0) {
                AVQuery<JyuSubscription> query_sublist = AVObject.getQuery(JyuSubscription.class);
                query_sublist.whereContainedIn("subFindID", subList)
                        .orderByDescending("pubDate")
                        .limit(20)
                        .skip(0*PageNum)
                        .findInBackground(new FindCallback<JyuSubscription>() {
                            @Override
                            public void done(List<JyuSubscription> list, AVException e) {
                                if (e == null)
                                    EventBus.getDefault().postSticky(new RequestSubscriptionContent(list));
                            }
                        });
            } else {
                getRandomSubscriptionData();
            }
        }
    }

    public synchronized static void getSubcriptionFindData(final Context mContext) {
        AVQuery<SubscriptionFind> query_SubFind = AVObject.getQuery(SubscriptionFind.class);
        query_SubFind.findInBackground(new FindCallback<SubscriptionFind>() {
            @Override
            public void done(List<SubscriptionFind> list, AVException e) {
                if (e == null) {
                    EventBus.getDefault().postSticky(new RequestSubscriptionFind(list));
                } else {
                    Toast.makeText(mContext, "error::" + e, Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public static synchronized void getNewsData(final Context mContext, final String Year_month) {

        //先从网络加载数据,
        if (NetWorkUtils.isOpenNetWork(mContext)) {
            AVQuery<JyuNews> query_News = AVObject.getQuery(JyuNews.class);
            query_News.whereContains("RequestDate", Year_month).orderByDescending("RequestDate").findInBackground(new FindCallback<JyuNews>() {
                @Override
                public void done(List<JyuNews> list, AVException e) {
                    if (e == null) {
                        CacheUtils cacheUtils = new CacheUtils(mContext);
                        cacheUtils.saveJsonToCacheFile(list, Year_month);
                        RequestNewsData data = new RequestNewsData(Year_month, list);
                        EventBus.getDefault().postSticky(data);
                    } else {
                        Toast.makeText(mContext, "error::" + e, Toast.LENGTH_LONG).show();
                    }
                }
            });
        } else {
            //            如果没有，去硬盘
            CacheUtils cacheUtils = new CacheUtils(mContext);
            List<JyuNews> list = cacheUtils.getJsonStr(Year_month);
            if (list != null) {
                RequestNewsData data = new RequestNewsData(Year_month, list);
                EventBus.getDefault().postSticky(data);
            }
        }

    }

}
