package com.stu.app.jyuapp.Utils;

import android.content.Context;

import com.stu.app.jyuapp.Domain.JYU_Important_News;
import com.stu.app.jyuapp.Domain.JyuUser;
import com.stu.app.jyuapp.Domain.SubscriptionFind;
import com.stu.app.jyuapp.EventOBJ.RequestNewsData;
import com.stu.app.jyuapp.EventOBJ.RequestSubscriptionContent;
import com.stu.app.jyuapp.EventOBJ.RequestSubscriptionFind;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.FindListener;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

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

    //     class RequestData extends AsyncTask<String,Float,String>{
    //        @Override
    //        protected String doInBackground(String... params) {
    //            String url = params[0];
    //            String userID= params[1];
    //            OkHttpClient client = new OkHttpClient();
    //            RequestBody requestbody = new FormBody
    //                    .Builder()
    //                    .add("userID",userID)
    //                    .build();
    //            Request request = new Request.Builder()
    //                    .post(requestbody)
    //                    .url(url).build();
    //            final String[] result = new String[1];
    //            client.newCall(request).enqueue(new Callback() {
    //                @Override
    //                public void onFailure(Call call, IOException e) {
    //                    Log.i("20160608","error"+ e.toString());
    //                }
    //
    //                @Override
    //                public void onResponse(Call call, Response response) throws IOException {
    //
    //                    result[0] = response.body().string();
    //                    Log.i("20160608", result[0]);
    //                }
    //            });
    //            return result[0];
    //        }
    //    }
    public static synchronized void getUserSubcriptionContent(Context mcontext) {
        //        url = "http://45.78.4.50:8000/RssParse?userID=1e20c632c0"
//        String url = "http://45.78.4.50:8000/RssParse";
        String url = "http://10.0.2.2:8000/RssParse";
        JyuUser user = BmobUser.getCurrentUser(mcontext, JyuUser.class);
        if (user == null) {
            //把精选的数据送过去
        } else {
            String userID = user.getObjectId();
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(url + "?userID=" + userID)
                    .build();
            //                    EventBus.getDefault().postSticky(new RequestTest(JsonString));
            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {

                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    String JsonString = response.body().string();
                    EventBus.getDefault().postSticky(new RequestSubscriptionContent(JsonString));
                }
            });
        }
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

            BmobQuery<JYU_Important_News> query_News = new BmobQuery<JYU_Important_News>();
            query_News.addWhereContains("Date", Year_month + "-");
            query_News.order("-Date");//字符前面有个-,就是降序,否则默认字符就是升序
            query_News.findObjects(mcontext, new FindListener<JYU_Important_News>() {
                @Override
                public void onSuccess(List<JYU_Important_News> mlist) {
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
                    //                List<JYU_Important_News> list  = cacheUtils.getJsonStr();
                    //                EventBus.getDefault().postSticky(list);
                    //弹提示框，请检查网络
                }
            });

        } else {
            //如果没有，去硬盘
            CacheUtils cacheUtils = new CacheUtils(mcontext);
            //        final String year =TimeUtils.getServerTime(mcontext,"yy");
            List<JYU_Important_News> list = cacheUtils.getJsonStr(Year_month);
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
