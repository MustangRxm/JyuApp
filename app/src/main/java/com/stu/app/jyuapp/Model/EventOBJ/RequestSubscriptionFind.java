package com.stu.app.jyuapp.Model.EventOBJ;

import com.stu.app.jyuapp.Model.Domain.SubscriptionFind;

import java.util.List;

/**
 * @author Jack
 * @time 2016/6/4 0004 22:42
 * @des TODO
 */

public class RequestSubscriptionFind {
    public RequestSubscriptionFind(List<SubscriptionFind> subscriptionFindList) {
        mSubscriptionFindList = subscriptionFindList;
    }

    public List<SubscriptionFind> getSubscriptionFindList() {
        return mSubscriptionFindList;
    }

    public void setSubscriptionFindList(List<SubscriptionFind> subscriptionFindList) {
        mSubscriptionFindList = subscriptionFindList;
    }

    private List<SubscriptionFind> mSubscriptionFindList;
}
