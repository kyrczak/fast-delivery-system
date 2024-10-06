package pl.pg.kyrczak.jakarta.parcel.dto;

import lombok.*;
import pl.pg.kyrczak.jakarta.parcel.entity.ParcelStatus;
import pl.pg.kyrczak.jakarta.warehouse.entity.Warehouse;

import java.time.LocalDate;
import java.util.UUID;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class PutParcelRequest {
    private Float weight;
    private String status;
    private LocalDate deliveryDate;
    private UUID warehouse;
}
