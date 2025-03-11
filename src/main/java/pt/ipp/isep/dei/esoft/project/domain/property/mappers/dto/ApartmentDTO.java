package pt.ipp.isep.dei.esoft.project.domain.property.mappers.dto;

import pt.ipp.isep.dei.esoft.project.domain.client.mappers.dto.ClientDTO;
import pt.ipp.isep.dei.esoft.project.domain.locations.mappers.dto.CityDTO;
import pt.ipp.isep.dei.esoft.project.domain.locations.mappers.dto.DistrictDTO;
import pt.ipp.isep.dei.esoft.project.domain.locations.mappers.dto.StateDTO;

/**
 * used as apartment data transfer object
 */
public class ApartmentDTO extends ResidentialPropertyDTO {

    /**
     * Instantiates a new Apartment dto.
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
    public ApartmentDTO(double area, double distanceFromCityCenter, String street, int zipCode, StateDTO state, DistrictDTO district, CityDTO city, String photos, ClientDTO client, int numberOfBedrooms, int numberOfBathrooms, int numberOfParkingSpaces, String availableEquipment) {
        super(area, distanceFromCityCenter, street, zipCode, state, district, city, photos, client, numberOfBedrooms, numberOfBathrooms, numberOfParkingSpaces, availableEquipment);
    }

    /**
     * Empty constructor for DTO
     */
    public ApartmentDTO() {
        super();
    }
}
