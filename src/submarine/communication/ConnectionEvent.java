/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package submarine.communication;

import java.util.EventObject;

/**
 *
 * @author Administrator
 */
public class ConnectionEvent extends EventObject {
    public boolean connected = false;

    public ConnectionEvent(Object o, boolean connected) {
        super(o);
        this.connected = connected;
    }    
}
