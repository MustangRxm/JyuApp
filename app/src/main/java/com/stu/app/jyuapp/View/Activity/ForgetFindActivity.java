package com.stu.app.jyuapp.View.Activity;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.stu.app.jyuapp.Controler.Utils.KeyBoardUtils;
import com.stu.app.jyuapp.Model.Domain.JyuUser;
import com.stu.app.jyuapp.R;
import com.stu.app.jyuapp.View.MainActivity;

import cn.bmob.v3.BmobSMS;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.RequestSMSCodeListener;
import cn.bmob.v3.listener.SaveListener;

public class ForgetFindActivity extends AppCompatActivity {
    private RelativeLayout activity_forget_find;
    private TextInputLayout et_login_forget_msgNum_textInputLayout;
    private TextInputLayout et_login_forget_userPhoneNum_textInputLayout;
    private TextInputLayout et_login_forget_userPassword_textInputLayout;
    private TextInputLayout et_login_forget_userPasswordAgain_textInputLayout;
    private Button bt_forget_find_msg;
    private Button bt_ok;
    private Button bt_forget_find_goback;
    private ProgressBar pb_forget_find_wait;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_forget_find);
        pb_forget_find_wait = (ProgressBar) findViewById(R.id.pb_forget_find_wait);
        bt_forget_find_msg = (Button) findViewById(R.id.bt_getmsg);
        bt_ok = (Button) findViewById(R.id.bt_ok);
        bt_forget_find_goback = (Button) findViewById(R.id.bt_forget_find_goback);
        activity_forget_find = (RelativeLayout) findViewById(R.id.activity_forget_find);
        activity_forget_find.getBackground().setAlpha(100);
        et_login_forget_userPasswordAgain_textInputLayout = (TextInputLayout) findViewById(R.id.et_login_forget_userPasswordAgain_textInputLayout);

        et_login_forget_userPhoneNum_textInputLayout = (TextInputLayout) findViewById(R.id.et_login_forget_userPhoneNum_textInputLayout);
        et_login_forget_msgNum_textInputLayout = (TextInputLayout) findViewById(R.id.et_login_forget_msgNum_textInputLayout);
        et_login_forget_userPassword_textInputLayout = (TextInputLayout) findViewById(R.id.et_login_forget_userPassword_textInputLayout);
        initEvent();
    }

    private void initEvent() {
        bt_forget_find_goback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        bt_forget_find_msg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userPhoneNum = et_login_forget_userPhoneNum_textInputLayout.getEditText().getText().toString();
                if (userPhoneNum.length() != 11) {
                    et_login_forget_userPhoneNum_textInputLayout.setError("请输入正确的手机号码");
                    //                    SignUpFlag &= (~(0x01 << 0));
                    //                    Log.i(constantsVAR.TAG, "phonenum::flag::" + Integer.toHexString(SignUpFlag));
                } else {
                    et_login_forget_userPhoneNum_textInputLayout.setErrorEnabled(false);
                    //                    SignUpFlag |= (0x01 << 0);
                    //                    Log.i(constantsVAR.TAG, "phonenum::flag::" + Integer.toHexString(SignUpFlag));
                    BmobSMS.requestSMSCode(ForgetFindActivity.this, userPhoneNum, "smsCode01", new RequestSMSCodeListener() {
                        @Override
                        public void done(Integer integer, BmobException e) {
                            if (e==null){
                                Toast.makeText(ForgetFindActivity.this,"send sms success smsid="+integer,Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }
            }
        });
        bt_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //验证号码和密码的正确性
                //将号码和密码发送给服务器验证登录
                int SignUpFlag = 0x00;
                KeyBoardUtils.hideKeyboard(ForgetFindActivity.this);
                String msg = et_login_forget_msgNum_textInputLayout.getEditText().getText().toString();
                String userPhoneNum = et_login_forget_userPhoneNum_textInputLayout.getEditText().getText().toString();
                String userPassword = et_login_forget_userPassword_textInputLayout.getEditText().getText().toString();
                String userPasswordAgain = et_login_forget_userPasswordAgain_textInputLayout.getEditText().getText().toString();

                if (userPhoneNum.length() != 11) {
                    et_login_forget_userPhoneNum_textInputLayout.setError("请输入正确的手机号码");
                    SignUpFlag &= (~(0x01 << 0));
                } else {
                    et_login_forget_userPhoneNum_textInputLayout.setErrorEnabled(false);
                    SignUpFlag |= (0x01 << 0);
                }
                if (userPassword.length() < 6) {
                    SignUpFlag &= (~(0x01 << 1));
                    et_login_forget_userPassword_textInputLayout.setError("请输入大于6个字符长度的密码");
                } else {
                    et_login_forget_userPassword_textInputLayout.setErrorEnabled(false);
                    SignUpFlag |= (0x01 << 1);
                }
                if ((userPasswordAgain.length() < 6)||(userPasswordAgain!=userPassword)) {
                    SignUpFlag &= (~(0x01 << 2));
                    et_login_forget_userPasswordAgain_textInputLayout.setError("两次输入的密码不相同");
                } else {
                    et_login_forget_userPasswordAgain_textInputLayout.setErrorEnabled(false);
                    SignUpFlag |= (0x01 << 2);
                }

                if (SignUpFlag == 0x07) {
                    final JyuUser user = new JyuUser();
                    //                            user.setEmail(email);
                    user.setUsername(userPhoneNum);
                    user.setPassword(userPassword);
                    user.setMobilePhoneNumber(userPhoneNum);
                    pb_forget_find_wait.setVisibility(View.VISIBLE);
                    user.signOrLogin(ForgetFindActivity.this, msg, new SaveListener() {
                        @Override
                        public void onSuccess() {
                            pb_forget_find_wait.setVisibility(View.GONE);
                            Toast.makeText(ForgetFindActivity.this, "find success", Toast.LENGTH_LONG).show();
                            startActivity(new Intent(ForgetFindActivity.this, MainActivity.class));
                            finish();
                        }

                        @Override
                        public void onFailure(int i, String s) {

                        }
                    });
                    //这里是更新用户资料
                    //                    user.login(getApplicationContext(), new SaveListener() {
                    //                        @Override
                    //                        public void onSuccess() {
                    //                            pb_forget_find_wait.setVisibility(View.GONE);
                    //                            Toast.makeText(getApplicationContext(), "signin success", Toast.LENGTH_LONG).show();
                    //                            //                                    et_login_userName.setText(user.getUsername());
                    //                            //需要设置动画效果
                    //                            JyuUser user_obtain = BmobUser.getCurrentUser(getApplicationContext(), JyuUser.class);
                    //                            Intent loadMainActivity = new Intent(ForgetFindActivity.this, MainActivity.class);
                    //                            startActivity(loadMainActivity);
                    //                            finish();
                    //                        }
                    //
                    //                        @Override
                    //                        public void onFailure(int i, String s) {
                    //                            Toast.makeText(getApplicationContext(), "signup fail::" + s, Toast.LENGTH_LONG).show();
                    //                            pb_forget_find_wait.setVisibility(View.GONE);
                    //                        }
                    //                    });
                }
            }
        });

    }
}
