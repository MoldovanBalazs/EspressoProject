package com.espresso.moldovanbalazs.espresso;

public class DataModel {

    private int sonarRight;
    private int sonarMiddle;
    private int sonarLeft;
    private int motorPWM;
    private int servoPWM;
    private int speed;
    private int distance;
    private int batteryLevel;
    private String comment;


    public DataModel() {
    }

    public DataModel(int sonarRight, int sonarMiddle, int sonarLeft, int motorPWM, int servoPWM, int speed, int distance, int batteryLevel, String comment) {
        this.sonarRight = sonarRight;
        this.sonarMiddle = sonarMiddle;
        this.sonarLeft = sonarLeft;
        this.motorPWM = motorPWM;
        this.servoPWM = servoPWM;
        this.speed = speed;
        this.distance = distance;
        this.batteryLevel = batteryLevel;
        this.comment = comment;
    }

    public int getSonarRight() {
        return sonarRight;
    }

    public void setSonarRight(int sonarRight) {
        this.sonarRight = sonarRight;
    }

    public int getSonarMiddle() {
        return sonarMiddle;
    }

    public void setSonarMiddle(int sonarMiddle) {
        this.sonarMiddle = sonarMiddle;
    }

    public int getSonarLeft() {
        return sonarLeft;
    }

    public void setSonarLeft(int sonarLeft) {
        this.sonarLeft = sonarLeft;
    }

    public int getMotorPWM() {
        return motorPWM;
    }

    public void setMotorPWM(int motorPWM) {
        this.motorPWM = motorPWM;
    }

    public int getServoPWM() {
        return servoPWM;
    }

    public void setServoPWM(int servoPWM) {
        this.servoPWM = servoPWM;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public int getBatteryLevel() {
        return batteryLevel;
    }

    public void setBatteryLevel(int batteryLevel) {
        this.batteryLevel = batteryLevel;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public String  toString() {
        return "DataModel{" +
                "sonarRight=" + sonarRight +
                ", sonarMiddle=" + sonarMiddle +
                ", sonarLeft=" + sonarLeft +
                ", sonarPWM=" + motorPWM +
                ", servoPWM=" + servoPWM +
                ", speed=" + speed +
                ", distance=" + distance +
                ", batteryLevel=" + batteryLevel +
                ", comment='" + comment + '\'' +
                '}';
    }
}
