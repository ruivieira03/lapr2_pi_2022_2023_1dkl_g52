package pt.ipp.isep.dei.esoft.project.domain.user;

import pt.ipp.isep.dei.esoft.project.domain.client.Client;
import pt.ipp.isep.dei.esoft.project.util.error.OperationResult;

import java.io.Serializable;
import java.util.Random;

/**
 * The type User.
 */
public abstract class User implements Serializable {
    /**
     * User name
     */
    private String name;
    /**
     * User passport number
     */
    private int passportNumber;
    /**
     * User phone number
     */
    private String phoneNumber;
    /**
     * User tax number
     */
    private String taxNumber;
    /**
     * User adress
     */
    private String address;
    /**
     * User email
     */
    private String email;


    /**
     * Instantiates a new User.
     */
    public User() {

    }

    /**
     * Method used to get the user name
     *
     * @return String representing the user name
     */
    public String getName() {
        return name;
    }

    /**
     * Method used to get the user email
     *
     * @return String representing the user email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Method used to get the user passport number
     *
     * @return String representing the user passport number
     */
    public int getPassportNumber() {
        return passportNumber;
    }

    /**
     * Method used to get the user phone number
     *
     * @return String representing the user phone number
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * Method used to get the user tax number
     *
     * @return String representing theusert tax number
     */
    public String getTaxNumber() {
        return taxNumber;
    }

    /**
     * Method used to get the user adress
     *
     * @return String representing the user adress
     */
    public String getAddress() {
        return address;
    }

    /**
     * Method used to check if a user is valid
     *
     * @param user user
     * @return true if the user is valid or false if not
     */
    public boolean isValid(User user) {
        // a user cannot be in the application with this type
        return false;
    }

    /**
     * Method used to check if one object is equal to another
     *
     * @param obj object to be compared to the initial object
     * @return true if the objects are the same or false if the objects are different
     */
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (obj.getClass() != getClass()) return false;
        if (obj == this) return true;

        Client other = (Client) obj;
        return (getName().equals(other.getName()) && getAddress().equals(other.getAddress()) && getEmail().equals(other.getEmail()) && getPassportNumber() == other.getPassportNumber() && getPhoneNumber().equalsIgnoreCase(other.getPhoneNumber()) && getTaxNumber().equalsIgnoreCase(other.getTaxNumber()));
    }

    /**
     * Method to check if the user has a specific email has string
     *
     * @param email email
     * @return true if the user have that email or false if not
     */
    public boolean hasEmail(String email) {
        return this.email.equals(email);
    }


    /**
     * Method used to check if the user is valid
     *
     * @return true if the user is valid or false if not
     */
    public OperationResult isValid() {
        return OperationResult.failed("Error - User - User cannot be type User!");
    }

    /**
     * Method used to set the user adress
     *
     * @param address new user adress
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * Method used to set the user email
     *
     * @param email new user email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Method used to set the user name
     *
     * @param name new user name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Method used to set client the user passport number
     *
     * @param passportNumber new user passport number
     */
    public void setPassportNumber(int passportNumber) {
        this.passportNumber = passportNumber;
    }

    /**
     * Method used to set user the user phone number
     *
     * @param phoneNumber new user phone number
     */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    /**
     * Method used to set user the user tax number
     *
     * @param taxNumber new user tax number
     */
    public void setTaxNumber(String taxNumber) {
        this.taxNumber = taxNumber;
    }


    /**
     * Generate password string.
     *
     * @return the string
     */
    public static String generatePassword() {
        Random rand = new Random();
        String uppercase = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String digits = "0123456789";
        StringBuilder password = new StringBuilder();

        // Add 3 uppercase letters
        for (int i = 0; i < 3; i++) {
            int index = rand.nextInt(uppercase.length());
            password.append(uppercase.charAt(index));
        }

        // Add 2 digits
        for (int i = 0; i < 2; i++) {
            int index = rand.nextInt(digits.length());
            password.append(digits.charAt(index));
        }

        // Add 2 lowercase letters
        for (int i = 0; i < 2; i++) {
            int ascii = rand.nextInt(26) + 97; // ASCII code for lowercase letters (a-z)
            char letter = (char) ascii;
            password.append(letter);
        }

        // Shuffle the characters in the password
        for (int i = 0; i < password.length(); i++) {
            int index = rand.nextInt(password.length());
            char temp = password.charAt(i);
            password.setCharAt(i, password.charAt(index));
            password.setCharAt(index, temp);
        }

        return password.toString();
    }

    /**
     * Is passport number valid operation result.
     *
     * @param passportNumber the passport number
     * @return the operation result
     */
    public OperationResult isPassportNumberValid(int passportNumber) {
        final int RequiredLength = 9;

        String passportString = String.valueOf(passportNumber);

        if (passportString.length() == RequiredLength && passportNumber > 0)
            return OperationResult.successfull();

        return OperationResult.failed("Error - User - PassportNumber must have 9 digits!");
    }

    /**
     * Is phone number valid operation result.
     *
     * @param phoneNumber the phone number
     * @return the operation result
     */
    public OperationResult isPhoneNumberValid(String phoneNumber) {
        final int RequiredLengthFirst = 3, RequiredLengthSecond = 3, RequiredLengthLast = 4;

        String[] split = phoneNumber.split("-");

        if (split.length == 3 && split[0].length() == RequiredLengthFirst && split[1].length() == RequiredLengthSecond && split[2].length() == RequiredLengthLast)
            return OperationResult.successfull();

        return OperationResult.failed("Error - User - Phone number must follow this format: 123-123-1234!");
    }

    /**
     * Is tax number valid operation result.
     *
     * @param taxNumber the tax number
     * @return the operation result
     */
    public OperationResult isTaxNumberValid(String taxNumber) {
        final int RequiredLengthFirst = 3, RequiredLengthSecond = 2, RequiredLengthLast = 4;

        String[] split = taxNumber.split("-");

        if (split.length == 3 && split[0].length() == RequiredLengthFirst && split[1].length() == RequiredLengthSecond && split[2].length() == RequiredLengthLast)
            return OperationResult.successfull();

        return OperationResult.failed("Error - User - Taxnumber must follow this format: 123-12-1234");
    }
}
