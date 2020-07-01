package q1;

import java.io.Serializable;

public class Message implements Serializable {

    private String sender;
    private String content;

    public Message(String from, String content) {
        sender = from;
        this.content = content;
    }

    public String getSender() {
        return sender;
    }

    public String toString() {
        return "@" + sender + ": " + content;
    }
}
