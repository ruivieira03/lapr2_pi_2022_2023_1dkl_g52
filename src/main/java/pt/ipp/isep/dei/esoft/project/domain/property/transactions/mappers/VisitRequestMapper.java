package pt.ipp.isep.dei.esoft.project.domain.property.transactions.mappers;

import pt.ipp.isep.dei.esoft.project.domain.client.Client;
import pt.ipp.isep.dei.esoft.project.domain.client.mappers.dto.ClientDTO;
import pt.ipp.isep.dei.esoft.project.domain.property.transactions.PropertySale;
import pt.ipp.isep.dei.esoft.project.domain.property.transactions.VisitRequest;
import pt.ipp.isep.dei.esoft.project.domain.property.transactions.mappers.dto.PropertySaleDTO;
import pt.ipp.isep.dei.esoft.project.domain.property.transactions.mappers.dto.VisitRequestDTO;
import pt.ipp.isep.dei.esoft.project.domain.user.mappers.UserMapper;
import pt.ipp.isep.dei.esoft.project.domain.user.mappers.dto.UserDTO;
import pt.ipp.isep.dei.esoft.project.repository.ClientRepository;
import pt.ipp.isep.dei.esoft.project.repository.PropertySaleRepository;
import pt.ipp.isep.dei.esoft.project.repository.Repositories;
import pt.ipp.isep.dei.esoft.project.util.error.ErrorOptional;

/**
 * The type Visit request mapper.
 */
public class VisitRequestMapper {

    /**
     * Instantiates a new Visit request mapper.
     */
    public VisitRequestMapper() {

    }

    private final transient UserMapper userMapper = new UserMapper();

    private final transient PropertySaleMapper propertySaleMapper = new PropertySaleMapper();

    /**
     * To dto error optional.
     * Used to map domain representation to dto representation.
     *
     * @param visitRequest the visit request
     * @return the error optional
     */
    public ErrorOptional<VisitRequestDTO> toDTO(VisitRequest visitRequest) {
        if (visitRequest == null) return ErrorOptional.empty("Error - Mapper - VisitRequest cannot be null!");

        VisitRequestDTO dto = new VisitRequestDTO();

        ErrorOptional<UserDTO> user = userMapper.toDTO(visitRequest.getClient());
        ErrorOptional<PropertySaleDTO> propertySale = propertySaleMapper.toDTO(visitRequest.getPropertySale());

        if (user.hasError())
            return ErrorOptional.empty(user.getErrorMessage() + "\nError - Mapper - Failed to convert Client into ClientDTO!");
        if (propertySale.hasError())
            return ErrorOptional.empty(propertySale.getErrorMessage() + "\nError - Mapper - Failed to convert PropertySale into PropertySaleDTO");

        dto.id = visitRequest.getId();
        dto.client = (ClientDTO) user.get();
        dto.propertySale = propertySale.get();
        dto.visitStart = visitRequest.getVisitStart();
        dto.visitEnd = visitRequest.getVisitEnd();

        return ErrorOptional.of(dto);
    }

    /**
     * To domain error optional.
     * Used to map dto representation to domain representation.
     *
     * @param dto the dto
     * @return the error optional
     */
    public ErrorOptional<VisitRequest> toDomain(VisitRequestDTO dto) {
        if (dto == null) return ErrorOptional.empty("Error - Mapper - VisitRequestDTO cannot be null!");

        ClientRepository clientRepository = Repositories.getClientRepository();
        PropertySaleRepository propertySaleRepository = Repositories.getPropertySaleRepository();

        VisitRequest visitRequest = new VisitRequest();

        ErrorOptional<Client> client = clientRepository.getClientFromEmail(dto.client.email);
        ErrorOptional<PropertySale> propertySale = propertySaleRepository.getPropertySaleFromID(dto.propertySale.id);


        if (client.hasError()) return ErrorOptional.empty(client.getErrorMessage() + "\nError - Mapper - Failed to get Client from repository!");
        if (propertySale.hasError()) return ErrorOptional.empty(propertySale.getErrorMessage() + "\nError - Mapper - Failed to get PropertySala from repository!");

        visitRequest.setClient(client.get());
        visitRequest.setPropertySale(propertySale.get());
        visitRequest.setVisitStart(dto.visitStart);
        visitRequest.setVisitEnd(dto.visitEnd);

        return ErrorOptional.of(visitRequest);
    }

}
