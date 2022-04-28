package ru.mirea.survillo.mireaproject;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class SensorsFragment extends Fragment implements SensorEventListener {

    private TextView lightSensorValue;
    private TextView accelerometerSensorValueX;
    private TextView accelerometerSensorValueY;
    private TextView accelerometerSensorValueZ;
    private TextView gyroscopeSensorXValue;
    private TextView gyroscopeSensorYValue;
    private TextView gyroscopeSensorZValue;
    private SensorManager sensorManager;
    private Sensor lightSensor;
    private Sensor accelerometerSensor;
    private Sensor gyroscopeSensor;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_sensors, container, false);
        lightSensorValue = view.findViewById(R.id.light_sensor);
        accelerometerSensorValueX = view.findViewById(R.id.acceler_x);
        accelerometerSensorValueY = view.findViewById(R.id.acceler_y);
        accelerometerSensorValueZ = view.findViewById(R.id.acceler_z);
        gyroscopeSensorXValue = view.findViewById(R.id.hyroscop_x);
        gyroscopeSensorYValue = view.findViewById(R.id.hyroscop_y);
        gyroscopeSensorZValue = view.findViewById(R.id.hyroscop_z);

        if (getActivity() != null){
            sensorManager = (SensorManager) getActivity()
                    .getSystemService(Context.SENSOR_SERVICE);

            lightSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
            accelerometerSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
            gyroscopeSensor = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        }
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        sensorManager.registerListener(this, accelerometerSensor,
                SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(this, gyroscopeSensor,
                SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(this, lightSensor,
                SensorManager.SENSOR_DELAY_NORMAL);
    }


    @Override
    public void onPause() {
        super.onPause();
        sensorManager.unregisterListener( this);
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        if (sensorEvent.sensor.getType() == Sensor.TYPE_ACCELEROMETER){
            String newValueX = sensorEvent.values[0] + " м/с^2";
            String newValueY = sensorEvent.values[1] + " м/с^2";
            String newValueZ = sensorEvent.values[2] + " м/с^2";
            accelerometerSensorValueX.setText(newValueX);
            accelerometerSensorValueY.setText(newValueY);
            accelerometerSensorValueZ.setText(newValueZ);

        } else if(sensorEvent.sensor.getType() == Sensor.TYPE_GYROSCOPE){
            String newValueX = sensorEvent.values[0] + " рад/с";
            String newValueY = sensorEvent.values[1] + " рад/с";
            String newValueZ = sensorEvent.values[2] + " рад/с";
            gyroscopeSensorXValue.setText(newValueX);
            gyroscopeSensorYValue.setText(newValueY);
            gyroscopeSensorZValue.setText(newValueZ);

        } else if(sensorEvent.sensor.getType() == Sensor.TYPE_LIGHT){
            String newValue = sensorEvent.values[0] + " лк";
            lightSensorValue.setText(newValue);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}