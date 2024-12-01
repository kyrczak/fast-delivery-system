package pl.pg.kyrczak.jakarta.parcel.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import pl.pg.kyrczak.jakarta.client.entity.Client;
import pl.pg.kyrczak.jakarta.entity.VersionAndCreationDateAuditable;
import pl.pg.kyrczak.jakarta.warehouse.entity.Warehouse;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name="parcels")
public class Parcel extends VersionAndCreationDateAuditable implements Serializable {
    @Id
    private UUID uuid;
    private Float weight;
    @Enumerated(EnumType.STRING)
    private ParcelStatus status;
    private LocalDate deliveryDate;
    @ManyToOne
    @JoinColumn(name = "warehouse")
    private Warehouse warehouse;

    private String image;
    @ManyToOne
    @JoinColumn(name = "client")
    private Client client;

    @PrePersist
    @Override
    public void updateCreationDateTime() {
        super.updateCreationDateTime();
    }
}
