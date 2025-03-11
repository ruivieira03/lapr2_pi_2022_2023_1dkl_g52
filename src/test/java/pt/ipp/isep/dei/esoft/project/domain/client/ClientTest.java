package pt.ipp.isep.dei.esoft.project.domain.client;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pt.ipp.isep.dei.esoft.project.domain.client.notifications.Message;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertFalse;

class ClientTest {

    private Client client;
    @BeforeEach
    void beforeEach() {
        client = new Client();
        client.setName("John");
        client.setPassportNumber(123456789);
        client.setPhoneNumber("123-123-1234");
        client.setTaxNumber("123-12-1234");
        client.setAddress("Adress");
        client.setEmail("email@ex.com");
    }


    @Test
    void addMessage() {
        // failed message empty
        Message message = new Message();
        assert (!client.addMessage(message).success());

        // passes
        message.setMessage("Hello how are you?");
        message.setAuthor(client);
        message.setDate(LocalDateTime.now());
        assert (client.addMessage(message).success());
    }

    @Test
    void getNotification() {
        assert (client.getNotification().getMessages().size() == 0);
    }

    @Test
    void isValid() {
        // valid
        assert (client.isValid().success());

        // invalid taxNumber
        client.setTaxNumber("1234");
        assertFalse(client.isValid().success());
        client.setTaxNumber("123-12-1234");

        // invalid name
        client.setName("");
        assertFalse(client.isValid().success());
        client.setName("John");

        // invalid passportNumber
        client.setPassportNumber(1234);
        assertFalse(client.isValid().success());
        client.setPassportNumber(123456789);

        // invalid phone number
        client.setPhoneNumber("123456789");
        assertFalse(client.isValid().success());
        client.setPhoneNumber("123-123-1234");

        // invalid email
        client.setEmail(null);
        assertFalse(client.isValid().success());
    }

    @Test
    void testEquals() {
        // test null
        assertFalse(client.equals(null));

        // test same reference
        assert (client.equals(client));

        // test same obj type but diff paramrters
        Client c2 = new Client();
        assertFalse(client.equals(c2));

        // test same parameters
        c2.setName("John");
        c2.setPassportNumber(123456789);
        c2.setPhoneNumber("123-123-1234");
        c2.setTaxNumber("123-12-1234");
        c2.setAddress("Adress");
        c2.setEmail("email@ex.com");
        assert (client.equals(c2));
    }
}