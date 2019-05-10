package com.espresso.moldovanbalazs.espresso;

import android.os.Environment;
import android.util.Log;

import java.io.File;

public class UpdateDataRunnable implements Runnable {

    private DataModel dataModel;
    File dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS);
    File file = new File(dir, "data.txt");


    public UpdateDataRunnable(DataModel dataModel) {
        this.dataModel = dataModel;
    }

    @Override
    public void run() {
        if (DataRetreivalActivity.isActive()) {
            DataRetreivalActivity.infrared1Field.setText(String.valueOf(dataModel.getInfrared1()));
            DataRetreivalActivity.infrared2Field.setText(String.valueOf(dataModel.getInfrared2()));
            DataRetreivalActivity.infrared3Field.setText(String.valueOf(dataModel.getInfrared3()));
            DataRetreivalActivity.infrared4Field.setText(String.valueOf(dataModel.getInfrared4()));
            DataRetreivalActivity.motorPWMField.setText(String.valueOf(dataModel.getMotorPWM()));
            DataRetreivalActivity.servoPWMField.setText(String.valueOf(dataModel.getServoPWM()));
            DataRetreivalActivity.batteryLevelField.setText(String.valueOf(dataModel.getBatteryLevel()));
            //DataRetreivalActivity.timeFromStartField.setText(String.valueOf(dataModel.getBatteryLevel()));
            DataRetreivalActivity.speedField.setText(String.valueOf(dataModel.getSpeed()));
            DataRetreivalActivity.commentField.setText(String.valueOf(dataModel.getComment()));

        }
        Log.d("Update Runnable", "reached here");
    }

}