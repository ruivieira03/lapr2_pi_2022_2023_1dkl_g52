package pt.ipp.isep.dei.esoft.project.domain.property.mappers.dto;

import pt.ipp.isep.dei.esoft.project.domain.client.mappers.dto.ClientDTO;
import pt.ipp.isep.dei.esoft.project.domain.locations.mappers.dto.CityDTO;
import pt.ipp.isep.dei.esoft.project.domain.locations.mappers.dto.DistrictDTO;
import pt.ipp.isep.dei.esoft.project.domain.locations.mappers.dto.StateDTO;

/**
 * used as Land data transfer object
 */
public class LandDTO extends PropertyDTO{

    /**
     * Instantiates a new Land dto.
     *
     * @param area                   the area
     * @param distanceFromCityCenter the distance from city center
     * @param street                 the street
     * @param zipCode                the zip code
     * @param state                  the state
     * @param district               the district
     * @param city                   the city
     * @param photos                 the photos
     * @param client                 the client
     */
    public LandDTO(double area, double distanceFromCityCenter, String street, int zipCode, StateDTO state, DistrictDTO district, CityDTO city, String photos, ClientDTO client) {
        super(area, distanceFromCityCenter, street, zipCode, state, district, city, photos, client);
    }

    /**
     * Empty constructor for DTO
     */
    public LandDTO() {
        super();
    }
}
