package pl.pg.kyrczak.jakarta.warehouse.model.function;

import pl.pg.kyrczak.jakarta.warehouse.entity.Warehouse;
import pl.pg.kyrczak.jakarta.warehouse.model.WarehousesModel;

import java.util.List;
import java.util.function.Function;

public class WarehousesToModelFunction implements Function<List<Warehouse>, WarehousesModel> {
    @Override
    public WarehousesModel apply(List<Warehouse> entity) {
        return WarehousesModel.builder()
                .warehouses(entity.stream()
                        .map(warehouse -> WarehousesModel.Warehouse.builder()
                                .uuid(warehouse.getUuid())
                                .name(warehouse.getName())
                                .build())
                        .toList())
                .build();
    }
}
