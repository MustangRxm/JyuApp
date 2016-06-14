package com.stu.app.jyuapp.Controler.Utils;

import android.content.Context;

import java.text.SimpleDateFormat;
import java.util.Date;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.listener.GetServerTimeListener;

/**
 * @author Jack
 * @time 2016/5/23 0023 17:50
 * @des TODO
 */

public class TimeUtils {
    public static String getServerTime(Context context, final String format){
        final String[] times = {null};
        SimpleDateFormat formatter = new SimpleDateFormat(format);
       times[0] = formatter.format(new Date(System.currentTimeMillis()));
        Bmob.getServerTime(context, new GetServerTimeListener() {
            @Override
            public void onSuccess(long l) {
                SimpleDateFormat formatter = new SimpleDateFormat(format);

                times[0] = formatter.format(new Date(l*1000l));
            }

            @Override
            public void onFailure(int i, String s) {

            }
        });

        return times[0];
    }
}
