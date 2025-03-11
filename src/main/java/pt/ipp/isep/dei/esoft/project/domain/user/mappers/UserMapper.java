package pt.ipp.isep.dei.esoft.project.domain.user.mappers;

import pt.ipp.isep.dei.esoft.project.domain.client.Client;
import pt.ipp.isep.dei.esoft.project.domain.client.mappers.ClientMapper;
import pt.ipp.isep.dei.esoft.project.domain.client.mappers.dto.ClientDTO;
import pt.ipp.isep.dei.esoft.project.domain.client.notifications.mappers.NotificationMapper;
import pt.ipp.isep.dei.esoft.project.domain.employee.Employee;
import pt.ipp.isep.dei.esoft.project.domain.employee.mappers.EmployeeMapper;
import pt.ipp.isep.dei.esoft.project.domain.employee.mappers.dto.EmployeeDTO;
import pt.ipp.isep.dei.esoft.project.domain.user.User;
import pt.ipp.isep.dei.esoft.project.domain.user.mappers.dto.UserDTO;
import pt.ipp.isep.dei.esoft.project.util.error.ErrorOptional;

/**
 * The type User mapper.
 */
public class UserMapper {
    private final transient ClientMapper clientMapper = new ClientMapper();
    private final transient EmployeeMapper employeeMapper = new EmployeeMapper();
    private final transient NotificationMapper notificationMapper = new NotificationMapper();

    /**
     * Instantiates a new User mapper.
     */
    public UserMapper() {

    }

    /**
     * To dto error optional.
     * Used to map domain representation to dto representation.
     *
     * @param user the user
     * @return the error optional
     */
    public ErrorOptional<UserDTO> toDTO(User user) {
        if (user == null) return ErrorOptional.empty("Error - Mapper - User cannot be null!");

        ErrorOptional<? extends UserDTO> errorOptional;
        if (user instanceof Client) errorOptional = clientMapper.toDTO((Client)  user);
        else if (user instanceof Employee) errorOptional = employeeMapper.toDTO((Employee) user);
        else return ErrorOptional.empty("Error - Mapper - Invalid User Type!");

        if (errorOptional.hasError()) return ErrorOptional.empty(errorOptional.getErrorMessage());
        UserDTO dto = errorOptional.get();

        dto.name = user.getName();
        dto.passportNumber = user.getPassportNumber();
        dto.phoneNumber = user.getPhoneNumber();
        dto.taxNumber = user.getTaxNumber();
        dto.address = user.getAddress();
        dto.email = user.getEmail();

        return ErrorOptional.of(dto);
    }

    /**
     * To domain error optional.
     * Used to map dto representation to domain representation.
     *
     * @param dto the dto
     * @return the error optional
     */
    public ErrorOptional<User> toDomain(UserDTO dto) {
        if (dto == null) return ErrorOptional.empty("Erro - Mapper - UserDTO cannot be null!");

        ErrorOptional<? extends User> userErrorOptional;
        if (dto instanceof ClientDTO) userErrorOptional = clientMapper.toDomain((ClientDTO) dto);
        else if (dto instanceof EmployeeDTO) userErrorOptional = employeeMapper.toDomain((EmployeeDTO) dto);
        else return ErrorOptional.empty("Error - Mapper - Invalid UserDTO type!");

        User user = userErrorOptional.get();

        user.setName(dto.name);
        user.setPassportNumber(dto.passportNumber);
        user.setPhoneNumber(dto.phoneNumber);
        user.setTaxNumber(dto.taxNumber);
        user.setAddress(dto.address);
        user.setEmail(dto.email);

        return ErrorOptional.of(user);
    }
}
