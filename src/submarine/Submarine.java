/*
 * To change this template, choose Tools | Templates 
 * and open the template in the editor.
 */
package submarine;

import com.martinnovak.utils.Configuration;
import com.martinnovak.utils.Util;
import java.util.Timer;
import javax.swing.event.EventListenerList;
import net.java.games.input.Controller;
import submarine.communication.*;

/**
 *
 * @author Martin Novak
 */
public class Submarine {

    public static final int ENGINE_RESOLUTION = 24;
    public static final int ENGINE_RESOLUTION_V = 36; // resolution for vertical engines
    public static final int SERVO_RESOLUTION_H = 15;
    public static final int SERVO_RESOLUTION_V = 21;
    
    public static final byte ADRESS_SERVOS = 6;
    public static final byte ADRESS_CAMERA = 8;
    public static final byte ADRESS_ENGINES = 1;
    public static final byte ADRESS_POWER = 9;
    public static final byte ADRESS_PING = 10;
    public static final byte ADRESS_FUNCTION1 = 11;
    public static final byte ADRESS_FUNCTION2 = 12;
    public static final byte ADRESS_PROGRAM = 13;
    public static final byte ADRESS_RESET = 14;
    public static final byte ADRESS_LIGHT = 15;
    
    public static final int CAMERA_RELEASE_DELAY = 400;
    public static final int SERVO_HORIZONTAL = 0;
    public static final int SERVO_VERTICAL = 1;
    
    public static int ENGINE_LEFT = 0;
    public static int ENGINE_RIGHT = 1;
    public static int ENGINE_VERTICAL1 = 2;
    public static int ENGINE_VERTICAL2 = 3;
    public static int ENGINE_VERTICAL3 = 4;
    
    public static int[] ENGINE_POLARITIES = new int[5];    
    
    private int[] engine_speeds = new int[5];
    private int[] servo_position = new int[2];
    //private javax.swing.JLabel[] servoLabels;
    private byte power_config = 0;
    public SubmarinePort port;
    public Sensors sensors;
    public Controller controler;
    public Gamepad gamepad;
    private Configuration config;
    private Timer timer = null;    // = new Timer();
    public static final int PING_PERIOD = 2000;
    //private Timer cameraBtnReleaseTimer = new Timer();  // timer for button release
    protected EventListenerList listenerList = new EventListenerList();

    public Submarine(Configuration conf) throws PortNotFoundException {
        config = conf;

        port = new SubmarinePort(config);
        sensors = new Sensors(port);

        try {
            gamepad = new Gamepad(this);

        } catch (GamepadNotFoundException e) {

            Util.log.write(e.getMessage());
        }

        // prepare variables
        for (int i = 0; i < engine_speeds.length; i++) {
            engine_speeds[i] = 0;
        }

        for (int i = 0; i < servo_position.length; i++) {
            servo_position[i] = 0;
        }
        
        // setup engines
        setupEngines(config);
        
        startPinging();        
    }
    
    /**
     * Setups engine addresses
     * @param config 
     */
    private void setupEngines(Configuration config) {
        ENGINE_LEFT = getEngineAddress(config, "engineLeft", ENGINE_LEFT);
        ENGINE_RIGHT = getEngineAddress(config, "engineRight", ENGINE_RIGHT);
        ENGINE_VERTICAL1 = getEngineAddress(config, "engineVertical1", ENGINE_VERTICAL1);
        ENGINE_VERTICAL2 = getEngineAddress(config, "engineVertical2", ENGINE_VERTICAL2);
        ENGINE_VERTICAL3 = getEngineAddress(config, "engineVertical3", ENGINE_VERTICAL3);
                
        System.out.println("Engines!!!!");
        System.out.println(""+ENGINE_LEFT+" "+ENGINE_RIGHT+" "+ENGINE_VERTICAL1+" "+ENGINE_VERTICAL2+" "+ENGINE_VERTICAL3);
        
        setupEnginePolarities(config);
    }
    
    private int getEngineAddress(Configuration config, String id, int defaultValue) {
        int[] value = getEngineConfig(config, id, defaultValue+":"+0);
        return value[0]-1;
    }
    
    private void setupEnginePolarities(Configuration config) {
        String[] ids = "engineLeft engineRight engineVertical1 engineVertical2 engineVertical3".split(" ");
        for(int i=0;i<5;i++) {
            String id = ids[i];
            int[] cfg;
            cfg = getEngineConfig(config, id, (i+1)+":"+0);
            ENGINE_POLARITIES[cfg[0]-1] = cfg[1]*2-1; // this will make it -1 or 1
        }
    }
    
