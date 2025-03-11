package pt.ipp.isep.dei.esoft.project.domain.property.transactions.mappers.dto;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pt.ipp.isep.dei.esoft.project.domain.client.mappers.dto.ClientDTO;
import pt.ipp.isep.dei.esoft.project.domain.employee.mappers.dto.AgentDTO;
import pt.ipp.isep.dei.esoft.project.domain.locations.mappers.dto.CityDTO;
import pt.ipp.isep.dei.esoft.project.domain.locations.mappers.dto.DistrictDTO;
import pt.ipp.isep.dei.esoft.project.domain.locations.mappers.dto.StateDTO;
import pt.ipp.isep.dei.esoft.project.domain.property.House;
import pt.ipp.isep.dei.esoft.project.domain.property.mappers.dto.ApartmentDTO;
import pt.ipp.isep.dei.esoft.project.domain.property.mappers.dto.HouseDTO;
import pt.ipp.isep.dei.esoft.project.domain.property.mappers.dto.LandDTO;
import pt.ipp.isep.dei.esoft.project.domain.property.transactions.PropertySaleRequest;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PropertySaleRequestDTOTest {

    private PropertySaleRequestDTO propertySaleRequestDTO;

    @BeforeEach
    void beforeEach() {
        // empty constructor
        propertySaleRequestDTO = new PropertySaleRequestDTO();

        // full contructor
        // locations
        StateDTO stateDTO = new StateDTO("State", new String[]{"District"}, new String[][]{{"City"}});
        DistrictDTO districtDTO = stateDTO.districtList.get(0);
        CityDTO cityDTO = districtDTO.cityList.get(0);

        // owner
        ClientDTO clientDTO = new ClientDTO("name", 123456789, "123-123-1234", "123-12-1234", "", "ex@ex.com");

        // property
        LandDTO landDTO = new LandDTO(1000, 1.0, "Street", 12345, stateDTO, districtDTO, cityDTO, "p", clientDTO);

        // agent

        AgentDTO agentDTO = new AgentDTO("John", 123456789, "123-123-1234", "123-12-1234", "adress", "ex@ex.com");

        propertySaleRequestDTO = new PropertySaleRequestDTO(landDTO, PropertySaleRequest.TypesOfBusinesses.RENT, 100_00, agentDTO, LocalDate.now());
    }

    @Test
    void testToString() {
        String expected = "                name|     Land|      RENT|           10000|               State|            District|                City|                                  Street|     12345|      1000|                1.00|          |           |         |                              |          |                  |              |";
        assertEquals(expected, propertySaleRequestDTO.toString());


        StateDTO stateDTO = new StateDTO("State", new String[]{"District"}, new String[][]{{"City"}});
        DistrictDTO districtDTO = stateDTO.districtList.get(0);
        CityDTO cityDTO = districtDTO.cityList.get(0);
        ClientDTO clientDTO = new ClientDTO("name", 123456789, "123-123-1234", "123-12-1234", "", "ex@ex.com");

        // apartment
        propertySaleRequestDTO.property = new ApartmentDTO(1000, 1.0, "Street", 12345, stateDTO, districtDTO, cityDTO, "p", clientDTO, 1, 2, 3, "Avaliable Equipment");
        expected = "                name|Apartment|      RENT|           10000|               State|            District|                City|                                  Street|     12345|      1000|                1.00|         1|          2|        3|           Avaliable Equipment|          |                  |              |";
        assertEquals(expected, propertySaleRequestDTO.toString());

        // house
        propertySaleRequestDTO.property = new HouseDTO(1000, 1.0, "Street", 12345, stateDTO, districtDTO, cityDTO, "p", clientDTO, 1, 2, 3, "Avaliable Equipment", false, true, House.SunExposure.NORTH);
        expected = "                name|    House|      RENT|           10000|               State|            District|                City|                                  Street|     12345|      1000|                1.00|         1|          2|        3|           Avaliable Equipment|        No|               Yes|         NORTH|";
        assertEquals(expected, propertySaleRequestDTO.toString());

    }
}