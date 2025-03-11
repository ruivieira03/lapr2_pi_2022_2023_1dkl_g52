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
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

class PropertySaleTest {

    private PropertySale propertySale;

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
        propertySale = new PropertySale();
        propertySale.setProperty(land);
        propertySale.setTypeOfBusiness(PropertySaleRequest.TypesOfBusinesses.RENT);
        propertySale.setAgent(agent);
        propertySale.setDateOfRequest(LocalDate.now());
        propertySale.setRequestedPrice(100_000);
        propertySale.setTypeOfCommission(PropertySale.CommissionType.PERCENTAGE);
        propertySale.setCommission(0.1);
    }

    @Test
    void getTypeOfCommission() {
        PropertySale.CommissionType expected = PropertySale.CommissionType.PERCENTAGE;
        assertEquals(expected, propertySale.getTypeOfCommission());
    }

    @Test
    void getCommission() {
        double expected = 0.1;
        assertEquals(expected, propertySale.getCommission());
    }

    @Test
    void getPrice() {
        double expected = 0;
        assertEquals(expected, propertySale.getPrice());
    }


    @Test
    void setTypeOfCommission() {
        PropertySale.CommissionType commissionType = PropertySale.CommissionType.FIXED;
        propertySale.setTypeOfCommission(commissionType);
        assertEquals(commissionType, propertySale.getTypeOfCommission());
    }

    @Test
    void setPrice() {
        double price = 110_000;
        propertySale.setPrice(price);
        assertEquals(price, propertySale.getPrice());
    }

    @Test
    void setCommission() {
        double commission = 0.05;
        propertySale.setCommission(commission);
        assertEquals(commission, propertySale.getCommission());
    }

    @Test
    void testEquals() {
        // valid - same reference
        assert (propertySale.equals(propertySale));

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
        PropertySale propertySale1 = new PropertySale();
        propertySale1.setProperty(land);
        propertySale1.setTypeOfBusiness(PropertySaleRequest.TypesOfBusinesses.RENT);
        propertySale1.setAgent(agent);
        propertySale1.setDateOfRequest(LocalDate.now());
        propertySale1.setRequestedPrice(100_000);
        propertySale1.setTypeOfCommission(PropertySale.CommissionType.PERCENTAGE);
        propertySale1.setCommission(0.1);

        assert(propertySale.equals(propertySale1));

        // invalid - null
        assertFalse(propertySale.equals(null));
    }

    @Test
    void calculatePrice() {
        double expected = 110_000;
        assertEquals(expected, propertySale.calculatePrice());
    }

    @Test
    void isValid() {
        // valid
        assert (propertySale.isValid().success());

        // invalid - Comission type
        propertySale.setTypeOfCommission(null);
        assertFalse(propertySale.isValid().success());
        propertySale.setTypeOfCommission(PropertySale.CommissionType.PERCENTAGE);

        // invalid - commission
        propertySale.setCommission(-1);
        assertFalse(propertySale.isValid().success());
    }

    @Test
    void sortByPriceComparator() {
        PropertySale propertySale1 = new PropertySale();
        propertySale1.setRequestedPrice(200_000);
        propertySale1.setTypeOfCommission(PropertySale.CommissionType.FIXED);
        propertySale1.setCommission(10_000);

        PropertySale.SortByPriceComparator comparator = new PropertySale.SortByPriceComparator();
        assert (comparator.compare(propertySale, propertySale1) < 0);
    }

    @Test
    void sortByDateComparator() {
        PropertySale propertySale1 = new PropertySale();
        propertySale1.setDateOfRequest(LocalDate.of(1900, 1, 1));


        PropertySale.SortByDateComparator comparator = new PropertySale.SortByDateComparator();
        assert (comparator.compare(propertySale, propertySale1) > 0);
    }
}