package com.stu.app.jyuapp.Controler.Utils;

import android.content.Context;

import java.text.SimpleDateFormat;
import java.util.Date;

import static java.lang.System.currentTimeMillis;

//import cn.bmob.v3.Bmob;
//import cn.bmob.v3.listener.GetServerTimeListener;

/**
 * @author Jack
 * @time 2016/5/23 0023 17:50
 * @des TODO
 */

public class TimeUtils {
    public static String getLocalTime(Context context, final String format){
        String times;
        SimpleDateFormat formatter = new SimpleDateFormat(format);
       times = formatter.format(new Date(currentTimeMillis()));
        long l = System.currentTimeMillis();
        return times;
    }
}
