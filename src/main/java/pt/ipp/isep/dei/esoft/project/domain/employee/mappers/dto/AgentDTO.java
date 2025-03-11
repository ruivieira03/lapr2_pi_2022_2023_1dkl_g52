package pt.ipp.isep.dei.esoft.project.domain.employee.mappers.dto;

/**
 * The type Agent dto.
 */
public class AgentDTO extends EmployeeDTO{

    /**
     * Instantiates a new Agent dto.
     *
     * @param name           the name
     * @param passportNumber the passport number
     * @param phoneNumber    the phone number
     * @param taxNumber      the tax number
     * @param address        the address
     * @param email          the email
     */
    public AgentDTO(String name, int passportNumber, String phoneNumber, String taxNumber, String address, String email) {
        super(name, passportNumber, phoneNumber, taxNumber, address, email);
    }

    /**
     * Instantiates a new Agent dto.
     */
    public AgentDTO() {
        super();
    }

    @Override
    public String toString() {
        return String.format("|%20s|%17d|%14s|%12s|%20s|%20s|", name, passportNumber, phoneNumber, taxNumber, address, email);
    }
}
