package pl.pg.kyrczak.jakarta.client.model;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class ClientModel {
    private UUID uuid;
    private String login;
}
