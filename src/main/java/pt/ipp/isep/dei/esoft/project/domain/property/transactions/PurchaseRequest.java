package pt.ipp.isep.dei.esoft.project.domain.property.transactions;

import pt.ipp.isep.dei.esoft.project.domain.client.Client;
import pt.ipp.isep.dei.esoft.project.util.error.OperationResult;

import java.io.Serializable;
import java.util.Comparator;

/**
 * The type Purchase request.
 */
public class PurchaseRequest implements Serializable {
    private PropertySale propertySale;
    private Client client;
    private float orderAmount;
    private int id;
    private static int CURRENT_ID = 1;

    /**
     * Instantiates a new Purchase request.
     *
     * @param propertySale the property sale
     * @param client       the client
     * @param orderAmount  the order amount
     */
    public PurchaseRequest(PropertySale propertySale, Client client, float orderAmount) {
        if (client == null) throw new IllegalArgumentException("Client not found");
        if (propertySale == null) throw new IllegalArgumentException("Property not found");
        this.orderAmount = orderAmount;
        this.client = client;
        this.propertySale = propertySale;
        this.id = CURRENT_ID;
        CURRENT_ID++;
    }

    /**
     * Instantiates a new Purchase request.
     */
    public PurchaseRequest() {
        this.id = CURRENT_ID;
        CURRENT_ID++;
    }

    /**
     * Gets id.
     *
     * @return the id
     */
    public int getID() {
        return id;
    }

    /**
     * Is valid operation result.
     *
     * @return the operation result
     */
    public OperationResult isValid() {
        if (orderAmount > propertySale.calculatePrice())
            return OperationResult.failed("Order amount can't be Higher than the price requested");

        return OperationResult.successfull();
    }

    /**
     * Gets client.
     *
     * @return the client
     */
    public Client getClient() {
        return client;
    }

    /**
     * Gets order amount.
     *
     * @return the order amount
     */
    public float getOrderAmount() {
        return orderAmount;
    }

    /**
     * Gets property sale.
     *
     * @return the property sale
     */
    public PropertySale getPropertySale() {
        return propertySale;
    }

    /**
     * Sets client.
     *
     * @param client the client
     */
    public void setClient(Client client) {
        this.client = client;
    }

    /**
     * Sets order amount.
     *
     * @param orderAmount the order amount
     */
    public void setOrderAmount(float orderAmount) {
        this.orderAmount = orderAmount;
    }

    /**
     * Sets property sale.
     *
     * @param propertySale the property sale
     */
    public void setPropertySale(PropertySale propertySale) {
        this.propertySale = propertySale;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (obj == this) return true;
        if (!(obj instanceof PurchaseRequest)) return false;

        PurchaseRequest request = (PurchaseRequest) obj;

        return propertySale.equals(request.propertySale) &&
                client.equals(request.client) &&
                orderAmount == request.orderAmount;
    }

    /**
     * Repeated client boolean.
     *
     * @param obj the obj
     * @return the boolean
     */
    public boolean repeatedClient(Object obj){
        if (!(obj instanceof PurchaseRequest)) return false;
        PurchaseRequest request = (PurchaseRequest) obj;
        return propertySale.equals(request.propertySale) && client.equals(request.client) ;

    }

    /**
     * The type Sort by price comparator.
     */
    public static class SortByPriceComparator implements Comparator<PurchaseRequest> {
        /**
         * Method used to compare two objects at their price
         *
         * @param p1 the first object to be compared.
         * @param p2 the second object to be compared.
         * @return positive int if p1 has higher price that p2, 0 if p1 has the same price as p2 and negative int if p1 has lower price that p2
         */
        public int compare(PurchaseRequest p1, PurchaseRequest p2) {
            return Float.compare(p1.orderAmount, p2.orderAmount);
        }
    }

}
