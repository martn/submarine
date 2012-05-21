/*
 * SubmarineApp.java
 */
package submarine;

import com.martinnovak.utils.Configuration;
import com.martinnovak.utils.Util;
import java.net.URL;
import javax.swing.ImageIcon;
import org.jdesktop.application.SingleFrameApplication;
import net.java.games.input.Controller;
import net.java.games.input.ControllerEnvironment;
import org.jdesktop.application.Application;
import org.jdesktop.application.ResourceMap;

/**
 * The main class of the application.
 */
public class SubmarineApp extends SingleFrameApplication {

    static Configuration config;
    static Submarine submarine;

    /**
     * At startup create and show the main frame of the application.
     */
    @Override
    protected void startup() {
        show(new SubmarineView(this));

        config = new Configuration();

        Util.log.logToTextArea(SubmarineView.logWindow);
        //Util.log.logToConsole(true);

        try {
            submarine = new Submarine(config);

            // Initial settings

            // stop engines and servo to 0 position
            submarine.setEngineSpeed(0, 0);
            submarine.setEngineSpeed(1, 0);
            //  submarine.setEngineSpeed(2, 0);
            //  submarine.setEngineSpeed(3, 0);
            //  submarine.setEngineSpeed(4, 0);

            submarine.setServoPosition(0, 0);
            submarine.setServoPosition(1, 0);

        } catch (PortNotFoundException e) {
            Util.log.write("Port Not Found");
        }
        // new Thread(port).start();
    }

    /**
     * This method is to initialize the specified window by injecting resources.
     * Windows shown in our application come fully initialized from the GUI
     * builder, so this additional configuration is not needed.
     */
    @Override
    protected void configureWindow(java.awt.Window root) {
        try {
            //InputStream imgStream = this.getClass().getResourceAsStream("submarine-icon.png");
            //BufferedImage bi = ImageIO.read(imgStream);

            ResourceMap resourceMap = getContext().getResourceMap();
            String filename = resourceMap.getResourcesDir() + "submarine-icon.png";
            ImageIcon icon = new ImageIcon(resourceMap.getClassLoader().getResource(filename));
            this.getMainFrame().setIconImage(icon.getImage());
        } catch (Exception e) {
            System.out.println(e);
        }

    }

    /**
     * A convenient static getter for the application instance.
     * @return the instance of SubmarineApp
     */
    public static SubmarineApp getApplication() {
        return Application.getInstance(SubmarineApp.class);
    }

    /**
     * Main method launching the application.
     */
    public static void main(String[] args) {
        launch(SubmarineApp.class, args);
        
        Controller[] ca = ControllerEnvironment.getDefaultEnvironment().getControllers();

        for (int i = 0; i < ca.length; i++) {

            // Get the name of the controller
            Util.log.write(ca[i].getName());
        }

    }
}
