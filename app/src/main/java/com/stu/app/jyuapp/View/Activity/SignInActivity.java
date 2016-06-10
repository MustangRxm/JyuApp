package com.stu.app.jyuapp.View.Activity;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.stu.app.jyuapp.Model.Domain.JyuUser;
import com.stu.app.jyuapp.View.MainActivity;
import com.stu.app.jyuapp.R;
import com.stu.app.jyuapp.Model.Utils.KeyBoardUtils;
import com.stu.app.jyuapp.Model.Utils.SpTools;
import com.stu.app.jyuapp.Model.Utils.constantsVAR;

import cn.bmob.v3.listener.SaveListener;

public class SignInActivity extends AppCompatActivity {
    private TextInputLayout userpwd_textInputLayout;
    private TextInputLayout usernumber_textInputLayout;
    private EditText et_login_userName;
    private EditText et_login_userPassword;
    private Button bt_login;
    private Button bt_sign_in_goback;
    private TextView tv_forgetpwd;
    private ProgressBar pb_log_in_wait;
    private RelativeLayout activity_sign_in;
    private TextView tv_registered;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        setContentView(R.layout.activity_sign_in);

        RelativeLayout rl_root = (RelativeLayout) findViewById(R.id.activity_sign_in);
        rl_root.getBackground().setAlpha(200);

        activity_sign_in = (RelativeLayout) findViewById(R.id.activity_sign_in);
        userpwd_textInputLayout = (TextInputLayout) findViewById(R.id.userpwd_textInputLayout);
        usernumber_textInputLayout = (TextInputLayout) findViewById(R.id.usernumber_textInputLayout);
        et_login_userName = (EditText) findViewById(R.id.et_login_userName);
        et_login_userPassword = (EditText) findViewById(R.id.et_login_userPassword);
        bt_login = (Button) findViewById(R.id.bt_login);
        bt_sign_in_goback = (Button) findViewById(R.id.bt_sign_in_goback);
        pb_log_in_wait = (ProgressBar) findViewById(R.id.pb_log_in_wait);
        tv_forgetpwd = (TextView) findViewById(R.id.tv_forgetpwd);
        tv_registered = (TextView) findViewById(R.id.tv_registered);
        activity_sign_in.getBackground().setAlpha(100);
        bt_sign_in_goback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignInActivity.this,MainActivity.class));
                finish();
            }
        });
        tv_registered.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent SignUp = new Intent(SignInActivity.this, SignUpActivity.class);
                startActivity(SignUp);
            }
        });
        tv_forgetpwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ForgetFind = new Intent(SignInActivity.this, ForgetFindActivity.class);
                startActivity(ForgetFind);
            }
        });
        bt_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //验证号码和密码的正确性
                //将号码和密码发送给服务器验证登录
                int SignInFlag = 0x00;
                KeyBoardUtils.hideKeyboard(SignInActivity.this);
                String userPhoneNum = usernumber_textInputLayout.getEditText().getText().toString();
                String userPassword = userpwd_textInputLayout.getEditText().getText().toString();
                if (userPhoneNum.length() != 11) {
                    usernumber_textInputLayout.setError("请输入正确的手机号码");
                    SignInFlag &= (~(0x01 << 0));
                } else {
                    usernumber_textInputLayout.setErrorEnabled(false);
                    SignInFlag |= (0x01 << 0);
                }
                if (userPassword.length() < 6) {
                    SignInFlag &= (~(0x01 << 1));
                    userpwd_textInputLayout.setError("请输入大于6个字符长度的密码");
                } else {
                    userpwd_textInputLayout.setErrorEnabled(false);
                    SignInFlag |= (0x01 << 1);
                }

                if (SignInFlag == 0x03) {
                    final JyuUser user = new JyuUser();
                    user.setUsername(userPhoneNum);
                    user.setPassword(userPassword);
                    user.setMobilePhoneNumber(userPhoneNum);
                    pb_log_in_wait.setVisibility(View.VISIBLE);
                    user.login(SignInActivity.this, new SaveListener() {
                        @Override
                        public void onSuccess() {
                            pb_log_in_wait.setVisibility(View.GONE);
                            SpTools.putBoolean(SignInActivity.this, constantsVAR.FirstTimeUse, false);
                            Toast.makeText(getApplicationContext(), "signin success", Toast.LENGTH_LONG).show();
                            //                                    et_login_userName.setText(user.getUsername());
                            //需要设置动画效果
//                            JyuUser user_obtain = BmobUser.getCurrentUser(getApplicationContext(), JyuUser.class);
//                            user_obtain.signOrLogin();
                            startActivity(new Intent(SignInActivity.this, MainActivity.class));
                            finish();
                        }

                        @Override
                        public void onFailure(int i, String s) {
                            Toast.makeText(getApplicationContext(), "signup fail::" + s, Toast.LENGTH_LONG).show();
                            pb_log_in_wait.setVisibility(View.GONE);
                        }
                    });
                }
            }
        });
    }
}
