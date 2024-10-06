package pl.pg.kyrczak.jakarta.parcel.dto.function;

import pl.pg.kyrczak.jakarta.parcel.dto.PutParcelRequest;
import pl.pg.kyrczak.jakarta.parcel.entity.Parcel;
import pl.pg.kyrczak.jakarta.parcel.entity.ParcelStatus;
import pl.pg.kyrczak.jakarta.warehouse.entity.Warehouse;

import java.util.UUID;
import java.util.function.BiFunction;

public class RequestToParcelFunction implements BiFunction<UUID, PutParcelRequest, Parcel> {
    @Override
    public Parcel apply(UUID uuid, PutParcelRequest request) {
        return Parcel.builder()
                .uuid(uuid)
                .weight(request.getWeight())
                .deliveryDate(request.getDeliveryDate())
                .warehouse(Warehouse.builder()
                        .uuid(request.getWarehouse())
                        .build())
                .status(ParcelStatus.valueOf(request.getStatus()))
                .build();
    }
}
