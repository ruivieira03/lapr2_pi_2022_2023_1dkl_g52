package pt.ipp.isep.dei.esoft.project.domain.client.notifications.mappers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pt.ipp.isep.dei.esoft.project.domain.client.Client;
import pt.ipp.isep.dei.esoft.project.domain.client.mappers.dto.ClientDTO;
import pt.ipp.isep.dei.esoft.project.domain.client.notifications.Message;
import pt.ipp.isep.dei.esoft.project.domain.client.notifications.mappers.dto.MessageDTO;
import pt.ipp.isep.dei.esoft.project.domain.employee.mappers.dto.AgentDTO;
import pt.ipp.isep.dei.esoft.project.domain.employee.mappers.dto.SystemAdministratorDTO;
import pt.ipp.isep.dei.esoft.project.domain.locations.mappers.dto.StateDTO;
import pt.ipp.isep.dei.esoft.project.domain.store.mappers.dto.StoreDTO;
import pt.ipp.isep.dei.esoft.project.repository.ClientRepository;
import pt.ipp.isep.dei.esoft.project.repository.Repositories;
import pt.ipp.isep.dei.esoft.project.util.error.ErrorOptional;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

class MessageMapperTest {

    private Message message;
    private MessageDTO messageDTO;
    private final MessageMapper mapper = new MessageMapper();

    @BeforeEach
    void beforeEach() {
        // -----Domain-----
        // author
        Client client = new Client();
        client.setName("John");
        client.setPassportNumber(123456789);
        client.setPhoneNumber("123-123-1234");
        client.setTaxNumber("123-12-1234");
        client.setAddress("Adress");
        client.setEmail("email@ex.com");

        // Message
        message = new Message();
        message.setAuthor(client);
        message.setDate(LocalDateTime.now());
        message.setMessage("Hello I'm under the water");
        message.setId(1);


        // -----DTO-----
        // author - must be in repository
        ClientDTO clientDTO = new ClientDTO();
        clientDTO.name = "John";
        clientDTO.passportNumber = 123456789;
        clientDTO.phoneNumber = "123-123-1234";
        clientDTO.taxNumber = "123-12-1234";
        clientDTO.address = "Adress";
        clientDTO.email = "email@ex.com";

        ClientRepository clientRepository = Repositories.getClientRepository();
        clientRepository.createClient(clientDTO);

        // message
        messageDTO = new MessageDTO();
        messageDTO.author = clientDTO;
        messageDTO.date = LocalDateTime.now();
        messageDTO.message = "Hello I'm under the water!";

    }

    @Test
    void toDTO() {
        // valid
        ErrorOptional<MessageDTO> dto = mapper.toDTO(message);
        assertFalse (dto.hasError());
        // all atributes must be equal
        assertEquals(dto.get().author.email, message.getAuthor().getEmail());
        assertEquals(dto.get().message, message.getMessage());
        assertEquals(dto.get().date, message.getDate());
        assertEquals(dto.get().id, message.getId());

        // test null
        dto = mapper.toDTO(null);
        assert (dto.hasError());
    }

    @Test
    void toDomain() {
        // valid
        ErrorOptional<Message> domain = mapper.toDomain(messageDTO);
        assertFalse(domain.hasError());
        // all atributes must be equal
        assertEquals(domain.get().getAuthor().getEmail(), messageDTO.author.email);
        assertEquals(domain.get().getMessage(), messageDTO.message);
        assertEquals(domain.get().getDate(), messageDTO.date);

        // test null
        domain =  mapper.toDomain(null);
        assert (domain.hasError());

        // test author SystemAdministrator
        SystemAdministratorDTO admin = new SystemAdministratorDTO();
        admin.name = "Baby shark";
        admin.email = "Baby@shark.music";
        admin.address = "Ocean";
        admin.passportNumber = 123456789;
        admin.phoneNumber = "123-123-1234";
        admin.taxNumber = "123-12-1234";

        Repositories.getSystemAdministratorRepository().createSystemAdministrator(admin);

        messageDTO.author = admin;
        domain = mapper.toDomain(messageDTO);
        assertFalse (domain.hasError());

        // test author Agent
        AgentDTO agent = new AgentDTO();
        agent.name = "Momma shark";
        agent.email = "Momma@shark.music";
        agent.address = "Sea";
        agent.passportNumber = 123456789;
        agent.phoneNumber = "123-123-1234";
        agent.taxNumber = "123-12-1234";

        StateDTO state = new StateDTO("state", new String[]{"District"}, new String[][]{{"City"}});
        Repositories.getLocationsRepository().createState(state);

        StoreDTO store = new StoreDTO("name", "ex@ex.com", "123-123-1234", "adress", 12345);
        store.state = state;
        store.district = state.districtList.get(0);
        store.city = state.districtList.get(0).cityList.get(0);
        store.agents.add(agent);

        Repositories.getStoreRepository().createStore(store);

        messageDTO.author = agent;
        domain = mapper.toDomain(messageDTO);
        assertFalse(domain.hasError());
    }
}