package pt.ipp.isep.dei.esoft.project.domain.property.transactions.mappers;

import pt.ipp.isep.dei.esoft.project.domain.employee.Agent;
import pt.ipp.isep.dei.esoft.project.domain.employee.mappers.dto.AgentDTO;
import pt.ipp.isep.dei.esoft.project.domain.property.Property;
import pt.ipp.isep.dei.esoft.project.domain.property.mappers.PropertyMapper;
import pt.ipp.isep.dei.esoft.project.domain.property.mappers.dto.PropertyDTO;
import pt.ipp.isep.dei.esoft.project.domain.property.transactions.PropertySale;
import pt.ipp.isep.dei.esoft.project.domain.property.transactions.mappers.dto.PropertySaleDTO;
import pt.ipp.isep.dei.esoft.project.domain.property.transactions.mappers.dto.PropertySaleRequestDTO;
import pt.ipp.isep.dei.esoft.project.domain.user.mappers.UserMapper;
import pt.ipp.isep.dei.esoft.project.domain.user.mappers.dto.UserDTO;
import pt.ipp.isep.dei.esoft.project.repository.Repositories;
import pt.ipp.isep.dei.esoft.project.repository.StoreRepository;
import pt.ipp.isep.dei.esoft.project.util.error.ErrorOptional;

/**
 * The type Property sale mapper.
 */
public class PropertySaleMapper {

    private final PropertyMapper propertyMapper = new PropertyMapper();
    private final UserMapper userMapper = new UserMapper();

    private final Repositories repositories = Repositories.getInstance();

    /**
     * Instantiates a new Property sale mapper.
     */
    public PropertySaleMapper() {

    }

    /**
     * To dto error optional.
     * Used to map domain representation to dto representation.
     *
     * @param propertySale the property sale
     * @return the error optional
     */
    public ErrorOptional<PropertySaleDTO> toDTO(PropertySale propertySale) {
        if (propertySale == null) return ErrorOptional.empty("Error - Mapper - PropertySale cannot be null!");

        PropertySaleDTO dto = new PropertySaleDTO();

        dto.id = propertySale.getID();

        ErrorOptional<PropertyDTO> propertyErrorOptional = propertyMapper.toDTO(propertySale.getProperty());
        if (propertyErrorOptional.hasError()) return ErrorOptional.empty(propertyErrorOptional.getErrorMessage());
        dto.property = propertyErrorOptional.get();

        dto.typeOfBusiness = propertySale.getTypeOfBusiness();

        ErrorOptional<UserDTO> userDTOErrorOptional = userMapper.toDTO(propertySale.getAgent());
        if (userDTOErrorOptional.hasError()) return ErrorOptional.empty(userDTOErrorOptional.getErrorMessage());
        dto.agent = (AgentDTO) userDTOErrorOptional.get();

        dto.dateOfRequest = propertySale.getDateOfRequest();
        dto.typeOfCommission = propertySale.getTypeOfCommission();
        dto.commission = propertySale.getCommission();
        dto.requestedPrice = propertySale.getRequestedPrice();
        dto.price = propertySale.calculatePrice();
        dto.typeOfBusiness = propertySale.getTypeOfBusiness();

        return ErrorOptional.of(dto);
    }

    /**
     * To domain error optional.
     * Used to map dto representation to domain representation.
     *
     * @param dto the dto
     * @return the error optional
     */
    public ErrorOptional<PropertySale> toDomain(PropertySaleDTO dto) {
        if (dto == null) return ErrorOptional.empty("Error - Mapper - PropertySaleDTO cannot be null!");

        StoreRepository storeNetworkRepository = Repositories.getStoreRepository();

        PropertySale propertySale = new PropertySale();

        ErrorOptional<Property> propertyErrorOptional = propertyMapper.toDomain(dto.property);
        if (propertyErrorOptional.hasError()) return ErrorOptional.empty(propertyErrorOptional.getErrorMessage());
        propertySale.setProperty(propertyErrorOptional.get());

        propertySale.setTypeOfBusiness(dto.typeOfBusiness);

        ErrorOptional<Agent> agentOptional = storeNetworkRepository.getAgentFromEmail(dto.agent.email);
        if (agentOptional.hasError())
            return ErrorOptional.empty(agentOptional.getErrorMessage() + "\nError - Mapper - StoreNetworkRepository does contain Agent[" + dto.agent.email + "]");
        propertySale.setAgent(agentOptional.get());

        propertySale.setRequestedPrice(dto.requestedPrice);
        propertySale.setDateOfRequest(dto.dateOfRequest);
        propertySale.setTypeOfCommission(dto.typeOfCommission);
        propertySale.setCommission(dto.commission);
        propertySale.setRequestedPrice(dto.requestedPrice);
        propertySale.setTypeOfBusiness(dto.typeOfBusiness);
        propertySale.setPrice(propertySale.calculatePrice());

        return ErrorOptional.of(propertySale);
    }

    /**
     * To domain error optional.
     * Used to map dto representation to domain representation.
     *
     * @param request       the request
     * @param comissionType the comission type
     * @param comission     the comission
     * @return the error optional
     */
    public ErrorOptional<PropertySale> toDomain(PropertySaleRequestDTO request, PropertySale.CommissionType comissionType, double comission){
        if (request == null) return ErrorOptional.empty("Error - Mapper - PropertySaleRequestDTO cannot be null!");

        StoreRepository storeNetworkRepository = Repositories.getStoreRepository();

        PropertySale propertySale = new PropertySale();

        ErrorOptional<Property> propertyErrorOptional = propertyMapper.toDomain(request.property);
        if (propertyErrorOptional.hasError()) return ErrorOptional.empty(propertyErrorOptional.getErrorMessage());
        propertySale.setProperty(propertyErrorOptional.get());

        ErrorOptional<Agent> agentOptional = storeNetworkRepository.getAgentFromEmail(request.agent.email);
        if (agentOptional.hasError())
            return ErrorOptional.empty(agentOptional.getErrorMessage() + "\nError - Mapper - StoreNetworkRepository does contain Agent[" + request.agent.email + "]");

        propertySale.setAgent(agentOptional.get());
        propertySale.setDateOfRequest(request.dateOfRequest);
        propertySale.setTypeOfCommission(comissionType);
        propertySale.setCommission(comission);
        propertySale.setTypeOfBusiness(request.typeOfBusiness);
        propertySale.setRequestedPrice(request.requestedPrice);
        propertySale.setPrice(propertySale.calculatePrice());

        return ErrorOptional.of(propertySale);


    }
}
