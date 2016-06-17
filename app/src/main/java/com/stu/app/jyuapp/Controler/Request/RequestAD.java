package com.stu.app.jyuapp.Controler.Request;

import android.content.Context;
import android.widget.Toast;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.FindCallback;
import com.stu.app.jyuapp.Model.Domain.advertising;

import java.util.List;

/**
 * Created by m1 on 16-6-16.
 */

public abstract class RequestAD {
    private Context mContext;
    public RequestAD(Context context) {
     mContext = context;
    }



    public  void RequestADData(){
        AVQuery<advertising> query_SubFind = AVObject.getQuery(advertising.class);
//        RequestInBackground(query_SubFind);
        query_SubFind.findInBackground(new FindCallback<advertising>() {
            @Override
            public void done(List<advertising> list, AVException e) {
                if (e == null) {
                         DoSomethingInSubFindSuccess(list);
                } else {
                    Toast.makeText(mContext, "error::" + e, Toast.LENGTH_LONG).show();
                }
            }
        });
//        return null;
    }




    public abstract void RequestSuccess(List<advertising> list);
    public void DoSomethingInSubFindSuccess(List<advertising> list) {
        RequestSuccess(list);
    }
}
