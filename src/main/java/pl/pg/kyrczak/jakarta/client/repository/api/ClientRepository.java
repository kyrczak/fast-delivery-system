package pl.pg.kyrczak.jakarta.client.repository.api;

import pl.pg.kyrczak.jakarta.client.entity.Client;
import pl.pg.kyrczak.jakarta.repository.api.Repository;

import java.util.Optional;
import java.util.UUID;

public interface ClientRepository extends Repository<Client, UUID> {
    Optional<Client> findByLogin(String login);
}
