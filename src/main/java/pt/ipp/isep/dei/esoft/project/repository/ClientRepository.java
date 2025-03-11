package pt.ipp.isep.dei.esoft.project.repository;

import pt.ipp.isep.dei.esoft.project.domain.client.Client;
import pt.ipp.isep.dei.esoft.project.domain.client.mappers.dto.ClientDTO;
import pt.ipp.isep.dei.esoft.project.domain.client.notifications.Message;
import pt.ipp.isep.dei.esoft.project.domain.client.notifications.Notification;
import pt.ipp.isep.dei.esoft.project.domain.client.notifications.mappers.MessageMapper;
import pt.ipp.isep.dei.esoft.project.domain.client.notifications.mappers.NotificationMapper;
import pt.ipp.isep.dei.esoft.project.domain.client.notifications.mappers.dto.MessageDTO;
import pt.ipp.isep.dei.esoft.project.domain.client.notifications.mappers.dto.NotificationDTO;
import pt.ipp.isep.dei.esoft.project.domain.user.User;
import pt.ipp.isep.dei.esoft.project.domain.user.mappers.UserMapper;
import pt.ipp.isep.dei.esoft.project.domain.user.mappers.dto.UserDTO;
import pt.ipp.isep.dei.esoft.project.util.error.ErrorOptional;
import pt.ipp.isep.dei.esoft.project.util.error.OperationResult;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * The type Client repository.
 */
public class ClientRepository implements Serializable {

    /**
     * The Clients.
     */
    List<Client> clients = new ArrayList<>();

    private transient UserMapper userMapper = new UserMapper();

    private transient NotificationMapper notificationMapper = new NotificationMapper();
    private transient MessageMapper messageMapper = new MessageMapper();

    /**
     * Used to add a client to the repository return a boolean to inform of the success of the operation.
     *
     * @param client Client
     * @return boolean (in)success of operation
     */
    public OperationResult addClient(Client client) {
        if (!client.isValid().success())
            return OperationResult.failed(client.isValid().getErrorMessage() + "\nError - Repository failed local validation!");
        if (!isValid(client).success())
            return OperationResult.failed(isValid(client).getErrorMessage() + "\nError - Repository failed global validation!");

        clients.add(client);
        return OperationResult.successfull();
    }

    /**
     * Add client data operation result.
     * Used to add data to client.
     *
     * @param client the client
     * @return the operation result
     */
    public OperationResult addClientData(Client client) {
        if (!client.isLegacyValid().success())
            return OperationResult.failed(client.isValid().getErrorMessage() + "\nError - Repository failed local validation!");
        if (!isValid(client).success())
            return OperationResult.failed(isValid(client).getErrorMessage() + "\nError - Repository failed global validation!");

        clients.add(client);
        return OperationResult.successfull();
    }

    /**
     * Gets notification dto from client.
     *
     * @param clientDTO the client dto
     * @return the notification dto from client
     */
    public ErrorOptional<NotificationDTO> getNotificationDTOFromClient(ClientDTO clientDTO) {
        ErrorOptional<Client> client = getClientFromEmail(clientDTO.email);

        if (client.hasError())
            return ErrorOptional.empty(client.getErrorMessage() + "\nError - Repository - Failed to get Client from ClientRepository!");

        Notification notification = client.get().getNotification();

        ErrorOptional<NotificationDTO> notificationDTO = notificationMapper.toDTO(notification);

        if (notificationDTO.hasError())
            return ErrorOptional.empty(notificationDTO.getErrorMessage() + "\nError - Repository - Failed to convert Notification into NotificatioDTO!");

        return ErrorOptional.of(notificationDTO.get());
    }

    /**
     * Notify client operation result.
     * Used to notify the client.
     *
     * @param clientDTO  the client dto
     * @param messageDTO the message dto
     * @return the operation result
     */
    public OperationResult notifyClient(ClientDTO clientDTO, MessageDTO messageDTO) {
        ErrorOptional<Client> client = getClientFromEmail(clientDTO.email);

        if (client.hasError())
            return OperationResult.failed(client.getErrorMessage() + "\nError - Repository - Failed to get Client from ClientRepository!");

        ErrorOptional<Message> message = messageMapper.toDomain(messageDTO);

        if (message.hasError())
            return OperationResult.failed(message.getErrorMessage() + "\nError - Repository - Failedd to convert MessageDTO into Message!");

        return client.get().addMessage(message.get());
    }

