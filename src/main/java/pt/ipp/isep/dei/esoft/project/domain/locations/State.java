package pt.ipp.isep.dei.esoft.project.domain.locations;

import pt.ipp.isep.dei.esoft.project.util.error.OperationResult;

import java.util.ArrayList;
import java.util.List;

/**
 * The type State.
 */
public class State extends Location {
    /**
     * List of districts
     */
    private List<District> locations;

    /**
     * Empty Constructor
     */
    public State() {
        super();
    }


    /**
     * Method used to get the districts list
     *
     * @return List<Districts> districts
     */
    public List<District> getDistricts() {
        return new ArrayList<>(locations);
    }

    /**
     * Method used to set districts list
     *
     * @param districtList List<District>
     */
    public void setDistricts(List<District> districtList) {
        this.locations = new ArrayList<>(districtList);
    }

    /**
     * Method to check if a state is valid
     *
     * @return true if it is valid or false if not
     */
    public OperationResult isValid() {
        if (!super.isValid().success()) return super.isValid();

        if (locations != null) {
            for (District district: locations) {
                if (!district.isValid().success()) return district.isValid();
            }
        }

        return OperationResult.successfull();
    }
}
