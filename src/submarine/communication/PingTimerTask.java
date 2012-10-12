/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package submarine.communication;

import java.util.TimerTask;
import submarine.Submarine;

/**
 *
 * @author Administrator
 */
public class PingTimerTask extends TimerTask {
    private Submarine submarine;
    
    public PingTimerTask(Submarine submarine) {
        this.submarine = submarine;
    }
  
    @Override
    public void run() {        
        submarine.sendPing();
    }
}
