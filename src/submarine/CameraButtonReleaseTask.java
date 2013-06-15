/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package submarine;

import java.util.TimerTask;

/**
 *
 * @author Administrator
 */
public class CameraButtonReleaseTask extends TimerTask {

    Submarine subObject;


    public CameraButtonReleaseTask(Submarine submarineObject) {
        subObject = submarineObject;
    }

    
    @Override
    public void run() {
        subObject.releaseCameraButtons();
    }
}
