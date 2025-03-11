package pt.ipp.isep.dei.esoft.project.ui;

import pt.ipp.isep.dei.esoft.project.application.controller.authorization.AuthenticationController;
import pt.ipp.isep.dei.esoft.project.domain.client.mappers.dto.ClientDTO;
import pt.ipp.isep.dei.esoft.project.domain.employee.mappers.dto.AgentDTO;
import pt.ipp.isep.dei.esoft.project.domain.employee.mappers.dto.NetworkManagerDTO;
import pt.ipp.isep.dei.esoft.project.domain.employee.mappers.dto.SystemAdministratorDTO;
import pt.ipp.isep.dei.esoft.project.domain.locations.mappers.dto.CityDTO;
import pt.ipp.isep.dei.esoft.project.domain.locations.mappers.dto.DistrictDTO;
import pt.ipp.isep.dei.esoft.project.domain.locations.mappers.dto.StateDTO;
import pt.ipp.isep.dei.esoft.project.domain.property.House;
import pt.ipp.isep.dei.esoft.project.domain.property.mappers.dto.ApartmentDTO;
import pt.ipp.isep.dei.esoft.project.domain.property.mappers.dto.HouseDTO;
import pt.ipp.isep.dei.esoft.project.domain.property.mappers.dto.LandDTO;
import pt.ipp.isep.dei.esoft.project.domain.property.transactions.PropertySale;
import pt.ipp.isep.dei.esoft.project.domain.property.transactions.PropertySaleRequest;
import pt.ipp.isep.dei.esoft.project.domain.property.transactions.mappers.PropertySaleMapper;
import pt.ipp.isep.dei.esoft.project.domain.property.transactions.mappers.dto.PropertySaleDTO;
import pt.ipp.isep.dei.esoft.project.domain.property.transactions.mappers.dto.VisitRequestDTO;
import pt.ipp.isep.dei.esoft.project.domain.store.mappers.dto.StoreDTO;
import pt.ipp.isep.dei.esoft.project.repository.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Bootstrap implements Runnable {

    public void run() {
        addLocations();
        addClientsAndRoles();
        addStoreNetworksAndEmployees();
        addSystemAdmin();
        addPropertySales();
        addVisitRequests();
    }

    private void addLocations() {
        LocationsRepository locationsRepository = Repositories.getLocationsRepository();

        StateDTO stateDTO1 = new StateDTO("Florida", new String[]{"Miami Dade"}, new String[][]{{"Miami"}});
        StateDTO stateDTO2 = new StateDTO("Texas", new String[]{"Brazora"}, new String[][]{{"PearLand"}});
        StateDTO stateDTO3 = new StateDTO("Alabama", new String[]{"Montgomery"}, new String[][]{{"Montgomery"}});
        StateDTO stateDTO4 = new StateDTO("California", new String[]{"San Francisco"}, new String[][]{{"San Francisco"}});

        locationsRepository.createState(stateDTO1);
        locationsRepository.createState(stateDTO2);
        locationsRepository.createState(stateDTO3);
        locationsRepository.createState(stateDTO4);
    }

    private void addStoreNetworksAndEmployees() {
        StoreRepository storeRepository = Repositories.getStoreRepository();
        LocationsRepository locationsRepository = Repositories.getLocationsRepository();
        AuthenticationRepository authenticationRepository = Repositories.getAuthenticationRepository();

        StoreDTO storeDTO1 = new StoreDTO("Miami", "store1@ex.com", "123-123-1234", "Pacific avenue", 12345);
        StoreDTO storeDTO2 = new StoreDTO("PearLand", "store2@ex.com", "123-123-1234", "Boring Street", 15293);

        StateDTO state1 = locationsRepository.getStatesList().get(0);
        StateDTO state2 = locationsRepository.getStatesList().get(1);

        storeDTO1.state = state1;
        storeDTO1.district = storeDTO1.state.districtList.get(0);
        storeDTO1.city = storeDTO1.district.cityList.get(0);

        storeDTO2.state = state2;
        storeDTO2.district = storeDTO2.state.districtList.get(0);
        storeDTO2.city = storeDTO2.district.cityList.get(0);

        NetworkManagerDTO networkManagerDTO = new NetworkManagerDTO("Andrew", 123534535, "123-432-2453", "123-13-1231", "Street", "netManage@gmail.com");

        storeRepository.registerEmployee(networkManagerDTO, null);


        AgentDTO agentDTO1 = new AgentDTO("Dave", 123456789, "123-123-1234", "123-12-1234", "street", "agent@gmail.com");
        AgentDTO agentDTO2 = new AgentDTO("John", 123454789, "123-173-1234", "123-12-1234", "street", "agent2@gmail.com");

        storeDTO1.agents.add(agentDTO1);
        storeDTO2.agents.add(agentDTO2);

        authenticationRepository.addUserWithRole(agentDTO1, "1234");
        authenticationRepository.addUserWithRole(agentDTO2, "1234");
        authenticationRepository.addUserWithRole(networkManagerDTO, "1234");

        storeRepository.createStore(storeDTO1);
        storeRepository.createStore(storeDTO2);
    }

    private void addSystemAdmin() {
        SystemAdministratorRepository systemAdministratorRepository = Repositories.getSystemAdministratorRepository();
        AuthenticationRepository authenticationRepository = Repositories.getAuthenticationRepository();


        SystemAdministratorDTO systemAdministratorDTO = new SystemAdministratorDTO("Clive", 123123123, "193-173-1234", "123-12-1234", "adress", "admin@gmail.com");

        systemAdministratorRepository.createSystemAdministrator(systemAdministratorDTO);
        authenticationRepository.addUserWithRole(systemAdministratorDTO, "1234");
    }

    private void addClientsAndRoles() {
        ClientRepository clientRepository = Repositories.getClientRepository();
        AuthenticationRepository authenticationRepository = Repositories.getAuthenticationRepository();

        for (AuthenticationController.Roles role : AuthenticationController.Roles.values()) {
            authenticationRepository.addUserRole(role.name(), role.name());
        }

        ClientDTO clientDTO1 = new ClientDTO("Anne", 123456789, "123-123-1234", "123-12-1234", "adress", "owner@gmail.com");
        ClientDTO clientDTO2 = new ClientDTO("Bill", 123456789, "123-123-1234", "123-12-1234", "adress", "client@gmail.com");

        clientRepository.createClient(clientDTO1);
        clientRepository.createClient(clientDTO2);

        authenticationRepository.addUserWithRole(clientDTO1, "1234");
        authenticationRepository.addUserWithRole(clientDTO2, "1234");
    }

    private void addPropertySales() {
        PropertySaleRepository propertySaleRepository = Repositories.getPropertySaleRepository();
        LocationsRepository locationsRepository = Repositories.getLocationsRepository();
        StoreRepository storeNetworkRepository = Repositories.getStoreRepository();
        ClientRepository clientRepository = Repositories.getClientRepository();


        StateDTO florida = locationsRepository.getStatesList().get(0);
        DistrictDTO miamiDade = florida.districtList.get(0);
        CityDTO miami = miamiDade.cityList.get(0);

        StateDTO texas = locationsRepository.getStatesList().get(1);
        DistrictDTO brazora = texas.districtList.get(0);
        CityDTO pearland = brazora.cityList.get(0);

        StateDTO alabama = locationsRepository.getStatesList().get(2);
        DistrictDTO montgomery = alabama.districtList.get(0);
        CityDTO montgomeryCity = montgomery.cityList.get(0);

        StateDTO california = locationsRepository.getStatesList().get(3);
        DistrictDTO sanFrancisco = california.districtList.get(0);
        CityDTO sanFranciscoCity = sanFrancisco.cityList.get(0);

        AgentDTO agent = storeNetworkRepository.getAgentDTOFromEmail("agent@gmail.com").get();

        ClientDTO clientDTO1 = clientRepository.getClientDTOFromEmail("owner@gmail.com").get();

        ApartmentDTO apartmentDTO1 = new ApartmentDTO(1000, 13, "5th Avenue n.19", 46265, florida, miamiDade, miami, "photos.png", clientDTO1, 1, 1, 10, "123");
        HouseDTO houseDTO1 = new HouseDTO(300, 4, "Independace Street n.4", 12345, texas, brazora, pearland, "photos.png", clientDTO1, 1, 1, 2, "None", true, true, House.SunExposure.SOUTH);
        LandDTO landDTO1 = new LandDTO(1200, 16, "Dirt Road plot 6", 43286, alabama, montgomery, montgomeryCity, "photos.png", clientDTO1);
        LandDTO landDTO2 = new LandDTO(2000, 21, "High Montains plot 17", 28930, california, sanFrancisco, sanFranciscoCity, "photos.png", clientDTO1);

        PropertySaleDTO propertySaleDTO1 = new PropertySaleDTO(apartmentDTO1, PropertySaleRequest.TypesOfBusinesses.RENT, 100_000, agent, LocalDate.now(), PropertySale.CommissionType.PERCENTAGE, 0.1);
        PropertySaleDTO propertySaleDTO2 = new PropertySaleDTO(houseDTO1, PropertySaleRequest.TypesOfBusinesses.SELL, 200_000, agent, LocalDate.now(), PropertySale.CommissionType.PERCENTAGE, 0.1);
        PropertySaleDTO propertySaleDTO3 = new PropertySaleDTO(landDTO1, PropertySaleRequest.TypesOfBusinesses.SELL, 50_000, agent, LocalDate.now(), PropertySale.CommissionType.PERCENTAGE, 0.1);
        PropertySaleDTO propertySaleDTO4 = new PropertySaleDTO(landDTO2, PropertySaleRequest.TypesOfBusinesses.RENT, 70_000, agent, LocalDate.now(), PropertySale.CommissionType.PERCENTAGE, 0.1);

        propertySaleRepository.createPropertySale(propertySaleDTO1);
        propertySaleRepository.createPropertySale(propertySaleDTO2);
        propertySaleRepository.createPropertySale(propertySaleDTO3);
        propertySaleRepository.createPropertySale(propertySaleDTO4);
    }

    private void addVisitRequests() {
        PropertySaleRepository propertySaleRepository = Repositories.getPropertySaleRepository();
        VisitRequestRepository visitRequestRepository = Repositories.getVisitRequestRepository();
        PropertySaleMapper propertySaleMapper = new PropertySaleMapper();

        ClientDTO clientDTO2 = new ClientDTO("Bill", 123456789, "123-123-1234", "123-12-1234", "adress", "client@gmail.com");

        visitRequestRepository.registerVisitRequest(new VisitRequestDTO(clientDTO2, propertySaleMapper.toDTO(propertySaleRepository.getPropertySaleFromID(2).get()).get(), LocalDateTime.of(2024, 6, 12, 10, 0),LocalDateTime.of(2024, 6, 12, 12, 0)));
        visitRequestRepository.registerVisitRequest(new VisitRequestDTO(clientDTO2, propertySaleMapper.toDTO(propertySaleRepository.getPropertySaleFromID(3).get()).get(), LocalDateTime.of(2024, 6, 12, 9, 0),LocalDateTime.of(2024, 6, 12, 9, 30)));
        visitRequestRepository.registerVisitRequest(new VisitRequestDTO(clientDTO2, propertySaleMapper.toDTO(propertySaleRepository.getPropertySaleFromID(4).get()).get(), LocalDateTime.of(2024, 6, 12, 13, 0),LocalDateTime.of(2024, 6, 12, 14, 0)));
        visitRequestRepository.registerVisitRequest(new VisitRequestDTO(clientDTO2, propertySaleMapper.toDTO(propertySaleRepository.getPropertySaleFromID(5).get()).get(), LocalDateTime.of(2024, 6, 12, 17, 30),LocalDateTime.of(2024, 6, 12, 18, 0)));
    }
}
