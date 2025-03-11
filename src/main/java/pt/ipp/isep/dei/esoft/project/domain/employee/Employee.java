package pt.ipp.isep.dei.esoft.project.domain.employee;


import pt.ipp.isep.dei.esoft.project.domain.user.User;
import pt.ipp.isep.dei.esoft.project.util.error.OperationResult;

import java.util.regex.Pattern;


/**
 * This class allows the construction of a hierarchy
 * of classes to represent different types of contributors.
 * Specifies members common to all classes in the hierarchy.
 */
public abstract class Employee extends User {

    /**
     * The enum Role.
     */
    public enum Role {
        /**
         * System admin role.
         */
        SYSTEM_ADMIN,
        /**
         * Agent role.
         */
        AGENT,
        /**
         * Network manager role.
         */
        NETWORK_MANAGER,
        /**
         * Store manager role.
         */
        STORE_MANAGER
    }


    /**
     * Instantiates a new Employee.
     */
    public Employee() {
        super();
    }


    /**
     * Method used to check if the object is equal to the other object
     *
     * @param outroObjeto Object to comparate to the employee
     * @return true if the objects are the same and false if the objects are different
     */
    @Override
    public boolean equals(Object outroObjeto) {
        if (this == outroObjeto)  return true;

        if (outroObjeto == null || getClass() != outroObjeto.getClass()) return false;


        Employee outroEmployee = (Employee) outroObjeto;
        return getName().equalsIgnoreCase(outroEmployee.getName())
                && getPassportNumber() == outroEmployee.getPassportNumber()
                && getTaxNumber() == outroEmployee.getTaxNumber()
                && getAddress().equalsIgnoreCase(outroEmployee.getAddress())
                && getEmail().equals(outroEmployee.getEmail())
                && getPhoneNumber() == outroEmployee.getPhoneNumber();
    }

    /**
     * method to verify all the attributes received to create employee
     *
     * @return
     */
    public OperationResult isValid() {
        if (!isNameValid(getName()).success())
            return isNameValid(getName());

        if (!isPassportNumberValid(getPassportNumber()).success())
            return isPassportNumberValid(getPassportNumber());

        if (!isTaxNumberValid(getTaxNumber()).success())
            return isTaxNumberValid(getTaxNumber());

        if (getAddress().isEmpty())
            return OperationResult.failed("Error - Employee - Adress cannot be empty!");

        if (!isPhoneNumberValid(getPhoneNumber()).success())
            return isPhoneNumberValid(getPhoneNumber());

        return OperationResult.successfull();
    }

    /**
     * method to verify name
     *
     * @param name the name
     * @return operation result
     */
    public static OperationResult isNameValid(String name) {
        if (name == null || name.isEmpty())
            return OperationResult.failed("Error - Employee - Name cannot be null or empty!");

        String nameRegex = "[a-zA-z\\p{javaWhitespace}]*";
        Pattern pat = Pattern.compile(nameRegex);
        if (pat.matcher(name).matches())
            return OperationResult.successfull();

        return OperationResult.failed("Error - Employee - Names can only have alphabetic characters and spaces!");
    }
}
