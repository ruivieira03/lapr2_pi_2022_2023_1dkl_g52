package pt.ipp.isep.dei.esoft.project.domain.employee.mappers.dto;

/**
 * The type Network manager dto.
 */
public class NetworkManagerDTO extends EmployeeDTO{

    /**
     * Instantiates a new Network manager dto.
     *
     * @param name           the name
     * @param passportNumber the passport number
     * @param phoneNumber    the phone number
     * @param taxNumber      the tax number
     * @param address        the address
     * @param email          the email
     */
    public NetworkManagerDTO(String name, int passportNumber, String phoneNumber, String taxNumber, String address, String email) {
        super(name, passportNumber, phoneNumber, taxNumber, address, email);
    }

    /**
     * Instantiates a new Network manager dto.
     */
    public NetworkManagerDTO() {
    }
}
