package pl.pg.kyrczak.jakarta.parcel.entity;

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
public class Parcel {
    private UUID uuid;
    private Float weight;
    private ParcelStatus status;
    private LocalDate deliveryDate;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private byte[] image;
}
