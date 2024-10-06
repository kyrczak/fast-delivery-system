package pl.pg.kyrczak.jakarta.parcel.dto.function;

import pl.pg.kyrczak.jakarta.parcel.dto.GetParcelResponse;
import pl.pg.kyrczak.jakarta.parcel.entity.Parcel;

import java.util.function.Function;

public class ParcelToResponseFunction implements Function<Parcel, GetParcelResponse> {
    @Override
    public GetParcelResponse apply(Parcel parcel) {
        return GetParcelResponse.builder()
                .uuid(parcel.getUuid())
                .weight(parcel.getWeight())
                .warehouse(GetParcelResponse.Warehouse.builder()
                        .uuid(parcel.getWarehouse().getUuid())
                        .name(parcel.getWarehouse().getName())
                        .build())
                .build();
    }
}
