package com.stu.app.jyuapp.Fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.stu.app.jyuapp.Activity.SignInActivity;
import com.stu.app.jyuapp.Domain.JyuUser;
import com.stu.app.jyuapp.R;

import cn.bmob.v3.BmobUser;

/**
 * A simple {@link Fragment} subclass.
 */
public class myFragment extends Fragment {

    private JyuUser jyuUser;
    public myFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
//        if (BmobUser.getCurrentUser(getContext(), JyuUser.class) == null) {
//            View SignInView = inflater.inflate(R.layout.fragment_please_signin,container,false);
//            Button bt_please_signin = (Button)SignInView.findViewById(R.id.bt_please_signin);
//            bt_please_signin.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    startActivity(new Intent(getContext(), SignInActivity.class));
//                }
//            });
//            return SignInView;
//        } else {
        View view =  inflater.inflate(R.layout.fragment_my, container, false);
        ImageView iv_user_info_headPortrait = (ImageView) view.findViewById(R.id.iv_user_info_headPortrait);
        TextView tv_user_info_nickName = (TextView) view.findViewById(R.id.tv_user_info_nickName);
        TextView tv_user_info_introduction = (TextView) view.findViewById(R.id.tv_user_info_introduction);
       CardView cv_user_info = (CardView) view.findViewById(R.id.cv_user_info);

        jyuUser = BmobUser.getCurrentUser(getContext(), JyuUser.class);
        if (jyuUser==null){
            Glide.with(getContext()).load(R.mipmap.ic_launcher).into(iv_user_info_headPortrait);
            tv_user_info_nickName.setText("请登录");
        }else {
            Glide.with(getContext()).load(jyuUser.getUserImage()).into(iv_user_info_headPortrait);
            if (TextUtils.isEmpty(jyuUser.getUserNickname())){
            tv_user_info_nickName.setText(jyuUser.getUsername());}
            else {
                tv_user_info_nickName.setText(jyuUser.getUserNickname());
            }
            if (TextUtils.isEmpty(jyuUser.getUserIntroduction())){
                tv_user_info_introduction.setText("这个人很懒，他什么都没留下");
            }else {
                tv_user_info_introduction.setText(jyuUser.getUserIntroduction());
            }
//            Toast.makeText(getContext(),"now is have",Toast.LENGTH_LONG).show();
        }
        cv_user_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (jyuUser==null){
                    startActivity(new Intent(getContext(), SignInActivity.class));

                }else {
                    //跳转到user info setting
                }
            }
        });
        //view.findViewById(R.id.iv_user_info_headPortrait);
        return view;
    }

}
