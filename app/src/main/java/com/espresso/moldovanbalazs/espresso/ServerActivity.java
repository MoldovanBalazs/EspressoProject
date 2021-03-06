package com.espresso.moldovanbalazs.espresso;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import java.io.IOException;

public class ServerActivity extends AppCompatActivity implements ServerSwitchCallback, ClientInterface{
    TextView serverStatusField, serverIpPortField, rcCarField, serverSwitchField;
    Switch serverSwitch;
    ServerThread serverThread;
    UIRunnable uiRunnable;

    float x1,x2,y1,y2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_server);

        uiRunnable = new UIRunnable();

        serverStatusField = (TextView) findViewById(R.id.serverStatusField);
        serverIpPortField = (TextView) findViewById(R.id.serverIpPortField);
        rcCarField = (TextView) findViewById(R.id.rcCarField);
        serverSwitchField = (TextView) findViewById(R.id.serverSwitchField);

        serverThread = ServerThread.getInstance();
        serverThread.setClientCallBack(this);

        initializeFields();

        Switch sw = (Switch) findViewById(R.id.serverSwitch);
        sw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    switchCallback(true);
                }
                else {
                    switchCallback(false);
                }
            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();

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
                    Intent i = new Intent(ServerActivity.this, ManualControlActivity.class);
                    startActivity(i);
                    overridePendingTransition(R.anim.right_in, R.anim.right_out);

                } else {
                    Intent i = new Intent(ServerActivity.this, DataRetreivalActivity.class);
                    startActivity(i);
                    overridePendingTransition(R.anim.left_in, R.anim.left_out);
                }
                break;
        }
        return false;
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    public void switchCallback(boolean state) {


        if(state ==  true) {

            if(serverThread.isInterrupted()) {
                serverThread.run();
            } else{
                serverThread.start();
            }



            //serverThread.setUiRunnable(uiRunnable);
            if(serverThread != null) {
                new Handler().post(new UIRunnable(serverStatusField, "ONLINE"));
                new Handler().post(new UIRunnable(serverIpPortField, serverThread.getServerData()));
                new Handler().post(new UIRunnable(rcCarField, serverThread.getClient()));
                new Handler().post(new UIRunnable(serverSwitchField, "stop server"));
            }

        } else {
            if(serverThread != null) {
                serverThread.interrupt();
            }
            new Handler().post(new UIRunnable(serverStatusField, "OFFLINE"));
            new Handler().post(new UIRunnable(serverIpPortField, "OFFLINE"));
            new Handler().post(new UIRunnable(rcCarField, "OFFLINE"));
            new Handler().post(new UIRunnable(serverSwitchField, "start server"));
        }
    }

    public void initializeFields() {
        new Handler().post(new UIRunnable(serverSwitchField, "start server"));
        new Handler().post(new UIRunnable(serverStatusField, "OFFLINE"));
        new Handler().post(new UIRunnable(serverIpPortField, "OFFLINE"));
        new Handler().post(new UIRunnable(rcCarField, "OFFLINE"));
    }

    @Override
    public void clientPresent(boolean isPresent) {
        if(isPresent) {
            Log.d("CALLBACK", "client present");
            new Handler().post(new UIRunnable(rcCarField, "ONLINE"));
            rcCarField.setText("ONLINE");
        } else {
            new Handler().post(new UIRunnable(rcCarField, "OFFLINE"));
            Log.d("CALLBACK", "client gone");
            rcCarField.setText("OFFLINE");
        }
    }
}

