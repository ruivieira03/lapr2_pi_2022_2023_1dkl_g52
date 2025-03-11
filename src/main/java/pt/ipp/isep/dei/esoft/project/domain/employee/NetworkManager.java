package pt.ipp.isep.dei.esoft.project.domain.employee;

/**
 * The type Network manager.
 */
public class NetworkManager extends Employee {


    /**
     * Instantiates a new Network manager.
     */
    public NetworkManager() {
        super();
    }

    /**
     * Method used to check if the object is equal to the other object
     *
     * @param outroObjeto Object to comparate to the Network Manager
     * @return true if the objects are the same and false if the objects are different
     */
    public boolean equals(Object outroObjeto) {
        if (this == outroObjeto) {
            return true;
        }
        if (outroObjeto == null || getClass() != outroObjeto.getClass()) {
            return false;
        }
        Employee outroNetworkManager = (Employee) outroObjeto;
        return super.equals(outroNetworkManager);
    }
}
