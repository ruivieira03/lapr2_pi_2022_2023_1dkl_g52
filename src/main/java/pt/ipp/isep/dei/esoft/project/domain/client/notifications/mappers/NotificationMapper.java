package pt.ipp.isep.dei.esoft.project.domain.client.notifications.mappers;

import pt.ipp.isep.dei.esoft.project.domain.client.notifications.Message;
import pt.ipp.isep.dei.esoft.project.domain.client.notifications.Notification;
import pt.ipp.isep.dei.esoft.project.domain.client.notifications.mappers.dto.MessageDTO;
import pt.ipp.isep.dei.esoft.project.domain.client.notifications.mappers.dto.NotificationDTO;
import pt.ipp.isep.dei.esoft.project.util.error.ErrorOptional;

import java.util.ArrayList;
import java.util.List;

/**
 * The type Notification mapper.
 */
public class NotificationMapper {

    private final transient MessageMapper messageMapper = new MessageMapper();

    /**
     * Instantiates a new Notification mapper.
     */
    public NotificationMapper() {

    }

    /**
     * To dto error optional.
     * Used to map domain representation to dto representation.
     *
     * @param notification the notification
     * @return the error optional
     */
    public ErrorOptional<NotificationDTO> toDTO(Notification notification) {
        if (notification == null) return ErrorOptional.empty("Error - Mapper - User cannot be null!");

        NotificationDTO dto = new NotificationDTO();

        dto.messages = new ArrayList<>();

        ErrorOptional<MessageDTO> messageDTO;
        for (Message message : notification.getMessages()) {
            messageDTO = messageMapper.toDTO(message);
            if (messageDTO.hasError()) return ErrorOptional.empty(messageDTO.getErrorMessage());
            dto.messages.add(messageDTO.get());
        }

        return ErrorOptional.of(dto);
    }

    /**
     * To domain error optional.
     * Used to map dto representation to domain representation.
     *
     * @param dto the dto
     * @return the error optional
     */
    public ErrorOptional<Notification> toDomain(NotificationDTO dto) {
        if (dto == null) return ErrorOptional.empty("Erro - Mapper - UserDTO cannot be null!");

        ErrorOptional<Message> message;
        List<Message> messages = new ArrayList<>();
        Notification notification = new Notification();

        for (MessageDTO messageDTO : dto.messages) {
            message = messageMapper.toDomain(messageDTO);
            if (message.hasError()) return ErrorOptional.empty(message.getErrorMessage());
            messages.add(message.get());
        }

        notification.setMessages(messages);

        return ErrorOptional.of(notification);
    }

}
