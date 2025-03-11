package pt.ipp.isep.dei.esoft.project.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pt.ipp.isep.dei.esoft.project.application.controller.authorization.AuthenticationController;
import pt.ipp.isep.dei.esoft.project.domain.client.mappers.dto.ClientDTO;
import pt.ipp.isep.dei.esoft.project.domain.employee.Agent;
import pt.ipp.isep.dei.esoft.project.domain.employee.NetworkManager;
import pt.ipp.isep.dei.esoft.project.domain.employee.mappers.AgentMapper;
import pt.ipp.isep.dei.esoft.project.domain.employee.mappers.NetworkManagerMapper;
import pt.ipp.isep.dei.esoft.project.domain.employee.mappers.StoreManagerMapper;
import pt.ipp.isep.dei.esoft.project.domain.employee.mappers.dto.AgentDTO;
import pt.ipp.isep.dei.esoft.project.domain.employee.mappers.dto.NetworkManagerDTO;
import pt.ipp.isep.dei.esoft.project.domain.employee.mappers.dto.StoreManagerDTO;
import pt.ipp.isep.dei.esoft.project.domain.locations.mappers.dto.CityDTO;
import pt.ipp.isep.dei.esoft.project.domain.locations.mappers.dto.DistrictDTO;
import pt.ipp.isep.dei.esoft.project.domain.locations.mappers.dto.StateDTO;
import pt.ipp.isep.dei.esoft.project.domain.property.House;
import pt.ipp.isep.dei.esoft.project.domain.property.mappers.dto.ApartmentDTO;
import pt.ipp.isep.dei.esoft.project.domain.property.mappers.dto.HouseDTO;
import pt.ipp.isep.dei.esoft.project.domain.property.transactions.PropertySale;
import pt.ipp.isep.dei.esoft.project.domain.property.transactions.PropertySaleRequest;
import pt.ipp.isep.dei.esoft.project.domain.property.transactions.mappers.dto.PropertySaleDTO;
import pt.ipp.isep.dei.esoft.project.domain.property.transactions.mappers.dto.PurchaseRequestDTO;
import pt.ipp.isep.dei.esoft.project.domain.store.Store;
import pt.ipp.isep.dei.esoft.project.domain.store.mappers.StoreMapper;
import pt.ipp.isep.dei.esoft.project.domain.store.mappers.dto.StoreDTO;
import pt.isep.lei.esoft.auth.domain.model.Email;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static pt.ipp.isep.dei.esoft.project.repository.Repositories.*;

class StoreRepositoryTest {

    private PurchaseRequestRepository purchaseRepository = getPurchaseRequestRepository();
    private LocationsRepository locationsRepository = getLocationsRepository();
    private StoreRepository storeRepository = getStoreRepository();
    private AuthenticationRepository authenticationRepository = Repositories.getAuthenticationRepository();
    private PropertySaleRepository propertySaleRepository = Repositories.getPropertySaleRepository();
    private StoreRepository storeNetworkRepository = Repositories.getStoreRepository();
    private ClientRepository clientRepository = Repositories.getClientRepository();

    private StateDTO stateDTO1 = new StateDTO("Florida", new String[]{"Miami Dade"}, new String[][]{{"Miami"}});
    private StateDTO stateDTO2 = new StateDTO("Texas", new String[]{"Brazora"}, new String[][]{{"PearLand"}});

    private StoreDTO storeDTO1 = new StoreDTO("Miami", "store1@ex.com", "123-123-1234", "Pacific avenue", 12345);
    private StoreDTO storeDTO2 = new StoreDTO("PearLand", "store2@ex.com", "123-123-1234", "Boring Street", 15293);
    private StoreDTO storeDTO3 = new StoreDTO("PearLand", "store3@ex.com", "123-123-2234", "Street", 15292);
    private AgentDTO agentDTO1 = new AgentDTO("Dave", 123456789, "123-123-1234", "123-12-1234", "street", "agent@ex.com");
    private AgentDTO agentDTO2 = new AgentDTO("John", 123454789, "123-173-1234", "123-12-1234", "street", "agent2@ex.com");
    private AgentDTO agentDTO3 = new AgentDTO("Joe", 123454782, "123-123-1234", "123-12-1224", "street2", "agent3@ex.com");
    ApartmentDTO apartmentDTO1;
    HouseDTO houseDTO1;

    ApartmentDTO apartmentDTO2;
    ApartmentDTO apartmentDTO3;

    PropertySaleDTO propertySaleDTO1;
    PropertySaleDTO propertySaleDTO2;
    PropertySaleDTO propertySaleDTO3;
    PropertySaleDTO propertySaleDTO4;
    NetworkManagerDTO networkManagerDTO;

