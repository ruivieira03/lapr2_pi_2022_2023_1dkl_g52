package pt.ipp.isep.dei.esoft.project.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pt.ipp.isep.dei.esoft.project.domain.employee.NetworkManager;

public class NetworkManagerRepositoryTest {

    private NetworkManagerRepository networkManagerRepository;

    @BeforeEach
    void beforeEach() {
        networkManagerRepository = new NetworkManagerRepository();
        networkManagerRepository.createNetworkManager("Andrew", 123, 123, "adress", "netManage@ex.com", 123123123);
    }
    @Test
    void testContructor() {
        NetworkManager netManage = new NetworkManager("Miller", 123, 123, "adress", "netManage@ex.com", 123123123);
        networkManagerRepository = new NetworkManagerRepository(netManage);

        assert (networkManagerRepository.getNetworkManager().getName().equals("Miller"));
    }
    @Test
    void getNetworkManager() {
        assert (networkManagerRepository.getNetworkManager() != null);
    }
    @Test
    void setNetworkManager() {
        NetworkManager networkManager = new NetworkManager("name", 123, 123, "Adress", "email@ex.com", 123123123);
        networkManagerRepository.setNetworkManager(networkManager);
        assert (networkManagerRepository.getNetworkManager().getName().equals("name"));
    }

}
