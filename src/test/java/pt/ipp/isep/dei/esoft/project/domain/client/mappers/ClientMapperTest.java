package pt.ipp.isep.dei.esoft.project.domain.client.mappers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pt.ipp.isep.dei.esoft.project.domain.client.Client;
import pt.ipp.isep.dei.esoft.project.domain.client.Owner;
import pt.ipp.isep.dei.esoft.project.domain.client.mappers.dto.ClientDTO;
import pt.ipp.isep.dei.esoft.project.domain.client.mappers.dto.OwnerDTO;
import pt.ipp.isep.dei.esoft.project.util.error.ErrorOptional;

import static org.junit.jupiter.api.Assertions.assertFalse;

class ClientMapperTest {

    private Client client;
    private Owner owner;
    private ClientDTO clientDTO;
    private OwnerDTO ownerDTO;
    private final ClientMapper mapper = new ClientMapper();

    @BeforeEach
    void beforeEach() {
        // -----Domain-----
        client = new Client();
        client.setName("name");
        client.setName("John");
        client.setPassportNumber(123456789);
        client.setPhoneNumber("123-123-1234");
        client.setTaxNumber("123-12-1234");
        client.setAddress("Adress");
        client.setEmail("email@ex.com");

        owner = new Owner();
        owner.setName("name");
        owner.setName("John");
        owner.setPassportNumber(123456789);
        owner.setPhoneNumber("123-123-1234");
        owner.setTaxNumber("123-12-1234");
        owner.setAddress("Adress");
        owner.setEmail("email@ex.com");

        // -----DTO-----
        clientDTO = new ClientDTO();
        clientDTO.name = "John";
        clientDTO.passportNumber = 123456789;
        clientDTO.phoneNumber = "123-123-1234";
        clientDTO.taxNumber = "123-12-1234";
        clientDTO.address = "Adress";
        clientDTO.email = "email@ex.com";

        ownerDTO = new OwnerDTO();
        ownerDTO.name = "John";
        ownerDTO.passportNumber = 123456789;
        ownerDTO.phoneNumber = "123-123-1234";
        ownerDTO.taxNumber = "123-12-1234";
        ownerDTO.address = "Adress";
        ownerDTO.email = "email@ex.com";

    }

    @Test
    void toDTO() {
        // valid - client
        ErrorOptional<ClientDTO> dto = mapper.toDTO(client);
        assertFalse(dto.hasError());

        // valid - owner
        dto = mapper.toDTO(owner);
        assertFalse(dto.hasError());

        // test null
        dto = mapper.toDTO(null);
        assert (dto.hasError());
    }

    @Test
    void toDomain() {
        // valid - client
        ErrorOptional<Client> domain = mapper.toDomain(clientDTO);
        assertFalse(domain.hasError());

        // valid - owner
        domain = mapper.toDomain(ownerDTO);
        assertFalse(domain.hasError());

        // test null
        domain = mapper.toDomain(null);
        assert (domain.hasError());
    }
}