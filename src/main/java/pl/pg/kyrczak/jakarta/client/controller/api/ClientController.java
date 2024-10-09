package pl.pg.kyrczak.jakarta.client.controller.api;

import pl.pg.kyrczak.jakarta.client.dto.GetClientResponse;
import pl.pg.kyrczak.jakarta.client.dto.GetClientsResponse;
import pl.pg.kyrczak.jakarta.client.dto.PatchClientRequest;
import pl.pg.kyrczak.jakarta.client.dto.PutClientRequest;

import java.util.UUID;

public interface ClientController {
    GetClientsResponse getClients();
    GetClientResponse getClient(UUID uuid);
    void putClient(UUID uuid, PutClientRequest request);
    void patchClient(UUID uuid, PatchClientRequest request);
    void deleteClient(UUID uuid);
}
