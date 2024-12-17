package fr.zm.bankaccount.unitTest;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import fr.zm.bankaccount.account.Client;

public class ClientUnitTest {

    @Test
    public void shouldCreateClient() {
        Client client = new Client("C001", "John", "Doe");
        assertEquals("C001", client.getClientId());
        assertEquals("John", client.getFirstName());
        assertEquals("Doe", client.getLastName());
    }
}