package pt.ipp.isep.dei.esoft.project.domain.client.notifications;

import pt.ipp.isep.dei.esoft.project.domain.user.User;
import pt.ipp.isep.dei.esoft.project.util.error.OperationResult;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * The type Message.
 */
public class Message implements Serializable {

    private int id;
    private User author;
    private LocalDateTime date;
    private String message;

    /**
     * Instantiates a new Message.
     */
    public Message() {

    }

    /**
     * Gets id.
     *
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * Gets date.
     *
     * @return the date
     */
    public LocalDateTime getDate() {
        return date;
    }

    /**
     * Gets message.
     *
     * @return the message
     */
    public String getMessage() {
        return message;
    }

    /**
     * Gets author.
     *
     * @return the author
     */
    public User getAuthor() {
        return author;
    }

    /**
     * Sets author.
     *
     * @param author the author
     */
    public void setAuthor(User author) {
        this.author = author;
    }

    /**
     * Sets date.
     *
     * @param date the date
     */
    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    /**
     * Sets id.
     *
     * @param id the id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Sets message.
     *
     * @param message the message
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * Is valid operation result.
     *
     * @return the operation result
     */
    public OperationResult isValid() {
        if (author == null)
            return OperationResult.failed("Error - Message - Author cannot be null!");

        else if (!author.isValid().success())
            return OperationResult.failed(author.isValid().getErrorMessage() + "\nError - Message  - Author must be a valid user!");

        if (message == null || message.isEmpty())
            return OperationResult.failed("Error - Message - Message cannot be null or empty!");

        if (date == null)
            return OperationResult.failed("Error - Message - Date cannot be null!");

        return OperationResult.successfull();
    }
}