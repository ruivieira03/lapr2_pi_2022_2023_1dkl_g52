package pt.ipp.isep.dei.esoft.project.repository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pt.ipp.isep.dei.esoft.project.domain.client.Client;
import pt.ipp.isep.dei.esoft.project.domain.client.mappers.dto.ClientDTO;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ClientRepositoryTest {

    private ClientRepository clientRepository;

    @BeforeEach
    void beforeEach() {
        clientRepository = new ClientRepository();
        clientRepository.createClient("Anne", 123123, 123123, 123123, "adress", "anne@ex.com");
    }

    @Test
    void addClient() {
        Client invalidGlobalClient = clientRepository.clients.get(0);
        assert (!clientRepository.addClient(invalidGlobalClient));


        Client validClient = new Client("name", 123123, 123123, 123123, "adress", "valid@email.com");
        assert (clientRepository.addClient(validClient));
    }

    @Test
    void getClientFromEmail() {
        assert (clientRepository.getClientFromEmail("anne@ex.com").get().getName().equals("Anne"));
    }

    @Test
    void testGetClientFromEmail() {
        Email email = new Email("anne@ex.com");
        assert (clientRepository.getClientFromEmail(email).get().getName().equals("Anne"));

    }

    @Test
    void createClient() {
        assert (! clientRepository.createClient("name", 123, 123, 123, "adress", "illegal||email"));

        clientRepository.createClient("Tyler", 123123, 123123, 123123, "adress", "email@ex.com");
        assert (clientRepository.clients.size() == 2);
    }


    @Test
    void addClientThroughData() {
        clientRepository = new ClientRepository();

        List<ClientDTO> clientData = new ArrayList<>();

        clientData.add(new ClientDTO ("Dummy", 123123123, "123123123", "123123123", "Dummy Street", "dummy@test.com"));
        clientData.add(new ClientDTO ("Dummy2", 123123124, "123123124", "123123124", "Dummy2 Street", "dummy2@test.com"));
        clientData.add(new ClientDTO ("Dummy3", 123123125, "123123125", "123123125", "Dummy3 Street", "dummy3@test.com"));
        clientData.add(new ClientDTO ("Dummy4", 123123126, "123123126", "123123126", "Dummy4 Street", "dummy4@test.com"));
        clientData.add(new ClientDTO ("Dummy5", 123123127, "123123127", "123123127", "Dummy5 Street", "dummy5@test.com"));

        List<Client> result = clientRepository.addClientThroughData(clientData);

        Assertions.assertEquals(5, result.size());
        Assertions.assertEquals("Dummy", result.get(0).getName());
        Assertions.assertEquals("123123127", result.get(4).getPhoneNumber());
    }


    @Test
    void addClientWithNullValues() {
        clientRepository = new ClientRepository();

        Assertions.assertThrows(IllegalArgumentException.class, () -> clientRepository.addClientThroughData(new ArrayList<>(List.of(new ClientDTO(null, 0, null, null, null, null)))));
    }

    @Test
    void doesntAddStoreThroughEmptyData() {
        clientRepository = new ClientRepository();
        List<ClientDTO> clientData = new ArrayList<>();

        clientRepository.addClientThroughData(clientData);

        assertEquals(0, clientRepository.getClients().size());
    }

    @Test
    void doesntAddStoreThroughInvalidData() {
        clientRepository = new ClientRepository();
        List<ClientDTO> clientData = new ArrayList<>();

        clientData.add(new ClientDTO());

        assertEquals(0, clientRepository.getClients().size());
    }
}