package com.espresso.moldovanbalazs.espresso;

public class DataModel {

    private int infrared3;
    private int infrared2;
    private int infrared1;
    private int infrared4;
    private int motorPWM;
    private int servoPWM;
    private int speed;

    private int batteryLevel;
    private String comment;

    public DataModel( int infrared1, int infrared2, int infrared3, int infrared4, int motorPWM, int servoPWM, int speed, int batteryLevel, String comment) {
        this.infrared3 = infrared3;
        this.infrared2 = infrared2;
        this.infrared1 = infrared1;
        this.motorPWM = motorPWM;
        this.servoPWM = servoPWM;
        this.speed = speed;
        this.infrared4 = infrared4;
        this.batteryLevel = batteryLevel;
        this.comment = comment;
    }

    public int getInfrared3() {
        return infrared3;
    }

    public int getInfrared2() {
        return infrared2;
    }

    public int getInfrared1() {
        return infrared1;
    }

    public int getInfrared4() {
        return infrared4;
    }

    public int getMotorPWM() {
        return motorPWM;
    }

    public int getServoPWM() {
        return servoPWM;
    }

    public int getSpeed() {
        return speed;
    }

    public int getBatteryLevel() {
        return batteryLevel;
    }

    public String getComment() {
        return comment;
    }
}
