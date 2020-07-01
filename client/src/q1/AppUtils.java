package q1;

/**
 * This is a utils class, that holds some common methods
 *
 * @author yossef
 * @version 
 */
public class AppUtils {

    public static final int MAX_PORT = 65535;
    public static final int MIN_PORT = 0;

    /**
     * checks if a string is empty or all white spaces
     *
     * @param s the string to check
     * @return true if the string is either all whites or an empty string
     */
    public static boolean isEmptyOrWhite(String s) {
        return s.trim().isEmpty();
    }

    /**
     * this validates a port number given as a string, and returns the port as an Integer
     *
     * @param portAsString the port as a string
     * @return the port as Integer in case it is a valid port, or null otherwise
     */
    public static Integer getPort(String portAsString) {
        try {
            int port = Integer.parseInt(portAsString);
            if (port <= AppUtils.MAX_PORT && port >= AppUtils.MIN_PORT)
                return port;
        }
        catch (NumberFormatException ex) {
        }
        return null;
    }
}
