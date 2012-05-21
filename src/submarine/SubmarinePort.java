/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package submarine;

import com.martinnovak.utils.Configuration;
import com.martinnovak.utils.HexCodec;
import com.martinnovak.utils.Util;
//import org.apache.commons.lang3.ArrayUtils;

/**
 *
 * @author Martin Novak
 */
public class SubmarinePort extends ComPort {

    // interval of reading data from buffer
    public static final int READ_INTERVAL = 20;
    public static final byte START_SEQUENCE_BYTE = 64;
    
    boolean synced = false;
    // updated pure read memory / sensors
    protected byte[] readMemory = new byte[Sensors.READ_BYTES];
    int memoryIndex = 0;
    public Sensors sensors;
    
    public SubmarinePort(Configuration config) throws PortNotFoundException {
        
        super(config.getProperty("portId"));
        
        sensors = new Sensors();

        //if(config.getProperty("portBaud")this.serialPort
        serialPort.notifyOnDataAvailable(true);
    }
    
    public Sensors getSensors() {
        return sensors;
    }

    /**
     * returns actual read memory
     * @return
     */
    public byte[] getReadMemory() {
        return readMemory;
    }
    

    @Override
    protected void readDataEvent() {
        super.readDataEvent();

        int startIndex = startSequenceIndex();

        //Util.log.write("startIndex: " + startIndex);

        if (startIndex >= 0) {
            // start sequence found, start filling. Restart process...

            fillReadMemory(0, startIndex);

        } else if (memoryIndex > 0) {
            // in filling process
            // not found start sequence
            // continue filling memory

            fillReadMemory(memoryIndex, 0);
        }
    }

    
    /**
     * fills read memory from readBuffer
     * @param memIndex
     * @param bufferIndex 
     */
    private void fillReadMemory(int memIndex, int bufferIndex) {

        // update memIndex
        memoryIndex = memIndex;

        // number of bytes from buffer
        int buffCount = readBufferCount - bufferIndex;

        // number of bytes to write to mem
        int count = Math.min(Sensors.READ_BYTES - memIndex, buffCount);

        System.arraycopy(readBuffer, bufferIndex, readMemory, memIndex, count);
        /*for (int i = memIndex; i < memIndex + count; i++) {
        readMemory[i] = readBuffer[i - memIndex + bufferIndex];
        }*/

        
        // update mem count
        memoryIndex += count;

        if (memoryIndex >= Sensors.READ_BYTES) {
            // read buffer filled                        
            memoryIndex = 0;
            //Util.log.write(HexCodec.bytes2Hex(readMemory));
            sensors.setData(readMemory);
        }
    }


    /**
     * returns index of start sequence in readBuffer or -1
     * @return index of start sequence if found, -1 otherwise
     */
    private int startSequenceIndex() {

        for (int i = 0; i < readBufferCount - 3; i++) {
            if (readBuffer[i] == START_SEQUENCE_BYTE
                    & readBuffer[i + 1] == START_SEQUENCE_BYTE
                    & readBuffer[i + 2] == START_SEQUENCE_BYTE) {

                return i;
            }
        }

        return -1;
    }

    /**
     * synchronizes with the submarine
     */
    /*    private void sync() {
    
    // suspend
    //serialPort.notifyOnDataAvailable(false);
    
    if (inputBufferCount > 0) {
    int firstOccurenceIndex = ArrayUtils.indexOf(inputBuffer, START_SEQUENCE_BYTE);
    
    if (firstOccurenceIndex != ArrayUtils.INDEX_NOT_FOUND) {
    if (inputBuffer[firstOccurenceIndex + 1] == START_SEQUENCE_BYTE) {
    // clear
    inputBuffer = ArrayUtils.subarray(inputBuffer, firstOccurenceIndex, inputBufferCount);
    inputBufferCount -= firstOccurenceIndex;
    }
    } else {
    // not found
    clearBuffer();
    }
    } else {
    // nothing to do... ?
    }
    serialPort.notifyOnDataAvailable(true);
    }
     */
    /**
     * sets address where to write data (including 1 at the beginning)
     * @param adress
     */
    public void setAdress(byte adress) {

        // accepts adress
        write((byte) ((adress & (byte) 0x00FF) | 128));
    }

    /**
     * writes data to previously selected address
     * @param data
     */
    public void setData(byte data) {
        // shifts one bit to the left (protocol)
        write((byte) ((byte) data & (byte) 127));
    }
}
