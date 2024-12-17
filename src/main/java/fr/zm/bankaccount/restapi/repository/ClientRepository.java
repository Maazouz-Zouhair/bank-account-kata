package fr.zm.bankaccount.restapi.repository;

import fr.zm.bankaccount.account.Client;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Repository;

@Repository
public class ClientRepository {
    private final Map<String, Client> clients = new HashMap<>();

    public void save(Client client) {
        clients.put(client.getClientId(), client);
    }

    public Optional<Client> findById(String clientId) {
        return Optional.ofNullable(clients.get(clientId));
    }
}
