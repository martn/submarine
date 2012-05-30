/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package submarine.communication;

import java.util.EventListener;

/**
 *
 * @author Administrator
 */
public interface ReadListener extends EventListener {
    public void ReadOccurred(ReadEvent evt);
}
