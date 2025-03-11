package pt.ipp.isep.dei.esoft.project.domain.property.transactions.mappers.dto;

import pt.ipp.isep.dei.esoft.project.domain.employee.mappers.dto.AgentDTO;
import pt.ipp.isep.dei.esoft.project.domain.property.mappers.dto.ApartmentDTO;
import pt.ipp.isep.dei.esoft.project.domain.property.mappers.dto.HouseDTO;
import pt.ipp.isep.dei.esoft.project.domain.property.mappers.dto.PropertyDTO;
import pt.ipp.isep.dei.esoft.project.domain.property.mappers.dto.ResidentialPropertyDTO;
import pt.ipp.isep.dei.esoft.project.domain.property.transactions.PropertySaleRequest;

import java.time.LocalDate;

/**
 * The type Property sale request dto.
 */
public class PropertySaleRequestDTO {

    /**
     * The id.
     */
    public int id;

    /**
     * property the to wich the request refers
     */
    public PropertyDTO property;
    /**
     * property business type
     */
    public PropertySaleRequest.TypesOfBusinesses typeOfBusiness;
    /**
     * requested price for the property
     */
    public double requestedPrice;

    /**
     * agent responsible for the property sale
     */
    public AgentDTO agent;

    /**
     * The Date of request.
     */
    public LocalDate dateOfRequest;

    /**
     * Instantiates a new Property sale request dto.
     *
     * @param id             the id
     * @param property       the property
     * @param typeOfBusiness the type of business
     * @param requestedPrice the requested price
     * @param agent          the agent
     * @param dateOfRequest  the date of request
     */
    public PropertySaleRequestDTO(int id, PropertyDTO property, PropertySaleRequest.TypesOfBusinesses typeOfBusiness, float requestedPrice, AgentDTO agent, LocalDate dateOfRequest) {
        this.id = id;
        this.property = property;
        this.typeOfBusiness = typeOfBusiness;
        this.requestedPrice = requestedPrice;
        this.agent = agent;
        this.dateOfRequest = dateOfRequest;
    }

    /**
     * Instantiates a new Property sale request dto.
     *
     * @param property       the property
     * @param typeOfBusiness the type of business
     * @param requestedPrice the requested price
     * @param agent          the agent
     * @param dateOfRequest  the date of request
     */
    public PropertySaleRequestDTO(PropertyDTO property, PropertySaleRequest.TypesOfBusinesses typeOfBusiness, float requestedPrice, AgentDTO agent, LocalDate dateOfRequest) {
        this.property = property;
        this.typeOfBusiness = typeOfBusiness;
        this.requestedPrice = requestedPrice;
        this.agent = agent;
        this.dateOfRequest = dateOfRequest;
    }


    /**
     * Instantiates a new Property sale request dto.
     */
    public PropertySaleRequestDTO(){

    }

    public String toString() {
        String propertyType = "Land";
        String clientName = property.client.name;
        String numberOfBedrooms = "";
        String numberOfBathrooms = "";
        String numberOfParkingSpaces = "";
        String availableEquipment = "";
        String basement = "";
        String inhabitableLoft = "";
        String sunExposure = "";

        if (property instanceof ResidentialPropertyDTO) {
            propertyType = "Apartment";
            numberOfBedrooms = String.valueOf(((ResidentialPropertyDTO) property).numberOfBedrooms);
            numberOfBathrooms = String.valueOf(((ResidentialPropertyDTO) property).numberOfBathrooms);
            numberOfParkingSpaces =String.valueOf(((ResidentialPropertyDTO) property).numberOfParkingSpaces);
            availableEquipment = ((ResidentialPropertyDTO) property).availableEquipment;
        }

        if (property instanceof HouseDTO) {
            propertyType = "House";
            basement = "No";
            inhabitableLoft = "No";

            if (((HouseDTO) property).existanceOfBasement) basement = "Yes";
            if (((HouseDTO) property).existanceOfInhabitableLoft) inhabitableLoft = "Yes";
            sunExposure = ((HouseDTO) property).sunExposure.toString();

        }


        return String.format("%20s|%9s|%10s|%16.0f|%20s|%20s|%20s|%40s|%10s|%10.0f|%20.2f|%10s|%11s|%9s|%30s|%10s|%18s|%14s|",
                clientName, propertyType, typeOfBusiness.toString(), requestedPrice, property.state, property.district, property.city, property.street,
                property.zipCode, property.area, property.distanceFromCityCenter, numberOfBedrooms, numberOfBathrooms, numberOfParkingSpaces, availableEquipment, basement,
                inhabitableLoft, sunExposure);

    }
}
