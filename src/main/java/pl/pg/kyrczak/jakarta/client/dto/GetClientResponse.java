package pl.pg.kyrczak.jakarta.client.dto;

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
public class GetClientResponse {
    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @ToString
    @EqualsAndHashCode
    public static class Parcel {
        private UUID uuid;
    }
    private UUID uuid;
    private String login;
    private String name;
    private String surname;
    private LocalDate registrationDate;
    private String email;
    private List<Parcel> parcels;
}
