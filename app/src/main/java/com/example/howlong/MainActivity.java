package com.example.howlong;

import com.example.howlong.AsyncCallback.GetTimeCallback;
import com.example.howlong.asyncTasks.GetTime;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements GetTimeCallback{
    TextView showTime;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button getTimeButton = (Button) findViewById(R.id.getTimeButton);
        showTime = (TextView) findViewById(R.id.showTime) ;
        getTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                GetTime getTime =new GetTime();
                getTime.getTimeCallback=MainActivity.this;
                getTime.execute();


            }
        });
    }

    @Override
    public void processFinish(String stringObject) {
        showTime.setText(stringObject);
    }


}