    @BeforeEach
    void beforeEach() {

        locationsRepository.createState(stateDTO1);
        locationsRepository.createState(stateDTO2);

        storeDTO1.state = stateDTO1;
        storeDTO1.district = storeDTO1.state.districtList.get(0);
        storeDTO1.city = storeDTO1.district.cityList.get(0);

        storeDTO2.state = stateDTO2;
        storeDTO2.district = storeDTO2.state.districtList.get(0);
        storeDTO2.city = storeDTO2.district.cityList.get(0);

        storeDTO3.state = stateDTO2;
        storeDTO3.district = storeDTO3.state.districtList.get(0);
        storeDTO3.city = storeDTO3.district.cityList.get(0);

        networkManagerDTO = new NetworkManagerDTO("Andrew", 123534535, "123-432-2453", "123-13-1231", "Street", "netManage@ex.com");

        storeRepository.registerEmployee(networkManagerDTO, null);

        storeDTO1.agents.add(agentDTO1);
        storeDTO2.agents.add(agentDTO2);
        storeDTO3.agents.add(agentDTO3);


        authenticationRepository.addUserWithRole(agentDTO1, "1234");
        authenticationRepository.addUserWithRole(agentDTO2, "1234");
        authenticationRepository.addUserWithRole(agentDTO3, "1234");
        authenticationRepository.addUserWithRole(networkManagerDTO, "1234");


        storeRepository.createStore(storeDTO1);
        storeRepository.createStore(storeDTO2);
        storeRepository.createStore(storeDTO3);


        StateDTO florida = locationsRepository.getStatesList().get(0);
        DistrictDTO miamiDade = florida.districtList.get(0);
        CityDTO miami = miamiDade.cityList.get(0);

        StateDTO texas = locationsRepository.getStatesList().get(1);
        DistrictDTO brazora = texas.districtList.get(0);
        CityDTO pearland = brazora.cityList.get(0);


        for (AuthenticationController.Roles role : AuthenticationController.Roles.values()) {
            authenticationRepository.addUserRole(role.name(), role.name());
        }

        ClientDTO clientDTO1 = new ClientDTO("Anne", 123456789, "123-123-1234", "123-12-1234", "adress", "client1@ex.com");
        ClientDTO clientDTO2 = new ClientDTO("Bill", 123456789, "123-123-1234", "123-12-1234", "adress", "client2@ex.com");
        ClientDTO clientDTO3 = new ClientDTO("Bil", 123456389, "123-123-1334", "133-12-1334", "adress", "client3@ex.com");
        ClientDTO clientDTO4 = new ClientDTO("Billl", 123436789, "123-123-1334", "133-12-1234", "adress", "client4@ex.com");

        clientRepository.createClient(clientDTO1);
        clientRepository.createClient(clientDTO2);
        clientRepository.createClient(clientDTO3);
        clientRepository.createClient(clientDTO4);

        authenticationRepository.addUserWithRole(clientDTO1, "1234");
        authenticationRepository.addUserWithRole(clientDTO2, "1234");
        authenticationRepository.addUserWithRole(clientDTO3, "1234");
        authenticationRepository.addUserWithRole(clientDTO4, "1234");

        apartmentDTO1 = new ApartmentDTO(1000, 13, "5th Avenue n.19", 46265, florida, miamiDade, miami, "photos.png", clientDTO1, 1, 1, 10, "123");
        houseDTO1 = new HouseDTO(300, 4, "Independace Street n.4", 12345, texas, brazora, pearland, "photos.png", clientDTO2, 1, 1, 2, "None", true, true, House.SunExposure.SOUTH);
        apartmentDTO2 = new ApartmentDTO(1000, 13, "5th Avenue n.19", 46265, florida, miamiDade, miami, "photos.png", clientDTO3, 1, 1, 10, "123");
        apartmentDTO3 = new ApartmentDTO(1000, 13, "5th Avenue n.12", 12265, florida, miamiDade, miami, "photos.png", clientDTO4, 1, 2, 3, "123");

        propertySaleDTO1 = new PropertySaleDTO(houseDTO1, PropertySaleRequest.TypesOfBusinesses.SELL, 200_000, agentDTO1, LocalDate.now(), PropertySale.CommissionType.PERCENTAGE, 0.1);
        propertySaleDTO2 = new PropertySaleDTO(apartmentDTO1, PropertySaleRequest.TypesOfBusinesses.SELL, 200_000, agentDTO2, LocalDate.now(), PropertySale.CommissionType.PERCENTAGE, 0.1);
        propertySaleDTO3 = new PropertySaleDTO(apartmentDTO2, PropertySaleRequest.TypesOfBusinesses.SELL, 200_000, agentDTO3, LocalDate.now(), PropertySale.CommissionType.PERCENTAGE, 0.1);
        propertySaleDTO4 = new PropertySaleDTO(apartmentDTO3, PropertySaleRequest.TypesOfBusinesses.SELL, 200_000, agentDTO3, LocalDate.now(), PropertySale.CommissionType.PERCENTAGE, 0.1);

        propertySaleRepository.createPropertySale(propertySaleDTO1);
        propertySaleRepository.createPropertySale(propertySaleDTO2);
        propertySaleRepository.createPropertySale(propertySaleDTO3);
        propertySaleRepository.createPropertySale(propertySaleDTO4);


    }

