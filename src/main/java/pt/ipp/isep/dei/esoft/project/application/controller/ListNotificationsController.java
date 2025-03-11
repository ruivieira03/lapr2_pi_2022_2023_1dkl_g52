package pt.ipp.isep.dei.esoft.project.application.controller;

import pt.ipp.isep.dei.esoft.project.domain.client.mappers.dto.ClientDTO;
import pt.ipp.isep.dei.esoft.project.domain.client.notifications.mappers.dto.MessageDTO;
import pt.ipp.isep.dei.esoft.project.domain.client.notifications.mappers.dto.NotificationDTO;
import pt.ipp.isep.dei.esoft.project.repository.AuthenticationRepository;
import pt.ipp.isep.dei.esoft.project.repository.ClientRepository;
import pt.ipp.isep.dei.esoft.project.repository.Repositories;
import pt.ipp.isep.dei.esoft.project.util.error.ErrorOptional;
import pt.ipp.isep.dei.esoft.project.util.error.OperationResult;

/**
 * The type List notifications controller.
 */
public class ListNotificationsController {

    private static MessageDTO selectedMessage;

    /**
     * Instantiates a new List notifications controller.
     */
    public ListNotificationsController() {

    }

    /**
     * Gets client from user session.
     *
     * @return the client from user session
     */
    public ErrorOptional<ClientDTO> getClientFromUserSession() {

        AuthenticationRepository authenticationRepository = Repositories.getAuthenticationRepository();
        ClientRepository clientRepository = Repositories.getClientRepository();

        String email = authenticationRepository.getCurrentUserSession().getUserId().toString();

        return clientRepository.getClientDTOFromEmail(email);
    }

    /**
     * Gets notifications.
     *
     * @return the notifications
     */
    public ErrorOptional<NotificationDTO> getNotifications() {
        ErrorOptional<ClientDTO> client = getClientFromUserSession();
        if (client.hasError())
            return ErrorOptional.empty(client.getErrorMessage() + "Error - Controller - Failed to get client from user session!");

        return ErrorOptional.of(client.get().notificationDTO);
    }

    /**
     * Gets number of notifications.
     *
     * @return the number of notifications
     */
    public int getNumberOfNotifications() {
        ErrorOptional<ClientDTO> client = getClientFromUserSession();
        if (client.hasError())
            return 0;

        return client.get().notificationDTO.messages.size();
    }

    /**
     * Gets selected message.
     *
     * @return the selected message
     */
    public MessageDTO getSelectedMessage() {
        return selectedMessage;
    }

    /**
     * Sets selected message.
     *
     * @param selectedMessage the selected message
     */
    public void setSelectedMessage(MessageDTO selectedMessage) {
        ListNotificationsController.selectedMessage = selectedMessage;
    }

    /**
     * Remove message operation result.
     *
     * @param message the message
     * @return the operation result
     */
    public OperationResult removeMessage(MessageDTO message) {
        ClientRepository clientRepository = Repositories.getClientRepository();

        ErrorOptional<ClientDTO> client = getClientFromUserSession();
        if (client.hasError())
            return OperationResult.failed(client.getErrorMessage() + "Error - Controller - Failed to get client from user session!");


        return clientRepository.removeMessageFromClient(client.get(), message);
    }
}
