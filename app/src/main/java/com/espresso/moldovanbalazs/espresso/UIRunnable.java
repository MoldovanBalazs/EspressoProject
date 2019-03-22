package com.espresso.moldovanbalazs.espresso;

import android.widget.TextView;

import org.w3c.dom.Text;

public class UIRunnable implements Runnable {

    private TextView textView;
    private String message;

    public UIRunnable() {
    }

    public UIRunnable(TextView textView, String message) {
        this.textView = textView;
        this.message = message;
    }

    public void setTextView(TextView tV) {
        this.textView = tV;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public void run() {
        this.textView.setText(this.message);
    }
}
