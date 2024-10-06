package pl.pg.kyrczak.jakarta.client.dto;

import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class GetClientResponse {
    private UUID uuid;
    private String login;
    private String name;
    private String surname;
    private LocalDate registrationDate;
    private String email;
}
