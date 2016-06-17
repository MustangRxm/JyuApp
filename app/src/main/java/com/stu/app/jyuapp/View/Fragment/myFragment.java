package com.stu.app.jyuapp.View.Fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.stu.app.jyuapp.Model.Domain.JyuUser;
import com.stu.app.jyuapp.R;
import com.stu.app.jyuapp.View.Activity.SignInActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class myFragment extends Fragment implements View.OnClickListener {

    //    private JyuUser jyuUser;
    public myFragment() {
        // Required empty public constructor
    }

    private JyuUser jyuUser;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my, container, false);
        ImageView iv_user_info_headPortrait = (ImageView) view.findViewById(R.id.iv_user_info_headPortrait);
        TextView tv_user_info_nickName = (TextView) view.findViewById(R.id.tv_user_info_nickName);
        TextView tv_user_info_introduction = (TextView) view.findViewById(R.id.tv_user_info_introduction);
        RelativeLayout rl_myfragment_ic_signup = (RelativeLayout) view.findViewById(R.id.rl_myfragment_ic_signup);
//        CardView cv_user_info = (CardView) view.findViewById(R.id.cv_user_info);
        RelativeLayout rl_user_info= (RelativeLayout) view.findViewById(R.id.rl_user_info);
        rl_myfragment_ic_signup.setOnClickListener(this);

        jyuUser = JyuUser.getCurrentUser(JyuUser.class);
        if (jyuUser == null) {
            Glide.with(getContext()).load(R.mipmap.ic_launcher).into(iv_user_info_headPortrait);
            tv_user_info_nickName.setText("请登录");
        } else {
            Glide.with(getContext()).load(jyuUser.getUserImage()).into(iv_user_info_headPortrait);
            if (TextUtils.isEmpty(jyuUser.getUserNickname())) {
                tv_user_info_nickName.setText(jyuUser.getUsername());
            } else {
                tv_user_info_nickName.setText(jyuUser.getUserNickname());
            }
            if (TextUtils.isEmpty(jyuUser.getUserIntroduction())) {
                tv_user_info_introduction.setText("这个人很懒，他什么都没留下");
            } else {
                tv_user_info_introduction.setText(jyuUser.getUserIntroduction());
            }
        }
        rl_user_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (jyuUser == null) {
                    startActivity(new Intent(getContext(), SignInActivity.class));

                } else {
                    //跳转到user info setting
                }
            }
        });
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_myfragment_ic_signup:
                //                BmobUser.logOut(getContext());
                JyuUser.logOut();
                startActivity(new Intent(getContext(), SignInActivity.class));
                break;

        }

    }
}
