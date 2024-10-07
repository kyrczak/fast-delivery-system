package pl.pg.kyrczak.jakarta.warehouse.controller.simple;

import pl.pg.kyrczak.jakarta.component.DtoFunctionFactory;
import pl.pg.kyrczak.jakarta.controller.servlet.exception.BadRequestException;
import pl.pg.kyrczak.jakarta.controller.servlet.exception.NotFoundException;
import pl.pg.kyrczak.jakarta.warehouse.controller.api.WarehouseController;
import pl.pg.kyrczak.jakarta.warehouse.dto.GetWarehouseResponse;
import pl.pg.kyrczak.jakarta.warehouse.dto.GetWarehousesResponse;
import pl.pg.kyrczak.jakarta.warehouse.dto.PatchWarehouseRequest;
import pl.pg.kyrczak.jakarta.warehouse.dto.PutWarehouseRequest;
import pl.pg.kyrczak.jakarta.warehouse.service.WarehouseService;

import java.util.UUID;

public class WarehouseSimpleController implements WarehouseController {
    private final WarehouseService service;
    private final DtoFunctionFactory factory;

    public WarehouseSimpleController(WarehouseService service, DtoFunctionFactory factory) {
        this.service = service;
        this.factory = factory;
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
    public void putWarehouse(UUID uuid, PutWarehouseRequest request) {
        try {
            service.create(factory.requestToWarehouseFunction().apply(uuid,request));
        } catch (IllegalArgumentException ex) {
            throw new BadRequestException(ex);
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
                entity -> service.delete(uuid),
                () -> {
                    throw new NotFoundException();
                }
        );
    }
}
