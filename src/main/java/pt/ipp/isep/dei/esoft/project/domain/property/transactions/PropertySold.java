package pt.ipp.isep.dei.esoft.project.domain.property.transactions;

import pt.ipp.isep.dei.esoft.project.domain.client.Client;
import pt.ipp.isep.dei.esoft.project.util.error.OperationResult;

import java.time.LocalDateTime;

/**
 * The type Property sold.
 */
public class PropertySold extends PropertySale {

    private double paidAmount;
    private Client buyer;
    private LocalDateTime purchaseDate;

    /**
     * Instantiates a new Property sold.
     */
    public PropertySold() {
    }

    /**
     * Gets paid amount.
     *
     * @return the paid amount
     */
    public double getPaidAmount() {
        return paidAmount;
    }

    /**
     * Sets paid amount.
     *
     * @param paidAmount the paid amount
     */
    public void setPaidAmount(double paidAmount) {
        this.paidAmount = paidAmount;
    }

    /**
     * Gets buyer.
     *
     * @return the buyer
     */
    public Client getBuyer() {
        return buyer;
    }

    /**
     * Sets buyer.
     *
     * @param buyer the buyer
     */
    public void setBuyer(Client buyer) {
        this.buyer = buyer;
    }

    /**
     * Gets purchase date.
     *
     * @return the purchase date
     */
    public LocalDateTime getPurchaseDate() {
        return purchaseDate;
    }

    /**
     * Sets purchase date.
     *
     * @param purchaseDate the purchase date
     */
    public void setPurchaseDate(LocalDateTime purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    @Override
    public OperationResult isValid() {
        return super.isValid();
    }

    @Override
    public boolean equals(Object outroObjeto) {
        return super.equals(outroObjeto);
    }
}
