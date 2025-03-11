package pt.ipp.isep.dei.esoft.project.application.controller;

import pt.ipp.isep.dei.esoft.project.domain.client.mappers.dto.ClientDTO;
import pt.ipp.isep.dei.esoft.project.domain.client.notifications.mappers.dto.MessageDTO;
import pt.ipp.isep.dei.esoft.project.domain.employee.mappers.dto.AgentDTO;
import pt.ipp.isep.dei.esoft.project.domain.property.transactions.PropertySale;
import pt.ipp.isep.dei.esoft.project.domain.property.transactions.mappers.dto.PropertySaleRequestDTO;
import pt.ipp.isep.dei.esoft.project.repository.*;
import pt.ipp.isep.dei.esoft.project.util.error.ErrorOptional;
import pt.ipp.isep.dei.esoft.project.util.error.OperationResult;
import pt.isep.lei.esoft.auth.UserSession;

import java.time.LocalDateTime;
import java.util.List;

/**
 * The type List property sale requests controller.
 */
public class ListPropertySaleRequestsController {

    private final Repositories repositories;

    /**
     * Instantiates a new List property sale requests controller.
     */
    public ListPropertySaleRequestsController() {
        repositories = Repositories.getInstance();
    }

    /**
     * Gets property sale request list from agent.
     *
     * @return the property sale request list from agent
     */
    public ErrorOptional<List<PropertySaleRequestDTO>> getPropertySaleRequestListFromAgent() {
        ErrorOptional<AgentDTO> agent = getAgentFromSession();

        if (agent.hasError()) return ErrorOptional.empty(agent.getErrorMessage());

        PropertySaleRequestRepository propertySaleRequestRepository = Repositories.getPropertySaleRequestRepository();
        return propertySaleRequestRepository.listPropertySaleRequests(agent.get());
    }

    /**
     * Gets clientDTO from email.
     *
     * @param ownerEmail
     * @return
     */

    private ErrorOptional<ClientDTO> getClientDTOFromEmail(String ownerEmail) {
        ClientRepository clientRepository = Repositories.getClientRepository();
        return clientRepository.getClientDTOFromEmail(ownerEmail);
    }

    /**
     * Method used to get the agent from a session.
     *
     * @return agent
     */
    private ErrorOptional<AgentDTO> getAgentFromSession() {
        AuthenticationRepository authenticationRepository = Repositories.getAuthenticationRepository();
        StoreRepository storeNetworkRepository = Repositories.getStoreRepository();

        UserSession userSession = authenticationRepository.getCurrentUserSession();

        String agentEmail = userSession.getUserId().toString();

        return storeNetworkRepository.getAgentDTOFromEmail(agentEmail);
    }

    /**
     * Method used to remove property sale request.
     *
     * @param propertySaleRequestDTO the property sale request dto
     * @return the operation result
     */
    public OperationResult removePropertySaleRequest(PropertySaleRequestDTO propertySaleRequestDTO) {
        PropertySaleRequestRepository propertySaleRequestRepository = Repositories.getPropertySaleRequestRepository();

        return propertySaleRequestRepository.removePropertySaleRequest(propertySaleRequestDTO);
    }

    /**
     * Method used to create a property sale request.
     *
     * @param propertySaleRequestDTO the property sale request dto
     * @param commissionType         the commission type
     * @param commission             the commission
     * @return the operation result
     */
    public OperationResult createPropertySale(PropertySaleRequestDTO propertySaleRequestDTO, PropertySale.CommissionType commissionType, double commission) {
        PropertySaleRepository propertySaleRepository = Repositories.getPropertySaleRepository();

        OperationResult operationResult = propertySaleRepository.convertRequestInPropertySale(propertySaleRequestDTO, commissionType, commission);
        if (!operationResult.success())
            return OperationResult.failed(operationResult.getErrorMessage() + "\nError - Controller - Failed to create PropertySale!");

        return removePropertySaleRequest(propertySaleRequestDTO);
    }

    /**
     * Used to send a message to the client.
     *
     * @param clientDTO  the client dto
     * @param messageDTO the message dto
     * @return the operation result
     */
    public OperationResult sendMessageToClient(ClientDTO clientDTO, MessageDTO messageDTO) {
        ClientRepository clientRepository = Repositories.getClientRepository();

        ErrorOptional<ClientDTO> client = getClientDTOFromEmail(clientDTO.email.toString());
        ErrorOptional<AgentDTO> agent = getAgentFromSession();

        if (client.hasError())
            return OperationResult.failed(client.getErrorMessage() + "\nError - Controller - Failed to get Client from Email!");
        if (agent.hasError())
            return OperationResult.failed(agent.getErrorMessage() + "\nError - Controller - Failed to get Agent from UserSession!");

        messageDTO.author = agent.get();
        messageDTO.date = LocalDateTime.now();

        return clientRepository.notifyClient(client.get(), messageDTO);
    }
}
