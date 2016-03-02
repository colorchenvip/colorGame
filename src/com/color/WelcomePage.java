package com.color;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.widget.LinearLayout;

import com.color.colorGame.GameActivity;
import com.color.colorGame.R;

import net.youmi.android.AdManager;

/**
 * app的欢迎界面
 * 
 * @created 2014-07-22
 * @author colorChen（http://my.oschina.net/LittleDY）
 * 
 */
public class WelcomePage extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		LinearLayout view = (LinearLayout) getLayoutInflater().inflate(
				R.layout.app_welcome_page, null);
		setContentView(view);

		// 渐变展示启动屏
		AlphaAnimation aa = new AlphaAnimation(0.3f, 1.0f);
		aa.setDuration(3000);
		view.startAnimation(aa);
		aa.setAnimationListener(new AnimationListener() {
			@Override
			public void onAnimationEnd(Animation arg0) {
				// 初始化有米广告
				finish();
				AdManager.getInstance(getApplicationContext()).init(
						"f4708ceb45e0f9ef", "66d7f749a0ffb965", false);
				startActivity(new Intent(WelcomePage.this, GameActivity.class)
						.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
			}

			@Override
			public void onAnimationRepeat(Animation animation) {
			}

			@Override
			public void onAnimationStart(Animation animation) {
			}

		});
	}
}
