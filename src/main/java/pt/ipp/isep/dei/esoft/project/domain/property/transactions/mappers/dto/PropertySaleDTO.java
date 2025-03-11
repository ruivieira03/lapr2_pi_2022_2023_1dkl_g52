package pt.ipp.isep.dei.esoft.project.domain.property.transactions.mappers.dto;

import pt.ipp.isep.dei.esoft.project.domain.employee.mappers.dto.AgentDTO;
import pt.ipp.isep.dei.esoft.project.domain.property.mappers.dto.*;
import pt.ipp.isep.dei.esoft.project.domain.property.transactions.PropertySale;
import pt.ipp.isep.dei.esoft.project.domain.property.transactions.PropertySaleRequest;

import java.time.LocalDate;

/**
 * used as property sale data transfer object
 */
public class PropertySaleDTO extends PropertySaleRequestDTO{
    /**
     * property in sale business commission type
     */
    public PropertySale.CommissionType typeOfCommission;

    /**
     * property in sale comission
     */
    public double commission;
    /**
     * The Contract duration.
     */
    public String contractDuration;
    /**
     * The Price.
     */
    public double price;


    /**
     * Instantiates a new Property sale dto.
     *
     * @param property         the property
     * @param typeOfBusiness   the type of business
     * @param requestedPrice   the requested price
     * @param agent            the agent
     * @param dateOfRequest    the date of request
     * @param typeOfCommission the type of commission
     * @param commission       the commission
     */
    public PropertySaleDTO(PropertyDTO property, PropertySaleRequest.TypesOfBusinesses typeOfBusiness, float requestedPrice, AgentDTO agent, LocalDate dateOfRequest, PropertySale.CommissionType typeOfCommission, double commission) {
        super(property, typeOfBusiness, requestedPrice, agent, dateOfRequest);
        this.typeOfCommission = typeOfCommission;
        this.commission = commission;
    }

    /**
     * Instantiates a new Property sale dto.
     *
     * @param id               the id
     * @param property         the property
     * @param typeOfBusiness   the type of business
     * @param requestedPrice   the requested price
     * @param agent            the agent
     * @param dateOfRequest    the date of request
     * @param typeOfCommission the type of commission
     * @param commission       the commission
     * @param contractDuration the contract duration
     */
    public PropertySaleDTO(int id, PropertyDTO property, PropertySaleRequest.TypesOfBusinesses typeOfBusiness, float requestedPrice, AgentDTO agent, LocalDate dateOfRequest, PropertySale.CommissionType typeOfCommission, double commission, String contractDuration) {
        super(id, property, typeOfBusiness, requestedPrice, agent, dateOfRequest);
        this.typeOfCommission = typeOfCommission;
        this.commission = commission;
        this.contractDuration = contractDuration;

    }

    /**
     * Empty constructor for DTO
     */
    public PropertySaleDTO() {
        this.typeOfCommission = null;
        this.commission = 0;
    }

    public String toString(){
        String propertyType = "Land";

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


        return String.format("%9s|%10s|%10.0f|%20s|%20s|%20s|%40s|%10s|%10.0f|%20.2f|%10s|%11s|%9s|%30s|%10s|%18s|%14s|",
                propertyType, typeOfBusiness.toString(), price, property.state, property.district, property.city, property.street,
                property.zipCode, property.area, property.distanceFromCityCenter, numberOfBedrooms, numberOfBathrooms, numberOfParkingSpaces, availableEquipment, basement,
                inhabitableLoft, sunExposure);
    }
}
