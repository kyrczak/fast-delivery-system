package pl.pg.kyrczak.jakarta.client.service;

import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.ejb.LocalBean;
import jakarta.ejb.Stateless;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import lombok.NoArgsConstructor;
import pl.pg.kyrczak.jakarta.client.entity.Client;
import pl.pg.kyrczak.jakarta.client.entity.ClientRoles;
import pl.pg.kyrczak.jakarta.client.repository.api.ClientRepository;
import jakarta.security.enterprise.identitystore.Pbkdf2PasswordHash;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@LocalBean
@Stateless
@NoArgsConstructor(force = true)
public class ClientService {
    private final ClientRepository repository;
    private final Pbkdf2PasswordHash passwordHash;

    @Inject
    public ClientService(ClientRepository repository,
                         @SuppressWarnings("CdiInjectionPointsInspection") Pbkdf2PasswordHash passwordHash) {
        this.repository = repository;
        this.passwordHash = passwordHash;
    }

    @RolesAllowed(ClientRoles.ADMIN)
    public List<Client> findAll() {
        return repository.findAll();
    }

    @RolesAllowed(ClientRoles.ADMIN)
    public Optional<Client> find(UUID uuid) {
        return repository.find(uuid);
    }

    @RolesAllowed(ClientRoles.ADMIN)
    public Optional<Client> find(String login) {
        return repository.findByLogin(login);
    }

    @PermitAll
    public void create(Client client) {
        if(repository.findByLogin(client.getLogin()).isPresent()) {
            throw new IllegalArgumentException("User with this login already exists");
        }
        client.setPassword(passwordHash.generate(client.getPassword().toCharArray()));
        repository.create(client);
    }

    @PermitAll
    public boolean verify(String login, String password) {
        return find(login)
                .map(client -> passwordHash.verify(password.toCharArray(), client.getPassword()))
                .orElse(false);
    }

    @RolesAllowed(ClientRoles.ADMIN)
    public void update(Client client) {
        repository.update(client);
    }

    @RolesAllowed(ClientRoles.ADMIN)
    public void delete(UUID uuid) {
        repository.delete(repository.find(uuid).orElseThrow());
    }

}
