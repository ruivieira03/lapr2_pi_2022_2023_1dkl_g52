package pt.ipp.isep.dei.esoft.project.domain.employee.mappers.dto;

import pt.ipp.isep.dei.esoft.project.domain.user.mappers.dto.UserDTO;

/**
 * The type Employee dto.
 */
public abstract class EmployeeDTO extends UserDTO {

    /**
     * Instantiates a new Employee dto.
     *
     * @param name           the name
     * @param passportNumber the passport number
     * @param phoneNumber    the phone number
     * @param taxNumber      the tax number
     * @param address        the address
     * @param email          the email
     */
    public EmployeeDTO(String name, int passportNumber, String phoneNumber, String taxNumber, String address, String email) {
        super(name, passportNumber, phoneNumber, taxNumber, address, email);
    }

    /**
     * Instantiates a new Employee dto.
     */
    public EmployeeDTO() {
        super();
    }

    public String toString() {
        return this.name;
    }
}
