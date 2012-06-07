/*
 * To change this template, choose Tools | Templates and open the template in
 * the editor.
 */
package submarine.conversion;

import java.math.BigDecimal;
import java.text.DecimalFormat;

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

    public static double getTemperature(byte highByte, byte lowByte) {
        double doubleValue;
        String signBinaryString;
        String temperatureBinaryString;
        DecimalFormat df = new DecimalFormat("00000000");
        boolean positiveValue;

        /*
         * convert low and high bytes to sign and value strings
         */
        try {
            signBinaryString = df.format(Integer.parseInt(Integer.toBinaryString(lowByte))).substring(0, 5);
            temperatureBinaryString = df.format(Integer.parseInt(Integer.toBinaryString(lowByte))).substring(5, 8)
                    + df.format(Integer.parseInt(Integer.toBinaryString(highByte)));
            positiveValue = Integer.parseInt(signBinaryString) == 0;

            /*
             * calculate temperature value
             */
            if (positiveValue) {
                doubleValue = Integer.parseInt(temperatureBinaryString, 2) * TEMPERATURE_INCREMENTAL_STEP;
            } else {
                doubleValue = Integer.parseInt("11111111111", 2) * TEMPERATURE_INCREMENTAL_STEP
                        - Integer.parseInt(temperatureBinaryString, 2) * TEMPERATURE_INCREMENTAL_STEP
                        + TEMPERATURE_INCREMENTAL_STEP;
                doubleValue = doubleValue * -1;
            }
        } catch (Exception ex) {
            System.out.println("getTemperature exception: " + ex.getMessage());
            doubleValue = 0;
        }

//        System.out.println("getTemperature decimalValue: " + String.valueOf(doubleValue)) ;

        return FormatConvertor.round(doubleValue, 2, BigDecimal.ROUND_HALF_UP);
    }

    // lowbyte is always zero at the moment
    public static int getAzimuth(byte highByte) {
        String highByteString = FormatConvertor.getBinaryByteString(highByte);
        return (int) (Integer.parseInt(highByteString, 2) * AZIMUTH_COEFFICIENT);
    }

    public static double getLogicVoltage(byte highByte, byte lowByte) {
        return ((double) lowByte) / 10;
    }

    public static double getServoVoltage(byte highByte, byte lowByte) {
        return ((double) lowByte) / 10;
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
    
    public static double getPWR(byte highByte, byte lowByte) {
        return (double)((highByte & 0xFF) << 8) + (double)lowByte;
    }
}
