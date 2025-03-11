package pt.ipp.isep.dei.esoft.project.domain.property;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pt.ipp.isep.dei.esoft.project.domain.client.Client;
import pt.ipp.isep.dei.esoft.project.domain.locations.City;
import pt.ipp.isep.dei.esoft.project.domain.locations.District;
import pt.ipp.isep.dei.esoft.project.domain.locations.State;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PropertyTest {

    private Land land;

    @BeforeEach
    void beforeEach() {
        // client
        Client client = new Client();
        client.setName("John");
        client.setPassportNumber(123456789);
        client.setPhoneNumber("123-123-1234");
        client.setTaxNumber("123-12-1234");
        client.setAddress("Adress");
        client.setEmail("email@ex.com");

        // city
        City city = new City();
        city.setName("City");

        // district
        District district = new District();
        district.setName("District");
        List<City>cityList = new ArrayList<>();
        cityList.add(city);
        district.setCities(cityList);

        // state
        State state = new State();
        state.setName("State");
        List<District> districtList = new ArrayList<>();
        districtList.add(district);
        state.setDistricts(districtList);

        // property
        land = new Land();
        land.setArea(1000);
        land.setDistanceFromCityCentre(1.0);
        land.setZipCode(12345);
        land.setStreet("Street");
        land.setState(state);
        land.setDistrict(district);
        land.setCity(city);
        land.setClient(client);
        land.setPhotos("p");
    }

    @Test
    void getArea() {
        double expected = 1000;
        assertEquals(expected, land.getArea());
    }

    @Test
    void getStreet() {
        String expected = "Street";
        assertEquals(expected, land.getStreet());
    }

    @Test
    void getDistanceFromCityCentre() {
        double expected = 1.0;
        assertEquals(expected, land.getDistanceFromCityCentre());
    }

    @Test
    void getZipCode() {
        int expected = 12345;
        assertEquals(expected, land.getZipCode());
    }

    @Test
    void getCity() {
        String expected = "City";
        assertEquals(expected, land.getCity().getName());
    }

    @Test
    void getDistrict() {
        String expected = "District";
        assertEquals(expected, land.getDistrict().getName());
    }

    @Test
    void getState() {
        String expected = "State";
        assertEquals(expected, land.getState().getName());
    }

    @Test
    void getPhotos() {
        String expected = "p";
        assertEquals(expected, land.getPhotos());
    }

    @Test
    void getClient() {
        String expected = "John";
        assertEquals(expected, land.getClient().getName());
    }

    @Test
    void setArea() {
        double area = 3000;
        land.setArea(area);
        assertEquals(area, land.getArea());
    }

    @Test
    void setDistanceFromCityCentre() {
        double distance = 4.6;
        land.setDistanceFromCityCentre(distance);
        assertEquals(distance, land.getDistanceFromCityCentre());
    }

    @Test
    void setStreet() {
        String street = "Another boring street";
        land.setStreet(street);
        assertEquals(street, land.getStreet());
    }

    @Test
    void setZipCode() {
        int zipCode = 39026;
        land.setZipCode(zipCode);
        assertEquals(zipCode, land.getZipCode());
    }

    @Test
    void setCity() {
        City city = new City();
        city.setName("Big city");
        land.setCity(city);
        assertEquals(city.getName(), land.getCity().getName());
    }

    @Test
    void setDistrict() {
        District district = new District();
        district.setName("Big district");
        land.setDistrict(district);
        assertEquals(district.getName(), land.getDistrict().getName());
    }

    @Test
    void setState() {
        State state = new State();
        state.setName("Big State");
        land.setState(state);
        assertEquals(state.getName(), land.getState().getName());
    }

    @Test
    void setPhotos() {
        String photos = "Lots of photos, even one that has a dog(not included with the peoperty)";
        land.setPhotos(photos);
        assertEquals(photos, land.getPhotos());
    }

    @Test
    void setClient() {
        Client client = new Client();
        client.setName("name");
        land.setClient(client);
        assertEquals(client.getName(), land.getClient().getName());
    }

    @Test
    void testEquals() {
        // valid - same referece
        assert (land.equals(land));

        // valid - same atributes
        // client
        Client client = new Client();
        client.setName("John");
        client.setPassportNumber(123456789);
        client.setPhoneNumber("123-123-1234");
        client.setTaxNumber("123-12-1234");
        client.setAddress("Adress");
        client.setEmail("email@ex.com");

        // city
        City city = new City();
        city.setName("City");

        // district
        District district = new District();
        district.setName("District");
        List<City>cityList = new ArrayList<>();
        cityList.add(city);
        district.setCities(cityList);

        // state
        State state = new State();
        state.setName("State");
        List<District> districtList = new ArrayList<>();
        districtList.add(district);
        state.setDistricts(districtList);

        // property
        Land land1 = new Land();
        land1.setArea(1000);
        land1.setDistanceFromCityCentre(1.0);
        land1.setZipCode(12345);
        land1.setStreet("Street");
        land1.setState(state);
        land1.setDistrict(district);
        land1.setCity(city);
        land1.setClient(client);
        land1.setPhotos("p");
        assert (land.equals(land1));

        // invalid - null
        assertFalse(land.equals(null));

    }

    @Test
    void isValid() {
        // valid
        assert (land.isValid().success());

        // distance from city center invalid
        land.setDistanceFromCityCentre(-1.0);
        assertFalse(land.isValid().success());

    }

    @Test
    void isZipCodeValid() {
        int validZipCode = 12345;
        int invalidZipCode = 123456;

        assert (land.isZipCodeValid(validZipCode).success());
        assertFalse(land.isZipCodeValid(invalidZipCode).success());
    }

    @Test
    void testEnum() {
        Property.Type propertyType = Property.Type.HOUSE;

        // test - getLabel()
        String expected = "House";
        assertEquals(expected, propertyType.getLabel());

        // test - valid - fromLabel()
        assertEquals(propertyType, Property.Type.fromLabel(expected));

        // test - invalid fromLabel
        String invalidLabel = "INVALID label";
        assertThrowsExactly(IllegalArgumentException.class, () -> {
            Property.Type.fromLabel(invalidLabel);
        });

    }
}