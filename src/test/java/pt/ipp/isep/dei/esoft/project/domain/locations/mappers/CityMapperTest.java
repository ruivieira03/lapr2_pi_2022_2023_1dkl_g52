package pt.ipp.isep.dei.esoft.project.domain.locations.mappers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pt.ipp.isep.dei.esoft.project.domain.locations.City;
import pt.ipp.isep.dei.esoft.project.domain.locations.mappers.dto.CityDTO;
import pt.ipp.isep.dei.esoft.project.util.error.ErrorOptional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

class CityMapperTest {

    private City city;
    private CityDTO cityDTO;
    private final CityMapper mapper = new CityMapper();


    @BeforeEach
    void beforeEach() {
        // ------Domain------
        city = new City();
        city.setName("City");

        // -----DTO-----
        cityDTO = new CityDTO("City");
    }
    @Test
    void toDTO() {
        // valid
        ErrorOptional<CityDTO> dto = mapper.toDTO(city);
        assertFalse(dto.hasError());
        // all atributes must be equal
        assertEquals(dto.get().name, city.getName());

        // invalid - null
        dto = mapper.toDTO(null);
        assert (dto.hasError());
    }

    @Test
    void toDomain() {
        // valid
        ErrorOptional<City> domain = mapper.toDomain(cityDTO);
        assertFalse(domain.hasError());
        // all atributes must be equal
        assertEquals(cityDTO.name, domain.get().getName());

        // invalid - null
        domain = mapper.toDomain(null);
        assert (domain.hasError());
    }
}