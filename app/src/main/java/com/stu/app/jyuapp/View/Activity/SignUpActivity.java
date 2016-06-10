package com.stu.app.jyuapp.View.Activity;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.stu.app.jyuapp.Model.Domain.JyuUser;
import com.stu.app.jyuapp.View.MainActivity;
import com.stu.app.jyuapp.R;
import com.stu.app.jyuapp.Model.Utils.KeyBoardUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.bmob.v3.BmobSMS;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.RequestSMSCodeListener;
import cn.bmob.v3.listener.SaveListener;

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
        bt_sign_up_goback= (Button) findViewById(R.id.bt_sign_up_goback);
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
                    BmobSMS.requestSMSCode(SignUpActivity.this, userPhoneNum, "smsCode01", new RequestSMSCodeListener() {
                        @Override
                        public void done(Integer integer, BmobException e) {
                            if (e==null){
                                Toast.makeText(SignUpActivity.this,"send sms success smsid="+integer,Toast.LENGTH_LONG).show();
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
                String userPhoneNum = et_login_registered_userPhoneNum_textInputLayout.getEditText().getText().toString();
                //验证功能暂时不用
                String msgNum = et_login_registered_msgNum_textInputLayout.getEditText().getText().toString();
                String email = et_login_registered_email_textInputLayout.getEditText().getText().toString();
                String userPassword = et_login_registered_userPassword_textInputLayout.getEditText().getText().toString();
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
                if (msgNum.length()!=6) {
                    et_login_registered_msgNum_textInputLayout.setError("请输入正确的验证码");
                    SignUpFlag &= (~(0x01 << 3));
                } else {

                    et_login_registered_msgNum_textInputLayout.setErrorEnabled(false);
                    SignUpFlag |= (0x01 << 3);

                }
                if ((userPasswordAgain.length() < 6)||(!userPasswordAgain.equals(userPassword))) {
                    SignUpFlag &= (~(0x01 << 4));
                    et_login_registered_userPasswordAgain_textInputLayout.setError("两次输入的密码不相同");
                } else {
                    et_login_registered_userPasswordAgain_textInputLayout.setErrorEnabled(false);
                    SignUpFlag |= (0x01 << 4);
                }


                if (SignUpFlag == 0x1F) {
                    final JyuUser user = new JyuUser();
                    user.setEmail(email);
                    user.setUsername(userPhoneNum);
                    user.setPassword(userPassword);
                    user.setMobilePhoneNumber(userPhoneNum);
                    pb_wait.setVisibility(View.VISIBLE);
                    user.signOrLogin(getApplicationContext(),msgNum ,new SaveListener() {
                        @Override
                        public void onSuccess() {
                            pb_wait.setVisibility(View.GONE);
                            Toast.makeText(getApplicationContext(), "signup success", Toast.LENGTH_LONG).show();
                            startActivity(new Intent(SignUpActivity.this, MainActivity.class));
                            finish();
                            //                            et_login_userName.setText(user.getUsername());
                            //需要设置动画效果
                            //                            LoginRegisteredPopWindow.dismiss();

                        }

                        @Override
                        public void onFailure(int i, String s) {
                            Toast.makeText(getApplicationContext(), "signup fail:::" + s, Toast.LENGTH_LONG).show();
                            pb_wait.setVisibility(View.GONE);
                        }
                    });
                }
            }


        });


    }
}
