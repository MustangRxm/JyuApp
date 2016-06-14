package com.stu.app.jyuapp.Controler.Utils;

import android.content.Context;
import android.util.Log;

import com.stu.app.jyuapp.Model.Domain.JyuNews;
import com.stu.app.jyuapp.Model.Domain.JyuSubChoice;
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

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.FindListener;

/**
 * @author Jack
 * @time 2016/5/23 0023 17:29
 * @des TODO
 */

public class getDataUtils {
    /**
     * @param1 url
     * @param2 userID
     **/
    public static synchronized void getsubshowVPdata(Context mcontext) {
        List<String> listsource = new ArrayList<>();
        listsource.add("http://www.adaymag.com/wp-content/uploads/2014/08/adaymag-aday-2014-v3-1-regular.png");
        listsource.add("http://7xrn7f.com1.z0.glb.clouddn.com/16-6-7/25192638.jpg");
        listsource.add("http://ww4.sinaimg.cn/large/74311666jw1f4jpgux3cgj203k03kglj.jpg");
        EventBus.getDefault().postSticky(new RequestVPdata(listsource));
    }

    public static synchronized void getUserSubcriptionContent(Context mcontext) {
        //                url = "http://45.78.4.50:8000/RssParse?userID=1e20c632c0"
        String url = "http://45.78.4.50:8000/RssParse";
        //        String url = "http://10.0.2.2:8000/RssParse";
//        if (BmobUser.getCurrentUser(mcontext) == null) {
//            Log.i("20160609", "null user");
//            return;
//        } else {
            JyuUser user = BmobUser.getCurrentUser(mcontext, JyuUser.class);
            if (user == null) {
                Log.i("20160614","user is null");
                //把精选的数据送过去
                BmobQuery<JyuSubChoice> query = new BmobQuery<>();
                query.order("-TimeID");
                query.findObjects(mcontext, new FindListener<JyuSubChoice>() {
                    @Override
                    public void onSuccess(List<JyuSubChoice> list) {
                        EventBus.getDefault().postSticky(new RequestSubscriptionChoice(list));
                    }

                    @Override
                    public void onError(int i, String s) {

                    }
                });
            } else {
                //user不等于null
                List<BmobQuery<JyuSubscription>> queryList = new ArrayList<>();
                BmobQuery<JyuSubscription> eq1;
                List<String> sublist = user.getSubscription();
                if (sublist.size() != 0) {
                    for (String each : sublist) {
                        eq1 = new BmobQuery<>();
                        Log.i("20160614", each);
                        eq1.addWhereEqualTo("subFindID", each);
                        queryList.add(eq1);
                        eq1 = null;
                    }
                    BmobQuery<JyuSubscription> mainQUery = new BmobQuery<>();
                    mainQUery.or(queryList).order("-TimeID");
                    mainQUery.findObjects(mcontext, new FindListener<JyuSubscription>() {
                        @Override
                        public void onSuccess(List<JyuSubscription> list) {
                            Log.i("20160614", "list::::size ::" + list.size());
//                            Log.i("20160614", "list content timeid::" + list.get(1).getTimeID());
                            EventBus.getDefault().postSticky(new RequestSubscriptionContent(list));
                        }

                        @Override
                        public void onError(int i, String s) {

                        }
                    });
                } else {
                    //把精选的数据送过去
                    BmobQuery<JyuSubChoice> query = new BmobQuery<>();
                    query.order("-TimeID");
                    query.findObjects(mcontext, new FindListener<JyuSubChoice>() {
                        @Override
                        public void onSuccess(List<JyuSubChoice> list) {
                            Log.i("20160614","request sub choice list size::"+list.size());
                            EventBus.getDefault().postSticky(new RequestSubscriptionChoice(list));
                        }

                        @Override
                        public void onError(int i, String s) {

                        }
                    });
                    //给他更新精选推送

                    //            String userID = user.getObjectId();
                    //            OkHttpClient client = new OkHttpClient();
                    //            Request request = new Request.Builder()
                    //                    .url(url + "?userID=" + userID)
                    //                    .build();
                    //            client.newCall(request).enqueue(new Callback() {
                    //                @Override
                    //                public void onFailure(Call call, IOException e) {
                    //
                    //                }
                    //
                    //                @Override
                    //                public void onResponse(Call call, Response response) throws IOException {
                    //                    String JsonString = response.body().string();
                    //                    EventBus.getDefault().postSticky(new RequestSubscriptionContent(JsonString));
                    //                }
                    //            });
                }
            }
//        }
    }

    public static synchronized void getSubcriptionFindData(final Context mcontext) {
        BmobQuery<SubscriptionFind> query_userSub = new BmobQuery<SubscriptionFind>();
        query_userSub.findObjects(mcontext, new FindListener<SubscriptionFind>() {
            @Override
            public void onSuccess(List<SubscriptionFind> list) {
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

            BmobQuery<JyuNews> query_News = new BmobQuery<JyuNews>();
            query_News.addWhereContains("Date", Year_month + "-");
            query_News.order("-Date");//字符前面有个-,就是降序,否则默认字符就是升序
            query_News.findObjects(mcontext, new FindListener<JyuNews>() {
                @Override
                public void onSuccess(List<JyuNews> mlist) {
                    CacheUtils cacheUtils = new CacheUtils(mcontext);
                    cacheUtils.saveJsonToCacheFile(mlist, Year_month);
                    RequestNewsData data = new RequestNewsData(Year_month, mlist);

                    EventBus.getDefault().postSticky(data);
                    //                    EventBus.getDefault().postSticky(mlist);
                    cacheUtils = null;
                }

                @Override
                public void onError(int i, String s) {
                    //                CacheUtils cacheUtils = new CacheUtils(mcontext);
                    //                List<JyuNews> list  = cacheUtils.getJsonStr();
                    //                EventBus.getDefault().postSticky(list);
                    //弹提示框，请检查网络
                }
            });

        } else {
            //如果没有，去硬盘
            CacheUtils cacheUtils = new CacheUtils(mcontext);
            //        final String year =TimeUtils.getServerTime(mcontext,"yy");
            List<JyuNews> list = cacheUtils.getJsonStr(Year_month);
            if (list != null) {
                RequestNewsData data = new RequestNewsData(Year_month, list);
                EventBus.getDefault().postSticky(data);
                //                EventBus.getDefault().postSticky(list);
                cacheUtils = null;
                list = null;
                //        return;
            }
        }

    }

}
