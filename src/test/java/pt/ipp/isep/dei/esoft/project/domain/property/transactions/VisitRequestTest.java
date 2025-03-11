package pt.ipp.isep.dei.esoft.project.domain.property.transactions;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pt.ipp.isep.dei.esoft.project.domain.client.Client;
import pt.ipp.isep.dei.esoft.project.domain.employee.Agent;
import pt.ipp.isep.dei.esoft.project.domain.locations.City;
import pt.ipp.isep.dei.esoft.project.domain.locations.District;
import pt.ipp.isep.dei.esoft.project.domain.locations.State;
import pt.ipp.isep.dei.esoft.project.domain.property.Land;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

class VisitRequestTest {

    private VisitRequest visitRequest;

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
        PropertySale propertySale = new PropertySale();
        propertySale.setProperty(land);
        propertySale.setTypeOfBusiness(PropertySaleRequest.TypesOfBusinesses.RENT);
        propertySale.setAgent(agent);
        propertySale.setDateOfRequest(LocalDate.now());
        propertySale.setRequestedPrice(100_000);
        propertySale.setTypeOfCommission(PropertySale.CommissionType.PERCENTAGE);
        propertySale.setCommission(0.1);

        visitRequest = new VisitRequest();
        visitRequest.setPropertySale(propertySale);
        visitRequest.setClient(client);
        visitRequest.setVisitStart(LocalDateTime.of(10, 10, 10, 10, 10));
        visitRequest.setVisitEnd(LocalDateTime.of(10, 10, 10, 11, 11));
    }

    @Test
    void getId() {
        assert (visitRequest.getId() > 0);
    }

    @Test
    void getClient() {
        String expected = "email@ex.com";
        assertEquals(expected, visitRequest.getClient().getEmail());
    }

    @Test
    void setClient() {
        visitRequest.setClient(null);
        assertEquals(null, visitRequest.getClient());
    }

    @Test
    void getPropertySale() {
        double expected = 100_000;
        assertEquals(expected, visitRequest.getPropertySale().getRequestedPrice());
    }

    @Test
    void setPropertySale() {
        visitRequest.setPropertySale(null);
        assertEquals(null, visitRequest.getPropertySale());
    }

    @Test
    void getVisitStart() {
        assert(visitRequest.getVisitStart() instanceof LocalDateTime);
    }

    @Test
    void setVisitStart() {
        LocalDateTime visitStart = LocalDateTime.of(22, 12, 22, 22, 22);
        visitRequest.setVisitStart(visitStart);
        assertEquals(visitStart, visitRequest.getVisitStart());
    }

    @Test
    void getVisitEnd() {
        assert(visitRequest.getVisitEnd() instanceof LocalDateTime);
    }

    @Test
    void setVisitEnd() {
        LocalDateTime visitEnd = LocalDateTime.of(22, 12, 22, 22, 22);
        visitRequest.setVisitEnd(visitEnd);
        assertEquals(visitEnd, visitRequest.getVisitEnd());
    }

    @Test
    void overlaps() {
        VisitRequest visitRequest1 = new VisitRequest();
        visitRequest1.setVisitStart(LocalDateTime.now());
        visitRequest1.setVisitEnd(LocalDateTime.now());

        assertFalse(visitRequest.overlaps(visitRequest1));
    }

    @Test
    void isValid() {
        // valid
        assert (visitRequest.isValid().success());

        // invalid - Start after end
        visitRequest.setVisitStart(LocalDateTime.now());
        assertFalse(visitRequest.isValid().success());

        // invalid - PropertySale null
        visitRequest.setPropertySale(null);
        assertFalse(visitRequest.isValid().success());

        // invalid - client null
        visitRequest.setClient(null);
        assertFalse(visitRequest.isValid().success());
    }

    @Test
    void testEquals() {
        // valid - same reference
        assert (visitRequest.equals(visitRequest));


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

        // propertySaleRequest
        PropertySale propertySale = new PropertySale();
        propertySale.setProperty(land);
        propertySale.setTypeOfBusiness(PropertySaleRequest.TypesOfBusinesses.RENT);
        propertySale.setAgent(agent);
        propertySale.setDateOfRequest(LocalDate.now());
        propertySale.setRequestedPrice(100_000);
        propertySale.setTypeOfCommission(PropertySale.CommissionType.PERCENTAGE);
        propertySale.setCommission(0.1);

        VisitRequest visitRequest1 = new VisitRequest();
        visitRequest1.setPropertySale(propertySale);
        visitRequest1.setClient(client);
        visitRequest1.setVisitStart(LocalDateTime.of(10, 10, 10, 10, 10));
        visitRequest1.setVisitEnd(LocalDateTime.of(10, 10, 10, 11, 11));

        assert (visitRequest.equals(visitRequest1));


        // invalid - null
        assertFalse(visitRequest.equals(null));
    }
}