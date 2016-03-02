package com.color.colorGame;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.color.util.ECallBack;
import com.color.util.Logic;
import com.color.util.SensorManagerHelper;
import com.color.util.SensorManagerHelper.OnShakeListener;

import net.youmi.android.banner.AdSize;
import net.youmi.android.banner.AdView;
import net.youmi.android.spot.SpotManager;

import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * coloGame 入口类
 * 作者：colorChen
 */
public class GameActivity extends BaseActivity implements OnClickListener {

    private RadioGroup group;

    /**
     * 酒游戏
     */
    private WheelView left;
    private WheelView right;
    private WheelView middle;
    private Button wineStart;
    private int item1 = 1;
    private int item2 = 1;
    private int item3 = 1;
    HashMap<Integer, SoftReference<Bitmap>> bitmaps = new HashMap<Integer, SoftReference<Bitmap>>();
    private int leftData[] = {R.drawable.game_zhiding, R.drawable.game_yiqi,
            R.drawable.game_ziji, R.drawable.game_left, R.drawable.game_right,
            R.drawable.game_zhiding, R.drawable.game_ziji,
            R.drawable.game_left, R.drawable.game_right,
            R.drawable.game_zhiding, R.drawable.game_ziji,
            R.drawable.game_mail, R.drawable.game_zhiding,
            R.drawable.game_ziji, R.drawable.game_left,
            R.drawable.game_zhiding, R.drawable.game_right,
            R.drawable.game_mail, R.drawable.game_femail};
    private int middleData[] = {R.drawable.game_hejiu,
            R.drawable.game_changge, R.drawable.game_hejiu,
            R.drawable.game_tiaowu, R.drawable.game_hejiu,
            R.drawable.game_xiaohua, R.drawable.game_hejiu,
            R.drawable.game_miyu};
    private int rightData[] = {R.drawable.game_1, R.drawable.game_2,
            R.drawable.game_1, R.drawable.game_1, R.drawable.game_2,
            R.drawable.game_1, R.drawable.game_1, R.drawable.game_3,
            R.drawable.game_1, R.drawable.game_2, R.drawable.game_1};
    private String leftDesc[] = {"指定", "一起", "自己", "左边", "右边", "指定", "自己",
            "左边", "右边", "指定", "自己", "男士", "指定", "自己", "左边", "指定", "右边", "男士",
            "女士"};
    private String middleDesc[] = {"喝", "唱", "喝", "跳", "喝", "笑话", "喝", "谜语",
            "喝",};
    private String middleDesc2[] = {"杯", "首歌", "杯", "支舞", "杯", "个", "杯", "个",
            "杯"};
    private String rightDesc[] = {"1", "2", "1", "1", "2", "1", "1", "3", "1",
            "2", "1"};

