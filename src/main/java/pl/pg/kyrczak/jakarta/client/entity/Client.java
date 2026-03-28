package pl.pg.kyrczak.jakarta.client.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import pl.pg.kyrczak.jakarta.parcel.entity.Parcel;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
@Entity
@Table(name = "clients")
public class Client implements Serializable {
    @Id
    private UUID uuid;
    private String login;
    @ToString.Exclude
    private String password;
    private String name;
    private String surname;
    @Column(name="registration_date")
    private LocalDate registrationDate;
    @Column(nullable = false, unique = true)
    private String email;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Parcel> parcels;

    @CollectionTable(name = "clients__roles", joinColumns = @JoinColumn(name = "id"))
    @Column(name = "role")
    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> roles;
}
