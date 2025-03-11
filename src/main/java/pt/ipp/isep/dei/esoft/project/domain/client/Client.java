package pt.ipp.isep.dei.esoft.project.domain.client;

import pt.ipp.isep.dei.esoft.project.domain.client.notifications.Message;
import pt.ipp.isep.dei.esoft.project.domain.client.notifications.Notification;
import pt.ipp.isep.dei.esoft.project.domain.user.User;
import pt.ipp.isep.dei.esoft.project.util.error.OperationResult;

/**
 * The type Client.
 */
public class Client extends User {

    private final Notification notification;


    /**
     * Instantiates a new Client.
     */
    public Client() {
        super();
        this.notification = new Notification();
    }

    /**
     * Gets notification.
     *
     * @return the notification
     */
    public Notification getNotification() {
        return notification;
    }

    /**
     * Add message operation result.
     *
     * @param message the message
     * @return the operation result
     */
    public OperationResult addMessage(Message message) {
        return notification.addMessage(message);
    }

    /**
     * Method used to check if a client is valid
     *
     * @return true if the client is valid or false if not
     */
    public OperationResult isValid() {
        if (this.getName().isEmpty())
            return OperationResult.failed("Error - Client - Name cannot be empty!");

        if (this.getEmail() == null)
            return OperationResult.failed("Error - Client - Email cannot be null!");

        if (!isPassportNumberValid(this.getPassportNumber()).success())
            return isPassportNumberValid(this.getPassportNumber());

        if (!isPhoneNumberValid(this.getPhoneNumber()).success())
            return isPhoneNumberValid(this.getPhoneNumber());

        if (!isTaxNumberValid(this.getTaxNumber()).success())
            return isTaxNumberValid(this.getTaxNumber());

        return OperationResult.successfull();
    }

    /**
     * Is legacy valid operation result.
     *
     * @return the operation result
     */
    public OperationResult isLegacyValid() {
        if (this.getName().isEmpty())
            return OperationResult.failed("Error - Client - Name cannot be empty!");

        if (this.getEmail() == null)
            return OperationResult.failed("Error - Client - Email cannot be null!");

        if (!isPassportNumberValid(this.getPassportNumber()).success())
            return isPassportNumberValid(this.getPassportNumber());

        if (isPhoneNumberValid(this.getPhoneNumber()).success())
            return isPhoneNumberValid(this.getPhoneNumber());

        if (isTaxNumberValid(this.getTaxNumber()).success())
            return isTaxNumberValid(this.getTaxNumber());

        return OperationResult.successfull();
    }

    /**
     * Method used to check if one object is equal to another
     *
     * @param obj object to be compared to the initial object
     * @return true if the objects are the same or false if the objects are different
     */
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (obj.getClass() != getClass()) return false;
        if (obj == this) return true;

        Client other = (Client) obj;
        return (getName().equals(other.getName()) && getEmail().equals(other.getEmail()) && getPassportNumber() == other.getPassportNumber() && getPhoneNumber() == other.getPhoneNumber() && getTaxNumber() == other.getTaxNumber());
    }

    /**
     * Remove message operation result.
     *
     * @param id the id
     * @return the operation result
     */
    public OperationResult removeMessage(int id) {
        return notification.removeMessage(id);
    }
}
