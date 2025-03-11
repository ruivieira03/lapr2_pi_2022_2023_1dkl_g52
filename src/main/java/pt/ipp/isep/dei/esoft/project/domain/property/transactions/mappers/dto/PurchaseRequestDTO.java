package pt.ipp.isep.dei.esoft.project.domain.property.transactions.mappers.dto;

import pt.ipp.isep.dei.esoft.project.domain.client.mappers.dto.ClientDTO;


/**
 * The type Purchase request dto.
 */
public class PurchaseRequestDTO {

    /**
     * The Order amount.
     */
    public float orderAmount;
    /**
     * The Client.
     */
    public ClientDTO client;
    /**
     * The Property sale.
     */
    public PropertySaleDTO propertySale;
    /**
     * The Id.
     */
    public int id;

    /**
     * Instantiates a new Purchase request dto.
     */
    public PurchaseRequestDTO(){}

    public String toString() {
        return String.format("%20s|%14s|%18s|", client.name, client.phoneNumber, orderAmount);
    }
}
