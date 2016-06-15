package com.stu.app.jyuapp.Model.Domain;

import com.avos.avoscloud.AVClassName;
import com.avos.avoscloud.AVObject;

/**
 * @author Jack
 * @time 2016/6/4 0004 18:19
 * @des TODO
 */
@AVClassName("SubscriptionFind")
public class SubscriptionFind extends AVObject {

    public String getSubName() {
        return this.getString("subName");
    }

    public void setSubName(String subName) {
        this.put("subName",subName);
    }

    public String getSubRssSrc() {
        return this.getString("subRssSrc");
    }

    public void setSubRssSrc(String subRssSrc) {
        this.put("subRssSrc",subRssSrc);

    }

    public Integer getSubscrber() {
        return this.getInt("subscrber");
    }

    public void setSubscrber(Integer subscrber) {
        this.put("subscrber",subscrber);
    }

    public String getHead_portraitSrc() {
        return this.getString("Head_portraitSrc");
    }

    public void setHead_portraitSrc(String head_portraitSrc) {
        this.put("Head_portraitSrc",head_portraitSrc);
    }

}
