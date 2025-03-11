package pt.ipp.isep.dei.esoft.project.domain.locations.mappers.dto;

/**
 * Used as City data transfer object
 */
public class CityDTO extends LocationDTO{

    /**
     * Constructor for new City
     *
     * @param name String - city name
     */
    public CityDTO(String name) {
        super(name);
    }

    /**
     * Empty Constructor
     */
    public CityDTO() {
        this.name = null;
    }
}
