package com.stu.app.jyuapp.Model.Utils;

import android.content.Context;
import android.net.ConnectivityManager;

/**
 * @author Jack
 * @time 2016/5/23 0023 23:58
 * @des TODO
 */

public class NetWorkUtils {
    public static boolean isOpenNetWork(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(context.CONNECTIVITY_SERVICE);
        if(connectivityManager.getActiveNetworkInfo()!=null)
        {return connectivityManager.getActiveNetworkInfo().isAvailable();}
            return false;
    }
}
