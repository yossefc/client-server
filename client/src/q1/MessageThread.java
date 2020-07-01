package q1;

import java.io.IOException;
import java.io.ObjectInputStream;

import q1.Client;

/**
 * This class listens to incoming messages from the server and deals with them
 *
 
 */
public class MessageThread extends Thread {

    private Client client;
    private ObjectInputStream in;

    /**
     * constructs a new message thread
     *
     * @param client the client that is connected to the server
     * @param in     input stream from the socket
     */
    public MessageThread(Client client, ObjectInputStream in) {
        this.client = client;
        this.in = in;
    }

    /**
     * listens to input from the server and deals with it
     */
    @Override
    public void run() {
        try {
            Object input = in.readObject();
            while (true) {
                if (input instanceof Message) { // normal message
                    client.displayMessage((Message) input);
                }
                else if (input instanceof UsersList) { // the server sent a list of users to update the client
                    if (((UsersList) input).getUserStatus() == UsersList.ActionStatus.USER_LEFT) {
                        client.userLoggedOut((UsersList) input);
                    }
                    else { // user joined
                        client.userLoggedIn((UsersList) input);
                    }
                }
                input = in.readObject();
            }
        }
        catch (IOException | ClassNotFoundException e) {
            client.serverDisconnected();
        }
    }
}
