package com.stu.app.jyuapp.Controler.Request;

import android.content.Context;
import android.widget.Toast;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.FindCallback;
import com.stu.app.jyuapp.Model.Domain.JyuSubscription;
import com.stu.app.jyuapp.Model.Domain.JyuUser;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by m1 on 16-6-16.
 */

public class RequestSubContent {
    public final static int NO_USER = 0x401;
    public final static int IS_USER = 0x402;
    private Context mContext;
    private int mREQUEST_ITEM_COUNT;
    private OnRequestSuccessListener mOnRequestSuccessListener = null;

    public RequestSubContent(Context context, int REQUEST_ITEM_COUNT) {
        mContext = context;
        mREQUEST_ITEM_COUNT = REQUEST_ITEM_COUNT;
    }

    public interface OnRequestSuccessListener {
        public void done(List<JyuSubscription> list, AVException e);
    }

    public void setOnRequestListener(OnRequestSuccessListener listener) {
        mOnRequestSuccessListener = listener;
    }

    public void RequestRandomNewsData() {
        AVQuery<JyuSubscription> query_RandomSub = AVObject.getQuery(JyuSubscription.class);
        query_RandomSub = query_RandomSub.orderByDescending("pubDate").limit(100);
        //            query_News = AVObject.getQuery(JyuSubscription.class).orderByDescending("Date").skip(mREQUEST_ITEM_COUNT).limit(20);
        query_RandomSub.findInBackground(new FindCallback<JyuSubscription>() {
            @Override
            public void done(List<JyuSubscription> list, AVException e) {
                List<JyuSubscription> list_ran = new ArrayList<JyuSubscription>();
                //随机发送40条
                Random rand = new Random();
                for (int i = 0; i < 39; i++)
                    list_ran.add(list.remove(rand.nextInt(list.size())));
                if (mOnRequestSuccessListener != null) {
                    mOnRequestSuccessListener.done(list_ran, e);
                } else {
                    Toast.makeText(mContext, "error::" + e, Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public void RequestNewsData() {
        AVQuery<JyuSubscription> query_Sub = null;
        JyuUser user = AVUser.getCurrentUser(JyuUser.class);
        if (user == null) {
            RequestRandomNewsData();
        } else if (user != null) {
            List<String> list = user.getSubscription();
            if (list.size() == 0) {
                RequestRandomNewsData();
            } else {
                query_Sub = AVObject.getQuery(JyuSubscription.class).whereContainedIn("subFindID", list)
                        .orderByDescending("pubDate")
                        .limit(20)
                        .skip(mREQUEST_ITEM_COUNT);
                query_Sub.findInBackground(new FindCallback<JyuSubscription>() {
                    @Override
                    public void done(List<JyuSubscription> list, AVException e) {
                        if (mOnRequestSuccessListener != null) {
                            mOnRequestSuccessListener.done(list, e);
                        } else {
                            Toast.makeText(mContext, "error::" + e, Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        }
    }
}
