package pt.ipp.isep.dei.esoft.project.domain.client.mappers;


import pt.ipp.isep.dei.esoft.project.domain.client.Owner;
import pt.ipp.isep.dei.esoft.project.domain.client.mappers.dto.OwnerDTO;
import pt.ipp.isep.dei.esoft.project.util.error.ErrorOptional;

import java.io.Serializable;

/**
 * The type Owner mapper.
 */
public class OwnerMapper implements Serializable {
    /**
     * Instantiates a new Owner mapper.
     */
    public OwnerMapper(){

    }

    /**
     * To dto error optional.
     * Used to map domain representation to dto representation.
     *
     * @param owner the owner
     * @return the error optional
     */
    public ErrorOptional<OwnerDTO> toDTO(Owner owner) {
        if (owner == null) return ErrorOptional.empty("Error - Mapper - Owner cannot be null!");

        OwnerDTO dto = new OwnerDTO();

        return ErrorOptional.of(dto);
    }

    /**
     * To domain error optional.
     * Used to map dto representation to domain representation.
     *
     * @param dto the dto
     * @return the error optional
     */
    public ErrorOptional<Owner> toDomain(OwnerDTO dto) {
        if (dto == null) return ErrorOptional.empty("Error - Mapper - OwnerDTO cannot be null!");

        Owner owner = new Owner();

        return ErrorOptional.of(owner);
    }
}
