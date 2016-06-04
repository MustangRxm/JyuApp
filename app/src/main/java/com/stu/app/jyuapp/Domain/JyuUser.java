package com.stu.app.jyuapp.Domain;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobGeoPoint;

/**
 * @author Jack
 * @time 2016/5/26 0026 14:54
 * @des TODO
 */

public class JyuUser extends BmobUser {
    public String getUserNickname() {
        return UserNickname;
    }

    public void setUserNickname(String userNickname) {
        this.UserNickname = userNickname;
    }

    private String UserNickname;

    public String getUserImage() {
        return UserImage;
    }

    public void setUserImage(String userImage) {
        UserImage = userImage;
    }

    private String UserImage;

    public String getUserIntroduction() {
        return UserIntroduction;
    }

    public void setUserIntroduction(String userIntroduction) {
        UserIntroduction = userIntroduction;
    }

    private String UserIntroduction;

    public String getUserSex() {
        return UserSex;
    }

    public void setUserSex(String userSex) {
        UserSex = userSex;
    }

    public Integer getUserAge() {
        return UserAge;
    }

    public void setUserAge(Integer userAge) {
        UserAge = userAge;
    }

    public String getCollege() {
        return College;
    }

    public void setCollege(String college) {
        College = college;
    }

    public String getClass_() {
        return class_;
    }

    public void setClass_(String class_) {
        this.class_ = class_;
    }

    public BmobGeoPoint getGpsAdd() {
        return gpsAdd;
    }

    public void setGpsAdd(BmobGeoPoint gpsAdd) {
        this.gpsAdd = gpsAdd;
    }

    private String UserSex;
    private Integer UserAge;
    private String College;
    private String class_;
    private BmobGeoPoint gpsAdd;
}
