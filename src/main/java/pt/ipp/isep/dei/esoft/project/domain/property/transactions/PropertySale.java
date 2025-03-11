package pt.ipp.isep.dei.esoft.project.domain.property.transactions;

import pt.ipp.isep.dei.esoft.project.util.error.OperationResult;

import java.util.Comparator;

/**
 * The type Property sale.
 */
public class PropertySale extends PropertySaleRequest {

    /**
     * The enum Commission type.
     */
    public enum CommissionType {
        /**
         * Percentage commission type.
         */
        PERCENTAGE,
        /**
         * Fixed commission type.
         */
        FIXED
    }

    /**
     * property in sale business commission type
     */
    private PropertySale.CommissionType typeOfCommission;

    /**
     * property in sale commission
     */
    private double commission;

    /**
     * Property price
     */
    private double price;;

    /**
     * Instantiates a new Property sale.
     */
    public PropertySale() {
        super();
    }


    /**
     * Method used to get the property business commission type
     *
     * @return int represent the commission type
     */
    public PropertySale.CommissionType getTypeOfCommission() {
        return typeOfCommission;
    }

    /**
     * Method used to get the property business comission
     *
     * @return double represent the commission
     */
    public double getCommission() {
        return commission;
    }

    /**
     * Gets price.
     *
     * @return the price
     */
    public double getPrice() {
        return price;
    }

    /**
     * Method used to change the property business commission type
     *
     * @param typeOfCommission commission type
     */
    public void setTypeOfCommission(PropertySale.CommissionType typeOfCommission) {
        this.typeOfCommission = typeOfCommission;
    }

    /**
     * Sets price.
     *
     * @param price the price
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * Method used to change the property business commission
     *
     * @param commission agent commission
     */
    public void setCommission(double commission) {
        this.commission = commission;
    }

    /**
     * Method used to check if one object is equal to another
     *
     * @param outroObjeto object to be compared to the initial object
     * @return true if the objects are the same or false if the objects are different
     */
    @Override
    public boolean equals(Object outroObjeto) {
        if (this == outroObjeto) return true;
        if (outroObjeto == null || getClass() != outroObjeto.getClass()) return false;
        PropertySale outraPropertySale = (PropertySale) outroObjeto;
        return super.equals(outroObjeto) && this.commission == outraPropertySale.commission
                && this.typeOfCommission == outraPropertySale.typeOfCommission;

    }

    /**
     * Method used to calculate the property price
     *
     * @return double representing the property price
     */
    public double calculatePrice() {
        if (typeOfCommission.equals(CommissionType.PERCENTAGE))
            return getRequestedPrice() + getRequestedPrice() * commission;

        else return getRequestedPrice() + commission;
    }

    /**
     * Method used to check if the property sale attributes are valid
     *
     * @return true if the property sale attributes are valid or false if not valid
     */
    public OperationResult isValid() {

        if (typeOfCommission == null || !(typeOfCommission.equals(CommissionType.FIXED) || typeOfCommission.equals(CommissionType.PERCENTAGE)))
            return OperationResult.failed("Error - PropertySale - Invalid Commission type!");

        if (commission <= 0)
            return OperationResult.failed("Error - PropertySale - Commission must be higher than 0!");

        return super.isValid();
    }

    /**
     * Method used to display the property sale attributes
     *
     * @return string with the property sale attributes
     */
    public String toString() {
        return getProperty().toString() + " price: " + calculatePrice();
    }

    /**
     * Class to be used as a comparator
     */
    public static class SortByPriceComparator implements Comparator<PropertySale> {
        /**
         * Method used to compare two objects at their price
         *
         * @param p1 the first object to be compared.
         * @param p2 the second object to be compared.
         * @return positive int if p1 has higher price that p2, 0 if p1 has the same price as p2 and negative int if p1 has lower price that p2
         */
        public int compare(PropertySale p1, PropertySale p2) {
            return Double.compare(p1.calculatePrice(), p2.calculatePrice());
        }
    }

    /**
     * The type Sort by date comparator.
     */
    public static class SortByDateComparator implements Comparator<PropertySale> {

        @Override
        public int compare(PropertySale o1, PropertySale o2) {
            return o1.getDateOfRequest().compareTo(o2.getDateOfRequest());
        }
    }
}
