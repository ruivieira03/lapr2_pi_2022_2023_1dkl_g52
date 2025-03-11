package pt.ipp.isep.dei.esoft.project.domain.locations;

import pt.ipp.isep.dei.esoft.project.util.error.OperationResult;

import java.io.Serializable;
import java.util.regex.Pattern;

/**
 * The type Location.
 */
public abstract class Location implements Serializable {
    /**
     * location name
     */
    private String name;

    /**
     * Empty Constructor
     */
    public Location() {

    }

    /**
     * Method used to get a location name
     *
     * @return string representing the location name
     */
    public String getName() {
        return name;
    }

    /**
     * Method used to change a location name
     *
     * @param name location name
     */
    public void setName(String name) {
        this.name = name;
    }


    /**
     * Method to check if the location is valid
     *
     * @return true if it is valid or false if not
     */
    public OperationResult isValid() {
        if (!isNameValid())
            return OperationResult.failed("Error - Location - Invalid name!");
        return OperationResult.successfull();
    }

    /**
     * Method used to check if a name is valid
     * @return true if it is valid or false if not
     */
    private boolean isNameValid() {
        String nameRegex = "[a-zA-z0-9.\\p{javaWhitespace}]*";
        Pattern pat = Pattern.compile(nameRegex);
        return pat.matcher(name).matches();
    }
    /**
     * Method used to check if two states are equal
     * @param obj state
     * @return true if they are equal or false if not
     */
    public boolean equals(Object obj) {
        if (obj == null)  return false;
        if (obj.getClass() != getClass()) return false;
        if (obj == this) return true;

        Location other = (Location) obj;
        return (getName().equals(other.getName()) );
    }

    /**
     * Method used to display the location name
     * @return
     */
    public String toString() {return name;}

    /**
     * Method used to compare alphabetically locations name
     *
     * @param other the other
     * @return positive int if the first name comes alphabetically after the second, 0 if they have the same name and negative int if the first name comes alphabetically before the second name
     */
    public  int compareTo(Location other) {
        return name.toLowerCase().compareTo(other.name.toLowerCase());
    }
}
