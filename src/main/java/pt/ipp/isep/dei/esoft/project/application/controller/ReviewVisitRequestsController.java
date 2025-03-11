package pt.ipp.isep.dei.esoft.project.application.controller;

import pt.ipp.isep.dei.esoft.project.domain.client.mappers.dto.ClientDTO;
import pt.ipp.isep.dei.esoft.project.domain.client.notifications.mappers.dto.MessageDTO;
import pt.ipp.isep.dei.esoft.project.domain.client.notifications.mappers.dto.VisitRequestResponseDTO;
import pt.ipp.isep.dei.esoft.project.domain.employee.mappers.dto.AgentDTO;
import pt.ipp.isep.dei.esoft.project.domain.property.transactions.mappers.dto.VisitRequestDTO;
import pt.ipp.isep.dei.esoft.project.repository.*;
import pt.ipp.isep.dei.esoft.project.ui.console.utils.Utils;
import pt.ipp.isep.dei.esoft.project.util.algorithms.Algorithms;
import pt.ipp.isep.dei.esoft.project.util.dispatch.EmailServices;
import pt.ipp.isep.dei.esoft.project.util.error.ErrorOptional;
import pt.ipp.isep.dei.esoft.project.util.error.OperationResult;
import pt.isep.lei.esoft.auth.UserSession;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * The type Review visit requests controller.
 */
public class ReviewVisitRequestsController {
    private final Repositories repositories;
    private long startTime;
    private long endTime;

    private static VisitRequestDTO selectedVisitRequest;

    public VisitRequestDTO getSelectedVisitRequest() {
        return selectedVisitRequest;
    }

    public void setSelectedVisitRequest(VisitRequestDTO selectedVisitRequest) {
        this.selectedVisitRequest = selectedVisitRequest;
    }

    /**
     * Instantiates a new Review visit requests controller.
     */
    public ReviewVisitRequestsController() {
        repositories = Repositories.getInstance();
    }

    /**
     * Gets property sale request list from agent.
     *
     * @return the property sale request list from agent
     */
    public ErrorOptional<List<VisitRequestDTO>> getPropertySaleRequestListFromAgent() {
        ErrorOptional<AgentDTO> agent = getAgentFromSession();

        if (agent.hasError()) return ErrorOptional.empty(agent.getErrorMessage());

        VisitRequestRepository visitRequestRepository = Repositories.getVisitRequestRepository();
        return visitRequestRepository.getVisitRequestListFromAgent(agent.get());
    }

    /**
     * Method used to get agent from session.
     *
     * @return
     */

    private ErrorOptional<AgentDTO> getAgentFromSession() {
        AuthenticationRepository authenticationRepository = Repositories.getAuthenticationRepository();
        StoreRepository storeNetworkRepository = Repositories.getStoreRepository();

        UserSession userSession = authenticationRepository.getCurrentUserSession();

        String agenteEmail = userSession.getUserId().toString();

        return storeNetworkRepository.getAgentDTOFromEmail(agenteEmail);
    }

    /**
     * Add accepted requests method.
     *
     * @param visitRequestDTO the visit request dto
     * @return the operation result
     */
    public OperationResult addAcceptedRequests(VisitRequestDTO visitRequestDTO) {
        VisitRequestRepository visitRequestRepository = Repositories.getVisitRequestRepository();

        OperationResult operationResultAdd = visitRequestRepository.addAcceptedVisitRequest(visitRequestDTO);

        if (!operationResultAdd.success()) {
            return OperationResult.failed(operationResultAdd.getErrorMessage() + "\nError - Controller - Failed to add the visitRequest in the repository!");
        }

        return OperationResult.successfull();
    }

    /**
     * Method to send email to client.
     *
     * @param client          the client
     * @param messageDTO      the message dto
     * @param subject         the subject
     * @param visitRequestDTO the visit request dto
     * @return the operation result
     */
    public OperationResult sendEmailToClient(ClientDTO client, MessageDTO messageDTO, String subject, VisitRequestDTO visitRequestDTO) {
        EmailServices emailServices = new EmailServices();
        messageDTO.author = getAgentFromSession().get();
        messageDTO.date = LocalDateTime.now();
        if (emailServices.isEmailValid(messageDTO.author.email) && emailServices.isEmailValid(client.email)) {
            return emailServices.createFile(client.email, subject, messageDTO.message, visitRequestDTO);
        } else {
            return OperationResult.failed("Client and/or agent Emails are not valid");
        }
    }

    /**
     * Add declined requests method.
     *
     * @param visitRequestDTO the visit request dto
     * @return the operation result
     */
    public OperationResult addDeclinedVisitRequest(VisitRequestDTO visitRequestDTO) {
        VisitRequestRepository visitRequestRepository = Repositories.getVisitRequestRepository();

        return visitRequestRepository.addDeclinedVisitRequest(visitRequestDTO);
    }

    /**
     * Used to send response to client.
     *
     * @param clientDTO               the client dto
     * @param visitRequestResponseDTO the visit request response dto
     * @return the operation result
     */
    public OperationResult sendResponseToClient(ClientDTO clientDTO, VisitRequestResponseDTO visitRequestResponseDTO) {
        ClientRepository clientRepository = Repositories.getClientRepository();

        return clientRepository.notifyClient(clientDTO, visitRequestResponseDTO);
    }


    public List<VisitRequestDTO> getVisitRequests(LocalDate beginDate, LocalDate endDate) {
        ErrorOptional<List<VisitRequestDTO>> visitRequestDTOList = getPropertySaleRequestListFromAgent();
        VisitRequestRepository repo = Repositories.getVisitRequestRepository();
        List<VisitRequestDTO> filteredVisitRequests = repo.filterByDate(visitRequestDTOList, beginDate, endDate);
        if (filteredVisitRequests != null && !filteredVisitRequests.isEmpty()) {
            sortList(filteredVisitRequests);
        }
        return filteredVisitRequests;
    }

    public void sortList(List<VisitRequestDTO> visitRequestDTOList) {
        String sortAlgorithm = Utils.ReadProperties("Sort.Algorithm");
        switch (sortAlgorithm) {
            case "Bubble":
                startTime= System.nanoTime();
                Algorithms.bubbleSort(visitRequestDTOList);
                endTime= System.nanoTime();
                float timeBubble = (float) (endTime - startTime) / 1000000;
                System.out.printf("\nBubble Sort: %.5f milliseconds\n", timeBubble);
                break;

            case "Merge":
                startTime= System.nanoTime();
                Algorithms.mergeSort(visitRequestDTOList, 0, visitRequestDTOList.size() - 1);
                endTime= System.nanoTime();
                float timeMerge = (float) (endTime - startTime) / 1000000;
                System.out.printf("\nMerge Sort: %.5f milliseconds\n", timeMerge);
                break;

            default:
                throw new IllegalArgumentException("No sort option");
        }
    }
}
