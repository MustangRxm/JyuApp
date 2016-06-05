package com.stu.app.jyuapp.EventOBJ;

import java.util.List;
import java.util.Map;

/**
 * @author Jack
 * @time 2016/6/5 0005 13:56
 * @des TODO
 */

public class UpdateUserSub {
    public UpdateUserSub(List<Map<String, Boolean>> mapList) {
        mMapList = mapList;
    }

    public List<Map<String, Boolean>> getMapList() {
        return mMapList;
    }

    public void setMapList(List<Map<String, Boolean>> mapList) {
        mMapList = mapList;
    }

    private List<Map<String,Boolean>> mMapList;
}