    /**
     * Notify client operation result.
     * Used to notify client.
     *
     * @param client     the client
     * @param messageDTO the message dto
     * @return the operation result
     */
    public OperationResult notifyClient(Client client, MessageDTO messageDTO) {
        ErrorOptional<Message> message = messageMapper.toDomain(messageDTO);

        if (message.hasError())
            return OperationResult.failed(message.getErrorMessage() + "\nError - Repository - Failedd to convert MessageDTO into Message!");

        return client.addMessage(message.get());
    }

    /**
     * Used to validate a client to be added in to the repository.
     *
     * @param client Client
     * @return bollean valid
     */
    private OperationResult isValid(Client client) {
        for (Client c : clients) {
            if (c.equals(client))
                return OperationResult.failed("Error - ClientRepository - Client already exists!");
        }

        return OperationResult.successfull();
    }

    /**
     * Method used to get the client from his email
     *
     * @param email client email
     * @return client with that email
     */
    public ErrorOptional<Client> getClientFromEmail(String email) {

        for (Client client : clients) {
            if (client.hasEmail(email))
                return ErrorOptional.of(client);
        }

        return ErrorOptional.empty("Error - Repository - ClientRepository does not contain Client[" + email + "]");
    }

    /**
     * Gets client dto from email.
     *
     * @param email the email
     * @return the client dto from email
     */
    public ErrorOptional<ClientDTO> getClientDTOFromEmail(String email) {
        ErrorOptional<Client> client = getClientFromEmail(email);

        if (client.hasError()) return ErrorOptional.empty(client.getErrorMessage());


        ErrorOptional<UserDTO> dto = userMapper.toDTO(client.get());

        if (dto.hasError())
            return ErrorOptional.empty(dto.getErrorMessage() + "\nError - Repository - Failed to convert Client into ClientDTO!");

        return ErrorOptional.of((ClientDTO) dto.get());

    }


    /**
     * Create client operation result.
     * Used to create client.
     *
     * @param clientDTO the client dto
     * @return the operation result
     */
    public OperationResult createClient(ClientDTO clientDTO) {
        ErrorOptional<User> user = userMapper.toDomain(clientDTO);
        if (user.hasError())
            return OperationResult.failed(user.getErrorMessage() + "\nError - ClientRepository - Failed to convert ClientDTO into Client!");

        return addClient((Client) user.get());
    }

    private OperationResult createClientData(ClientDTO dto) {
        ErrorOptional<User> user = userMapper.toDomain(dto);
        if (user.hasError())
            return OperationResult.failed(user.getErrorMessage() + "\nError - ClientRepository - Failed to convert ClientDTO into Client!");

        return addClientData((Client) user.get());
    }

    /**
     * Gets clients.
     *
     * @return the clients
     */
    public List<Client> getClients() {
        return clients;
    }


    /**
     * Add client through data list.
     *
     * @param clientData the client data
     * @return the list
     */
    public List<Client> addClientThroughData(List<ClientDTO> clientData) {
        List<Client> clients = new ArrayList<>();

        for (ClientDTO dto : clientData) {
            try {

                OperationResult result = createClientData(dto);

                if (result.success()) {
                    System.out.println("Client added: " + dto.email);
                    clients.add((Client) userMapper.toDomain(dto).get());
                }

            } catch (IllegalArgumentException e) {
                System.err.println("Error adding client: " + dto.email);
                System.err.println("Error: " + e.getMessage() + "\n");
            }
        }
        return clients;
    }

    /**
     * Remove message from client operation result.
     * Used to remove the message from client.
     *
     * @param clientDTO the client dto
     * @param message   the message
     * @return the operation result
     */
    public OperationResult removeMessageFromClient(ClientDTO clientDTO, MessageDTO message) {
        ErrorOptional<Client> client = getClientFromEmail(clientDTO.email);
        if (client.hasError())
            return OperationResult.failed(client.getErrorMessage() + "Error - Repository - Failed to get client from email!");

        return client.get().removeMessage(message.id);
    }

    /**
     * Deserialize.
     */
    public void deserialize() {
        messageMapper = new MessageMapper();
        notificationMapper = new NotificationMapper();
        userMapper = new UserMapper();
    }
}
