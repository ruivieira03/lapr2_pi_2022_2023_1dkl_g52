package pt.ipp.isep.dei.esoft.project.domain.property.transactions;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pt.ipp.isep.dei.esoft.project.domain.client.Client;
import pt.ipp.isep.dei.esoft.project.domain.employee.Agent;
import pt.ipp.isep.dei.esoft.project.domain.locations.City;
import pt.ipp.isep.dei.esoft.project.domain.locations.District;
import pt.ipp.isep.dei.esoft.project.domain.locations.State;
import pt.ipp.isep.dei.esoft.project.domain.property.Land;
import pt.ipp.isep.dei.esoft.project.domain.property.transactions.mappers.PurchaseRequestMapper;
import pt.ipp.isep.dei.esoft.project.repository.PurchaseRequestRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static pt.ipp.isep.dei.esoft.project.repository.Repositories.getPurchaseRequestRepository;

class PurchaseRequestTest {
    private Client owner;
    private  Client client;
    private PropertySale propertySale;
    private PropertySale propertySale2;
    private PurchaseRequest purchaseRequest;
    private PurchaseRequest purchaseRequest2;
    private PurchaseRequest purchaseRequest3;
    private PurchaseRequestRepository purchaseRepository = getPurchaseRequestRepository();
    private PurchaseRequestMapper mapper = new PurchaseRequestMapper();
    @BeforeEach
    void beforeEach() {
        // owner
        owner = new Client();
        owner.setName("John");
        owner.setPassportNumber(123456789);
        owner.setPhoneNumber("123-123-1234");
        owner.setTaxNumber("123-12-1234");
        owner.setAddress("Address");
        owner.setEmail("email@ex.com");

        // owner
        client = new Client();
        client.setName("David");
        client.setPassportNumber(123456789);
        client.setPhoneNumber("123-123-1234");
        client.setTaxNumber("123-12-1234");
        client.setAddress("Address");
        client.setEmail("email2@ex.com");

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
        land.setClient(owner);
        land.setPhotos("p");

        // agent
        Agent agent = new Agent();
        agent.setName("Dave");
        agent.setPassportNumber(123456789);
        agent.setPhoneNumber("123-113-1234");
        agent.setTaxNumber("123-12-1254");
        agent.setAddress("Address");
        agent.setEmail("agent@ex.com");

        //property sale
        propertySale = new PropertySale();
        propertySale.setCommission(20);
        propertySale.setPrice(100_000_000);
        propertySale.setProperty(land);
        propertySale.setAgent(agent);
        propertySale.setDateOfRequest(LocalDate.now());
        propertySale.setTypeOfBusiness(PropertySaleRequest.TypesOfBusinesses.SELL);
        propertySale.setTypeOfCommission(PropertySale.CommissionType.PERCENTAGE);

        //property sale
        propertySale2 = new PropertySale();
        propertySale2.setCommission(30);
        propertySale2.setPrice(10_000_000);
        propertySale2.setProperty(land);
        propertySale2.setAgent(agent);
        propertySale2.setDateOfRequest(LocalDate.now());
        propertySale2.setTypeOfBusiness(PropertySaleRequest.TypesOfBusinesses.SELL);
        propertySale2.setTypeOfCommission(PropertySale.CommissionType.PERCENTAGE);

        // purchase request
        purchaseRequest = new PurchaseRequest();
        purchaseRequest.setClient(owner);
        purchaseRequest.setPropertySale(propertySale);
        purchaseRequest.setOrderAmount(100);
        purchaseRepository.createPurchaseRequest(mapper.toDTO(purchaseRequest).get());

        // purchase request 2
        purchaseRequest2 = new PurchaseRequest();
        purchaseRequest2.setClient(client);
        purchaseRequest2.setPropertySale(propertySale2);
        purchaseRequest2.setOrderAmount(20);
        purchaseRepository.createPurchaseRequest(mapper.toDTO(purchaseRequest2).get());

        // purchase request 3
        purchaseRequest3 = new PurchaseRequest();
        purchaseRequest3.setClient(client);
        purchaseRequest3.setPropertySale(propertySale2);
        purchaseRequest3.setOrderAmount(99999999);
    }

    @Test
    void getID() {
        assertEquals(purchaseRequest.getID(),11);

    }

    @Test
    void isValid() {
        boolean valid = purchaseRequest3.isValid().success();
        assertFalse(valid);
    }

    @Test
    void getClient() {
        assertEquals(owner, purchaseRequest.getClient());
    }

    @Test
    void getOrderAmount() {
        assertEquals(purchaseRequest.getOrderAmount(),100);
    }

    @Test
    void getPropertySale() {
        assertEquals(propertySale, purchaseRequest.getPropertySale());
    }

    @Test
    void setClient() {
        purchaseRequest.setClient(client);
        assertEquals(client, purchaseRequest.getClient());
    }

    @Test
    void setOrderAmount() {
        purchaseRequest.setOrderAmount(99);
        assertEquals(purchaseRequest.getOrderAmount(),99);
    }

    @Test
    void setPropertySale() {
        purchaseRequest.setPropertySale(propertySale2);
        assertEquals(purchaseRequest.getPropertySale(),propertySale2);
    }

    @Test
    void testEquals() {
        boolean equal = purchaseRequest.equals(purchaseRequest2);
        assertFalse(equal);
    }
    @Test
    void testEquals2(){
        PurchaseRequest purchaseRequest = purchaseRequest2;
        assertTrue(purchaseRequest.equals(purchaseRequest2));
    }
    @Test
    void isValidOrderAmount(){
        assertFalse(purchaseRequest3.isValid().success());
    }
    @Test
    void compare(){
        PurchaseRequest.SortByPriceComparator comparator = new PurchaseRequest.SortByPriceComparator();
        int i = comparator.compare(purchaseRequest,purchaseRequest2);
        assertEquals(i,1);
    }

}