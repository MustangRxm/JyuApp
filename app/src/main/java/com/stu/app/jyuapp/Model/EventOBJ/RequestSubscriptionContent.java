package com.stu.app.jyuapp.Model.EventOBJ;

import com.stu.app.jyuapp.Model.Domain.JyuSubscription;

import java.util.List;

/**
 * @author Jack
 * @time 2016/6/5 0005 15:24
 * @des TODO
 */

public class RequestSubscriptionContent {


    public RequestSubscriptionContent(List<JyuSubscription> subscriptioncontent) {
        this.subscriptioncontent = subscriptioncontent;
    }

    public List<JyuSubscription> getSubscriptioncontent() {
        return subscriptioncontent;
    }

    private List<JyuSubscription> subscriptioncontent;

}
