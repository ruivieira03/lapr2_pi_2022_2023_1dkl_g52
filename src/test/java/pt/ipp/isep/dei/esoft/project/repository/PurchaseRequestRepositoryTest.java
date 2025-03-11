package pt.ipp.isep.dei.esoft.project.repository;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pt.ipp.isep.dei.esoft.project.application.controller.authorization.AuthenticationController;
import pt.ipp.isep.dei.esoft.project.domain.client.mappers.dto.ClientDTO;
import pt.ipp.isep.dei.esoft.project.domain.employee.mappers.dto.AgentDTO;
import pt.ipp.isep.dei.esoft.project.domain.employee.mappers.dto.NetworkManagerDTO;
import pt.ipp.isep.dei.esoft.project.domain.locations.mappers.dto.CityDTO;
import pt.ipp.isep.dei.esoft.project.domain.locations.mappers.dto.DistrictDTO;
import pt.ipp.isep.dei.esoft.project.domain.locations.mappers.dto.StateDTO;
import pt.ipp.isep.dei.esoft.project.domain.property.House;
import pt.ipp.isep.dei.esoft.project.domain.property.mappers.dto.ApartmentDTO;
import pt.ipp.isep.dei.esoft.project.domain.property.mappers.dto.HouseDTO;
import pt.ipp.isep.dei.esoft.project.domain.property.transactions.PropertySale;
import pt.ipp.isep.dei.esoft.project.domain.property.transactions.PropertySaleRequest;
import pt.ipp.isep.dei.esoft.project.domain.property.transactions.PurchaseRequest;
import pt.ipp.isep.dei.esoft.project.domain.property.transactions.mappers.PropertySaleMapper;
import pt.ipp.isep.dei.esoft.project.domain.property.transactions.mappers.PurchaseRequestMapper;
import pt.ipp.isep.dei.esoft.project.domain.property.transactions.mappers.dto.PropertySaleDTO;
import pt.ipp.isep.dei.esoft.project.domain.property.transactions.mappers.dto.PurchaseRequestDTO;
import pt.ipp.isep.dei.esoft.project.domain.store.mappers.dto.StoreDTO;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static pt.ipp.isep.dei.esoft.project.repository.Repositories.*;

class PurchaseRequestRepositoryTest {

    private final PurchaseRequestMapper purchaseRequestMapper = new PurchaseRequestMapper();

    private final PropertySaleMapper propertySaleMapper = new PropertySaleMapper();

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
    private AgentDTO agentDTO1 = new AgentDTO("Dave", 123456789, "123-123-1234", "123-12-1234", "street", "agent@ex.com");
    private AgentDTO agentDTO2 = new AgentDTO("John", 123454789, "123-173-1234", "123-12-1234", "street", "agent2@ex.com");
    ApartmentDTO apartmentDTO1;
    HouseDTO houseDTO1;

    PropertySaleDTO propertySaleDTO1;
    private PurchaseRequestDTO purchaseRequestDTO1;
    private PurchaseRequestDTO purchaseRequestDTO2;
    private PurchaseRequestDTO purchaseRequestDTO3;
    private PurchaseRequestDTO purchaseRequestDTO4;
    private PropertySaleDTO propertySaleDTO2;

