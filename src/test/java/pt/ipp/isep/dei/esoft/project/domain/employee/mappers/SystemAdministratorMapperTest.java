package pt.ipp.isep.dei.esoft.project.domain.employee.mappers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pt.ipp.isep.dei.esoft.project.domain.employee.SystemAdministrator;
import pt.ipp.isep.dei.esoft.project.domain.employee.mappers.dto.SystemAdministratorDTO;
import pt.ipp.isep.dei.esoft.project.util.error.ErrorOptional;

import static org.junit.jupiter.api.Assertions.assertFalse;

class SystemAdministratorMapperTest {

    private SystemAdministrator systemAdministrator;
    private SystemAdministratorDTO systemAdministratorDTO;
    private final SystemAdministratorMapper mapper = new SystemAdministratorMapper();

    @BeforeEach
    void beforeEach() {
        // -----Domain------
        systemAdministrator = new SystemAdministrator();
        systemAdministrator.setName("John");
        systemAdministrator.setAddress("Adress");
        systemAdministrator.setEmail("ex@ex.com");
        systemAdministrator.setTaxNumber("123-12-1234");
        systemAdministrator.setPhoneNumber("123-123-1234");
        systemAdministrator.setPassportNumber(123456789);

        // -----DTO------
        systemAdministratorDTO = new SystemAdministratorDTO("John", 123456789, "123-123-1234", "123-12-1234", "adress", "ex@ex.com");
    }

    @Test
    void toDTO() {
        // valid
        ErrorOptional<SystemAdministratorDTO> dto = mapper.toDTO(systemAdministrator);
        assertFalse(dto.hasError());

        // invalid - null
        dto = mapper.toDTO(null);
        assert (dto.hasError());
    }

    @Test
    void toDomain() {
        // valid
        ErrorOptional<SystemAdministrator> domain = mapper.toDomain(systemAdministratorDTO);
        assertFalse(domain.hasError());

        // invalid - null
        domain = mapper.toDomain(null);
        assert (domain.hasError());
    }
}