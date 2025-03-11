package pt.ipp.isep.dei.esoft.project.application.controller;

import pt.ipp.isep.dei.esoft.project.domain.client.mappers.dto.ClientDTO;
import pt.ipp.isep.dei.esoft.project.domain.property.transactions.mappers.dto.PropertySaleDTO;
import pt.ipp.isep.dei.esoft.project.domain.property.transactions.mappers.dto.VisitRequestDTO;
import pt.ipp.isep.dei.esoft.project.repository.AuthenticationRepository;
import pt.ipp.isep.dei.esoft.project.repository.ClientRepository;
import pt.ipp.isep.dei.esoft.project.repository.Repositories;
import pt.ipp.isep.dei.esoft.project.repository.VisitRequestRepository;
import pt.ipp.isep.dei.esoft.project.util.error.ErrorOptional;
import pt.ipp.isep.dei.esoft.project.util.error.OperationResult;

/**
 * The type Schedule visit request controller.
 */
public class ScheduleVisitRequestController {

    /**
     * Instantiates a new Schedule visit request controller.
     */
    public ScheduleVisitRequestController() {

    }


    /**
     * Gets selected property sale dto.
     *
     * @return the selected property sale dto
     */
    public ErrorOptional<PropertySaleDTO> getSelectedPropertySaleDTO() {
        ListPropertiesController listPropertiesController = new ListPropertiesController();
        return listPropertiesController.getSelectedProperty();
    }

    /**
     * Used to register a new visit request.
     *
     * @param visitRequestDTO the visit request dto
     * @return the operation result
     */
    public OperationResult registerVisitRequest(VisitRequestDTO visitRequestDTO) {
        VisitRequestRepository visitRequestRepository = Repositories.getVisitRequestRepository();

        ErrorOptional<ClientDTO> clientDTO = getClientFromSession();
        if (clientDTO.hasError())
            return OperationResult.failed(clientDTO.getErrorMessage() + "\nError - Controller - Failed to get Client from ClientRepository!");

        visitRequestDTO.client = clientDTO.get();

        return visitRequestRepository.registerVisitRequest(visitRequestDTO);
    }

    /**
     * Gets client from session.
     *
     * @return the client from session
     */
    public ErrorOptional<ClientDTO> getClientFromSession() {
        ClientRepository clientRepository = Repositories.getClientRepository();
        AuthenticationRepository authenticationRepository = Repositories.getAuthenticationRepository();

        String email = authenticationRepository.getCurrentUserSession().getUserId().toString();

        return clientRepository.getClientDTOFromEmail(email.toString());
    }
}
