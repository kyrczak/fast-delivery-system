package pl.pg.kyrczak.jakarta.parcel.view;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import pl.pg.kyrczak.jakarta.component.ModelFunctionFactory;
import pl.pg.kyrczak.jakarta.parcel.model.ParcelsModel;
import pl.pg.kyrczak.jakarta.parcel.service.ParcelService;

@RequestScoped
@Named
public class ParcelList {
    private final ParcelService service;
    private ParcelsModel parcels;
    private final ModelFunctionFactory factory;

    @Inject
    public ParcelList(ParcelService service, ModelFunctionFactory factory) {
        this.service = service;
        this.factory = factory;
    }

    public ParcelsModel getParcels() {
        if (parcels == null) {
            parcels = factory.parcelsToModelFunction().apply(service.findAll());
        }
        return parcels;
    }

    public String deleteAction(ParcelsModel.Parcel parcel) {
        service.delete(parcel.getUuid());
        return "parcel_list?faces-redirect=true";
    }
}