    @Test
    void getAgentList() {
        List<AgentDTO> list = new ArrayList<>();
        list.add(agentDTO1);
        list.add(agentDTO2);
        list.add(agentDTO3);

        assertEquals(list.toString(), storeRepository.getAgentList().toString());
    }

    @Test
    void testGetAgentFromEmail() {
        AgentMapper agentMapper = new AgentMapper();
        Email email = agentDTO2.email;
        Agent agent = storeRepository.getAgentFromEmail(email).get();

        assertEquals(agentDTO2.name, agent.getName());
    }

    @Test
    void getStore() {
        StoreMapper storeMapper = new StoreMapper();
        Store store = storeRepository.getStore(storeDTO1).get();
        storeDTO1.propertyCount = 1;

        assertEquals(storeMapper.toDTO(store).get().toString(), storeDTO1.toString());
    }

    @Test
    void getAgentDTOFromEmail() {
        assertEquals(agentDTO1.toString(), storeRepository.getAgentDTOFromEmail(agentDTO1.email).get().toString());
    }

    @Test
    void createStore() {
        StoreDTO storeDTO4 = storeDTO1;
        StoreMapper storeMapper = new StoreMapper();
        StoreRepository storeRepository2 = new StoreRepository();
        storeRepository2.createStore(storeDTO4);
        storeRepository.createStore(storeDTO4);
        storeDTO4.propertyCount = 1;

        assertEquals(storeDTO4.toString(), storeMapper.toDTO(storeRepository2.stores.get(0)).get().toString());
    }

    @Test
    void isValid() {
        StoreDTO storeDTO5 = storeDTO2;

        List<Store> list = storeRepository.stores;

        storeRepository.createStore(storeDTO5);

        assertEquals(list.toString(), storeRepository.stores.toString());
    }

    @Test
    void registerEmployeeNetworkManager() {
        StoreDTO storeDTO4 = storeDTO1;
        StoreRepository storeRepository2 = new StoreRepository();
        storeRepository2.createStore(storeDTO4);
        NetworkManagerMapper mapper = new NetworkManagerMapper();
        NetworkManagerDTO networkManagerDTO2 = new NetworkManagerDTO("Andre", 1235345235, "123-422-2453", "123-12-1231", "Street2", "netManage2@ex.com");
        storeRepository2.registerEmployee(networkManagerDTO2, null);

        assertEquals(storeRepository2.networkManager.getName(), networkManagerDTO2.toString());
    }

    @Test
    void registerEmployeeAgent() {
        StoreRepository storeRepository2 = new StoreRepository();
        StoreDTO storeDTO4 = storeDTO1;
        storeRepository2.createStore(storeDTO4);
        AgentMapper mapper = new AgentMapper();
        AgentDTO agentDTO4 = new AgentDTO("Johe", 122454782, "123-122-1234", "122-12-1224", "street2", "agent4@ex.com");
        storeRepository2.registerEmployee(agentDTO4, storeDTO4);

        assertEquals(storeRepository2.getAgentDTOFromEmail(agentDTO4.email).get().toString(), agentDTO4.toString());
    }

    @Test
    void registerEmployee() {

        StoreDTO storeDTO4 = storeDTO1;
        StoreRepository storeRepository2 = new StoreRepository();
        storeRepository2.createStore(storeDTO4);
        StoreManagerMapper mapper = new StoreManagerMapper();
        StoreManagerDTO storeManagerDTO = new StoreManagerDTO("Johel", 123454782, "123-122-1434", "122-14-1224", "street2", "agent6@ex.com");
        storeRepository2.registerEmployee(storeManagerDTO, storeDTO4);

        assertEquals(storeRepository2.getStore(storeDTO1).get().getStoreManager().getName(), storeManagerDTO.name);
    }

    @Test
    void getSortedStoreList() {
        storeDTO1.propertyCount = 1;
        storeDTO2.propertyCount = 1;
        storeDTO3.propertyCount = 2;

        List<StoreDTO> list = new ArrayList<>();
        list.add(storeDTO1);
        list.add(storeDTO2);
        list.add(storeDTO3);

        assertEquals(list.toString(), storeRepository.getSortedStoreList().toString());
    }

    @Test
    void getAgentListFromStore() {
        assertEquals(storeDTO1.agents.toString(), storeRepository.getAgentListFromStore(storeDTO1).toString());
    }


}