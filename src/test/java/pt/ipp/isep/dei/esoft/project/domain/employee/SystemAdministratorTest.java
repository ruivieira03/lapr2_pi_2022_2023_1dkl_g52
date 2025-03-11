package pt.ipp.isep.dei.esoft.project.domain.employee;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;

class SystemAdministratorTest {

    private SystemAdministrator systemAdministrator;

    @BeforeEach
    void beforeEach() {
        systemAdministrator = new SystemAdministrator();
        systemAdministrator.setName("John");
        systemAdministrator.setAddress("Adress");
        systemAdministrator.setEmail("ex@ex.com");
        systemAdministrator.setTaxNumber("123-12-1234");
        systemAdministrator.setPhoneNumber("123-123-1234");
        systemAdministrator.setPassportNumber(123456789);
    }
    @Test
    void testEquals() {
        // valid - same reference
        assert (systemAdministrator.equals(systemAdministrator));


        // valid - same attributes
        SystemAdministrator systemAdministrator1 = new SystemAdministrator();
        systemAdministrator1.setName("John");
        systemAdministrator1.setAddress("Adress");
        systemAdministrator1.setEmail("ex@ex.com");
        systemAdministrator1.setTaxNumber("123-12-1234");
        systemAdministrator1.setPhoneNumber("123-123-1234");
        systemAdministrator1.setPassportNumber(123456789);

        assert (systemAdministrator.equals(systemAdministrator1));


        // invalid null
        assertFalse(systemAdministrator.equals(null));
    }
}