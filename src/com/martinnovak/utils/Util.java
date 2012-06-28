/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.martinnovak.utils;

/**
 *
 * @author Martin
 */
public class Util {

    public static Log log = new Log();

    public static String ByteToBinary(byte b) {

        StringBuilder output = new StringBuilder();

        for(int bit = 7; bit >= 0 ;bit--) {
            if((b & (1 << bit)) > 0) {
                output.append('1');
            } else {
                output.append('0');
            }
        }
        return output.toString();
    }
    
    /**
     * Rounds number to decimal places given by digits
     * @param number
     * @param digits
     * @return 
     */
    public static double round(double number, int digits) {
        double offset = Math.pow(10, digits);
        return Math.round(number*offset)/offset;
    }
    
    public static int byte2UnsignedInt(byte data) {
        return (int) data & 0xFF;
    }    
}
