package pl.pg.kyrczak.jakarta.parcel.model.function;

import pl.pg.kyrczak.jakarta.parcel.entity.Parcel;
import pl.pg.kyrczak.jakarta.parcel.model.ParcelModel;

import java.io.Serializable;
import java.util.function.Function;

public class ParcelToModelFunction implements Function<Parcel, ParcelModel>, Serializable {
    @Override
    public ParcelModel apply(Parcel entity) {
        return ParcelModel.builder()
                .uuid(entity.getUuid())
                .deliveryDate(entity.getDeliveryDate())
                .weight(entity.getWeight())
                .warehouse(entity.getWarehouse().getName())
                .parcelStatus(entity.getStatus())
                .build();
    }
}
