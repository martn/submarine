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
        return get10BitVoltageValue(lowByte, highByte)*10;
    }

    public static double getServoVoltage(byte lowByte, byte highByte) {
        return get10BitVoltageValue(lowByte, highByte)*10;
    }
    
    public static double get10BitVoltageValue(byte lowByte, byte highByte) {
        // rozsah prevodniku / rozsah dat * data
        return 2.5/1024*((Util.byte2UnsignedInt(highByte) << 2) + Util.byte2UnsignedInt((byte)(0x03 & lowByte)));
    }

    public static double getDepth(byte lowByte, byte highByte) {
        return get10BitVoltageValue(lowByte, highByte);
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
        return (Util.byte2UnsignedInt(highByte) << 8) + Util.byte2UnsignedInt(lowByte);
    }

    public static int getAccelY(byte highByte, byte lowByte) {        
        return (Util.byte2UnsignedInt(highByte) << 8) + Util.byte2UnsignedInt(lowByte);
    }

    public static int getAccelZ(byte highByte, byte lowByte) {
        return (Util.byte2UnsignedInt(highByte) << 8) + Util.byte2UnsignedInt(lowByte);
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
    
    public static double getI(byte data, double resolution) {
        return (252-(double)Util.byte2UnsignedInt(data))*resolution/252;
    }
    
    public static double getU(byte data) {
        return ((double)Util.byte2UnsignedInt(data))/10;
    }
}
