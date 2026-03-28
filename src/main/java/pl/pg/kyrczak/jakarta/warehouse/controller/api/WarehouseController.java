package pl.pg.kyrczak.jakarta.warehouse.controller.api;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import pl.pg.kyrczak.jakarta.warehouse.dto.GetWarehouseResponse;
import pl.pg.kyrczak.jakarta.warehouse.dto.GetWarehousesResponse;
import pl.pg.kyrczak.jakarta.warehouse.dto.PatchWarehouseRequest;
import pl.pg.kyrczak.jakarta.warehouse.dto.PutWarehouseRequest;

import java.util.UUID;

@Path("")
public interface WarehouseController {
    @GET
    @Path("/warehouses")
    @Produces(MediaType.APPLICATION_JSON)
    GetWarehousesResponse getWarehouses();

    @GET
    @Path("/warehouses/{uuid}")
    @Produces(MediaType.APPLICATION_JSON)
    GetWarehouseResponse getWarehouse(@PathParam("uuid") UUID uuid);

    @PUT
    @Path("/warehouses/{uuid}")
    @Produces(MediaType.APPLICATION_JSON)
    void putWarehouse(@PathParam("uuid") UUID uuid, PutWarehouseRequest request);

    @PATCH
    @Path("/warehouses/{uuid}")
    @Produces(MediaType.APPLICATION_JSON)
    void patchWarehouse(@PathParam("uuid") UUID uuid, PatchWarehouseRequest request);

    @DELETE
    @Path("/warehouses/{uuid}")
    void deleteWarehouse(@PathParam("uuid") UUID uuid);

}
