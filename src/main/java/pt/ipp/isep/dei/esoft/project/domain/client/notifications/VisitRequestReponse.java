package pt.ipp.isep.dei.esoft.project.domain.client.notifications;

import pt.ipp.isep.dei.esoft.project.domain.property.transactions.VisitRequest;
import pt.ipp.isep.dei.esoft.project.domain.user.User;
import pt.ipp.isep.dei.esoft.project.util.error.OperationResult;

import java.util.Objects;

/**
 * The type Visit request reponse.
 */
public class VisitRequestReponse extends Message{
    /**
     * The Visit request.
     */
    VisitRequest visitRequest;

    /**
     * Instantiates a new Visit request reponse.
     */
    public VisitRequestReponse() {
    }

    /**
     * Gets visit request.
     *
     * @return the visit request
     */
    public VisitRequest getVisitRequest() {
        return visitRequest;
    }

    /**
     * Sets visit request.
     *
     * @param visitRequest the visit request
     */
    public void setVisitRequest(VisitRequest visitRequest) {
        this.visitRequest = visitRequest;
    }
    @Override
    public String toString() {
        return super.toString() + visitRequest.toString();
    }

    public OperationResult isValid() {
        if(!visitRequest.isValid().success()){
            return OperationResult.failed(visitRequest.isValid().getErrorMessage() + "Error - VisitRequestResponse  - Visit request must be valid!");
        }
        return OperationResult.successfull();
    }
}
