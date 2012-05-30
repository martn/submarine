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
public class ReadEvent extends EventObject {
    public ReadEvent(Object source) {
        super(source);
    }
}
