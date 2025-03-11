package pt.ipp.isep.dei.esoft.project.domain.property.transactions.mappers.dto;

import pt.ipp.isep.dei.esoft.project.domain.client.mappers.dto.ClientDTO;
import pt.ipp.isep.dei.esoft.project.domain.property.mappers.dto.HouseDTO;
import pt.ipp.isep.dei.esoft.project.domain.property.mappers.dto.ResidentialPropertyDTO;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * The type Visit request dto.
 */
public class VisitRequestDTO {

    /**
     * The id.
     */
    public int id;

    /**
     * The Client.
     */
    public ClientDTO client;

    /**
     * The Property sale.
     */
    public PropertySaleDTO propertySale;

    /**
     * The Visit start.
     */
    public LocalDateTime visitStart;

    /**
     * The Visit end.
     */
    public LocalDateTime visitEnd;

    /**
     * Instantiates a new Visit request dto.
     *
     * @param client       the client
     * @param propertySale the property sale
     * @param visitStart   the visit start
     * @param visitEnd     the visit end
     */
    public VisitRequestDTO(ClientDTO client, PropertySaleDTO propertySale, LocalDateTime visitStart, LocalDateTime visitEnd) {
        this.client = client;
        this.propertySale = propertySale;
        this.visitStart = visitStart;
        this.visitEnd = visitEnd;
    }


    /**
     * Instantiates a new Visit request dto.
     */
    public VisitRequestDTO() {
    }

    @Override
    public String toString() {
        DateTimeFormatter formatterHour = DateTimeFormatter.ofPattern("HH:mm");
        DateTimeFormatter formatterDate = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        String visitDayFormat = visitStart.format(formatterDate);
        String visitStartFormat = visitStart.format(formatterHour);
        String visitEndFormat = visitEnd.format(formatterHour);
        String propertyType = "Land";
        String clientName = propertySale.property.client.name;
        String numberOfBedrooms = "";
        String numberOfBathrooms = "";
        String numberOfParkingSpaces = "";
        String availableEquipment = "";
        String basement = "";
        String inhabitableLoft = "";
        String sunExposure = "";

        if (propertySale.property instanceof ResidentialPropertyDTO) {
            propertyType = "Apartment";
            numberOfBedrooms = String.valueOf(((ResidentialPropertyDTO) propertySale.property).numberOfBedrooms);
            numberOfBathrooms = String.valueOf(((ResidentialPropertyDTO) propertySale.property).numberOfBathrooms);
            numberOfParkingSpaces =String.valueOf(((ResidentialPropertyDTO) propertySale.property).numberOfParkingSpaces);
            availableEquipment = ((ResidentialPropertyDTO) propertySale.property).availableEquipment;
        }

        if (propertySale.property instanceof HouseDTO) {
            propertyType = "House";
            basement = "No";
            inhabitableLoft = "No";

            if (((HouseDTO) propertySale.property).existanceOfBasement) basement = "Yes";
            if (((HouseDTO) propertySale.property).existanceOfInhabitableLoft) inhabitableLoft = "Yes";
            sunExposure = ((HouseDTO) propertySale.property).sunExposure.toString();

        }


        return String.format("%20s|%9s|%10s|%16.0f|%20s|%20s|%20s|%40s|%10s|%10.0f|%20.2f|%10s|%11s|%9s|%30s|%10s|%18s|%14s|%12s|%12s|%12s|",
                clientName, propertyType, propertySale.typeOfBusiness.toString(), propertySale.requestedPrice, propertySale.property.state, propertySale.property.district, propertySale.property.city, propertySale.property.street,
                propertySale.property.zipCode, propertySale.property.area, propertySale.property.distanceFromCityCenter, numberOfBedrooms, numberOfBathrooms, numberOfParkingSpaces, availableEquipment, basement,
                inhabitableLoft, sunExposure, visitDayFormat, visitStartFormat, visitEndFormat);

    }
}
