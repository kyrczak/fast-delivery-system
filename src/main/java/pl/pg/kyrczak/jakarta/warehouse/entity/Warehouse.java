package pl.pg.kyrczak.jakarta.warehouse.entity;

import jakarta.persistence.*;
import lombok.*;
import pl.pg.kyrczak.jakarta.parcel.entity.Parcel;

import java.io.Serializable;
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
@Entity
@Table(name = "warehouses")
public class Warehouse implements Serializable {
    @Id
    private UUID uuid;
    private String name;
    private String location;
    @Column(name = "established_date")
    private LocalDate establishedDate;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "warehouse", cascade = CascadeType.REMOVE)
    private List<Parcel> parcels;

}
