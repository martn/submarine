/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package submarine;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.awt.event.*;
import javax.swing.*;
import net.java.games.input.Component;
import net.java.games.input.Controller;
import net.java.games.input.Rumbler;

/**
 *
 * @author Administrator
 */
public class Gamepad {

    public GamePadController gpController;
    private static final int DELAY = 50;   // ms (polling interval)
    // needs to be fast to catch fast button pressing!
    private Timer pollTimer;   // timer which triggers the polling
    private Submarine submarine;

    public Gamepad(Submarine subm) throws GamepadNotFoundException {

        gpController = new GamePadController();
        submarine = subm;

        printDetails(getController(), System.out);

        startPolling();
    }

    public Controller getController() {
        return gpController.getController();
    }

    public void updateData() {
        // System.out.println("polling...");
        gpController.poll();

        // update the GUI:
        // get POV hat compass direction
        int compassDir = gpController.getHatDir();
        //hatPanel.setCompass(compassDir);

        // get compass direction for the two analog sticks
        float xyValue[] = gpController.getXYStickValue();
        //xyPanel.setCompass(compassDir);
        
        

        // ========== Servo =============================
        float zrValue[] = gpController.getZRZStickValue();
        //zrzPanel.setCompass(compassDir);
        //System.out.println("0: " + zrValue[0] + " 1: " + zrValue[1]);

        submarine.setServoPosition(Submarine.ID_SERVO_HORIZONTAL, 
               (int)(-zrValue[0]*Submarine.SERVO_RESOLUTION));
        
        submarine.setServoPosition(Submarine.ID_SERVO_VERTICAL, 
               (int)(-zrValue[1]*Submarine.SERVO_RESOLUTION));

        // get button settings
        boolean[] buttons = gpController.getButtons();
        //buttonsPanel.setButtons(buttons);
    }
    

    private void startPolling() /* Set up a timer which is activated every DELAY ms
    and polls the game pad and updates the GUI. 
    Safe since the action handler is executed in the 
    event-dispatching thread. */ {
        ActionListener pollPerformer = new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                updateData();
            }
        };

        pollTimer = new Timer(DELAY, pollPerformer);
        pollTimer.start();
    }  // end of startPolling()

    private static int extractIndex(String s, int csLen) /* Extract the index integer from the string, and check that
    it's valid, i.e. between 0 and csLen-1.
     */ {
        int index = 0;
        try {
            index = Integer.parseInt(s);
        } catch (NumberFormatException e) {
            System.out.println("Incorrect index format; using 0");
            System.exit(0);
        }

        if (index < 0 || index >= csLen) {
            System.out.println("Index not between 0 and " + (csLen - 1));
            System.exit(0);
        }
        return index;
    }  // end of extractIndex()

    private static PrintStream getPrintStream(String[] args) /* Gets the optional filename from the command line, and
    return a PrintStream for it. If no filename was supplied
    then return System.out (stdout) instead. 
     */ {
        if (args.length < 2) // no filename
        {
            return System.out;
        }

        String fnm = args[1];
        PrintStream ps = null;
        try {
            ps = new PrintStream(new FileOutputStream(fnm));
            System.out.println("Writing to file: " + fnm);
        } catch (IOException e) {
            System.out.println("Unable to open output file: " + fnm);
            ps = System.out;
        }
        return ps;
    }  // end of getPrintStream()

    private static void printDetails(Controller c, PrintStream ps) /* Report the component and rumbler information for the gpController c.
    A gpController may contain subcontrollers (e.g. the mouse), so
    recursively visit them, and report their details as well.
     */ {
        ps.println("Details for: " + c.getName() + ", "
                + c.getType() + ", " + c.getPortType());

        printComponents(c.getComponents(), ps);
        printRumblers(c.getRumblers(), ps);

        // print details about any subcontrollers
        Controller[] subCtrls = c.getControllers();
        if (subCtrls.length == 0) {
            ps.println("No subcontrollers");
        } else {
            ps.println("No. of subcontrollers: " + subCtrls.length);
            // recursively visit each subcontroller
            for (int i = 0; i < subCtrls.length; i++) {
                ps.println("---------------");
                ps.println("Subcontroller: " + i);
                printDetails(subCtrls[i], ps);
            }
        }
    } // end of printDetails()

    private static void printComponents(Component[] comps, PrintStream ps) // print info about each Component
    {
        if (comps.length == 0) {
            ps.println("No Components");
        } else {
            ps.println("Components: (" + comps.length + ")");
            for (int i = 0; i < comps.length; i++) {
                ps.println(i + ". "
                        + comps[i].getName() + ", "
                        + getIdentifierName(comps[i]) + ", "
                        + // comps[i].getIdentifier() + ", " + 
                        (comps[i].isRelative() ? "relative" : "absolute") + ", "
                        + (comps[i].isAnalog() ? "analog" : "digital") + ", "
                        + comps[i].getDeadZone());
            }
        }
    } // end of printComponents()

    private static String getIdentifierName(Component comp) /* Return the identifier name for the component.
    If the component's identifier is UNKNOWN, then change the
    returned string to "button" or "key" depending on the
    identifier type.
     */ {
        Component.Identifier id = comp.getIdentifier();
        // System.out.println("Id: " + id.getName());

        if (id == Component.Identifier.Button.UNKNOWN) {
            return "button";   // an unknown button
        } else if (id == Component.Identifier.Key.UNKNOWN) {
            return "key";   // an unknown key
        } else {
            return id.getName();
        }
    }  // end of getIdentifierName()

    private static void printRumblers(Rumbler[] rumblers, PrintStream ps) // print info about each rumbler
    {
        if (rumblers.length == 0) {
            ps.println("No Rumblers");
        } else {
            ps.println("Rumblers: (" + rumblers.length + ")");
            Component.Identifier rumblerID;
            for (int i = 0; i < rumblers.length; i++) {
                rumblerID = rumblers[i].getAxisIdentifier();
                ps.print(i + ". " + rumblers[i].getAxisName() + " on axis; ");
                if (rumblerID == null) {
                    ps.println("no name");
                } else {
                    ps.println("name: " + rumblerID.getName());
                }
            }
        }
    }  // end of printRumblers()
}
