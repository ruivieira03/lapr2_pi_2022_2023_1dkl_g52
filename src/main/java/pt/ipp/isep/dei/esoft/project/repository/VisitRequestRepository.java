package pt.ipp.isep.dei.esoft.project.repository;

import pt.ipp.isep.dei.esoft.project.domain.employee.mappers.dto.AgentDTO;
import pt.ipp.isep.dei.esoft.project.domain.property.transactions.VisitRequest;
import pt.ipp.isep.dei.esoft.project.domain.property.transactions.mappers.VisitRequestMapper;
import pt.ipp.isep.dei.esoft.project.domain.property.transactions.mappers.dto.PropertySaleDTO;
import pt.ipp.isep.dei.esoft.project.domain.property.transactions.mappers.dto.VisitRequestDTO;
import pt.ipp.isep.dei.esoft.project.util.error.ErrorOptional;
import pt.ipp.isep.dei.esoft.project.util.error.OperationResult;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * The type Visit request repository.
 */
public class VisitRequestRepository implements Serializable {

    /**
     * The Visit requests.
     */
    List<VisitRequest> visitRequests = new ArrayList<>();
    /**
     * The Accepted visit requests.
     */
    List<VisitRequest> acceptedVisitRequests = new ArrayList<>();
    /**
     * The Declined visit requests.
     */
    List<VisitRequest> declinedVisitRequests = new ArrayList<>();

    private transient VisitRequestMapper mapper = new VisitRequestMapper();

    /**
     * Gets visit request list from agent.
     *
     * @param agentDTO the agent dto
     * @return the visit request list from agent
     */
    public ErrorOptional<List<VisitRequestDTO>> getVisitRequestListFromAgent(AgentDTO agentDTO) {
        List<VisitRequest> list = new ArrayList<>();

        // get
        for (VisitRequest visitRequest : visitRequests) {
            if (visitRequest.getPropertySale().getAgent().hasEmail(agentDTO.email)) {
                list.add(visitRequest);
            }
        }

        // toDTO
        List<VisitRequestDTO> listDTO = new ArrayList<>();

        for (VisitRequest visitRequest : list) {
            ErrorOptional<VisitRequestDTO> dto = mapper.toDTO(visitRequest);
            if (!dto.hasError())
                listDTO.add(dto.get());
        }

        return ErrorOptional.of(listDTO);
    }

    /**
     * Used to remove visit request from a property sale.
     *
     * @param propertySaleDTO the property sale dto
     * @return the operation result
     */
    public OperationResult removeVisitRequestFromPropertySale(PropertySaleDTO propertySaleDTO) {
        boolean removedAny = false;

        for (VisitRequest visitRequest : visitRequests) {
            if (visitRequest.getPropertySale().getID() == propertySaleDTO.id) {
                visitRequests.remove(visitRequest);
                removedAny = true;
            }
        }

        for (VisitRequest visitRequest : acceptedVisitRequests) {
            if (visitRequest.getPropertySale().getID() == propertySaleDTO.id) {
                acceptedVisitRequests.remove(visitRequest);
                removedAny = true;
            }
        }

        if (removedAny)
            return OperationResult.successfull();

        return OperationResult.failed("Error - Repository - Cannot remove VisitRequest because none have refer to the PropertySale[" + propertySaleDTO.id + "]");
    }

    /**
     * Method used to remove a visit request.
     *
     * @param dto the dto
     * @return the operation result
     */
    public OperationResult removeVisitRequest(VisitRequestDTO dto) {
        for (VisitRequest visitRequest : visitRequests) {
            if (visitRequest.getId() == dto.id) {
                visitRequests.remove(visitRequest);
                return OperationResult.successfull();
            }
        }

        return OperationResult.failed("Error - Repository - Cannot remove VisitRequest[" + dto.id + "] because it doesn't exists!");
    }

    /**
     * Used to register a new visit request.
     *
     * @param visitRequestDTO the visit request dto
     * @return the operation result
     */
    public OperationResult registerVisitRequest(VisitRequestDTO visitRequestDTO) {
        ErrorOptional<VisitRequest> visitRequest = mapper.toDomain(visitRequestDTO);

        if (visitRequest.hasError())
            return OperationResult.failed(visitRequest.getErrorMessage() + "\nErro - Repository - Failed to convert VisitRequestDTO into VisitRequest!");

        return addVisitRequest(visitRequest.get());
    }

