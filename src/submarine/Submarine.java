/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package submarine;

import com.martinnovak.utils.Configuration;
import com.martinnovak.utils.Util;
import java.util.Timer;

/**
 *
 * @author Martin Novak
 */
public class Submarine {

    public static final int ENGINE_RESOLUTION = 12;
    public static final int SERVO_RESOLUTION = 13;
    public static final byte ADRESS_SERVOS = 6;
    public static final byte ADRESS_CAMERA = 8;
    public static final byte ADRESS_ENGINES = 1;
    public static final byte ADRESS_POWER = 9;
    public static final int CAMERA_RELEASE_DELAY = 400;
    private int[] engine_speeds = new int[5];
    private int[] servo_position = new int[2];
    private byte power_config = 0;
    public SubmarinePort port;
    private Configuration config;
    private Timer cameraBtnReleaseTimer = new Timer();  // timer for button release

    public Submarine(Configuration conf) throws PortNotFoundException {
        config = conf;
        port = new SubmarinePort(config);

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

            //           cameraBtnReleaseTimer.schedule(new CameraButtonReleaseTask(this), CAMERA_RELEASE_DELAY);
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
        port.setData((byte)(~power_config & 127));
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
            engine_speeds[id] = speed;

            port.setAdress((byte) (ADRESS_ENGINES + id));

            int polarity = speed < 0 ? 64 : 0;

            port.setData((byte) (polarity | (Math.abs(speed) * 63 / ENGINE_RESOLUTION)));

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

            servo_position[id] = position;

            port.setAdress((byte) (ADRESS_SERVOS + id));
            port.setData((byte) (position < 0 ? Math.abs(position) + 64 : position));

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
