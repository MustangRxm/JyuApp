package com.stu.app.jyuapp.EventOBJ;

import java.util.List;

/**
 * @author Jack
 * @time 2016/6/5 0005 13:56
 * @des TODO
 */

public class UpdateUserSub {
    public UpdateUserSub(List<String> mapList) {
        mMapList = mapList;
    }

    public List<String> getMapList() {
        return mMapList;
    }

    public void setMapList(List<String> mapList) {
        mMapList = mapList;
    }

    private List<String> mMapList;
}
