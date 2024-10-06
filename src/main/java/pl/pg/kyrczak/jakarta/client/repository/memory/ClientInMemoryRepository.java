package pl.pg.kyrczak.jakarta.client.repository.memory;

import pl.pg.kyrczak.jakarta.client.entity.Client;
import pl.pg.kyrczak.jakarta.client.repository.api.ClientRepository;
import pl.pg.kyrczak.jakarta.datastore.component.DataStore;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class ClientInMemoryRepository implements ClientRepository {
    private final DataStore store;

    public ClientInMemoryRepository(DataStore store) {
        this.store = store;
    }

    @Override
    public Optional<Client> find(UUID id) {
        return store.findAllClients().stream()
                .filter(client -> client.getUuid().equals(id))
                .findFirst();
    }

    @Override
    public List<Client> findAll() {
        return store.findAllClients();
    }

    @Override
    public void create(Client entity) {
        store.createClient(entity);
    }

    @Override
    public void delete(Client entity) {
        throw new UnsupportedOperationException("Not implemented. ");
    }

    @Override
    public void update(Client entity) {
        store.updateClient(entity);
    }

    @Override
    public Optional<Client> findByLogin(String login) {
        return store.findAllClients().stream()
                .filter(client -> client.getLogin().equals(login))
                .findFirst();
    }
}
