package com.stu.app.jyuapp.Controler.Request;

import android.content.Context;
import android.widget.Toast;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.FindCallback;
import com.stu.app.jyuapp.Model.Domain.JyuNews;

import java.util.List;



public class RequestNews {
    private Context mContext;
    private int mREQUEST_ITEM_COUNT;
    private OnRequestSuccessListener mOnRequestSuccessListener = null;
    public RequestNews(Context context,int REQUEST_ITEM_COUNT) {
        mContext = context;
        mREQUEST_ITEM_COUNT = REQUEST_ITEM_COUNT;
    }
    public interface OnRequestSuccessListener {
         void done(List<JyuNews> list, AVException e);
    }
    public void setOnRequestListener(OnRequestSuccessListener listener) {
        mOnRequestSuccessListener = listener;
    }
    public  void RequestNewsData(){
        AVQuery<JyuNews> query_News = AVObject.getQuery(JyuNews.class).orderByDescending("Date").skip(mREQUEST_ITEM_COUNT).limit(20);
        query_News.findInBackground(new FindCallback<JyuNews>() {
            @Override
            public void done(List<JyuNews> list, AVException e) {
                    if (mOnRequestSuccessListener!=null){
                        mOnRequestSuccessListener.done(list,e);
                } else {
                    Toast.makeText(mContext, "error::" + e, Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
