package pt.ipp.isep.dei.esoft.project.domain.locations.mappers.dto;

import java.util.ArrayList;
import java.util.List;

/**
 * Used as State data transfer object
 */
public class StateDTO extends LocationDTO {
    /**
     * List of districts
     */
    public List<DistrictDTO> districtList;


    /**
     * Instantiates a new State dto.
     *
     * @param name             the name
     * @param districtNameList the district name list
     * @param citiesNamesArray the cities names array
     */
    public StateDTO(String name, String[] districtNameList, String[][] citiesNamesArray) {
        if (name == null || districtNameList == null || citiesNamesArray == null) throw new IllegalArgumentException("Arguments cannot be null!");
        if (districtNameList.length != citiesNamesArray.length) throw new IllegalArgumentException("DistrictNameList must have the same length as citiesNameArray");
        if (districtNameList.length == 0) throw new IllegalArgumentException("districtNameList cannot have length 0!");

        this.name = name;
        this.districtList = new ArrayList<>();

        for (int i = 0; i < districtNameList.length; i++) {
            DistrictDTO d = new DistrictDTO(districtNameList[i], citiesNamesArray[i]);
            this.districtList.add(d);
        }
    }

    /**
     * Constructor for new State
     *
     * @param name String - State name
     */
    public StateDTO(String name) {
        super(name);

        this.districtList = new ArrayList<>();
    }

    /**
     * Empty constructor
     */
    public StateDTO() {
        this.name = null;
        this.districtList = new ArrayList<>();
    }
}
