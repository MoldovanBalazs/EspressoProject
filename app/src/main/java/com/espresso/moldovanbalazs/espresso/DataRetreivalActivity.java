package com.espresso.moldovanbalazs.espresso;

import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;

import java.io.IOException;

public class DataRetreivalActivity extends AppCompatActivity  {

    float x1,x2,y1,y2;

    public static final String CAR_MODE = "CarMode";

    private static boolean active = false;

    Button button;

    static TextView sonarLeftField;
    static TextView sonarRightField;
    static TextView sonarMiddleField;
    static TextView motorPWMField;
    static TextView servoPWMField;
    static TextView batteryLevelField;
    static TextView timeFromStartField;
    static TextView distanceField;
    static TextView speedField;
    static TextView commentField;
    ScrollView scrollView;
    Button ledButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_retreival);

        scrollView = findViewById(R.id.scroll);
        sonarLeftField = findViewById(R.id.sonarLeftField);
        sonarRightField = findViewById(R.id.sonarRightField);
        sonarMiddleField = findViewById(R.id.sonarMiddleField);
        motorPWMField = findViewById(R.id.motorPWMField);
        servoPWMField = findViewById(R.id.servoPWMField);
        batteryLevelField = findViewById(R.id.batteryLevelField);
        timeFromStartField = findViewById(R.id.timeFromStartField);
        distanceField = findViewById(R.id.distanceField);
        speedField = findViewById(R.id.speedField);
        commentField = findViewById(R.id.commentField);

        ServerThread serverThread = ServerThread.getInstance();
        MessageSender messageSender = null;
        if(serverThread.isAlive()) {
            if(serverThread.getClientSocket() != null && serverThread.getClientSocket().isConnected()) {
                try {
                    messageSender = new MessageSender(ServerThread.getInstance().getClientSocket());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }

        button = findViewById(R.id.button);
        SharedPreferences prefs = getSharedPreferences(CAR_MODE, MODE_PRIVATE);
        int buttonTag = prefs.getInt("mode", 0);
        if (buttonTag == 1) {


            button.setTag(1);
            button.setText("Auto");
        } else {
            button.setTag(0);
            button.setText("Manual");
            if(messageSender != null) {
                messageSender.setMessage(String.valueOf(28000));
                new Thread(messageSender).start();
            }

        }


        final MessageSender finalMessageSender = messageSender;
        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                final int status = (int) v.getTag();
                if(status == 1) {
                    button.setText("Manual");
                    v.setTag(0);
                    finalMessageSender.setMessage(String.valueOf(28000));
                    new Thread(finalMessageSender).start();

                }
                if(status == 0) {
                    button.setText("Auto");
                    v.setTag(1);
                    finalMessageSender.setMessage(String.valueOf(28001));
                    new Thread(finalMessageSender).start();

                }
                SharedPreferences.Editor editor = getSharedPreferences(CAR_MODE, MODE_PRIVATE).edit();
                editor.putInt("mode", (int) v.getTag());
                editor.apply();
            }
        });

        ledButton = findViewById(R.id.buttonLed);
        ledButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finalMessageSender.setMessage(String.valueOf(29000));
                new Thread(finalMessageSender).start();

            }
        });

    }

    @Override
    public void onStart() {
        super.onStart();
        active = true;
    }

    @Override
    public void onStop() {
        super.onStop();
        active = false;
    }

    public static boolean isActive() {
        return active;
    }

    public boolean onTouchEvent(MotionEvent touchEvent) {
        switch(touchEvent.getAction()) {
            case MotionEvent.ACTION_DOWN:
                x1 = touchEvent.getX();
                y1 = touchEvent.getY();
                break;
            case MotionEvent.ACTION_UP:
                x2 = touchEvent.getX();
                y2 = touchEvent.getY();
                if(x1 < x2) {
                    super.onBackPressed();
                    overridePendingTransition(R.anim.right_in, R.anim.right_out);
                }
                break;
        }
        return false;
    }

    @Override
    public void onBackPressed(){
        super.onBackPressed();
        overridePendingTransition(R.anim.right_in, R.anim.right_out);
    }

}
