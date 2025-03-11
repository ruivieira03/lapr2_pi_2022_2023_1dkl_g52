package pt.ipp.isep.dei.esoft.project.domain.client.notifications.mappers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pt.ipp.isep.dei.esoft.project.domain.client.Client;
import pt.ipp.isep.dei.esoft.project.domain.client.mappers.dto.ClientDTO;
import pt.ipp.isep.dei.esoft.project.domain.client.notifications.Message;
import pt.ipp.isep.dei.esoft.project.domain.client.notifications.Notification;
import pt.ipp.isep.dei.esoft.project.domain.client.notifications.mappers.dto.MessageDTO;
import pt.ipp.isep.dei.esoft.project.domain.client.notifications.mappers.dto.NotificationDTO;
import pt.ipp.isep.dei.esoft.project.repository.ClientRepository;
import pt.ipp.isep.dei.esoft.project.repository.Repositories;
import pt.ipp.isep.dei.esoft.project.util.error.ErrorOptional;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

class NotificationMapperTest {

    private NotificationDTO notificationDTO;
    private Notification notification;
    private final NotificationMapper mapper = new NotificationMapper();

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
        Message message = new Message();
        message.setAuthor(client);
        message.setDate(LocalDateTime.now());
        message.setMessage("Hello I'm under the water");
        message.setId(1);

        // Notification
        notification = new Notification();
        notification.addMessage(message);


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
        MessageDTO messageDTO = new MessageDTO();
        messageDTO.author = clientDTO;
        messageDTO.date = LocalDateTime.now();
        messageDTO.message = "Hello I'm under the water!";

        notificationDTO = new NotificationDTO();
        notificationDTO.messages.add(messageDTO);

    }

    @Test
    void toDTO() {
        // valid
        ErrorOptional<NotificationDTO> dto = mapper.toDTO(notification);
        assertFalse(dto.hasError());
        // all atributtes must be equal
        assertEquals(dto.get().messages.size(), notification.getMessages().size());

        // test null
        dto = mapper.toDTO(null);
        assert (dto.hasError());
    }

    @Test
    void toDomain() {
        // valid
        ErrorOptional<Notification> domain = mapper.toDomain(notificationDTO);
        assertFalse(domain.hasError());
        // all atributes must be equal
        assertEquals(notificationDTO.messages.size(), domain.get().getMessages().size());

        // test null
        domain = mapper.toDomain(null);
        assert (domain.hasError());


    }
}