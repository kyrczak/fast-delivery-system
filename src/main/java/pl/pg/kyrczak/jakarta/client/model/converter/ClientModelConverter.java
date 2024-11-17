package pl.pg.kyrczak.jakarta.client.model.converter;

import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.convert.Converter;
import jakarta.faces.convert.FacesConverter;
import jakarta.inject.Inject;
import pl.pg.kyrczak.jakarta.client.entity.Client;
import pl.pg.kyrczak.jakarta.client.model.ClientModel;
import pl.pg.kyrczak.jakarta.client.model.ClientsModel;
import pl.pg.kyrczak.jakarta.client.service.ClientService;
import pl.pg.kyrczak.jakarta.component.ModelFunctionFactory;

import java.util.Optional;

@FacesConverter(forClass = ClientModel.class, managed = true)
public class ClientModelConverter implements Converter<ClientModel> {
    private final ClientService service;
    private final ModelFunctionFactory factory;

    @Inject
    public ClientModelConverter(ClientService service,
                                ModelFunctionFactory factory) {
        this.service = service;
        this.factory = factory;
    }
    @Override
    public ClientModel getAsObject(FacesContext context, UIComponent component, String value) {
        if (value == null || value.isBlank()) {
            return null;
        }
        Optional<Client> user = service.find(value);
        return user.map(factory.clientToModel()).orElse(null);

    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, ClientModel value) {
        return value == null ? "" : value.getLogin();
    }
}
