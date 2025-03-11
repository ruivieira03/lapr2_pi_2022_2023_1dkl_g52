package pt.ipp.isep.dei.esoft.project.domain.client.notifications;

import pt.ipp.isep.dei.esoft.project.util.error.OperationResult;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * The type Notification.
 */
public class Notification implements Serializable {
    private List<Message> messages;
    private int id_counter;

    /**
     * Instantiates a new Notification.
     */
    public Notification() {
        this.messages = new ArrayList<>();
    }

    /**
     * Add message operation result.
     *
     * @param message the message
     * @return the operation result
     */
    public OperationResult addMessage(Message message) {
        if (message == null)
            return OperationResult.failed("Error - Notification - Message cannot be null!");

        message.setId(id_counter);
        id_counter++;

        OperationResult localvalidation = message.isValid();
        if (!localvalidation.success())
            return OperationResult.failed(localvalidation.getErrorMessage() + "\nError - Notification - Message failed local validation!");


        messages.add(message);
        return OperationResult.successfull();
    }

    /**
     * Add visit request response operation result.
     *
     * @param visitRequestReponse the visit request reponse
     * @return the operation result
     */
    public OperationResult addVisitRequestResponse(VisitRequestReponse visitRequestReponse) {
        messages.add(visitRequestReponse);
        return OperationResult.successfull();
    }

    /**
     * Gets messages.
     *
     * @return the messages
     */
    public List<Message> getMessages() {
        return new ArrayList<>(messages);
    }

    /**
     * Sets messages.
     *
     * @param messages the messages
     */
    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }

    /**
     * Remove message operation result.
     *
     * @param id the id
     * @return the operation result
     */
    public OperationResult removeMessage(int id) {
        for (Message message : messages) {
            if (message.getId() == id) {
                messages.remove(message);
                return OperationResult.successfull();
            }
        }
        return OperationResult.failed("Error - Notification - Notification does not contain Message[" + id + "]!");
    }

    public String toString(){
        return String.format("%s", messages);
    }
}
