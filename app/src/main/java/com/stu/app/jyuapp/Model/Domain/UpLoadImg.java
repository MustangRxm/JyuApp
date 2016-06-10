package com.stu.app.jyuapp.Model.Domain;

/**
 * @author Jack
 * @time 2016/6/1 0001 23:24
 * @des TODO
 */

public class UpLoadImg {
    public UpLoadImg(String id, String[] paths) {
        this.id = id;
        this.paths = paths;
    }

    public String getId() {
        return id;
    }

    public String[] getPaths() {
        return paths;
    }

    private String id;
   private String [] paths;
}
