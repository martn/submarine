/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package submarine;

import com.martinnovak.utils.Configuration;
import com.martinnovak.utils.Util;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Timer;
import net.java.games.input.Component;
import net.java.games.input.Controller;
import net.java.games.input.ControllerEnvironment;
import net.java.games.input.Rumbler;

/**
 *
 * @author Martin Novak
 */
public class Submarine {

    public static final int ENGINE_RESOLUTION = 24;
    public static final int SERVO_RESOLUTION = 25;
    public static final byte ADRESS_SERVOS = 6;
    public static final byte ADRESS_CAMERA = 8;
    public static final byte ADRESS_ENGINES = 1;
    public static final byte ADRESS_POWER = 9;
    public static final int CAMERA_RELEASE_DELAY = 400;
    public static final int SERVO_HORIZONTAL = 0;
    public static final int SERVO_VERTICAL = 1;
    public static final int ENGINE_LEFT = 0;
    public static final int ENGINE_RIGHT = 1;
    private int[] engine_speeds = new int[5];
    private int[] servo_position = new int[2];
    private javax.swing.JLabel[] servoLabels;
    private byte power_config = 0;
    public SubmarinePort port;
    public Controller controler;
    public Gamepad gamepad;
    private Configuration config;
    private Timer cameraBtnReleaseTimer = new Timer();  // timer for button release

    public Submarine(Configuration conf) throws PortNotFoundException {
        config = conf;
        port = new SubmarinePort(config);

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
    }

    /**
     *
     * sends a push pulse of given number of camera button
     * @param number index of a button 1 - 16
     */
    public void pushCameraButton(int number) {
        if (number >= 1 & number <= 16) {
            // if in range
            port.setAdress(ADRESS_CAMERA);
            port.setData((byte) number);

            // cameraBtnReleaseTimer.schedule(new CameraButtonReleaseTask(this), CAMERA_RELEASE_DELAY);
        }
    }

    /**
     * release all camera buttons
     */
    public void releaseCameraButtons() {
        port.setAdress(ADRESS_CAMERA);
        port.setData((byte) 0);

        Util.log.write("released...");
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
        return Math.signum(y)*Math.max(Math.abs(x), Math.abs(y));
    }

    /**
     * 
     * Sets joystick values, converts them to engine speeds
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
        
        setEngineSpeed(ADRESS_ENGINES+ENGINE_LEFT, (int)(leftValue*ENGINE_RESOLUTION));
        setEngineSpeed(ADRESS_ENGINES+ENGINE_RIGHT, (int)(rightValue*ENGINE_RESOLUTION));
    }

    /**
     * Sets joystick values, converts them to servos position
     * @param x
     * @param y 
     */
    public void joystick2Servos(float x, float y) {
        setServoPosition(SERVO_HORIZONTAL, (int) (x * SERVO_RESOLUTION));
        setServoPosition(SERVO_VERTICAL, (int) (y * SERVO_RESOLUTION));
    }

    /**
     *
     * sets speed of selected engine
     * @param id
     * @param speed
     */
    public int setEngineSpeed(int id, int speed) {

        
        // max min test
        if (speed <= ENGINE_RESOLUTION & speed >= -ENGINE_RESOLUTION) {
            if (engine_speeds[id] != speed) {
                engine_speeds[id] = speed;
                
                System.out.println(speed);
                
                port.setAdress((byte) (ADRESS_ENGINES + id));

                int polarity = speed < 0 ? 64 : 0;
                port.setData((byte) (polarity | (Math.abs(speed) * 63 / ENGINE_RESOLUTION)));
            }
            return speed;
        } else {
            return speed > 0 ? ENGINE_RESOLUTION : -ENGINE_RESOLUTION;
        }
    }

    /**
     * increments current engine speed, if possible.
     * returns actual value
     * @param id
     * @return
     */
    public int incrementEngineSpeed(int id) {
        return setEngineSpeed(id, getEngineSpeed(id) + 1);
    }

    /**
     * decrements current engine speed, if possible
     * @param id
     * @return
     */
    public int decrementEngineSpeed(int id) {
        return setEngineSpeed(id, getEngineSpeed(id) - 1);
    }

    /**
     * increments servo position if possible
     * @return
     */
    public int incrementServoPosition(int id) {
        return setServoPosition(id, getServoPosition(id) + 1);
    }

    /**
     * decrements servo position if possible
     * @return
     */
    public int decrementServoPosition(int id) {
        return setServoPosition(id, getServoPosition(id) - 1);
    }

    /**
     * sets servo position
     * @param position
     */
    public int setServoPosition(int id, int position) {
        if (position <= SERVO_RESOLUTION & position >= -SERVO_RESOLUTION) {

            if (servo_position[id] != position) {
                // if last position is different

                servo_position[id] = position;
                
                port.setAdress((byte) (ADRESS_SERVOS + id));
                port.setData((byte) (position < 0 ? Math.abs(position) + 64 : position));
            }
            return position;
        } else {
            return position > 0 ? SERVO_RESOLUTION : -SERVO_RESOLUTION;
        }
    }

    /**
     * returns servo position
     * @return
     */
    public int getServoPosition(int id) {
        return servo_position[id];
    }

    /**
     * returns speed of selected engine
     * @param id
     */
    public int getEngineSpeed(int id) {
        return engine_speeds[id];
    }
}
