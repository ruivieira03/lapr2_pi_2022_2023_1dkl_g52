package pt.ipp.isep.dei.esoft.project.domain.property.transactions.mappers;

import pt.ipp.isep.dei.esoft.project.domain.client.Client;
import pt.ipp.isep.dei.esoft.project.domain.client.mappers.dto.ClientDTO;
import pt.ipp.isep.dei.esoft.project.domain.employee.Agent;
import pt.ipp.isep.dei.esoft.project.domain.employee.mappers.dto.AgentDTO;
import pt.ipp.isep.dei.esoft.project.domain.property.Property;
import pt.ipp.isep.dei.esoft.project.domain.property.mappers.PropertyMapper;
import pt.ipp.isep.dei.esoft.project.domain.property.mappers.dto.PropertyDTO;
import pt.ipp.isep.dei.esoft.project.domain.property.transactions.PropertySold;
import pt.ipp.isep.dei.esoft.project.domain.property.transactions.mappers.dto.PropertySoldDTO;
import pt.ipp.isep.dei.esoft.project.domain.property.transactions.mappers.dto.PurchaseRequestDTO;
import pt.ipp.isep.dei.esoft.project.domain.user.mappers.UserMapper;
import pt.ipp.isep.dei.esoft.project.domain.user.mappers.dto.UserDTO;
import pt.ipp.isep.dei.esoft.project.repository.ClientRepository;
import pt.ipp.isep.dei.esoft.project.repository.Repositories;
import pt.ipp.isep.dei.esoft.project.repository.StoreRepository;
import pt.ipp.isep.dei.esoft.project.util.error.ErrorOptional;

import java.time.LocalDateTime;

/**
 * The type Property sold mapper.
 */
public class PropertySoldMapper {

    private final PropertyMapper propertyMapper = new PropertyMapper();
    private final UserMapper userMapper = new UserMapper();

    /**
     * Instantiates a new Property sold mapper.
     */
    public PropertySoldMapper() {

    }

    /**
     * To dto error optional.
     * Used to map domain representation to dto representation
     *
     * @param propertySold the property sold
     * @return the error optional
     */
    public ErrorOptional<PropertySoldDTO> toDTO(PropertySold propertySold) {
        if (propertySold == null) return ErrorOptional.empty("Error - Mapper - PropertySold cannot be null!");

        PropertySoldDTO dto = new PropertySoldDTO();

        ErrorOptional<PropertyDTO> propertyErrorOptional = propertyMapper.toDTO(propertySold.getProperty());
        if (propertyErrorOptional.hasError()) return ErrorOptional.empty(propertyErrorOptional.getErrorMessage());

        if (propertySold.getAgent() != null) {
            ErrorOptional<UserDTO> agentDTOErrorOptional = userMapper.toDTO(propertySold.getAgent());
            if (agentDTOErrorOptional.hasError()) return ErrorOptional.empty(agentDTOErrorOptional.getErrorMessage());
            dto.agent = (AgentDTO) agentDTOErrorOptional.get();
        }

        if (propertySold.getBuyer() != null) {
            ErrorOptional<UserDTO> buyerDTOErrorOptional = userMapper.toDTO(propertySold.getBuyer());
            if (buyerDTOErrorOptional.hasError()) return ErrorOptional.empty(buyerDTOErrorOptional.getErrorMessage());
            dto.buyer = (ClientDTO) buyerDTOErrorOptional.get();
        }

        dto.id = propertySold.getID();
        dto.typeOfBusiness = propertySold.getTypeOfBusiness();
        dto.property = propertyErrorOptional.get();
        dto.dateOfRequest = propertySold.getDateOfRequest();
        dto.typeOfCommission = propertySold.getTypeOfCommission();
        dto.commission = propertySold.getCommission();
        dto.requestedPrice = propertySold.getRequestedPrice();
        dto.price = propertySold.calculatePrice();
        dto.typeOfBusiness = propertySold.getTypeOfBusiness();
        dto.paidAmount = propertySold.getPaidAmount();
        dto.purchaseDate = propertySold.getPurchaseDate();

        return ErrorOptional.of(dto);
    }

