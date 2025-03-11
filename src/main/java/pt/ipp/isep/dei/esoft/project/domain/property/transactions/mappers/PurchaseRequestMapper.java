package pt.ipp.isep.dei.esoft.project.domain.property.transactions.mappers;

import pt.ipp.isep.dei.esoft.project.domain.client.Client;
import pt.ipp.isep.dei.esoft.project.domain.client.mappers.dto.ClientDTO;
import pt.ipp.isep.dei.esoft.project.domain.property.transactions.PropertySale;
import pt.ipp.isep.dei.esoft.project.domain.property.transactions.PurchaseRequest;
import pt.ipp.isep.dei.esoft.project.domain.property.transactions.mappers.dto.PropertySaleDTO;
import pt.ipp.isep.dei.esoft.project.domain.property.transactions.mappers.dto.PurchaseRequestDTO;
import pt.ipp.isep.dei.esoft.project.domain.user.mappers.UserMapper;
import pt.ipp.isep.dei.esoft.project.domain.user.mappers.dto.UserDTO;
import pt.ipp.isep.dei.esoft.project.repository.ClientRepository;
import pt.ipp.isep.dei.esoft.project.repository.PropertySaleRepository;
import pt.ipp.isep.dei.esoft.project.repository.Repositories;
import pt.ipp.isep.dei.esoft.project.util.error.ErrorOptional;

/**
 * The type Purchase request mapper.
 */
public class PurchaseRequestMapper {
    private final transient PropertySaleMapper propertySaleMapper = new PropertySaleMapper();
    private final transient UserMapper userMapper = new UserMapper();
    private final transient Repositories repositories = Repositories.getInstance();


    /**
     * Instantiates a new Purchase request mapper.
     */
    public PurchaseRequestMapper() {
    }

    /**
     * To dto error optional.
     * Used to map domain representation to dto representation.
     *
     *
     * @param purchaseRequest the purchase request
     * @return the error optional
     */
    public ErrorOptional<PurchaseRequestDTO> toDTO(PurchaseRequest purchaseRequest) {
        if (purchaseRequest == null) return ErrorOptional.empty("Error - Mapper - PurchaseRequest cannot be null!");

        PurchaseRequestDTO dto = new PurchaseRequestDTO();
        dto.orderAmount = purchaseRequest.getOrderAmount();

        ErrorOptional<PropertySaleDTO> propertySale = propertySaleMapper.toDTO(purchaseRequest.getPropertySale());
        if (propertySale.hasError())
            return ErrorOptional.empty(propertySale.getErrorMessage() + "\nError - Mapper Failed to convert PurchaseRequest into PurchaseRequestDTO!");
        dto.propertySale = propertySale.get();

        ErrorOptional<UserDTO> user = userMapper.toDTO(purchaseRequest.getClient());
        if (user.hasError())
            return ErrorOptional.empty(user.getErrorMessage() + "\nError - Mapper Failed to convert PurchaseRequest into PurchaseRequestDTO!");
        dto.client = (ClientDTO) user.get();

        dto.id = purchaseRequest.getID();

        return ErrorOptional.of(dto);
    }

    /**
     * To domain error optional.
     * Used to map dto representation to domain representation.
     *
     * @param dto the dto
     * @return the error optional
     */
    public ErrorOptional<PurchaseRequest> toDomain(PurchaseRequestDTO dto) {
        if (dto == null) return ErrorOptional.empty("Error - Mapper - PurchaseRequestDTO cannot be null!");

        ClientRepository clientRepository = Repositories.getClientRepository();
        PropertySaleRepository propertySaleRepository = Repositories.getPropertySaleRepository();

        PurchaseRequest purchaseRequest = new PurchaseRequest();

        ErrorOptional<PropertySale> propertySaleErrorOptional = propertySaleRepository.getPropertySaleFromID(dto.propertySale.id);
        if (propertySaleErrorOptional.hasError())
            return ErrorOptional.empty(propertySaleErrorOptional.getErrorMessage() + "\nError - Mapper - Failed to get Propertysale from repository!");
        purchaseRequest.setPropertySale(propertySaleErrorOptional.get());


        ErrorOptional<Client> client = clientRepository.getClientFromEmail(dto.client.email);
        if (client.hasError())
            return ErrorOptional.empty(client.getErrorMessage() + "\nError - Mapper Failed to convert PurchaseRequestDTO into PurchaseRequest!");
        purchaseRequest.setClient(client.get());

        purchaseRequest.setOrderAmount(dto.orderAmount);

        return ErrorOptional.of(purchaseRequest);
    }
}
