package pt.ipp.isep.dei.esoft.project.domain.client.mappers.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ClientDTOTest {

    @Test
    void test() {

        assert (new ClientDTO() instanceof ClientDTO);

        ClientDTO clientDTO = new ClientDTO("name", 123456789, "123-123-1234", "123-12-1234", "", "ex@ex.com");
        assert(clientDTO instanceof ClientDTO);
    }

}