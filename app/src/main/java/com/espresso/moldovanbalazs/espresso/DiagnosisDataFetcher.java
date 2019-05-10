package com.espresso.moldovanbalazs.espresso;

import java.io.ByteArrayOutputStream;
import java.nio.ByteBuffer;

public class DiagnosisDataFetcher {

    public static DataModel decodeData(byte[] data) {


        ByteBuffer byteBuffer = ByteBuffer.wrap(data);
        int infrared1 = byteBuffer.getShort();
        int infrared2 = byteBuffer.getShort();
        int infrared3 = byteBuffer.getShort();
        int infrared4 = byteBuffer.getShort();
        int speed = byteBuffer.getShort();
        int servoPWM = byteBuffer.getShort();
        int motorPWM = byteBuffer.getShort();
        int batteryLevel = byteBuffer.getShort();


        return new DataModel(infrared1, infrared2, infrared3, infrared4, motorPWM, servoPWM, speed, batteryLevel, "");
    }

}
