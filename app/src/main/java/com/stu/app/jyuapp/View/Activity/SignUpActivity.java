package com.stu.app.jyuapp.View.Activity;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVOSCloud;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.RequestMobileCodeCallback;
import com.avos.avoscloud.SignUpCallback;
import com.stu.app.jyuapp.Controler.Utils.KeyBoardUtils;
import com.stu.app.jyuapp.R;
import com.stu.app.jyuapp.View.MainActivity;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class SignUpActivity extends AppCompatActivity {
    private TextInputLayout et_login_registered_userPhoneNum_textInputLayout;
    private TextInputLayout et_login_registered_email_textInputLayout;
    private TextInputLayout et_login_registered_msgNum_textInputLayout;
    private TextInputLayout et_login_registered_userPassword_textInputLayout;
    private TextInputLayout et_login_registered_userPasswordAgain_textInputLayout;
    private EditText et_login_registered_userPhoneNum;
    private EditText et_login_registered_email;
    private EditText et_login_registered_userPassword;
    private EditText et_login_registered_msgNum;
    private Button bt_login_registered_commit;
    private Button bt_login_registered_msg;
    private Button bt_sign_up_goback;
    private RelativeLayout activity_sign_up;
    private ProgressBar pb_wait;
    private static final String EMAIL_PATTERN = "^[a-zA-Z0-9#_~!$&'()*+,;=:.\"(),:;<>@\\[\\]\\\\]+@[a-zA-Z0-9-]+(\\.[a-zA-Z0-9-]+)*$";
    private Pattern pattern = Pattern.compile(EMAIL_PATTERN);
    private Matcher matcher;

    public boolean validateEmail(String email) {
        matcher = pattern.matcher(email);
        return matcher.matches();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_sign_up);

        activity_sign_up = (RelativeLayout) findViewById(R.id.activity_sign_up);
        et_login_registered_userPhoneNum_textInputLayout = (TextInputLayout) findViewById(R.id.et_login_registered_userPhoneNum_textInputLayout);
        et_login_registered_email_textInputLayout = (TextInputLayout) findViewById(R.id.et_login_registered_email_textInputLayout);
        et_login_registered_msgNum_textInputLayout = (TextInputLayout) findViewById(R.id.et_login_registered_msgNum_textInputLayout);
        et_login_registered_userPassword_textInputLayout = (TextInputLayout) findViewById(R.id.et_login_registered_userPassword_textInputLayout);
        et_login_registered_userPasswordAgain_textInputLayout = (TextInputLayout) findViewById(R.id.et_login_registered_userPasswordAgain_textInputLayout);

        et_login_registered_userPhoneNum = (EditText) findViewById(R.id.et_login_registered_userPhoneNum);
        et_login_registered_email = (EditText) findViewById(R.id.et_login_registered_email);
        et_login_registered_msgNum = (EditText) findViewById(R.id.et_login_registered_msgNum);
        et_login_registered_userPassword = (EditText) findViewById(R.id.et_login_registered_userPassword);
        bt_login_registered_commit = (Button) findViewById(R.id.bt_login_registered_commit);
        bt_login_registered_msg = (Button) findViewById(R.id.bt_login_registered_msg);
        bt_sign_up_goback = (Button) findViewById(R.id.bt_sign_up_goback);
        pb_wait = (ProgressBar) findViewById(R.id.pb_wait);
        activity_sign_up.getBackground().setAlpha(100);
        bt_sign_up_goback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        bt_login_registered_msg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userPhoneNum = et_login_registered_userPhoneNum_textInputLayout.getEditText().getText().toString();
                if (userPhoneNum.length() != 11) {
                    et_login_registered_userPhoneNum_textInputLayout.setError("请输入正确的手机号码");
                    //                    SignUpFlag &= (~(0x01 << 0));
                    //                    Log.i(constantsVAR.TAG, "phonenum::flag::" + Integer.toHexString(SignUpFlag));
                } else {
                    et_login_registered_userPhoneNum_textInputLayout.setErrorEnabled(false);
                    //                    SignUpFlag |= (0x01 << 0);
                    //                    Log.i(constantsVAR.TAG, "phonenum::flag::" + Integer.toHexString(SignUpFlag));
                    //                    BmobSMS.requestSMSCode(SignUpActivity.this, userPhoneNum, "smsCode01", new RequestSMSCodeListener() {
                    //                        @Override
                    //                        public void done(Integer integer, BmobException e) {
                    //                            if (e==null){
                    //                                Toast.makeText(SignUpActivity.this,"send sms success smsid="+integer,Toast.LENGTH_LONG).show();
                    //                            }
                    //                        }
                    //                    });
                    AVOSCloud.requestSMSCodeInBackground(userPhoneNum, new RequestMobileCodeCallback() {
                        @Override
                        public void done(AVException e) {
                            if (e == null) {
                                Log.i("20160615", "seed message success");
                            }
                        }
                    });
                }
            }
        });
        bt_login_registered_commit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int SignUpFlag = 0x00;
                KeyBoardUtils.hideKeyboard(SignUpActivity.this);
                final String userPhoneNum = et_login_registered_userPhoneNum_textInputLayout.getEditText().getText().toString();
                //验证功能暂时不用
                String msgNum = et_login_registered_msgNum_textInputLayout.getEditText().getText().toString();
                final String email = et_login_registered_email_textInputLayout.getEditText().getText().toString();
                final String userPassword = et_login_registered_userPassword_textInputLayout.getEditText().getText().toString();
                String userPasswordAgain = et_login_registered_userPasswordAgain_textInputLayout.getEditText().getText().toString();

                if (userPhoneNum.length() != 11) {
                    et_login_registered_userPhoneNum_textInputLayout.setError("请输入正确的手机号码");
                    SignUpFlag &= (~(0x01 << 0));
                } else {
                    et_login_registered_userPhoneNum_textInputLayout.setErrorEnabled(false);
                    SignUpFlag |= (0x01 << 0);
                }
                if (userPassword.length() < 6) {
                    SignUpFlag &= (~(0x01 << 1));
                    et_login_registered_userPassword_textInputLayout.setError("请输入大于6个字符长度的密码");
                } else {
                    et_login_registered_userPassword_textInputLayout.setErrorEnabled(false);
                    SignUpFlag |= (0x01 << 1);
                }
                if (!validateEmail(email)) {
                    et_login_registered_email_textInputLayout.setError("请输入正确的邮箱地址");
                    SignUpFlag &= (~(0x01 << 2));
                } else {

                    et_login_registered_email_textInputLayout.setErrorEnabled(false);
                    SignUpFlag |= (0x01 << 2);

                }
