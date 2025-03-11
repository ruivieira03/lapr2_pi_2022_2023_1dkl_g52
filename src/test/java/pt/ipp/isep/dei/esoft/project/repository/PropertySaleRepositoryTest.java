package pt.ipp.isep.dei.esoft.project.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pt.ipp.isep.dei.esoft.project.domain.client.Client;
import pt.ipp.isep.dei.esoft.project.domain.employee.Agent;
import pt.ipp.isep.dei.esoft.project.domain.locations.City;
import pt.ipp.isep.dei.esoft.project.domain.locations.District;
import pt.ipp.isep.dei.esoft.project.domain.locations.State;
import pt.ipp.isep.dei.esoft.project.domain.property.House;
import pt.ipp.isep.dei.esoft.project.domain.property.Property;
import pt.ipp.isep.dei.esoft.project.domain.property.PropertySale;
import pt.ipp.isep.dei.esoft.project.domain.property.PropertySaleRequest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import pt.ipp.isep.dei.esoft.project.domain.property.transactions.PropertySale;
import pt.ipp.isep.dei.esoft.project.domain.property.transactions.PropertySaleRequest;

class PropertySaleRepositoryTest {

    private PropertySaleRepository propertySaleRepository;

    @BeforeEach
    void beforeEach() {
        State state = new State("state", new String[]{"District"}, new String[][]{{"City"}});
        District district = state.getDistricts().get(0);
        City city = district.getCities().get(0);
        Client client = new Client("name", 123, 123, 123, "adress", "email@ex.com");
        Agent agent = new Agent("name", 123, 123, "123", "agent@ex.com", 123123123);

        propertySaleRepository = new PropertySaleRepository();

        propertySaleRepository.createPropertySale(PropertySaleRequest.TypesOfBusinesses.RENT, Property.Type.HOUSE,
                100, 10, 10000, state, district, city, "adress", "123", "photo",
                client, PropertySale.CommissionType.PERCENTAGE, 1, agent, 1, 1, 1, "none",
                true, true, House.SunExposure.SOUTH);

    }

    @Test
    void createPropertySale() {
        State state = new State("state", new String[]{"District"}, new String[][]{{"City"}});
        District district = state.getDistricts().get(0);
        City city = district.getCities().get(0);
        Client client = new Client("name", 123, 123, 123, "adress", "email@ex.com");
        Agent agent = new Agent("name", 123, 123, "123", "agent@ex.com", 123123123);

        // illegal arguments
        assert (!propertySaleRepository.createPropertySale(null, null, 0, 0,
                0, null, null, null, null, null, null, null,
                null, 0, null, 0, 0, 0,
                null, false, false, null));


        // failed local validation
        assert (!propertySaleRepository.createPropertySale(PropertySaleRequest.TypesOfBusinesses.RENT, Property.Type.HOUSE,
                -1, 10, 10000, state, district, city, "adress", "123", "photo",
                client, PropertySale.CommissionType.PERCENTAGE, 1, agent, 1, 1, 1, "none",
                true, true, House.SunExposure.SOUTH));

        // failed global validattion
        // assert (!propertySaleRepository.createPropertySale(PropertySaleRequest.TypesOfBusinesses.RENT, Property.Type.HOUSE,
           //     100, 10, 10000, state, district, city, "adress", "123", "photo",
             //   client, PropertySale.CommissionType.PERCENTAGE, 1, agent, 1, 1, 1, "none",
               // true, true, House.SunExposure.SOUTH));


        // working
        assert (propertySaleRepository.createPropertySale(PropertySaleRequest.TypesOfBusinesses.RENT, Property.Type.HOUSE,
                100, 10, 10000, state, district, city, "new street", "123", "photo",
                client, PropertySale.CommissionType.PERCENTAGE, 1, agent, 1, 1, 1, "none",
                true, true, House.SunExposure.SOUTH));
    }

    @Test
    void listProperties() {
        assert (propertySaleRepository.listProperties(PropertySaleRepository.SortByRecentlyAdded, PropertySaleRequest.TypesOfBusinesses.SELL, Property.Type.HOUSE, 1).size() == 0);
        assert (propertySaleRepository.listProperties(PropertySaleRepository.SortByRecentlyAdded).size() == 1);
    }



    @Test
    void getPropertySales() {
        assert (propertySaleRepository.getPropertySales().size() == 1);
    }

    @Test
    void addPropertySaleThroughData() {
        List<String[]> propertyList = new ArrayList<>();
        List<Client> clientList = new ArrayList<>();

        propertyList.add(new String[]{"house", "1710", 	"449 N Santa Claus Lane, North Pole, AK, 99705", "29", "3",	"4", "2", "Y", "Y", "Y", "Y", "S", "208500", "203748", "5", "NA", "08/01/2001" , "10/01/2001", "sale"});
        propertyList.add(new String[]{"apartment", "1711", "450 N Santa Claus Lane, North Pole, AK, 99705", "13", "5", "1", "4", "Y", "NA", "Y", "N", "S", "300000", "300200", "3", "14", "14/04/2013" , "10/01/2014", "rent"});

        clientList.add(new Client("Chris", 123456789, 123456789, 123456789, "Rua da 7", "chris@brown.app"));
        clientList.add(new Client("Rihanna", 987654321, 987654321, 987654321, "Rua da 8", "rihanna@riri.app"));

        assertEquals(true, propertySaleRepository.addPropertySaleThroughData(propertyList, clientList));
    }
}