package pl.pg.kyrczak.jakarta.client.service;

import pl.pg.kyrczak.jakarta.client.entity.Client;
import pl.pg.kyrczak.jakarta.client.repository.api.ClientRepository;
import pl.pg.kyrczak.jakarta.crypto.component.Pbkdf2PasswordHash;

import java.util.Optional;
import java.util.UUID;

public class ClientService {
    private final ClientRepository repository;
    private final Pbkdf2PasswordHash passwordHash;

    public ClientService(ClientRepository repository, Pbkdf2PasswordHash passwordHash) {
        this.repository = repository;
        this.passwordHash = passwordHash;
    }

    public Optional<Client> find(UUID uuid) {
        return repository.find(uuid);
    }

    public Optional<Client> find(String login) {
        return repository.findByLogin(login);
    }

    public void create(Client client) {
        client.setPassword(passwordHash.generate(client.getPassword().toCharArray()));
        repository.create(client);
    }

    public boolean verify(String login, String password) {
        return find(login)
                .map(client -> passwordHash.verify(password.toCharArray(), client.getPassword()))
                .orElse(false);
    }

}
