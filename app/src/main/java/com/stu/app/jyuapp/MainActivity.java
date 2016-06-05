package com.stu.app.jyuapp;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

import com.gigamole.library.NavigationTabBar;
import com.nightonke.boommenu.BoomMenuButton;
import com.nightonke.boommenu.Eases.EaseType;
import com.nightonke.boommenu.Types.BoomType;
import com.nightonke.boommenu.Types.ButtonType;
import com.nightonke.boommenu.Types.ClickEffectType;
import com.nightonke.boommenu.Types.DimType;
import com.nightonke.boommenu.Types.OrderType;
import com.nightonke.boommenu.Types.PlaceType;
import com.nightonke.boommenu.Util;
import com.stu.app.jyuapp.Adapter.HomeViewPagerAdapter;
import com.stu.app.jyuapp.Adapter.MainPagerAdapter;
import com.stu.app.jyuapp.Fragment.SchoolNewsFragment;
import com.stu.app.jyuapp.Fragment.SubscriptionShowFragment;
import com.stu.app.jyuapp.Fragment.myFragment;
import com.stu.app.jyuapp.Fragment.subscriptionFindFragment;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private String[] Colors = {
            "#F44336",
            "#E91E63",
            "#9C27B0",
            "#2196F3",
            "#03A9F4",
            "#00BCD4",
            "#009688",
            "#4CAF50",
            "#8BC34A",
            "#CDDC39",
            "#FFEB3B",
            "#FFC107",
            "#FF9800",
            "#FF5722",
            "#795548",
            "#9E9E9E",
            "#607D8B"};
    private BoomMenuButton boomMenuButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        //        this.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        setContentView(R.layout.activity_home_ui_horizontal_ntb);

        boomMenuButton = (BoomMenuButton) findViewById(R.id.boom);
        initData();
        initUI();
        initEvent();
    }

    private void initData() {

    }

    private void initEvent() {

    }

    public int GetRandomColor() {
        Random random = new Random();
        int p = random.nextInt(Colors.length);
        return Color.parseColor(Colors[p]);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        int number = 4;
        //多少个按钮就多少列颜色组合，分别是平时状态和按下的状态
        int[][] colors = new int[number][2];
        for (int i = 0; i < number; i++) {
            colors[i][1] = GetRandomColor();
            colors[i][0] = Util.getInstance().getPressedColor(colors[i][1]);
        }
        new BoomMenuButton.Builder()
                // set all sub buttons with subButtons method
                //.subButtons(subButtonDrawables, subButtonColors, subButtonTexts)
                // or add each sub button with addSubButton method
                .addSubButton(this, R.mipmap.ic_chat, colors[0], "嘉大聊天室")
                .addSubButton(this, R.mipmap.ic_hollow, colors[1], "树洞")
                .addSubButton(this, R.mipmap.ic_lostfind, colors[2], "失物认领")
                .addSubButton(this, R.mipmap.ic_market, colors[3], "跳蚤市场")
                .frames(80)
                .duration(800)
                .delay(100)
                .subButtonTextColor(Color.WHITE)
                .showOrder(OrderType.RANDOM)
                .hideOrder(OrderType.RANDOM)
                .button(ButtonType.CIRCLE)
                .boom(BoomType.PARABOLA)
                .place(PlaceType.CIRCLE_4_2)
                .showMoveEase(EaseType.EaseOutBack)
                .hideMoveEase(EaseType.EaseOutCirc)
                .showScaleEase(EaseType.EaseOutBack)
                .hideScaleType(EaseType.EaseOutCirc)
                .rotateDegree(720)
                .showRotateEase(EaseType.EaseOutBack)
                .hideRotateType(EaseType.Linear)
                .autoDismiss(true)
                .cancelable(true)
                .dim(DimType.DIM_6)
                .clickEffect(ClickEffectType.RIPPLE)
                .boomButtonShadow(Util.getInstance().dp2px(2), Util.getInstance().dp2px(2))
                .subButtonsShadow(Util.getInstance().dp2px(2), Util.getInstance().dp2px(2))
                .subButtonTextColor(Color.BLACK)
                .onBoomButtonBlick(null)
                .animator(null)
                .onSubButtonClick(null)
                // this only work when the place type is SHARE_X_X
                .shareStyle(0, 0, 0)
                .init(boomMenuButton);

        //进行页面跳转时，将activity进栈
        boomMenuButton.setOnSubButtonClickListener(new BoomMenuButton.OnSubButtonClickListener() {
            @Override
            public void onClick(int buttonIndex) {
                // 返回被点击的子按钮下标
                Toast.makeText(MainActivity.this, "button index  " + buttonIndex, Toast.LENGTH_LONG).show();
                Log.i("20160603", "button ::" + buttonIndex + "  is click");
            }
        });

    }

    private void initUI() {
        ViewPager viewPager = (ViewPager) findViewById(R.id.vp_horizontal_ntb);
        List<View> mViewsSource = new ArrayList<>();
        //        mViewsSource.add(View.inflate(this, R.layout.item_vp, null));
        //        mViewsSource.add(View.inflate(this, R.layout.item_vp, null));
        //        mViewsSource.add(View.inflate(this, R.layout.item_vp, null));
        //        mViewsSource.add(View.inflate(this, R.layout.item_vp, null));
        //        mViewsSource.add( View.inflate(this,R.layout.activity_home_ui_horizontal_ntb,null));

        MainPagerAdapter adapter = new MainPagerAdapter(MainActivity.this, mViewsSource);
        List<Fragment> list = new ArrayList<Fragment>();
        list.add(new SchoolNewsFragment());
        list.add(new SubscriptionShowFragment());
        list.add(new subscriptionFindFragment());
        list.add(new myFragment());
        HomeViewPagerAdapter homeViewPagerAdapter = new HomeViewPagerAdapter(getSupportFragmentManager(), list);
        viewPager.setOffscreenPageLimit(3);
        //        viewPager.setAdapter(adapter);
        viewPager.setAdapter(homeViewPagerAdapter);
        String[] colors = getResources().getStringArray(R.array.default_preview);

        NavigationTabBar navigationTabBar = (NavigationTabBar) findViewById(R.id.ntb_horizontal);
        ArrayList<NavigationTabBar.Model> models = new ArrayList<>();
        models.add(
                new NavigationTabBar.Model.Builder(
                        getResources().getDrawable(R.mipmap.ic_home),
                        Color.parseColor(colors[0]))
                        .badgeTitle("11")
                        .title("校园首页")

                        //                        .selectedIcon(getResources().getDrawable(R.drawable.ic_eighth))
                        .build()
        );
        models.add(
                new NavigationTabBar.Model.Builder(
                        getResources().getDrawable(R.mipmap.ic_subscription),
                        Color.parseColor(colors[1]))
                        .title("订阅")
                        .badgeTitle("22")
                        //                        .selectedIcon(getResources().getDrawable(R.drawable.ic_eighth))
                        .build()
        );
        models.add(
                new NavigationTabBar.Model.Builder(
                        getResources().getDrawable(R.mipmap.ic_find),
                        Color.parseColor(colors[2]))
                        .title("发现")
                        .badgeTitle("33")
                        //                        .selectedIcon(getResources().getDrawable(R.drawable.ic_eighth))
                        .build()
        );
        models.add(
                new NavigationTabBar.Model.Builder(
                        getResources().getDrawable(R.mipmap.ic_my),
                        Color.parseColor(colors[3]))
                        .title("我")
                        .badgeTitle("44")
                        //                        .selectedIcon(getResources().getDrawable(R.drawable.ic_eighth))
                        .build()
        );
        //        models.add(
        //                new NavigationTabBar.Model.Builder(
        //                        getResources().getDrawable(R.mipmap.ic_news),
        //                        Color.parseColor(colors[4]))
        //                        .title("44")
        //                        .badgeTitle("44")
        //                        //                        .selectedIcon(getResources().getDrawable(R.drawable.ic_eighth))
        //                        .build()
        //        );
        navigationTabBar.setModels(models);
        navigationTabBar.setViewPager(viewPager, 0);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
