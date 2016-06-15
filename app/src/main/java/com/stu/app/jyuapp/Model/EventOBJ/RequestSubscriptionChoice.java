package com.stu.app.jyuapp.Model.EventOBJ;

import com.stu.app.jyuapp.Model.Domain.JyuSubscription;

import java.util.List;

/**
 * @author Jack
 * @time 2016/6/5 0005 15:24
 * @des TODO
 */

public class RequestSubscriptionChoice {


    public RequestSubscriptionChoice(List<JyuSubscription> subscriptionchoice) {
        this.subscriptionchoice = subscriptionchoice;
    }

    public List<JyuSubscription> getSubscriptionchoice() {
        return subscriptionchoice;
    }

    private List<JyuSubscription> subscriptionchoice;

}
