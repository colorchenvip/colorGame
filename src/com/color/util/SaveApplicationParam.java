package com.color.util;


import android.app.Activity;
import android.content.Context;


public class SaveApplicationParam {

	/**
	 * 保存pwd
	 * 
	 * @author xuwu
	 * @param context
	 * @param regist
	 */
	public static void savePassword(Context context, String pwd) {
		context.getSharedPreferences(Const.USERINFO, Activity.MODE_PRIVATE)
				.edit().putString(Const.PASSWORD, pwd).commit();

	}

	/**
	 * 保存用户登陆状态
	 * 
	 * @param context
	 * @param val
	 */
	public static void saveLandState(Context context, boolean val) {
		context.getSharedPreferences(Const.USERINFO, Activity.MODE_PRIVATE)
				.edit().putBoolean(Const.LANDSTATE, val).commit();

	}

	/**
	 * 获取用户登陆状态
	 * 
	 * @param context
	 * @param val
	 */
	public static boolean getLandState(Context context) {
		return context.getSharedPreferences(Const.USERINFO,
				Activity.MODE_PRIVATE).getBoolean(Const.LANDSTATE, false);

	}

	/**
	 * 保存用户是否进入过引导界面
	 * 
	 * @param context
	 * @param val
	 */
	public static void saveGuideVal(Context context, boolean val) {
		context.getSharedPreferences(Const.USERINFO, Activity.MODE_PRIVATE)
				.edit().putBoolean(Const.INGUIDE, val).commit();

	}

	/**
	 * 获取用户是否进入过引导界面
	 * 
	 * @param context
	 * @param val
	 */
	public static boolean getGuideVal(Context context) {
		return context.getSharedPreferences(Const.USERINFO,
				Activity.MODE_PRIVATE).getBoolean(Const.INGUIDE, false);

	}

	/**
	 * 保存消息推送的开关状态
	 * 
	 * @param context
	 * @param key
	 */
	public static void saveMsgSendVal(Context context, boolean key) {
		context.getSharedPreferences(Const.PERSONAL, Activity.MODE_PRIVATE)
				.edit().putBoolean(Const.MSGSEND, key).commit();

	}

	/**
	 * 读取消息推送的开关状态
	 * 
	 * @param context
	 * @param key
	 */
	public static boolean getMsgSendVal(Context context) {
		return context.getSharedPreferences(Const.PERSONAL,
				Activity.MODE_PRIVATE).getBoolean(Const.MSGSEND, false);

	}

	/**
	 * 保存声音提醒的开关状态
	 * 
	 * @param context
	 * @param key
	 */
	public static void savaVoiceRemind(Context context, boolean key) {
		context.getSharedPreferences(Const.PERSONAL, Activity.MODE_PRIVATE)
				.edit().putBoolean(Const.VOICEREMIND, key).commit();
	}

	/**
	 * 读取声音提醒的开关状态
	 * 
	 * @param context
	 * @param key
	 */
	public static boolean getVoiceRemind(Context context) {
		return context.getSharedPreferences(Const.PERSONAL,
				Activity.MODE_PRIVATE).getBoolean(Const.VOICEREMIND, false);
	}

	/**
	 * 保存是否在无图模式下
	 * 
	 * @param context
	 * @param key
	 */
	public static void saveNoPaint(Context context, boolean key) {
		context.getSharedPreferences(Const.PERSONAL, Activity.MODE_PRIVATE)
				.edit().putBoolean(Const.NOPAINT, key).commit();
	}

	/**
	 * 读取是否在无图模式下
	 * 
	 * @param context
	 * @return
	 */
	public static boolean getNoPaint(Context context) {
		return context.getSharedPreferences(Const.PERSONAL,
				Activity.MODE_PRIVATE).getBoolean(Const.NOPAINT, false);
	}

	/**
	 * 
	 * @param context
	 * @param key
	 */
	public static void saveHelpState(Context context, boolean key) {
		context.getSharedPreferences("saveHelpState", Activity.MODE_PRIVATE)
				.edit().putBoolean(Const.NOPAINT, key).commit();
	}

	/**
	 * 
	 * @param context
	 * @return
	 */
	public static boolean getHelpState(Context context) {
		return context.getSharedPreferences("saveHelpState",
				Activity.MODE_PRIVATE).getBoolean(Const.NOPAINT, false);
	}

	/**
	 * 
	 * @param context
	 * @param key
	 */
	public static void saveServerState(Context context, String value) {
		context.getSharedPreferences("saveServerState", Activity.MODE_PRIVATE)
				.edit().putBoolean(value, true).commit();
	}

	/**
	 * 
	 * @param context
	 * @return
	 */
	public static boolean getServerState(Context context, String key) {
		return context.getSharedPreferences("saveServerState",
				Activity.MODE_PRIVATE).getBoolean(key, false);
	}

}
