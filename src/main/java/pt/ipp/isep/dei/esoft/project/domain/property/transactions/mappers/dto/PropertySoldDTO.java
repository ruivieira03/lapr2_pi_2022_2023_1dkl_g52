package pt.ipp.isep.dei.esoft.project.domain.property.transactions.mappers.dto;

import pt.ipp.isep.dei.esoft.project.domain.client.mappers.dto.ClientDTO;
import pt.ipp.isep.dei.esoft.project.domain.employee.mappers.dto.AgentDTO;
import pt.ipp.isep.dei.esoft.project.domain.property.mappers.dto.HouseDTO;
import pt.ipp.isep.dei.esoft.project.domain.property.mappers.dto.PropertyDTO;
import pt.ipp.isep.dei.esoft.project.domain.property.mappers.dto.ResidentialPropertyDTO;
import pt.ipp.isep.dei.esoft.project.domain.property.transactions.PropertySale;
import pt.ipp.isep.dei.esoft.project.domain.property.transactions.PropertySaleRequest;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * The type Property sold dto.
 */
public class PropertySoldDTO extends PropertySaleDTO {
    /**
     * The Paid amount.
     */
    public double paidAmount;
    /**
     * The Buyer.
     */
    public ClientDTO buyer;
    /**
     * The Purchase date.
     */
    public LocalDateTime purchaseDate;

    /**
     * Instantiates a new Property sold dto.
     *
     * @param property         the property
     * @param typeOfBusiness   the type of business
     * @param requestedPrice   the requested price
     * @param agent            the agent
     * @param dateOfRequest    the date of request
     * @param typeOfCommission the type of commission
     * @param commission       the commission
     * @param paidAmount       the paid amount
     * @param buyer            the buyer
     * @param purchaseDate     the purchase date
     */
    public PropertySoldDTO(PropertyDTO property, PropertySaleRequest.TypesOfBusinesses typeOfBusiness, float requestedPrice, AgentDTO agent, LocalDate dateOfRequest, PropertySale.CommissionType typeOfCommission, double commission, double paidAmount, ClientDTO buyer, LocalDateTime purchaseDate) {
        super(property, typeOfBusiness, requestedPrice, agent, dateOfRequest, typeOfCommission, commission);
        this.paidAmount = paidAmount;
        this.buyer = buyer;
        this.purchaseDate = purchaseDate;
    }

    /**
     * Instantiates a new Property sold dto.
     */
    public PropertySoldDTO() {
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


        return String.format("%9s|%10s|%10.0f|%12.0f|%20s|%23s|%20s|%40s|%10s|%10.0f|%20.2f|%10s|%11s|%9s|%30s|%10s|%18s|%14s|",
                propertyType, typeOfBusiness.toString(), price, paidAmount, property.state, property.district, property.city, property.street,
                property.zipCode, property.area, property.distanceFromCityCenter, numberOfBedrooms, numberOfBathrooms, numberOfParkingSpaces, availableEquipment, basement,
                inhabitableLoft, sunExposure);
    }
}