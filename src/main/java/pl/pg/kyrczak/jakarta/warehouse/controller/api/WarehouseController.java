package pl.pg.kyrczak.jakarta.warehouse.controller.api;

import pl.pg.kyrczak.jakarta.warehouse.dto.GetWarehouseResponse;
import pl.pg.kyrczak.jakarta.warehouse.dto.GetWarehousesResponse;
import pl.pg.kyrczak.jakarta.warehouse.dto.PatchWarehouseRequest;
import pl.pg.kyrczak.jakarta.warehouse.dto.PutWarehouseRequest;

import java.util.UUID;

public interface WarehouseController {
    GetWarehousesResponse getWarehouses();
    GetWarehouseResponse getWarehouse(UUID uuid);
    void putWarehouse(UUID uuid, PutWarehouseRequest request);
    void patchWarehouse(UUID uuid, PatchWarehouseRequest request);
    void deleteWarehouse(UUID uuid);

}
