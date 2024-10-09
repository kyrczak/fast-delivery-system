package pl.pg.kyrczak.jakarta.client.dto.function;

import pl.pg.kyrczak.jakarta.client.dto.GetClientResponse;
import pl.pg.kyrczak.jakarta.client.entity.Client;

import java.util.function.Function;

public class ClientToResponseFunction implements Function<Client, GetClientResponse> {
    @Override
    public GetClientResponse apply(Client client) {
        return GetClientResponse.builder()
                .uuid(client.getUuid())
                .login(client.getLogin())
                .name(client.getName())
                .registrationDate(client.getRegistrationDate())
                .surname(client.getSurname())
                .email(client.getEmail())
                .parcels(client.getParcels().stream()
                        .map(parcel -> GetClientResponse.Parcel.builder()
                                .uuid(parcel.getUuid())
                                .build())
                        .toList())
                .build();

    }
}
