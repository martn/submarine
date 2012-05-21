/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package submarine;

import com.martinnovak.utils.HexCodec;
import com.martinnovak.utils.Util;
import java.util.Arrays;
import java.util.Timer;

/**
 *
 * @author Administrator
 */
public class Sensors {

    //private Timer timer;    // = new Timer();
    
    //private SensorsTimerTask timerTask;
    public static final int READ_BYTES = 23;
    public static final int DATA_START_CODE = 64;
    public static final int DATA_INDEX_TEMPERATURE = 3;
    public static final int DATA_INDEX_AZIMUTH = 5;
    public static final int DATA_INDEX_BATTERY1 = 7;
    public static final int DATA_INDEX_BATTERY2 = 9;
    protected byte[] data = new byte[READ_BYTES];
    public static final String[] test = {"40 40 40 02 02 CC 00 03 00 01 00 03 01 6B 8C 9B B5 CF B2 00 FD 26 E5 ",
        "40 40 40 02 02 CC 00 03 00 00 00 00 00 6C 8C 99 B5 CF B9 01 0E 26 89 ",
        "40 40 40 02 02 CC 00 03 01 01 00 01 00 70 8C 9B B4 CF B2 00 F4 27 2E ",
        "40 40 40 01 02 CC 00 01 00 01 00 03 00 70 8C 9B B4 CF AD 01 00 26 C5 ",
        "40 40 40 01 02 CC 00 01 00 01 00 03 00 70 8C 9B B4 CF C2 01 F3 26 D0 ",
        "40 40 40 02 02 CC 00 03 00 00 00 03 00 6D 8C 9B B6 CF B5 01 15 26 9D ",
        "40 40 40 01 02 CC 00 03 00 03 01 02 01 6C 8C 9B B5 FC 3F 1C CC 3D 99 ",
        "40 40 40 02 02 49 00 03 00 03 01 01 00 70 8C 9C B4 2D F1 22 50 1A B1 ",
        "40 40 40 02 02 2B 00 00 00 01 00 03 00 70 8F 9B B3 1F 01 C9 A1 03 B2 ",
        "40 40 40 02 12 2B 00 03 00 03 00 01 00 6C 8F 9B B3 1E E3 C9 8E 03 B4 ",
        "40 40 40 02 02 2B 00 01 00 00 00 01 00 70 8F 9C B4 1E DC C9 91 03 79 ",
        "40 40 40 02 02 2B 00 00 00 03 00 01 00 70 8F 9C B3 1E F2 C9 8E 03 84 ",
        "40 40 40 03 02 2A 00 03 00 03 00 03 00 70 8F 9C B3 1E D6 C9 7F 03 64 ",
        "40 40 40 23 02 2A 00 03 00 01 00 03 00 6F 8F 9C B4 1E E7 C9 83 03 93 ",
        "40 40 40 03 02 2A 00 03 00 01 00 03 00 6C 8F 9B B4 1E DB C9 80 03 7C ",
        "40 40 40 03 00 2A 00 03 00 01 00 01 00 70 8F 9B B4 1E D5 C9 91 03 8E ",
        "40 40 40 03 02 2A 00 01 00 01 00 03 00 70 8F 9C B4 1E CD C9 77 03 75 ",
        "40 40 40 03 12 2A 00 03 01 01 00 03 01 70 8F 9B B3 1E E4 C9 8E 03 70 ",
        "40 40 40 04 02 2A 00 01 00 03 01 03 01 6C 8F 9C B3 1E D2 C9 8F 03 9A ",
        "40 40 40 04 02 2A 00 03 00 03 01 00 00 70 8F 9C B4 1E F3 C9 89 03 66 ",
        "40 40 40 04 02 2A 00 03 00 03 00 00 00 70 8F 9C B4 1E D0 C9 86 03 90 ",
        "40 40 40 24 02 2A 00 03 01 01 00 03 01 70 8F 9C B3 1E D9 C9 8F 03 A3 ",
        "40 40 40 04 02 2A 00 00 00 03 00 03 00 70 8F 9B B4 1E E6 C9 8C 03 61 ",
        "40 40 40 04 02 2A 00 01 00 03 00 03 01 6B 8F 9C B4 1E FE C9 95 03 A6 ",
        "40 40 40 04 02 2A 00 01 00 01 00 03 00 6F 8F 9B B4 1E EC C9 83 03 6F ",
        "40 40 40 04 02 2A 00 03 00 00 00 03 01 70 8F 9C B3 1E D9 C9 7C 03 91 ",
        "40 40 40 04 02 2B 00 01 00 03 00 03 00 70 8F 9C B4 1E E9 C9 88 03 5A "};

    
    public Sensors() {
        //timerTask = new SensorsTimerTask(this);        
    }

    /**
     * Sets new read data array
     * @param d 
     */
    public void setData(byte[] d) {        
        data = Arrays.copyOfRange(d, 0, READ_BYTES);
        
        //Util.log.write(HexCodec.bytes2Hex(data));
    }
    
    /*
    private boolean testData() {
        String strData = HexCodec.bytes2Hex(data);
        for(int i=0;i<test.length;i++) {
            if(test[i].equalsIgnoreCase(strData))
                return true;
        }
        return false;
    }*/

    /*
    private int getNextDataIndex() {
        if (dataIndex == data.length - 1) {
            return dataIndex = 0;
        }

        return dataIndex++;
    }


    private byte[] getNextData() {
        String[] dataStr = test[getNextDataIndex()].toLowerCase().split(" ");

        byte[] dataOut = new byte[dataStr.length];

        for (int i = 0; i < dataStr.length; i++) {
            dataOut[i] = HexCodec.hexToByte(dataStr[i]);
        }

        return dataOut;
    }*/
}
