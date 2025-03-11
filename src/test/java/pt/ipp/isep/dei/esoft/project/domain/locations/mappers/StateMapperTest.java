package pt.ipp.isep.dei.esoft.project.domain.locations.mappers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pt.ipp.isep.dei.esoft.project.domain.locations.City;
import pt.ipp.isep.dei.esoft.project.domain.locations.District;
import pt.ipp.isep.dei.esoft.project.domain.locations.State;
import pt.ipp.isep.dei.esoft.project.domain.locations.mappers.dto.StateDTO;
import pt.ipp.isep.dei.esoft.project.util.error.ErrorOptional;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

class StateMapperTest {

    private State state;
    private StateDTO stateDTO;
    private final StateMapper mapper = new StateMapper();

    @BeforeEach
    void beforeEach() {
        // -----Domain-----
        state = new State();
        state.setName("State");

        District district = new District();
        district.setName("District");

        City city = new City();
        city.setName("City");

        List<City> cityList = new ArrayList<>();
        cityList.add(city);
        district.setCities(cityList);

        List<District> districtList = new ArrayList<>();
        districtList.add(district);
        state.setDistricts(districtList);

        // -----DTO-----
        stateDTO = new StateDTO("State", new String[]{"District"}, new String[][]{{"City"}});
    }

    @Test
    void toDTO() {
        // valid
        ErrorOptional<StateDTO> dto = mapper.toDTO(state);
        assertFalse(dto.hasError());
        // all atributes must be equal
        assertEquals(state.getName(), dto.get().name);
        assertEquals(state.getDistricts().size(), dto.get().districtList.size());

        // invalid - null
        dto = mapper.toDTO(null);
        assert (dto.hasError());
    }

    @Test
    void toDomain() {
        // valid
        ErrorOptional<State> domain = mapper.toDomain(stateDTO);
        assertFalse(domain.hasError());
        // all attributes must be equal
        assertEquals(stateDTO.name, domain.get().getName());
        assertEquals(stateDTO.districtList.size(), domain.get().getDistricts().size());

        // invalid null
        domain = mapper.toDomain(null);
        assert (domain.hasError());
    }
}