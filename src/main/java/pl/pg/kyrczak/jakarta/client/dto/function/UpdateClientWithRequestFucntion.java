package pl.pg.kyrczak.jakarta.client.dto.function;

import pl.pg.kyrczak.jakarta.client.dto.PatchClientRequest;
import pl.pg.kyrczak.jakarta.client.entity.Client;

import java.util.function.BiFunction;

public class UpdateClientWithRequestFucntion implements BiFunction<Client, PatchClientRequest, Client> {
    @Override
    public Client apply(Client entity, PatchClientRequest request) {
        return Client.builder()
                .uuid(entity.getUuid())
                .login(entity.getLogin())
                .registrationDate(entity.getRegistrationDate())
                .name(request.getName())
                .surname(request.getSurname())
                .email(request.getEmail())
                .password(entity.getPassword())
                .parcels(entity.getParcels())
                .build();
    }
}
