package pt.ipp.isep.dei.esoft.project.application.controller;

import pt.ipp.isep.dei.esoft.project.domain.client.mappers.dto.ClientDTO;
import pt.ipp.isep.dei.esoft.project.repository.AuthenticationRepository;
import pt.ipp.isep.dei.esoft.project.repository.ClientRepository;
import pt.ipp.isep.dei.esoft.project.repository.Repositories;
import pt.ipp.isep.dei.esoft.project.util.error.OperationResult;

/**
 * The type Registration controller.
 */
public class RegistrationController {

    /**
     * Instantiates a new Registration controller.
     */
    public RegistrationController() {
    }

    /**
     * Method to create a new client.
     *
     * @param clientDTO the client dto
     * @param password  the password
     * @return operation result
     */
    public OperationResult createClient(ClientDTO clientDTO, String password){
        AuthenticationRepository authenticationRepository = Repositories.getAuthenticationRepository();
        authenticationRepository.addUserWithRole(clientDTO, password);

        ClientRepository clientRepository = Repositories.getClientRepository();

        return clientRepository.createClient(clientDTO);
    }
}
