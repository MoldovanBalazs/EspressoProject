package com.espresso.moldovanbalazs.espresso;

import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;

import java.io.IOException;
import java.util.Random;
import java.util.Timer;

public class DataRetreivalActivity extends AppCompatActivity {

    float x1, x2, y1, y2;

    public static final String CAR_MODE = "CarMode";
    public static final String LED_MODE = "LedMode";

    private static boolean active = false;
    int minutes, seconds, hours;
    Button button;
    Button ledButton;

    static TextView infrared1Field;
    static TextView infrared3Field;
    static TextView infrared2Field;
    static TextView motorPWMField;
    static TextView servoPWMField;
    static TextView batteryLevelField;
    static TextView timeFromStartField;
    static TextView infrared4Field;
    static TextView speedField;
    static TextView commentField;
    ScrollView scrollView;

    Handler timerHandler = new Handler();
    Runnable timerRunnable = new Runnable() {
        @Override
        public void run() {
            seconds = (int) TimeThread.counter.get() / 1000;
            minutes = seconds / 60;
            hours = minutes / 60;
            seconds = seconds % 60;
            timeFromStartField.setText(hours + ":" + minutes + ":" + seconds);
            timerHandler.postDelayed(this, 100);

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_retreival);
        timerHandler.postDelayed(timerRunnable, 0);

        scrollView = findViewById(R.id.scroll);
        infrared1Field = findViewById(R.id.infrared1Field);
        infrared3Field = findViewById(R.id.infrared3Field);
        infrared2Field = findViewById(R.id.infrared2Field);
        motorPWMField = findViewById(R.id.motorPWMField);
        servoPWMField = findViewById(R.id.servoPWMField);
        batteryLevelField = findViewById(R.id.batteryLevelField);
        timeFromStartField = findViewById(R.id.timeFromStartField);
        infrared4Field = findViewById(R.id.infrared4Field);
        speedField = findViewById(R.id.speedField);
        commentField = findViewById(R.id.commentField);

        ServerThread serverThread = ServerThread.getInstance();
        MessageSender messageSender = null;
        if (serverThread.isAlive()) {
            if (serverThread.getClientSocket() != null && serverThread.getClientSocket().isConnected()) {
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
            if (messageSender != null) {
                messageSender.setMessage(String.valueOf(28000));
                new Thread(messageSender).start();
            }
        }

        final MessageSender finalMessageSender = messageSender;
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final int status = (int) v.getTag();
                if (status == 1) {
                    button.setText("Manual");
                    v.setTag(0);
                    finalMessageSender.setMessage(String.valueOf(28000));
                    new Thread(finalMessageSender).start();

                }
                if (status == 0) {
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
        SharedPreferences ledPrefs = getSharedPreferences(LED_MODE, MODE_PRIVATE);
        int ledButtonTag = ledPrefs.getInt("mode", 0);
        if (ledButtonTag == 1) {
            ledButton.setTag(1);
            ledButton.setText("Led2");
        } else {
            ledButton.setTag(0);
            ledButton.setText("Led1");
            if (messageSender != null) {
                messageSender.setMessage(String.valueOf(29000));
                new Thread(messageSender).start();
            }

        }
        if(!TimeThread.getInstance().isAlive()) {
            TimeThread.getInstance().start();
        }


        ledButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final int ledStatus = (int) v.getTag();

                if (ledStatus == 1) {
                    ledButton.setText("Led1");
                    v.setTag(0);
                    //finalMessageSender.setMessage(String.valueOf(29000));
                    //new Thread(finalMessageSender).start();
                    TimeThread.counting.set(false);


                }
                if (ledStatus == 0) {
                    ledButton.setText("Led2");
                    v.setTag(1);
                    //finalMessageSender.setMessage(String.valueOf(29001));
                    //new Thread(finalMessageSender).start();
                    TimeThread.counting.set(true);
                }
                SharedPreferences.Editor editor = getSharedPreferences(LED_MODE, MODE_PRIVATE).edit();
                editor.putInt("mode", (int) v.getTag());
                editor.apply();
            }
        });

        /*new Thread(new Runnable() {
            @Override
            public void run() {
                while(true) {
                    DataModel data = new DataModel(new Random().nextInt(), new Random().nextInt(), new Random().nextInt(), new Random().nextInt(), new Random().nextInt(), new Random().nextInt(), new Random().nextInt(), new Random().nextInt(),"");
                    new Thread(new UpdateDataRunnable(data)).start();
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();*/
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
        switch (touchEvent.getAction()) {
            case MotionEvent.ACTION_DOWN:
                x1 = touchEvent.getX();
                y1 = touchEvent.getY();
                break;
            case MotionEvent.ACTION_UP:
                x2 = touchEvent.getX();
                y2 = touchEvent.getY();
                if (x1 < x2) {
                    super.onBackPressed();
                    overridePendingTransition(R.anim.right_in, R.anim.right_out);
                }
                break;
        }
        return false;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.right_in, R.anim.right_out);
    }

}
