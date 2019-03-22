package com.espresso.moldovanbalazs.espresso;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;

public class ManualControlActivity extends AppCompatActivity implements ManualControlView.JoyStickListener {

    float x1,x2,y1,y2;
    TextView joystickData;
    MessageSender messageSender;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manual_control);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ManualControlView manualControlView = new ManualControlView(this); // new joystick view
        addContentView(manualControlView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        joystickData = (TextView) findViewById(R.id.joyo);

        ServerThread serverThread = ServerThread.getInstance();

        if(serverThread.isAlive()){
            try {
                messageSender = new MessageSender(serverThread.getClientSocket());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
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
                if(x1 > x2) {
                    super.onBackPressed();

                    overridePendingTransition(R.anim.right_in, R.anim.right_out);
                }
                break;
        }
        return false;
    }

    @Override
    public void onJoyStickMoved(float xPercent, float yPercent, int id)  {
        //this.joystickData.setText(xPercent + "" + yPercent);
        if(ServerThread.getInstance().isAlive()) {
            int x, y;
            if(xPercent < 0) {
                x = (int) (51 + (-(50 * xPercent)));
            } else {
                x = (int) (50 * xPercent);
            }

            if(yPercent < 0) {
                y = (int) (51 + (-(50 * yPercent)));
            } else {
                y = (int) (50 * yPercent);
            }

            Log.d("COORD", x + " " + y);
            String toSend = String.valueOf(x * 256 + y);
            messageSender.setMessage(toSend);
            if(ServerThread.getInstance().getClientSocket() != null) {
                new Thread(messageSender).start();

            }
        }
        Log.d("JOYSTICK", xPercent + "" + yPercent);

    }

    @Override
    public void onBackPressed(){
        super.onBackPressed();
        overridePendingTransition(R.anim.left_in, R.anim.left_out);
    }

}
