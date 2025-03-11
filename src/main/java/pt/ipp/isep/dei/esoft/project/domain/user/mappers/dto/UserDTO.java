package pt.ipp.isep.dei.esoft.project.domain.user.mappers.dto;



import java.io.Serializable;

/**
 * Used as User data transfer object
 */
public abstract class UserDTO implements Serializable {
    /**
     * The Name.
     */
    public String name;
    /**
     * The Passport number.
     */
    public int passportNumber;
    /**
     * The Phone number.
     */
    public String phoneNumber;
    /**
     * The Tax number.
     */
    public String taxNumber;
    /**
     * The Address.
     */
    public String address;
    /**
     * The Email.
     */
    public String email;

    /**
     * Instantiates a new User dto.
     *
     * @param name           the name
     * @param passportNumber the passport number
     * @param phoneNumber    the phone number
     * @param taxNumber      the tax number
     * @param address        the address
     * @param email          the email
     */
    public UserDTO(String name, int passportNumber, String phoneNumber, String taxNumber, String address, String email) {
        this.name = name;
        this.passportNumber = passportNumber;
        this.phoneNumber = phoneNumber;
        this.taxNumber = taxNumber;
        this.address = address;
        this.email = email;
    }

    /**
     * Instantiates a new User dto.
     */
    public UserDTO() {

    }
}
