package com.color.util;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

/**
 * @author renxinwei
 * 
 * @date 2013-1-17 ����09:19:58
 */
public class SensorManagerHelper implements SensorEventListener {

	// �ٶ���ֵ����ҡ���ٶȴﵽ��ֵ���������
	private static final int SPEED_SHRESHOLD = 5000;
	// ���μ���ʱ����
	private static final int UPTATE_INTERVAL_TIME = 50;
	// ������������
	private SensorManager sensorManager;
	// ������
	private Sensor sensor;
	// ������Ӧ������
	private OnShakeListener onShakeListener;
	// �����Ķ���context
	private Context context;
	// �ֻ���һ��λ��ʱ������Ӧ���
	private float lastX;
	private float lastY;
	private float lastZ;
	// �ϴμ��ʱ��
	private long lastUpdateTime;
	// ������
	public SensorManagerHelper(Context context) {
		// ��ü������
		this.context = context;
		start();
	}

	// ��ʼ
	public void start() {
		// ��ô�����������
		sensorManager = (SensorManager) context
				.getSystemService(Context.SENSOR_SERVICE);
		if (sensorManager != null) {
			// �������������
			sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
		}
		// ע��
		if (sensor != null) {
			sensorManager.registerListener(this, sensor,
					SensorManager.SENSOR_DELAY_GAME);
		}
	}

	// ֹͣ���
	public void stop() {
		sensorManager.unregisterListener(this);
	}

	// ҡ�μ���ӿ�
	public interface OnShakeListener {
		public void onShake();
	}

	// ����������Ӧ������
	public void setOnShakeListener(OnShakeListener listener) {
		onShakeListener = listener;
	}

	/*
	 * (non-Javadoc)
	 * android.hardware.SensorEventListener#onAccuracyChanged(android.hardware
	 * .Sensor, int)
	 */
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		// TODO Auto-generated method stub
	}

	/*
	 * ������Ӧ����Ӧ��ñ仯���
	 * android.hardware.SensorEventListener#onSensorChanged(android.hardware
	 * .SensorEvent)
	 */
	public void onSensorChanged(SensorEvent event) {
		// TODO Auto-generated method stub
		// ���ڼ��ʱ��
		long currentUpdateTime = System.currentTimeMillis();
		// ���μ���ʱ����
		long timeInterval = currentUpdateTime - lastUpdateTime;
		// �ж��Ƿ�ﵽ�˼��ʱ����
		if (timeInterval < UPTATE_INTERVAL_TIME) return;
		// ���ڵ�ʱ����lastʱ��
		lastUpdateTime = currentUpdateTime;
		// ���x,y,z���
		float x = event.values[0];
		float y = event.values[1];
		float z = event.values[2];
		// ���x,y,z�ı仯ֵ
		float deltaX = x - lastX;
		float deltaY = y - lastY;
		float deltaZ = z - lastZ;
		// �����ڵ������last���
		lastX = x;
		lastY = y;
		lastZ = z;
		double speed = Math.sqrt(deltaX * deltaX + deltaY * deltaY + deltaZ
				* deltaZ)
				/ timeInterval * 10000;
		// �ﵽ�ٶȷ�ֵ��������ʾ
		if (speed >= SPEED_SHRESHOLD)
			onShakeListener.onShake();
	}
}
