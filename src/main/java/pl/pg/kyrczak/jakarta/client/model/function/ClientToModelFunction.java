package pl.pg.kyrczak.jakarta.client.model.function;

import pl.pg.kyrczak.jakarta.client.entity.Client;
import pl.pg.kyrczak.jakarta.client.model.ClientModel;

import java.io.Serializable;
import java.util.function.Function;

public class ClientToModelFunction implements Function<Client, ClientModel>, Serializable {
    @Override
    public ClientModel apply(Client client) {
        return ClientModel.builder()
                .uuid(client.getUuid())
                .login(client.getLogin())
                .build();
    }
}
