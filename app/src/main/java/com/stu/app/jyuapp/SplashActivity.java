package com.stu.app.jyuapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

import com.stu.app.jyuapp.Activity.SignInActivity;
import com.stu.app.jyuapp.Domain.JyuUser;
import com.stu.app.jyuapp.Utils.NewsUtils;
import com.stu.app.jyuapp.Utils.TimeUtils;
import com.stu.app.jyuapp.Utils.constantsVAR;

import java.util.List;

import cn.bmob.v3.BmobUser;

public class SplashActivity extends AppCompatActivity {
    private int versionCode;
    private AlphaAnimation aa;
    private String versionName;
    private RelativeLayout rl_splash_activity;
//    private Button startActivity;
    private PopupWindow LoginPopupWindow;
    private ViewPager vp_splash;
    //    private CircleIndicator circleIndicator;
    View view = null;
    //    splashViewPagerAdapter fwp;
    List<View> list = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        initView();
        //        EventBus.getDefault().register(this);
        checkVersion();
        initData();
        mHandler.sendEmptyMessageDelayed(constantsVAR.LoadMainActivity, 3000);
        //        if (SpTools.getBoolean(this, constantsVAR.FirstTimeUse, true)) {
        //            //进入轮播图+动画
        //            startActivity.setOnClickListener(new View.OnClickListener() {
        //                @Override
        //                public void onClick(View v) {
        //                    startActivity(new Intent(SplashActivity.this, SignInActivity.class));
        //                    finish();
        //                }
        //            });
        //        } else {
        //            startActivity(new Intent(SplashActivity.this, MainActivity.class));
        //            finish();
        //        }
        //        initEvent();

    }

    //
    //    protected void onDestroy() {
    //        super.onDestroy();
    //        EventBus.getDefault().unregister(this);
    //    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case constantsVAR.LoadMainActivity:
                  JyuUser jyuUser =  BmobUser.getCurrentUser(SplashActivity.this,JyuUser.class);
                    if (jyuUser!=null) {
                        Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    }else {
                        //跳到登录界面
                        Intent LoginIntent =new Intent(SplashActivity.this, SignInActivity.class);
                        startActivity(LoginIntent);
                        finish();
                    }
                    //                    overridePendingTransition();
                    break;
            }
        }
    };

    private void initAnimation() {
        aa = new AlphaAnimation(0.0f, 1.0f);
        aa.setDuration(3000);
        aa.setFillAfter(true);
        rl_splash_activity.setAnimation(aa);
        rl_splash_activity.startAnimation(aa);

    }

    private void initData() {
//        PackageManager pm = getPackageManager();
//        try {
//            PackageInfo packageInfo = pm.getPackageInfo(getPackageName(), 0);
//            versionCode = packageInfo.versionCode;
//            versionName = packageInfo.versionName;
//        } catch (Exception e) {
//
//        } finally {
//
//        }

//        list = new ArrayList<>();
//        ImageView imageView;
//        view = new View(this);
//        view.setBackgroundResource(R.mipmap.login_background);
//        view.setAlpha(0.7f);
//        list.add(view);
//        view = new View(this);
//        view.setBackgroundResource(R.mipmap.registered_background);
//        view.setAlpha(0.7f);
//        list.add(view);
//        view = new View(this);
//        view.setBackgroundResource(R.mipmap.forget_find_backgroud);
//        view.setAlpha(0.7f);
//        list.add(view);
        //下面两个步骤需要扔到线程里
        String year_month = TimeUtils.getServerTime(SplashActivity.this, "yy-MM");
        Log.i("20160601", "now time is ::" + year_month);
        NewsUtils.getNewsData(SplashActivity.this, year_month);

    }

    private void checkVersion() {
        //        如果有新版本，弹框提示用户
        //        否则进入splash页面
    }

    private void initView() {
        setContentView(R.layout.activity_splash);
//        startActivity = (Button) findViewById(R.id.button);
        rl_splash_activity = (RelativeLayout) findViewById(R.id.rl_splash_activity);
    }

}
