package com.espresso.moldovanbalazs.espresso;

import android.os.Handler;
import android.util.Log;
import android.widget.TextView;

public class Callback implements ServerSwitchCallback {

    ServerThread serverThread;
    UIRunnable uiRunnable;
    TextView status;

    public Callback(ServerThread serverThread, UIRunnable uiRunnable, TextView status) {
        this.serverThread = serverThread;
        this.uiRunnable = uiRunnable;
        this.status = status;
    }

    @Override
    public void switchCallback(boolean state) {
        status.setText("REACHED");
        if(state ==  true) {
            serverThread = ServerThread.getInstance();
            uiRunnable.setTextView(status);
            uiRunnable.setMessage("ON");
            Log.e("SWITCH CALLBACK", "ON");
            new Handler().post(uiRunnable);
        } else {
            uiRunnable.setTextView(status);
            uiRunnable.setMessage("OFF");
            Log.e("SWITCH CALLBACK", "OFF");
            new Handler().post(uiRunnable);
        }
    }
}
