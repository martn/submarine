/*
 * To change this template, choose Tools | Templates and open the template in
 * the editor.
 */
package submarine.conversion;

import com.martinnovak.utils.Util;

/**
 *
 * @author Ondroid
 */
public class SentenceDataConvertor {

    // temperature convertor constants
    static final double TEMPERATURE_INCREMENTAL_STEP = 0.0625;
    static final short TEMPERATURE_NUMBER_OF_SIGN_BYTES = 5;
    // azimuth constants
    static final double AZIMUTH_COEFFICIENT = 1.41;

    public static double getTemperature(byte lowByte, byte highByte) {
        return (double)(((lowByte >>> 4) & 0x0F) + ((highByte & 0x07) << 4)) + Util.round((double)(lowByte & 0x0F)/16, 2);
    }

    // lowbyte is always zero at the moment
    public static int getAzimuth(byte highByte) {
        String highByteString = FormatConvertor.getBinaryByteString(highByte);
        return (int) (Integer.parseInt(highByteString, 2) * AZIMUTH_COEFFICIENT);
    }

    public static double getLogicVoltage(byte lowByte, byte highByte) {
        return Util.byte2UnsignedInt(lowByte) + 256*Util.byte2UnsignedInt(highByte);
    }

    public static double getServoVoltage(byte lowByte, byte highByte) {
        return Util.byte2UnsignedInt(lowByte) + 256*Util.byte2UnsignedInt(highByte);
    }

    public static double getDepth(byte highByte, byte lowByte) {
        double depth;

        short sh = (short) highByte;
        sh <<= 8;
        short ret = (short) (sh | lowByte);
        depth = ret & 0xFFFF;

        return depth;
    }

    public static double getAdci1(byte highByte, byte lowByte) {
        return -1;
    }

    public static double getAdci2(byte highByte, byte lowByte) {
        return -1;
    }

    public static double getAdci3(byte highByte, byte lowByte) {
        return -1;
    }

    public static double getAdci4(byte highByte, byte lowByte) {
        return -1;
    }

    public static int getAccelX(byte highByte, byte lowByte) {
        // TODO
        int accelX;

        short sh = (short) (highByte & 0xFF);
        sh <<= 8;

        short ret = (short) (sh | (short) lowByte & 0xFF);
        accelX = ret & 0xFF;

        return accelX;
    }

    public static int getAccelY(byte highByte, byte lowByte) {
        int accelY;

        short sh = (short) (highByte & 0xFF);
        sh <<= 8;

        int ret = sh | lowByte & 0xFF;
        accelY = ret & 0xFF;

        return accelY;
    }

    public static int getAccelZ(byte highByte, byte lowByte) {
        int accelZ;

        short sh = (short) highByte;
        sh <<= 8;
        short ret = (short) (sh | lowByte);
        accelZ = ret & 0xFF;

        return accelZ;
    }

    public static double getI2C1(byte highByte, byte lowByte) {
        return -1;
    }

    public static double getI2C2(byte highByte, byte lowByte) {
        return -1;
    }

    public static double getI2C3(byte highByte, byte lowByte) {
        return -1;
    }

    public static double getI2C4(byte highByte, byte lowByte) {
        return -1;
    }
    
    public static double getI(byte data) {
        return (double)Util.byte2UnsignedInt(data);
    }
    
    public static double getU(byte data) {
        return ((double)Util.byte2UnsignedInt(data))/10;
    }
}
