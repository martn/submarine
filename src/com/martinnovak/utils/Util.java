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
}
