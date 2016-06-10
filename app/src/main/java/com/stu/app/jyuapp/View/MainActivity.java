package com.stu.app.jyuapp.View;

import android.animation.ValueAnimator;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.animation.LinearInterpolator;
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
import com.stu.app.jyuapp.Model.Adapter.HomeViewPagerAdapter;
import com.stu.app.jyuapp.Model.Adapter.MainPagerAdapter;
import com.stu.app.jyuapp.Model.EventOBJ.RequestChangeBoomBtStatus;
import com.stu.app.jyuapp.R;
import com.stu.app.jyuapp.View.Fragment.SchoolNewsFragment;
import com.stu.app.jyuapp.View.Fragment.SubscriptionShowFragment;
import com.stu.app.jyuapp.View.Fragment.myFragment;
import com.stu.app.jyuapp.View.Fragment.subscriptionFindFragment;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static com.stu.app.jyuapp.Model.EventOBJ.RequestChangeBoomBtStatus.BoomMenuStatus.BOOM_INVISIBLE;
import static com.stu.app.jyuapp.Model.EventOBJ.RequestChangeBoomBtStatus.BoomMenuStatus.BOOM_NOTIFY;
import static com.stu.app.jyuapp.Model.EventOBJ.RequestChangeBoomBtStatus.BoomMenuStatus.BOOM_VISIBLE;

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
        EventBus.getDefault().register(this);
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
    Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 0:
                    ValueAnimator vValue_visible = ValueAnimator.ofFloat(0.0f,0.2f,0.4f,0.6f,0.8f,1.0f);
                    vValue_visible.setDuration(500);
                    vValue_visible.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                        @Override
                        public void onAnimationUpdate(ValueAnimator animation) {
                            Float scale = (Float) animation.getAnimatedValue();
                            boomMenuButton.setScaleX(scale);
                            boomMenuButton.setScaleY(scale);
                        }
                    });
                    vValue_visible.setInterpolator(new LinearInterpolator());
                    vValue_visible.start();
                    break;
                case 1:
                    ValueAnimator vValue_invisible = ValueAnimator.ofFloat(1.0f,0.8f,0.6f,0.4f,0.2f,0.0f);
                    vValue_invisible.setDuration(500);
                    vValue_invisible.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                        @Override
                        public void onAnimationUpdate(ValueAnimator animation) {
                            Float scale = (Float) animation.getAnimatedValue();
                            boomMenuButton.setScaleX(scale);
                            boomMenuButton.setScaleY(scale);
                        }
                    });
                    vValue_invisible.setInterpolator(new LinearInterpolator());
                    vValue_invisible.start();
                    break;

            }

        }
    };
    private  RequestChangeBoomBtStatus.BoomMenuStatus mainlastStatus;
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void receiverRequestChangeBoomBtStatus(RequestChangeBoomBtStatus.BoomMenuStatus boomMenuStatus) {
        switch (boomMenuStatus){
            case BOOM_VISIBLE:
//                if (mainlastStatus!=BOOM_VISIBLE) {
//                mainlastStatus = BOOM_VISIBLE;
                    mHandler.sendEmptyMessageDelayed(0,0);
//                }
                break;
            case BOOM_INVISIBLE:
//                if (mainlastStatus!=BOOM_INVISIBLE){
//                    mainlastStatus = BOOM_INVISIBLE;
                    mHandler.sendEmptyMessageDelayed(1,0);
//                }
                break;
        }
        mainlastStatus=boomMenuStatus;
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
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (mainlastStatus ==BOOM_INVISIBLE) {
                    EventBus.getDefault().post(BOOM_VISIBLE);
                    EventBus.getDefault().post(BOOM_NOTIFY);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
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
        navigationTabBar.setModels(models);
        navigationTabBar.setViewPager(viewPager, 0);
    }
private long exitTime=0;
    @Override
    public void onBackPressed() {
        if ((System.currentTimeMillis()-exitTime)>1500){
            Toast.makeText(getApplicationContext(),"再按一次退出程序",Toast.LENGTH_LONG).show();
            exitTime=System.currentTimeMillis();
        }else {
            finish();
        }
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
