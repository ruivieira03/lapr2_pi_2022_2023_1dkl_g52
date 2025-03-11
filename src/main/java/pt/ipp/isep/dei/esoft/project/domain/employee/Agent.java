package pt.ipp.isep.dei.esoft.project.domain.employee;


/**
 * The type Agent.
 */
public class Agent extends Employee {

    /**
     * Instantiates a new Agent.
     */
    public Agent() {
        super();
    }

    /**
     * Method used to check if the object is equal to the other object
     *
     * @param outroObjeto Object to comparate to the Agent
     * @return true if the objects are the same and false if the objects are different
     */
    public boolean equals(Object outroObjeto) {
        if (this == outroObjeto) {
            return true;
        }
        if (outroObjeto == null || getClass() != outroObjeto.getClass()) {
            return false;
        }
        Employee outroAgent = (Employee) outroObjeto;
        return super.equals(outroAgent);
    }

}
