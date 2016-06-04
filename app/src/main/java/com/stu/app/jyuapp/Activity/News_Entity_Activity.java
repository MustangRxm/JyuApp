package com.stu.app.jyuapp.Activity;


import android.net.Uri;
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

import com.stu.app.jyuapp.Domain.JYU_Important_News;
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
        //        getSupportActionBar().setTitle("ddd");
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
                    JYU_Important_News news = (JYU_Important_News) msg.obj;
//                    toolbar.setTitle(news.getTitle());
//                    setSupportActionBar(toolbar);
                    //setNavigationOnClickListener写在这里的原因是:这里的setSupportActionBar是二次覆盖
//                    toolbar.setNavigationOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            News_Entity_Activity.this.finish();
//                        }
//                    });
//                    News_title.setText(news.getTitle());
                    String[] News_main_content = news.getNews_Main_content().trim().split(" ");
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
                    News_Author.setText(news.getNews_Author());
                    List<String> img_list = news.getNews_Img();
                    ImageView img;
                    for (String url : img_list) {
                        img = new ImageView(News_Entity_Activity.this);
//                        img.setAspectRatio(1.33f);
                        //                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(800, 600);
                        //                    sv_img.setLayoutParams(params);
                        img.setClickable(true);
                        img.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(News_Entity_Activity.this, "hehe", Toast.LENGTH_LONG).show();
                            }
                        });
                        News_img_content.addView(img);
                        img.setImageURI(Uri.parse(url));
                    }
                    break;

            }

        }
    };

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void getNews(JYU_Important_News news) {
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
