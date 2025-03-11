package pt.ipp.isep.dei.esoft.project.domain.property.mappers.dto;

import pt.ipp.isep.dei.esoft.project.domain.client.mappers.dto.ClientDTO;
import pt.ipp.isep.dei.esoft.project.domain.locations.mappers.dto.CityDTO;
import pt.ipp.isep.dei.esoft.project.domain.locations.mappers.dto.DistrictDTO;
import pt.ipp.isep.dei.esoft.project.domain.locations.mappers.dto.StateDTO;

/**
 * used as Residential property data transfer object
 */
public abstract class ResidentialPropertyDTO extends PropertyDTO {

    /**
     * number of bedrooms in the residential property
     */
    public int numberOfBedrooms;
    /**
     * number of bathrooms in the residential property
     */
    public int numberOfBathrooms;
    /**
     * number of parking spaces in the residential property
     */
    public int numberOfParkingSpaces;
    /**
     * available equipment in the residential property
     */
    public String availableEquipment;

    /**
     * Instantiates a new Residential property dto.
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
     * @param numberOfBedrooms       the number of bedrooms
     * @param numberOfBathrooms      the number of bathrooms
     * @param numberOfParkingSpaces  the number of parking spaces
     * @param availableEquipment     the available equipment
     */
    public ResidentialPropertyDTO(double area, double distanceFromCityCenter, String street, int zipCode, StateDTO state, DistrictDTO district, CityDTO city, String photos, ClientDTO client, int numberOfBedrooms, int numberOfBathrooms, int numberOfParkingSpaces, String availableEquipment) {
        super(area, distanceFromCityCenter, street, zipCode, state, district, city, photos, client);
        this.numberOfBedrooms = numberOfBedrooms;
        this.numberOfBathrooms = numberOfBathrooms;
        this.numberOfParkingSpaces = numberOfParkingSpaces;
        this.availableEquipment = availableEquipment;
    }

    /**
     * Empty contructor for DTO
     */
    public ResidentialPropertyDTO() {
        super();
        this.numberOfBedrooms = 0;
        this.numberOfBathrooms = 0;
        this.numberOfParkingSpaces = 0;
        this.availableEquipment = null;
    }
}
