package q1;

import javax.swing.*;
import java.awt.event.ActionListener;

/**
 * this is the GUI for the login screen
 *
 
 */
public class LoginGui extends JFrame {

    private final int FORM_PADDING = 10;

    // start screen
    private JPanel startPanel;
    private JButton startChatButton;

    // form
    private JPanel formPanel;
    private JLabel userNameLabel;
    private JTextField userNameInput;
    private JLabel hostLabel;
    private JTextField hostInput;
    private JLabel portLabel;
    private JTextField portInput;
    private JButton connectButton;

    /**
     * constructs a new login screen, but displays only half of it
     */
    public LoginGui() {
        // start screen
        startChatButton = new JButton("Start Chat");
        startPanel = new JPanel();
        startPanel.setBorder(BorderFactory.createEmptyBorder(FORM_PADDING, FORM_PADDING, FORM_PADDING, FORM_PADDING));
        startPanel.add(startChatButton);
        add(startPanel);

        // form screen
        formPanel = new JPanel();
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
        formPanel.setBorder(BorderFactory.createEmptyBorder(FORM_PADDING, FORM_PADDING, FORM_PADDING, FORM_PADDING));
        userNameLabel = new JLabel("User Name");
        userNameInput = new JTextField();
        formPanel.add(userNameLabel);
        formPanel.add(userNameInput);

        hostLabel = new JLabel("Server Name (Default is " + ConnectionInfo.DEFAULT_HOST + ")");
        hostInput = new JTextField();
        formPanel.add(hostLabel);
        formPanel.add(hostInput);

        portLabel = new JLabel("Port (Default is " + ConnectionInfo.DEFAULT_PORT + ")");
        portInput = new JTextField();
        formPanel.add(portLabel);
        formPanel.add(portInput);

        connectButton = new JButton("Connect");
        formPanel.add(connectButton);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null); // set to the middle of the screen
    }

    /**
     * make the gui visible
     */
    public void showGui() {
        setVisible(true);
    }

    /**
     * @return the "start" button
     */
    public JButton getStartChatButton() {
        return startChatButton;
    }

    /**
     * switches the display from the "start" screen to the login details
     */
    public void switchToLoginForm() {
        remove(startPanel);
        add(formPanel);
        pack();
    }

    /**
     * @return the "connect" button
     */
    public JButton getConnectButton() {
        return connectButton;
    }

    /**
     * returns the login info from the login form.
     * note - this does not validate the form
     *
     * @return the login details
     */
    public ConnectionInfo getLoginInfo() {
        return new ConnectionInfo(userNameInput.getText(), hostInput.getText(), portInput.getText());
    }

    /**
     * adds event listener for the "start" and "connect" buttons
     *
     * @param listener
     */
    public void addLoginEventListeners(ActionListener listener) {
        startChatButton.addActionListener(listener);
        connectButton.addActionListener(listener);
    }

    /**
     * displays a message for the user
     *
     * @param message the message to display
     */
    public void showPopupMessage(String message) {
        JOptionPane.showMessageDialog(this, message);
    }

    /**
     * checks of the form is valid
     *
     * @return null if the form is valid - else it will return the error message
     */
    public String isFormValid() {
        String err = null;
        if (!isValidUserName()) {
            err = "You must enter a valid username";
        }
        if (!isValidPort()) {
            err = "Port is invalid";
        }
        return err;
    }

    /**
     * checks if thr port is valid.
     * note - also empty port is valid!
     *
     * @return true if the port is valid, false otherwise
     */
    private boolean isValidPort() {
        String port = portInput.getText();
        if (AppUtils.isEmptyOrWhite(port))
            return true;
        if (AppUtils.getPort(port) != null) // returns null in case of error
            return true;
        return false;
    }

    /**
     * @return true if the username is valid, false otherwise
     */
    private boolean isValidUserName() {
        return !AppUtils.isEmptyOrWhite(userNameInput.getText());
    }

    /**
     * closes this screen
     */
    public void close() {
        setVisible(false);
        dispose(); //Destroy the JFrame object
    }
}
