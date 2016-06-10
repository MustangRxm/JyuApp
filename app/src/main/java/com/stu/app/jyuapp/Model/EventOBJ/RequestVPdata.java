package com.stu.app.jyuapp.Model.EventOBJ;

import java.util.List;

/**
 * @author Jack
 * @time 2016/6/10 0010 16:19
 * @des TODO
 */

public class RequestVPdata {
    public RequestVPdata(List<String> vpdata) {
        Vpdata = vpdata;
    }

    public List<String> getVpdata() {
        return Vpdata;
    }

    private List<String> Vpdata;
}
