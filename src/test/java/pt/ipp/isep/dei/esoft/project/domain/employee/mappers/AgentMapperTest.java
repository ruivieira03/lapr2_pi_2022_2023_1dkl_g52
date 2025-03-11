package pt.ipp.isep.dei.esoft.project.domain.employee.mappers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pt.ipp.isep.dei.esoft.project.domain.employee.Agent;
import pt.ipp.isep.dei.esoft.project.domain.employee.mappers.dto.AgentDTO;
import pt.ipp.isep.dei.esoft.project.util.error.ErrorOptional;

import static org.junit.jupiter.api.Assertions.assertFalse;

class AgentMapperTest {

    private Agent agent;

    private AgentDTO agentDTO;

    private final AgentMapper mapper = new AgentMapper();

    @BeforeEach
    void beforeEach() {
        // -----Domain-----
        agent = new Agent();
        agent.setName("John");
        agent.setAddress("Adress");
        agent.setEmail("ex@ex.com");
        agent.setTaxNumber("123-12-1234");
        agent.setPhoneNumber("123-123-1234");
        agent.setPassportNumber(123456789);

        // -----DTO-----
        agentDTO = new AgentDTO("John", 123456789, "123-123-1234", "123-12-1234", "adress", "ex@ex.com");
    }

    @Test
    void toDTO() {
        // valid
        ErrorOptional<AgentDTO> dto = mapper.toDTO(agent);
        assertFalse(dto.hasError());

        // invalid - null
        dto = mapper.toDTO(null);
        assert (dto.hasError());
    }

    @Test
    void toDomain() {
        // valid
        ErrorOptional<Agent> domain = mapper.toDomain(agentDTO);
        assertFalse(domain.hasError());

        // invalid - null
        domain = mapper.toDomain(null);
        assert (domain.hasError());
    }
}