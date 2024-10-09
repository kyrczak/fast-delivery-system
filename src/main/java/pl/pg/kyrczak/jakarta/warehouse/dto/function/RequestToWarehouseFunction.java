package pl.pg.kyrczak.jakarta.warehouse.dto.function;

import pl.pg.kyrczak.jakarta.parcel.entity.Parcel;
import pl.pg.kyrczak.jakarta.warehouse.dto.PutWarehouseRequest;
import pl.pg.kyrczak.jakarta.warehouse.entity.Warehouse;

import java.util.UUID;
import java.util.function.BiFunction;

public class RequestToWarehouseFunction implements BiFunction <UUID, PutWarehouseRequest, Warehouse> {
    @Override
    public Warehouse apply(UUID uuid, PutWarehouseRequest request) {
        return Warehouse.builder()
                .uuid(uuid)
                .name(request.getName())
                .location(request.getLocation())
                .establishedDate(request.getEstablishedDate())
                .parcels(request.getParcels().stream()
                        .map(parcel -> Parcel.builder()
                                .uuid(parcel.getUuid())
                                .build())
                        .toList())
                .build();
    }
}
