package pt.ipp.isep.dei.esoft.project.domain.locations;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class LocationTest {

    private City city;

    @BeforeEach
    void beforeEach() {
        city = new City();
        city.setName("City");
    }
    @Test
    void getName() {
        String expected = "City";
        assertEquals(city.getName(), expected);
    }

    @Test
    void testSetName() {
        String expected = "City1";

        city.setName(expected);
        assertEquals(city.getName(), expected);
    }

    @Test
    void testIsValid() {
        // valid
        assert (city.isValid().success());

        // invalid
        city.setName("__!|!!!*");
        assertFalse(city.isValid().success());
    }

    @Test
    void testEquals() {
        // valid - same reference
        assert (city.equals(city));

        // valid - same atrributes
        City city1 = new City();
        city1.setName("City");

        assert (city.equals(city1));

        // invalid - null
        assertFalse(city.equals(null));
    }

    @Test
    void testToString() {
        String expected = "City";

        assertEquals(city.toString(), expected);
    }

    @Test
    void compareTo() {
        // bigger
        City bigger = new City();
        bigger.setName("A");

        assert (city.compareTo(bigger) > 0);


        // lower
        City lower = new City();
        lower.setName("Z");

        assert (city.compareTo(lower) < 0);
    }
}