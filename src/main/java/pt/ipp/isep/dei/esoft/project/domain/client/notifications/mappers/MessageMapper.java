package pt.ipp.isep.dei.esoft.project.domain.client.notifications.mappers;

import pt.ipp.isep.dei.esoft.project.domain.client.mappers.dto.ClientDTO;
import pt.ipp.isep.dei.esoft.project.domain.client.notifications.Message;
import pt.ipp.isep.dei.esoft.project.domain.client.notifications.mappers.dto.MessageDTO;
import pt.ipp.isep.dei.esoft.project.domain.employee.mappers.dto.SystemAdministratorDTO;
import pt.ipp.isep.dei.esoft.project.domain.user.User;
import pt.ipp.isep.dei.esoft.project.domain.user.mappers.UserMapper;
import pt.ipp.isep.dei.esoft.project.domain.user.mappers.dto.UserDTO;
import pt.ipp.isep.dei.esoft.project.repository.ClientRepository;
import pt.ipp.isep.dei.esoft.project.repository.Repositories;
import pt.ipp.isep.dei.esoft.project.repository.StoreRepository;
import pt.ipp.isep.dei.esoft.project.repository.SystemAdministratorRepository;
import pt.ipp.isep.dei.esoft.project.util.error.ErrorOptional;

/**
 * The type Message mapper.
 */
public class MessageMapper {

    /**
     * Instantiates a new Message mapper.
     */
    public MessageMapper() {

    }

    /**
     * To dto error optional.
     * Used to map domain representation to dto representation.
     *
     * @param message the message
     * @return the error optional
     */
    public ErrorOptional<MessageDTO> toDTO(Message message) {
        if (message == null) return ErrorOptional.empty("Error - Mapper - User cannot be null!");

        UserMapper userMapper = new UserMapper();

        MessageDTO dto = new MessageDTO();

        dto.id = message.getId();
        dto.date = message.getDate();
        ErrorOptional<UserDTO> userDTO = userMapper.toDTO(message.getAuthor());
        if (userDTO.hasError()) return ErrorOptional.empty(userDTO.getErrorMessage());
        dto.author = userDTO.get();
        dto.message = message.getMessage();

        return ErrorOptional.of(dto);
    }

    /**
     * To domain error optional.
     * Used to map dto representation to domain representation.
     *
     * @param dto the dto
     * @return the error optional
     */
    public ErrorOptional<Message> toDomain(MessageDTO dto) {
        if (dto == null) return ErrorOptional.empty("Error - Mapper - UserDTO cannot be null!");

        ClientRepository clientRepository = Repositories.getClientRepository();
        SystemAdministratorRepository systemAdministratorRepository = Repositories.getSystemAdministratorRepository();
        StoreRepository storeNetworkRepository = Repositories.getStoreRepository();

        ErrorOptional<? extends User> userErrorOptional;


        if (dto.author instanceof ClientDTO)
            userErrorOptional = clientRepository.getClientFromEmail(dto.author.email);
        else if (dto.author instanceof SystemAdministratorDTO)
            userErrorOptional = ErrorOptional.of(systemAdministratorRepository.getSystemAdministrator());
        else
            userErrorOptional = storeNetworkRepository.getAgentFromEmail(dto.author.email);

        if (userErrorOptional.hasError()) return ErrorOptional.empty(userErrorOptional.getErrorMessage());

        Message message = new Message();

        message.setMessage(dto.message);
        message.setAuthor(userErrorOptional.get());
        message.setDate(dto.date);

        return ErrorOptional.of(message);
    }
}
