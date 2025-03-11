package pt.ipp.isep.dei.esoft.project.domain.client;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;



public class OwnerTest {
    @Test
    void testOwner() {
        Owner owner = new Owner();
        assertTrue(owner instanceof Owner);
    }
}