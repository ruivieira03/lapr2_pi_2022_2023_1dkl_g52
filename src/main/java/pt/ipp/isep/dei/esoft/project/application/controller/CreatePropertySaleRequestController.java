package pt.ipp.isep.dei.esoft.project.application.controller;

import pt.ipp.isep.dei.esoft.project.domain.client.mappers.dto.ClientDTO;
import pt.ipp.isep.dei.esoft.project.domain.employee.mappers.dto.AgentDTO;
import pt.ipp.isep.dei.esoft.project.domain.locations.mappers.dto.StateDTO;
import pt.ipp.isep.dei.esoft.project.domain.property.transactions.mappers.dto.PropertySaleRequestDTO;
import pt.ipp.isep.dei.esoft.project.domain.store.mappers.dto.StoreDTO;
import pt.ipp.isep.dei.esoft.project.repository.*;
import pt.ipp.isep.dei.esoft.project.util.error.ErrorOptional;
import pt.ipp.isep.dei.esoft.project.util.error.OperationResult;

import java.util.List;


/**
 * The type Create property sale request controller.
 */
public class CreatePropertySaleRequestController {
    private final static Repositories repositories = Repositories.getInstance();

    /**
     * Instantiates a new Create property sale request controller.
     */
    public CreatePropertySaleRequestController() {

    }

    /**
     * Used to create a sales request.
     *
     * @param propertySaleRequestDTO the property sale request dto
     * @return the operation result
     */
    public OperationResult createSalesRequest(PropertySaleRequestDTO propertySaleRequestDTO) {

        ErrorOptional<ClientDTO> client = getClientFromUserSession();
        if (client.hasError())
            return OperationResult.failed(client.getErrorMessage() + "\nError - Controller - Failed to get Client from user Session!");

        propertySaleRequestDTO.property.client = client.get();

        PropertySaleRequestRepository propertySaleRequestRepository = Repositories.getPropertySaleRequestRepository();

        return (propertySaleRequestRepository.createPropertySaleRequest(propertySaleRequestDTO));

    }

    /**
     * Gets client from user session.
     *
     * @return the client from user session
     */
    public ErrorOptional<ClientDTO> getClientFromUserSession() {
        AuthenticationRepository authenticationRepository = Repositories.getAuthenticationRepository();
        String email = authenticationRepository.getCurrentUserSession().getUserId().toString();

        ClientRepository clientRepository = Repositories.getClientRepository();

        return clientRepository.getClientDTOFromEmail(email.toString());
    }

    /**
     * Gets agents from store.
     *
     * @param storeDTO the store dto
     * @return the agents from store
     */
    public List<AgentDTO> getAgentsFromStore(StoreDTO storeDTO) {
        StoreRepository storeNetworkRepository = Repositories.getStoreRepository();

        return storeNetworkRepository.getAgentListFromStore(storeDTO);
    }

    /**
     * Gets states.
     *
     * @return the states
     */
    public List<StateDTO> getStates() {
        LocationsRepository locationsRepository = Repositories.getLocationsRepository();
        return locationsRepository.getStatesList();
    }

    /**
     * Gets store list.
     *
     * @return the store list
     */
    public List<StoreDTO> getStoreList() {
        StoreRepository storeRepository = Repositories.getStoreRepository();
        return storeRepository.getSortedStoreList();
    }
}