    /**
     * Method to add a visit request.
     *
     * @param visitRequest the visit request
     * @return the operation result
     */
    public OperationResult addVisitRequest(VisitRequest visitRequest) {
        OperationResult localValidation = visitRequest.isValid();
        if (!localValidation.success())
            return OperationResult.failed(localValidation.getErrorMessage() + "\nError - Repository - Failed local validation!");

        if (visitRequest.getVisitStart().isBefore(LocalDateTime.now()))
            return OperationResult.failed("Error - Repository - Visit Request cannot be in the past!");

        OperationResult globalValidation = isValid(visitRequest);
        if (!globalValidation.success())
            return OperationResult.failed(globalValidation.getErrorMessage() + "\nError - Repository - Failed global validation!");

        visitRequests.add(visitRequest);
        return OperationResult.successfull();

    }

    /**
     * Used to validate visit request (global validation).
     *
     * @param visitRequest
     * @return
     */

    private OperationResult isValid(VisitRequest visitRequest) {
        for (VisitRequest v : visitRequests) {
            if (v.equals(visitRequest))
                return OperationResult.failed("Error - Reporitory - VisitRequest already exists!");

            else if (v.getClient().equals(visitRequest.getClient())) {
                if (visitRequest.overlaps(v))
                    return OperationResult.failed("Error - Reporitory - VisitRequest overlaps an existing request!");
            }
        }

        return OperationResult.successfull();
    }

    /**
     * Used to add accepted visit request.
     *
     * @param visitRequestDTO the visit request dto
     * @return the operation result
     */
    public OperationResult addAcceptedVisitRequest(VisitRequestDTO visitRequestDTO) {
        ErrorOptional<VisitRequest> visitRequest = mapper.toDomain(visitRequestDTO);

        if(visitRequest.hasError()){
            return OperationResult.failed(visitRequest.getErrorMessage() + "\nErro - Repository - Failed to convert VisitRequestDTO into VisitRequest!");
        }

        acceptedVisitRequests.add(visitRequest.get());
        OperationResult operationResult = removeVisitRequest(visitRequestDTO);
         if(!operationResult.success()){
             return OperationResult.failed(operationResult.getErrorMessage() + "Failed in removing the visit request from the visit requests list");
         }

        return OperationResult.successfull();
    }

    /**
     * Used to add a declined visit request.
     *
     * @param visitRequestDTO the visit request dto
     * @return the operation result
     */
    public OperationResult addDeclinedVisitRequest(VisitRequestDTO visitRequestDTO) {
        ErrorOptional<VisitRequest> visitRequest = mapper.toDomain(visitRequestDTO);

        if(visitRequest.hasError()){
            return OperationResult.failed(visitRequest.getErrorMessage() + "\nErro - Repository - Failed to convert VisitRequestDTO into VisitRequest!");
        }

        declinedVisitRequests.add(visitRequest.get());
        OperationResult operationResult = removeVisitRequest(visitRequestDTO);
        if(!operationResult.success()){
            return OperationResult.failed(operationResult.getErrorMessage() + "Failed in removing the visit request from the visit requests list");
        }

        return OperationResult.successfull();
    }

    /**
     * Get visit request by agent as string [ ].
     *
     * @param agentDTO the agent dto
     * @return the string [ ]
     */
    public String[] getVisitRequestByAgentAsString(AgentDTO agentDTO) {
        List<VisitRequestDTO> list = getVisitRequestListFromAgent(agentDTO).get();
        String[] visitRequests = new String[list.size()];
        int index = 0;

        for ( VisitRequestDTO visitRequestDTO : list) {
            visitRequests[index] = visitRequestDTO.toString();
            index++;
        }


        return visitRequests;
    }

    public void deserialize() {
        mapper = new VisitRequestMapper();
    }

    public List<VisitRequestDTO> filterByDate(ErrorOptional<List<VisitRequestDTO>> visitRequestDTOList, LocalDate beginDate, LocalDate endDate) {
        List<VisitRequestDTO> filteredList = new ArrayList<>();

        for (VisitRequestDTO visitRequestDTO : visitRequestDTOList.get()) {
            if (visitRequestDTO.visitStart.isAfter(beginDate.atStartOfDay()) && visitRequestDTO.visitEnd.isBefore(endDate.atTime(23, 59, 59))) {
                filteredList.add(visitRequestDTO);
            }
        }

        return filteredList;
    }
}
