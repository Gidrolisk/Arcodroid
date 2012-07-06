package ru.fw_tm;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;

/**
 * @author : Ragnarok
 * @date : 05.07.12  12:16
 */
public class Accelerometer implements SensorEventListener {
    private float x = 0;

    @Override
    public void onSensorChanged(SensorEvent event) {
        x = event.values[0];
    }

    public float getX() {
        return x;
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // Do nothing
    }
}
