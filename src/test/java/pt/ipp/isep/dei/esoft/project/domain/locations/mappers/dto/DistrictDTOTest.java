package pt.ipp.isep.dei.esoft.project.domain.locations.mappers.dto;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DistrictDTOTest {

    private DistrictDTO districtDTO;

    @BeforeEach
    void beforeEach() {
        districtDTO = new DistrictDTO();
        districtDTO = new DistrictDTO("District");
        districtDTO = new DistrictDTO("District", new String[]{"City"});
    }

    @Test
    void test() {
        assertEquals(districtDTO.name, districtDTO.toString());
    }

}