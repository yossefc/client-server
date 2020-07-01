package q1;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.ConnectException;
import java.net.UnknownHostException;

/**
 * this is a client class
 *
 * @author yossef
 */
public class Client {

    private LoginGui login;
    private ClientGui gui;
    private ClientConnection connection;
    private String userName;

    /**
     * starts the client
     */
    private void start() {
        login = new LoginGui();
        login.addLoginEventListeners(new LoginActionListener());
        login.showGui();
    }

    /**
     * displays a message to the client
     *
     * @param message the message to display
     */
    public void displayMessage(Message message) {
        gui.displayMessage(message.toString());
    }

    /**
     * handles when another user just logged in
     *
     * @param list the list of all users
     */
    public void userLoggedIn(UsersList list) {
        gui.displayMessage("\t" + list.getLastUserToJoin() + " joined the chat");
        gui.displayLoggedInUsers(list.getUsersList());
    }

    /**
     * handles when another user just logged out
     *
     * @param list the list of all users
     */
    public void userLoggedOut(UsersList list) {
        gui.displayMessage("\t" + list.getLastUserToLeave() + " left the chat");
        gui.displayLoggedInUsers(list.getUsersList());
    }

    /**
     * is case the server disconnected
     */
    public void serverDisconnected() {
        gui.showPopupMessage("Can't connect to server. try again");
        logout();
    }

    /**
     * kills the client
     */
    private void logout() {
        connection.killConnection();
        gui.kill();
        start();
    }

    /**
     * listens to actions in the login screen
     */
    private class LoginActionListener implements ActionListener {

        /**
         * a button hit on the login screen
         *
         * @param e the event
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            Object source = e.getSource();
            // start screen
            if (source == login.getStartChatButton()) {
                login.switchToLoginForm();
            }

            // login form
            else if (source == login.getConnectButton()) {
                String errMessage = login.isFormValid();
                if (errMessage != null) {
                    login.showPopupMessage(errMessage);
                }
                else {
                    // make connection
                    ConnectionInfo info = login.getLoginInfo();
                    try {
                        connection = new ClientConnection(Client.this, info.getHost(), info.getPort());
                    }
                    catch (ConnectException | UnknownHostException ex) {
                        login.showPopupMessage("No response from that server. try a different one");
                        return;
                    }
                    catch (IOException ex) {
                    }

                    userName = info.getUserName();

                    // go to chat gui
                    login.close();
                    gui = new ClientGui(userName);
                    gui.showGui();
                    gui.addSendActionListeners(new sendMessageActionListener());
                    gui.addLogoutActionListeners(new LogoutActionListener());

                    // send username
                    connection.sendMessage(new Message(userName, "")); // the content does not matter
                }
            }
        }
    }

    /**
     * handles sending message events
     */
    private class sendMessageActionListener implements ActionListener {

        /**
         * a button was hit to send message, or enter key was pressed
         *
         * @param e the event
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            String message = gui.consumeMessage();
            connection.sendMessage(new Message(userName, message));
        }
    }

    /**
     * this handles logout action
     */
    private class LogoutActionListener implements ActionListener {

        /**
         * logout button was hit
         *
         * @param e the event
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            logout();
        }
    }

    /**
     * this is the main runner of the client
     *
     * @param args command line arguments. this is not used in the client side
     */
    public static void main(String[] args) {
        Client client = new Client();
        client.start();
    }
}
