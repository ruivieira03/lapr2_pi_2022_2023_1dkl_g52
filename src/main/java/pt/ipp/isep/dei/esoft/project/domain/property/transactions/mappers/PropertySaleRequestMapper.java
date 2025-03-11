package pt.ipp.isep.dei.esoft.project.domain.property.transactions.mappers;

import pt.ipp.isep.dei.esoft.project.domain.employee.Agent;
import pt.ipp.isep.dei.esoft.project.domain.employee.mappers.dto.AgentDTO;
import pt.ipp.isep.dei.esoft.project.domain.property.Property;
import pt.ipp.isep.dei.esoft.project.domain.property.mappers.PropertyMapper;
import pt.ipp.isep.dei.esoft.project.domain.property.mappers.dto.PropertyDTO;
import pt.ipp.isep.dei.esoft.project.domain.property.transactions.PropertySaleRequest;
import pt.ipp.isep.dei.esoft.project.domain.property.transactions.mappers.dto.PropertySaleRequestDTO;
import pt.ipp.isep.dei.esoft.project.domain.user.mappers.UserMapper;
import pt.ipp.isep.dei.esoft.project.domain.user.mappers.dto.UserDTO;
import pt.ipp.isep.dei.esoft.project.repository.Repositories;
import pt.ipp.isep.dei.esoft.project.repository.StoreRepository;
import pt.ipp.isep.dei.esoft.project.util.error.ErrorOptional;

/**
 * The type Property sale request mapper.
 */
public class PropertySaleRequestMapper {

    private final transient PropertyMapper propertyMapper = new PropertyMapper();
    private final transient UserMapper userMapper = new UserMapper();
    private final Repositories repositories = Repositories.getInstance();

    /**
     * Instantiates a new Property sale request mapper.
     */
    public PropertySaleRequestMapper() {

    }

    /**
     * To dto error optional.
     * Used to map domain representation to dto representation.
     *
     * @param propertySaleRequest the property sale request
     * @return the error optional
     */
    public ErrorOptional<PropertySaleRequestDTO> toDTO(PropertySaleRequest propertySaleRequest) {
        if (propertySaleRequest == null)
            return ErrorOptional.empty("Error - Mapper - PropertySaleRequest cannot be null!");

        PropertySaleRequestDTO dto = new PropertySaleRequestDTO();

        dto.id = propertySaleRequest.getID();

        ErrorOptional<PropertyDTO> errorOptional = propertyMapper.toDTO(propertySaleRequest.getProperty());
        if (errorOptional.hasError()) return ErrorOptional.empty(errorOptional.getErrorMessage());
        dto.property = errorOptional.get();

        dto.typeOfBusiness = propertySaleRequest.getTypeOfBusiness();
        dto.requestedPrice = propertySaleRequest.getRequestedPrice();

        ErrorOptional<UserDTO> userDTOErrorOptional = userMapper.toDTO(propertySaleRequest.getAgent());
        if (userDTOErrorOptional.hasError()) return ErrorOptional.empty(userDTOErrorOptional.getErrorMessage());
        if (!(userDTOErrorOptional.get() instanceof AgentDTO))
            return ErrorOptional.empty("Error - Mapper - UserMapper did not return an agent!");

        dto.agent = (AgentDTO) userDTOErrorOptional.get();
        dto.dateOfRequest = propertySaleRequest.getDateOfRequest();

        return ErrorOptional.of(dto);
    }

    /**
     * To domain error optional.
     * Used to map dto representation to domain representation.
     *
     * @param dto the dto
     * @return the error optional
     */
    public ErrorOptional<PropertySaleRequest> toDomain(PropertySaleRequestDTO dto) {
        if (dto == null) return ErrorOptional.empty("Error - Mapper - PropertySaleRequestDTO cannot be null!");

        StoreRepository storeNetworkRepository = Repositories.getStoreRepository();

        PropertySaleRequest propertySaleRequest = new PropertySaleRequest();

        ErrorOptional<Property> propertyErrorOptional = propertyMapper.toDomain(dto.property);
        if (propertyErrorOptional.hasError()) return ErrorOptional.empty(propertyErrorOptional.getErrorMessage());
        propertySaleRequest.setProperty(propertyErrorOptional.get());

        propertySaleRequest.setTypeOfBusiness(dto.typeOfBusiness);
        propertySaleRequest.setRequestedPrice(dto.requestedPrice);

        ErrorOptional<Agent> agent = storeNetworkRepository.getAgentFromEmail(dto.agent.email);
        if (agent.hasError()) return ErrorOptional.empty(agent.getErrorMessage() + "\nError - Mapper - Agent[" + dto.agent.email + "] not found!");
        propertySaleRequest.setAgent(agent.get());

        propertySaleRequest.setDateOfRequest(dto.dateOfRequest);

        return ErrorOptional.of(propertySaleRequest);
    }
}
