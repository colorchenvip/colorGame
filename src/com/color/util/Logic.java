package com.color.util;

import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

import android.app.Activity;
import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Environment;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.color.colorGame.BaseActivity;
import com.color.colorGame.R;


public class Logic {
	public static ArrayList<Activity> allActivity = new ArrayList<Activity>();// 用于保存运行过的activity
	/* 持有私有静态实例，防止被引用，此处赋值为null，目的是实现延迟加载 */
	private static Logic instance = null;

	/* 私有构造方法，防止被实例化 */
	private Logic() {

	}
	/**
	 * 从集合中读取activity
	 * 
	 * @param name
	 *            activity名称
	 * @return
	 */
	public static Activity getActivityByName(String name) {
		for (Activity ac : allActivity) {
			if (ac.getClass().getName().indexOf(name) >= 0) {
				return ac;
			}
		}
		return null;
	}


	/**
	 * 用户聊天图片路径
	 * 
	 * @param userId
	 * @param friendUserId
	 * @param name
	 * @return
	 */
	public static File getImagePath(String name) {
		File file1 = new File(Environment.getExternalStorageDirectory(),
				"/jiusiji/file/msg/image/");
		if (!file1.exists()) {
			try {
				file1.mkdirs();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		File file = new File(Environment.getExternalStorageDirectory(),
				"/jiusiji/file/msg/image/" + name);
		return file;
	}
	
	/**
	 * 改变登陆状态
	 * 
	 * @param context
	 */
	public static void changeLoginState(Context context, boolean state) {
		SaveApplicationParam.saveLandState(context, false);
	}
	static MediaPlayer mMediaPlayer;
	private final static AtomicBoolean lock = new AtomicBoolean();

	public static void startAlert(Context context) {
		try {
			if (lock.compareAndSet(false, true)) {
				mMediaPlayer = new MediaPlayer();
				AssetFileDescriptor afd = context.getAssets().openFd(
						"saizi.mp3");
				mMediaPlayer.setDataSource(afd.getFileDescriptor(),
						afd.getStartOffset(), afd.getLength());
				afd.close();
				mMediaPlayer.prepare();
				mMediaPlayer.start();
				mMediaPlayer
						.setOnCompletionListener(new OnCompletionListener() {

							public void onCompletion(MediaPlayer mp) {
								// TODO Auto-generated method stub
								try {
									if (mMediaPlayer != null) {
										mMediaPlayer.stop();
										mMediaPlayer.release();
										mMediaPlayer = null;
										return;
									}
								} catch (Exception e) {
									// TODO: handle exception
								} finally {
									lock.set(false);
								}
							}
						});
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 关闭声音
	 */
	public void stopMedia(){
		try {
			if (mMediaPlayer != null) {
				mMediaPlayer.stop();
				mMediaPlayer.release();
				mMediaPlayer = null;
				return;
			}
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			lock.set(false);
		}
	}
	public static Logic getInstance() {
		if (instance == null) {
			synchronized (Logic.class) {
				if (instance == null) {
					instance = new Logic();
				}
			}
		}
		return instance;
	}
	
	/**
	 * 
	 * @param view
	 * @param mainTitle
	 *            title
	 * @param leftState
	 *            左button是否显示
	 * @param rightContent
	 *            右button 是否显示
	 */
	public void initHeadView(View view, String mainTitle, boolean leftState,
			String rightContent, final ECallBack callBack) {
		TextView mainText = (TextView) view.findViewById(R.id.main_text);
		mainText.setText(getString(mainTitle));
		LinearLayout left = (LinearLayout) view.findViewById(R.id.head_back);
		if (!leftState) {
			left.setVisibility(View.GONE);
		} else {
			left.setVisibility(View.VISIBLE);
		}
		left.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (callBack == null) {
					return;
				}
				callBack.OnCreate(Const.LEFT);
			}
		});
		Button right = (Button) view.findViewById(R.id.right);
		if (Logic.getString(rightContent).length() == 0) {
			right.setVisibility(View.GONE);
		} else {
			right.setVisibility(View.VISIBLE);
			right.setText(rightContent);
		}
		right.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (callBack == null) {
					return;
				}
				callBack.OnCreate(Const.RIGHT);
			}
		});
		TextView textRight = (TextView) view.findViewById(R.id.empty);
		textRight.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (callBack == null) {
					return;
				}
				callBack.OnCreate(Const.CLEAR);
			}
		});
	}

	

	/**
	 * 是否为手机号码
	 * 
	 * @param num
	 * @return
	 */
	public static boolean ifPhoneNum(String num) {

		return num.matches("^1[3458]\\d{9}$");
	}


	public BaseActivity currentActivity;

	/**
	 * 防止NullPointException
	 * 
	 * @param obj
	 * @return
	 */
	public static String getString(Object obj) {
		if (obj == null) {
			return "";
		}
		StringBuffer sb = new StringBuffer();
		sb.append(obj);
		return sb.toString();
	}

	/**
	 * 初始化当前activity
	 * 
	 * @param activity
	 */
	
	public void initCurrentActivity(BaseActivity activity) {
		currentActivity = activity;
	}

	/**
	 * 初始化图片显示逻辑
	 * 
	 * @param imageView
	 */
	public void initImageView(View view) {
		LinearLayout.LayoutParams layoutParams = (LayoutParams) view
				.getLayoutParams();
		layoutParams.width = BaseActivity.SCREENWIDE;
		layoutParams.height = BaseActivity.SCREENWIDE * Const.PICTURE_HEIGHT
				/ Const.PICTURE_WIDE;
		view.setLayoutParams(layoutParams);
	}

	/**
	 * 初始化酒游戏背景显示逻辑
	 * 
	 * @param imageView
	 */
	public void initWineGameView(View view) {
		RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) view
				.getLayoutParams();
		layoutParams.width = (int) 300;
		layoutParams.height = (int) 192;
		view.setLayoutParams(layoutParams);
	}

	/**
	 * 初始化色子游戏背景显示逻辑
	 * 
	 * @param imageView
	 */
	public void initDiceGameView(Context context, View view) {
		RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) view
				.getLayoutParams();
		layoutParams.width = (int) (BaseActivity.SCREENWIDE - BaseActivity
				.dip2px(context, 30)) * 3 / 4;
		layoutParams.height = (int) ((BaseActivity.SCREENWIDE - BaseActivity
				.dip2px(context, 30)) * 3 / 4 * 253 / 281);
		view.setLayoutParams(layoutParams);
	}

	/**
	 * 初始化listView的高度
	 * 
	 * @param listView
	 * @param listSize
	 */
	public void initListView(ListView listView, int listSize, Context context) {
		LinearLayout.LayoutParams layoutParams = (LayoutParams) listView
				.getLayoutParams();
		layoutParams.width = BaseActivity.SCREENWIDE;
		layoutParams.height = BaseActivity.dip2px(context, 40) * listSize;
		listView.setLayoutParams(layoutParams);
	}

	/**
	 * 初始化ImageView的高度
	 * 
	 * @param listView
	 * @param listSize
	 */
	public void initErWeiMaView(View imageView, Context context) {
		FrameLayout.LayoutParams layoutParams = (android.widget.FrameLayout.LayoutParams) imageView
				.getLayoutParams();
		layoutParams.width = BaseActivity.SCREENWIDE;
		layoutParams.height = (int) (BaseActivity.SCREENWIDE * 0.8);
		imageView.setLayoutParams(layoutParams);
	}

	/**
	 * 精确相加
	 * 
	 * @param a
	 * @param b
	 * @return
	 */
	public double doubleAdd(String a, String b) {
		BigDecimal b1 = new BigDecimal(a);
		BigDecimal b2 = new BigDecimal(b);
		return b1.add(b2).doubleValue();
	}

	/**
	 * 精确相乘
	 * 
	 * @param a
	 * @param b
	 * @return
	 */
	public double doubleMultiply(String a, String b) {
		BigDecimal b1 = new BigDecimal(a);
		BigDecimal b2 = new BigDecimal(b);
		return b1.multiply(b2).doubleValue();
	}



	public String getAddress(String address) {
		String str;
		if (address.contains("区")) {
			int index = address.indexOf("区");
			str = address.substring(index + 1);
			if (str.length() <= 2) {
				return address;
			}
		} else if (address.contains("市")) {
			int index = address.indexOf("市");
			str = address.substring(index + 1);
		} else if (address.contains("省")) {
			int index = address.indexOf("省");
			str = address.substring(index + 1);
		} else {
			str = address;
		}
		if (Logic.getString(str).length() == 0) {
			return address;
		} else if (Logic.getString(str).length() > 4) {
			if (str.contains("镇")) {
				int index1 = str.indexOf("镇");
				return getFourStr(str.substring(0, index1 + 1));
			} else if (str.contains("村")) {
				int index1 = str.indexOf("村");
				return getFourStr(str.substring(0, index1 + 1));
			} else if (str.contains("街")) {
				int index1 = str.indexOf("街");
				return getFourStr(str.substring(0, index1 + 1));
			} else if (str.contains("路")) {
				int index1 = str.indexOf("路");
				return getFourStr(str.substring(0, index1 + 1));
			} else if (str.contains("处")) {
				int index1 = str.indexOf("处");
				return getFourStr(str.substring(0, index1 + 1));
			} else if (str.contains("村")) {
				int index1 = str.indexOf("村");
				return getFourStr(str.substring(0, index1 + 1));
			}
		}
		return str;
	}

	private String getFourStr(String str) {
		if (Logic.getString(str).length() > 4) {
			return str.substring(0, 4) + "...";
		}
		return str;
	}


	private String path = "/data/data/com.jiusiji/files/searchhistory.db";


	// animation 左进
	public void AnimaLeftIn(Context context, View view) {
		Animation animation_out = AnimationUtils.loadAnimation(context,
				R.anim.push_left_in);
		view.startAnimation(animation_out);
	}

	// animation 左出
	public void AnimaLeftOut(Context context, View view) {
		Animation animation_out = AnimationUtils.loadAnimation(context,
				R.anim.push_left_out);
		view.startAnimation(animation_out);
	}

	// animation 右进
	public void AnimaRightIn(Context context, View view) {
		Animation animation_out = AnimationUtils.loadAnimation(context,
				R.anim.push_right_in);
		view.startAnimation(animation_out);
	}

	// animation 左出
	public void AnimaRightOut(Context context, View view) {
		Animation animation_out = AnimationUtils.loadAnimation(context,
				R.anim.push_right_out);
		view.startAnimation(animation_out);
	}


}
