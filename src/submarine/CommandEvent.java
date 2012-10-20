/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package submarine;

import java.util.EventObject;

/**
 *
 * @author Administrator
 */
public class CommandEvent extends EventObject {

    String type;
    int id;
    int value;

    public CommandEvent(Object source, String type, int id, int value) {
        super(source);
        this.type = type;
        this.id = id;
        this.value = value;
    }
}
