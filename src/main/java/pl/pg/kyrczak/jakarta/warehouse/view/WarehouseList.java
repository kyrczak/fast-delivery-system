package pl.pg.kyrczak.jakarta.warehouse.view;

import jakarta.ejb.EJB;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.ws.rs.NotFoundException;
import pl.pg.kyrczak.jakarta.component.ModelFunctionFactory;
import pl.pg.kyrczak.jakarta.parcel.service.ParcelService;
import pl.pg.kyrczak.jakarta.warehouse.model.WarehouseEditModel;
import pl.pg.kyrczak.jakarta.warehouse.model.WarehousesModel;
import pl.pg.kyrczak.jakarta.warehouse.service.WarehouseService;

@RequestScoped
@Named
public class WarehouseList {
    private WarehouseService service;
    private ParcelService parcelService;
    private WarehousesModel warehouses;
    private final ModelFunctionFactory factory;

    @Inject
    public WarehouseList(ModelFunctionFactory factory) {
        this.factory = factory;
    }

    @EJB
    public void setService(WarehouseService service) {
        this.service = service;
    }

    @EJB
    public void setParcelService(ParcelService parcelService) {
        this.parcelService = parcelService;
    }

    public WarehousesModel getWarehouses() {
        if (warehouses == null) {
            warehouses = factory.warehousesToModelFunction().apply(service.findAll());
        }
        return warehouses;
    }

    public String deleteAction(WarehousesModel.Warehouse warehouse) {
        service.find(warehouse.getUuid()).ifPresentOrElse(
                entity -> parcelService.findAllByWarehouse(entity.getUuid()).forEach(
                        parcel -> parcelService.delete(parcel.getUuid())
                ),
                () -> {
                    throw new NotFoundException();
                }
        );
        service.delete(warehouse.getUuid());
        return "warehouse_list?faces-redirect=true";
    }
}
