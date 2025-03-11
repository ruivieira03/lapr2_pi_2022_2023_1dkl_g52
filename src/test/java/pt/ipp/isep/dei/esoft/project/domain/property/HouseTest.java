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

class HouseTest {
    private House house;

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
        List<City> cityList = new ArrayList<>();
        cityList.add(city);
        district.setCities(cityList);

        // state
        State state = new State();
        state.setName("State");
        List<District> districtList = new ArrayList<>();
        districtList.add(district);
        state.setDistricts(districtList);

        // property
        house = new House();
        house.setArea(1000);
        house.setDistanceFromCityCentre(1.0);
        house.setZipCode(12345);
        house.setStreet("Street");
        house.setState(state);
        house.setDistrict(district);
        house.setCity(city);
        house.setClient(client);
        house.setPhotos("p");
        house.setNumberOfBathrooms(1);
        house.setNumberOfBedrooms(2);
        house.setNumberOfParkingSpaces(3);
        house.setAvailableEquipment("Available Equipment");
        house.setSunExposure(House.SunExposure.WEST);
        house.setExistanceOfBasement(false);
        house.setExistanceOfInhabitableLoft(true);
    }

    @Test
    void getSunExposure() {
        House.SunExposure expected = House.SunExposure.WEST;
        assertEquals(expected, house.getSunExposure());
    }

    @Test
    void getExistanceOfBasement() {
        boolean expected = false;
        assertEquals(expected, house.getExistanceOfBasement());
    }

    @Test
    void getExistanceOfInhabitableLoft() {
        boolean expected = true;
        assertEquals(expected, house.getExistanceOfInhabitableLoft());
    }

    @Test
    void setExistanceOfBasement() {
        boolean existanceOfBasement = true;
        house.setExistanceOfBasement(existanceOfBasement);
        assertEquals(existanceOfBasement, house.getExistanceOfBasement());
    }

    @Test
    void setExistanceOfInhabitableLoft() {
        boolean existanceOfInhabitableLoft = false;
        house.setExistanceOfInhabitableLoft(existanceOfInhabitableLoft);
        assertEquals(existanceOfInhabitableLoft, house.getExistanceOfInhabitableLoft());
    }

    @Test
    void setSunExposure() {
        House.SunExposure sunExposure = House.SunExposure.NORTH;
        house.setSunExposure(sunExposure);
        assertEquals(sunExposure, house.getSunExposure());
    }

    @Test
    void testEquals() {
        // valid - same reference
        assert (house.equals(house));

        // valid - same attributes
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
        List<City> cityList = new ArrayList<>();
        cityList.add(city);
        district.setCities(cityList);

        // state
        State state = new State();
        state.setName("State");
        List<District> districtList = new ArrayList<>();
        districtList.add(district);
        state.setDistricts(districtList);

        // property
        House house1 = new House();
        house1.setArea(1000);
        house1.setDistanceFromCityCentre(1.0);
        house1.setZipCode(12345);
        house1.setStreet("Street");
        house1.setState(state);
        house1.setDistrict(district);
        house1.setCity(city);
        house1.setClient(client);
        house1.setPhotos("p");
        house1.setNumberOfBathrooms(1);
        house1.setNumberOfBedrooms(2);
        house1.setNumberOfParkingSpaces(3);
        house1.setAvailableEquipment("Available Equipment");
        house1.setSunExposure(House.SunExposure.WEST);
        house1.setExistanceOfBasement(false);
        house1.setExistanceOfInhabitableLoft(true);

        assert (house.equals(house1));

        // invalid - null
        assertFalse(house.equals(null));
    }

    @Test
    void testEnum() {
        // test getAbreviation
        House.SunExposure sunExposure = House.SunExposure.NORTH;
        String sunExposureAbreviation = "N";
        assertEquals(sunExposureAbreviation, sunExposure.getAbbreviation());


        // test from label
        assertEquals(sunExposure, House.SunExposure.fromAbbreviation(sunExposureAbreviation));

        // test - invalid from label
        String sunExposureAbreviationInvalid = "invalid Abreviation";
        assertThrowsExactly(IllegalArgumentException.class, () -> {
            House.SunExposure.fromAbbreviation(sunExposureAbreviationInvalid);
        });
    }

}