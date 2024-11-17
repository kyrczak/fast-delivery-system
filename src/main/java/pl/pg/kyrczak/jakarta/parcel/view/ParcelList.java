package pl.pg.kyrczak.jakarta.parcel.view;

import jakarta.ejb.EJB;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import pl.pg.kyrczak.jakarta.component.ModelFunctionFactory;
import pl.pg.kyrczak.jakarta.parcel.model.ParcelsModel;
import pl.pg.kyrczak.jakarta.parcel.service.ParcelService;

@RequestScoped
@Named
public class ParcelList {
    private ParcelService service;
    private ParcelsModel parcels;
    private final ModelFunctionFactory factory;

    @Inject
    public ParcelList(ModelFunctionFactory factory) {
        this.factory = factory;
    }

    @EJB
    public void setService(ParcelService service) {
        this.service = service;
    }

    public ParcelsModel getParcels() {
        if (parcels == null) {
            parcels = factory.parcelsToModelFunction().apply(service.findAllForCallerPrincipal());
        }
        return parcels;
    }

    public String deleteAction(ParcelsModel.Parcel parcel) {
        service.delete(parcel.getUuid());
        return "parcel_list?faces-redirect=true";
    }
}
