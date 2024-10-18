package pl.pg.kyrczak.jakarta.warehouse.model;

import lombok.*;
import pl.pg.kyrczak.jakarta.parcel.model.ParcelModel;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class WarehouseModel {

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @ToString
    @EqualsAndHashCode
    public static class Parcel {
        private UUID uuid;
        private String status;
    }

    private UUID uuid;
    private String name;
    private String location;
    private LocalDate establishedDate;

    @Singular
    @EqualsAndHashCode.Exclude
    private List<Parcel> parcels;

}
