package pt.ipp.isep.dei.esoft.project.domain.property;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pt.ipp.isep.dei.esoft.project.domain.client.Client;
import pt.ipp.isep.dei.esoft.project.domain.locations.City;
import pt.ipp.isep.dei.esoft.project.domain.locations.District;
import pt.ipp.isep.dei.esoft.project.domain.locations.State;

import java.util.ArrayList;
import java.util.List;

class ApartmentTest {

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
    void test() {
        assert (apartment instanceof Apartment);
    }
}