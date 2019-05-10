package com.espresso.moldovanbalazs.espresso;

import android.os.SystemClock;
import android.util.Log;
import android.widget.Chronometer;

import java.sql.Time;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

import static java.lang.Thread.sleep;

public class TimeCounter implements Runnable {

    double startTime = 0, startTimeAux = 0, milliSecondTime = 0,  updateTime = 0;
    static AtomicLong timeBuffer;
    public static int minutes, seconds;

    public static volatile AtomicBoolean onPause;


    private static TimeCounter timeCounter;

    private TimeCounter() {
        onPause = new AtomicBoolean(true);
        timeBuffer = new AtomicLong(0);
        pauseEnd = new AtomicLong(0);
    }

    public static TimeCounter getInstance() {
        if (timeCounter == null) {
            return new TimeCounter();
        }
        return timeCounter;
    }

    public void initTime() {
        startTime = SystemClock.uptimeMillis();
        startTimeAux = startTime;

    }

    boolean updateable = false;

    double pauseStart = 0;
    public static AtomicLong pauseEnd;

    @Override
    public void run() {
        while (true) {
            Log.d("TimeCounter", "while loop");
            if (!onPause.get()) {
                milliSecondTime = SystemClock.uptimeMillis() - startTime;
                updateTime = timeBuffer.get() + milliSecondTime;
                updateTime = milliSecondTime - (pauseEnd.get() - pauseStart);
                seconds = (int) (updateTime / 1000);
                minutes = seconds / 60;
                seconds %= 60;
                Log.d("TimeCounter", "working" + minutes + " " + seconds);
                updateable = true;
            } else {
                    if(updateable) {
                        timeBuffer.set((long) milliSecondTime);
                        pauseStart += SystemClock.uptimeMillis();
                        updateable = false;
                    }


                    startTimeAux += SystemClock.uptimeMillis() - milliSecondTime;
            }
            try {
                sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
