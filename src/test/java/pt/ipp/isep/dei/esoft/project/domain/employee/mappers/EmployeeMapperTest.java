package pt.ipp.isep.dei.esoft.project.domain.employee.mappers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pt.ipp.isep.dei.esoft.project.domain.employee.*;
import pt.ipp.isep.dei.esoft.project.domain.employee.mappers.dto.*;
import pt.ipp.isep.dei.esoft.project.util.error.ErrorOptional;

import static org.junit.jupiter.api.Assertions.assertFalse;

class EmployeeMapperTest {

    private Agent agent;
    private StoreManager storeManager;
    private NetworkManager networkManager;
    private SystemAdministrator systemAdministrator;

    private AgentDTO agentDTO;
    private StoreManagerDTO storeManagerDTO;
    private NetworkManagerDTO networkManagerDTO;
    private SystemAdministratorDTO systemAdministratorDTO;

    private final EmployeeMapper mapper = new EmployeeMapper();

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

        storeManager = new StoreManager();
        storeManager.setName("John");
        storeManager.setAddress("Adress");
        storeManager.setEmail("ex@ex.com");
        storeManager.setTaxNumber("123-12-1234");
        storeManager.setPhoneNumber("123-123-1234");
        storeManager.setPassportNumber(123456789);

        networkManager = new NetworkManager();
        networkManager.setName("John");
        networkManager.setAddress("Adress");
        networkManager.setEmail("ex@ex.com");
        networkManager.setTaxNumber("123-12-1234");
        networkManager.setPhoneNumber("123-123-1234");
        networkManager.setPassportNumber(123456789);

        systemAdministrator = new SystemAdministrator();
        systemAdministrator.setName("John");
        systemAdministrator.setAddress("Adress");
        systemAdministrator.setEmail("ex@ex.com");
        systemAdministrator.setTaxNumber("123-12-1234");
        systemAdministrator.setPhoneNumber("123-123-1234");
        systemAdministrator.setPassportNumber(123456789);


        // -----DTO-----
        agentDTO = new AgentDTO("John", 123456789, "123-123-1234", "123-12-1234", "adress", "ex@ex.com");

        storeManagerDTO = new StoreManagerDTO("John", 123456789, "123-123-1234", "123-12-1234", "adress", "ex@ex.com");

        networkManagerDTO = new NetworkManagerDTO("John", 123456789, "123-123-1234", "123-12-1234", "adress", "ex@ex.com");

        systemAdministratorDTO = new SystemAdministratorDTO("John", 123456789, "123-123-1234", "123-12-1234", "adress", "ex@ex.com");

    }

    @Test
    void toDTO() {
        // valid
        ErrorOptional<EmployeeDTO> dto = mapper.toDTO(agent);
        assertFalse(dto.hasError());

        dto = mapper.toDTO(storeManager);
        assertFalse(dto.hasError());

        dto = mapper.toDTO(networkManager);
        assertFalse(dto.hasError());

        dto = mapper.toDTO(systemAdministrator);
        assertFalse(dto.hasError());

        // invalid - null
        dto = mapper.toDTO(null);
        assert (dto.hasError());

        // invalid - invalid Employee type
        class InvalidEmployeeType extends Employee{}
        InvalidEmployeeType invalidEmployeeType = new InvalidEmployeeType();
        dto = mapper.toDTO(invalidEmployeeType);
        assert (dto.hasError());

    }

    @Test
    void toDomain() {
        // valid
        ErrorOptional<Employee> domain = mapper.toDomain(agentDTO);
        assertFalse(domain.hasError());

        domain = mapper.toDomain(networkManagerDTO);
        assertFalse(domain.hasError());

        domain = mapper.toDomain(storeManagerDTO);
        assertFalse(domain.hasError());

        domain = mapper.toDomain(systemAdministratorDTO);
        assertFalse(domain.hasError());

        // invalid - null
        domain = mapper.toDomain(null);
        assert (domain.hasError());


        // invalid - invalid Employee type
        class InvalidEmployeeTypeDTO extends EmployeeDTO{}
        InvalidEmployeeTypeDTO invalidEmployeeTypeDTO = new InvalidEmployeeTypeDTO();
        domain = mapper.toDomain(invalidEmployeeTypeDTO);
        assert (domain.hasError());

    }
}