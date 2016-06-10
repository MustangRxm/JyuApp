package com.stu.app.jyuapp.Model.Domain;

/**
 * @author Jack
 * @time 2016/5/4 0004 22:29
 * @des TODO
 */
public class AppItem {
    public int getImageView() {
        return mImageView;
    }

    public String getTextView() {
        return mTextView;
    }

    private int mImageView;
    private String mTextView;
    public AppItem(int imageView, String textView){
        mImageView=imageView;
        mTextView=textView;
    }

}
