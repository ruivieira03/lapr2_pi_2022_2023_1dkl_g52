package pt.ipp.isep.dei.esoft.project.application.controller;


import pt.ipp.isep.dei.esoft.project.domain.client.mappers.dto.ClientDTO;
import pt.ipp.isep.dei.esoft.project.domain.client.notifications.mappers.dto.MessageDTO;
import pt.ipp.isep.dei.esoft.project.domain.employee.mappers.dto.AgentDTO;
import pt.ipp.isep.dei.esoft.project.domain.property.transactions.mappers.dto.PropertySaleDTO;
import pt.ipp.isep.dei.esoft.project.domain.property.transactions.mappers.dto.PurchaseRequestDTO;
import pt.ipp.isep.dei.esoft.project.repository.*;
import pt.ipp.isep.dei.esoft.project.util.error.ErrorOptional;
import pt.ipp.isep.dei.esoft.project.util.error.OperationResult;
import pt.isep.lei.esoft.auth.UserSession;

import java.time.LocalDateTime;
import java.util.List;

/**
 * The type List purchase request controller.
 */
public class ListPurchaseRequestController {
    /**
     * Instantiates a new List purchase request controller.
     */
    public ListPurchaseRequestController() {

    }


    /**
     * Gets purchase request list.
     *
     * @param propertySaleDTO the property sale dto
     * @return the purchase request list
     */
    public ErrorOptional<List<PurchaseRequestDTO>> getPurchaseRequestList(PropertySaleDTO propertySaleDTO) {
        PurchaseRequestRepository purchaseRequestRepository = Repositories.getPurchaseRequestRepository();

        return ErrorOptional.of(purchaseRequestRepository.listPurchaseRequests(propertySaleDTO));
    }

    /**
     * Gets property sale list.
     *
     * @return the property sale list
     */
    public ErrorOptional<List<PropertySaleDTO>> getPropertySaleList() {
        AuthenticationRepository authenticationRepository = Repositories.getAuthenticationRepository();
        PurchaseRequestRepository purchaseRequestRepository = Repositories.getPurchaseRequestRepository();
        StoreRepository storeNetworkRepository = Repositories.getStoreRepository();

        String email = authenticationRepository.getCurrentUserSession().getUserId().toString();
        ErrorOptional<AgentDTO> agentDTO = storeNetworkRepository.getAgentDTOFromEmail(email);
        if (agentDTO.hasError())
            return ErrorOptional.empty(agentDTO.getErrorMessage() + "\nError - Controller - Fail to get agentDTO from repository!");

        return ErrorOptional.of(purchaseRequestRepository.listProperties(agentDTO.get()));
    }

    /**
     * Used to remove property sale.
     *
     * @param propertySaleDTO the property sale dto
     * @return the operation result
     */
    public OperationResult removePropertySale(PropertySaleDTO propertySaleDTO) {
        PropertySaleRepository propertySaleRepository = Repositories.getPropertySaleRepository();

        return propertySaleRepository.removePropertySaleRequest(propertySaleDTO);
    }

    /**
     * Used to remove purchase request.
     *
     * @param purchaseRequestDTO the purchase request dto
     * @return the operation result
     */
    public OperationResult removePurchaseRequest(PurchaseRequestDTO purchaseRequestDTO) {
        PurchaseRequestRepository purchaseRequestRepository = Repositories.getPurchaseRequestRepository();

        return purchaseRequestRepository.removePurchaseRequest(purchaseRequestDTO);
    }

    /**
     * Used to get agent from the session.
     *
     * @return
     */

    private ErrorOptional<AgentDTO> getAgentFromSession() {
        AuthenticationRepository authenticationRepository = Repositories.getAuthenticationRepository();

        UserSession userSession = authenticationRepository.getCurrentUserSession();
        String agenteEmail = userSession.getUserId().toString();

        StoreRepository storeNetworkRepository = Repositories.getStoreRepository();
        return storeNetworkRepository.getAgentDTOFromEmail(agenteEmail);
    }

    /**
     * Method to remove all purchase requests.
     *
     * @param propertySaleDTO the property sale dto
     * @return the operation result
     */
    public OperationResult removeAllPurchaseRequest(PropertySaleDTO propertySaleDTO) {
        PurchaseRequestRepository purchaseRequestRepository = Repositories.getPurchaseRequestRepository();

        ErrorOptional<AgentDTO> agent = getAgentFromSession();
        if (agent.hasError())
            return OperationResult.failed(agent.getErrorMessage() + "\nError - Controller - Failed to get agent from user session!");

        return purchaseRequestRepository.removeAllPurchaseRequest(propertySaleDTO, agent.get());
    }

    /**
     * Used to send notification to client.
     *
     * @param messageDTO the message dto
     * @param clientDTO  the client dto
     * @return the operation result
     */
    public OperationResult sendNotificationToClient(MessageDTO messageDTO, ClientDTO clientDTO) {
        ClientRepository clientRepository = Repositories.getClientRepository();

        ErrorOptional<AgentDTO> agent = getAgentFromSession();
        if (agent.hasError())
            return OperationResult.failed(agent.getErrorMessage() + "\nError - Controller - Failed to get agent from user session!");

        messageDTO.author = agent.get();
        messageDTO.date = LocalDateTime.now();

        return clientRepository.notifyClient(clientDTO, messageDTO);
    }

    /**
     * Used to add a property sold.
     *
     * @param purchaseRequest the purchase request
     * @return the operation result
     */
    public OperationResult addPropertySold (PurchaseRequestDTO purchaseRequest) {
        PropertySoldRepository propertySoldRepository = Repositories.getPropertySoldRepository();

        return propertySoldRepository.createPropertySold(purchaseRequest);
    }

    /**
     * Used for an accepted request.
     *
     * @param purchaseRequest the purchase request
     * @param message         the message
     * @return the operation result
     */
    public OperationResult acceptRequest(PurchaseRequestDTO purchaseRequest, MessageDTO message) {
        OperationResult operationResult = removePropertySale(purchaseRequest.propertySale);
        if (!operationResult.success())
            return OperationResult.failed(operationResult.getErrorMessage() + "\nError - Controller - Failed to remove PropertySale from repository!");

        operationResult = removePurchaseRequest(purchaseRequest);
        if (!operationResult.success())
            return OperationResult.failed(operationResult.getErrorMessage() + "\nError - Controller - Failed to remove PurchaseRequest from repository!");

        operationResult = sendNotificationToClient(message, purchaseRequest.client);
        if (!operationResult.success())
            return OperationResult.failed(operationResult.getErrorMessage() + "\nError - Controller - Failed to send the message to the client!");

        operationResult = addPropertySold(purchaseRequest);
        if (!operationResult.success())
            return  OperationResult.failed(operationResult.getErrorMessage() + "\nError Controller - Failed to add property sold to repository!");

        return removeAllPurchaseRequest(purchaseRequest.propertySale);
    }

    /**
     * USed for a declined request.
     *
     * @param purchaseRequest the purchase request
     * @param message         the message
     * @return the operation result
     */
    public OperationResult declineRequest(PurchaseRequestDTO purchaseRequest, MessageDTO message) {
        OperationResult operationResult = removePurchaseRequest(purchaseRequest);
        if (!operationResult.success())
            return OperationResult.failed(operationResult.getErrorMessage() + "\nError - Controller - Failed to remove PurchaseRequest from repository!");

        return sendNotificationToClient(message, purchaseRequest.client);
    }


 }
