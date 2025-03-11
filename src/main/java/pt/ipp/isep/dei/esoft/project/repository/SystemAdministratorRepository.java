package pt.ipp.isep.dei.esoft.project.repository;

import pt.ipp.isep.dei.esoft.project.domain.employee.SystemAdministrator;
import pt.ipp.isep.dei.esoft.project.domain.employee.mappers.dto.SystemAdministratorDTO;
import pt.ipp.isep.dei.esoft.project.domain.user.User;
import pt.ipp.isep.dei.esoft.project.domain.user.mappers.UserMapper;
import pt.ipp.isep.dei.esoft.project.util.error.ErrorOptional;
import pt.ipp.isep.dei.esoft.project.util.error.OperationResult;

import java.io.Serializable;

/**
 * The type System administrator repository.
 */
public class SystemAdministratorRepository implements Serializable {
    /**
     * application system administrator
     */
    private SystemAdministrator systemAdministrator;

    private transient UserMapper userMapper = new UserMapper();

    /**
     * Method used to create the systemadministrator repository with no arguments
     */
    public SystemAdministratorRepository() {
    }

    /**
     * Method used to create the system administrator repository with system administrator as argument
     *
     * @param systemAdministrator system administrator
     */
    public SystemAdministratorRepository(SystemAdministrator systemAdministrator) {
        this.systemAdministrator = systemAdministrator;
    }

    /**
     * Gets system administrator.
     *
     * @return the system administrator
     */
    public SystemAdministrator getSystemAdministrator() {
        return systemAdministrator;
    }

    /**
     * Sets system administrator.
     *
     * @param systemAdministrator the system administrator
     * @deprecated
     */
    public void setSystemAdministrator(SystemAdministrator systemAdministrator) {
        this.systemAdministrator = systemAdministrator;
    }

    /**
     * Used to create system administrator.
     *
     * @param systemAdministratorDTO the system administrator dto
     * @return the operation result
     */
    public OperationResult createSystemAdministrator(SystemAdministratorDTO systemAdministratorDTO) {
        ErrorOptional<User> user = userMapper.toDomain(systemAdministratorDTO);
        if (user.hasError())
            return OperationResult.failed(user.getErrorMessage() + "\nError - Repository - Failed to convert SystemAdminstratorDTO into SystemAdministrator!");

        return addSystemAdministrator((SystemAdministrator) user.get());
    }


    /**
     * USed to add a system administrator.
     *
     * @param admin the admin
     * @return the operation result
     */
    public OperationResult addSystemAdministrator(SystemAdministrator admin) {
        if (!admin.isValid().success()) return OperationResult.failed(admin.isValid().getErrorMessage() + "\nError - Repository - Failed local validation!");
        if (this.systemAdministrator != null)
            if (!this.systemAdministrator.equals(admin))
                return OperationResult.failed("Error - Failed global validation!");

        this.systemAdministrator = admin;
        return OperationResult.successfull();
    }

    /**
     * Deserialize.
     */
    public void deserialize() {
        userMapper = new UserMapper();
    }
}
