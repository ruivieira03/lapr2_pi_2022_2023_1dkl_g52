package pt.ipp.isep.dei.esoft.project.domain.client.notifications;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pt.ipp.isep.dei.esoft.project.domain.client.Client;
import pt.ipp.isep.dei.esoft.project.domain.user.User;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class MessageTest {

    private Message message;


    @BeforeEach
    void beforeEach() {
        // client (author)
        Client client = new Client();
        client.setName("John");
        client.setPassportNumber(123456789);
        client.setPhoneNumber("123-123-1234");
        client.setTaxNumber("123-12-1234");
        client.setAddress("Adress");
        client.setEmail("email@ex.com");


        message = new Message();
        message.setAuthor(client);
        message.setDate(LocalDateTime.now());
        message.setMessage("Hello I'm under the water");
        message.setId(1);
    }

    @Test
    void getId() {
        int expected = 1;

        assertEquals(expected, message.getId());
    }

    @Test
    void getDate() {
        assert (message.getDate() instanceof LocalDateTime);
        assertNotNull(message.getDate());
    }

    @Test
    void getMessage() {
        String expected = "Hello I'm under the water";

        assertEquals(expected, message.getMessage());
    }

    @Test
    void getAuthor() {
        assert (message.getAuthor() instanceof User);
        assertNotNull(message.getAuthor());
    }

    @Test
    void setAuthor() {
        Client c = new Client();
        c.setName("Storm Tropper");

        message.setAuthor(c);

        assertEquals(message.getAuthor(), c);
    }

    @Test
    void setDate() {
        LocalDateTime date = LocalDateTime.of(10, 1, 23, 12, 12);

        message.setDate(date);

        assertEquals(message.getDate(), date);
    }

    @Test
    void setId() {
        int id = 2;

        message.setId(2);

        assertEquals(message.getId(), id);
    }

    @Test
    void setMessage() {
        String s = "HERE COMES THE PARTY!!";

        message.setMessage(s);

        assertEquals(message.getMessage(), s);
    }

    @Test
    void isValid() {
        // valid
        assert (message.isValid().success());


        // test message
        message.setMessage(null);
        assertFalse(message.isValid().success());


        message.setMessage("");
        assertFalse(message.isValid().success());

        message.setMessage("Hello I'm under the water");


        // test date
        message.setDate(null);
        assertFalse(message.isValid().success());

        message.setDate(LocalDateTime.now());


        // test author
        message.getAuthor().setEmail(null);
        assertFalse(message.isValid().success());

        message.setAuthor(null);
        assertFalse(message.isValid().success());
    }

    @Test
    void testToString() {
    }
}