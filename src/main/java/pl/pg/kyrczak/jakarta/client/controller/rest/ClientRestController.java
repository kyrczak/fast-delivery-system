package pl.pg.kyrczak.jakarta.client.controller.rest;

import jakarta.ejb.EJB;
import jakarta.ejb.EJBException;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.TransactionalException;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import lombok.SneakyThrows;
import lombok.extern.java.Log;
import pl.pg.kyrczak.jakarta.authorization.exception.NoPrincipalException;
import pl.pg.kyrczak.jakarta.authorization.exception.NoRolesException;
import pl.pg.kyrczak.jakarta.client.controller.api.ClientController;
import pl.pg.kyrczak.jakarta.client.dto.GetClientResponse;
import pl.pg.kyrczak.jakarta.client.dto.GetClientsResponse;
import pl.pg.kyrczak.jakarta.client.dto.PatchClientRequest;
import pl.pg.kyrczak.jakarta.client.dto.PutClientRequest;
import pl.pg.kyrczak.jakarta.client.service.ClientService;
import pl.pg.kyrczak.jakarta.component.DtoFunctionFactory;

import java.util.UUID;
import java.util.logging.Level;

@Path("")
@Log
public class ClientRestController implements ClientController {

    private ClientService service;
    private final DtoFunctionFactory factory;

    private final UriInfo uriInfo;

    private HttpServletResponse response;

    @Context
    public void setResponse(HttpServletResponse response) {
        this.response = response;
    }

    @Inject
    public ClientRestController(DtoFunctionFactory factory,
                                @SuppressWarnings("CdiInjectionPointsInspection") UriInfo uriInfo) {
        this.factory = factory;
        this.uriInfo = uriInfo;
    }

    @EJB
    public void setService(ClientService service) {
        this.service = service;
    }

    @Override
    public GetClientsResponse getClients() {
        try {
            return factory.clientsToResponseFunction().apply(service.findAll());
        } catch (NoRolesException ex) {
            throw new ForbiddenException();
        } catch (NoPrincipalException ex) {
            throw new NotAuthorizedException("");
        }
    }

    @Override
    public GetClientResponse getClient(UUID uuid) {
        try {
            return service.find(uuid)
                    .map(factory.clientToResponseFunction())
                    .orElseThrow(NotFoundException::new);
        } catch (NoRolesException ex) {
            throw new ForbiddenException();
        } catch (NoPrincipalException ex) {
            throw new NotAuthorizedException("");
        }
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
        } catch (EJBException ex) {
            if (ex.getCause() instanceof IllegalArgumentException) {
                log.log(Level.WARNING, ex.getMessage(), ex);
                throw new BadRequestException(ex);
            }
            throw ex;
        } catch (NoRolesException ex) {
            throw new ForbiddenException();
        } catch (NoPrincipalException ex) {
            throw new NotAuthorizedException("");
        }

    }

    @Override
    public void patchClient(UUID uuid, PatchClientRequest request) {
        try {
            service.find(uuid).ifPresentOrElse(
                    entity -> service.update(factory.updateClientWithRequestFucntion().apply(entity, request)),
                    () -> {
                        throw new NotFoundException();
                    }
            );
        } catch (NoRolesException ex) {
            throw new ForbiddenException();
        } catch (NoPrincipalException ex) {
            throw new NotAuthorizedException("");
        }
    }

    @Override
    public void deleteClient(UUID uuid) {
        try {
            service.find(uuid).ifPresentOrElse(
                    entity -> service.delete(uuid),
                    () -> {
                        throw new NotFoundException();
                    }
            );
        } catch (NoRolesException ex) {
            throw new ForbiddenException();
        } catch (NoPrincipalException ex) {
            throw new NotAuthorizedException("");
        }
    }
}
