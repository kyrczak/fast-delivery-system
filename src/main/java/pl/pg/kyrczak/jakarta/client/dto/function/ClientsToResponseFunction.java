package pl.pg.kyrczak.jakarta.client.dto.function;

import pl.pg.kyrczak.jakarta.client.dto.GetClientsResponse;
import pl.pg.kyrczak.jakarta.client.entity.Client;

import java.util.List;
import java.util.function.Function;

public class ClientsToResponseFunction implements Function<List<Client>, GetClientsResponse> {
    @Override
    public GetClientsResponse apply(List<Client> clients) {
        return GetClientsResponse.builder()
                .clients(clients.stream()
                        .map(client -> GetClientsResponse.Client.builder()
                                .uuid(client.getUuid())
                                .login(client.getLogin())
                                .build())
                        .toList())
                .build();
    }
}
