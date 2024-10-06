package pl.pg.kyrczak.jakarta.parcel.entity;

import lombok.*;
import pl.pg.kyrczak.jakarta.client.entity.Client;
import pl.pg.kyrczak.jakarta.warehouse.entity.Warehouse;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class Parcel implements Serializable {
    private UUID uuid;
    private Float weight;
    private ParcelStatus status;
    private LocalDate deliveryDate;
    private Warehouse warehouse;
    private Client client;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private byte[] image;
}
