package pl.pg.kyrczak.jakarta.parcel.view;

import jakarta.ejb.EJB;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import lombok.Getter;
import lombok.Setter;
import pl.pg.kyrczak.jakarta.component.ModelFunctionFactory;
import pl.pg.kyrczak.jakarta.parcel.entity.Parcel;
import pl.pg.kyrczak.jakarta.parcel.entity.ParcelStatus;
import pl.pg.kyrczak.jakarta.parcel.model.ParcelEditModel;
import pl.pg.kyrczak.jakarta.parcel.service.ParcelService;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@ViewScoped
@Named
public class ParcelEdit implements Serializable {
    private ParcelService service;
    private final ModelFunctionFactory factory;

    @Setter
    @Getter
    private UUID uuid;

    @Getter
    private ParcelEditModel parcel;

    @Inject
    public ParcelEdit(ModelFunctionFactory factory) {
        this.factory = factory;
    }

    @EJB
    public void setService(ParcelService service) {
        this.service = service;
    }
    public List<ParcelStatus> getStatusOptions() {
        return List.of(ParcelStatus.values());
    }
    public void init() throws IOException {
        Optional<Parcel> parcel = service.findForCallerPrincipal(uuid);
        System.out.println("Initializing ParcelEdit with UUID: " + uuid);
        if (parcel.isPresent()) {
            this.parcel = factory.parcelToEditModelFunction().apply(parcel.get());
        } else {
            System.out.println("Parcel not found for UUID: " + uuid);
            FacesContext.getCurrentInstance().getExternalContext().responseSendError(
                    HttpServletResponse.SC_NOT_FOUND,
                    "Parcel not found");
        }
    }

    public String saveAction() {
        service.update(factory.updateParcel().apply(service.findForCallerPrincipal(uuid).orElseThrow(), parcel));
        Part image = parcel.getImage();
        if (image != null) {
            try (InputStream inputStream = image.getInputStream()) {
                service.uploadImage(uuid, inputStream);
            } catch (IOException e) {
                return null; // Stay on the page if there's an error
            }
        }
        String viewId = FacesContext.getCurrentInstance().getViewRoot().getViewId();
        return viewId + "?faces-redirect=true&includeViewParams=true";
    }
}
