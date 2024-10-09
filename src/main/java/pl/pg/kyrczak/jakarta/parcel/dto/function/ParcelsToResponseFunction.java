package pl.pg.kyrczak.jakarta.parcel.dto.function;

import pl.pg.kyrczak.jakarta.parcel.dto.GetParcelsResponse;
import pl.pg.kyrczak.jakarta.parcel.entity.Parcel;

import java.util.function.Function;
import java.util.List;

public class ParcelsToResponseFunction implements Function<List<Parcel>, GetParcelsResponse> {
    @Override
    public GetParcelsResponse apply(List<Parcel> entities) {
        return GetParcelsResponse.builder()
                .parcels(entities.stream()
                        .map(parcel -> GetParcelsResponse.Parcel.builder()
                                .uuid(parcel.getUuid())
                                .build())
                        .toList())
                .build();
    }
}
