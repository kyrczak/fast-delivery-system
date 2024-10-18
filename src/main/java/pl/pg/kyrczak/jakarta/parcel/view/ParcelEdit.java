package pl.pg.kyrczak.jakarta.parcel.view;

import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Getter;
import lombok.Setter;
import pl.pg.kyrczak.jakarta.component.ModelFunctionFactory;
import pl.pg.kyrczak.jakarta.parcel.entity.Parcel;
import pl.pg.kyrczak.jakarta.parcel.model.ParcelEditModel;
import pl.pg.kyrczak.jakarta.parcel.service.ParcelService;

import java.io.IOException;
import java.io.Serializable;
import java.util.Optional;
import java.util.UUID;

@ViewScoped
@Named
public class ParcelEdit implements Serializable {
    private final ParcelService service;
    private final ModelFunctionFactory factory;

    @Setter
    @Getter
    private UUID uuid;

    @Getter
    private ParcelEditModel parcel;

    @Inject
    public ParcelEdit(ParcelService service, ModelFunctionFactory factory) {
        this.service = service;
        this.factory = factory;
    }

    public void init() throws IOException {
        Optional<Parcel> parcel = service.find(uuid);
        if (parcel.isPresent()) {
            this.parcel = factory.parcelToEditModelFunction().apply(parcel.get());
        } else {
            FacesContext.getCurrentInstance().getExternalContext().responseSendError(
                    HttpServletResponse.SC_NOT_FOUND,
                    "Parcel not found");
        }
    }

    public String saveAction() {
        service.update(factory.updateParcel().apply(service.find(uuid).orElseThrow(), parcel));
        String viewId = FacesContext.getCurrentInstance().getViewRoot().getViewId();
        return viewId+"?faces-redirect=true&includeViewParams=true";
    }
}
