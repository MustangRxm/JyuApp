package com.stu.app.jyuapp.Model.Utils;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.stu.app.jyuapp.Model.Domain.JYU_Important_News;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author Jack
 * @time 2016/5/19 0019 23:19
 * @des TODO
 */

public class CacheUtils {
    private File cacheFile;
    private ExecutorService threadPool;
    //    private MainActivity mMainActivity;
    private Context mContext;

    public CacheUtils(Context context) {
        //        mMainActivity =mainActivity;
        mContext = context;
        cacheFile = context.getCacheDir();
        threadPool = Executors.newFixedThreadPool(6);//等下看看
    }

    //    public void display()
    public void saveJsonToCacheFile(List list,String name) {
        File file = new File(cacheFile, Md5Utils.md5(name));
        Gson gson = new Gson();
        String jsonStr = gson.toJson(list);

        FileOutputStream fos = null;
        BufferedOutputStream bos = null;
        try {
            fos = new FileOutputStream(file);
            bos = new BufferedOutputStream(fos);
            byte[] jsonbyte = jsonStr.getBytes();
            bos.write(jsonbyte, 0, jsonbyte.length);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                bos.close();
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        //        SpTools.putString(mContext,Md5Utils.md5(url),jsonstr);
    }

    public List<JYU_Important_News> getJsonStr(String name) {
        File file = new File(cacheFile, Md5Utils.md5(name));
        Gson gson = new Gson();
        List<JYU_Important_News> list = null;
        StringBuilder sb = null;
        BufferedReader br = null;
        if (file.exists() && file.isFile()) {
            try {
                sb = new StringBuilder();
                String line = null;
                br = new BufferedReader(new FileReader(file));
                while ((line = br.readLine()) != null) {
                    sb.append(line);
                }
                list = gson.fromJson(sb.toString(),new TypeToken<List<JYU_Important_News>>(){}.getType());
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }finally {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
        return list;
    }
}


