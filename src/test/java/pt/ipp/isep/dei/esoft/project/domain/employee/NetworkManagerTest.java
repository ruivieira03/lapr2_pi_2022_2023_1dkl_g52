package pt.ipp.isep.dei.esoft.project.domain.employee;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;

class NetworkManagerTest {

    private NetworkManager networkManager;

    @BeforeEach
    void beforeEach() {
        networkManager = new NetworkManager();
        networkManager.setName("John");
        networkManager.setAddress("Adress");
        networkManager.setEmail("ex@ex.com");
        networkManager.setTaxNumber("123-12-1234");
        networkManager.setPhoneNumber("123-123-1234");
        networkManager.setPassportNumber(123456789);
    }

    @Test
    void testEquals() {
        // valid - same reference
        assert (networkManager.equals(networkManager));

        // valid - same attributes
        NetworkManager networkManager1 = new NetworkManager();
        networkManager1.setName("John");
        networkManager1.setAddress("Adress");
        networkManager1.setEmail("ex@ex.com");
        networkManager1.setTaxNumber("123-12-1234");
        networkManager1.setPhoneNumber("123-123-1234");
        networkManager1.setPassportNumber(123456789);

        assert (networkManager.equals(networkManager1));

        // invalid - null
        assertFalse(networkManager.equals(null));

    }
}