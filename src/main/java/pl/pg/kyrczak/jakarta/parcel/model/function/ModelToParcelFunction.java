package pl.pg.kyrczak.jakarta.parcel.model.function;

import pl.pg.kyrczak.jakarta.parcel.entity.Parcel;
import pl.pg.kyrczak.jakarta.parcel.model.ParcelCreateModel;
import pl.pg.kyrczak.jakarta.warehouse.entity.Warehouse;

import java.io.Serializable;
import java.util.function.Function;

public class ModelToParcelFunction implements Function<ParcelCreateModel, Parcel>, Serializable {
    @Override
    public Parcel apply(ParcelCreateModel parcelCreateModel) {
        return Parcel.builder()
                .uuid(parcelCreateModel.getUuid())
                .weight(parcelCreateModel.getWeight())
                .status(parcelCreateModel.getStatus())
                .deliveryDate(parcelCreateModel.getDeliveryDate())
                .warehouse(Warehouse.builder()
                        .uuid(parcelCreateModel.getWarehouse().getUuid())
                        .build())
                .build();
    }
}
