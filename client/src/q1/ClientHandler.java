package q1;

import java.io.*;
import java.net.Socket;

/**
 * This is a thread that handles a connection with a client (from the server side)
 *
 
 */
public class ClientHandler extends Thread {

    private Server server;
    private ObjectInputStream in;
    private ObjectOutputStream out;
    private String clientName;

    /**
     * constructs a cliend handler
     *
     * @param socket the socket connection of the client
     * @param server the main server
     */
    public ClientHandler(Socket socket, Server server) {
        this.server = server;
        try {
            in = new ObjectInputStream(socket.getInputStream());
            out = new ObjectOutputStream(socket.getOutputStream());
        }
        catch (IOException e) {
        }
    }

    /**
     * this is the main runner of the program. it will listen to incoming messages from the client.
     * the first thing that must be sent is the use's name, and then other messages
     */
    @Override
    public void run() {
        try {
            Message message = (Message) in.readObject();

            // first get the client name
            clientName = message.getSender();
            server.notifyClientJoined(clientName);

            // start the chat
            while (true) {
                try {
                    message = (Message) in.readObject();
                    server.sendToAll(message);
                }
                catch (EOFException e) {
                    // close the connection
                    in.close();
                    out.close();
                    break;
                }
            }
        }
        catch (ClassNotFoundException | IOException e) {
        }
        finally { // this must execute in order to finnish the connection
            // the client logged out
            server.deleteClient(this);
        }
    }

    /**
     * send a message to the client
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

    /**
     * updates the client with a fresh list of logged in users.
     *
     * @param list the list of the users
     */
    public void updateUsersList(UsersList list) {
        try {
            out.writeObject(new UsersList(list)); // it is important to create a new list because the ObjectOutputStream doesn't send updated objects
        }
        catch (IOException e) {
        }
    }

    /**
     * @return the name of this client
     */
    public String getClientName() {
        return clientName;
    }
}