    private int[] getEngineConfig(Configuration config, String id, String defaultVal) {
        String cfg = config.getProperty(id, defaultVal);
        String[] strval = cfg.split(":");
        int[] val = new int[2];
        val[0] = Integer.parseInt(strval[0]);
        val[1] = Integer.parseInt(strval[1]);
        return val;
    }
    
    public void addCommandListener(CommandListener listener) {
        listenerList.add(CommandListener.class, listener);
    }
    
    public void removeCommandListener(CommandListener listener) {
        listenerList.remove(CommandListener.class, listener);
    }
    
    // This private class is used to fire MyEvents
    void fireCommandEvent(CommandEvent evt) {
        Object[] listeners = listenerList.getListenerList();
        // Each listener occupies two elements - the first is the listener class
        // and the second is the listener instance
        for (int i = 0; i < listeners.length; i += 2) {
            if (listeners[i] == CommandListener.class) {
                ((CommandListener) listeners[i + 1]).CommandOccurred(evt);
            }
        }
    }
    
    public SubmarinePort getPort() {
        return port;
    }

    public Sensors getSensors() {
        return sensors;
    }
    
    private void restartTestTimer() {
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
        timer = new Timer();
        timer.schedule(new PingTimerTask(this), PING_PERIOD);
    }

    /**
     * Pings the submarine
     */
    public void sendPing() {
        Util.log.write("Sending ping");
        port.setAdress(ADRESS_PING);
        port.setData(1);
        restartTestTimer();
    }
    
    private void startPinging() {
        restartTestTimer();
    }
    
    private void stopPinging() {
        if(timer != null) {
            timer.cancel();
        }        
    }
    
    /**
     * Sets a program mode of the submarine
     * @param program 
     */
    public void setProgramMode(boolean program) {
        port.setAdress(ADRESS_PROGRAM);
        byte data;
        if(program) {
            data = 1;
            stopPinging();
        } else {
            data = 0;
            startPinging();
        }
        port.setData(data);
    }
    
    /**
     * Sends a reset pulse
     * @param program 
     */
    public void reset() {
        port.setAdress(ADRESS_RESET);
        port.setData(1);
    }

    /**
     *
     * sends a push pulse of given number of camera button
     *
     * @param number index of a button 1 - 16
     */
    public void pushCameraButton(int number) {
        if (number >= 1 & number <= 16) {
            // if in range
            port.setAdress(ADRESS_CAMERA);
            port.setData((byte) number);
        }
    }

    /**
     * release all camera buttons
     */
    public void releaseCameraButtons() {
        port.setAdress(ADRESS_CAMERA);
        port.setData((byte) 0);
    }

    public void powerConfigToggle(int number) {
    }

    public void setPowerConfig(int number, boolean value) {
        // start from zero
        byte conf = (byte) (1 << (number - 1));
        power_config = value ? (byte) (power_config | conf) : (byte) (power_config & ~conf);

        port.setAdress(ADRESS_POWER);
        // invert, zero last bit
        port.setData((byte) (~power_config & 127));
    }

    private float computeEngineCompoundValue(float x, float y) {
        return Math.signum(y) * Math.max(Math.abs(x), Math.abs(y));
    }

    /**
     *
     * Sets joystick values, converts them to engine speeds
     *
     * @param x -1~1
     * @param y -1~1
     */
    public void joystick2Engines(float x, float y) {
        float leftValue, rightValue;
        if (x * y >= 0) {
            leftValue = computeEngineCompoundValue(x, y);
            rightValue = y - x;
        } else {
            leftValue = x + y;
            rightValue = computeEngineCompoundValue(x, y);
        }

        //int leftIntValue = (int)(leftValue*ENGINE_RESOLUTION);
        //int rightIntValue = (int)(rightValue*ENGINE_RESOLUTION);

        //if (engine_speeds[ENGINE_LEFT] != leftIntValue || engine_speeds[ENGINE_RIGHT] != rightIntValue) {
        //System.out.println("X: "+x+"  Y: "+y);
        //    System.out.println("L: "+leftValue+"  R: "+rightValue);
        // }

        int l_old = engine_speeds[ENGINE_LEFT];
        int r_old = engine_speeds[ENGINE_RIGHT];

        int l = setEngineSpeed(ENGINE_LEFT, (int) Math.floor(leftValue * ENGINE_RESOLUTION));
        int r = setEngineSpeed(ENGINE_RIGHT, (int) Math.floor(rightValue * ENGINE_RESOLUTION));

        if (l_old != l || r_old != r) {
            Util.log.write("Left: " + l + " Right: " + r);
        }
    }

