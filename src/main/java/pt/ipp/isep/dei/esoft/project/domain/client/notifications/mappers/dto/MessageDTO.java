package pt.ipp.isep.dei.esoft.project.domain.client.notifications.mappers.dto;

import pt.ipp.isep.dei.esoft.project.domain.user.mappers.dto.UserDTO;

import java.time.LocalDateTime;

/**
 * The type Message dto.
 */
public class MessageDTO {
    /**
     * The Id.
     */
    public int id;
    /**
     * The Author.
     */
    public UserDTO author;
    /**
     * The Date.
     */
    public LocalDateTime date;
    /**
     * The Message.
     */
    public String message;

    /**
     * Instantiates a new Message dto.
     */
    public MessageDTO() {

    }

    @Override
    public String toString() {
        return String.format("ID: %d%nAuthor: %s%nDate:%s%nMessage:%s%n",id,author,date,message);
    }
}
