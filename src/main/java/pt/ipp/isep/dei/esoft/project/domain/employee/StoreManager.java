package pt.ipp.isep.dei.esoft.project.domain.employee;

/**
 * The type Store manager.
 */
public class StoreManager extends Employee {


    /**
     * Instantiates a new Store manager.
     */
    public StoreManager() {
        super();
    }

    /**
     * Method used to check if the object is equal to the other object
     *
     * @param outroObjeto Object to comparate to the Store Manager
     * @return true if the objects are the same and false if the objects are different
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
