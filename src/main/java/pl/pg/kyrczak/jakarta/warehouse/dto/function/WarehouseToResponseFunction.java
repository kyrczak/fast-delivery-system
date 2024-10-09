package pl.pg.kyrczak.jakarta.warehouse.dto.function;

import pl.pg.kyrczak.jakarta.client.dto.GetClientResponse;
import pl.pg.kyrczak.jakarta.warehouse.dto.GetWarehouseResponse;
import pl.pg.kyrczak.jakarta.warehouse.entity.Warehouse;

import java.util.function.Function;

public class WarehouseToResponseFunction implements Function<Warehouse, GetWarehouseResponse> {
    @Override
    public GetWarehouseResponse apply(Warehouse warehouse) {
        return GetWarehouseResponse.builder()
                .name(warehouse.getName())
                .location(warehouse.getLocation())
                .establishedDate(warehouse.getEstablishedDate())
                .parcels(warehouse.getParcels().stream()
                        .map(parcel -> GetWarehouseResponse.Parcel.builder()
                                .uuid(parcel.getUuid())
                                .build())
                        .toList())
                .build();
    }
}
