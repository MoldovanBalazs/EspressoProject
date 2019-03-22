package com.espresso.moldovanbalazs.espresso;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Calendar;
import java.util.Date;

public class UpdateDataRunnable implements Runnable {

    private DataModel dataModel;
    File dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS);
    File file = new File(dir, "data.txt");


    public UpdateDataRunnable(DataModel dataModel) {
        this.dataModel = dataModel;
    }

    @Override
    public void run() {
        if(DataRetreivalActivity.isActive()) {
            DataRetreivalActivity.sonarLeftField.setText(String.valueOf(dataModel.getSonarLeft()));
            DataRetreivalActivity.sonarRightField.setText(String.valueOf(dataModel.getSonarRight()));
            DataRetreivalActivity.sonarMiddleField.setText(String.valueOf(dataModel.getSonarMiddle()));
            DataRetreivalActivity.motorPWMField.setText(String.valueOf(dataModel.getMotorPWM()));
            DataRetreivalActivity.servoPWMField.setText(String.valueOf(dataModel.getServoPWM()));
            DataRetreivalActivity.batteryLevelField.setText(String.valueOf(dataModel.getBatteryLevel()));
            DataRetreivalActivity.timeFromStartField.setText(String.valueOf(dataModel.getBatteryLevel()));
            DataRetreivalActivity.distanceField.setText(String.valueOf(dataModel.getDistance()));
            DataRetreivalActivity.speedField.setText(String.valueOf(dataModel.getSpeed()));
            DataRetreivalActivity.commentField.setText(String.valueOf(dataModel.getComment()));

        }
        Log.d("Update Runnable","reached here");
    }

}