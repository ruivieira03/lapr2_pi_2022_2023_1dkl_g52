package pt.ipp.isep.dei.esoft.project.application.controller;

import pt.ipp.isep.dei.esoft.project.domain.client.mappers.dto.ClientDTO;
import pt.ipp.isep.dei.esoft.project.domain.client.notifications.mappers.dto.MessageDTO;
import pt.ipp.isep.dei.esoft.project.domain.employee.mappers.dto.AgentDTO;
import pt.ipp.isep.dei.esoft.project.domain.locations.mappers.dto.StateDTO;
import pt.ipp.isep.dei.esoft.project.domain.property.transactions.mappers.dto.PropertySaleDTO;
import pt.ipp.isep.dei.esoft.project.repository.*;
import pt.ipp.isep.dei.esoft.project.util.dispatch.MessageBuilder;
import pt.ipp.isep.dei.esoft.project.util.dispatch.SMSServices;
import pt.ipp.isep.dei.esoft.project.util.error.ErrorOptional;
import pt.ipp.isep.dei.esoft.project.util.error.OperationResult;
import pt.isep.lei.esoft.auth.UserSession;

import java.time.LocalDateTime;
import java.util.List;

/**
 * The type Register property sale controller.
 */
public class RegisterPropertySaleController {

    private final Repositories repositories;

    /**
     * Instantiates a new Register property sale controller.
     */
    public RegisterPropertySaleController() {
        repositories = Repositories.getInstance();
    }

    /**
     * Gets states list.
     *
     * @return the states list
     */
    public List<StateDTO> getStatesList() {
        LocationsRepository locationsRepository = Repositories.getLocationsRepository();
        return locationsRepository.getStatesList();
    }


    /**
     * Create property sale  method.
     *
     * @param propertySaleDTO the property sale dto
     * @param ownerEmail      the owner email
     * @return the operation result
     */
    public OperationResult createPropertySale(PropertySaleDTO propertySaleDTO, String ownerEmail) {

        ErrorOptional<AgentDTO> agent = getAgentFromSession();
        if (agent.hasError()) {
            return OperationResult.failed(agent.getErrorMessage() + "\nError - Controller - Failed to get Agent from sessoin!");
        }
        propertySaleDTO.agent = agent.get();

        ErrorOptional<ClientDTO> client = getClientFromEmail(ownerEmail);
        if (client.hasError())
            return OperationResult.failed(client.getErrorMessage() + "\nError - Controller - Failed to get Client from ClientRepository");
        propertySaleDTO.property.client = client.get();

        PropertySaleRepository propertySaleRepository = Repositories.getPropertySaleRepository();
        OperationResult result = propertySaleRepository.createPropertySale(propertySaleDTO);

        if (result.success()){
            new SMSServices().sendSMS(propertySaleDTO.property.client.phoneNumber, MessageBuilder.propertySalePostedByAgent(propertySaleDTO));

            MessageDTO messageDTO = new MessageDTO();
            messageDTO.message = MessageBuilder.propertySalePostedByAgent(propertySaleDTO);
            messageDTO.date = LocalDateTime.now();
            messageDTO.author = agent.get();
            Repositories.getClientRepository().notifyClient(client.get(), messageDTO);
        }

        return result;
    }

    /**
     * Method used to get the client from his email.
     *
     * @param ownerEmail client email
     * @return client that have that email
     */
    private ErrorOptional<ClientDTO> getClientFromEmail(String ownerEmail) {
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

        UserSession userSession = authenticationRepository.getCurrentUserSession();
        String agenteEmail = userSession.getUserId().toString();

        StoreRepository storeNetworkRepository = Repositories.getStoreRepository();
        return storeNetworkRepository.getAgentDTOFromEmail(agenteEmail);
    }
}
