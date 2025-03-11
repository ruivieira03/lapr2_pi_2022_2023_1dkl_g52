package pt.ipp.isep.dei.esoft.project.domain.employee.mappers;

import pt.ipp.isep.dei.esoft.project.domain.employee.StoreManager;
import pt.ipp.isep.dei.esoft.project.domain.employee.mappers.dto.StoreManagerDTO;
import pt.ipp.isep.dei.esoft.project.util.error.ErrorOptional;

/**
 * The type Store manager mapper.
 */
public class StoreManagerMapper {
    /**
     * Instantiates a new Store manager mapper.
     */
    public StoreManagerMapper() {

    }

    /**
     * To dto error optional.
     * Used to map domain representation to dto representation.
     *
     * @param storeManager the store manager
     * @return the error optional
     */
    public ErrorOptional<StoreManagerDTO> toDTO(StoreManager storeManager) {
        if (storeManager == null) return ErrorOptional.empty("Error - Mapper - StoreMAnager cannot be null!");

        StoreManagerDTO dto = new StoreManagerDTO();

        return ErrorOptional.of(dto);
    }

    /**
     * To domain error optional.
     * Used to map dto representation to domain representation.
     *
     * @param dto the dto
     * @return the error optional
     */
    public ErrorOptional<StoreManager> toDomain(StoreManagerDTO dto) {
        if (dto == null) return ErrorOptional.empty("Error - Mapper - StoreManagerDTO cannot be null!");

        StoreManager storeManager = new StoreManager();

        return ErrorOptional.of(storeManager);
    }
}
