package pl.pg.kyrczak.jakarta.parcel.dto.function;

import pl.pg.kyrczak.jakarta.parcel.dto.PatchParcelRequest;
import pl.pg.kyrczak.jakarta.parcel.entity.Parcel;
import pl.pg.kyrczak.jakarta.parcel.entity.ParcelStatus;

import java.util.function.BiFunction;

public class UpdateParcelWithRequestFunction implements BiFunction<Parcel, PatchParcelRequest, Parcel> {
    @Override
    public Parcel apply(Parcel parcel, PatchParcelRequest request) {
        return Parcel.builder()
                .uuid(parcel.getUuid())
                .weight(parcel.getWeight())
                .warehouse(parcel.getWarehouse())
                .status(ParcelStatus.valueOf(request.getStatus()))
                .deliveryDate(request.getDeliveryDate())
                .build();
    }
}
