package pl.pg.kyrczak.jakarta.parcel.model.function;

import pl.pg.kyrczak.jakarta.parcel.entity.Parcel;
import pl.pg.kyrczak.jakarta.parcel.model.ParcelEditModel;

import java.io.Serializable;
import java.util.function.Function;

public class ParcelToEditModelFunction implements Function<Parcel, ParcelEditModel>, Serializable {
    @Override
    public ParcelEditModel apply(Parcel entity) {
        return ParcelEditModel.builder()
                .weight(entity.getWeight())
                .status(String.valueOf(entity.getStatus()))
                .deliveryDate(entity.getDeliveryDate())
                .build();

    }
}
