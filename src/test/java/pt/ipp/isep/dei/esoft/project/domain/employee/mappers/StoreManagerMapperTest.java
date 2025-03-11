package pt.ipp.isep.dei.esoft.project.domain.employee.mappers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pt.ipp.isep.dei.esoft.project.domain.employee.StoreManager;
import pt.ipp.isep.dei.esoft.project.domain.employee.mappers.dto.StoreManagerDTO;
import pt.ipp.isep.dei.esoft.project.util.error.ErrorOptional;
import pt.isep.lei.esoft.auth.domain.model.Email;

import static org.junit.jupiter.api.Assertions.assertFalse;

class StoreManagerMapperTest {

    private StoreManager storeManager;
    private StoreManagerDTO storeManagerDTO;
    private final StoreManagerMapper mapper = new StoreManagerMapper();

    @BeforeEach
    void beforeEach() {
        // -----Domain------
        storeManager = new StoreManager();
        storeManager.setName("John");
        storeManager.setAddress("Adress");
        storeManager.setEmail(new Email("ex@ex.com"));
        storeManager.setTaxNumber("123-12-1234");
        storeManager.setPhoneNumber("123-123-1234");
        storeManager.setPassportNumber(123456789);

        // -----DTO------
        storeManagerDTO = new StoreManagerDTO("John", 123456789, "123-123-1234", "123-12-1234", "adress", "ex@ex.com");
    }

    @Test
    void toDTO() {
        // valid
        ErrorOptional<StoreManagerDTO> dto = mapper.toDTO(storeManager);
        assertFalse(dto.hasError());

        // invalid - null
        dto = mapper.toDTO(null);
        assert (dto.hasError());
    }

    @Test
    void toDomain() {
        // valid
        ErrorOptional<StoreManager> domain = mapper.toDomain(storeManagerDTO);
        assertFalse(domain.hasError());

        // invalid - null
        domain = mapper.toDomain(null);
        assert (domain.hasError());
    }
}