//                if (msgNum.length() != 6) {
//                    et_login_registered_msgNum_textInputLayout.setError("请输入正确的验证码");
//                    SignUpFlag &= (~(0x01 << 3));
//                } else {
//
//                    et_login_registered_msgNum_textInputLayout.setErrorEnabled(false);
//                    SignUpFlag |= (0x01 << 3);
//
//                }
                if ((userPasswordAgain.length() < 6) || (!userPasswordAgain.equals(userPassword))) {
                    SignUpFlag &= (~(0x01 << 4));
                    et_login_registered_userPasswordAgain_textInputLayout.setError("两次输入的密码不相同");
                } else {
                    et_login_registered_userPasswordAgain_textInputLayout.setErrorEnabled(false);
                    SignUpFlag |= (0x01 << 4);
                }


                if (SignUpFlag == 0x17) {
                    pb_wait.setVisibility(View.VISIBLE);
                    //                    final JyuUser user = new JyuUser();
                    AVUser user = new AVUser();
                    user.setEmail(email);
                    user.setUsername(userPhoneNum);
                    user.setPassword(userPassword);
                    user.setMobilePhoneNumber(userPhoneNum);

                    user.signUpInBackground(new SignUpCallback() {
                        @Override
                        public void done(AVException e) {
                            if (e==null){
                                //success
                                pb_wait.setVisibility(View.GONE);
                                Toast.makeText(getApplicationContext(), "signup success", Toast.LENGTH_LONG).show();
                            startActivity(new Intent(SignUpActivity.this, MainActivity.class));
                            SignUpActivity.this.finish();
                            }else {
                                Toast.makeText(getApplicationContext(),"signup fail",Toast.LENGTH_LONG).show();
                            pb_wait.setVisibility(View.GONE);
                            }

                        }
                    });

                }
            }


        });


    }
}
