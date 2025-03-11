package pt.ipp.isep.dei.esoft.project.domain.client.notifications;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pt.ipp.isep.dei.esoft.project.domain.client.Client;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;

class NotificationTest {

    private Notification notification;

    @BeforeEach
    void beforeEach() {
        notification = new Notification();
    }

    @Test
    void addMessage() {
        // valid message
        // client (author)
        Client client = new Client();
        client.setName("John");
        client.setPassportNumber(123456789);
        client.setPhoneNumber("123-123-1234");
        client.setTaxNumber("123-12-1234");
        client.setAddress("Adress");
        client.setEmail("email@ex.com");

        // message
        Message message = new Message();
        message.setAuthor(client);
        message.setDate(LocalDateTime.now());
        message.setMessage("Hello I'm under the water");

        assert (notification.addMessage(message).success());

        // null message
        assertFalse(notification.addMessage(null).success());

        // invalid message
        assertFalse(notification.addMessage(new Message()).success());
    }

    @Test
    void getMessages() {
        assert (notification.getMessages().size() == 0);
    }

    @Test
    void setMessages() {
        List<Message> list = new ArrayList<>();
        list.add(new Message());
        list.add(new Message());
        notification.setMessages(list);
        assert (notification.getMessages().size() == 2);
    }
}