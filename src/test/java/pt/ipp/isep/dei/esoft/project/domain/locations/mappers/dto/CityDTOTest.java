package pt.ipp.isep.dei.esoft.project.domain.locations.mappers.dto;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CityDTOTest {

    private CityDTO cityDTO;

    @BeforeEach
    void beforeEach() {
        cityDTO = new CityDTO();
        cityDTO = new CityDTO("City");
    }

    @Test
    void test() {
        assertEquals(cityDTO.name, cityDTO.toString());
    }

}