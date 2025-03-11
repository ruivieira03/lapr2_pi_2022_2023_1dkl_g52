package pt.ipp.isep.dei.esoft.project.domain.employee;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;

class AgentTest {

    private Agent agent;

    @BeforeEach
    void beforeEach() {
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
        // valid - same reference
        assert (agent.equals(agent));

        // valid same attributes
        Agent agent1 = new Agent();
        agent1.setName("John");
        agent1.setAddress("Adress");
        agent1.setEmail("ex@ex.com");
        agent1.setTaxNumber("123-12-1234");
        agent1.setPhoneNumber("123-123-1234");
        agent1.setPassportNumber(123456789);

        assert (agent.equals(agent1));

        // invalid - null
        assertFalse(agent.equals(null));
    }

    @Test
    void hasEmail() {
        assert (agent.hasEmail("ex@ex.com"));
    }

    @Test
    void testHasEmail() {
        assert (agent.hasEmail("ex@ex.com"));
    }
}