package pt.ipp.isep.dei.esoft.project.domain.property.mappers;

import pt.ipp.isep.dei.esoft.project.domain.property.Apartment;
import pt.ipp.isep.dei.esoft.project.domain.property.mappers.dto.ApartmentDTO;
import pt.ipp.isep.dei.esoft.project.util.error.ErrorOptional;

/**
 * The type Apartment mapper.
 */
public class ApartmentMapper {

    /**
     * Instantiates a new Apartment mapper.
     */
    public ApartmentMapper() {

    }

    /**
     * To dto error optional.
     * Used to map domain representation to dto representation.
     *
     * @param apartment the apartment
     * @return the error optional
     */
    public ErrorOptional<ApartmentDTO> toDTO(Apartment apartment) {
        if (apartment == null) return ErrorOptional.empty("Error - Mapper - Apartment cannot be null!");

        ApartmentDTO dto = new ApartmentDTO();

        return ErrorOptional.of(dto);
    }

    /**
     * To domain error optional.
     * Used to map dto representation to domain representation.
     *
     * @param dto the dto
     * @return the error optional
     */
    public ErrorOptional<Apartment> toDomain(ApartmentDTO dto) {
        if (dto == null) return ErrorOptional.empty("Error - Mapper - ApartmentDTO cannot be null!");

        Apartment apartment = new Apartment();

        return ErrorOptional.of(apartment);
    }
}
