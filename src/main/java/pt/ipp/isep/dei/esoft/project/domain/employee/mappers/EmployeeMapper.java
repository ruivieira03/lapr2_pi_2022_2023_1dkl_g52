package pt.ipp.isep.dei.esoft.project.domain.employee.mappers;

import pt.ipp.isep.dei.esoft.project.domain.employee.*;
import pt.ipp.isep.dei.esoft.project.domain.employee.mappers.dto.*;
import pt.ipp.isep.dei.esoft.project.util.error.ErrorOptional;

/**
 * The type Employee mapper.
 */
public class EmployeeMapper {

    private final transient AgentMapper agentMapper = new AgentMapper();
    private final transient NetworkManagerMapper networkManagerMapper = new NetworkManagerMapper();
    private final transient StoreManagerMapper storeManagerMapper = new StoreManagerMapper();
    private final transient SystemAdministratorMapper systemAdministratorMapper = new SystemAdministratorMapper();

    /**
     * Instantiates a new Employee mapper.
     */
    public EmployeeMapper() {

    }

    /**
     * To dto error optional.
     * Used to map domain representation to dto representation.
     *
     * @param employee the employee
     * @return the error optional
     */
    public ErrorOptional<EmployeeDTO> toDTO(Employee employee) {
        if (employee == null) return ErrorOptional.empty("Error - Mapper - Employee cannot be null!");

        ErrorOptional<? extends EmployeeDTO> errorOptional;
        if (employee instanceof Agent) errorOptional = agentMapper.toDTO((Agent) employee);
        else if (employee instanceof NetworkManager)
            errorOptional = networkManagerMapper.toDTO((NetworkManager) employee);
        else if (employee instanceof StoreManager) errorOptional = storeManagerMapper.toDTO((StoreManager) employee);
        else if (employee instanceof SystemAdministrator)
            errorOptional = systemAdministratorMapper.toDTO((SystemAdministrator) employee);
        else return ErrorOptional.empty("Error - Mappper - Invalid Employee type!");

        if (errorOptional.hasError()) return ErrorOptional.empty(errorOptional.getErrorMessage());
        EmployeeDTO dto = errorOptional.get();

        return ErrorOptional.of(dto);
    }

    /**
     * To domain error optional.
     * Used to map dto representation to domain representation.
     *
     * @param dto the dto
     * @return the error optional
     */
    public ErrorOptional<Employee> toDomain(EmployeeDTO dto) {
        if (dto == null) return ErrorOptional.empty("Error - Mapper - EmployeeDTO cannot be null!");

        ErrorOptional<? extends Employee> employeeErrorOptional;
        if (dto instanceof AgentDTO) employeeErrorOptional = agentMapper.toDomain((AgentDTO) dto);
        else if (dto instanceof NetworkManagerDTO)
            employeeErrorOptional = networkManagerMapper.toDomain((NetworkManagerDTO) dto);
        else if (dto instanceof StoreManagerDTO)
            employeeErrorOptional = storeManagerMapper.toDomain((StoreManagerDTO) dto);
        else if (dto instanceof SystemAdministratorDTO)
            employeeErrorOptional = systemAdministratorMapper.toDomain((SystemAdministratorDTO) dto);
        else return ErrorOptional.empty("Error - Mapper - Invalid EmployeeDTO type!");

        if (employeeErrorOptional.hasError()) return ErrorOptional.empty(employeeErrorOptional.getErrorMessage());

        Employee employee = employeeErrorOptional.get();

        return ErrorOptional.of(employee);
    }
}
