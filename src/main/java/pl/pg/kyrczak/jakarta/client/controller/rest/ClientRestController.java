package pl.pg.kyrczak.jakarta.client.controller.rest;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import lombok.SneakyThrows;
import pl.pg.kyrczak.jakarta.client.controller.api.ClientController;
import pl.pg.kyrczak.jakarta.client.dto.GetClientResponse;
import pl.pg.kyrczak.jakarta.client.dto.GetClientsResponse;
import pl.pg.kyrczak.jakarta.client.dto.PatchClientRequest;
import pl.pg.kyrczak.jakarta.client.dto.PutClientRequest;
import pl.pg.kyrczak.jakarta.client.service.ClientService;
import pl.pg.kyrczak.jakarta.component.DtoFunctionFactory;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.NotFoundException;

import java.util.UUID;

@Path("")
public class ClientRestController implements ClientController {

    private final ClientService service;
    private final DtoFunctionFactory factory;

    private final UriInfo uriInfo;

    private HttpServletResponse response;

    @Context
    public void setResponse(HttpServletResponse response) {
        this.response = response;
    }

    @Inject
    public ClientRestController(ClientService service, DtoFunctionFactory factory,
                                @SuppressWarnings("CdiInjectionPointsInspection") UriInfo uriInfo) {
        this.service = service;
        this.factory = factory;
        this.uriInfo = uriInfo;
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
    @SneakyThrows
    public void putClient(UUID uuid, PutClientRequest request) {
        try {
            service.create(factory.requestToClientFunction().apply(uuid,request));
            response.setHeader("Location", uriInfo.getBaseUriBuilder()
                    .path(ClientController.class, "getClient")
                    .build(uuid)
                    .toString());
            throw new WebApplicationException(Response.Status.CREATED);
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
