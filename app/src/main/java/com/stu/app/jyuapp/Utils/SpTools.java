package com.stu.app.jyuapp.Utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Administrator on 2016/4/19.
 */
public class SpTools {
    public static void putString(Context context,String key ,String value){
        SharedPreferences sp = context.getSharedPreferences(constantsVAR.SpFile,Context.MODE_PRIVATE);
        sp.edit().putString(key,value).commit();
    }
    public static String getString(Context context,String key ,String defvalue){
        SharedPreferences sp = context.getSharedPreferences(constantsVAR.SpFile,Context.MODE_PRIVATE);
        return sp.getString(key,defvalue);
    }
    public static void putBoolean(Context context,String key ,Boolean defvalue){
        SharedPreferences sp = context.getSharedPreferences(constantsVAR.SpFile,Context.MODE_PRIVATE);
        sp.edit().putBoolean(key,defvalue).commit();
    }
    public static boolean getBoolean(Context context,String key ,Boolean defvalue){
        SharedPreferences sp = context.getSharedPreferences(constantsVAR.SpFile,Context.MODE_PRIVATE);
        return sp.getBoolean(key,defvalue);
    }
//    public static void putList(Context context,String key ,String value){
//        SharedPreferences sp = context.getSharedPreferences(constantsVAR.CacheListFile,Context.MODE_PRIVATE);
//        sp.edit().putStringSet(key,value).commit();
//    }
//    public static String getList(Context context,String key ,String defvalue){
//        SharedPreferences sp = context.getSharedPreferences(constantsVAR.CacheListFile,Context.MODE_PRIVATE);
//        return sp.getString(key,defvalue);
//    }
}
