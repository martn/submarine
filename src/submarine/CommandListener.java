/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package submarine;
import java.util.EventListener;
/**
 *
 * @author Administrator
 */
public interface CommandListener extends EventListener {
    public void CommandOccurred(CommandEvent evt);    
}
