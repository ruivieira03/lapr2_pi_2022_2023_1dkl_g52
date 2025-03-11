package pt.ipp.isep.dei.esoft.project.domain.property;

/**
 * The type Land.
 */
public class Land extends Property {

    /**
     * Instantiates a new Land.
     */
    public Land(){
        super();
    }


    /**
     * Method used to check if one object is equal to another
     *
     * @param outroObjeto object to be compared to the initial object
     * @return true if the objects are the same or false if the objects are different
     */
    @Override
    public boolean equals(Object outroObjeto) {
        if (this == outroObjeto) {
            return true;
        }
        if (outroObjeto == null || getClass() != outroObjeto.getClass()) {
            return false;
        }
        return super.equals(outroObjeto);

    }
}
