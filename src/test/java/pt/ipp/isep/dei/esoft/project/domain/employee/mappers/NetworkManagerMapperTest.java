package pt.ipp.isep.dei.esoft.project.domain.employee.mappers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pt.ipp.isep.dei.esoft.project.domain.employee.NetworkManager;
import pt.ipp.isep.dei.esoft.project.domain.employee.mappers.dto.NetworkManagerDTO;
import pt.ipp.isep.dei.esoft.project.util.error.ErrorOptional;
import pt.isep.lei.esoft.auth.domain.model.Email;

import static org.junit.jupiter.api.Assertions.assertFalse;

class NetworkManagerMapperTest {

    private NetworkManager networkManager;
    private NetworkManagerDTO networkManagerDTO;
    private final NetworkManagerMapper mapper = new NetworkManagerMapper();

    @BeforeEach
    void beforeEach() {
        // -----Domain-----
        networkManager = new NetworkManager();
        networkManager.setName("John");
        networkManager.setAddress("Adress");
        networkManager.setEmail(new Email("ex@ex.com"));
        networkManager.setTaxNumber("123-12-1234");
        networkManager.setPhoneNumber("123-123-1234");
        networkManager.setPassportNumber(123456789);

        // ------DTO------
        networkManagerDTO = new NetworkManagerDTO("John", 123456789, "123-123-1234", "123-12-1234", "adress", "ex@ex.com");
    }


    @Test
    void toDTO() {
        // valid
        ErrorOptional<NetworkManagerDTO> dto = mapper.toDTO(networkManager);
        assertFalse(dto.hasError());

        // invalid - null
        dto = mapper.toDTO(null);
        assert (dto.hasError());
    }

    @Test
    void toDomain() {
        // valid
        ErrorOptional<NetworkManager> domain = mapper.toDomain(networkManagerDTO);
        assertFalse(domain.hasError());

        // invalid - null
        domain = mapper.toDomain(null);
        assert (domain.hasError());
    }
}