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
import com.stu.app.jyuapp.Utils.TimeUtils;
import com.stu.app.jyuapp.Utils.constantsVAR;
import com.stu.app.jyuapp.Utils.getDataUtils;

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
        mHandler.sendEmptyMessageDelayed(constantsVAR.LoadMainActivity, 300);
    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case constantsVAR.LoadMainActivity:
                    JyuUser jyuUser = BmobUser.getCurrentUser(SplashActivity.this,JyuUser.class);
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
        //下面两个步骤需要扔到线程里
        String year_month = TimeUtils.getServerTime(SplashActivity.this, "yy-MM");
        Log.i("20160601", "now time is ::" + year_month);
        getDataUtils.getNewsData(SplashActivity.this, year_month);
        getDataUtils.getSubcriptionFindData(this);
        getDataUtils.getUserSubcriptionContent(this);

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