    /**
     * To domain error optional.
     * Used to map dto representation to domain representation.
     *
     * @param dto the dto
     * @return the error optional
     */
    public ErrorOptional<PropertySold> toDomain(PropertySoldDTO dto) {
        if (dto == null) return ErrorOptional.empty("Error - Mapper - PropertySoldDTO cannot be null!");

        StoreRepository storeNetworkRepository = Repositories.getStoreRepository();
        ClientRepository clientRepository = Repositories.getClientRepository();

        PropertySold propertySold = new PropertySold();

        ErrorOptional<Property> propertyErrorOptional = propertyMapper.toDomain(dto.property);
        if (propertyErrorOptional.hasError()) return ErrorOptional.empty(propertyErrorOptional.getErrorMessage());


        if (dto.agent != null) {
            ErrorOptional<Agent> agentOptional = storeNetworkRepository.getAgentFromEmail(dto.agent.email);
            if (agentOptional.hasError()) {
                return ErrorOptional.empty(agentOptional.getErrorMessage() + "\nError - Mapper - StoreNetworkRepository does contain Agent[" + dto.agent.email + "]");
            }
            propertySold.setAgent(agentOptional.get());
        }


        if (dto.buyer != null) {
            ErrorOptional<Client> buyerErrorOptional = clientRepository.getClientFromEmail(dto.buyer.email);
            if (buyerErrorOptional.hasError()) {
                return ErrorOptional.empty(buyerErrorOptional.getErrorMessage() + "\nError - Mapper - ClientRepository does contain Client[" + dto.buyer.email + "]");
            }
            propertySold.setBuyer(buyerErrorOptional.get());
        }

        propertySold.setTypeOfBusiness(dto.typeOfBusiness);
        propertySold.setProperty(propertyErrorOptional.get());
        propertySold.setRequestedPrice(dto.requestedPrice);
        propertySold.setDateOfRequest(dto.dateOfRequest);
        propertySold.setTypeOfCommission(dto.typeOfCommission);
        propertySold.setCommission(dto.commission);
        propertySold.setRequestedPrice(dto.requestedPrice);
        propertySold.setTypeOfBusiness(dto.typeOfBusiness);
        propertySold.setPrice(propertySold.calculatePrice());
        propertySold.setPaidAmount(dto.paidAmount);
        propertySold.setPurchaseDate(dto.purchaseDate);

        return ErrorOptional.of(propertySold);
    }

    /**
     * To domain error optional.
     * Used to map dto representation to domain representation.
     *
     * @param dto the dto
     * @return the error optional
     */
    public ErrorOptional<PropertySold> toDomain(PurchaseRequestDTO dto) {
        if (dto == null) return ErrorOptional.empty("Error - Mapper - PurchaseRequestDTO cannot be null!");

        StoreRepository storeNetworkRepository = Repositories.getStoreRepository();
        ClientRepository clientRepository = Repositories.getClientRepository();

        PropertySold propertySold = new PropertySold();

        ErrorOptional<Property> propertyErrorOptional = propertyMapper.toDomain(dto.propertySale.property);
        if (propertyErrorOptional.hasError()) return ErrorOptional.empty(propertyErrorOptional.getErrorMessage());


        ErrorOptional<Agent> agentOptional = storeNetworkRepository.getAgentFromEmail(dto.propertySale.agent.email);
        if (agentOptional.hasError()) {
            return ErrorOptional.empty(agentOptional.getErrorMessage() + "\nError - Mapper - StoreNetworkRepository does contain Agent[" + dto.propertySale.agent.email + "]");
        }

        ErrorOptional<Client> buyerErrorOptional = clientRepository.getClientFromEmail(dto.client.email);
        if (buyerErrorOptional.hasError())
            return ErrorOptional.empty(agentOptional.getErrorMessage() + "\nError - Mapper - ClientRepository does contain Client[" + dto.client.email + "]");

        propertySold.setAgent(agentOptional.get());
        propertySold.setTypeOfBusiness(dto.propertySale.typeOfBusiness);
        propertySold.setProperty(propertyErrorOptional.get());
        propertySold.setRequestedPrice(dto.propertySale.requestedPrice);
        propertySold.setDateOfRequest(dto.propertySale.dateOfRequest);
        propertySold.setTypeOfCommission(dto.propertySale.typeOfCommission);
        propertySold.setCommission(dto.propertySale.commission);
        propertySold.setRequestedPrice(dto.propertySale.requestedPrice);
        propertySold.setPrice(propertySold.calculatePrice());
        propertySold.setBuyer(buyerErrorOptional.get());
        propertySold.setPaidAmount(dto.orderAmount);
        propertySold.setPurchaseDate(LocalDateTime.now());

        return ErrorOptional.of(propertySold);
    }
}
