package pt.ipp.isep.dei.esoft.project.domain.client.notifications.mappers.dto;

import java.util.ArrayList;
import java.util.List;

/**
 * The type Notification dto.
 */
public class NotificationDTO {

    /**
     * The Messages.
     */
    public List<MessageDTO> messages;

    /**
     * Instantiates a new Notification dto.
     */
    public NotificationDTO() {
        this.messages = new ArrayList<>();
    }
}
