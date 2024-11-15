package pl.pg.kyrczak.jakarta.warehouse.model.function;

import pl.pg.kyrczak.jakarta.warehouse.entity.Warehouse;
import pl.pg.kyrczak.jakarta.warehouse.model.WarehouseEditModel;

import java.io.Serializable;
import java.util.function.Function;

public class WarehouseToEditModelFunction implements Function<Warehouse, WarehouseEditModel>, Serializable {
    @Override
    public WarehouseEditModel apply(Warehouse warehouse) {
        return WarehouseEditModel.builder()
                .name(warehouse.getName())
                .location(warehouse.getLocation())
                .establishedDate(warehouse.getEstablishedDate())
                .build();
    }
}
