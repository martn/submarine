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
public class FormatConvertor {
    
    public static String getHexString(byte[] bytes, int length) {
        String result = "";
        for (int i=0; i < length; i++) {
            result += " " +
            Integer.toString( ( bytes[i] & 0xff ) + 0x100, 16).substring( 1 );
        }
        return result;
    }
    
    public static byte getByte(String binaryString) {
        return Byte.parseByte(binaryString, 2);
    }
    
    public static String getBinaryByteString (byte b) {
        // TO DO - efficiency
        DecimalFormat df = new DecimalFormat("00000000");
        String binaryStringNoLeadingZeroes = Integer.toBinaryString((int) b & 0xFF);
        return df.format(Integer.parseInt(binaryStringNoLeadingZeroes));
    }
            
    public static double round(double unrounded, int precision, int roundingMode)
    {
        BigDecimal bd = new BigDecimal(unrounded);
        BigDecimal rounded = bd.setScale(precision, roundingMode);
        return rounded.doubleValue();
    }
}
