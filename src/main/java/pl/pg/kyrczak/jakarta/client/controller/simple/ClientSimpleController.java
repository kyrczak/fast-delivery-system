package pl.pg.kyrczak.jakarta.client.controller.simple;

import pl.pg.kyrczak.jakarta.client.controller.api.ClientController;
import pl.pg.kyrczak.jakarta.client.dto.GetClientResponse;
import pl.pg.kyrczak.jakarta.client.dto.GetClientsResponse;
import pl.pg.kyrczak.jakarta.client.dto.PatchClientRequest;
import pl.pg.kyrczak.jakarta.client.dto.PutClientRequest;
import pl.pg.kyrczak.jakarta.client.service.ClientService;
import pl.pg.kyrczak.jakarta.component.DtoFunctionFactory;
import pl.pg.kyrczak.jakarta.controller.servlet.exception.BadRequestException;
import pl.pg.kyrczak.jakarta.controller.servlet.exception.NotFoundException;

import java.util.UUID;

public class ClientSimpleController implements ClientController {

    private final ClientService service;
    private final DtoFunctionFactory factory;

    public ClientSimpleController(ClientService service, DtoFunctionFactory factory) {
        this.service = service;
        this.factory = factory;
    }
    @Override
    public GetClientsResponse getClients() {
        return factory.clientsToResponseFunction().apply(service.findAll());
    }

    @Override
    public GetClientResponse getClient(UUID uuid) {
        return service.find(uuid)
                .map(factory.clientToResponseFunction())
                .orElseThrow(NotFoundException::new);
    }

    @Override
    public void putClient(UUID uuid, PutClientRequest request) {
        try {
            service.create(factory.requestToClientFunction().apply(uuid,request));
        } catch (IllegalArgumentException ex) {
            throw new BadRequestException(ex);
        }
    }

    @Override
    public void patchClient(UUID uuid, PatchClientRequest request) {
        service.find(uuid).ifPresentOrElse(
                entity -> service.update(factory.updateClientWithRequestFucntion().apply(entity,request)),
                () -> {
                    throw new NotFoundException();
                }
        );
    }

    @Override
    public void deleteClient(UUID uuid) {
        service.find(uuid).ifPresentOrElse(
                entity -> service.delete(uuid),
                () -> {
                    throw new NotFoundException();
                }
        );
    }
}
