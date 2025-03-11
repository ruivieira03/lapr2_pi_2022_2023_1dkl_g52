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
import pt.ipp.isep.dei.esoft.project.domain.property.transactions.PropertySaleRequest;

class PropertySaleRequestRepositoryTest {

    private PropertySaleRequestRepository propertySaleRequestRepository;

    @BeforeEach
    void beforeEach () {
        propertySaleRequestRepository = new PropertySaleRequestRepository();

        State state = new State("state", new String[]{"District"}, new String[][]{{"City"}});
        District district = state.getDistricts().get(0);
        City city = district.getCities().get(0);
        Client client = new Client("name", 123, 123, 123, "adress", "email@ex.com");
        Agent agent = new Agent("name", 123, 123, "123", "agent@ex.com", 123123123);

        propertySaleRequestRepository.createPropertySaleRequest(PropertySaleRequest.TypesOfBusinesses.RENT, Property.Type.HOUSE,
                100, 10, 10000, state, district, city, "adress", "123", "photo",
                client, agent, 1, 1, 1, "none",
                true, true, House.SunExposure.SOUTH);

    }

    @Test
    void createPropertySaleRequest() {

        State state = new State("state", new String[]{"District"}, new String[][]{{"City"}});
        District district = state.getDistricts().get(0);
        City city = district.getCities().get(0);
        Client client = new Client("name", 123, 123, 123, "adress", "email@ex.com");
        Agent agent = new Agent("name", 123, 123, "123", "agent@ex.com", 123123123);

        // fail local validation
        assert(!propertySaleRequestRepository.createPropertySaleRequest(PropertySaleRequest.TypesOfBusinesses.RENT, Property.Type.HOUSE,
                -1, 10, 10000, state, district, city, "adress", "123", "photo",
                client, agent, 1, 1, 1, "none",
                true, true, House.SunExposure.SOUTH));


        // fail global validation
        //assert(!propertySaleRequestRepository.createPropertySaleRequest(PropertySaleRequest.TypesOfBusinesses.RENT, Property.Type.HOUSE,
          //      100, 10, 10000, state, district, city, "adress", "123", "photo",
            //    client, agent, 1, 1, 1, "none",
              //  true, true, House.SunExposure.SOUTH));

        // working

        assert(propertySaleRequestRepository.createPropertySaleRequest(PropertySaleRequest.TypesOfBusinesses.RENT, Property.Type.HOUSE,
                10, 1, 10000, state, district, city, "street", "123", "photo",
                client, agent, 1, 1, 1, "none",
                true, true, House.SunExposure.SOUTH));


    }


}