package q1;

import java.io.*;
import java.net.Socket;
import java.util.Random;

/**
 * This handles the connection to the server (from the client side)
 *
 * @author yossef
 * @version 13.06.19
 */
public class ClientConnection {

    // this is a hack in order to notify the server about leaving the connection.
    // this can be any object that the server doesn't expect to receive
    private final Random RANDOM_OBJECT = new Random();

    private Socket socket;
    private ObjectOutputStream out;
    private ObjectInputStream in;

    /**
     * constructs a new connection with the server.
     * the connection is held, and then we construct a different thread to listen for incoming messages from the server
     *
     * @param client the client that want to connect
     * @param host   the host name to connect to
     * @param port   the port to connect to
     * @throws IOException in case there was something wrong with the connection
     */
    public ClientConnection(Client client, String host, int port) throws IOException {
        socket = new Socket(host, port);
        out = new ObjectOutputStream(socket.getOutputStream());
        in = new ObjectInputStream(socket.getInputStream());

        Thread messagesListener = new MessageThread(client, in);
        messagesListener.start();
    }

    /**
     * kills the connection and notifies the server about it
     */
    public void killConnection() {
        try {
            out.writeObject(RANDOM_OBJECT);
        }
        catch (IOException e) {
        }
    }

    /**
     * send a message to the server
     *
     * @param message the message to send
     */
    public void sendMessage(Message message) {
        try {
            out.writeObject(message);
        }
        catch (IOException e) {
        }
    }


}
