package pl.pg.kyrczak.jakarta.parcel.controller.rest;

import jakarta.annotation.security.RolesAllowed;
import jakarta.ejb.EJB;
import jakarta.ejb.EJBAccessException;
import jakarta.ejb.EJBException;
import jakarta.inject.Inject;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.TransactionalException;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import lombok.SneakyThrows;
import lombok.extern.java.Log;
import pl.pg.kyrczak.jakarta.client.entity.ClientRoles;
import pl.pg.kyrczak.jakarta.component.DtoFunctionFactory;
import pl.pg.kyrczak.jakarta.parcel.controller.api.ParcelController;
import pl.pg.kyrczak.jakarta.parcel.dto.GetParcelResponse;
import pl.pg.kyrczak.jakarta.parcel.dto.GetParcelsResponse;
import pl.pg.kyrczak.jakarta.parcel.dto.PatchParcelRequest;
import pl.pg.kyrczak.jakarta.parcel.dto.PutParcelRequest;
import pl.pg.kyrczak.jakarta.parcel.service.ParcelService;
import pl.pg.kyrczak.jakarta.warehouse.service.WarehouseService;


import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;
import java.util.logging.Level;

@Path("")
@Log
@RolesAllowed(ClientRoles.USER)
public class ParcelRestController implements ParcelController {

    private ParcelService service;
    private WarehouseService warehouseService;
    private final DtoFunctionFactory factory;

    private final UriInfo uriInfo;

    private HttpServletResponse response;

    @Context
    public void setResponse(HttpServletResponse response) {
        this.response = response;
    }
    @Inject
    public ParcelRestController(DtoFunctionFactory factory,
                                @SuppressWarnings("CdiInjectionPointsInspection") UriInfo uriInfo
    ) {
        this.factory = factory;
        this.uriInfo = uriInfo;
    }

    @EJB
    public void setService(ParcelService service) {
        this.service = service;
    }

    @EJB
    public void setWarehouseService(WarehouseService warehouseService) {
        this.warehouseService = warehouseService;
    }

    @Override
    public GetParcelsResponse getParcels() {
        return factory.parcelsToResponseFunction().apply(service.findAllForCallerPrincipal());
    }

    @Override
    public GetParcelsResponse getWarehouseParcels(UUID uuid) {
        return factory.parcelsToResponseFunction().apply(service.findAllForCallerPrincipalAndRepository(uuid));
    }

    @Override
    public GetParcelsResponse getClientParcels(UUID uuid) {
        return service.findAllByClient(uuid)
                .map(factory.parcelsToResponseFunction())
                .orElseThrow(NotFoundException::new);
    }

    @Override
    public GetParcelResponse getParcel(UUID uuid, UUID warehouse_uuid) {
        return warehouseService.find(warehouse_uuid)
                .flatMap(warehouse -> service.findForCallerPrincipal(uuid).map(factory.parcelToResponseFunction()))
                .orElseThrow(NotFoundException::new);
    }

    @Override
    @SneakyThrows
    public void putParcel(UUID uuid, UUID warehouse_uuid, PutParcelRequest request) {
        try {
            request.setWarehouse(warehouse_uuid);
            //service.create(factory.requestToParcelFunction().apply(uuid,request));
            service.createForCallerPrincipal(factory.requestToParcelFunction().apply(uuid,request));
            response.setHeader("Location", uriInfo.getBaseUriBuilder()
                    .path(ParcelController.class, "getParcel")
                    .build(uuid, warehouse_uuid)
                    .toString());
            throw new WebApplicationException(Response.Status.CREATED);
        } catch (EJBException ex) {
            if (ex.getCause() instanceof IllegalArgumentException) {
                log.log(Level.WARNING, ex.getMessage(), ex);
                throw new BadRequestException(ex);
            }
            throw ex;
        }
    }

    @Override
    public void patchParcel(UUID uuid, UUID warehouse_uuid, PatchParcelRequest request) {
        service.find(uuid).ifPresentOrElse(
                entity -> {
                    try {
                        service.update(factory.updateParcelWithRequestFunction().apply(entity,request));
                    } catch (EJBAccessException ex) {
                        log.log(Level.WARNING, ex.getMessage(),ex);
                        throw new ForbiddenException(ex.getMessage());
                    }
                },
                () -> {
                    throw new NotFoundException();
                }
        );
    }

    @Override
    public void deleteParcel(UUID uuid, UUID warehouse_uuid) {
        service.find(uuid).ifPresentOrElse(
                entity -> {
                  try {
                      service.delete(uuid);
                  } catch (EJBAccessException ex) {
                      log.log(Level.WARNING, ex.getMessage(),ex);
                      throw new ForbiddenException(ex.getMessage());
                  }
                },
                () -> {
                    throw new NotFoundException();
                }
        );
    }

    @Override
    public byte[] getParcelImage(UUID uuid) {
        try {
            return service.downloadImage(uuid);
        } catch (IOException ex) {

            throw new NotFoundException();
        }
    }

    @Override
    public void putParcelImage(UUID uuid, UUID warehouse_uuid, InputStream image) {
        service.find(uuid).ifPresentOrElse(
                entity -> {
                    try {
                        service.uploadImage(uuid,image);
                    } catch (EJBAccessException ex) {
                        log.log(Level.WARNING, ex.getMessage(), ex);
                        throw new ForbiddenException(ex.getMessage());
                    } catch (IOException e) {
                        throw new NotFoundException(e);
                    }
                    response.setHeader("Location", uriInfo.getBaseUriBuilder()
                            .path(ParcelController.class, "getParcelImage")
                            .build(uuid)
                            .toString());
                    throw new WebApplicationException(Response.Status.CREATED);
                },
                () -> {
                    throw new NotFoundException();
                }
        );
    }

    @Override
    public void deleteParcelImage(UUID uuid, UUID warehouse_uuid) {
        try {
            service.deleteImage(uuid);
        } catch (IOException e) {
            throw new NotFoundException();
        }
    }

    @Override
    public void patchParcelImage(UUID uuid,UUID warehouse_uuid,InputStream image) {
        try {
            service.overwriteImage(uuid, image);
        } catch (IOException e) {
            throw new NotFoundException();
        }
    }

}
