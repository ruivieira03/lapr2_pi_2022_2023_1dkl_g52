package pt.ipp.isep.dei.esoft.project.domain.property.transactions;

import pt.ipp.isep.dei.esoft.project.domain.client.Client;
import pt.ipp.isep.dei.esoft.project.domain.client.notifications.Message;
import pt.ipp.isep.dei.esoft.project.util.error.OperationResult;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * The type Visit request.
 */
public class VisitRequest implements Serializable {

    private final int id;
    private Client client;
    private Message message;
    private PropertySale propertySale;

    private LocalDateTime visitStart;

    private LocalDateTime visitEnd;

    private static int CURRENT_ID = 1;

    /**
     * Instantiates a new Visit request.
     */
    public VisitRequest() {
        this.id = CURRENT_ID;
        CURRENT_ID++;
    }

    /**
     * Gets id.
     *
     * @return the id
     */
    public int getId() {
        return id;
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
     * Sets client.
     *
     * @param client the client
     */
    public void setClient(Client client) {
        this.client = client;
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
     * Sets property sale.
     *
     * @param propertySale the property sale
     */
    public void setPropertySale(PropertySale propertySale) {
        this.propertySale = propertySale;
    }

    /**
     * Gets visit start.
     *
     * @return the visit start
     */
    public LocalDateTime getVisitStart() {
        return visitStart;
    }

    /**
     * Sets visit start.
     *
     * @param visitStart the visit start
     */
    public void setVisitStart(LocalDateTime visitStart) {
        this.visitStart = visitStart;
    }

    /**
     * Gets visit end.
     *
     * @return the visit end
     */
    public LocalDateTime getVisitEnd() {
        return visitEnd;
    }

    /**
     * Sets visit end.
     *
     * @param visitEnd the visit end
     */
    public void setVisitEnd(LocalDateTime visitEnd) {
        this.visitEnd = visitEnd;
    }

    /**
     * Overlaps boolean.
     *
     * @param other the other
     * @return the boolean
     */
    public boolean overlaps(VisitRequest other) {
        return (!this.visitStart.isAfter(other.visitStart) && !this.visitEnd.isBefore(other.visitStart)) ||
                (!this.visitStart.isAfter(other.visitEnd) && !this.visitEnd.isBefore(other.visitEnd));
    }

    /**
     * Is valid operation result.
     *
     * @return the operation result
     */
    public OperationResult isValid() {
        if (this.client == null)
            return OperationResult.failed("Error - VisitRequest - Client cannot be null!");

        if (this.propertySale == null)
            return OperationResult.failed("Error - VisitREquest - PropertySale cannot be null!");

        if (this.visitStart.isAfter(this.visitEnd))
            return OperationResult.failed(("Error - VisitRequest - VisitStart must be before visitEnd!"));

        return OperationResult.successfull();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (obj == this) return true;
        if (!(obj instanceof VisitRequest)) return false;

        VisitRequest v = (VisitRequest) obj;
        return v.client.equals(client) &&
                v.propertySale.equals(propertySale) &&
                v.visitStart.equals(visitStart) &&
                v.visitEnd.equals(visitEnd);
    }
}
