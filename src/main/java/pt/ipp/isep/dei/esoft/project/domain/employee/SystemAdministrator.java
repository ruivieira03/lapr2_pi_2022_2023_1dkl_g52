package pt.ipp.isep.dei.esoft.project.domain.employee;

/**
 * The type System administrator.
 */
public class SystemAdministrator extends Employee {

    /**
     * Instantiates a new System administrator.
     */
    public SystemAdministrator() {

    }

    /**
     * Method used to check if the object is equal to the other object
     * @param outroObjeto Object to comparate to the employee
     * @return
     */
    public boolean equals(Object outroObjeto) {
        if (this == outroObjeto) {
            return true;
        }
        if (outroObjeto == null || getClass() != outroObjeto.getClass()) {
            return false;
        }
        Employee outroStoreManager = (Employee) outroObjeto;
        return super.equals(outroStoreManager);
    }
}
