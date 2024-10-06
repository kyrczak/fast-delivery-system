package pl.pg.kyrczak.jakarta.client.entity;

import lombok.*;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;
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
}
