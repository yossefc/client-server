package q1;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * this is the main gui of the app
 *
 * @author yossef
 * @version 13.06.19
 */
public class ClientGui extends JFrame {

    private final int PADDING = 10;
    private final int USERS_LIST_WIDTH = 15;
    private final int CONVERSATION_WIDTH = 35;
    private final int CENTER_PANEL_SIZE = 600;
    private final Border BORDER = BorderFactory.createLineBorder(Color.BLACK);

    private JTextArea usersListArea;
    private JTextArea conversationArea;
    private JTextField inputMessageField;
    private JButton sendButton;
    private JLabel title;
    private JButton logoutButton;

    /**
     * constructs a new gui instance.
     *
     * @param userName the name of the client to be displayed on top of the screen
     */
    public ClientGui(String userName) {
        usersListArea = new JTextArea();
        conversationArea = new JTextArea();
        inputMessageField = new JTextField();
        sendButton = new JButton("Send");
        title = new JLabel("Logged in as " + userName);

        // display settings
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout(PADDING, PADDING));
        mainPanel.setBorder(new EmptyBorder(PADDING, PADDING, PADDING, PADDING));

        // top panel
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BorderLayout());
        topPanel.add(title, BorderLayout.CENTER);
        logoutButton = new JButton("Logout");
        topPanel.add(logoutButton, BorderLayout.EAST);
        mainPanel.add(topPanel, BorderLayout.NORTH);

        // center panel
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BorderLayout(PADDING, 0));
        centerPanel.setPreferredSize(new Dimension(CENTER_PANEL_SIZE, CENTER_PANEL_SIZE));

        // make scrollable
        usersListArea.setLineWrap(true);
        usersListArea.setColumns(USERS_LIST_WIDTH);
        usersListArea.setBorder(BORDER);
        usersListArea.setEditable(false);
        JScrollPane scrollListArea = new JScrollPane(usersListArea);
        scrollListArea.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollListArea.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        centerPanel.add(scrollListArea, BorderLayout.EAST);

        conversationArea.setColumns(CONVERSATION_WIDTH);
        conversationArea.setBorder(BORDER);
        conversationArea.setLineWrap(true);
        conversationArea.setEditable(false);
        JScrollPane chatScroll = new JScrollPane(conversationArea);
        chatScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        chatScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        centerPanel.add(chatScroll, BorderLayout.CENTER);
        mainPanel.add(centerPanel, BorderLayout.CENTER);

        // bottom panel
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new BorderLayout());
        inputMessageField.setBorder(BORDER);
        bottomPanel.add(inputMessageField, BorderLayout.CENTER);
        bottomPanel.add(sendButton, BorderLayout.EAST);
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);
        add(mainPanel);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null); // set to the middle of the screen
    }

    /**
     * makes the gui visible
     */
    public void showGui() {
        setVisible(true);
    }

    /**
     * gets the user's input and flushes the text field
     *
     * @return the user's message
     */
    public String consumeMessage() {
        String message = inputMessageField.getText();
        inputMessageField.setText("");
        return message;
    }

    /**
     * adds action listeners to the send button and text field
     *
     * @param listener the listener to add
     */
    public void addSendActionListeners(ActionListener listener) {
        sendButton.addActionListener(listener);
        inputMessageField.addActionListener(listener);
    }

    /**
     * adds a chat message to the conversation area
     *
     * @param message the message to display
     */
    public void displayMessage(String message) {
        conversationArea.append(message + "\n");
    }

    /**
     * displays a list of users on the screen
     *
     * @param loggedInUsers a list of usernames
     */
    public void displayLoggedInUsers(ArrayList<String> loggedInUsers) {
        usersListArea.setText(""); // clear the list
        for (String user : loggedInUsers) {
            usersListArea.append(user + "\n");
        }
    }

    /**
     * display a pop up message
     *
     * @param message the message to display
     */
    public void showPopupMessage(String message) {
        JOptionPane.showMessageDialog(this, message);
    }

    /**
     * kills the gui
     */
    public void kill() {
        setVisible(false);
        dispose(); //Destroy the JFrame object
    }

    /**
     * add an action listener to the logout button
     *
     * @param listener the listener to add
     */
    public void addLogoutActionListeners(ActionListener listener) {
        logoutButton.addActionListener(listener);
    }
}
