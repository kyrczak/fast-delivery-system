package pl.pg.kyrczak.jakarta.parcel.entity;

import jakarta.persistence.*;
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
@Entity
@Table(name="parcels")
public class Parcel implements Serializable {
    @Id
    private UUID uuid;
    private Float weight;
    private ParcelStatus status;
    private LocalDate deliveryDate;
    @ManyToOne
    @JoinColumn(name = "warehouse")
    private Warehouse warehouse;

    @ManyToOne
    @JoinColumn(name = "client")
    private Client client;
}
