package com.stu.app.jyuapp.Domain;

import cn.bmob.v3.BmobObject;

/**
 * @author Jack
 * @time 2016/6/4 0004 18:19
 * @des TODO
 */

public class SubscriptionFind extends BmobObject {
    private String subName;

    public String getSubName() {
        return subName;
    }

    public void setSubName(String subName) {
        this.subName = subName;
    }

    public String getSubRssSrc() {
        return subRssSrc;
    }

    public void setSubRssSrc(String subRssSrc) {
        this.subRssSrc = subRssSrc;
    }

    public Integer getSubscrber() {
        return subscrber;
    }

    public void setSubscrber(Integer subscrber) {
        this.subscrber = subscrber;
    }

    public String getHead_portraitSrc() {
        return Head_portraitSrc;
    }

    public void setHead_portraitSrc(String head_portraitSrc) {
        Head_portraitSrc = head_portraitSrc;
    }

    private String subRssSrc;
    private Integer subscrber;
    private String Head_portraitSrc;
}
