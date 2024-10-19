package pl.pg.kyrczak.jakarta.warehouse.view;

import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Getter;
import lombok.Setter;
import pl.pg.kyrczak.jakarta.component.ModelFunctionFactory;
import pl.pg.kyrczak.jakarta.parcel.service.ParcelService;
import pl.pg.kyrczak.jakarta.warehouse.entity.Warehouse;
import pl.pg.kyrczak.jakarta.warehouse.model.WarehouseModel;
import pl.pg.kyrczak.jakarta.warehouse.service.WarehouseService;

import java.io.IOException;
import java.io.Serializable;
import java.util.Optional;
import java.util.UUID;

@ViewScoped
@Named
public class WarehouseView implements Serializable {
    private final WarehouseService service;

    private final ParcelService parcelService;
    private final ModelFunctionFactory factory;

    @Getter
    @Setter
    private UUID uuid;

    @Getter
    private WarehouseModel warehouse;

    @Inject
    public WarehouseView(WarehouseService service, ParcelService parcelService, ModelFunctionFactory factory) {
        this.service = service;
        this.parcelService = parcelService;
        this.factory = factory;
    }

    public void init() throws IOException {
        Optional<Warehouse> warehouse = service.find(uuid);
        if (warehouse.isPresent()) {
            this.warehouse = factory.warehouseToModelFunction().apply(warehouse.get());
        } else {
            FacesContext.getCurrentInstance().getExternalContext().responseSendError(
                    HttpServletResponse.SC_NOT_FOUND,
                    "Warehouse not found");
        }
    }


    public String deleteParcelAction(WarehouseModel.Parcel parcel) {
        parcelService.delete(parcel.getUuid());
        return "warehouse_view.xhtml?uuid=" + this.getUuid() + "&faces-redirect=true";
    }

}
