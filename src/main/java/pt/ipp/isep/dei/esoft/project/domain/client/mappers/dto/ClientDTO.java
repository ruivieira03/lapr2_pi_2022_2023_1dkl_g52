package pt.ipp.isep.dei.esoft.project.domain.client.mappers.dto;

import pt.ipp.isep.dei.esoft.project.domain.client.notifications.mappers.dto.NotificationDTO;
import pt.ipp.isep.dei.esoft.project.domain.user.mappers.dto.UserDTO;

/**
 * The type Client dto.
 */
public class ClientDTO extends UserDTO {
    /**
     * The Notification dto.
     */
    public NotificationDTO notificationDTO;

    /**
     * Instantiates a new Client dto.
     *
     * @param name           the name
     * @param passportNumber the passport number
     * @param phoneNumber    the phone number
     * @param taxNumber      the tax number
     * @param address        the address
     * @param email          the email
     */
    public ClientDTO(String name, int passportNumber, String phoneNumber, String taxNumber, String address, String email) {
        super(name, passportNumber, phoneNumber, taxNumber, address, email);
    }

    /**
     * Instantiates a new Client dto.
     */
    public ClientDTO() {
        super();
    }

}