    /**
     * Sets joystick values, converts them to servos position
     *
     * @param x
     * @param y
     */
    public void joystick2Servos(float x, float y) {
        int h_old = servo_position[SERVO_HORIZONTAL];
        int v_old = servo_position[SERVO_VERTICAL];

        int h = setServoPosition(SERVO_HORIZONTAL, (int) Math.floor(-x * SERVO_RESOLUTION_H));
        int v = setServoPosition(SERVO_VERTICAL, (int) Math.floor(y * SERVO_RESOLUTION_V));

        if (h_old != h || v_old != v) {
            Util.log.write("Horizontal: " + h + " Vertical: " + v);
        }
    }

    /**
     * Goes up with the submarine
     *
     * @return
     */
    public void goUp() {
        Util.log.write("Go up");
        incrementEngineSpeed(ENGINE_VERTICAL1);
        incrementEngineSpeed(ENGINE_VERTICAL2);
        incrementEngineSpeed(ENGINE_VERTICAL3);
    }

    /**
     * Goes up with the submarine
     *
     * @return
     */
    public void goDown() {
        Util.log.write("Go down");
        decrementEngineSpeed(ENGINE_VERTICAL1);
        decrementEngineSpeed(ENGINE_VERTICAL2);
        decrementEngineSpeed(ENGINE_VERTICAL3);
    }

    /**
     *
     * sets speed of selected engine
     *
     * @param id
     * @param speed
     */
    public int setEngineSpeed(int id, int speed) {
        // max min test
        int resolution = ENGINE_RESOLUTION;
        if(id >= 2) {
            // engine is vertical ???
            resolution = ENGINE_RESOLUTION_V;
        }
        
        if (speed <= resolution & speed >= -resolution) {
            if (engine_speeds[id] != speed) {
                engine_speeds[id] = speed;

                //System.out.println(speed);

                port.setAdress((byte) (ADRESS_ENGINES + id));
                                
                int polarity = (speed*ENGINE_POLARITIES[id]) < 0 ? 64 : 0;
                port.setData((byte) (polarity | (Math.abs(speed) * 63 / resolution)));
                
                fireCommandEvent(new CommandEvent(this, "engine", id, speed));
            }
            return speed;
        } else {
            return speed > 0 ? resolution : -resolution;
        }
    }

    /**
     * increments current engine speed, if possible. returns actual value
     *
     * @param id
     * @return
     */
    public int incrementEngineSpeed(int id) {
        return setEngineSpeed(id, getEngineSpeed(id) + 1);
    }

    /**
     * decrements current engine speed, if possible
     *
     * @param id
     * @return
     */
    public int decrementEngineSpeed(int id) {
        return setEngineSpeed(id, getEngineSpeed(id) - 1);
    }

    /**
     * increments servo position if possible
     *
     * @return
     */
    public int incrementServoPosition(int id) {
        return setServoPosition(id, getServoPosition(id) + 1);
    }

    /**
     * decrements servo position if possible
     *
     * @return
     */
    public int decrementServoPosition(int id) {
        return setServoPosition(id, getServoPosition(id) - 1);
    }

    /**
     * sets servo position
     *
     * @param position
     */
    public int setServoPosition(int id, int position) {

        int res = id == SERVO_VERTICAL ? SERVO_RESOLUTION_V : SERVO_RESOLUTION_H;
        position = (int) Math.signum(position) * Math.min(Math.abs(position), res);

        if (servo_position[id] != position) {
            // if last position is different

            servo_position[id] = position;

            //System.out.println(position);

            port.setAdress((byte) (ADRESS_SERVOS + id));
            port.setData((byte) (position < 0 ? Math.abs(position) + 64 : position));
        }
        return position;
    }

    /**
     * returns servo position
     *
     * @return
     */
    public int getServoPosition(int id) {
        return servo_position[id];
    }

    /**
     * returns speed of selected engine
     *
     * @param id
     */
    public int getEngineSpeed(int id) {
        return engine_speeds[id];
    }
}
