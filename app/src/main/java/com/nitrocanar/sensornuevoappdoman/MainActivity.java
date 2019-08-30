package com.nitrocanar.sensornuevoappdoman;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.graphics.ImageFormat;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {
    SensorManager sensorManager;
    Sensor sensor;
    SensorEventListener sensorEventListener;
    int whip = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        if (sensor == null) finish();

        sensorEventListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent sensorEvent) {
                float z = sensorEvent.values[2];
                System.out.println("Valor giro en Y: "+z);
                if (z > -9 &&  z <=0 && whip == 0){
                    whip++;
                    getWindow().getDecorView().setBackgroundColor(Color.BLUE);

                }else if (z >= 2 && z < 9 && whip == 1){
                    whip++;
                    getWindow().getDecorView().setBackgroundColor(Color.RED);
                }

                if (whip == 2){
                    sound();
                    whip = 0;
                }
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int i) {

            }
        };

    }
    private void sound(){
        MediaPlayer mediaPlayer = MediaPlayer.create(this,R.raw.latigo);
        mediaPlayer.start();
    }

    private void start(){
        sensorManager.registerListener(sensorEventListener,sensor,SensorManager.SENSOR_DELAY_FASTEST);
    }

    private void stop(){
        sensorManager.unregisterListener(sensorEventListener);
    }

    @Override
    protected void onPause() {
        stop();
        super.onPause();
    }

    @Override
    protected void onResume() {
        start();
        super.onResume();
    }
}
