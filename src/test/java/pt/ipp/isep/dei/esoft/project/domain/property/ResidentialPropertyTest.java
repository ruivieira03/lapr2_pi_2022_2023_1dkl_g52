package pt.ipp.isep.dei.esoft.project.domain.property;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pt.ipp.isep.dei.esoft.project.domain.client.Client;
import pt.ipp.isep.dei.esoft.project.domain.locations.City;
import pt.ipp.isep.dei.esoft.project.domain.locations.District;
import pt.ipp.isep.dei.esoft.project.domain.locations.State;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

class ResidentialPropertyTest {

    private Apartment apartment;

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
        apartment = new Apartment();
        apartment.setArea(1000);
        apartment.setDistanceFromCityCentre(1.0);
        apartment.setZipCode(12345);
        apartment.setStreet("Street");
        apartment.setState(state);
        apartment.setDistrict(district);
        apartment.setCity(city);
        apartment.setClient(client);
        apartment.setPhotos("p");
        apartment.setNumberOfBathrooms(1);
        apartment.setNumberOfBedrooms(2);
        apartment.setNumberOfParkingSpaces(3);
        apartment.setAvailableEquipment("Available Equipment");
    }

    @Test
    void getNumberOfBathrooms() {
        int expected = 1;
        assertEquals(expected, apartment.getNumberOfBathrooms());
    }

    @Test
    void getNumberOfBedrooms() {
        int expected = 2;
        assertEquals(expected, apartment.getNumberOfBedrooms());
    }

    @Test
    void getNumberOfParkingSpaces() {
        int expected = 3;
        assertEquals(expected, apartment.getNumberOfParkingSpaces());
    }

    @Test
    void getAvailableEquipment() {
        String expected = "Available Equipment";
        assertEquals(expected, apartment.getAvailableEquipment());
    }

    @Test
    void setNumberOfBathrooms() {
        int numberOfBathrooms = 12;
        apartment.setNumberOfBathrooms(numberOfBathrooms);
        assertEquals(numberOfBathrooms, apartment.getNumberOfBathrooms());
    }

    @Test
    void setNumberOfBedrooms() {
        int numberOfBedrooms = 24;
        apartment.setNumberOfBedrooms(numberOfBedrooms);
        assertEquals(numberOfBedrooms, apartment.getNumberOfBedrooms());
    }

    @Test
    void setNumberOfParkingSpaces() {
        int numberOfParkingSpaces = 30;
        apartment.setNumberOfParkingSpaces(numberOfParkingSpaces);
        assertEquals(numberOfParkingSpaces, apartment.getNumberOfParkingSpaces());
    }

    @Test
    void setAvailableEquipment() {
        String availableEquipment = "No available u buy yourself some blankets";
        apartment.setAvailableEquipment(availableEquipment);
        assertEquals(availableEquipment, apartment.getAvailableEquipment());
    }

    @Test
    void testEquals() {
        // valid - same reference
        assert (apartment.equals(apartment));

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
        Apartment apartment1 = new Apartment();
        apartment1.setArea(1000);
        apartment1.setDistanceFromCityCentre(1.0);
        apartment1.setZipCode(12345);
        apartment1.setStreet("Street");
        apartment1.setState(state);
        apartment1.setDistrict(district);
        apartment1.setCity(city);
        apartment1.setClient(client);
        apartment1.setPhotos("p");
        apartment1.setNumberOfBathrooms(1);
        apartment1.setNumberOfBedrooms(2);
        apartment1.setNumberOfParkingSpaces(3);
        apartment1.setAvailableEquipment("Available Equipment");

        assert (apartment.equals(apartment1));

        // invalid - null
        assertFalse(apartment.equals(null));
    }

    @Test
    void checkEquipment() {
        // have both
        String[] string = new String[]{"Y", "Y"};
        String expected = "Central Heating, Air Conditioner, ";

        assertEquals(expected, ResidentialProperty.checkEquipment(string));

        // NA—Y
        string = new String[]{"NA", "Y"};
        expected = "NA, Air Conditioner, ";

        assertEquals(expected, ResidentialProperty.checkEquipment(string));

        // N - Y
        string = new String[]{"N", "Y"};
        expected = "No Central Heating, Air Conditioner, ";

        assertEquals(expected, ResidentialProperty.checkEquipment(string));

        // Y - NA
        string = new String[]{"Y", "NA"};
        expected = "Central Heating, NA, ";

        assertEquals(expected, ResidentialProperty.checkEquipment(string));

        // Y - N
        string = new String[]{"Y", "N"};
        expected = "Central Heating, No Air Conditioner, ";

        assertEquals(expected, ResidentialProperty.checkEquipment(string));
    }
}