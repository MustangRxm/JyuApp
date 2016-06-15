package com.stu.app.jyuapp.Model.EventOBJ;

import java.util.List;

/**
 * Created by m1 on 16-6-15.
 */

public class BaseRequestEvent<T> {
    public BaseRequestEvent(List<T> objList) {
        this.objList = objList;
    }

    public List<T> getObjList() {
        return objList;
    }

    private List<T> objList;

    public BaseRequestEvent(T objT) {
        this.objT = objT;
    }

    public T getObjT() {
        return objT;
    }

    public void setObjT(T objT) {
        this.objT = objT;
    }

    public BaseRequestEvent(List<T> objList, T objT) {
        this.objList = objList;
        this.objT = objT;
    }

    private T objT;

}
