package pl.pg.kyrczak.jakarta.parcel.model.function;

import pl.pg.kyrczak.jakarta.client.model.function.ClientToModelFunction;
import pl.pg.kyrczak.jakarta.parcel.entity.Parcel;
import pl.pg.kyrczak.jakarta.parcel.model.ParcelEditModel;

import java.io.Serializable;
import java.util.function.Function;

public class ParcelToEditModelFunction implements Function<Parcel, ParcelEditModel>, Serializable {

    private final ClientToModelFunction clientToModelFunction;

    public ParcelToEditModelFunction(ClientToModelFunction clientToModelFunction) {
        this.clientToModelFunction = clientToModelFunction;
    }
    @Override
    public ParcelEditModel apply(Parcel entity) {
        return ParcelEditModel.builder()
                .weight(entity.getWeight())
                .status(String.valueOf(entity.getStatus()))
                .deliveryDate(entity.getDeliveryDate())
                .client(clientToModelFunction.apply(entity.getClient()))
                .version(entity.getVersion())
                .build();

    }
}
