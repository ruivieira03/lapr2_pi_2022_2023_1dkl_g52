package pt.ipp.isep.dei.esoft.project.domain.client.mappers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pt.ipp.isep.dei.esoft.project.domain.client.Owner;
import pt.ipp.isep.dei.esoft.project.domain.client.mappers.dto.OwnerDTO;
import pt.ipp.isep.dei.esoft.project.util.error.ErrorOptional;

import static org.junit.jupiter.api.Assertions.assertFalse;

class OwnerMapperTest {

    private OwnerDTO ownerDTO;
    private Owner owner;
    private final OwnerMapper mapper = new OwnerMapper();


    @BeforeEach
    void beforeEach() {
        // -----Domain-----
        owner = new Owner();

        // -----DTO-----
        ownerDTO = new OwnerDTO();

    }

    @Test
    void toDTO() {
        // valid
        ErrorOptional<OwnerDTO> dto = mapper.toDTO(owner);
        assertFalse(dto.hasError());

        // test null
        dto = mapper.toDTO(null);
        assert (dto.hasError());

    }

    @Test
    void toDomain() {
        // valid
        ErrorOptional<Owner> domain = mapper.toDomain(ownerDTO);
        assertFalse(domain.hasError());

        // test null
        domain = mapper.toDomain(null);
        assert (domain.hasError());
    }
}