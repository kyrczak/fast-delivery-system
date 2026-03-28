package pl.pg.kyrczak.jakarta.parcel.model;

import lombok.*;
import pl.pg.kyrczak.jakarta.parcel.entity.ParcelStatus;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class ParcelModel {
    private UUID uuid;
    private Float weight;
    private ParcelStatus parcelStatus;
    private LocalDate deliveryDate;
    private String warehouse;
}
