package pl.pg.kyrczak.jakarta.warehouse.dto;

import lombok.*;

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
public class GetWarehouseResponse {
    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @ToString
    @EqualsAndHashCode
    public static class Parcel {
        private UUID uuid;
        private LocalDate deliveryDate;
    }
    private String name;
    private String location;
    private LocalDate establishedDate;
    private List<Parcel> parcels;
}