    @BeforeEach
    void beforeEach() {


        locationsRepository.createState(stateDTO1);
        locationsRepository.createState(stateDTO2);


        StateDTO state1 = locationsRepository.getStatesList().get(0);
        StateDTO state2 = locationsRepository.getStatesList().get(1);

        storeDTO1.state = state1;
        storeDTO1.district = storeDTO1.state.districtList.get(0);
        storeDTO1.city = storeDTO1.district.cityList.get(0);

        storeDTO2.state = state2;
        storeDTO2.district = storeDTO2.state.districtList.get(0);
        storeDTO2.city = storeDTO2.district.cityList.get(0);

        NetworkManagerDTO networkManagerDTO = new NetworkManagerDTO("Andrew", 123534535, "123-432-2453", "123-13-1231", "Street", "netManage@ex.com");

        storeRepository.registerEmployee(networkManagerDTO, null);

        storeDTO1.agents.add(agentDTO1);
        storeDTO2.agents.add(agentDTO2);

        authenticationRepository.addUserWithRole(agentDTO1, "1234");
        authenticationRepository.addUserWithRole(agentDTO2, "1234");
        authenticationRepository.addUserWithRole(networkManagerDTO, "1234");

        storeRepository.createStore(storeDTO1);
        storeRepository.createStore(storeDTO2);


        StateDTO florida = locationsRepository.getStatesList().get(0);
        DistrictDTO miamiDade = florida.districtList.get(0);
        CityDTO miami = miamiDade.cityList.get(0);

        StateDTO texas = locationsRepository.getStatesList().get(1);
        DistrictDTO brazora = texas.districtList.get(0);
        CityDTO pearland = brazora.cityList.get(0);


        for (AuthenticationController.Roles role : AuthenticationController.Roles.values()) {
            authenticationRepository.addUserRole(role.name(), role.name());
        }

        ClientDTO clientDTO1 = new ClientDTO("Anne", 123456789, "123-123-1234", "123-12-1234", "adress", "owner@ex.com");
        ClientDTO clientDTO2 = new ClientDTO("Bill", 123456789, "123-123-1234", "123-12-1234", "adress", "client@ex.com");

        clientRepository.createClient(clientDTO1);
        clientRepository.createClient(clientDTO2);

        authenticationRepository.addUserWithRole(clientDTO1, "1234");
        authenticationRepository.addUserWithRole(clientDTO2, "1234");

        apartmentDTO1 = new ApartmentDTO(1000, 13, "5th Avenue n.19", 46265, florida, miamiDade, miami, "photos.png", clientDTO1, 1, 1, 10, "123");
        houseDTO1 = new HouseDTO(300, 4, "Independace Street n.4", 12345, texas, brazora, pearland, "photos.png", clientDTO1, 1, 1, 2, "None", true, true, House.SunExposure.SOUTH);

        propertySaleDTO1 = new PropertySaleDTO(houseDTO1, PropertySaleRequest.TypesOfBusinesses.SELL, 200_000, agentDTO1, LocalDate.now(), PropertySale.CommissionType.PERCENTAGE, 0.1);
        propertySaleDTO2 = new PropertySaleDTO(apartmentDTO1, PropertySaleRequest.TypesOfBusinesses.SELL, 200_000, agentDTO2, LocalDate.now(), PropertySale.CommissionType.PERCENTAGE, 0.1);

        propertySaleRepository.createPropertySale(propertySaleDTO1);
        propertySaleRepository.createPropertySale(propertySaleDTO2);

        purchaseRequestDTO1 = new PurchaseRequestDTO();
        purchaseRequestDTO2 = new PurchaseRequestDTO();
        purchaseRequestDTO3 = new PurchaseRequestDTO();
        purchaseRequestDTO4 = new PurchaseRequestDTO();

        purchaseRequestDTO4.orderAmount = 20;
        purchaseRequestDTO4.client = clientRepository.getClientDTOFromEmail("owner@ex.com").get();
        purchaseRequestDTO4.propertySale = propertySaleRepository.getPropertySales().get(1);

        purchaseRequestDTO3.orderAmount = 100;
        purchaseRequestDTO3.client = clientRepository.getClientDTOFromEmail("owner@ex.com").get();
        purchaseRequestDTO3.propertySale = propertySaleRepository.getPropertySales().get(0);

        purchaseRequestDTO2.orderAmount = 100;
        purchaseRequestDTO2.client = clientRepository.getClientDTOFromEmail("client@ex.com").get();
        purchaseRequestDTO2.propertySale = propertySaleRepository.getPropertySales().get(0);

        purchaseRequestDTO1.orderAmount = 50;
        purchaseRequestDTO1.client = clientRepository.getClientDTOFromEmail("owner@ex.com").get();
        purchaseRequestDTO1.propertySale = propertySaleRepository.getPropertySales().get(0);

        purchaseRepository.createPurchaseRequest(purchaseRequestDTO1);
        purchaseRepository.createPurchaseRequest(purchaseRequestDTO2);
        purchaseRepository.createPurchaseRequest(purchaseRequestDTO4);
    }

