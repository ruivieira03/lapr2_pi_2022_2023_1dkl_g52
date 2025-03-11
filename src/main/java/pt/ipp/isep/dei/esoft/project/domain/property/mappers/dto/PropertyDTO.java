package pt.ipp.isep.dei.esoft.project.domain.property.mappers.dto;

import pt.ipp.isep.dei.esoft.project.domain.client.mappers.dto.ClientDTO;
import pt.ipp.isep.dei.esoft.project.domain.locations.mappers.dto.CityDTO;
import pt.ipp.isep.dei.esoft.project.domain.locations.mappers.dto.DistrictDTO;
import pt.ipp.isep.dei.esoft.project.domain.locations.mappers.dto.StateDTO;


/**
 * Used as property daat transfer object
 */
public abstract class PropertyDTO {
    /**
     * property area
     */
    public double area;
    /**
     * distance from the property to the city center
     */
    public double distanceFromCityCenter;
    /**
     * property street
     */
    public String street;
    /**
     * property zip code
     */
    public int zipCode;
    /**
     * state where the property is located
     */
    public StateDTO state;
    /**
     * district where the property is located
     */
    public DistrictDTO district;
    /**
     * city where the property is located
     */
    public CityDTO city;
    /**
     * property photos
     */
    public String photos;
    /**
     * property owner
     */
    public ClientDTO client;

    /**
     * Instantiates a new Property dto.
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
    public PropertyDTO(double area, double distanceFromCityCenter, String street, int zipCode, StateDTO state, DistrictDTO district, CityDTO city, String photos, ClientDTO client) {
        this.area = area;
        this.distanceFromCityCenter = distanceFromCityCenter;
        this.street = street;
        this.zipCode = zipCode;
        this.state = state;
        this.district = district;
        this.city = city;
        this.photos = photos;
        this.client = client;
    }

    /**
     * Empty constructor for DTO
     */
    public PropertyDTO() {
        this.area = 0F;
        this.street = null;
        this.zipCode = 0;
        this.distanceFromCityCenter = 0;
        this.city = null;
        this.district = null;
        this.state = null;
        this.photos = null;
        this.client = null;
    }

}
