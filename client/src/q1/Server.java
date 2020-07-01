package q1;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

/**
 * This is the server of the group chat room application.
 * The server can handle multiple clients
 *
 
 */
public class Server extends Thread {

    private static final int DEFAULT_PORT = 7777;

    private ServerSocket serverSocket;
    private ArrayList<ClientHandler> clients;
    private UsersList usersList;
    private String[] args;

    /**
     * Construcs a new Server
     *
     * @param args you can pass the port as a command line argument. if a port is not passed or there is a problem with the port,
     *             the default port will be 7777
     */
    public Server(String[] args) {
        clients = new ArrayList<>();
        usersList = new UsersList();
        this.args = args;
    }

    /**
     * starts the server
     */
    @Override
    public void start() {
        try {
            serverSocket = new ServerSocket(getPort());
            System.out.println("Server started");

            while (true) { // the server will keep listening for new clients forever
                Socket socket = serverSocket.accept();
                ClientHandler client = new ClientHandler(socket, this);
                clients.add(client);
                client.start();
            }
        }
        catch (IOException e) {
        }
    }

    /**
     * gets the port from the command lind.
     * if there is a problem with the port (or if there isn't any command line argument),
     * the default port will be used
     *
     * @return the port to run the server on
     */
    private Integer getPort() {
        if (args.length > 0) {
            Integer port = AppUtils.getPort(args[0]);
            if (port != null) {
                return port;
            }
            System.out.print("Invalid port. ");
        }
        System.out.println("Running on port " + DEFAULT_PORT);
        return DEFAULT_PORT;
    }

    /**
     * sends message to all clients
     *
     * @param message the message to send
     */
    public synchronized void sendToAll(Message message) {
        for (ClientHandler client : clients) {
            client.sendMessage(message);
        }
    }

    /**
     * removes a client and notifies all the other clients that the cliend left
     *
     * @param clientHandler the client to remove
     */
    public synchronized void deleteClient(ClientHandler clientHandler) {
        clients.remove(clientHandler);
        usersList.removeUser(clientHandler.getClientName());
        for (ClientHandler client : clients) {
            client.updateUsersList(usersList);
        }
    }

    /**
     * handles when a client joins the chat, and notifies all the clients that he joined
     *
     * @param clientName the name of the client that joined
     */
    public synchronized void notifyClientJoined(String clientName) {
        usersList.addUser(clientName);
        for (ClientHandler client : clients) {
            client.updateUsersList(usersList);
        }
    }

    /**
     * this is the main runned of the server
     *
     * @param args only one argument is relevant - the port number. the port must be valid, otherwise a default port will be used
     */
    public static void main(String[] args) {
        Server server = new Server(args);
        server.start();
    }
}
