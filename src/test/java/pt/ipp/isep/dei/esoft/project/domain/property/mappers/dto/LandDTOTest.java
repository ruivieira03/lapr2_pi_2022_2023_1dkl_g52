package pt.ipp.isep.dei.esoft.project.domain.property.mappers.dto;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pt.ipp.isep.dei.esoft.project.domain.client.mappers.dto.ClientDTO;
import pt.ipp.isep.dei.esoft.project.domain.locations.mappers.dto.CityDTO;
import pt.ipp.isep.dei.esoft.project.domain.locations.mappers.dto.DistrictDTO;
import pt.ipp.isep.dei.esoft.project.domain.locations.mappers.dto.StateDTO;

class LandDTOTest {
    private LandDTO landDTO;

    @BeforeEach
    void beforeEach() {
        // empty constructor
        landDTO = new LandDTO();

        // full contructor
        // locations
        StateDTO stateDTO = new StateDTO("State", new String[]{"District"}, new String[][]{{"City"}});
        DistrictDTO districtDTO = stateDTO.districtList.get(0);
        CityDTO cityDTO = districtDTO.cityList.get(0);

        // owner
        ClientDTO clientDTO = new ClientDTO("name", 123456789, "123-123-1234", "123-12-1234", "", "ex@ex.com");

        landDTO = new LandDTO(1000, 1.0, "Street", 12345, stateDTO, districtDTO, cityDTO, "p", clientDTO);
    }

    @Test
    void test() {
        assert (landDTO instanceof PropertyDTO);
    }
}