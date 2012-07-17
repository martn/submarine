/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package submarine;

import com.martinnovak.utils.Configuration;
import com.martinnovak.utils.Util;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.WindowEvent;
import java.net.URL;
import java.text.DecimalFormat;
import javax.swing.ImageIcon;
import submarine.communication.ConnectionEvent;
import submarine.communication.ConnectionListener;
import submarine.communication.PortNotFoundException;
import submarine.communication.ReadEvent;
import submarine.communication.ReadListener;

/**
 *
 * @author Administrator
 */
public class SubmarineApp extends javax.swing.JFrame implements ReadListener, ConnectionListener {

    static Configuration config;
    static Submarine submarine;
    DecimalFormat decimalFormat = new DecimalFormat("0.0");
    DecimalFormat decimalFormat0 = new DecimalFormat("0");
    DecimalFormat decimalFormat3 = new DecimalFormat("0.00");

    /**
     * Creates new form SubmarineApp
     */
    public SubmarineApp() {
        initComponents();


        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(screenSize.width - this.getWidth(), 0);

        //this.setLocationRelativeTo(null);
        config = new Configuration();

        Util.log.logToTextArea(logTextArea);
        Util.log.logToConsole(true);

        // ============ INIT VISUALS =========================        

        try {
            URL iconUrl = this.getClass().getResource("resources/submarine-icon.png");
            ImageIcon icon = new ImageIcon(iconUrl);
            this.setIconImage(icon.getImage());
        } catch (Exception e) {
            System.out.println(e);
        }


        // ============ INIT FONTS ===========================


        Font fontGuifx = FontLoader.getFont("guifx.ttf");
        Font buttonFont = fontGuifx.deriveFont(14f);

        cameraButton11_Rec.setFont(buttonFont);
        cameraButton7_Play.setFont(buttonFont);
        cameraButton1_Left.setFont(buttonFont);
        cameraButton2_Down.setFont(buttonFont);
        cameraButton5_Right.setFont(buttonFont);
        cameraButton8_Ok.setFont(buttonFont);
        cameraButton6_Up.setFont(buttonFont);
        cameraButton16_Power.setFont(buttonFont);
        cameraButton4_Cancel.setFont(buttonFont);

        Font fontKamera = FontLoader.getFont("kameradings.ttf");
        Font buttonFontKamera = fontKamera.deriveFont(13f);

        cameraButton10_Shot.setFont(buttonFontKamera);


        // ============ INIT SUBMARINE =======================

        try {
            submarine = new Submarine(config);

            submarine.getPort().addReadListener(this);
            submarine.getPort().addConnectionListener(this);

            // Initial settings

            // stop engines and servo to 0 position
            submarine.setEngineSpeed(0, 0);
            submarine.setEngineSpeed(1, 0);
            submarine.setEngineSpeed(2, 0);
            submarine.setEngineSpeed(3, 0);
            submarine.setEngineSpeed(4, 0);

            submarine.setServoPosition(0, 0);
            submarine.setServoPosition(1, 0);

        } catch (PortNotFoundException e) {
            Util.log.write("Port Not Found");
        }
        /*
         * Controller[] ca =
         * ControllerEnvironment.getDefaultEnvironment().getControllers();
         *
         * for (int i = 0; i < ca.length; i++) {
         *
         * // Get the name of the controller Util.log.write(ca[i].getName()); }
         */
    }

    @Override
    public void ConnectionOccurred(ConnectionEvent evt) {
        Status.setText(evt.connected ? "OK" : "NO CONNECTION");
        URL iconUrl = this.getClass().getResource(evt.connected ? "resources/accept.png" : "resources/exclamation.png");
        Status.setIcon(new ImageIcon(iconUrl));
    }

    @Override
    public void ReadOccurred(ReadEvent evt) {

        jTextFieldTemperature.setText(Double.toString(getSensors().getTemperature()));

        /*
         * jSliderTemperature.setValue((int)temp);
         */


        jTextFieldAzimuth.setText(Integer.toString(getSensors().getAzimuth()));
        jTextFieldBat1.setText(decimalFormat3.format(getSensors().getLogicVoltage()));
        jTextFieldBat2.setText(decimalFormat3.format(getSensors().getServoVoltage()));

        jTextFieldDepth.setText(decimalFormat3.format(getSensors().getDepth()));
        
        jTextFieldAccelX.setText(decimalFormat0.format(getSensors().getAccelX()));
        jTextFieldAccelY.setText(decimalFormat0.format(getSensors().getAccelY()));
        jTextFieldAccelZ.setText(decimalFormat0.format(getSensors().getAccelZ()));

        jTextFieldPWR_Icam.setText(decimalFormat.format(getSensors().getPWR_Icam()));
        jTextFieldPWR_Ieng.setText(decimalFormat.format(getSensors().getPWR_Ieng()));
        jTextFieldPWR_Ilog.setText(decimalFormat.format(getSensors().getPWR_Ilog()));
        jTextFieldPWR_UINcam.setText(decimalFormat.format(getSensors().getPWR_UINcam()));
        jTextFieldPWR_UOUT4.setText(decimalFormat.format(getSensors().getPWR_UOUT4()));
        jTextFieldPWR_UOUTcam.setText(decimalFormat.format(getSensors().getPWR_UOUTcam()));
        jTextFieldPWR_Ueng.setText(decimalFormat.format(getSensors().getPWR_Ueng()));
        jTextFieldPWR_Ulog.setText(decimalFormat.format(getSensors().getPWR_Ulog()));

        /*
         * jTextFieldAccelX.setText(Integer.toString(dataSentence.getAccelX()));
         * jTextFieldAccelY.setText(Integer.toString(dataSentence.getAccelY()));
         * jTextFieldAccelZ.setText(Integer.toString(dataSentence.getAccelZ()));
         * jTextFieldI2C1.setText(Double.toString(dataSentence.getI2C1()));
         * jTextFieldI2C2.setText(Double.toString(dataSentence.getI2C2()));
         * jTextFieldI2C3.setText(Double.toString(dataSentence.getI2C3()));
         * jTextFieldI2C4.setText(Double.toString(dataSentence.getI2C4()));
         */
    }

