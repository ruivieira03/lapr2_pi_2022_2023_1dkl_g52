package pt.ipp.isep.dei.esoft.project.domain.employee.mappers.dto;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AgentDTOTest {

    private AgentDTO agentDTO;

    @BeforeEach
    void beforeEach() {
        agentDTO = new AgentDTO();
        agentDTO = new AgentDTO("John", 123456789, "123-123-1234", "123-12-1234", "adress", "ex@ex.com");
    }

    @Test
    void testToString() {
        String expected = "|                John|        123456789|  123-123-1234| 123-12-1234|              adress|           ex@ex.com|";
        assertEquals(agentDTO.toString(), expected);
    }
}