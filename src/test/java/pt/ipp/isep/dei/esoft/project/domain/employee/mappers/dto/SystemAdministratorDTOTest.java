package pt.ipp.isep.dei.esoft.project.domain.employee.mappers.dto;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SystemAdministratorDTOTest {

    private SystemAdministratorDTO systemAdministratorDTO;

    @BeforeEach
    void beforeEach() {
        systemAdministratorDTO = new SystemAdministratorDTO();
        systemAdministratorDTO = new SystemAdministratorDTO("John", 123456789, "123-123-1234", "123-12-1234", "adress", "ex@ex.com");
    }

    @Test
    void test() {
        assertEquals(systemAdministratorDTO.toString(), systemAdministratorDTO.name);
    }

}