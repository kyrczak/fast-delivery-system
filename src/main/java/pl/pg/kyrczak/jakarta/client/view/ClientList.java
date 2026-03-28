package pl.pg.kyrczak.jakarta.client.view;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import pl.pg.kyrczak.jakarta.client.model.ClientsModel;
import pl.pg.kyrczak.jakarta.client.service.ClientService;
import pl.pg.kyrczak.jakarta.component.ModelFunctionFactory;

@RequestScoped
@Named
public class ClientList {
    private final ClientService service;
    private ClientsModel clients;

    private final ModelFunctionFactory factory;

    @Inject
    public ClientList(ClientService service, ModelFunctionFactory factory) {
        this.service = service;
        this.factory = factory;
    }

    public ClientsModel getClients() {
        if (clients == null) {
            clients = factory.clientsToModel().apply(service.findAll());
        }
        return clients;
    }

    public String deleteAction(ClientsModel.Client client) {
        service.delete(client.getUuid());
        return "client_list?faces-redirect=true";
    }
}
