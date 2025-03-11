package pt.ipp.isep.dei.esoft.project.domain.property.mappers.dto;

import pt.ipp.isep.dei.esoft.project.domain.client.mappers.dto.ClientDTO;
import pt.ipp.isep.dei.esoft.project.domain.locations.mappers.dto.CityDTO;
import pt.ipp.isep.dei.esoft.project.domain.locations.mappers.dto.DistrictDTO;
import pt.ipp.isep.dei.esoft.project.domain.locations.mappers.dto.StateDTO;
import pt.ipp.isep.dei.esoft.project.domain.property.House;

/**
 * used as house data transfer DTO
 */
public class HouseDTO extends ResidentialPropertyDTO{
    /**
     * existance of basement in a house
     */
    public boolean existanceOfBasement;
    /**
     * existance of inhabitable loft in a house
     */
    public boolean existanceOfInhabitableLoft;
    /**
     * sun exposure of a house
     */
    public House.SunExposure sunExposure;

    /**
     * Instantiates a new House dto.
     *
     * @param area                       the area
     * @param distanceFromCityCenter     the distance from city center
     * @param street                     the street
     * @param zipCode                    the zip code
     * @param state                      the state
     * @param district                   the district
     * @param city                       the city
     * @param photos                     the photos
     * @param client                     the client
     * @param numberOfBedrooms           the number of bedrooms
     * @param numberOfBathrooms          the number of bathrooms
     * @param numberOfParkingSpaces      the number of parking spaces
     * @param availableEquipment         the available equipment
     * @param existanceOfBasement        the existance of basement
     * @param existanceOfInhabitableLoft the existance of inhabitable loft
     * @param sunExposure                the sun exposure
     */
    public HouseDTO(double area, double distanceFromCityCenter, String street, int zipCode, StateDTO state, DistrictDTO district, CityDTO city, String photos, ClientDTO client, int numberOfBedrooms, int numberOfBathrooms, int numberOfParkingSpaces, String availableEquipment, boolean existanceOfBasement, boolean existanceOfInhabitableLoft, House.SunExposure sunExposure) {
        super(area, distanceFromCityCenter, street, zipCode, state, district, city, photos, client, numberOfBedrooms, numberOfBathrooms, numberOfParkingSpaces, availableEquipment);
        this.existanceOfBasement = existanceOfBasement;
        this.existanceOfInhabitableLoft = existanceOfInhabitableLoft;
        this.sunExposure = sunExposure;
    }

    /**
     * Empty constructor for DTO
     */
    public HouseDTO() {
        super();
        this.existanceOfBasement = false;
        this.existanceOfInhabitableLoft = false;
        this.sunExposure = null;
    }

}
