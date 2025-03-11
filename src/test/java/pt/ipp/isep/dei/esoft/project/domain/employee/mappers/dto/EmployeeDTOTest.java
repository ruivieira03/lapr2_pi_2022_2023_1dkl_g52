package pt.ipp.isep.dei.esoft.project.domain.employee.mappers.dto;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EmployeeDTOTest {

    private StoreManagerDTO storeManagerDTO;

    @BeforeEach
    void beforeEach() {
        storeManagerDTO = new StoreManagerDTO();
        storeManagerDTO = new StoreManagerDTO("John", 123456789, "123-123-1234", "123-12-1234", "adress", "ex@ex.com");
    }

    @Test
    void testToString() {
        assertEquals(storeManagerDTO.toString(), storeManagerDTO.name);
    }
}