    @Test
    void createPurchaseRequest() {

        List<PurchaseRequest> purchaseList = new ArrayList<>();

        purchaseList.add(purchaseRequestMapper.toDomain(purchaseRequestDTO1).get());
        purchaseList.add(purchaseRequestMapper.toDomain(purchaseRequestDTO2).get());


        assertEquals(purchaseList, purchaseRepository.purchaseRequestList);
    }
    @Test
    void createPurchaseRequestSameClient(){
        purchaseRepository.createPurchaseRequest(purchaseRequestDTO3);

        List<PurchaseRequest> purchaseList = new ArrayList<>();
        purchaseList.add(purchaseRequestMapper.toDomain(purchaseRequestDTO1).get());
        purchaseList.add(purchaseRequestMapper.toDomain(purchaseRequestDTO2).get());
        purchaseList.add(purchaseRequestMapper.toDomain(purchaseRequestDTO4).get());

        assertEquals(purchaseList,purchaseRepository.purchaseRequestList);
    }
    @Test
    void samePurchaseRequest(){
        purchaseRequestDTO3 = purchaseRequestDTO1;
        purchaseRepository.createPurchaseRequest(purchaseRequestDTO3);

        List<PurchaseRequest> purchaseList = new ArrayList<>();
        purchaseList.add(purchaseRequestMapper.toDomain(purchaseRequestDTO1).get());
        purchaseList.add(purchaseRequestMapper.toDomain(purchaseRequestDTO2).get());

        assertEquals(purchaseList,purchaseRepository.purchaseRequestList);

    }

    @Test
    void listProperties() {
        List<PropertySaleDTO> propertySaleDTOList = new ArrayList<>();
        propertySaleDTO1.price = 220000;
        propertySaleDTOList.add(propertySaleDTO1);
        List<PropertySaleDTO> propertySaleDTOList1 = purchaseRepository.listProperties(agentDTO1);

        assertEquals(propertySaleDTOList.toString(), propertySaleDTOList1.toString());
    }

    @Test
    void listPurchaseRequests() {
        List<PurchaseRequestDTO> purchaseRequestDTOS = new ArrayList<>();
        purchaseRequestDTOS.add(purchaseRequestDTO1);
        purchaseRequestDTOS.add(purchaseRequestDTO2);

        List<PurchaseRequestDTO> purchaseRequestDTOS1 = purchaseRepository.listPurchaseRequests(propertySaleDTO1);

        assertEquals(purchaseRequestDTOS1.toString(), purchaseRequestDTOS.toString());
    }

    @Test
    void removePurchaseRequest() {

        boolean equal = false;

        List<PurchaseRequest> purchaseRequests = purchaseRepository.purchaseRequestList;

        purchaseRepository.removePurchaseRequest(purchaseRequestDTO1);

        for (PurchaseRequest purchaseRequest : purchaseRequests) {
            if (purchaseRequestMapper.toDTO(purchaseRequest).get() == purchaseRequestDTO1)
                equal = true;
        }
        assertFalse(equal);
    }

    @Test
    void removeAllPurchaseRequest() {
        boolean equal = false;
        purchaseRepository.removeAllPurchaseRequest(propertySaleDTO1, agentDTO1);

        for (PurchaseRequest purchaseRequest : purchaseRepository.purchaseRequestList) {
            if (purchaseRequest.getPropertySale() == propertySaleMapper.toDomain(propertySaleDTO1).get())
                equal = true;
        }
        assertFalse(equal);

    }
}