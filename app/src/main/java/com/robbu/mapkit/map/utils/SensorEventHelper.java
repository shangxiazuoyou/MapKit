package com.robbu.mapkit.map.utils;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.view.Display;
import android.view.Surface;
import android.view.WindowManager;

import com.robbu.mapkit.map.common.ConfigConstants;
import com.robbu.mapkit.map.common.MapHelper;

public class SensorEventHelper implements SensorEventListener {

    private SensorManager mSensorManager;
    private Sensor mSensor;
    private Context mContext;
    private MapHelper.MyOrientationListener orientationListener;

    private long lastTime = 0;
    private final int TIME_SENSOR = 100;
    private float mAngle;

    private float lastX;

    public SensorEventHelper(Context context, MapHelper.MyOrientationListener orientationListener) {
        mContext = context;
        mSensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION);
        this.orientationListener = orientationListener;
    }

    public void registerSensorListener() {
        mSensorManager.registerListener(this, mSensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    public void unRegisterSensorListener() {
        mSensorManager.unregisterListener(this, mSensor);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (orientationListener != null && event != null) {
            if(ConfigConstants.MAP_TYPE == ConfigConstants.MAP_TYPE_BAIDU){
                if (Sensor.TYPE_ORIENTATION == event.sensor.getType()) {
                    // 这里我们可以得到数据，然后根据需要来处理
                    float x = event.values[SensorManager.DATA_X];
                    if (Math.abs(x - lastX) > 1.0) {
                        orientationListener.onOrientationChanged(x);
                    }
                    lastX = x;
                }
            }else if(ConfigConstants.MAP_TYPE == ConfigConstants.MAP_TYPE_GAODE){
                if (System.currentTimeMillis() - lastTime < TIME_SENSOR) {
                    return;
                }

                if (Sensor.TYPE_ORIENTATION == event.sensor.getType()) {
                    float x = event.values[0];
                    x += getScreenRotationOnPhone(mContext);
                    x %= 360.0F;
                    if (x > 180.0F)
                        x -= 360.0F;
                    else if (x < -180.0F)
                        x += 360.0F;

                    if (Math.abs(mAngle - x) < 3.0f) {
                        return;
                    }
                    mAngle = Float.isNaN(x) ? 0 : x;
                    orientationListener.onOrientationChanged(360 - mAngle);
                    lastTime = System.currentTimeMillis();
                }
            }
        }
    }

    public static int getScreenRotationOnPhone(Context context) {
        Display display = ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE))
                .getDefaultDisplay();

        if (display == null) {
            return 0;
        }

        switch (display.getRotation()) {
            case Surface.ROTATION_0:
                return 0;

            case Surface.ROTATION_90:
                return 90;

            case Surface.ROTATION_180:
                return 180;

            case Surface.ROTATION_270:
                return -90;
        }
        return 0;
    }
}
