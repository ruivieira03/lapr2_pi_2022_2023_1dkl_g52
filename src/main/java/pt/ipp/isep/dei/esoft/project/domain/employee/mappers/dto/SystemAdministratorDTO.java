package pt.ipp.isep.dei.esoft.project.domain.employee.mappers.dto;

/**
 * The type System administrator dto.
 */
public class SystemAdministratorDTO extends EmployeeDTO{
    /**
     * Instantiates a new System administrator dto.
     *
     * @param name           the name
     * @param passportNumber the passport number
     * @param phoneNumber    the phone number
     * @param taxNumber      the tax number
     * @param address        the address
     * @param email          the email
     */
    public SystemAdministratorDTO(String name, int passportNumber, String phoneNumber, String taxNumber, String address, String email) {
        super(name, passportNumber, phoneNumber, taxNumber, address, email);
    }

    /**
     * Instantiates a new System administrator dto.
     */
    public SystemAdministratorDTO() {
        super();
    }
}