    private TextView wineDesc;
    private boolean WINE;
    /**
     * 色子
     */
    private int sizeNum;
    private ImageView leftNum, rightNum;// 每个色子总数目
    private ImageView basinBg, dice1, dice2, dice3, dice4, dice5, dice6;
    private TextView diceNum;// 色子个数
    private Button triangle, dice_start;
    private ListView listView;
    RadioButton diceRadioButton, wineRadioButton;
    private MyScrollLayout myScrollLayout;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;

    }

    @Override
    public boolean onMenuItemSelected(int featureId, MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                startActivity(new Intent(GameActivity.this, MainActivity.class));
                break;

            default:
                break;
        }
        return super.onMenuItemSelected(featureId, item);
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game);
        myScrollLayout = (MyScrollLayout) this.findViewById(R.id.scrollLayout);
        myScrollLayout.SetOnViewChangeListener(new OnViewChangeListener() {

            public void OnViewChange(int index) {
                if (index == 10) {// 自动进入， 暂时屏蔽
                } else if (index == 0) {
                    diceRadioButton.setChecked(true);
                    WINE = false;
                } else if (index == 1111) {
                    listView.setVisibility(View.GONE);
                } else {
                    WINE = true;
                    wineRadioButton.setChecked(true);
                }
            }
        });
        View view = this.findViewById(R.id.view);
        Logic.getInstance().initHeadView(view, "酒斯基游戏", true, "",
                new ECallBack() {

                    public void OnError(Object obj) {
                        // TODO Auto-generated method stub

                    }

                    public void OnCreate(Object obj) {
                        // TODO Auto-generated method stub
                        finish();
                    }
                });
        diceRadioButton = (RadioButton) this.findViewById(R.id.dice);
        diceRadioButton.setChecked(true);
        wineRadioButton = (RadioButton) this.findViewById(R.id.wine);
        WINE = false;
        group = (RadioGroup) this.findViewById(R.id.group);
        group.setOnCheckedChangeListener(new OnCheckedChangeListener() {

            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // TODO Auto-generated method stub.
                listView.setVisibility(View.GONE);
                if (checkedId == R.id.dice) {
                    WINE = false;
                    myScrollLayout.snapToScreen(0);

                } else if (checkedId == R.id.wine) {
                    WINE = true;
                    myScrollLayout.snapToScreen(1);
                }
            }
        });
        SensorManagerHelper sensorHelper = new SensorManagerHelper(this);
        sensorHelper.setOnShakeListener(new OnShakeListener() {

            public void onShake() {
                // TODO Auto-generated method stub
                if (WINE || animRuning) {// 酒游戏界面不显示
                    return;
                }
                startImage();
            }
        });
        initWineView();
        initDiceView();

        // 实例化广告条
        AdView adView = new AdView(this, AdSize.FIT_SCREEN);
        // 获取要嵌入广告条的布局
        LinearLayout adLayout = (LinearLayout) findViewById(R.id.adLayout);
        // 将广告条加入到布局中
        adLayout.addView(adView);
        // // 实例化 LayoutParams（重要）
        // FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(
        // FrameLayout.LayoutParams.FILL_PARENT,
        // FrameLayout.LayoutParams.WRAP_CONTENT);
        // // 设置广告条的悬浮位置
        // layoutParams.gravity = Gravity.TOP | Gravity.RIGHT; // 这里示例为右下角
        // // 实例化广告条
        // AdView adView = new AdView(this, AdSize.FIT_SCREEN);
        // // 调用 Activity 的 addContentView 函数
        // this.addContentView(adView, layoutParams);

        // 插屏广告
        SpotManager.getInstance(getApplicationContext()).loadSpotAds();
        // 竖屏的值为 SpotManager.ORIENTATION_PORTRAIT
        // 横屏的值为 SpotManager.ORIENTATION_LANDSCAPE
        SpotManager.getInstance(getApplicationContext()).setSpotOrientation(
                SpotManager.ORIENTATION_LANDSCAPE);
        SpotManager.getInstance(getApplicationContext()).setAnimationType(
                SpotManager.ANIM_ADVANCE);// 为高级动画效果
        SpotManager.getInstance(this).showSpotAds(this);
        // 插屏流程错误检查
        SpotManager.getInstance(getApplicationContext()).checkSDKProcess(
                "f4708ceb45e0f9ef", "66d7f749a0ffb965");

    }

    Animation mAnimation;
    boolean animRuning = false;

    private void initDice(View view) {
        int wide = (int) (BaseActivity.SCREENWIDE - BaseActivity.dip2px(this,
                30)) * 3 / 4;
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) view
                .getLayoutParams();
        layoutParams.width = wide / 6;
        layoutParams.height = wide / 6;
        view.setLayoutParams(layoutParams);
    }

    private void initDiceView() {
        leftNum = (ImageView) this.findViewById(R.id.leftNum);
        rightNum = (ImageView) this.findViewById(R.id.rightNum);
        diceNum = (TextView) this.findViewById(R.id.diceNum);
        diceNum.setText("五粒");
        sizeNum = 4;

        diceNum.setOnClickListener(this);
        triangle = (Button) this.findViewById(R.id.triangle);
        triangle.setOnClickListener(this);
        basinBg = (ImageView) this.findViewById(R.id.basinBg);
        Logic.getInstance().initDiceGameView(this, basinBg);
        dice1 = (ImageView) this.findViewById(R.id.dice1);
        initDice(dice1);
        dice2 = (ImageView) this.findViewById(R.id.dice2);
        initDice(dice2);
        dice3 = (ImageView) this.findViewById(R.id.dice3);
        initDice(dice3);
        dice4 = (ImageView) this.findViewById(R.id.dice4);
        initDice(dice4);
        dice5 = (ImageView) this.findViewById(R.id.dice5);
        initDice(dice5);
        dice6 = (ImageView) this.findViewById(R.id.dice6);
        initDice(dice6);
        initDice(4);
        dice_start = (Button) this.findViewById(R.id.dice_start);
        dice_start.setOnClickListener(new OnClickListener() {

            public void onClick(View v) {
                // TODO Auto-generated method stub
                if (WINE || animRuning) {// 酒游戏界面不显示
                    return;
                }
                startImage();
            }
        });
        listView = (ListView) this.findViewById(R.id.listView);
        SpinnerAdapter adapter = new SpinnerAdapter();
        listView.setAdapter(adapter);
        listView.setVisibility(View.GONE);
        listView.setOnItemClickListener(new OnItemClickListener() {

            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {
                // TODO Auto-generated method stub
                sizeNum = arg2;
                initDice(arg2);
                listView.setVisibility(View.GONE);
            }
        });
        // 动画
        mAnimation = AnimationUtils
                .loadAnimation(this, R.anim.myown_design_bak);
        mAnimation.setAnimationListener(new AnimationListener() {
            public void onAnimationStart(Animation animation) {
                animRuning = true;
                new Thread(new Runnable() {

                    public void run() {
                        // TODO Auto-generated method stub
                        SystemClock.sleep(1000);
                        Logic.startAlert(GameActivity.this);
                    }
                }).start();

            }

            public void onAnimationEnd(Animation animation) {
                // TODO Auto-generated method stub
                int num = 0;
                int num1 = (int) (Math.random() * 6) + 1;
                int num2 = (int) (Math.random() * 6) + 1;
                int num3 = (int) (Math.random() * 6) + 1;
                int num4 = (int) (Math.random() * 6) + 1;
                int num5 = (int) (Math.random() * 6) + 1;
                int num6 = (int) (Math.random() * 6) + 1;
                if (sizeNum >= 0) {
                    num += num1;
                    initDicePic(dice1, num1);
                }
                if (sizeNum >= 1) {
                    num += num2;
                    initDicePic(dice2, num2);
                }
                if (sizeNum >= 2) {
                    num += num3;
                    initDicePic(dice3, num3);
                }
                if (sizeNum >= 3) {
                    num += num4;
                    initDicePic(dice4, num4);
                }
                if (sizeNum >= 4) {
                    num += num5;
                    initDicePic(dice5, num5);
                }
                if (sizeNum >= 5) {
                    num += num6;
                    initDicePic(dice6, num6);
                }
                Vibrate(GameActivity.this, 200);
                showNum(num);
                animRuning = false;
            }

            public void onAnimationRepeat(Animation animation) {
                // TODO Auto-generated method stub

            }
        });
    }

    private void initDice(int arg2) {
        if (arg2 >= 0) {
            dice1.setVisibility(View.VISIBLE);
        } else {
            dice1.setVisibility(View.INVISIBLE);
        }
        if (arg2 >= 1) {
            dice2.setVisibility(View.VISIBLE);
        } else {
            dice2.setVisibility(View.INVISIBLE);
        }
        if (arg2 >= 2) {
            dice3.setVisibility(View.VISIBLE);
        } else {
            dice3.setVisibility(View.INVISIBLE);
        }
        if (arg2 >= 3) {
            dice4.setVisibility(View.VISIBLE);
        } else {
            dice4.setVisibility(View.INVISIBLE);
        }
        if (arg2 >= 4) {
            dice5.setVisibility(View.VISIBLE);
        } else {
            dice5.setVisibility(View.INVISIBLE);
        }
        if (arg2 >= 5) {
            dice6.setVisibility(View.VISIBLE);
        } else {
            dice6.setVisibility(View.INVISIBLE);
        }
        switch (arg2) {
            case 0:
                diceNum.setText("一粒");
                break;
            case 1:
                diceNum.setText("二粒");
                break;
            case 2:
                diceNum.setText("三粒");
                break;
            case 3:
                diceNum.setText("四粒");
                break;
            case 4:
                diceNum.setText("五粒");
                break;
            case 5:
                diceNum.setText("六粒");
                break;
            default:
                break;
        }
    }

    public void showNum(int num) {
        int TENNUM = 0;
        int NUM = 0;
        if (num >= 20) {
            TENNUM = 2;
            leftNum.setBackgroundResource(R.drawable.num_2);
        } else if (num >= 10) {
            TENNUM = 1;
            leftNum.setBackgroundResource(R.drawable.num_1);
        } else {
            leftNum.setBackgroundResource(R.drawable.num_0);
        }
        NUM = num - TENNUM * 10;
        switch (NUM) {
            case 0:
                rightNum.setBackgroundResource(R.drawable.num_0);
                break;
            case 1:
                rightNum.setBackgroundResource(R.drawable.num_1);
                break;
            case 2:
                rightNum.setBackgroundResource(R.drawable.num_2);
                break;
            case 3:
                rightNum.setBackgroundResource(R.drawable.num_3);
                break;
            case 4:
                rightNum.setBackgroundResource(R.drawable.num_4);
                break;
            case 5:
                rightNum.setBackgroundResource(R.drawable.num_5);
                break;
            case 6:
                rightNum.setBackgroundResource(R.drawable.num_6);
                break;
            case 7:
                rightNum.setBackgroundResource(R.drawable.num_7);
                break;
            case 8:
                rightNum.setBackgroundResource(R.drawable.num_8);
                break;
            case 9:
                rightNum.setBackgroundResource(R.drawable.num_9);
                break;
            default:
                break;
        }
    }

    public void startImage() {
        if (sizeNum >= 0) {
            dice1.startAnimation(mAnimation);
        }
        if (sizeNum >= 1) {
            dice2.startAnimation(mAnimation);
        }
        if (sizeNum >= 2) {
            dice3.startAnimation(mAnimation);
        }
        if (sizeNum >= 3) {
            dice4.startAnimation(mAnimation);
        }
        if (sizeNum >= 4) {
            dice5.startAnimation(mAnimation);
        }
        if (sizeNum >= 5) {
            dice6.startAnimation(mAnimation);
        }

    }

    private void initDicePic(View view, int num) {
        switch (num) {
            case 1:
                view.setBackgroundResource(R.drawable.d1);
                break;
            case 2:
                view.setBackgroundResource(R.drawable.d2);
                break;
            case 3:
                view.setBackgroundResource(R.drawable.d3);
                break;
            case 4:
                view.setBackgroundResource(R.drawable.d4);
                break;
            case 5:
                view.setBackgroundResource(R.drawable.d5);
                break;
            case 6:
                view.setBackgroundResource(R.drawable.d6);
                break;

            default:
                break;
        }
    }

    private boolean VATSTATE;

    private void initWineView() {
        if (SCREENWIDE == 0 || SCREENWIDE == 0) {
            getScreenInfo();
        }
        // wineLayout = (ViewGroup) this.findViewById(R.id.wineLayout);
        // Logic.getInstance().initWineGameView(wineLayout);
        wineDesc = (TextView) this.findViewById(R.id.wine_desc);
        wineStart = (Button) this.findViewById(R.id.wine_start);
        wineStart.setOnClickListener(new OnClickListener() {

            public void onClick(View v) {
                // TODO Auto-generated method stub
                VATSTATE = true;
                wheel1 = false;
                wheel2 = false;
                wheel3 = false;
                mixWheel(left, -(int) (Math.random() * leftData.length) - 100);
                mixWheel(middle,
                        -(int) (Math.random() * middleData.length) - 100);
                mixWheel(right, -(int) (Math.random() * rightData.length) - 100);
            }
        });
        left = (WheelView) this.findViewById(R.id.leftView);
        right = (WheelView) this.findViewById(R.id.rightView);
        middle = (WheelView) this.findViewById(R.id.middle);
        middle.setVisibleItems(3);
        middle.setViewAdapter(new CountryAdapter(this, getList(middleData)));
        middle.setCyclic(true);
        left.setVisibleItems(3);
        left.setViewAdapter(new CountryAdapter(this, getList(leftData)));
        left.setCyclic(true);
        right.setVisibleItems(3);
        right.setViewAdapter(new CountryAdapter(this, getList(rightData)));
        right.setCyclic(true);

        left.addChangingListener(new OnWheelChangedListener() {
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
            }
        });

        left.addScrollingListener(new OnWheelScrollListener() {
            public void onScrollingStarted(WheelView wheel) {
            }

            public void onScrollingFinished(WheelView wheel) {
                item1 = wheel.getCurrentItem();
                setWheel(1, item1);
            }
        });
        middle.addChangingListener(new OnWheelChangedListener() {
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
            }
        });

        middle.addScrollingListener(new OnWheelScrollListener() {
            public void onScrollingStarted(WheelView wheel) {
            }

            public void onScrollingFinished(WheelView wheel) {
                item2 = wheel.getCurrentItem();
                setWheel(2, item2);
            }
        });
        right.addChangingListener(new OnWheelChangedListener() {
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
            }
        });

        right.addScrollingListener(new OnWheelScrollListener() {
            public void onScrollingStarted(WheelView wheel) {
            }

            public void onScrollingFinished(WheelView wheel) {
                item3 = wheel.getCurrentItem();
                setWheel(3, item3);
            }
        });

    }

    boolean wheel1, wheel2, wheel3;

    private void setWheel(int type, int position) {
        if (type == 1) {
            wheel1 = true;
        } else if (type == 2) {
            wheel2 = true;
        } else if (type == 3) {
            wheel3 = true;
        }

        if (wheel1 && wheel2 && wheel3) {
            if (leftDesc[item1].equals("一起") || leftDesc[item1].equals("男士")
                    || leftDesc[item1].equals("女士")) {// 一起时不能有谜语，且数量都为1
                if (middleDesc[item2].equals("谜语")
                        || middleDesc[item2].equals("笑话")) {
                    mixWheel(middle, -1);
                }
                if (!rightDesc[item3].equals("1")) {
                    mixWheel(right, -1);
                }
                if (VATSTATE) {
                    VATSTATE = false;
                    Vibrate(this, 200);
                }
                wineDesc.setText(leftDesc[item1] + middleDesc[item2] + "1"
                        + middleDesc2[item2]);
            } else if (!middleDesc[item2].equals("喝")) {// 不为喝酒时，数量都为1
                if (!rightDesc[item3].equals("1")) {
                    mixWheel(right, -1);
                }
                if (VATSTATE) {
                    VATSTATE = false;
                    Vibrate(this, 200);
                }
                wineDesc.setText(leftDesc[item1] + middleDesc[item2] + "1"
                        + middleDesc2[item2]);
            } else {
                if (VATSTATE) {
                    VATSTATE = false;
                    Vibrate(this, 200);
                }
                wineDesc.setText(leftDesc[item1] + middleDesc[item2]
                        + rightDesc[item3] + middleDesc2[item2]);
            }

        }
    }

    private ArrayList<Integer> getList(int[] data) {
        ArrayList<Integer> list = new ArrayList<Integer>();
        for (int i : data) {
            list.add(i);
        }
        return list;
    }

    /**
     * Adapter for countries
     */
    private class CountryAdapter extends AbstractWheelTextAdapter {
        // Countries names
        ArrayList<Integer> list = new ArrayList<Integer>();

        /**
         * Constructor
         */
        protected CountryAdapter(Context context, ArrayList<Integer> list) {
            super(context, R.layout.country_layout, NO_RESOURCE);
            this.list = list;
        }

        @Override
        public View getItem(int index, View cachedView, ViewGroup parent) {
            View view = super.getItem(index, cachedView, parent);

            ImageView img = (ImageView) view.findViewById(R.id.flag);
            img.setImageResource(list.get(index));
            return view;
        }

        public int getItemsCount() {
            return list.size();
        }

        protected CharSequence getItemText(int index) {
            return Logic.getString(list.get(index));
        }
    }

    private void mixWheel(WheelView wv, int distance) {
        wv.scroll(distance, 2000);
    }

    public void onClick(View v) {
        // TODO Auto-generated method stub
        if (v.getId() == R.id.triangle) {
            if (listView.getVisibility() == View.VISIBLE) {
                listView.setVisibility(View.GONE);
            } else {
                listView.setVisibility(View.VISIBLE);
            }

        } else if (v.getId() == R.id.diceNum) {

        }
    }


    class SpinnerAdapter extends BaseAdapter {
        private String[] data = {"一粒", "二粒", "三粒", "四粒", "五粒", "六粒"};

        public int getCount() {
            // TODO Auto-generated method stub
            return data.length;
        }

        public Object getItem(int position) {
            // TODO Auto-generated method stub
            return data[position];
        }

        public long getItemId(int position) {
            // TODO Auto-generated method stub
            return position;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            // TODO Auto-generated method stub
            ViewHolder viewHolder;
            if (convertView == null) {
                convertView = LinearLayout.inflate(GameActivity.this,
                        R.layout.diceitem, null);
                viewHolder = new ViewHolder();
                viewHolder.content = (TextView) convertView
                        .findViewById(R.id.content);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            viewHolder.content.setText(data[position]);
            return convertView;
        }

        class ViewHolder {
            TextView content;
        }

    }

    public void onBackPressed() {

        // 如果有需要，可以点击后退关闭插播广告。
        if (!SpotManager.getInstance(this).disMiss()) {
            // 弹出退出窗口，可以使用自定义退屏弹出和回退动画,参照demo,若不使用动画，传入-1
            super.onBackPressed();
        }
    }

    @Override
    protected void onStop() {

        // 如果不调用此方法，则按home键的时候会出现图标无法显示的情况。
        SpotManager.getInstance(this).onStop();
        super.onStop();
    }

    @Override
    protected void onDestroy() {

        SpotManager.getInstance(this).onDestroy();
        super.onDestroy();
    }

    /**
     * 返回键响应
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exitBy2Click(); // 调用双击退出函数
        }
        return true;
    }
}
