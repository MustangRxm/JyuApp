package com.stu.app.jyuapp.View.Activity;


import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.stu.app.jyuapp.Model.Domain.JyuNews;
import com.stu.app.jyuapp.R;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

public class News_Entity_Activity extends AppCompatActivity {
    private TextView News_title;
    private TextView News_Author;
    private TextView News_Main_content;
    private LinearLayout News_img_content;
    private Toolbar toolbar;

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
//        this.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        //        getSupportActionBar().setRootTitle("ddd");
        EventBus.getDefault().register(this);
        setContentView(R.layout.activity_news_entity_);
//        toolbar = (Toolbar) findViewById(R.id.toolbar);
//        toolbar.setNavigationIcon(R.mipmap.ic_nav_go_back);
//        toolbar.setNavigationContentDescription("go back");
        setSupportActionBar(toolbar);
        bindView();
    }


    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    JyuNews news = (JyuNews) msg.obj;
//                    toolbar.setRootTitle(news.getRootTitle());
//                    setSupportActionBar(toolbar);
                    //setNavigationOnClickListener写在这里的原因是:这里的setSupportActionBar是二次覆盖
//                    toolbar.setNavigationOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            News_Entity_Activity.this.finish();
//                        }
//                    });
//                    News_title.setText(news.getRootTitle());
                    String[] News_main_content = news.getNewsContent().trim().split(" ");
                    for (int i = 0; i < News_main_content.length; i++) {
                        if (!TextUtils.isEmpty(News_main_content[i].trim())) {
                            if (i == 0) {
                                News_Main_content.append("\n");
                                News_Main_content.append("\u3000\u3000");
                            }
                            //                    News_Main_content.append("\u0020");
                            News_Main_content.append(News_main_content[i]);
                            News_Main_content.append("\n");
                        }
                    }
                    News_Author.setText(news.getAuthor());
                    List<String> img_list = news.getNewsImage();
                    ImageView img;
                    for (String url : img_list) {
                        img = new ImageView(News_Entity_Activity.this);
                        img.setClickable(true);
                        img.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(News_Entity_Activity.this, "hehe", Toast.LENGTH_LONG).show();
                            }
                        });
                        Glide.with(News_Entity_Activity.this).load(url).into(img);
                        News_img_content.addView(img);
                    }
                    break;

            }

        }
    };

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void getNews(JyuNews news) {
        Message msg = mHandler.obtainMessage();
        msg.obj = news;
        msg.what = 1;
        mHandler.sendMessage(msg);

    }

    private void bindView() {
        News_title = (TextView) findViewById(R.id.News_title);
        News_Main_content = (TextView) findViewById(R.id.News_Main_content);
        News_img_content = (LinearLayout) findViewById(R.id.News_img_content);
        News_Author = (TextView) findViewById(R.id.News_Author);
    }

}
