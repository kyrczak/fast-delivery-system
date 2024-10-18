package pl.pg.kyrczak.jakarta.parcel.model.function;

import pl.pg.kyrczak.jakarta.parcel.entity.Parcel;
import pl.pg.kyrczak.jakarta.parcel.model.ParcelsModel;

import java.util.List;
import java.util.function.Function;

public class ParcelsToModelFunction implements Function<List<Parcel>, ParcelsModel> {
    @Override
    public ParcelsModel apply(List<Parcel> entity) {
        return ParcelsModel.builder()
                .parcels(entity.stream()
                        .map(parcel -> ParcelsModel.Parcel.builder()
                                .uuid(parcel.getUuid())
                                .status(String.valueOf(parcel.getStatus()))
                                .build())
                        .toList())
                .build();
    }
}
