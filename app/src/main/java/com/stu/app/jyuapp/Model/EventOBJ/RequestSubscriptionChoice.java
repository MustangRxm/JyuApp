package com.stu.app.jyuapp.Model.EventOBJ;

import com.stu.app.jyuapp.Model.Domain.JyuSubChoice;
import com.stu.app.jyuapp.Model.Domain.JyuSubscription;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Jack
 * @time 2016/6/5 0005 15:24
 * @des TODO
 */

public class RequestSubscriptionChoice {


    public RequestSubscriptionChoice(List<JyuSubChoice> subscriptionchoice) {
        this.subscriptionchoice = subscriptionchoice;
    }

    public List<JyuSubscription> getSubscriptionchoice() {
        List<JyuSubscription> list = new ArrayList<>();
        for (JyuSubChoice each : subscriptionchoice){
            list.add(each);

        }
        return list;
    }

    private List<JyuSubChoice> subscriptionchoice;

}
