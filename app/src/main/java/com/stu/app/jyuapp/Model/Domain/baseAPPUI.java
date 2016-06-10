package com.stu.app.jyuapp.Model.Domain;


import java.util.List;
import java.util.Map;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobDate;

/**
 * Created by m1 on 16-5-28.
 */

public  class baseAPPUI extends BmobObject {
    public   enum  Contact_Type{
        QQ,WEICHAT,PHONE

    }
    public Integer TYPE;
    public baseAPPUI(Integer TYPE){
        this.TYPE = TYPE;
    }

    public List<String> getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(List<String> imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public BmobDate getDate() {
        return Date;
    }

    public void setDate(BmobDate date) {
        Date = date;
    }

    public List<Map<Contact_Type,String>> getContact() {
        return Contact;
    }

    public void setContact(List<Map<Contact_Type,String>> contact) {
        Contact = contact;
    }

//    public com.stu.app.jyu.Domain.Comment getComment() {
//        return Comment;
//    }
//
//    public void setComment(com.stu.app.jyu.Domain.Comment comment) {
//        Comment = comment;
//    }
//
//    public com.stu.app.jyu.Domain.Collect getCollect() {
//        return Collect;
//    }
//
//    public void setCollect(com.stu.app.jyu.Domain.Collect collect) {
//        Collect = collect;
//    }

    public JyuUser getJyuUser() {
        return JyuUser;
    }

    public void setJyuUser(JyuUser jyuUser) {
        JyuUser = jyuUser;
    }

    private List<String> imageUrl;
    private String location;
    private BmobDate Date;
    private List<Map<Contact_Type,String>> Contact;
//    private Comment Comment;
    //    private Shared  Shared;
//    private Collect Collect;
    private JyuUser JyuUser;


}
