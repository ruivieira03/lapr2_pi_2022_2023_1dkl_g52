package pt.ipp.isep.dei.esoft.project.domain.locations;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CityTest {

    private City city;

    @BeforeEach
    void beforeEach() {
        city = new City();
        city.setName("City");
    }

    @Test
    void test() {
        assertEquals(city.toString(), city.getName());
    }

}