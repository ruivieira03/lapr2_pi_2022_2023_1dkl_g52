package pt.ipp.isep.dei.esoft.project.repository;

import pt.ipp.isep.dei.esoft.project.domain.employee.Agent;
import pt.ipp.isep.dei.esoft.project.domain.employee.mappers.dto.AgentDTO;
import pt.ipp.isep.dei.esoft.project.domain.property.transactions.PropertySaleRequest;
import pt.ipp.isep.dei.esoft.project.domain.property.transactions.mappers.PropertySaleRequestMapper;
import pt.ipp.isep.dei.esoft.project.domain.property.transactions.mappers.dto.PropertySaleRequestDTO;
import pt.ipp.isep.dei.esoft.project.util.error.ErrorOptional;
import pt.ipp.isep.dei.esoft.project.util.error.OperationResult;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * The type Property sale request repository.
 */
public class PropertySaleRequestRepository implements Serializable {
    /**
     * The Property sale requests.
     */
    List<PropertySaleRequest> propertySaleRequests = new ArrayList<>();

    private transient PropertySaleRequestMapper mapper = new PropertySaleRequestMapper();


    /**
     * Method used to create a propertySaleRequest type object.
     *
     * @param dto property sale request data transfer dto
     * @return OperationResult operation result
     */
    public OperationResult createPropertySaleRequest(PropertySaleRequestDTO dto) {
        try {
            ErrorOptional<PropertySaleRequest> propertySaleRequest = mapper.toDomain(dto);
            if (propertySaleRequest.hasError())
                return OperationResult.failed(propertySaleRequest.getErrorMessage() + "\nError - Repository - Failed to initiate PropertySaleRequest!");
            return addPropertySaleRequest(propertySaleRequest.get());
        } catch (IllegalArgumentException e) {
            return OperationResult.failed(e.getLocalizedMessage());
        }
    }

    /**
     * Method used to add a propertySaleRequest in the system.
     *
     * @param propertySaleRequest property sale request to be added
     * @return true if added successfully or false if not
     */

    private OperationResult addPropertySaleRequest(PropertySaleRequest propertySaleRequest) {
        if (!propertySaleRequest.isValid().success())
            return OperationResult.failed(propertySaleRequest.isValid().getErrorMessage() + "\nError - Repository - Failed local validation!");
        if (!isValid(propertySaleRequest))
            return OperationResult.failed(isValid(propertySaleRequest) + "\nError - Repository - Failed global validation!");

        propertySaleRequests.add(propertySaleRequest);
        return OperationResult.successfull();
    }

    /**
     * Method used to validate property sale request (global validation).
     *
     * @param propertySaleRequest
     * @return
     */

    private boolean isValid(PropertySaleRequest propertySaleRequest) {
        return !propertySaleRequests.contains(propertySaleRequest);
    }

    /**
     * Used to list property sale requests.
     *
     * @param agentDTO the agent dto
     * @return the error optional
     */
    public ErrorOptional<List<PropertySaleRequestDTO>> listPropertySaleRequests(AgentDTO agentDTO) {
        List<PropertySaleRequestDTO> list = new ArrayList<>();

        StoreRepository storeNetworkRepository = Repositories.getStoreRepository();

        ErrorOptional<Agent> agent = storeNetworkRepository.getAgentFromEmail(agentDTO.email);
        if (agent.hasError()) return ErrorOptional.empty(agent.getErrorMessage());


        ErrorOptional<PropertySaleRequestDTO> propertySaleRequestDTO;
        for (PropertySaleRequest request : propertySaleRequests) {
            if (agent.get().equals(request.getAgent())) {
                propertySaleRequestDTO = mapper.toDTO(request);
                if (propertySaleRequestDTO.hasError())
                    return ErrorOptional.empty(propertySaleRequestDTO.getErrorMessage());
                list.add(propertySaleRequestDTO.get());
            }
        }

        return ErrorOptional.of(list);
    }

    /**
     * Used to removed property sale request.
     *
     * @param dto the dto
     * @return the operation result
     */
    public OperationResult removePropertySaleRequest(PropertySaleRequestDTO dto) {
        if (dto == null) {
            return OperationResult.failed("Error - Repository - PropertySaleRequestDTO cannot be null!");
        }

        for (PropertySaleRequest propertySaleRequest : propertySaleRequests) {
            if (propertySaleRequest.getID() == dto.id) {
                propertySaleRequests.remove(propertySaleRequest);
                return OperationResult.successfull();
            }
        }

        return OperationResult.failed("Error - Repository - PropertySaleRequestDTO doesn't exist in the repository!");
    }

    /**
     * Deserialize.
     */
    public void deserialize() {
        mapper = new PropertySaleRequestMapper();
    }
}
