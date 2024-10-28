package pl.pg.kyrczak.jakarta.client.controller.api;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import pl.pg.kyrczak.jakarta.client.dto.GetClientResponse;
import pl.pg.kyrczak.jakarta.client.dto.GetClientsResponse;
import pl.pg.kyrczak.jakarta.client.dto.PatchClientRequest;
import pl.pg.kyrczak.jakarta.client.dto.PutClientRequest;

import java.util.UUID;

@Path("")
public interface ClientController {
    @GET
    @Path("/clients")
    @Produces(MediaType.APPLICATION_JSON)
    GetClientsResponse getClients();
    @GET
    @Path("/clients/{uuid}")
    @Produces(MediaType.APPLICATION_JSON)
    GetClientResponse getClient(@PathParam("uuid") UUID uuid);

    @PUT
    @Path("/clients/{uuid}")
    @Consumes(MediaType.APPLICATION_JSON)
    void putClient(@PathParam("uuid") UUID uuid, PutClientRequest request);

    @PATCH
    @Path("/clients/{uuid}")
    @Consumes(MediaType.APPLICATION_JSON)
    void patchClient(@PathParam("uuid") UUID uuid, PatchClientRequest request);

    @DELETE
    @Path("/clients/{uuid}")
    void deleteClient(@PathParam("uuid") UUID uuid);
}
