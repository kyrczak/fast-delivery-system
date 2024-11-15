package pl.pg.kyrczak.jakarta.warehouse.model.function;

import pl.pg.kyrczak.jakarta.warehouse.entity.Warehouse;
import pl.pg.kyrczak.jakarta.warehouse.model.WarehouseEditModel;

import java.util.function.BiFunction;

public class UpdateWarehouseWithModelFunction implements BiFunction<Warehouse, WarehouseEditModel, Warehouse> {
    @Override
    public Warehouse apply(Warehouse warehouse, WarehouseEditModel warehouseEditModel) {
        return Warehouse.builder()
                .uuid(warehouse.getUuid())
                .name(warehouseEditModel.getName())
                .location(warehouseEditModel.getLocation())
                .establishedDate(warehouseEditModel.getEstablishedDate())
                .parcels(warehouse.getParcels())
                .build();
    }
}
