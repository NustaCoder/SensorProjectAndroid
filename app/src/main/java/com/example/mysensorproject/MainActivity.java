package com.example.mysensorproject;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;

import java.util.List;

public class MainActivity extends Activity implements SensorEventListener {

    TextView tv;
    SensorManager sam;
    float x=0,y=0,z=0;
    boolean cx=false,cy=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv=findViewById(R.id.tv);
        sam=(SensorManager)getSystemService(SENSOR_SERVICE);
        List<Sensor> lst=sam.getSensorList(Sensor.TYPE_ALL);
        String data="";
        for(Sensor sen:lst)
        {
            data+=sen.getName()+"\n";
        }
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        sam.registerListener(this,sam.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause()
    {
        super.onPause();
        sam.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        if(sensorEvent.sensor.getType()==Sensor.TYPE_ACCELEROMETER){
            float val[]=sensorEvent.values;
            float px=x;

            x=val[0];
            y=val[1];
            z=val[2];

            if(x<(px-10) || x>(px+10)) {
                if(cx) {
                    tv.setBackgroundColor(Color.RED);
                    cx = false;
                }
            else
                {
                    tv.setBackgroundColor(Color.GREEN);
                    cx = true;
                }
            }
        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}
