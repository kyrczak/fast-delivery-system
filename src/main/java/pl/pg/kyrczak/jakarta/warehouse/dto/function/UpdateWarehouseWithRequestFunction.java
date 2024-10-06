package pl.pg.kyrczak.jakarta.warehouse.dto.function;

import pl.pg.kyrczak.jakarta.warehouse.dto.PatchWarehouseRequest;
import pl.pg.kyrczak.jakarta.warehouse.entity.Warehouse;

import java.util.UUID;
import java.util.function.BiFunction;

public class UpdateWarehouseWithRequestFunction implements BiFunction<Warehouse, PatchWarehouseRequest, Warehouse> {
    @Override
    public Warehouse apply(Warehouse warehouse, PatchWarehouseRequest request) {
        return Warehouse.builder()
                .uuid(warehouse.getUuid())
                .establishedDate(warehouse.getEstablishedDate())
                .parcels(warehouse.getParcels())
                .name(request.getName())
                .location(request.getLocation())
                .build();
    }
}
