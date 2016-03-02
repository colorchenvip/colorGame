package com.color.colorGame;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import android.app.Activity;
import android.app.ProgressDialog;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.color.util.Const;
import com.color.util.ECallBack;
import com.nostra13.universalimageloader.core.ImageLoader;


/**
 * 继承类，存放一些常用方法，方便调用
 * 
 * @author color
 * 
 */
public class BaseActivity extends Activity {

	// 屏幕宽
	public static int SCREENWIDE;
	// 屏幕高
	public static int SCREENHEIGHT;

	public static int DIPWIDE;
	public static float currentLat;
	public static float currentLng;
	public static ExecutorService FULL_TASK_EXECUTOR;

	public static boolean FRASHSHOPCAT = true;
	public static boolean FRASHADDRESS = true;
	public static ArrayList<Activity> allActivity = new ArrayList<Activity>();// 用于保存运行过的activity
																				// [com.ltd.mos.shopcar.ShopCarActivity@427df870]
	
	public static final int TIMELINE_SUPPORTED_VERSION = 0x21020001;// 分享到朋友圈支持的微信最低版本
	static {
		FULL_TASK_EXECUTOR = (ExecutorService) Executors.newCachedThreadPool();
	};
	public static ImageLoader imageLoader = ImageLoader.getInstance();



	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

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
	 * 显示网络图片
	 * 
	 * @author xuwu
	 * @param url
	 * @return
	 */
	public static Bitmap returnBitMap(String url) {

		try {
			URL myFile = new URL(url);

			Bitmap bm = null;

			HttpURLConnection conn = (HttpURLConnection) myFile
					.openConnection();
			conn.setDoInput(true);
			conn.connect();

			InputStream is = conn.getInputStream();

			bm = BitmapFactory.decodeStream(is);
			is.close();
			return bm;

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * start Activity
	 * 
	 * @param cls
	 */
	public void startActivity(Class<?> cls) {

		startActivity(new Intent(this, cls));
	}

	/**
	 * 获取屏幕宽高
	 * 
	 * @param activity
	 */
	public void getScreenInfo() {
		DisplayMetrics metrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(metrics);
		SCREENHEIGHT = metrics.heightPixels;
		SCREENWIDE = metrics.widthPixels;
		DIPWIDE = px2dip(this, SCREENWIDE);
	}

	/**
	 * 设置酒详情 酒图片的宽高
	 * 
	 * @param view
	 */
	public void setWineDetailImageWH(View view) {

		if (SCREENWIDE == 0 || SCREENHEIGHT == 0) {
			getScreenInfo();
		}
		LayoutParams layoutParams = view.getLayoutParams();
		layoutParams.width = SCREENWIDE;
		layoutParams.height = 307 * SCREENWIDE / 480;
		view.setLayoutParams(layoutParams);

	}

	/**
	 * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
	 */
	public static int px2dip(Context context, float pxValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (pxValue / scale + 0.5f);
	}

	/**
	 * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
	 */
	public static int dip2px(Context context, double dpValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dpValue * scale + 0.5f);
	}

	/**
	 * 双击退出
	 */
	private static Boolean isExit = false;

	public void exitBy2Click() {
		Timer tExit = null;
		if (isExit == false) {
			isExit = true; // 准备退出
			Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
			tExit = new Timer();
			tExit.schedule(new TimerTask() {
				@Override
				public void run() {
					isExit = false; // 取消退出
				}
			}, 2000); // 如果2秒钟内没有按下返回键，则启动定时器取消掉刚才执行的任务

		} else {
			finish();
			System.exit(0);
		}
	}

	private BaseDialog billDialog;
	int selectType = 1;



	private ProgressDialog progressDialog;

	/**
	 * 进度条对话框
	 * 
	 * @author xuwu
	 * @param context
	 * @param content
	 */
	public void showProgressDialog(Context context, String content) {
		progressDialog = new ProgressDialog(context);
		progressDialog.setCanceledOnTouchOutside(true);
		progressDialog.setMessage(content);
		progressDialog.show();

	}


	private BaseDialog shareDialog;
	/**
	 * 分享对话框
	 * 
	 * @author color
	 * @param context
	 */
	public void showShareDialog(final ECallBack callBack) {
		shareDialog = new BaseDialog(this, R.style.dialog);
		View view = LinearLayout.inflate(this, R.layout.instraction, null);
		ImageView share_wxfriends = (ImageView) view
				.findViewById(R.id.share_wxfriends);

		share_wxfriends.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {

				if (callBack != null) {
					callBack.OnCreate(Const.WXFRIEND);// 好友
				}

			}
		});
		ImageView share_wxcircle = (ImageView) view
				.findViewById(R.id.share_wxcircle);
		share_wxcircle.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {

				if (callBack != null) {
					callBack.OnCreate(Const.WXCIRCLE);// 朋友圈
				}
			}
		});

		shareDialog.setContentView(view);
		shareDialog.setCanceledOnTouchOutside(true);
		shareDialog.show();
	}

	/**
	 * final Activity activity ：调用该方法的Activity实例 long milliseconds ：震动的时长，单位是毫秒
	 * long[] pattern ：自定义震动模式 。数组中数字的含义依次是[静止时长，震动时长，静止时长，震动时长。。。]时长的单位是毫秒
	 * boolean isRepeat ： 是否反复震动，如果是true，反复震动，如果是false，只震动一次
	 */
	private BaseDialog areaListDialog;
	public static void Vibrate(final Activity activity, long milliseconds) {
		Vibrator vib = (Vibrator) activity
				.getSystemService(Service.VIBRATOR_SERVICE);
		vib.vibrate(milliseconds);
	}

	public static void Vibrate(final Activity activity, long[] pattern,
			boolean isRepeat) {
		Vibrator vib = (Vibrator) activity
				.getSystemService(Service.VIBRATOR_SERVICE);
		vib.vibrate(pattern, isRepeat ? 1 : -1);
	}

	public static PopupWindow unloginPop;

	public void dismissAreaList() {

		if (areaListDialog != null && areaListDialog.isShowing()) {
			areaListDialog.dismiss();
			areaListDialog = null;
		}
	}

	BaseDialog pd;
}
