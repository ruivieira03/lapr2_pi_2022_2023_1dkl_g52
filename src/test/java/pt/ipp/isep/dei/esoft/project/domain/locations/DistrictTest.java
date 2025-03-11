package pt.ipp.isep.dei.esoft.project.domain.locations;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DistrictTest {

    private District district;

    @BeforeEach
    void beforeEach() {
        district = new District();
        district.setName("District");
    }

    @Test
    void testGetCities() {
        assert (district.getCities().size() == 0);
    }

    @Test
    void setCities() {
        List<City> cityList = new ArrayList<>();
        cityList.add(new City());

        district.setCities(cityList);
        assert (district.getCities().size() == 1);
    }

    @Test
    void testIsValid() {
        // invalid
        assertFalse(district.isValid().success());

        // valid
        List<City> cityList = new ArrayList<>();
        City city = new City();
        city.setName("City");
        cityList.add(city);

        district.setCities(cityList);
        assert (district.isValid().success());
    }
}