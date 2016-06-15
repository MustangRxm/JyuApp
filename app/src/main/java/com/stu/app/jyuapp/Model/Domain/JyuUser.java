package com.stu.app.jyuapp.Model.Domain;

import com.avos.avoscloud.AVUser;

import java.util.List;

/**
 * @author Jack
 * @time 2016/5/26 0026 14:54
 * @des TODO
 */

public class JyuUser extends AVUser {
    public String getUserNickname() {
        return this.getString("UserNickname");
    }

    public void setUserNickname(String userNickname) {
        this.put("UserNickname",userNickname);
    }

    public String getUserImage() {

        return this.getString("UserImage");
    }

    public void setUserImage(String userImage) {
        this.put("UserImage",userImage);
    }


    public String getUserIntroduction() {

        return this.getString("UserIntroduction");
    }

    public void setUserIntroduction(String userIntroduction) {
        this.put("UserIntroduction",userIntroduction);
    }

    public String getUserSex() {

        return this.getString("UserSex");
    }

    public void setUserSex(String userSex) {
        this.put("UserSex",userSex);
    }

    public Integer getUserAge() {

        return this.getInt("UserAge");
    }

    public void setUserAge(Integer userAge) {
        this.put("UserAge",userAge);
    }

    public String getCollege() {

        return this.getString("College");
    }

    public void setCollege(String college) {

        this.put("College",college);
    }

    public String getClass_() {
        return this.getString("class_");
    }

    public void setClass_(String class_) {
        this.put("class_",class_);
    }


//    private String UserNickname;
//
//    private String UserImage;
//    private String UserSex;
//    private String UserIntroduction;
//    private Integer UserAge;
//    private String College;
//    private String class_;

    public List<String> getSubscription() {
        return this.getList("subscription");
    }

    public void setSubscription(List<String> subscription) {
        this.put("subscription",subscription);
    }

}
