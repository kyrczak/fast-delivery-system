package pl.pg.kyrczak.jakarta.client.entity;

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
public class Client implements Serializable {
    private UUID uuid;
    private String login;
    @ToString.Exclude
    private String password;
    private String name;
    private String surname;
    private LocalDate registrationDate;
    private String email;
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private List<Parcel> parcels;
}
