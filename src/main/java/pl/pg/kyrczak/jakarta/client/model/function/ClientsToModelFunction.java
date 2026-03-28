package pl.pg.kyrczak.jakarta.client.model.function;

import pl.pg.kyrczak.jakarta.client.entity.Client;
import pl.pg.kyrczak.jakarta.client.model.ClientsModel;

import java.util.List;
import java.util.function.Function;

public class ClientsToModelFunction implements Function<List<Client>, ClientsModel> {
    @Override
    public ClientsModel apply(List<Client> clients) {
        return ClientsModel.builder()
                .clients(clients.stream()
                        .map(client -> ClientsModel.Client.builder()
                                .uuid(client.getUuid())
                                .login(client.getLogin())
                                .build())
                        .toList())
                .build();
    }
}
