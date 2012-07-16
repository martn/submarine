/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package submarine.communication;

import java.util.TimerTask;

/**
 *
 * @author Administrator
 */
public class ConnectionTestTimerTask extends TimerTask {

    private SubmarinePort port;
    
    public ConnectionTestTimerTask(SubmarinePort port) {
        this.port = port;
    }
  
    @Override
    public void run() {
        port.connectedStatus = false;
        port.lookupConnection();
    }    
}
