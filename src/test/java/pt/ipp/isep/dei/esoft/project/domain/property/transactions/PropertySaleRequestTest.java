package pt.ipp.isep.dei.esoft.project.domain.property.transactions;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pt.ipp.isep.dei.esoft.project.domain.client.Client;
import pt.ipp.isep.dei.esoft.project.domain.employee.Agent;
import pt.ipp.isep.dei.esoft.project.domain.locations.City;
import pt.ipp.isep.dei.esoft.project.domain.locations.District;
import pt.ipp.isep.dei.esoft.project.domain.locations.State;
import pt.ipp.isep.dei.esoft.project.domain.property.Apartment;
import pt.ipp.isep.dei.esoft.project.domain.property.Land;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PropertySaleRequestTest {

    private PropertySaleRequest propertySaleRequest;

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
        Land land = new Land();
        land.setArea(1000);
        land.setDistanceFromCityCentre(1.0);
        land.setZipCode(12345);
        land.setStreet("Street");
        land.setState(state);
        land.setDistrict(district);
        land.setCity(city);
        land.setClient(client);
        land.setPhotos("p");

        // agent
        Agent agent = new Agent();
        agent.setName("John");
        agent.setAddress("Adress");
        agent.setEmail("agent@ex.com");
        agent.setTaxNumber("123-12-1234");
        agent.setPhoneNumber("123-123-1234");
        agent.setPassportNumber(123456789);

        // propertySaleRequest
        propertySaleRequest = new PropertySaleRequest();
        propertySaleRequest.setProperty(land);
        propertySaleRequest.setTypeOfBusiness(PropertySaleRequest.TypesOfBusinesses.RENT);
        propertySaleRequest.setAgent(agent);
        propertySaleRequest.setDateOfRequest(LocalDate.now());
        propertySaleRequest.setRequestedPrice(100_000);
    }

    @Test
    void getID() {
        assert (propertySaleRequest.getID() > 0);
    }

    @Test
    void getRequestedPrice() {
        int expected = 100_000;
        assertEquals(expected, propertySaleRequest.getRequestedPrice());
    }

    @Test
    void getTypeOfBusiness() {
        PropertySaleRequest.TypesOfBusinesses expected = PropertySaleRequest.TypesOfBusinesses.RENT;
        assertEquals(expected, propertySaleRequest.getTypeOfBusiness());
    }

    @Test
    void getProperty() {
        assert (propertySaleRequest.getProperty() instanceof Land);
    }

    @Test
    void getAgent() {
        String expected = "agent@ex.com";
        assertEquals(expected, propertySaleRequest.getAgent().getEmail().toString());
    }

    @Test
    void getDateOfRequest() {
        assert (propertySaleRequest.getDateOfRequest() instanceof LocalDate);
    }

    @Test
    void setProperty() {
        propertySaleRequest.setProperty(new Apartment());
        assert (propertySaleRequest.getProperty() instanceof Apartment);
    }

    @Test
    void setRequestedPrice() {
        int requestedPrice = 200_000;
        propertySaleRequest.setRequestedPrice(requestedPrice);
        assertEquals(requestedPrice, propertySaleRequest.getRequestedPrice());
    }

    @Test
    void setTypeOfBusiness() {
        PropertySaleRequest.TypesOfBusinesses typesOfBusinesses = PropertySaleRequest.TypesOfBusinesses.SELL;
        propertySaleRequest.setTypeOfBusiness(typesOfBusinesses);
        assertEquals(typesOfBusinesses, propertySaleRequest.getTypeOfBusiness());
    }

    @Test
    void setAgent() {
        Agent agent = new Agent();
        agent.setName("AbstractQwerty");
        agent.setAddress("Adress");
        agent.setEmail("abstractAgent@ex.com");
        agent.setTaxNumber("123-12-1234");
        agent.setPhoneNumber("123-123-1234");
        agent.setPassportNumber(123456789);

        propertySaleRequest.setAgent(agent);
        assertEquals(agent, propertySaleRequest.getAgent());
    }

    @Test
    void setDateOfRequest() {
        LocalDate dateOfRequest = LocalDate.of(1999, 10, 20);
        propertySaleRequest.setDateOfRequest(dateOfRequest);
        assertEquals(dateOfRequest, propertySaleRequest.getDateOfRequest());
    }

    @Test
    void testEquals() {
        // valid - same reference
        assert (propertySaleRequest.equals(propertySaleRequest));

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
        Land land = new Land();
        land.setArea(1000);
        land.setDistanceFromCityCentre(1.0);
        land.setZipCode(12345);
        land.setStreet("Street");
        land.setState(state);
        land.setDistrict(district);
        land.setCity(city);
        land.setClient(client);
        land.setPhotos("p");

        // agent
        Agent agent = new Agent();
        agent.setName("John");
        agent.setAddress("Adress");
        agent.setEmail("agent@ex.com");
        agent.setTaxNumber("123-12-1234");
        agent.setPhoneNumber("123-123-1234");
        agent.setPassportNumber(123456789);

        // propertySaleRequest1
        PropertySaleRequest propertySaleRequest1 = new PropertySaleRequest();
        propertySaleRequest1.setProperty(land);
        propertySaleRequest1.setTypeOfBusiness(PropertySaleRequest.TypesOfBusinesses.RENT);
        propertySaleRequest1.setAgent(agent);
        propertySaleRequest1.setDateOfRequest(LocalDate.now());
        propertySaleRequest1.setRequestedPrice(100_000);

        assert (propertySaleRequest.equals(propertySaleRequest1));

        // invalid - null
        assertFalse(propertySaleRequest.equals(null));
    }

    @Test
    void isValid() {
        // valid
        assert (propertySaleRequest.isValid().success());

        // invalid - typeOfbusiness
        propertySaleRequest.setTypeOfBusiness(null);
        assertFalse(propertySaleRequest.isValid().success());
        propertySaleRequest.setTypeOfBusiness(PropertySaleRequest.TypesOfBusinesses.RENT);

        // invalid - requested price
        propertySaleRequest.setRequestedPrice(-100);
        assertFalse(propertySaleRequest.isValid().success());
        propertySaleRequest.setRequestedPrice(100_000);
    }

    @Test
    void testEnum() {
        // valid
        assertDoesNotThrow(() -> {
            PropertySaleRequest.TypesOfBusinesses.fromLabel(PropertySaleRequest.TypesOfBusinesses.RENT.getLabel());
        });

        // invalid
        assertThrowsExactly(IllegalArgumentException.class, () -> {
            int i = 1;
            System.out.println(i);
            PropertySaleRequest.TypesOfBusinesses.fromLabel("INVALIDLABEL");
        });
    }

    @Test
    void testComparator() {
        // cities
        City city1 = new City();
        city1.setName("A");
        City city2 = new City();
        city2.setName("z");

        // properties sale requests
        PropertySaleRequest propertySaleRequest1 = new PropertySaleRequest();
        propertySaleRequest1.setProperty(new Land());
        propertySaleRequest1.getProperty().setCity(city1);

        PropertySaleRequest propertySaleRequest2 = new PropertySaleRequest();
        propertySaleRequest2.setProperty(new Land());
        propertySaleRequest2.getProperty().setCity(city2);

        // lists
        List<PropertySaleRequest> unorderedList = new ArrayList<>();
        unorderedList.add(propertySaleRequest1);
        unorderedList.add(propertySaleRequest2);
        unorderedList.add(propertySaleRequest);


        List<PropertySaleRequest> orderedList = new ArrayList<>();
        orderedList.add(propertySaleRequest1);
        orderedList.add(propertySaleRequest);
        orderedList.add(propertySaleRequest2);

        // sort
        PropertySaleRequest.SortByCityComparator sortByCityComparator = new PropertySaleRequest.SortByCityComparator();
        unorderedList.sort(sortByCityComparator);

        assertEquals(orderedList, unorderedList);
    }
}