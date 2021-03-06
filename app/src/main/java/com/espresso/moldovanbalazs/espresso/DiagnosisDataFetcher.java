package com.espresso.moldovanbalazs.espresso;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class DiagnosisDataFetcher {

    public static DataModel decodeData(byte[] data) {


        ByteBuffer byteBuffer = ByteBuffer.wrap(data);
        byteBuffer = byteBuffer.order(ByteOrder.LITTLE_ENDIAN);
        int infrared1 = byteBuffer.getShort();
        int infrared2 = byteBuffer.getShort();
        int infrared3 = byteBuffer.getShort();
        int infrared4 = 0;
        int batteryLevel = byteBuffer.getShort();
        int speed = byteBuffer.getShort();
        int motorPWM = byteBuffer.getShort();
        int servoPWM = byteBuffer.getShort();

        return new DataModel(infrared1, infrared2, infrared3, infrared4, motorPWM, servoPWM, speed, batteryLevel, "");
    }

}
