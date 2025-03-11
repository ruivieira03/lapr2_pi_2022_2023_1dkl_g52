package pt.ipp.isep.dei.esoft.project.domain.employee.mappers.dto;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class NetworkManagerDTOTest {

    private NetworkManagerDTO networkManagerDTO;

    @BeforeEach
    void beforeEach() {
        networkManagerDTO = new NetworkManagerDTO();
        networkManagerDTO = new NetworkManagerDTO("John", 123456789, "123-123-1234", "123-12-1234", "adress", "ex@ex.com");
    }

    @Test
    void test() {
        assertEquals(networkManagerDTO.toString(), networkManagerDTO.name);
    }

}