    private Sensors getSensors() {
        return submarine.getSensors();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        Status = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        SensorsPanel = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jLabelTemperature = new javax.swing.JLabel();
        jTextFieldTemperature = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabelAzimuth = new javax.swing.JLabel();
        jTextFieldAzimuth = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabelUBattery1 = new javax.swing.JLabel();
        jTextFieldBat1 = new javax.swing.JTextField();
        jTextFieldBat2 = new javax.swing.JTextField();
        jLabelAzimuth1 = new javax.swing.JLabel();
        jTextFieldDepth = new javax.swing.JTextField();
        jTextFieldAccelX = new javax.swing.JTextField();
        jLabelAccelX = new javax.swing.JLabel();
        jTextFieldAccelY = new javax.swing.JTextField();
        jLabelAccelY = new javax.swing.JLabel();
        jTextFieldAccelZ = new javax.swing.JTextField();
        jLabelAccelZ = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        jTextFieldPWR_UINcam = new javax.swing.JTextField();
        jLabelUBattery2 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabelUBattery3 = new javax.swing.JLabel();
        jTextFieldPWR_Ilog = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        jLabelUBattery4 = new javax.swing.JLabel();
        jTextFieldPWR_Ulog = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        jLabelUBattery5 = new javax.swing.JLabel();
        jTextFieldPWR_Icam = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        jLabelUBattery6 = new javax.swing.JLabel();
        jTextFieldPWR_Ieng = new javax.swing.JTextField();
        jLabel18 = new javax.swing.JLabel();
        jLabelUBattery7 = new javax.swing.JLabel();
        jTextFieldPWR_Ueng = new javax.swing.JTextField();
        jLabel19 = new javax.swing.JLabel();
        jLabelUBattery8 = new javax.swing.JLabel();
        jTextFieldPWR_UOUTcam = new javax.swing.JTextField();
        jLabel20 = new javax.swing.JLabel();
        jLabelUBattery9 = new javax.swing.JLabel();
        jTextFieldPWR_UOUT4 = new javax.swing.JTextField();
        jLabel21 = new javax.swing.JLabel();
        MainTabPanel = new javax.swing.JTabbedPane();
        Control = new javax.swing.JPanel();
        CameraPanel = new javax.swing.JPanel();
        cameraButton12_F = new javax.swing.JButton();
        cameraButton16_Power = new javax.swing.JButton();
        cameraButton13_FPlus = new javax.swing.JButton();
        cameraButton15_ZoomMinus = new javax.swing.JButton();
        cameraButton10_Shot = new javax.swing.JButton();
        cameraButton14_ZoomPlus = new javax.swing.JButton();
        cameraButton11_Rec = new javax.swing.JButton();
        cameraButton7_Play = new javax.swing.JButton();
        cameraButton6_Up = new javax.swing.JButton();
        cameraButton1_Left = new javax.swing.JButton();
        cameraButton5_Right = new javax.swing.JButton();
        cameraButton2_Down = new javax.swing.JButton();
        cameraButton8_Ok = new javax.swing.JButton();
        cameraButton3_Menu = new javax.swing.JButton();
        cameraButton4_Cancel = new javax.swing.JButton();
        PowerPanel = new javax.swing.JPanel();
        PowerButton1 = new javax.swing.JToggleButton();
        PowerButton2 = new javax.swing.JToggleButton();
        PowerButton3 = new javax.swing.JToggleButton();
        PowerButton5 = new javax.swing.JToggleButton();
        PowerButton4 = new javax.swing.JToggleButton();
        PowerButton6 = new javax.swing.JToggleButton();
        PowerButton7 = new javax.swing.JToggleButton();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        logTextArea = new javax.swing.JTextArea();
        MenuBar = new javax.swing.JMenuBar();
        jMenuFile = new javax.swing.JMenu();
        jMenuItemClose = new javax.swing.JMenuItem();
        jMenuEdit = new javax.swing.JMenu();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Submarine");
        setLocationByPlatform(true);
        setPreferredSize(new java.awt.Dimension(500, 850));
        setResizable(false);

        jPanel1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        jPanel1.setPreferredSize(new java.awt.Dimension(412, 26));

        Status.setIcon(new javax.swing.ImageIcon(getClass().getResource("/submarine/resources/accept.png"))); // NOI18N
        Status.setText("OK");
        Status.setDisabledIcon(new javax.swing.ImageIcon(getClass().getResource("/submarine/resources/about.png"))); // NOI18N
        Status.setIconTextGap(6);
        Status.setMaximumSize(new java.awt.Dimension(336, 16));
        Status.setPreferredSize(new java.awt.Dimension(36, 20));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(Status, javax.swing.GroupLayout.PREFERRED_SIZE, 205, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(271, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(Status, javax.swing.GroupLayout.DEFAULT_SIZE, 22, Short.MAX_VALUE)
        );

        getContentPane().add(jPanel1, java.awt.BorderLayout.SOUTH);

        jPanel2.setLayout(new javax.swing.BoxLayout(jPanel2, javax.swing.BoxLayout.Y_AXIS));

        SensorsPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Sensors"));
        SensorsPanel.setPreferredSize(new java.awt.Dimension(445, 300));

        jLabelTemperature.setForeground(new java.awt.Color(0, 0, 153));
        jLabelTemperature.setText("TEMP:");

        jTextFieldTemperature.setEditable(false);
        jTextFieldTemperature.setForeground(new java.awt.Color(0, 0, 153));
        jTextFieldTemperature.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jTextFieldTemperature.setText("0");
        jTextFieldTemperature.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 51, 153)));

        jLabel3.setForeground(new java.awt.Color(0, 0, 153));
        jLabel3.setText("°C");

        jLabelAzimuth.setForeground(new java.awt.Color(0, 102, 0));
        jLabelAzimuth.setText("AZIMUTH:");

        jTextFieldAzimuth.setEditable(false);
        jTextFieldAzimuth.setForeground(new java.awt.Color(0, 102, 0));
        jTextFieldAzimuth.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jTextFieldAzimuth.setText("0");
        jTextFieldAzimuth.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 102, 0)));
        jTextFieldAzimuth.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldAzimuthActionPerformed(evt);
            }
        });

        jLabel13.setForeground(new java.awt.Color(204, 0, 51));
        jLabel13.setText("V");

        jLabel12.setForeground(new java.awt.Color(204, 0, 51));
        jLabel12.setText("V");

        jLabel6.setForeground(new java.awt.Color(204, 0, 51));
        jLabel6.setText("U BAT2:");

        jLabelUBattery1.setForeground(new java.awt.Color(204, 0, 51));
        jLabelUBattery1.setText("U BAT1:");

        jTextFieldBat1.setEditable(false);
        jTextFieldBat1.setForeground(new java.awt.Color(204, 0, 51));
        jTextFieldBat1.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jTextFieldBat1.setText("0");
        jTextFieldBat1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 0, 51)));
        jTextFieldBat1.setCaretColor(new java.awt.Color(255, 51, 51));

        jTextFieldBat2.setEditable(false);
        jTextFieldBat2.setForeground(new java.awt.Color(204, 0, 51));
        jTextFieldBat2.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jTextFieldBat2.setText("0");
        jTextFieldBat2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 0, 51)));
        jTextFieldBat2.setCaretColor(new java.awt.Color(255, 51, 51));

        jLabelAzimuth1.setForeground(new java.awt.Color(0, 102, 0));
        jLabelAzimuth1.setText("DEPTH:");

        jTextFieldDepth.setEditable(false);
        jTextFieldDepth.setForeground(new java.awt.Color(0, 102, 0));
        jTextFieldDepth.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jTextFieldDepth.setText("0");
        jTextFieldDepth.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 102, 0)));
        jTextFieldDepth.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldDepthActionPerformed(evt);
            }
        });

        jTextFieldAccelX.setEditable(false);
        jTextFieldAccelX.setForeground(new java.awt.Color(0, 0, 153));
        jTextFieldAccelX.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jTextFieldAccelX.setText("0");
        jTextFieldAccelX.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 51, 153)));

        jLabelAccelX.setForeground(new java.awt.Color(0, 0, 153));
        jLabelAccelX.setText("ACCEL X:");

        jTextFieldAccelY.setEditable(false);
        jTextFieldAccelY.setForeground(new java.awt.Color(0, 0, 153));
        jTextFieldAccelY.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jTextFieldAccelY.setText("0");
        jTextFieldAccelY.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 51, 153)));
        jTextFieldAccelY.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldAccelYActionPerformed(evt);
            }
        });

        jLabelAccelY.setForeground(new java.awt.Color(0, 0, 153));
        jLabelAccelY.setText("ACCEL Y:");

        jTextFieldAccelZ.setEditable(false);
        jTextFieldAccelZ.setForeground(new java.awt.Color(0, 0, 153));
        jTextFieldAccelZ.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jTextFieldAccelZ.setText("0");
        jTextFieldAccelZ.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 51, 153)));

        jLabelAccelZ.setForeground(new java.awt.Color(0, 0, 153));
        jLabelAccelZ.setText("ACCEL Z:");

        jLabel4.setForeground(new java.awt.Color(0, 102, 0));
        jLabel4.setText("°");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabelUBattery1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel6))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextFieldBat1, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextFieldBat2, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel12)
                            .addComponent(jLabel13))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                                .addComponent(jLabelAccelX)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTextFieldAccelX, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                                .addComponent(jLabelAccelY)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTextFieldAccelY, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                                .addComponent(jLabelAccelZ)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTextFieldAccelZ, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(0, 5, Short.MAX_VALUE)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                                .addComponent(jLabelTemperature)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTextFieldTemperature, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel3)
                                .addGap(50, 50, 50)
                                .addComponent(jLabelAzimuth))
                            .addComponent(jLabelAzimuth1, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addGap(4, 4, 4)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jTextFieldDepth, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextFieldAzimuth, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(3, 3, 3)
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 5, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jTextFieldTemperature, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabelTemperature)
                        .addComponent(jLabel3))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextFieldAzimuth, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabelAzimuth)
                            .addComponent(jLabel4))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextFieldDepth, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabelAzimuth1))))
                .addGap(21, 21, 21)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel12)
                            .addComponent(jLabelUBattery1)
                            .addComponent(jTextFieldBat1, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel13)
                            .addComponent(jLabel6)
                            .addComponent(jTextFieldBat2, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextFieldAccelX, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabelAccelX))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextFieldAccelY, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabelAccelY))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldAccelZ, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelAccelZ))
                .addContainerGap())
        );

        jTextFieldPWR_UINcam.setEditable(false);
        jTextFieldPWR_UINcam.setForeground(new java.awt.Color(204, 0, 51));
        jTextFieldPWR_UINcam.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jTextFieldPWR_UINcam.setText("0");
        jTextFieldPWR_UINcam.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 0, 51)));
        jTextFieldPWR_UINcam.setCaretColor(new java.awt.Color(255, 51, 51));
        jTextFieldPWR_UINcam.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldPWR_UINcamActionPerformed(evt);
            }
        });

        jLabelUBattery2.setForeground(new java.awt.Color(204, 0, 51));
        jLabelUBattery2.setText("U PWB IN cam.");

        jLabel14.setForeground(new java.awt.Color(204, 0, 51));
        jLabel14.setText("V");

        jLabelUBattery3.setForeground(new java.awt.Color(204, 0, 51));
        jLabelUBattery3.setText("I PWB log.");

        jTextFieldPWR_Ilog.setEditable(false);
        jTextFieldPWR_Ilog.setForeground(new java.awt.Color(204, 0, 51));
        jTextFieldPWR_Ilog.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jTextFieldPWR_Ilog.setText("0");
        jTextFieldPWR_Ilog.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 0, 51)));
        jTextFieldPWR_Ilog.setCaretColor(new java.awt.Color(255, 51, 51));

        jLabel15.setForeground(new java.awt.Color(204, 0, 51));
        jLabel15.setText("A");

        jLabelUBattery4.setForeground(new java.awt.Color(204, 0, 51));
        jLabelUBattery4.setText("U PWB log.");

        jTextFieldPWR_Ulog.setEditable(false);
        jTextFieldPWR_Ulog.setForeground(new java.awt.Color(204, 0, 51));
        jTextFieldPWR_Ulog.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jTextFieldPWR_Ulog.setText("0");
        jTextFieldPWR_Ulog.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 0, 51)));
        jTextFieldPWR_Ulog.setCaretColor(new java.awt.Color(255, 51, 51));

        jLabel16.setForeground(new java.awt.Color(204, 0, 51));
        jLabel16.setText("V");

        jLabelUBattery5.setForeground(new java.awt.Color(204, 0, 51));
        jLabelUBattery5.setText("I PWB cam.");

        jTextFieldPWR_Icam.setEditable(false);
        jTextFieldPWR_Icam.setForeground(new java.awt.Color(204, 0, 51));
        jTextFieldPWR_Icam.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jTextFieldPWR_Icam.setText("0");
        jTextFieldPWR_Icam.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 0, 51)));
        jTextFieldPWR_Icam.setCaretColor(new java.awt.Color(255, 51, 51));

        jLabel17.setForeground(new java.awt.Color(204, 0, 51));
        jLabel17.setText("A");

        jLabelUBattery6.setForeground(new java.awt.Color(204, 0, 51));
        jLabelUBattery6.setText("I PWB eng.");

        jTextFieldPWR_Ieng.setEditable(false);
        jTextFieldPWR_Ieng.setForeground(new java.awt.Color(204, 0, 51));
        jTextFieldPWR_Ieng.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jTextFieldPWR_Ieng.setText("0");
        jTextFieldPWR_Ieng.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 0, 51)));
        jTextFieldPWR_Ieng.setCaretColor(new java.awt.Color(255, 51, 51));

        jLabel18.setForeground(new java.awt.Color(204, 0, 51));
        jLabel18.setText("A");

        jLabelUBattery7.setForeground(new java.awt.Color(204, 0, 51));
        jLabelUBattery7.setText("U PWB eng.");

        jTextFieldPWR_Ueng.setEditable(false);
        jTextFieldPWR_Ueng.setForeground(new java.awt.Color(204, 0, 51));
        jTextFieldPWR_Ueng.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jTextFieldPWR_Ueng.setText("0");
        jTextFieldPWR_Ueng.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 0, 51)));
        jTextFieldPWR_Ueng.setCaretColor(new java.awt.Color(255, 51, 51));

        jLabel19.setForeground(new java.awt.Color(204, 0, 51));
        jLabel19.setText("V");

        jLabelUBattery8.setForeground(new java.awt.Color(204, 0, 51));
        jLabelUBattery8.setText("U PWB OUT cam.");

        jTextFieldPWR_UOUTcam.setEditable(false);
        jTextFieldPWR_UOUTcam.setForeground(new java.awt.Color(204, 0, 51));
        jTextFieldPWR_UOUTcam.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jTextFieldPWR_UOUTcam.setText("0");
        jTextFieldPWR_UOUTcam.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 0, 51)));
        jTextFieldPWR_UOUTcam.setCaretColor(new java.awt.Color(255, 51, 51));

        jLabel20.setForeground(new java.awt.Color(204, 0, 51));
        jLabel20.setText("V");

        jLabelUBattery9.setForeground(new java.awt.Color(204, 0, 51));
        jLabelUBattery9.setText("U PWB OUT 4");

        jTextFieldPWR_UOUT4.setEditable(false);
        jTextFieldPWR_UOUT4.setForeground(new java.awt.Color(204, 0, 51));
        jTextFieldPWR_UOUT4.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jTextFieldPWR_UOUT4.setText("0");
        jTextFieldPWR_UOUT4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 0, 51)));
        jTextFieldPWR_UOUT4.setCaretColor(new java.awt.Color(255, 51, 51));

        jLabel21.setForeground(new java.awt.Color(204, 0, 51));
        jLabel21.setText("V");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(jLabelUBattery9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextFieldPWR_UOUT4, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel21))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabelUBattery8, javax.swing.GroupLayout.DEFAULT_SIZE, 83, Short.MAX_VALUE)
                            .addComponent(jLabelUBattery6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabelUBattery5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabelUBattery4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addComponent(jLabelUBattery3)
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addComponent(jTextFieldPWR_Ulog, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel16))
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addComponent(jTextFieldPWR_Ieng, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel18))
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel6Layout.createSequentialGroup()
                                        .addComponent(jTextFieldPWR_Ilog, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLabel15))
                                    .addGroup(jPanel6Layout.createSequentialGroup()
                                        .addComponent(jTextFieldPWR_UOUTcam, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLabel20))
                                    .addGroup(jPanel6Layout.createSequentialGroup()
                                        .addComponent(jTextFieldPWR_Icam, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLabel17))
                                    .addComponent(jTextFieldPWR_UINcam, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jTextFieldPWR_Ueng, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(0, 0, Short.MAX_VALUE)))))
                .addContainerGap())
            .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel6Layout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel6Layout.createSequentialGroup()
                            .addComponent(jLabelUBattery2, javax.swing.GroupLayout.DEFAULT_SIZE, 83, Short.MAX_VALUE)
                            .addGap(58, 58, 58)
                            .addComponent(jLabel14))
                        .addGroup(jPanel6Layout.createSequentialGroup()
                            .addComponent(jLabelUBattery7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGap(58, 58, 58)
                            .addComponent(jLabel19)))
                    .addContainerGap()))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTextFieldPWR_UINcam, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldPWR_Ilog, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelUBattery3)
                    .addComponent(jLabel15))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldPWR_Ulog, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelUBattery4)
                    .addComponent(jLabel16))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldPWR_Icam, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelUBattery5)
                    .addComponent(jLabel17))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextFieldPWR_Ueng, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldPWR_Ieng, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelUBattery6)
                    .addComponent(jLabel18))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldPWR_UOUTcam, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelUBattery8)
                    .addComponent(jLabel20))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldPWR_UOUT4, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel21)
                    .addComponent(jLabelUBattery9))
                .addContainerGap(22, Short.MAX_VALUE))
            .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel6Layout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel14)
                        .addComponent(jLabelUBattery2))
                    .addGap(92, 92, 92)
                    .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel19)
                        .addComponent(jLabelUBattery7))
                    .addContainerGap(99, Short.MAX_VALUE)))
        );

        javax.swing.GroupLayout SensorsPanelLayout = new javax.swing.GroupLayout(SensorsPanel);
        SensorsPanel.setLayout(SensorsPanelLayout);
        SensorsPanelLayout.setHorizontalGroup(
            SensorsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(SensorsPanelLayout.createSequentialGroup()
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(14, Short.MAX_VALUE))
        );
        SensorsPanelLayout.setVerticalGroup(
            SensorsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(SensorsPanelLayout.createSequentialGroup()
                .addGroup(SensorsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 43, Short.MAX_VALUE))
        );

        jPanel2.add(SensorsPanel);

        MainTabPanel.setPreferredSize(new java.awt.Dimension(412, 300));

        Control.setPreferredSize(new java.awt.Dimension(407, 200));
        Control.setLayout(new javax.swing.BoxLayout(Control, javax.swing.BoxLayout.X_AXIS));

        CameraPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Camera"));
        CameraPanel.setPreferredSize(new java.awt.Dimension(240, 180));

        cameraButton12_F.setText("F");
        cameraButton12_F.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                cameraButton12_FMousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                cameraButton12_FMouseReleased(evt);
            }
        });

        cameraButton16_Power.setText("q");
        cameraButton16_Power.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                cameraButton16_PowerMousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                cameraButton16_PowerMouseReleased(evt);
            }
        });

        cameraButton13_FPlus.setText("F+");
        cameraButton13_FPlus.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                cameraButton13_FPlusMousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                cameraButton13_FPlusMouseReleased(evt);
            }
        });

        cameraButton15_ZoomMinus.setText("-Z");
        cameraButton15_ZoomMinus.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                cameraButton15_ZoomMinusMousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                cameraButton15_ZoomMinusMouseReleased(evt);
            }
        });

        cameraButton10_Shot.setText("7");
        cameraButton10_Shot.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                cameraButton10_ShotMousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                cameraButton10_ShotMouseReleased(evt);
            }
        });

        cameraButton14_ZoomPlus.setText("Z+");
        cameraButton14_ZoomPlus.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                cameraButton14_ZoomPlusMousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                cameraButton14_ZoomPlusMouseReleased(evt);
            }
        });

        cameraButton11_Rec.setText("4");
        cameraButton11_Rec.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                cameraButton11_RecMousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                cameraButton11_RecMouseReleased(evt);
            }
        });

        cameraButton7_Play.setText("1");
        cameraButton7_Play.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                cameraButton7_PlayMousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                cameraButton7_PlayMouseReleased(evt);
            }
        });

        cameraButton6_Up.setText("W");
        cameraButton6_Up.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                cameraButton6_UpMousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                cameraButton6_UpMouseReleased(evt);
            }
        });

        cameraButton1_Left.setText("A");
        cameraButton1_Left.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                cameraButton1_LeftMousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                cameraButton1_LeftMouseReleased(evt);
            }
        });

        cameraButton5_Right.setText("D");
        cameraButton5_Right.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                cameraButton5_RightMousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                cameraButton5_RightMouseReleased(evt);
            }
        });

        cameraButton2_Down.setText("S");
        cameraButton2_Down.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                cameraButton2_DownMousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                cameraButton2_DownMouseReleased(evt);
            }
        });

        cameraButton8_Ok.setText("z");
        cameraButton8_Ok.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                cameraButton8_OkMousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                cameraButton8_OkMouseReleased(evt);
            }
        });

        cameraButton3_Menu.setText("Menu");
        cameraButton3_Menu.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                cameraButton3_MenuMousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                cameraButton3_MenuMouseReleased(evt);
            }
        });

        cameraButton4_Cancel.setText("x");
        cameraButton4_Cancel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                cameraButton4_CancelMousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                cameraButton4_CancelMouseReleased(evt);
            }
        });

        javax.swing.GroupLayout CameraPanelLayout = new javax.swing.GroupLayout(CameraPanel);
        CameraPanel.setLayout(CameraPanelLayout);
        CameraPanelLayout.setHorizontalGroup(
            CameraPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(CameraPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(CameraPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cameraButton11_Rec)
                    .addGroup(CameraPanelLayout.createSequentialGroup()
                        .addGroup(CameraPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(cameraButton3_Menu, javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, CameraPanelLayout.createSequentialGroup()
                                .addComponent(cameraButton12_F)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cameraButton16_Power))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, CameraPanelLayout.createSequentialGroup()
                                .addComponent(cameraButton15_ZoomMinus)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(CameraPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(cameraButton6_Up)
                                    .addComponent(cameraButton10_Shot))))
                        .addGap(4, 4, 4)
                        .addGroup(CameraPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cameraButton13_FPlus)
                            .addComponent(cameraButton7_Play)
                            .addComponent(cameraButton14_ZoomPlus)
                            .addComponent(cameraButton5_Right)
                            .addComponent(cameraButton4_Cancel)))
                    .addGroup(CameraPanelLayout.createSequentialGroup()
                        .addComponent(cameraButton1_Left)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(CameraPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cameraButton2_Down)
                            .addComponent(cameraButton8_Ok))))
                .addContainerGap(72, Short.MAX_VALUE))
        );

        CameraPanelLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {cameraButton10_Shot, cameraButton11_Rec, cameraButton12_F, cameraButton13_FPlus, cameraButton14_ZoomPlus, cameraButton15_ZoomMinus, cameraButton16_Power, cameraButton1_Left, cameraButton2_Down, cameraButton3_Menu, cameraButton4_Cancel, cameraButton5_Right, cameraButton6_Up, cameraButton7_Play, cameraButton8_Ok});

        CameraPanelLayout.setVerticalGroup(
            CameraPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(CameraPanelLayout.createSequentialGroup()
                .addGroup(CameraPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cameraButton12_F)
                    .addComponent(cameraButton16_Power)
                    .addComponent(cameraButton13_FPlus))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(CameraPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cameraButton15_ZoomMinus)
                    .addComponent(cameraButton10_Shot)
                    .addComponent(cameraButton14_ZoomPlus))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(CameraPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(CameraPanelLayout.createSequentialGroup()
                        .addGroup(CameraPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cameraButton11_Rec)
                            .addComponent(cameraButton7_Play))
                        .addGap(29, 29, 29))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, CameraPanelLayout.createSequentialGroup()
                        .addComponent(cameraButton6_Up)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                .addGroup(CameraPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cameraButton1_Left)
                    .addComponent(cameraButton8_Ok)
                    .addComponent(cameraButton5_Right))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cameraButton2_Down)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(CameraPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cameraButton3_Menu)
                    .addComponent(cameraButton4_Cancel))
                .addGap(17, 17, 17))
        );

        CameraPanelLayout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {cameraButton10_Shot, cameraButton11_Rec, cameraButton12_F, cameraButton13_FPlus, cameraButton14_ZoomPlus, cameraButton15_ZoomMinus, cameraButton16_Power, cameraButton1_Left, cameraButton2_Down, cameraButton3_Menu, cameraButton4_Cancel, cameraButton5_Right, cameraButton6_Up, cameraButton7_Play, cameraButton8_Ok});

        Control.add(CameraPanel);

        PowerPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Power"));
        PowerPanel.setPreferredSize(new java.awt.Dimension(150, 353));

        PowerButton1.setText("5V");
        PowerButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PowerButton1ActionPerformed(evt);
            }
        });

        PowerButton2.setText("Servo");
        PowerButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PowerButton2ActionPerformed(evt);
            }
        });

        PowerButton3.setText("Regulator");
        PowerButton3.setToolTipText("");
        PowerButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PowerButton3ActionPerformed(evt);
            }
        });

        PowerButton5.setText("5");
        PowerButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PowerButton5ActionPerformed(evt);
            }
        });

        PowerButton4.setText("12V");
        PowerButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PowerButton4ActionPerformed(evt);
            }
        });

        PowerButton6.setText("Camera");
        PowerButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PowerButton6ActionPerformed(evt);
            }
        });

        PowerButton7.setText("7");
        PowerButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PowerButton7ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout PowerPanelLayout = new javax.swing.GroupLayout(PowerPanel);
        PowerPanel.setLayout(PowerPanelLayout);
        PowerPanelLayout.setHorizontalGroup(
            PowerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PowerPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(PowerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(PowerButton4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(PowerButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(PowerButton5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(PowerButton7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(PowerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(PowerButton3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(PowerButton2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(PowerButton6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(39, Short.MAX_VALUE))
        );
        PowerPanelLayout.setVerticalGroup(
            PowerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PowerPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(PowerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(PowerButton1)
                    .addComponent(PowerButton2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(PowerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(PowerButton3)
                    .addComponent(PowerButton4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(PowerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(PowerButton5)
                    .addComponent(PowerButton6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(PowerButton7)
                .addContainerGap(132, Short.MAX_VALUE))
        );

        Control.add(PowerPanel);

        MainTabPanel.addTab("Control", Control);

        jPanel2.add(MainTabPanel);

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("Log"));
        jPanel3.setPreferredSize(new java.awt.Dimension(490, 100));
        jPanel3.setLayout(new java.awt.BorderLayout());

        logTextArea.setColumns(20);
        logTextArea.setRows(5);
        jScrollPane1.setViewportView(logTextArea);

        jPanel3.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        jPanel2.add(jPanel3);

        getContentPane().add(jPanel2, java.awt.BorderLayout.CENTER);

        jMenuFile.setText("File");

        jMenuItemClose.setText("Close");
        jMenuItemClose.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemCloseActionPerformed(evt);
            }
        });
        jMenuFile.add(jMenuItemClose);

        MenuBar.add(jMenuFile);

        jMenuEdit.setText("Edit");
        MenuBar.add(jMenuEdit);

        setJMenuBar(MenuBar);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void PowerButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PowerButton1ActionPerformed
        // TODO add your handling code here:
        submarine.setPowerConfig(1, PowerButton1.isSelected());
    }//GEN-LAST:event_PowerButton1ActionPerformed

    private void PowerButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PowerButton6ActionPerformed
        // TODO add your handling code here:
        submarine.setPowerConfig(6, PowerButton6.isSelected());
    }//GEN-LAST:event_PowerButton6ActionPerformed

    private void PowerButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PowerButton2ActionPerformed
        // TODO add your handling code here:
        submarine.setPowerConfig(2, PowerButton2.isSelected());
    }//GEN-LAST:event_PowerButton2ActionPerformed

    private void PowerButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PowerButton3ActionPerformed
        // TODO add your handling code here:
        submarine.setPowerConfig(3, PowerButton3.isSelected());
    }//GEN-LAST:event_PowerButton3ActionPerformed

    private void PowerButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PowerButton4ActionPerformed
        // TODO add your handling code here:
        submarine.setPowerConfig(4, PowerButton4.isSelected());
    }//GEN-LAST:event_PowerButton4ActionPerformed

    private void PowerButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PowerButton5ActionPerformed
        // TODO add your handling code here:
        submarine.setPowerConfig(5, PowerButton5.isSelected());
    }//GEN-LAST:event_PowerButton5ActionPerformed

    private void PowerButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PowerButton7ActionPerformed
        // TODO add your handling code here:
        submarine.setPowerConfig(7, PowerButton7.isSelected());
    }//GEN-LAST:event_PowerButton7ActionPerformed

    private void cameraButton12_FMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cameraButton12_FMousePressed
        // TODO add your handling code here:
        submarine.pushCameraButton(12);
    }//GEN-LAST:event_cameraButton12_FMousePressed

    private void cameraButton12_FMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cameraButton12_FMouseReleased
        // TODO add your handling code here:
        submarine.releaseCameraButtons();
    }//GEN-LAST:event_cameraButton12_FMouseReleased

    private void cameraButton16_PowerMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cameraButton16_PowerMousePressed
        // TODO add your handling code here:
        submarine.pushCameraButton(16);
    }//GEN-LAST:event_cameraButton16_PowerMousePressed

    private void cameraButton16_PowerMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cameraButton16_PowerMouseReleased
        // TODO add your handling code here:
        submarine.releaseCameraButtons();
    }//GEN-LAST:event_cameraButton16_PowerMouseReleased

    private void cameraButton13_FPlusMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cameraButton13_FPlusMousePressed
        // TODO add your handling code here:
        submarine.pushCameraButton(13);
    }//GEN-LAST:event_cameraButton13_FPlusMousePressed

    private void cameraButton13_FPlusMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cameraButton13_FPlusMouseReleased
        // TODO add your handling code here:
        submarine.releaseCameraButtons();
    }//GEN-LAST:event_cameraButton13_FPlusMouseReleased

    private void cameraButton15_ZoomMinusMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cameraButton15_ZoomMinusMousePressed
        // TODO add your handling code here:
        submarine.pushCameraButton(15);
    }//GEN-LAST:event_cameraButton15_ZoomMinusMousePressed

    private void cameraButton15_ZoomMinusMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cameraButton15_ZoomMinusMouseReleased
        // TODO add your handling code here:
        submarine.releaseCameraButtons();
    }//GEN-LAST:event_cameraButton15_ZoomMinusMouseReleased

    private void cameraButton10_ShotMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cameraButton10_ShotMousePressed
        // TODO add your handling code here:
        submarine.pushCameraButton(10);
    }//GEN-LAST:event_cameraButton10_ShotMousePressed

    private void cameraButton10_ShotMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cameraButton10_ShotMouseReleased
        // TODO add your handling code here:
        submarine.releaseCameraButtons();
    }//GEN-LAST:event_cameraButton10_ShotMouseReleased

    private void cameraButton14_ZoomPlusMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cameraButton14_ZoomPlusMousePressed
        // TODO add your handling code here:
        submarine.pushCameraButton(14);
    }//GEN-LAST:event_cameraButton14_ZoomPlusMousePressed

    private void cameraButton14_ZoomPlusMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cameraButton14_ZoomPlusMouseReleased
        // TODO add your handling code here:
        submarine.releaseCameraButtons();
    }//GEN-LAST:event_cameraButton14_ZoomPlusMouseReleased

    private void cameraButton11_RecMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cameraButton11_RecMousePressed
        // TODO add your handling code here:
        submarine.pushCameraButton(11);
    }//GEN-LAST:event_cameraButton11_RecMousePressed

    private void cameraButton11_RecMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cameraButton11_RecMouseReleased
        // TODO add your handling code here:
        submarine.releaseCameraButtons();
    }//GEN-LAST:event_cameraButton11_RecMouseReleased

    private void cameraButton7_PlayMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cameraButton7_PlayMousePressed
        // TODO add your handling code here:
        submarine.pushCameraButton(7);
    }//GEN-LAST:event_cameraButton7_PlayMousePressed

    private void cameraButton7_PlayMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cameraButton7_PlayMouseReleased
        // TODO add your handling code here:submarine.releaseCameraButtons();
        submarine.releaseCameraButtons();
    }//GEN-LAST:event_cameraButton7_PlayMouseReleased

    private void cameraButton6_UpMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cameraButton6_UpMousePressed
        // TODO add your handling code here:
        submarine.pushCameraButton(6);
    }//GEN-LAST:event_cameraButton6_UpMousePressed

    private void cameraButton6_UpMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cameraButton6_UpMouseReleased
        // TODO add your handling code here:
        submarine.releaseCameraButtons();
    }//GEN-LAST:event_cameraButton6_UpMouseReleased

    private void cameraButton1_LeftMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cameraButton1_LeftMousePressed
        // TODO add your handling code here:
        submarine.pushCameraButton(1);
    }//GEN-LAST:event_cameraButton1_LeftMousePressed

    private void cameraButton1_LeftMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cameraButton1_LeftMouseReleased
        // TODO add your handling code here:
        submarine.releaseCameraButtons();
    }//GEN-LAST:event_cameraButton1_LeftMouseReleased

    private void cameraButton8_OkMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cameraButton8_OkMousePressed
        // TODO add your handling code here:
        submarine.pushCameraButton(8);
    }//GEN-LAST:event_cameraButton8_OkMousePressed

    private void cameraButton8_OkMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cameraButton8_OkMouseReleased
        // TODO add your handling code here:
        submarine.releaseCameraButtons();
    }//GEN-LAST:event_cameraButton8_OkMouseReleased

    private void cameraButton5_RightMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cameraButton5_RightMousePressed
        // TODO add your handling code here:
        submarine.pushCameraButton(5);
    }//GEN-LAST:event_cameraButton5_RightMousePressed

    private void cameraButton5_RightMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cameraButton5_RightMouseReleased
        // TODO add your handling code here:
        submarine.releaseCameraButtons();
    }//GEN-LAST:event_cameraButton5_RightMouseReleased

    private void cameraButton2_DownMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cameraButton2_DownMousePressed
        // TODO add your handling code here:
        submarine.pushCameraButton(2);
    }//GEN-LAST:event_cameraButton2_DownMousePressed

    private void cameraButton2_DownMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cameraButton2_DownMouseReleased
        // TODO add your handling code here:
        submarine.releaseCameraButtons();
    }//GEN-LAST:event_cameraButton2_DownMouseReleased

    private void cameraButton3_MenuMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cameraButton3_MenuMousePressed
        // TODO add your handling code here:
        submarine.pushCameraButton(3);
    }//GEN-LAST:event_cameraButton3_MenuMousePressed

    private void cameraButton3_MenuMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cameraButton3_MenuMouseReleased
        // TODO add your handling code here:
        submarine.releaseCameraButtons();
    }//GEN-LAST:event_cameraButton3_MenuMouseReleased

    private void cameraButton4_CancelMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cameraButton4_CancelMousePressed
        // TODO add your handling code here:
        submarine.pushCameraButton(4);
    }//GEN-LAST:event_cameraButton4_CancelMousePressed

    private void cameraButton4_CancelMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cameraButton4_CancelMouseReleased
        // TODO add your handling code here:submarine.releaseCameraButtons();
        submarine.releaseCameraButtons();
    }//GEN-LAST:event_cameraButton4_CancelMouseReleased

    private void jTextFieldAzimuthActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldAzimuthActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldAzimuthActionPerformed

    private void jMenuItemCloseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemCloseActionPerformed
        // TODO add your handling code here:
        processWindowEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
    }//GEN-LAST:event_jMenuItemCloseActionPerformed

    private void jTextFieldPWR_UINcamActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldPWR_UINcamActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldPWR_UINcamActionPerformed

    private void jTextFieldDepthActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldDepthActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldDepthActionPerformed

    private void jTextFieldAccelYActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldAccelYActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldAccelYActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /*
         * Set the Nimbus look and feel
         */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /*
         * If Nimbus (introduced in Java SE 6) is not available, stay with the
         * default look and feel. For details see
         * http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(SubmarineApp.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /*
         * Create and display the form
         */
        java.awt.EventQueue.invokeLater(new Runnable() {

            @Override
            public void run() {
                new SubmarineApp().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel CameraPanel;
    private javax.swing.JPanel Control;
    private javax.swing.JTabbedPane MainTabPanel;
    private javax.swing.JMenuBar MenuBar;
    private javax.swing.JToggleButton PowerButton1;
    private javax.swing.JToggleButton PowerButton2;
    private javax.swing.JToggleButton PowerButton3;
    private javax.swing.JToggleButton PowerButton4;
    private javax.swing.JToggleButton PowerButton5;
    private javax.swing.JToggleButton PowerButton6;
    private javax.swing.JToggleButton PowerButton7;
    private javax.swing.JPanel PowerPanel;
    private javax.swing.JPanel SensorsPanel;
    private javax.swing.JLabel Status;
    private javax.swing.JButton cameraButton10_Shot;
    private javax.swing.JButton cameraButton11_Rec;
    private javax.swing.JButton cameraButton12_F;
    private javax.swing.JButton cameraButton13_FPlus;
    private javax.swing.JButton cameraButton14_ZoomPlus;
    private javax.swing.JButton cameraButton15_ZoomMinus;
    private javax.swing.JButton cameraButton16_Power;
    private javax.swing.JButton cameraButton1_Left;
    private javax.swing.JButton cameraButton2_Down;
    private javax.swing.JButton cameraButton3_Menu;
    private javax.swing.JButton cameraButton4_Cancel;
    private javax.swing.JButton cameraButton5_Right;
    private javax.swing.JButton cameraButton6_Up;
    private javax.swing.JButton cameraButton7_Play;
    private javax.swing.JButton cameraButton8_Ok;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabelAccelX;
    private javax.swing.JLabel jLabelAccelY;
    private javax.swing.JLabel jLabelAccelZ;
    private javax.swing.JLabel jLabelAzimuth;
    private javax.swing.JLabel jLabelAzimuth1;
    private javax.swing.JLabel jLabelTemperature;
    private javax.swing.JLabel jLabelUBattery1;
    private javax.swing.JLabel jLabelUBattery2;
    private javax.swing.JLabel jLabelUBattery3;
    private javax.swing.JLabel jLabelUBattery4;
    private javax.swing.JLabel jLabelUBattery5;
    private javax.swing.JLabel jLabelUBattery6;
    private javax.swing.JLabel jLabelUBattery7;
    private javax.swing.JLabel jLabelUBattery8;
    private javax.swing.JLabel jLabelUBattery9;
    private javax.swing.JMenu jMenuEdit;
    private javax.swing.JMenu jMenuFile;
    private javax.swing.JMenuItem jMenuItemClose;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField jTextFieldAccelX;
    private javax.swing.JTextField jTextFieldAccelY;
    private javax.swing.JTextField jTextFieldAccelZ;
    private javax.swing.JTextField jTextFieldAzimuth;
    private javax.swing.JTextField jTextFieldBat1;
    private javax.swing.JTextField jTextFieldBat2;
    private javax.swing.JTextField jTextFieldDepth;
    private javax.swing.JTextField jTextFieldPWR_Icam;
    private javax.swing.JTextField jTextFieldPWR_Ieng;
    private javax.swing.JTextField jTextFieldPWR_Ilog;
    private javax.swing.JTextField jTextFieldPWR_UINcam;
    private javax.swing.JTextField jTextFieldPWR_UOUT4;
    private javax.swing.JTextField jTextFieldPWR_UOUTcam;
    private javax.swing.JTextField jTextFieldPWR_Ueng;
    private javax.swing.JTextField jTextFieldPWR_Ulog;
    private javax.swing.JTextField jTextFieldTemperature;
    private javax.swing.JTextArea logTextArea;
    // End of variables declaration//GEN-END:variables
}
