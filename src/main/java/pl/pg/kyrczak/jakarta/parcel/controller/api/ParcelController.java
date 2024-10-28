package pl.pg.kyrczak.jakarta.parcel.controller.api;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import pl.pg.kyrczak.jakarta.parcel.dto.GetParcelResponse;
import pl.pg.kyrczak.jakarta.parcel.dto.GetParcelsResponse;
import pl.pg.kyrczak.jakarta.parcel.dto.PatchParcelRequest;
import pl.pg.kyrczak.jakarta.parcel.dto.PutParcelRequest;

import java.io.InputStream;
import java.util.UUID;

@Path("")
public interface ParcelController {

    @GET
    @Path("/parcels")
    @Produces(MediaType.APPLICATION_JSON)
    GetParcelsResponse getParcels();

    @GET
    @Path("/warehouses/{uuid}/parcels")
    @Produces(MediaType.APPLICATION_JSON)
    GetParcelsResponse getWarehouseParcels(@PathParam("uuid") UUID uuid);

    @GET
    @Path("/clients/{uuid}/parcels")
    @Produces(MediaType.APPLICATION_JSON)
    GetParcelsResponse getClientParcels(@PathParam("uuid") UUID uuid);

    @GET
    @Path("/parcels/{uuid}")
    @Produces(MediaType.APPLICATION_JSON)
    GetParcelResponse getParcel(@PathParam("uuid") UUID uuid);

    @PUT
    @Path("/parcels/{uuid}")
    @Consumes(MediaType.APPLICATION_JSON)
    void putParcel(@PathParam("uuid") UUID uuid, PutParcelRequest request);

    @PATCH
    @Path("/parcels/{uuid}")
    @Consumes(MediaType.APPLICATION_JSON)
    void patchParcel(@PathParam("uuid") UUID uuid, PatchParcelRequest request);

    @DELETE
    @Path("/parcels/{uuid}")
    void deleteParcel(@PathParam("uuid") UUID uuid);

    @GET
    @Path("/parcels/{uuid}/image")
    @Produces("image/png")
    byte[] getParcelImage(@PathParam("uuid") UUID uuid);

    @PUT
    @Path("/parcels/{uuid}/image")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    void putParcelImage(
            @PathParam("uuid") UUID uuid,
            @SuppressWarnings("RestParamTypeInspection") @FormParam("image") InputStream image
    );

    @DELETE
    @Path("/parcels/{uuid}/image")
    void deleteParcelImage(@PathParam("uuid") UUID uuid);

    @PATCH
    @Path("/parcels/{uuid}/image")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    void patchParcelImage(
            @PathParam("uuid") UUID uuid,
            @SuppressWarnings("RestParamTypeInspection") @FormParam("image") InputStream image
    );
}
