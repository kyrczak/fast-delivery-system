package pl.pg.kyrczak.jakarta.client.dto.function;

import pl.pg.kyrczak.jakarta.client.dto.PutClientRequest;
import pl.pg.kyrczak.jakarta.client.entity.Client;

import java.util.UUID;
import java.util.function.BiFunction;

public class RequestToClientFunction implements BiFunction<UUID, PutClientRequest, Client> {
    public Client apply(UUID uuid, PutClientRequest request) {
        return Client.builder()
                .uuid(uuid)
                .login(request.getLogin())
                .name(request.getName())
                .registrationDate(request.getRegistrationDate())
                .surname(request.getSurname())
                .email(request.getEmail())
                .password(request.getPassword())
                .build();
    }
}
