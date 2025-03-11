package pt.ipp.isep.dei.esoft.project.domain.property.transactions;

import pt.ipp.isep.dei.esoft.project.domain.employee.Agent;
import pt.ipp.isep.dei.esoft.project.domain.property.Property;
import pt.ipp.isep.dei.esoft.project.util.error.OperationResult;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Comparator;

/**
 * The type Property sale request.
 */
public class PropertySaleRequest implements Serializable {
    /**
     * enum that represents the types of business (rent)
     */
    public enum TypesOfBusinesses {
        /**
         * Rent types of businesses.
         */
        RENT("Rent"),
        /**
         * Sell types of businesses.
         */
        SELL("Sale");

        private final String label;

        TypesOfBusinesses(String label) {
            this.label = label;
        }

        /**
         * Gets label.
         *
         * @return the label
         */
        public String getLabel() {
            return label;
        }

        /**
         * From label types of businesses.
         *
         * @param label the label
         * @return the types of businesses
         */
        public static TypesOfBusinesses fromLabel(String label) {
            for (TypesOfBusinesses type : TypesOfBusinesses.values()) {
                if (type.getLabel().equalsIgnoreCase(label)) {
                    return type;
                }
            }
            throw new IllegalArgumentException("Invalid label: " + label);
        }

    }

    /**
     * property the to which the request refers
     */
    private final int id;

    private Property property;
    /**
     * property business type
     */
    private TypesOfBusinesses typeOfBusiness;
    /**
     * requested price for the property
     */
    private double requestedPrice;
    /**
     * agent responsible for the property sale
     */
    private Agent agent;

    private LocalDate dateOfRequest;

    private static int CURRENT_ID = 1;


    /**
     * Instantiates a new Property sale request.
     */
    public PropertySaleRequest() {
        this.id = CURRENT_ID;
        CURRENT_ID++;
    }

    /**
     * Gets id.
     *
     * @return the id
     */
    public int getID() {
        return this.id;
    }

    /**
     * Method used to get the request price for a property
     *
     * @return double representing the requested price
     */
    public double getRequestedPrice() {
        return requestedPrice;
    }

    /**
     * Method used to get the request price for a property
     *
     * @return double representing the requested price
     */
    public TypesOfBusinesses getTypeOfBusiness() {
        return typeOfBusiness;
    }

    /**
     * Method used to get the property
     *
     * @return property property
     */
    public Property getProperty() {
        return property;
    }

    /**
     * Method used to get the agent
     *
     * @return agent agent
     */
    public Agent getAgent() {
        return agent;
    }

    /**
     * Gets date of request.
     *
     * @return the date of request
     */
    public LocalDate getDateOfRequest() {
        return dateOfRequest;
    }


    /**
     * Method used to set the property
     *
     * @param property property
     */
    public void setProperty(Property property) {
        this.property = property;
    }

    /**
     * Method used to set the requested price
     *
     * @param requestedPrice requested price
     */
    public void setRequestedPrice(double requestedPrice) {
        this.requestedPrice = requestedPrice;
    }

    /**
     * Method used to set the type of business
     *
     * @param typeOfBusiness type of business
     */
    public void setTypeOfBusiness(TypesOfBusinesses typeOfBusiness) {
        this.typeOfBusiness = typeOfBusiness;
    }

    /**
     * Sets agent.
     *
     * @param agent the agent
     */
    public void setAgent(Agent agent) {
        this.agent = agent;
    }

    /**
     * Sets date of request.
     *
     * @param dateOfRequest the date of request
     */
    public void setDateOfRequest(LocalDate dateOfRequest) {
        this.dateOfRequest = dateOfRequest;
    }

    /**
     * Method used to check if one object is equal to another
     *
     * @param outroObjeto
     * @return true if the objects are the same or false if the objects are different
     */
    @Override
    public boolean equals(Object outroObjeto) {
        if (this == outroObjeto) {
            return true;
        }
        if (outroObjeto == null || getClass() != outroObjeto.getClass()) {
            return false;
        }
        PropertySaleRequest outraPropertySale = (PropertySaleRequest) outroObjeto;
        return this.property.equals(outraPropertySale.property)
                && this.requestedPrice == outraPropertySale.requestedPrice
                && this.typeOfBusiness == outraPropertySale.typeOfBusiness
                && this.agent.equals(outraPropertySale.agent);

    }

    /**
     * Method used to check if the request attributes are valid
     *
     * @return true if the attributes are valid or false if not valid
     */
    public OperationResult isValid() {
        if (typeOfBusiness == null)
            return OperationResult.failed("Error - PropertySaleRequest - Invalid Business type!");

        if (requestedPrice < 0)
            return OperationResult.failed("Error - PropertySaleRequest - RequestedPrice must be higher than 0!");

        return property.isValid();
    }


    /**
     * Class to be used as a comparator
     */
    public static class SortByCityComparator implements Comparator<PropertySaleRequest> {

        /**
         * Method used to compare the cities of the two objects.
         *
         * @param p1 the first request to be compared.
         * @param p2 the second request to be compared.
         * @return if the city of request p1 is alphabetically earlier than the request p2, 0 if the cities of the two requests are the same and negative int if the city of request p1 is after alphabetically than the city of request p2.
         */
        public int compare(PropertySaleRequest p1, PropertySaleRequest p2) {
            return p1.property.getCity().compareTo(p2.property.getCity());
        }
    }

}
