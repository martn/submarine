/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package submarine.communication;

import com.martinnovak.utils.Util;
import gnu.io.*;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.TooManyListenersException;

/**
 *
 * @author Martin Novak
 */
class ComPort implements Runnable, SerialPortEventListener {

    static CommPortIdentifier portId;
    static Enumeration portList;
    InputStream inputStream;
    SerialPort serialPort;
    Thread readThread;
    OutputStream outputStream;
    protected String divertCode = "10";
    static String TimeStamp;
    boolean outputBufferEmptyFlag = false;
    byte[] readBuffer = new byte[1024];
    int readBufferCount = 0;

    public ComPort(String defaultPort) throws PortNotFoundException {

        boolean portFound = false;

        defaultPort = defaultPort.toUpperCase();

        portList = CommPortIdentifier.getPortIdentifiers();

        while (portList.hasMoreElements()) {
            portId = (CommPortIdentifier) portList.nextElement();
            if (portId.getPortType() == CommPortIdentifier.PORT_SERIAL) {

                Util.log.write(portId.getName());

                if (portId.getName().equals(defaultPort)) {
                    Util.log.write("Found port: " + defaultPort);
                    portFound = true;
                    // init reader thread
                    // ComPort reader = new ComPort();
                    break;
                }
            }
        }
        if (!portFound) {
            Util.log.write("port " + defaultPort + " not found.");

            throw new PortNotFoundException("Port " + defaultPort + " not found.");
        } else {


            // initalize serial port
            try {
                serialPort = (SerialPort) portId.open(this.getClass().getName(), 2000);

            } catch (PortInUseException e) {
                Util.log.write("Error: port is in use");
            }

            try {
                inputStream = serialPort.getInputStream();
            } catch (IOException e) {
            }

            try {

                // set default port parameters
                serialPort.setSerialPortParams(19200, SerialPort.DATABITS_8,
                        SerialPort.STOPBITS_1,
                        SerialPort.PARITY_NONE);
            } catch (UnsupportedCommOperationException e) {
            }

            try {
                // get the outputstream
                outputStream = serialPort.getOutputStream();
            } catch (IOException e) {
            }

            try {
                serialPort.addEventListener(this);
                //serialPort.notifyOnDataAvailable(true);
            } catch (TooManyListenersException e) {
            }
            // start the read thread
            // readThread = new Thread(this);
            // readThread.start();
        }

    }

    /**
     *
     * thread to clear buffer;
     */
    public void run() {
    }

    /**
     * fills inBuffer with count number of bytes from incoming buffer
     * @param inBuffer
     * @param count
     * @return
     */
    /*   synchronized public int readBytes(byte inBuffer[], int count) {
    // TODO throw exceptions
    if (inputBufferCount == 0 | count < 0) {
    return 0;
    }
    
    if (count > inputBufferCount) {
    int retval = inputBufferCount;
    System.arraycopy(inputBuffer, 0, inBuffer, 0, inputBufferCount);
    clearBuffer();
    return retval;
    } else {
    System.arraycopy(inputBuffer, 0, inBuffer, 0, count);
    inputBuffer = ArrayUtils.subarray(inputBuffer, count, inputBufferCount);
    inputBufferCount -= count;
    return count;
    }
    }*/
    /**
     * clears input buffer
     */
    protected void clearReadBuffer() {
        readBufferCount = 0;
    }

    /**
     * copies elements from one array to another
     * @param targetArray
     * @param sourceArray
     * @param count
     */
    /*    private void arrayCopy(byte targetArray[], byte sourceArray[], int count) {
    for (int i = 0; i < count; i++) {
    targetArray[i]=sourceArray[i];
    }
    
    }*/
    /**
     * writes string to port
     * @param string
     */
    public void write(String string) {
        Util.log.write("Writing \"" + string + "\" to " + serialPort.getName());

        try {
            // write string to serial port
            outputStream.write(string.getBytes());
        } catch (IOException e) {
        }
    }

    /**
     * writes single byte to port
     * @param string
     */
    public void write(byte b) {

        Util.log.write("Writing \"" + Util.ByteToBinary(b));

        try {
            // write string to serial port
            outputStream.write(b);
        } catch (IOException e) {
        }
    }

    /**
     * method fired if new data in read buffer, for overriding
     */
    protected void readDataEvent() {
    }

    /**
     * asynchronous serial read event 
     * @param event
     */
    @Override
    synchronized public void serialEvent(SerialPortEvent event) {

        // Util.log.write("Event: "+event.getEventType());
        switch (event.getEventType()) {
            case SerialPortEvent.BI:
            case SerialPortEvent.OE:
            case SerialPortEvent.FE:
            case SerialPortEvent.PE:
            case SerialPortEvent.CD:
            case SerialPortEvent.CTS:
            case SerialPortEvent.DSR:
            case SerialPortEvent.RI:
            case SerialPortEvent.OUTPUT_BUFFER_EMPTY:
                break;
            case SerialPortEvent.DATA_AVAILABLE:

                // we get here if data has been received
                //Util.log.write("Reading: .............................");

                try {
                    int availableBytes = inputStream.available();

                    // read data
                    // while (availableBytes > 0) {
                    if (availableBytes > 0) {
                        readBufferCount = inputStream.read(readBuffer, 0, availableBytes);

                        //Util.log.write("buffer: " + availableBytes + " " + readBufferCount);

                        // buffer overflow
                        //         int overFlow = Math.max(readBufferCount + inputBufferCount - BUFFER_SIZE, 0);

                        // update inputBuffer, shifts data to the left if buffer full
          /*          inputBuffer = (byte[]) ArrayUtils.addAll(ArrayUtils.subarray(inputBuffer, overFlow, inputBufferCount),
                        ArrayUtils.subarray(readBuffer, 0, readBufferCount));
                         */
                        //      inputBufferCount += readBufferCount - overFlow;


                        // log
                        //    String result = new String(ArrayUtils.subarray(inputBuffer, 0, readBufferCount + inputBufferCount));


                        readDataEvent();
                        clearReadBuffer();
                    }
                    // } while (availableBytes > 0);
                    // }
                    //  Util.log.write("buffer: " + result);
                } catch (IOException e) {
                }
                break;
        }
    }
}
