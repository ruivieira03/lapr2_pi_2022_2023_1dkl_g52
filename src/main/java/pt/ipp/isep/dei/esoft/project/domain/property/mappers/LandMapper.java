package pt.ipp.isep.dei.esoft.project.domain.property.mappers;

import pt.ipp.isep.dei.esoft.project.domain.property.Land;
import pt.ipp.isep.dei.esoft.project.domain.property.mappers.dto.LandDTO;
import pt.ipp.isep.dei.esoft.project.util.error.ErrorOptional;

/**
 * The type Land mapper.
 */
public class LandMapper {

    /**
     * Instantiates a new Land mapper.
     */
    public LandMapper(){

    }

    /**
     * To dto error optional.
     * Used to map domain representation to dto representation.
     *
     * @param land the land
     * @return the error optional
     */
    public ErrorOptional<LandDTO> toDTO(Land land) {
        if (land == null) return ErrorOptional.empty("Error - Mapper - Land cannot be null!");

        LandDTO dto = new LandDTO();

        return ErrorOptional.of(dto);
    }

    /**
     * To domain error optional.
     * Used to map dto representation to domain representation.
     *
     * @param dto the dto
     * @return the error optional
     */
    public ErrorOptional<Land> toDomain(LandDTO dto) {
        if (dto == null) return ErrorOptional.empty("Error - Mapper - LandDTO cannot be null");

        Land land = new Land();

        return ErrorOptional.of(land);
    }
}
