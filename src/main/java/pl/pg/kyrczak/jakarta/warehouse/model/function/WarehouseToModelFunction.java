package pl.pg.kyrczak.jakarta.warehouse.model.function;

import pl.pg.kyrczak.jakarta.warehouse.entity.Warehouse;
import pl.pg.kyrczak.jakarta.warehouse.model.WarehouseModel;

import java.io.Serializable;
import java.util.function.Function;
import java.util.stream.Collectors;

public class WarehouseToModelFunction implements Function<Warehouse, WarehouseModel>, Serializable {
    @Override
    public WarehouseModel apply(Warehouse entity) {
        return WarehouseModel.builder()
                .uuid(entity.getUuid())
                .establishedDate(entity.getEstablishedDate())
                .location(entity.getLocation())
                .name(entity.getName())
                .parcels(entity.getParcels().stream()
                        .map(
                                parcel -> WarehouseModel.Parcel.builder()
                                        .uuid(parcel.getUuid())
                                        .deliveryDate(parcel.getDeliveryDate())
                                        .build()
                        ).collect(Collectors.toList())
                ).build();
    }
}
