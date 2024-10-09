package pl.pg.kyrczak.jakarta.client.dto.function;

import pl.pg.kyrczak.jakarta.client.dto.PutPasswordRequest;
import pl.pg.kyrczak.jakarta.client.entity.Client;

import java.util.function.BiFunction;

public class UpdateClientPasswordWithRequestFunction implements BiFunction<Client, PutPasswordRequest, Client> {
    @Override
    public Client apply(Client entity, PutPasswordRequest request) {
        return Client.builder()
                .uuid(entity.getUuid())
                .login(entity.getLogin())
                .name(entity.getName())
                .registrationDate(entity.getRegistrationDate())
                .surname(entity.getSurname())
                .email(entity.getEmail())
                .password(request.getPassword())
                .parcels(entity.getParcels())
                .build();

    }
}
