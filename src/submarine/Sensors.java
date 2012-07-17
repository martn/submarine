/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package submarine;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.Arrays;
import submarine.communication.ReadEvent;
import submarine.communication.ReadListener;
import submarine.communication.SubmarinePort;
import submarine.conversion.SentenceDataConvertor;

/**
 *
 * @author Administrator
 */
public class Sensors implements ReadListener {

    public static final int READ_BYTES = 31;
    public static final int DATA_START_CODE = 64;
    public static final int DATA_INDEX_TEMPERATURE = 3;
    public static final int DATA_INDEX_AZIMUTH = 5;
    public static final int DATA_INDEX_BATTERY1 = 7;
    public static final int DATA_INDEX_BATTERY2 = 9;
    
    // private Map<String, Double> _sentence = new HashMap<String, Double>();
    private byte[] data;
    private DecimalFormat decimalFormatDouble = new DecimalFormat("#0.00");
    private SubmarinePort port;

    public Sensors(SubmarinePort port) {
        this.port = port;
        
        port.addReadListener(this);
        
        decimalFormatDouble.setRoundingMode(RoundingMode.UP);
    }
    
    
    @Override
    public void ReadOccurred(ReadEvent evt) {
        setData(port.getReadMemory());
    }

    /**
     * Sets new read data array
     * @param d 
     */
    public void setData(byte[] d) {        
        data = Arrays.copyOfRange(d, 0, READ_BYTES);
        //Util.log.write(HexCodec.bytes2Hex(data));
    }
        
    
    // temperature
    public double getTemperature() {
        // TO DO
        // return Double.parseDouble(_decimalFormatDouble.format(SentenceDataConvertor.getTemperature(_sentence[0], _sentence[1])));
        return SentenceDataConvertor.getTemperature(data[3], data[4]);
    }

    // azimuth
    public int getAzimuth() {
        return SentenceDataConvertor.getAzimuth(data[5]);
    }
    
    public double getLogicVoltage() {
        return SentenceDataConvertor.getLogicVoltage(data[7], data[8]);
    }

    public double getServoVoltage() {
        return SentenceDataConvertor.getServoVoltage(data[11], data[12]);
    }

    public double getDepth() {
        return SentenceDataConvertor.getDepth(data[9], data[10]);
    }

    // TODO
    public int getAccelX() {
        return SentenceDataConvertor.getAccelX(data[21], data[22]);
    }

    public int getAccelY() {
        return SentenceDataConvertor.getAccelY(data[23], data[24]);
    }

    public int getAccelZ() {
        return SentenceDataConvertor.getAccelZ(data[19], data[18]);
    }

    public double getI2C1() {
        return SentenceDataConvertor.getI2C1(data[8], data[9]);
    }

    public double getI2C2() {
        return SentenceDataConvertor.getI2C2(data[8], data[9]);
    }

    public double getI2C3() {
        return SentenceDataConvertor.getI2C3(data[8], data[9]);
    }

    public double getI2C4() {
        return SentenceDataConvertor.getI2C4(data[8], data[9]);
    }
    
    public double getPWR_UINcam() {
        return SentenceDataConvertor.getU(data[23])*0.8845;
    }
    
    public double getPWR_Ilog() {
        return SentenceDataConvertor.getI(data[24], 50);
    }
    
    public double getPWR_Ulog() {
        return SentenceDataConvertor.getU(data[25])*0.894;
    }
    public double getPWR_Icam() {
        return SentenceDataConvertor.getI(data[26], 50);
    }
    
    public double getPWR_Ieng() {
        return SentenceDataConvertor.getI(data[27], 100);
    }
    
    public double getPWR_Ueng() {
        return SentenceDataConvertor.getU(data[28])*0.86;
    }
    
    public double getPWR_UOUTcam() {
        return SentenceDataConvertor.getU(data[29])*0.86;
    }
    
    public double getPWR_UOUT4() {
        return SentenceDataConvertor.getU(data[30])*0.86;
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
