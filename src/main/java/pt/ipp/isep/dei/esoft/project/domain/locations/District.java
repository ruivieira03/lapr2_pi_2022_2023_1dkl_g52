package pt.ipp.isep.dei.esoft.project.domain.locations;

import pt.ipp.isep.dei.esoft.project.util.error.OperationResult;

import java.util.ArrayList;
import java.util.List;

/**
 * The type District.
 */
public class District extends Location {
    /**
     * list of cities
     */
    private List<City> locations;


    /**
     * Instantiates a new District.
     */
    public District() {
        super();
        this.locations = new ArrayList<>();
    }

    /**
     * Method used to get the list of cities
     *
     * @return list of cities
     */
    public List<City> getCities() {
        return new ArrayList<>(locations);
    }

    /**
     * Sets cities.
     *
     * @param locations the locations
     */
    public void setCities(List<City> locations) {
        this.locations = new ArrayList<>(locations);
    }

    /**
     * Method used to check if a district is valid
     * @return true if it is valid or false if not
     */
    public OperationResult isValid() {
        if (!super.isValid().success()) return super.isValid();

        if (locations.size() == 0)
            return OperationResult.failed("Error - District - District must have at least one city!");

        for (City city : locations) {
            if (!city.isValid().success()) return city.isValid();
        }

        return OperationResult.successfull();
    }
}
