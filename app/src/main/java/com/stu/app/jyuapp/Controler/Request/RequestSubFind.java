package com.stu.app.jyuapp.Controler.Request;

import android.content.Context;
import android.widget.Toast;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.FindCallback;
import com.stu.app.jyuapp.Model.Domain.SubscriptionFind;

import java.util.List;

/**
 * Created by m1 on 16-6-16.
 */

public abstract class RequestSubFind {
    private Context mContext;
    public RequestSubFind(Context context) {
     mContext = context;
    }

    public  void RequestSubFindData(){
        AVQuery<SubscriptionFind> query_SubFind = AVObject.getQuery(SubscriptionFind.class);
//        RequestInBackground(query_SubFind);
        query_SubFind.findInBackground(new FindCallback<SubscriptionFind>() {
            @Override
            public void done(List<SubscriptionFind> list, AVException e) {
                if (e == null) {
                         DoSomethingInSubFindSuccess(list);
                } else {
                    Toast.makeText(mContext, "error::" + e, Toast.LENGTH_LONG).show();
                }
            }
        });
    }




    public abstract void RequestSuccess(List<SubscriptionFind> list);
    public void DoSomethingInSubFindSuccess(List<SubscriptionFind> list) {
        RequestSuccess(list);
    }
}
