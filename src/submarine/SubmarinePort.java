/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package submarine;

import com.martinnovak.utils.Configuration;
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
    public static final int READ_MEM_COUNT = 23;
    boolean synced = false;
    // updated pure read memory / sensors
    protected byte[] readMemory = new byte[READ_MEM_COUNT];
    int memoryIndex = 0;

    
    public SubmarinePort(Configuration config) throws PortNotFoundException {

        super(config.getProperty("portId"));

        //if(config.getProperty("portBaud")this.serialPort
        serialPort.notifyOnDataAvailable(true);
    }

    /**
     * returns read memory
     * @return
     */
    public byte[] getReadMemory() {
        return readMemory;
    }

    
    @Override
    protected void readDataEvent() {
        super.readDataEvent();

        if (memoryIndex > 0) {
            // in filling process

            int startIndex = startSequenceIndex();

            if (startIndex >= 0) {
                // found start sequence
                // restart process

                fillReadMemory(0, startIndex);

            } else {
                // not found start sequence
                // continue filling memory

                fillReadMemory(memoryIndex, 0);
            }

        } else {
            // not filling up memory

            if (startSequenceIndex() >= 0) {
                // start sequence found, start filling

                fillReadMemory(memoryIndex, 0);
            }
            // not found, do nothing

        }

      //  Util.log.write("received.....");
    }


        
    /**
     * fills read memory from readBuffer
     * @param index from where to start
     */
    private void fillReadMemory(int memIndex, int bufferIndex) {

        // update memIndex
        memoryIndex = memIndex;

        // number of bytes from buffer
        int buffCount = readBufferCount - bufferIndex;

        // number of bytes to write to mem
        int count = Math.min(READ_MEM_COUNT - memIndex, buffCount);

        for (int i = memIndex; i < memIndex + count; i++) {
            readMemory[i] = readBuffer[i - memIndex + bufferIndex];
        }

        updateMemIndex(count);
    }

    /**
     * updates memory index
     * @param count number of bytes written to
     */
    private void updateMemIndex(int count) {
        memoryIndex += count;

        if (memoryIndex == READ_MEM_COUNT) {
            memoryIndex = 0;
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
