package com.color.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import android.content.Context;
import android.os.Environment;
import android.os.StatFs;
import android.util.Log;

public class FileUtil {

	/**
	 * ��ݿ��ļ�����
	 * 
	 * @param context
	 */
	public static void copyDBFile(Context context, String dbName) {
		File destFile = new File(context.getFilesDir(), dbName);
		if (destFile.exists() && destFile.length() > 0) {
			Log.d("LOG", "exist");
			return;
		}
		try {
			InputStream is = context.getAssets().open(dbName);
			File file = copyFile(is, destFile.getAbsolutePath());
			if (file == null) {
				Log.d("LOG", "copy failed");
			} else {
				Log.d("LOG", "copy success");
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// �ļ�����
	public static File copyFile(InputStream is, String path) {
		try {
			File file = new File(path);
			FileOutputStream fos = new FileOutputStream(file);

			byte[] buffer = new byte[1024];
			int len = 0;

			while ((len = is.read(buffer)) != -1) {
				fos.write(buffer, 0, len);
			}
			fos.flush();
			fos.close();
			is.close();
			return file;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	public static long getAvailaleSize() {

		File path = Environment.getExternalStorageDirectory(); // ȡ��sdcard�ļ�·��

		StatFs stat = new StatFs(path.getPath());
		long blockSize = stat.getBlockSize();// ��ȡblock��SIZE
		long availableBlocks = stat.getAvailableBlocks();// ���е�Block������;
		return (availableBlocks * blockSize) / 1024 / 1024;// MIB��λ

	}
	/**
	 * д�ļ�
	 */
	public static void write2SDcard(String str, String name) {

		String sDStateString = android.os.Environment.getExternalStorageState();
		File myFile = null;

		if (sDStateString.equals(android.os.Environment.MEDIA_MOUNTED)) {

			try {
				File SDFile = android.os.Environment
						.getExternalStorageDirectory();

				File destDir = new File(SDFile.getAbsolutePath() + "/humos");

				if (!destDir.exists()) {// �ж�Ŀ¼�Ƿ���ڣ������ڴ���
					destDir.mkdir();// ����Ŀ¼
				}

				// ���ļ�
				myFile = new File(destDir.getAbsolutePath() + File.separator
						+ name);

				// �ж��ļ��Ƿ����,�������򴴽�
				if (!myFile.exists()) {
					myFile.createNewFile();// �����ļ�
				}
				// д��� ע��������������һ����д����ļ����ڶ�����ָ�Ǹ��ǻ���׷�ӣ�
				// Ĭ���Ǹ��ǵģ����ǲ�д�ڶ���������������Ϊtrue����˵�����ǣ����ں���׷�ӡ�
				FileOutputStream outputStream = new FileOutputStream(myFile,
						true);
				outputStream.write(str.getBytes());// д������
				outputStream.close();// �ر���

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

	}
	/**
	 * �ļ���С
	 */
	public static long getFileSizes(File f) {// ȡ���ļ���С
		long s = 0;
		if (f.exists()) {
			try {
				FileInputStream fis = null;
				fis = new FileInputStream(f);
				s = fis.available();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return 0;
			}
		} else {
			return 0;
		}
		return s;
	}
}
