package pt.ipp.isep.dei.esoft.project.domain.employee;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;

class StoreManagerTest {

    private StoreManager storeManager;

    @BeforeEach
    void beforeEach() {
        storeManager = new StoreManager();
        storeManager.setName("John");
        storeManager.setAddress("Adress");
        storeManager.setEmail("ex@ex.com");
        storeManager.setTaxNumber("123-12-1234");
        storeManager.setPhoneNumber("123-123-1234");
        storeManager.setPassportNumber(123456789);
    }
    @Test
    void testEquals() {
        // valid - same reference
        assert (storeManager.equals(storeManager));

        // valid - same attributes
        StoreManager storeManager1 = new StoreManager();
        storeManager1.setName("John");
        storeManager1.setAddress("Adress");
        storeManager1.setEmail("ex@ex.com");
        storeManager1.setTaxNumber("123-12-1234");
        storeManager1.setPhoneNumber("123-123-1234");
        storeManager1.setPassportNumber(123456789);

        assert (storeManager.equals(storeManager1));

        // invalid - null
        assertFalse(storeManager.equals(null));
    }
}