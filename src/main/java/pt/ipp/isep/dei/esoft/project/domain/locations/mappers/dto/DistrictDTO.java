package pt.ipp.isep.dei.esoft.project.domain.locations.mappers.dto;

import java.util.ArrayList;
import java.util.List;

/**
 * Used as Distric data transfer object
 */
public class DistrictDTO extends LocationDTO{
    /**
     * List of Cities
     */
    public List<CityDTO> cityList;

    /**
     * Instantiates a new District dto.
     *
     * @param name           the name
     * @param citiesNameList the cities name list
     */
    public DistrictDTO(String name, String[] citiesNameList) {
        if (name == null || citiesNameList == null) throw new IllegalArgumentException("Arguments cannot be null!");
        if (citiesNameList.length == 0) throw new IllegalArgumentException("citiesNameList cannot have size 0!");

        this.name = name;
        this.cityList = new ArrayList<>();

        for (String cName : citiesNameList) {
            CityDTO c = new CityDTO(cName);
            cityList.add(c);
        }
    }

    /**
     * Constructor for new District
     *
     * @param name the name
     */
    public DistrictDTO(String name) {
        super(name);

        this.cityList = new ArrayList<>();
    }

    /**
     * Empty constructor
     */
    public DistrictDTO() {
        this.name = null;
        this.cityList = new ArrayList<>();
    }
}
