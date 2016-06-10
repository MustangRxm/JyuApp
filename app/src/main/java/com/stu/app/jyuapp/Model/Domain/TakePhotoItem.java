package com.stu.app.jyuapp.Model.Domain;

import android.graphics.Bitmap;

/**
 * @author Jack
 * @time 2016/5/31 0031 17:00
 * @des TODO
 */

public class TakePhotoItem {
    public Bitmap getSmallIMG() {
        return smallIMG;
    }

    public void setSmallIMG(Bitmap smallIMG) {
        this.smallIMG = smallIMG;
    }

    public String getImgPath() {
        return ImgPath;
    }

    public void setImgPath(String imgPath) {
        ImgPath = imgPath;
    }

    public Bitmap smallIMG;
    public String ImgPath;
}
