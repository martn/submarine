/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package submarine.communication;

import com.martinnovak.utils.Configuration;
import com.martinnovak.utils.HexCodec;
import com.martinnovak.utils.Util;
import java.util.Arrays;
import java.util.Timer;
import javax.swing.event.EventListenerList;
import submarine.Sensors;
//import org.apache.commons.lang3.ArrayUtils;

/**
 *
 * @author Martin Novak
 */
public class SubmarinePort extends ComPort {

    // interval of reading data from buffer
    //public static final int READ_INTERVAL = 20;
    public static final int TEST_PERIOD = 2000;
    public static final byte START_SEQUENCE_BYTE = 64;
    String startSequence = "@@@";
    // if true, start sequence is on the beginning of readMemory
    boolean readValid = false;
    boolean connectedStatus = false;
    boolean oldConnectedStatus = false;
    private Timer timer = null;    // = new Timer();
    //private ConnectionTestTimerTask testTimerTask = new ConnectionTestTimerTask(this);
    // updated pure read memory / sensors
    protected byte[] readMemory = new byte[Sensors.READ_BYTES * 2];
    int memoryIndex = 0;
    // Create the listener list
    protected EventListenerList listenerList = new EventListenerList();

    public SubmarinePort(Configuration config) throws PortNotFoundException {

        super(config.getProperty("portId"));

        //if(config.getProperty("portBaud")this.serialPort
        serialPort.notifyOnDataAvailable(true);

        restartTestTimer();
    }

    private void restartTestTimer() {
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
        timer = new Timer();
        timer.schedule(new ConnectionTestTimerTask(this), TEST_PERIOD);
    }

    public void addConnectionListener(ConnectionListener listener) {
        listenerList.add(ConnectionListener.class, listener);
    }

    // This methods allows classes to unregister for MyEvents
    public void removeConnectionListener(ConnectionListener listener) {
        listenerList.remove(ConnectionListener.class, listener);
    }

    // This methods allows classes to register for MyEvents
    public void addReadListener(ReadListener listener) {
        listenerList.add(ReadListener.class, listener);
    }

    // This methods allows classes to unregister for MyEvents
    public void removeReadListener(ReadListener listener) {
        listenerList.remove(ReadListener.class, listener);
    }

    // This private class is used to fire MyEvents
    void fireConnectionEvent(ConnectionEvent evt) {
        Object[] listeners = listenerList.getListenerList();
        // Each listener occupies two elements - the first is the listener class
        // and the second is the listener instance
        for (int i = 0; i < listeners.length; i += 2) {
            if (listeners[i] == ConnectionListener.class) {
                ((ConnectionListener) listeners[i + 1]).ConnectionOccurred(evt);
            }
        }
    }

    // This private class is used to fire MyEvents
    void fireReadEvent(ReadEvent evt) {
        Object[] listeners = listenerList.getListenerList();
        // Each listener occupies two elements - the first is the listener class
        // and the second is the listener instance
        for (int i = 0; i < listeners.length; i += 2) {
            if (listeners[i] == ReadListener.class) {
                ((ReadListener) listeners[i + 1]).ReadOccurred(evt);
            }
        }
    }

    /**
     * returns actual read memory
     *
     * @return
     */
    public byte[] getReadMemory() {
        return readMemory;
    }

    public void lookupConnection() {
        if (oldConnectedStatus != connectedStatus) {
            oldConnectedStatus = connectedStatus;
            fireConnectionEvent(new ConnectionEvent(this, connectedStatus));
        }
    }

    @Override
    protected void readDataEvent() {
        super.readDataEvent();

        // copy all incomming bytes to the end of readMemory
        System.arraycopy(readBuffer, 0, readMemory, memoryIndex, readBufferCount);

        // update memory index
        memoryIndex += readBufferCount;

        int startIndex = startSequenceIndex();

        //Util.log.write("startIndex: " + startIndex);` 

        switch (startIndex) {
            case -1:
                // sequence not found, data not valid
                readValid = false;
                break;
            case 0:
                // sequence is at the beginning, everything ok.
                readValid = true;
                break;
            default:
                // all other cases. Start sequence is in the middle. Index > 0
                memoryIndex -= startIndex;
                System.arraycopy(readMemory, startIndex, readMemory, 0, memoryIndex);
                readValid = true;
                break;
        }

        if (memoryIndex >= Sensors.READ_BYTES & readValid) {
            // read buffer filled                        
            memoryIndex = 0;

            connectedStatus = true;
            lookupConnection();
            // restart connection timeout
            restartTestTimer();

            //Util.log.write(HexCodec.bytes2Hex(readMemory));


            // HERE ==============================
            //sensors.setData(readMemory);
            fireReadEvent(new ReadEvent(this));
        }
    }

    /**
     * fills read memory from readBuffer
     *
     * @param memIndex
     * @param bufferIndex
     */
//    private void fillReadMemory(int memIndex, int bufferIndex) {
//
//        // update memIndex
//        memoryIndex = memIndex;
//
//        // number of bytes from buffer
//        int buffCount = readBufferCount - bufferIndex;
//
//        // number of bytes to write to mem
//        int count = Math.min(Sensors.READ_BYTES - memIndex, buffCount);
//
//        System.arraycopy(readBuffer, bufferIndex, readMemory, memIndex, count);
//
//
//        // update mem count
//        memoryIndex += count;
//
//        if (memoryIndex >= Sensors.READ_BYTES) {
//            // read buffer filled                        
//            memoryIndex = 0;
//            //Util.log.write(HexCodec.bytes2Hex(readMemory));
//
//
//            // HERE ==============================
//            //sensors.setData(readMemory);
//            fireReadEvent(new ReadEvent(this));
//        }
//    }
    /**
     * returns index of start sequence in readBuffer or -1
     *
     * @return index of start sequence if found, -1 otherwise
     */
    private int startSequenceIndex() {
        String strBuf = new String(Arrays.copyOfRange(readMemory, 0, memoryIndex));
        return strBuf.indexOf(startSequence);
    }

    /**
     * sets address where to write data (including 1 at the beginning)
     *
     * @param adress
     */
    public void setAdress(byte adress) {

        // accepts adress
        write((byte) ((adress & (byte) 0x00FF) | 128));
    }

    /**
     * writes data to previously selected address
     *
     * @param data
     */
    public void setData(byte data) {
        // shifts one bit to the left (protocol)
        write((byte) ((byte) data & (byte) 127));
    }
}
