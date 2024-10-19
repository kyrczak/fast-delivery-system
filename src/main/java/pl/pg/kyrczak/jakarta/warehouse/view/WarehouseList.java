package pl.pg.kyrczak.jakarta.warehouse.view;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import pl.pg.kyrczak.jakarta.component.ModelFunctionFactory;
import pl.pg.kyrczak.jakarta.controller.servlet.exception.NotFoundException;
import pl.pg.kyrczak.jakarta.parcel.service.ParcelService;
import pl.pg.kyrczak.jakarta.warehouse.model.WarehousesModel;
import pl.pg.kyrczak.jakarta.warehouse.service.WarehouseService;

@RequestScoped
@Named
public class WarehouseList {
    private final WarehouseService service;
    private final ParcelService parcelService;
    private WarehousesModel warehouses;
    private final ModelFunctionFactory factory;

    @Inject
    public WarehouseList(WarehouseService service,
                         ModelFunctionFactory factory,
                         ParcelService parcelService) {
        this.service = service;
        this.parcelService = parcelService;
        this.factory = factory;
    }

    public WarehousesModel getWarehouses() {
        if (warehouses == null) {
            warehouses = factory.warehousesToModelFunction().apply(service.findAll());
        }
        return warehouses;
    }

    public String deleteAction(WarehousesModel.Warehouse warehouse) {
        service.find(warehouse.getUuid()).ifPresentOrElse(
                entity -> parcelService.findAllByWarehouse(entity.getUuid()).ifPresentOrElse(
                        parcels -> parcels.forEach(parcel -> parcelService.delete(parcel.getUuid())),
                        () -> {
                            throw new NotFoundException();
                        }
                ),
                () -> {
                    throw new NotFoundException();
                }
        );
        service.delete(warehouse.getUuid());
        return "warehouse_list?faces-redirect=true";
    }
}
