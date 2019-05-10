package com.espresso.moldovanbalazs.espresso;

import java.sql.Time;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;

public class TimeThread extends Thread {

    public static AtomicLong getCounter() {
        return counter;
    }

    private static TimeThread timeThread;

    public static TimeThread getInstance() {
        if(timeThread == null) {
            timeThread = new TimeThread();
        }
        return timeThread;
    }

    static AtomicLong counter;
    AtomicLong buffer;
    long count;
    static AtomicBoolean counting = new AtomicBoolean(false);

    private TimeThread() {
        this.counter = new AtomicLong(0);
        this.buffer = new AtomicLong(0);
        count = 0;
    }

    @Override
    public void run() {
        while(true) {
            if(counting.get()) {
                count++;
                counter.set(count);
                try {
                    sleep(0, 900000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }
    }
}
