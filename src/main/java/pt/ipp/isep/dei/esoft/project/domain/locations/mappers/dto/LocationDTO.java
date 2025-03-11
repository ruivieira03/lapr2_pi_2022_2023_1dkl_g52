package pt.ipp.isep.dei.esoft.project.domain.locations.mappers.dto;

/**
 * Used as Location data transfer object
 */
public abstract class LocationDTO {
    /**
     * location name
     */
    public String name;

    /**
     * Method used to create locations
     *
     * @param name location name
     */
    public LocationDTO(String name) {
        if (name == null) throw new IllegalArgumentException("Name can not be null!");

        this.name = name;
    }

    /**
     * Method used to create a Location
     */
    public LocationDTO() {
        this.name = null;
    }

    public String toString() {
        return this.name;
    }
}
