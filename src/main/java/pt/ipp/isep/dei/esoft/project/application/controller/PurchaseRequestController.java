package pt.ipp.isep.dei.esoft.project.application.controller;

import pt.ipp.isep.dei.esoft.project.domain.client.mappers.dto.ClientDTO;
import pt.ipp.isep.dei.esoft.project.domain.property.transactions.mappers.dto.PurchaseRequestDTO;
import pt.ipp.isep.dei.esoft.project.repository.AuthenticationRepository;
import pt.ipp.isep.dei.esoft.project.repository.ClientRepository;
import pt.ipp.isep.dei.esoft.project.repository.PurchaseRequestRepository;
import pt.ipp.isep.dei.esoft.project.repository.Repositories;
import pt.ipp.isep.dei.esoft.project.util.error.ErrorOptional;
import pt.ipp.isep.dei.esoft.project.util.error.OperationResult;

/**
 * The type Purchase request controller.
 */
public class PurchaseRequestController {
    private final Repositories repositories;

    /**
     * Instantiates a new Purchase request controller.
     */
    public PurchaseRequestController() {
        this.repositories = Repositories.getInstance();
    }

    /**
     * USed to create a purchase request.
     *
     * @param purchaseRequestDTO the purchase request dto
     * @return the operation result
     */
    public OperationResult createPurchaseRequest(PurchaseRequestDTO purchaseRequestDTO) {
        PurchaseRequestRepository purchaseRequestRepository = Repositories.getPurchaseRequestRepository();
        ErrorOptional<ClientDTO> client = getClientFromUserSession();
        if (client.hasError())
            System.out.println(client.getErrorMessage() + "\nError - Controller - Failed to get ClientDTO from user session");
        else
            purchaseRequestDTO.client = client.get();

        return purchaseRequestRepository.createPurchaseRequest(purchaseRequestDTO);

    }

    /**
     * Gets client from user session.
     *
     * @return the client from user session
     */
    public ErrorOptional<ClientDTO> getClientFromUserSession() {

        AuthenticationRepository authenticationRepository = Repositories.getAuthenticationRepository();
        ClientRepository clientRepository = Repositories.getClientRepository();

        String email = authenticationRepository.getCurrentUserSession().getUserId().toString();

        return clientRepository.getClientDTOFromEmail(email);
    }
}
