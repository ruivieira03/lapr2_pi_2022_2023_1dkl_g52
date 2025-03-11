package pt.ipp.isep.dei.esoft.project.domain.client.mappers;

import pt.ipp.isep.dei.esoft.project.domain.client.Client;
import pt.ipp.isep.dei.esoft.project.domain.client.Owner;
import pt.ipp.isep.dei.esoft.project.domain.client.mappers.dto.ClientDTO;
import pt.ipp.isep.dei.esoft.project.domain.client.mappers.dto.OwnerDTO;
import pt.ipp.isep.dei.esoft.project.domain.client.notifications.mappers.NotificationMapper;
import pt.ipp.isep.dei.esoft.project.domain.client.notifications.mappers.dto.NotificationDTO;
import pt.ipp.isep.dei.esoft.project.util.error.ErrorOptional;

import java.io.Serializable;

/**
 * The type Client mapper.
 */
public class ClientMapper implements Serializable {
    private final transient OwnerMapper ownerMapper = new OwnerMapper();
    private final transient NotificationMapper notificationMapper = new NotificationMapper();


    /**
     * Instantiates a new Client mapper.
     */
    public ClientMapper() {

    }

    /**
     * To dto error optional.
     * Used to map domain representation to dto representation.
     *
     * @param client the client
     * @return the error optional
     */
    public ErrorOptional<ClientDTO> toDTO(Client client) {
        if (client == null) return ErrorOptional.empty("Error - Mapper - Client cannot be null!");

        ClientDTO dto;
        if (client instanceof Owner) {
            ErrorOptional<OwnerDTO> errorOptional = ownerMapper.toDTO((Owner) client);
            if (errorOptional.hasError()) return ErrorOptional.empty(errorOptional.getErrorMessage());
            dto = errorOptional.get();
        } else dto = new ClientDTO();

        ErrorOptional<NotificationDTO> errorOptional = notificationMapper.toDTO(client.getNotification());
        if(errorOptional.hasError()) return ErrorOptional.empty(errorOptional.getErrorMessage());

        dto.notificationDTO = errorOptional.get();

        return ErrorOptional.of(dto);
    }

    /**
     * To domain error optional.
     * Used to map dto representation to domain representation.
     *
     * @param dto the dto
     * @return the error optional
     */
    public ErrorOptional<Client> toDomain(ClientDTO dto) {
        if (dto == null) return ErrorOptional.empty("Error - Mapper - ClientDTO cannot be null!");

        Client client;

        if (dto instanceof OwnerDTO) {
            ErrorOptional<Owner> owner = ownerMapper.toDomain((OwnerDTO) dto);
            if (owner.hasError()) return ErrorOptional.empty(owner.getErrorMessage());
            client = owner.get();
        } else client = new Client();

        return ErrorOptional.of(client);
    }
}
