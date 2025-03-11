package pt.ipp.isep.dei.esoft.project.domain.locations;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class StateTest {

    private State state;

    @BeforeEach
    void beforeEach() {
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
    }

    @Test
    void testGetDistricts() {
        assert (state.getDistricts().size() == 1);
    }

    @Test
    void setDistricts() {
        state.setDistricts(new ArrayList<>());
        assert (state.getDistricts().size() == 0);
    }

    @Test
    void testIsValid() {
        // valid
        assert (state.isValid().success());

        // invalid - name
        state.setName("!!!");
        assertFalse(state.isValid().success());
    }
}