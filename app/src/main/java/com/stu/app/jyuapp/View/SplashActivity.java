package com.stu.app.jyuapp.View;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import com.avos.avoscloud.AVUser;
import com.stu.app.jyuapp.Controler.Utils.getDataUtils;
import com.stu.app.jyuapp.Model.Domain.JyuUser;
import com.stu.app.jyuapp.R;
import com.stu.app.jyuapp.View.Activity.SignInActivity;

import static com.stu.app.jyuapp.Controler.Utils.TimeUtils.getLocalTime;

public class SplashActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        initView();
        checkVersion();
        initData();
        mHandler.sendEmptyMessageDelayed(com.stu.app.jyuapp.Controler.Utils.constantsVAR.LoadMainActivity, 3000);
    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case com.stu.app.jyuapp.Controler.Utils.constantsVAR.LoadMainActivity:
                    JyuUser jyuUser =AVUser.getCurrentUser(JyuUser.class);
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

    private void initData() {
        //下面两个步骤需要扔到线程里
        String year_month = getLocalTime(SplashActivity.this, "yyyy-MM");
        Log.i("20160601", "now time is ::" + year_month);
        getDataUtils.getNewsData(SplashActivity.this,year_month);
        getDataUtils.getSubcriptionFindData(this);
        getDataUtils.getUserSubcriptionContent(this);
        getDataUtils.getsubshowVPdata();

    }

    private void checkVersion() {
        //        如果有新版本，弹框提示用户
        //        否则进入splash页面
    }

    private void initView() {
        setContentView(R.layout.activity_splash);
    }

}
