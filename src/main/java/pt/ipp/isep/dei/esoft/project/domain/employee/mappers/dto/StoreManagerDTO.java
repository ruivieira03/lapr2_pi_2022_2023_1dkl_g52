package pt.ipp.isep.dei.esoft.project.domain.employee.mappers.dto;

/**
 * The type Store manager dto.
 */
public class StoreManagerDTO extends EmployeeDTO{

    /**
     * Instantiates a new Store manager dto.
     *
     * @param name           the name
     * @param passportNumber the passport number
     * @param phoneNumber    the phone number
     * @param taxNumber      the tax number
     * @param address        the address
     * @param email          the email
     */
    public StoreManagerDTO(String name, int passportNumber, String phoneNumber, String taxNumber, String address, String email) {
        super(name, passportNumber, phoneNumber, taxNumber, address, email);
    }

    /**
     * Instantiates a new Store manager dto.
     */
    public StoreManagerDTO() {
    }
}
