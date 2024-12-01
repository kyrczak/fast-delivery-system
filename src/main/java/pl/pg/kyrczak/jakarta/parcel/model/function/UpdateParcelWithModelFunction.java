package pl.pg.kyrczak.jakarta.parcel.model.function;

import lombok.SneakyThrows;
import pl.pg.kyrczak.jakarta.client.entity.Client;
import pl.pg.kyrczak.jakarta.parcel.entity.Parcel;
import pl.pg.kyrczak.jakarta.parcel.entity.ParcelStatus;
import pl.pg.kyrczak.jakarta.parcel.model.ParcelEditModel;

import java.io.Serializable;
import java.util.function.BiFunction;

public class UpdateParcelWithModelFunction implements BiFunction<Parcel, ParcelEditModel, Parcel>, Serializable {
    @Override
    @SneakyThrows
    public Parcel apply(Parcel parcel, ParcelEditModel parcelEditModel) {
        return Parcel.builder()
                .uuid(parcel.getUuid())
                .weight(parcelEditModel.getWeight())
                .status(ParcelStatus.valueOf(parcelEditModel.getStatus()))
                .deliveryDate(parcelEditModel.getDeliveryDate())
                .warehouse(parcel.getWarehouse())
                .image(parcel.getImage())
                .client(Client.builder()
                        .uuid(parcelEditModel.getClient().getUuid())
                        .build())
                .version(parcelEditModel.getVersion())
                .creationDateTime(parcel.getCreationDateTime())
                .build();

    }
}
