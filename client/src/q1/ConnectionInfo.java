package q1;

/**
 * this is the information from the login form of the user, about the connection
 *
 * 
 */
public class ConnectionInfo {

    public static final int DEFAULT_PORT = 7777;
    public static final String DEFAULT_HOST = "localhost";

    private String userName;
    private String host;
    private int port;

    /**
     * construcs a new connection info
     *
     * @param userName the name of the user
     * @param host     the host name (can be empty)
     * @param port     the port to connect (can be empty)
     */
    public ConnectionInfo(String userName, String host, String port) {
        this.userName = userName.trim();
        this.host = AppUtils.isEmptyOrWhite(host) ? DEFAULT_HOST : host;
        // the port
        if (AppUtils.isEmptyOrWhite(port) || AppUtils.getPort(port) == null) {
            this.port = DEFAULT_PORT;
        }
        else {
            this.port = AppUtils.getPort(port);
        }
    }

    /**
     * @return the user name
     */
    public String getUserName() {
        return userName;
    }

    /**
     * @return the host
     */
    public String getHost() {
        return host;
    }

    /**
     * @return the port name  (as a string)
     */
    public int getPort() {
        return port;
    }
}
