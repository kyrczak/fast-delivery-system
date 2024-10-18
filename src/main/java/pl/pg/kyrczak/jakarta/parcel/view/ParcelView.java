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
import pl.pg.kyrczak.jakarta.parcel.model.ParcelModel;
import pl.pg.kyrczak.jakarta.parcel.service.ParcelService;

import java.io.IOException;
import java.io.Serializable;
import java.util.Optional;
import java.util.UUID;

@ViewScoped
@Named
public class ParcelView implements Serializable {
    private final ParcelService service;
    private final ModelFunctionFactory factory;

    @Getter
    @Setter
    private UUID uuid;

    @Getter
    private ParcelModel parcel;

    @Inject
    public ParcelView(ParcelService service, ModelFunctionFactory factory) {
        this.service = service;
        this.factory = factory;
    }

    public void init() throws IOException {
        Optional<Parcel> parcel = service.find(uuid);
        if (parcel.isPresent()) {
            this.parcel = factory.parcelToModelFunction().apply(parcel.get());
        } else {
            FacesContext.getCurrentInstance().getExternalContext().responseSendError(
                    HttpServletResponse.SC_NOT_FOUND,
                    "Parcel not found");
        }
    }
}
