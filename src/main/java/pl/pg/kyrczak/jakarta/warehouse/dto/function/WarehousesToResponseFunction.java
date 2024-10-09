package pl.pg.kyrczak.jakarta.warehouse.dto.function;

import pl.pg.kyrczak.jakarta.warehouse.dto.GetWarehouseResponse;
import pl.pg.kyrczak.jakarta.warehouse.dto.GetWarehousesResponse;
import pl.pg.kyrczak.jakarta.warehouse.entity.Warehouse;

import java.util.List;
import java.util.function.Function;

public class WarehousesToResponseFunction implements Function<List<Warehouse>, GetWarehousesResponse> {
    @Override
    public GetWarehousesResponse apply(List<Warehouse> entites) {
        return GetWarehousesResponse.builder()
                .warehouses(entites.stream()
                        .map(warehouse -> GetWarehousesResponse.Warehouse.builder()
                                .uuid(warehouse.getUuid())
                                .name(warehouse.getName())
                                .build())
                        .toList())
                .build();
    }
}
