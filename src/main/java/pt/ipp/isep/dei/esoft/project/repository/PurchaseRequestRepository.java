package pt.ipp.isep.dei.esoft.project.repository;

import pt.ipp.isep.dei.esoft.project.domain.client.notifications.mappers.dto.MessageDTO;
import pt.ipp.isep.dei.esoft.project.domain.employee.mappers.dto.AgentDTO;
import pt.ipp.isep.dei.esoft.project.domain.property.transactions.PropertySale;
import pt.ipp.isep.dei.esoft.project.domain.property.transactions.PurchaseRequest;
import pt.ipp.isep.dei.esoft.project.domain.property.transactions.mappers.PropertySaleMapper;
import pt.ipp.isep.dei.esoft.project.domain.property.transactions.mappers.PurchaseRequestMapper;
import pt.ipp.isep.dei.esoft.project.domain.property.transactions.mappers.dto.PropertySaleDTO;
import pt.ipp.isep.dei.esoft.project.domain.property.transactions.mappers.dto.PurchaseRequestDTO;
import pt.ipp.isep.dei.esoft.project.util.error.ErrorOptional;
import pt.ipp.isep.dei.esoft.project.util.error.OperationResult;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * The type Purchase request repository.
 */
public class PurchaseRequestRepository implements Serializable {
    /**
     * The Purchase request list.
     */
    ArrayList<PurchaseRequest> purchaseRequestList = new ArrayList<>();

    private transient PurchaseRequestMapper mapper = new PurchaseRequestMapper();
    private transient PropertySaleMapper mapperSale = new PropertySaleMapper();

    /**
     * Create purchase request operation result.
     * Used to create purchase request.
     *
     * @param purchaseRequestDTO the purchase request dto
     * @return the operation result
     */
    public OperationResult createPurchaseRequest(PurchaseRequestDTO purchaseRequestDTO) {
        ErrorOptional<PurchaseRequest> purchaseRequest = mapper.toDomain(purchaseRequestDTO);
        if (purchaseRequest.hasError())
            return OperationResult.failed(purchaseRequest.getErrorMessage() + "\nError - Repository - Failed to convert PurchaseRequestDTO into PurchaseRequest!");

        return addPurchaseRequest(purchaseRequest.get());
    }

    /**
     * Method used to add purchase request.
     *
     * @param purchaseRequest
     * @return
     */
    private OperationResult addPurchaseRequest(PurchaseRequest purchaseRequest) {
        OperationResult localValidation = purchaseRequest.isValid();
        if (!localValidation.success())
            return OperationResult.failed(localValidation.getErrorMessage() + "\nError - Repository - Failed local validation!");

        // FIXME : make global validation return operatio result
        if (!isValid(purchaseRequest)) return OperationResult.failed("Error - Repository - Failed global validation!");

        purchaseRequestList.add(purchaseRequest);
        return OperationResult.successfull();
    }

    /**
     * Method used to validate purchase request (global validation).
     * @param purchaseRequest
     * @return
     */
    private boolean isValid(PurchaseRequest purchaseRequest) {
        for (PurchaseRequest p : purchaseRequestList) {
            if (p.equals(purchaseRequest))
                return false;
        }

        return true;
    }

    /**
     * Used to list properties from an agent.
     *
     * @param agentDTO the agent dto
     * @return the list
     */
    public List<PropertySaleDTO> listProperties(AgentDTO agentDTO) {
        List<PropertySale> list = new ArrayList<>();

        for (PurchaseRequest purchaseRequest : purchaseRequestList) {
            if (purchaseRequest.getPropertySale().getAgent().getEmail().equals(agentDTO.email))
                if (!list.contains(purchaseRequest.getPropertySale()))
                    list.add(purchaseRequest.getPropertySale());

        }

        //sort by date

        PropertySale.SortByDateComparator sortByDate = new PropertySale.SortByDateComparator();

        list.sort(sortByDate);

        List<PropertySaleDTO> dtoList = new ArrayList<>();
        for (PropertySale p : list) {
            ErrorOptional<PropertySaleDTO> dto = mapperSale.toDTO(p);
            if (!dto.hasError())
                dtoList.add(dto.get());
        }

        return dtoList;
    }

    /**
     * Used to list purchase requests of a property sale.
     *
     * @param propertySaleDTO the property sale dto
     * @return the list
     */
    public List<PurchaseRequestDTO> listPurchaseRequests(PropertySaleDTO propertySaleDTO) {
        List<PurchaseRequest> list = new ArrayList<>();

        for (PurchaseRequest purchaseRequest : purchaseRequestList) {
            if (purchaseRequest.getPropertySale().getID() == propertySaleDTO.id)
                list.add(purchaseRequest);
        }

        PurchaseRequest.SortByPriceComparator sortByPrice = new PurchaseRequest.SortByPriceComparator();

        list.sort(sortByPrice);

        List<PurchaseRequestDTO> purchaseRequestDTOS = new ArrayList<>();
        for (PurchaseRequest purchaseRequest : purchaseRequestList) {
            purchaseRequestDTOS.add(mapper.toDTO(purchaseRequest).get());
        }
        return purchaseRequestDTOS;
    }

    /**
     * Used to remove a purchase request.
     *
     * @param dto the dto
     * @return the operation result
     */
    public OperationResult removePurchaseRequest(PurchaseRequestDTO dto) {
        if (dto == null) {
            return OperationResult.failed("Error - Repository - PurchaseRequestDTO cannot be null!");
        }

        for (PurchaseRequest purchaseRequest : purchaseRequestList) {
            if (purchaseRequest.getID() == dto.id) {
                purchaseRequestList.remove(purchaseRequest);
                return OperationResult.successfull();
            }
        }

        return OperationResult.failed("Error - Repository - PurchaseRequestDTO doesn't exist in the repository!");
    }


    /**
     * Used to remove all purchase requests of a property sale.
     *
     * @param propertySaleDTO the property sale dto
     * @param agentDTO        the agent dto
     * @return the operation result
     */
    public OperationResult removeAllPurchaseRequest(PropertySaleDTO propertySaleDTO, AgentDTO agentDTO) {
        ClientRepository clientRepository = Repositories.getClientRepository();

        MessageDTO messageDTO = new MessageDTO();
        messageDTO.message = "The property you made a purchase request has been sold to another client!";
        messageDTO.date = LocalDateTime.now();
        messageDTO.author = agentDTO;

        for (PurchaseRequest purchaseRequest : purchaseRequestList) {
            if (purchaseRequest.getPropertySale().getID() == propertySaleDTO.id) {
                purchaseRequestList.remove(purchaseRequest);
                clientRepository.notifyClient(purchaseRequest.getClient(), messageDTO);
            }
        }

        return OperationResult.successfull();
    }

    /**
     * Deserialize.
     */
    public void deserialize() {
        mapper = new PurchaseRequestMapper();
        mapperSale = new PropertySaleMapper();
    }
}
