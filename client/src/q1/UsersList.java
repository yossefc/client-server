package q1;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * this is the list of all users that are joined in the chat.
 * this class also keeps the state of the last action that happened with the users
 *

 */
public class UsersList implements Serializable {

    /**
     * the state of the last action (login or logout)
     */
    public enum ActionStatus {
        USER_LEFT, USER_JOINED
    }

    private String lastUserToLeave;
    private String lastUserToJoin;
    private ArrayList<String> users;
    private ActionStatus lastActionStatus;

    /**
     * constructs a new list
     */
    public UsersList() {
        users = new ArrayList<>();
    }

    /**
     * this is a copy constructor of another user list
     *
     * @param list the list to copy
     */
    public UsersList(UsersList list) {
        lastUserToLeave = list.lastUserToLeave;
        lastUserToJoin = list.lastUserToJoin;
        users = new ArrayList<>(list.users);
        lastActionStatus = list.lastActionStatus;
    }

    /**
     * adds a user to the list
     *
     * @param user the name of the user to add
     */
    public synchronized void addUser(String user) {
        lastActionStatus = ActionStatus.USER_JOINED;
        users.add(user);
        lastUserToJoin = new String(user);
    }

    /**
     * removers a user from the list
     *
     * @param user the user name to remove
     */
    public synchronized void removeUser(String user) {
        lastActionStatus = ActionStatus.USER_LEFT;
        users.remove(user);
        lastUserToLeave = new String(user);
    }

    /**
     * @return the last action that happened
     */
    public ActionStatus getUserStatus() {
        return lastActionStatus;
    }

    /**
     * @return the list itself
     */
    public ArrayList<String> getUsersList() {
        return users;
    }

    /**
     * @return the last user that logged out
     */
    public String getLastUserToLeave() {
        return lastUserToLeave;
    }

    /**
     * @return the last user that logged in
     */
    public String getLastUserToJoin() {
        return lastUserToJoin;
    }


}
