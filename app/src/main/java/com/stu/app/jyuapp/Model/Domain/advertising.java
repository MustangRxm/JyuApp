package com.stu.app.jyuapp.Model.Domain;

import com.avos.avoscloud.AVClassName;
import com.avos.avoscloud.AVObject;

/**
 * Created by m1 on 16-6-16.
 */
@AVClassName("advertising")
public class advertising extends AVObject {
    public String gethead_img() {
        return getString("head_img");
    }

    public void sethead_img(String head_img) {
        put("RequestDate",head_img) ;
    }

    public String gettitle() {
        return getString("title");
    }

    public void settitle(String title) {
        put("title",title) ;
    }

    public String getlink() {
        return getString("link");
    }

    public void setlink(String link) {
        put("link",link) ;
    }

}
