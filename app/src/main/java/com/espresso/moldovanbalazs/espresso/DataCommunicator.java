package com.espresso.moldovanbalazs.espresso;

public class DataCommunicator {

    private static int sonarRight = 0;
    private static int sonarMiddle = 0;
    private static int sonarLeft = 0;
    private static int motorPWM = 0;
    private static int servoPWM = 0;
    private static int speed = 0;
    private static int distance = 0;
    private static int batteryLevel = 0;
    private static String comment = "empty";

    public static int getSonarRight() {
        return sonarRight;
    }

    public static int getSonarMiddle() {
        return sonarMiddle;
    }

    public static int getSonarLeft() {
        return sonarLeft;
    }

    public static int getMotorPWM() {
        return motorPWM;
    }

    public static int getServoPWM() {
        return servoPWM;
    }

    public static int getSpeed() {
        return speed;
    }

    public static int getDistance() {
        return distance;
    }

    public static int getBatteryLevel() {
        return batteryLevel;
    }

    public static String getComment() {
        return comment;
    }

    public static void setDataFields (DataModel dataModel) {
        sonarRight = dataModel.getInfrared3();
        sonarMiddle = dataModel.getInfrared2();
        sonarLeft = dataModel.getInfrared1();
        motorPWM = dataModel.getMotorPWM();
        servoPWM = dataModel.getServoPWM();
        speed = dataModel.getSpeed();
        distance = dataModel.getInfrared4();
        batteryLevel = dataModel.getBatteryLevel();
        comment = dataModel.getComment();
    }

    public static DataModel getDataFields () {
        return new DataModel(sonarRight, sonarMiddle, sonarLeft, distance,motorPWM, servoPWM, speed,  batteryLevel, comment);
    }

}
