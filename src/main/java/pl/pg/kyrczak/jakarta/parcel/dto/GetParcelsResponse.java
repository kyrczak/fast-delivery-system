package pl.pg.kyrczak.jakarta.parcel.dto;

import lombok.*;
import pl.pg.kyrczak.jakarta.parcel.entity.Parcel;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class GetParcelsResponse {
    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @ToString
    @EqualsAndHashCode
    public static class Parcel {
        private UUID uuid;
    }
    @Singular
    private List<Parcel> parcels;

}
