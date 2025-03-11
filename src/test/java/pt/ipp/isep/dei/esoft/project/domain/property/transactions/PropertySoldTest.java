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

import static org.junit.jupiter.api.Assertions.*;

class PropertySoldTest {

    private PropertySold propertySold;

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
        propertySold = new PropertySold();
        propertySold.setProperty(land);
        propertySold.setTypeOfBusiness(PropertySaleRequest.TypesOfBusinesses.RENT);
        propertySold.setAgent(agent);
        propertySold.setDateOfRequest(LocalDate.now());
        propertySold.setRequestedPrice(100_000);
        propertySold.setTypeOfCommission(PropertySale.CommissionType.PERCENTAGE);
        propertySold.setCommission(0.1);
        propertySold.setBuyer(client);
        propertySold.setPaidAmount(99_000);
        propertySold.setPurchaseDate(LocalDateTime.now());
    }

    @Test
    void getPaidAmount() {
        double expected = 99_000;
        assertEquals(expected, propertySold.getPaidAmount());
    }

    @Test
    void setPaidAmount() {
        double paidAmount = 10_000;
        propertySold.setPaidAmount(paidAmount);
        assertEquals(paidAmount, propertySold.getPaidAmount());
    }

    @Test
    void getBuyer() {
        String expected = "email@ex.com";
        assertEquals(expected, propertySold.getBuyer().getEmail());
    }

    @Test
    void getPurchaseDate() {
        assert (propertySold.getPurchaseDate() instanceof LocalDateTime);
    }

    @Test
    void setPurchaseDate() {
        LocalDateTime purchaceDate = LocalDateTime.of(10, 10, 10, 10, 10);
        propertySold.setPurchaseDate(purchaceDate);
        assertEquals(purchaceDate, propertySold.getPurchaseDate());
    }

    @Test
    void isValid() {
        // valid
        assert (propertySold.isValid().success());
    }

    @Test
    void testEquals() {
        // valid - same reference
        assert (propertySold.equals(propertySold));

        // invalid - null
        assertFalse(propertySold.equals(null));
    }
}