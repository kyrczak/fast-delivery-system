package pl.pg.kyrczak.jakarta.client.dto;

import jakarta.ejb.Local;
import lombok.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class PutClientRequest {
    private String login;
    private String name;
    private String surname;
    private LocalDate registrationDate;
    private String password;
    private String email;
    private List<UUID> parcels;
}
