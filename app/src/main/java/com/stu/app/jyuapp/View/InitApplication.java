package com.stu.app.jyuapp.View;

import android.app.Application;

import com.avos.avoscloud.AVOSCloud;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVUser;
import com.stu.app.jyuapp.Model.Domain.JyuNews;
import com.stu.app.jyuapp.Model.Domain.JyuSubscription;
import com.stu.app.jyuapp.Model.Domain.JyuUser;
import com.stu.app.jyuapp.Model.Domain.SubscriptionFind;
import com.stu.app.jyuapp.Model.Domain.advertising;

/**
 * Created by 06peng on 2015/6/24.
 *
 *
 *
 */
public class InitApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        AVUser.alwaysUseSubUserClass(JyuUser.class);
        AVObject.registerSubclass(JyuNews.class);
        AVObject.registerSubclass(advertising.class);
        AVObject.registerSubclass(JyuSubscription.class);
        AVObject.registerSubclass(SubscriptionFind.class);
        // 初始化参数依次为 this, AppId, AppKey
        AVOSCloud.initialize(this,"j91tukXq289B8L5udnJxmRIs-gzGzoHsz","x0M1yFOFhw5xMGO4SN3VTHMN");
//        Bmob.initialize(this, "06beaae856eb317097fd9381493b62ed");
        /*
        Fresco.initialize(this, ImagePipelineConfigFactory.getImagePipelineConfig(this));
       ThemeConfig theme = new ThemeConfig.Builder()
                .setTitleBarTextColor(Color.WHITE)
                .build();
        //配置功能
        FunctionConfig functionConfig = new FunctionConfig.Builder()
                .setMutiSelectMaxSize(9)

                .setEnableCamera(true)
                .setEnableEdit(true)
                .setEnableCrop(true)
                .setEnableRotate(true)
                .setCropSquare(true)
                .setEnablePreview(true)
                .setEnablePreview(true)
                .build();
        //        //配置imageloader
//        PicassoImageLoader imageLoader = new PicassoImageLoader();
        GlideImageLoader imageLoader = new GlideImageLoader();
        //        ImageLoader imageloader = new UILImageLoader();
        //设置核心配置信息
        CoreConfig coreConfig = new CoreConfig.Builder(this, imageLoader, theme)
                .setFunctionConfig(functionConfig)
//                .setPauseOnScrollListener(new P(false, true))
                .build();
        GalleryFinal.init(coreConfig);*/
    }
}
