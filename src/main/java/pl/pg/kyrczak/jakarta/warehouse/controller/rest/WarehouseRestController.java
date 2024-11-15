package pl.pg.kyrczak.jakarta.warehouse.controller.rest;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.transaction.TransactionalException;
import lombok.extern.java.Log;
import pl.pg.kyrczak.jakarta.component.DtoFunctionFactory;
import pl.pg.kyrczak.jakarta.parcel.service.ParcelService;
import pl.pg.kyrczak.jakarta.warehouse.controller.api.WarehouseController;
import pl.pg.kyrczak.jakarta.warehouse.dto.GetWarehouseResponse;
import pl.pg.kyrczak.jakarta.warehouse.dto.GetWarehousesResponse;
import pl.pg.kyrczak.jakarta.warehouse.dto.PatchWarehouseRequest;
import pl.pg.kyrczak.jakarta.warehouse.dto.PutWarehouseRequest;
import pl.pg.kyrczak.jakarta.warehouse.service.WarehouseService;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.NotFoundException;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import lombok.SneakyThrows;

import java.util.UUID;
import java.util.logging.Level;

@Path("")
@Log
public class WarehouseRestController implements WarehouseController {
    private final WarehouseService service;
    private final DtoFunctionFactory factory;
    private final ParcelService parcelService;

    private final UriInfo uriInfo;

    private HttpServletResponse response;

    @Context
    public void setResponse(HttpServletResponse response) {
        this.response = response;
    }
    @Inject
    public WarehouseRestController(WarehouseService service,
                                   ParcelService parcelService,
                                   DtoFunctionFactory factory,
                                   @SuppressWarnings("CdiInjectionPointsInspection") UriInfo uriInfo
    ) {
        this.service = service;
        this.factory = factory;
        this.parcelService = parcelService;
        this.uriInfo = uriInfo;
    }
    @Override
    public GetWarehousesResponse getWarehouses() {
        return factory.warehousesToResponseFunction().apply(service.findAll());
    }

    @Override
    public GetWarehouseResponse getWarehouse(UUID uuid) {
        return service.find(uuid)
                .map(factory.warehouseToResponseFunction())
                .orElseThrow(NotFoundException::new);
    }

    @Override
    @SneakyThrows
    public void putWarehouse(UUID uuid, PutWarehouseRequest request) {
        try {
            service.create(factory.requestToWarehouseFunction().apply(uuid,request));
            response.setHeader("Location", uriInfo.getBaseUriBuilder()
                    .path(WarehouseController.class, "getWarehouse")
                    .build(uuid)
                    .toString());
            throw new WebApplicationException(Response.Status.CREATED);
        } catch (IllegalArgumentException ex) {
            throw new BadRequestException();
        }

    }

    @Override
    public void patchWarehouse(UUID uuid, PatchWarehouseRequest request) {
        service.find(uuid).ifPresentOrElse(
                entity -> service.update(factory.updateWarehouseWithRequestFunction().apply(entity,request)),
                () -> {
                    throw new NotFoundException();
                }
        );
    }

    @Override
    public void deleteWarehouse(UUID uuid) {
        service.find(uuid).ifPresentOrElse(
                entity -> {
                    parcelService.findAllByWarehouse(uuid).ifPresentOrElse(
                            parcels -> parcels.forEach(parcel -> parcelService.delete(parcel.getUuid())),
                            () -> {
                                throw new NotFoundException();
                            }
                    );
                    service.delete(uuid);
                },
                () -> {
                    throw new NotFoundException();
                }
        );
    }
}
