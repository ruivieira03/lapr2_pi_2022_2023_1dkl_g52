package pt.ipp.isep.dei.esoft.project.domain.employee;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;

class EmployeeTest {

    private Agent agent;

    @BeforeEach
    void beforeEach() {
        // -----Agent-----
        agent = new Agent();
        agent.setName("John");
        agent.setAddress("Adress");
        agent.setEmail("ex@ex.com");
        agent.setTaxNumber("123-12-1234");
        agent.setPhoneNumber("123-123-1234");
        agent.setPassportNumber(123456789);
    }

    @Test
    void testEquals() {
        Agent agent1 = new Agent();
        agent1.setName("John");
        agent1.setAddress("Adress");
        agent1.setEmail("ex@ex.com");
        agent1.setTaxNumber("123-12-1234");
        agent1.setPhoneNumber("123-123-1234");
        agent1.setPassportNumber(123456789);


        // valid - same reference
        assert (agent.equals(agent));

        // valid - same attributes
        assert (agent.equals(agent1));

        // invalid - null
        assertFalse (agent.equals(null));
    }

    @Test
    void isValid() {
        // valid
        assert (agent.isValid().success());

        // invalid name
        agent.setName("1234");
        assertFalse(agent.isValid().success());
        agent.setName("John");

        // invalid passportNumber
        agent.setPassportNumber(1234);
        assertFalse(agent.isValid().success());
        agent.setPassportNumber(123456789);

        // invalid taxNumber
        agent.setTaxNumber("123");
        assertFalse(agent.isValid().success());
        agent.setTaxNumber("123-12-1234");

        // invalid adress
        agent.setAddress("");
        assertFalse(agent.isValid().success());
        agent.setAddress("adress");

        // invalid phoneNumber
        agent.setPhoneNumber("123");
        assertFalse(agent.isValid().success());
        agent.setPhoneNumber("123-123-1234");
    }

    @Test
    void isNameValid() {
        String validName = "John Cena";
        String invalidName = "Jonh123";
        String nullName = null;
        String emptyName = "";

        assert (Employee.isNameValid(validName).success());
        assertFalse(Employee.isNameValid(invalidName).success());
        assertFalse(Employee.isNameValid(nullName).success());
        assertFalse(Employee.isNameValid(emptyName).success());
    }

    @Test
    void testEnum() {
        assert (Employee.Role.SYSTEM_ADMIN instanceof Employee.Role);
        assert (Employee.Role.STORE_MANAGER instanceof Employee.Role);
        assert (Employee.Role.NETWORK_MANAGER instanceof Employee.Role);
        assert (Employee.Role.AGENT instanceof Employee.Role);


    }
}