package com.color.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.net.Uri;
import android.util.Base64;
public class UtilString {
	private final static Pattern emailer = Pattern
			.compile("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*");

	private final static ThreadLocal<SimpleDateFormat> dateFormater = new ThreadLocal<SimpleDateFormat>() {
		@Override
		protected SimpleDateFormat initialValue() {
			return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		}
	};

	private final static ThreadLocal<SimpleDateFormat> dateFormater2 = new ThreadLocal<SimpleDateFormat>() {
		@Override
		protected SimpleDateFormat initialValue() {
			return new SimpleDateFormat("yyyy-MM-dd");
		}
	};

	public static String removeNonDigits(String paramString) {
		int i = paramString.length();
		StringBuilder localStringBuilder = new StringBuilder(i);
		for (int j = 0; j < i; ++j) {
			char c = paramString.charAt(j);
			if ((((c < '0') || (c > '9'))) && (c != '*') && (c != '#')
					&& (c != '+'))
				continue;
			localStringBuilder.append(c);
		}
		return localStringBuilder.toString();
	}

	public static SimpleDateFormat date_formate5 = new SimpleDateFormat(
			"yyyy-MM-dd");

	public static String isDate(Date date) {
		try {
			return date_formate5.format(date);
		} catch (Exception e) {
			return "";
		}

	}

	/**
	 * ���ַ�תλ��������
	 * 
	 * @param sdate
	 * @return
	 */
	public static Date toDate(String sdate) {
		try {
			return dateFormater2.get().parse(sdate);
		} catch (ParseException e) {
			return null;
		}
	}

	/**
	 * ���Ѻõķ�ʽ��ʾʱ��
	 * 
	 * @param sdate
	 * @return
	 */
	public static String friendly_time(String sdate) {
		Date time = toDate(sdate);
		if (time == null) {
			return "Unknown";
		}
		String ftime = "";
		Calendar cal = Calendar.getInstance();

		// �ж��Ƿ���ͬһ��
		String curDate = dateFormater2.get().format(cal.getTime());
		String paramDate = dateFormater2.get().format(time);
		if (curDate.equals(paramDate)) {
			int hour = (int) ((cal.getTimeInMillis() - time.getTime()) / 3600000);
			if (hour == 0)
				ftime = Math.max(
						(cal.getTimeInMillis() - time.getTime()) / 60000, 1)
						+ "����ǰ";
			else
				ftime = hour + "Сʱǰ";
			return ftime;
		}

		long lt = time.getTime() / 86400000;
		long ct = cal.getTimeInMillis() / 86400000;
		int days = (int) (ct - lt);
		if (days == 0) {
			int hour = (int) ((cal.getTimeInMillis() - time.getTime()) / 3600000);
			if (hour == 0)
				ftime = Math.max(
						(cal.getTimeInMillis() - time.getTime()) / 60000, 1)
						+ "����ǰ";
			else
				ftime = hour + "Сʱǰ";
		} else if (days == 1) {
			ftime = "����";
		} else if (days == 2) {
			ftime = "ǰ��";
		} else if (days > 2 && days <= 10) {
			ftime = days + "��ǰ";
		} else if (days > 10) {
			ftime = dateFormater2.get().format(time);
		}
		return ftime;
	}

	/**
	 * �жϸ��ַ�ʱ���Ƿ�Ϊ����
	 * 
	 * @param sdate
	 * @return boolean
	 */
	public static boolean isToday(String sdate) {
		boolean b = false;
		Date time = toDate(sdate);
		Date today = new Date();
		if (time != null) {
			String nowDate = dateFormater2.get().format(today);
			String timeDate = dateFormater2.get().format(time);
			if (nowDate.equals(timeDate)) {
				b = true;

			}
		}
		return b;
	}

	/**
	 * �ж��ǲ�������
	 * 
	 * @param sdate
	 * @return
	 */
	public static boolean isTomorrow(String sdate) {
		boolean b = false;
		Date time = toDate(sdate);
		Date date = new Date();// ȡʱ��
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		calendar.add(Calendar.DATE, 1);// �������������һ��.���������,������ǰ�ƶ�
		date = calendar.getTime(); // ���ʱ��������������һ��Ľ��
		if (time != null) {
			String tomorrowDate = dateFormater2.get().format(date);
			String timeDate = dateFormater2.get().format(time);
			if (tomorrowDate.equals(timeDate)) {
				b = true;

			}
		}
		return b;
	}

	public static String toFormatDateTime(long time)
	{
		SimpleDateFormat dtF = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date curDt = new Date(time);
		return dtF.format(curDt);
	}
	/**
	 * �ж��ַ��ܲ���ת����ʱ��
	 * 
	 * @param time
	 * @return
	 */
	public static boolean isDateTime(String time) {
		boolean b = false;
		try {
			Date date = null;
			date = dateFormater2.get().parse(time);
			b = true;
		} catch (Exception e) {
			b = false;
			e.printStackTrace();
		}
		return b;
	}

