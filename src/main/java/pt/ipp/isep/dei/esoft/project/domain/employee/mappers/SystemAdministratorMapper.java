package pt.ipp.isep.dei.esoft.project.domain.employee.mappers;

import pt.ipp.isep.dei.esoft.project.domain.employee.SystemAdministrator;
import pt.ipp.isep.dei.esoft.project.domain.employee.mappers.dto.SystemAdministratorDTO;
import pt.ipp.isep.dei.esoft.project.util.error.ErrorOptional;

/**
 * The type System administrator mapper.
 */
public class SystemAdministratorMapper {
    /**
     * Instantiates a new System administrator mapper.
     */
    public SystemAdministratorMapper() {

    }

    /**
     * To dto error optional.
     * Used to map domain representation to dto representation.
     *
     * @param systemAdministrator the system administrator
     * @return the error optional
     */
    public ErrorOptional<SystemAdministratorDTO> toDTO(SystemAdministrator systemAdministrator) {
        if (systemAdministrator == null)
            return ErrorOptional.empty("Error - Mapper - SystemAdministrator cannot be null!");

        SystemAdministratorDTO dto = new SystemAdministratorDTO();

        return ErrorOptional.of(dto);
    }

    /**
     * To domain error optional.
     * Used to map dto representation to domain representation.
     *
     * @param dto the dto
     * @return the error optional
     */
    public ErrorOptional<SystemAdministrator> toDomain(SystemAdministratorDTO dto) {
        if (dto == null) return ErrorOptional.empty("Error - Mapper - SystemAdministrator cannot be null!");

        SystemAdministrator systemAdministrator = new SystemAdministrator();

        return ErrorOptional.of(systemAdministrator);
    }
}
