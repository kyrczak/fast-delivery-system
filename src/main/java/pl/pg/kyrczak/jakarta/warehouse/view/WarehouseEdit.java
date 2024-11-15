package pl.pg.kyrczak.jakarta.warehouse.view;

import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Getter;
import lombok.Setter;
import pl.pg.kyrczak.jakarta.component.ModelFunctionFactory;
import pl.pg.kyrczak.jakarta.warehouse.entity.Warehouse;
import pl.pg.kyrczak.jakarta.warehouse.model.WarehouseEditModel;
import pl.pg.kyrczak.jakarta.warehouse.service.WarehouseService;

import java.io.IOException;
import java.io.Serializable;
import java.util.Optional;
import java.util.UUID;

@ViewScoped
@Named
public class WarehouseEdit implements Serializable {
    private final WarehouseService service;
    private final ModelFunctionFactory factory;

    @Getter
    @Setter
    private UUID uuid;

    @Getter
    private WarehouseEditModel warehouse;

    @Inject
    public WarehouseEdit(WarehouseService service, ModelFunctionFactory factory) {
        this.service = service;
        this.factory = factory;
    }

    public void init() throws IOException {
        Optional<Warehouse> warehouse = service.find(uuid);
        if(warehouse.isPresent()) {
            this.warehouse = factory.warehouseToEditModelFunction().apply(warehouse.get());
        } else {
            FacesContext.getCurrentInstance().getExternalContext().responseSendError(
                    HttpServletResponse.SC_NOT_FOUND,
                    "Warehouse not found"
            );
        }
    }

    public String saveAction() {
        service.update(factory.updateWarehouse().apply(service.find(uuid).orElseThrow(), warehouse));
        String viewId = FacesContext.getCurrentInstance().getViewRoot().getViewId();
        return viewId + "?faces-redirect=true&includeViewParams=true";
    }
}