	/**
	 * �жϸ��ַ��Ƿ�հ״��� �հ״���ָ�ɿո��Ʊ��س����з���ɵ��ַ� �������ַ�Ϊnull����ַ�����true
	 * 
	 * @param input
	 * @return boolean
	 */
	public static boolean isEmpty(String input) {
		if (input == null || "".equals(input))
			return true;

		for (int i = 0; i < input.length(); i++) {
			char c = input.charAt(i);
			if (c != ' ' && c != '\t' && c != '\r' && c != '\n') {
				return false;
			}
		}
		return true;
	}

	/**
	 * �ܷ�ת��������
	 * 
	 * @param input
	 * @return
	 */
	public static boolean parsetoInt(String input) {
		try {
			Integer.parseInt(input);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * �ǲ��Ǵ���0
	 * 
	 * @param input
	 * @return
	 */
	public static boolean isbigthanzero(String input) {
		if (input != null && input.length() > 0 && parsetoInt(input)) {
			int num = Integer.parseInt(input);
			if (num > 0) {
				return true;
			} else {

				return false;
			}
		} else {
			return false;
		}

	}

	/**
	 * �ж��ǲ���һ���Ϸ��ĵ����ʼ���ַ
	 * 
	 * @param email
	 * @return
	 */
	public static boolean isEmail(String email) {
		if (email == null || email.trim().length() == 0)
			return false;
		return emailer.matcher(email).matches();
	}

	/**
	 * �ַ�ת����
	 * 
	 * @param str
	 * @param defValue
	 * @return
	 */
	public static int toInt(String str, int defValue) {
		try {
			return Integer.parseInt(str);
		} catch (Exception e) {
		}
		return defValue;
	}

	/**
	 * ����ת����
	 * 
	 * @param obj
	 * @return ת���쳣���� 0
	 */
	public static int toInt(Object obj) {
		if (obj == null)
			return 0;
		return toInt(obj.toString(), 0);
	}

	/**
	 * ����ת����
	 * 
	 * @param obj
	 * @return ת���쳣���� 0
	 */
	public static long toLong(String obj) {
		try {
			return Long.parseLong(obj);
		} catch (Exception e) {
		}
		return 0;
	}

	/**
	 * �ַ�ת����ֵ
	 * 
	 * @param b
	 * @return ת���쳣���� false
	 */
	public static boolean toBool(String b) {
		try {
			return Boolean.parseBoolean(b);
		} catch (Exception e) {
		}
		return false;
	}

	public static boolean isPhoneNumberValid(String phoneNumber) {
		   boolean isValid = false;
		   /*
		    * �ɽ��ܵĵ绰��ʽ�У�
		    */
		   String expression = "^\\(?(\\d{3})\\)?[- ]?(\\d{3})[- ]?(\\d{5})$";
		   /*
		    * �ɽ��ܵĵ绰��ʽ�У�
		    */
		   String expression2 = "^\\(?(\\d{3})\\)?[- ]?(\\d{4})[- ]?(\\d{4})$";
		   /*
		    * �ɽ��ܵĵ绰��ʽ�У�
		    */
		   String expression3 = "^\\(?(\\d)$";
		   CharSequence inputStr = phoneNumber;
		   Pattern pattern = Pattern.compile(expression);
		   Matcher matcher = pattern.matcher(inputStr);
		   
		   Pattern pattern2 = Pattern.compile(expression2);
		   Matcher matcher2 = pattern2.matcher(inputStr);
		   
		   Pattern pattern3 = Pattern.compile(expression3);
		   Matcher matcher3 = pattern3.matcher(inputStr);
		   if(matcher.matches() || matcher2.matches() || matcher3.matches()) {
			   isValid = true;
		   }
		   return isValid;
	   }
	/**
	 * ��֤�ֻ��
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isCellphone(String str) {
		Pattern p = Pattern
				.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");
		Matcher m = p.matcher(str);
		System.out.println(m.matches() + "---");
		return m.matches();
	}

	public static String toDateString(long time) {
		SimpleDateFormat dtF = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date curDt = new Date(time);
		return dtF.format(curDt);
	}

	/**
	 * �ַ�ǿ��ж�
	 * 
	 * @param value
	 * @return
	 */
	public static String isStrNull(Object value) {
		if (value != null && !value.equals("null") && !"".equals(value)) {
			return value.toString();
		}
		return "";
	}

	public static long copyFile(File f1, File f2) throws Exception {
		long time = new Date().getTime();
		int length = 2097152;
		FileInputStream in = new FileInputStream(f1);
		FileOutputStream out = new FileOutputStream(f2);
		byte[] buffer = new byte[length];
		while (true) {
			int ins = in.read(buffer);
			if (ins == -1) {
				in.close();
				out.flush();
				out.close();
				return new Date().getTime() - time;
			} else
				out.write(buffer, 0, ins);
		}
	}

	public static Bitmap getBitmapByUri(Context ctx, String uri) {
		try {
			Uri u = Uri.parse(uri);
			InputStream imageStream = ctx.getContentResolver().openInputStream(
					u);
			//long size = imageStream.available();
			BitmapFactory.Options ops = new BitmapFactory.Options();
			Rect rect = new Rect(0, 0, 500, 500);
			ops.inSampleSize = 2;

			return BitmapFactory.decodeStream(imageStream, rect, ops);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static Bitmap getLoacalBitmap(String url) {

		try {
			FileInputStream fis = new FileInputStream(url);
			return BitmapFactory.decodeStream(fis); 

		} catch (FileNotFoundException e) {
			return null;
		}
	}

	/**
	 * ��ȡͼƬ��
	 * 
	 * @param uri
	 *            ͼƬ��ַ
	 * 
	 * @return
	 * @throws MalformedURLException
	 */
	public static InputStream GetImageByUrl(String uri)
			throws MalformedURLException {
		URL url = new URL(uri);
		URLConnection conn;
		InputStream is;
		try {
			conn = url.openConnection();
			conn.connect();
			is = conn.getInputStream();
			// int ilen = conn.getContentLength();
			// ���������·���

			// is=(InputStream)url.getContent();
			return is;
		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * ��ȡBitmap
	 * 
	 * 
	 * @param uri
	 *            ͼƬ��ַ
	 * @return
	 */
	static Bitmap bitmap;
	public static Bitmap GetBitmapByUrl(String uri) {

	
		InputStream is;
		try {
			if(bitmap!=null&&!bitmap.isRecycled()){
				bitmap=null;
			}
			is = GetImageByUrl(uri);

			bitmap = BitmapFactory.decodeStream(is);
			is.close();

			return bitmap;

		} catch (MalformedURLException e) {
			e.printStackTrace();

		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * 
	 * compare_date(ʱ��Ƚ�)
	 * 
	 * @param @return �趨�ļ�
	 * @Exception �쳣����
	 * @since CodingExample��Ver(���뷶��鿴) 1.1
	 */
	public static Boolean compare_date(String DATE1, String DATE2) {

		Boolean result = false;

		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		try {
			Date dt1 = df.parse(DATE1);
			Date dt2 = df.parse(DATE2);
			if (dt1.getTime() > dt2.getTime()) {
				result = true;
			} else if (dt1.getTime() < dt2.getTime()) {
				result = false;
			} else {
				result = false;
			}
		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return result;
	}

	public static Boolean compare_date(String DATE1) {
		Boolean result = false;

		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		try {
			Date dt1 = df.parse(DATE1);
			Date dt2 = new Date();
			if (dt1.getTime() > dt2.getTime()) {
				result = true;
			} else if (dt1.getTime() < dt2.getTime()) {
				result = false;
			} else {
				result = true;
			}
		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return result;
	}

	public static String replaceBlank(String str) {
		String dest = "";
		if (str != null) {
			Pattern p = Pattern.compile("\\s*|\t|\r|\n");
			Matcher m = p.matcher(str);
			dest = m.replaceAll("");
		}
		return dest;
	}

	/**
	 * bitmapתΪbase64
	 * 
	 * @param bitmap
	 * @return
	 */
	public static String bitmapToBase64(Bitmap bitmap) {

		String result = "";
		ByteArrayOutputStream baos = null;
		try {
			if (bitmap != null) {
				baos = new ByteArrayOutputStream();
				bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);

				baos.flush();
				baos.close();

				byte[] bitmapBytes = baos.toByteArray();
				result = Base64.encodeToString(bitmapBytes, Base64.DEFAULT);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (baos != null) {
					baos.flush();
					baos.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	/**
	 * base64תΪbitmap
	 * 
	 * @param base64Data
	 * @return
	 */
	public static Bitmap base64ToBitmap(String base64Data) {
		byte[] bytes = Base64.decode(base64Data, Base64.DEFAULT);
		return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
	}

	static final String HEXES = "0123456789ABCDEF";

	public static String getHex(byte[] raw) {
		if (raw == null) {
			return null;
		}
		final StringBuilder hex = new StringBuilder(2 * raw.length);
		for (final byte b : raw) {
			hex.append(HEXES.charAt((b & 0xF0) >> 4)).append(
					HEXES.charAt((b & 0x0F)));
		}
		return hex.toString();
	}
}
