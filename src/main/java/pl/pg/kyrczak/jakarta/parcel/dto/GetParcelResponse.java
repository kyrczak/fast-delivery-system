package pl.pg.kyrczak.jakarta.parcel.dto;


import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class GetParcelResponse {

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @ToString
    @EqualsAndHashCode
    public static class Warehouse {
        private UUID uuid;
        private String name;
    }
    private UUID uuid;
    private Float weight;
    private Warehouse warehouse;
    private LocalDate deliveryDate;
}
