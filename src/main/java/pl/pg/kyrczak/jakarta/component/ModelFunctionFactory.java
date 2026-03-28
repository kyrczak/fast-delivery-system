package pl.pg.kyrczak.jakarta.component;

import jakarta.enterprise.context.ApplicationScoped;
import pl.pg.kyrczak.jakarta.client.model.function.ClientToModelFunction;
import pl.pg.kyrczak.jakarta.client.model.function.ClientsToModelFunction;
import pl.pg.kyrczak.jakarta.parcel.model.function.*;
import pl.pg.kyrczak.jakarta.warehouse.model.function.*;

@ApplicationScoped
public class ModelFunctionFactory {
    public ParcelToModelFunction parcelToModelFunction() {
        return new ParcelToModelFunction();
    }

    public ParcelsToModelFunction parcelsToModelFunction() {
        return new ParcelsToModelFunction();
    }

    public ParcelToEditModelFunction parcelToEditModelFunction() {
        return new ParcelToEditModelFunction(clientToModel());
    }

    public ModelToParcelFunction modelToParcelFunction() {
        return new ModelToParcelFunction();
    }

    public UpdateParcelWithModelFunction updateParcel() {
        return new UpdateParcelWithModelFunction();
    }

    public WarehouseToModelFunction warehouseToModelFunction() {
        return new WarehouseToModelFunction();
    }
    public WarehousesToModelFunction warehousesToModelFunction() {
        return new WarehousesToModelFunction();
    }

    public ModelToWarehouseFunction modelToWarehouseFunction() {return new ModelToWarehouseFunction();}

    public UpdateWarehouseWithModelFunction updateWarehouse() {return new UpdateWarehouseWithModelFunction();}
    public WarehouseToEditModelFunction warehouseToEditModelFunction() { return new WarehouseToEditModelFunction();}

    public ClientToModelFunction clientToModel() {
        return new ClientToModelFunction();
    }

    public ClientsToModelFunction clientsToModel() {
        return new ClientsToModelFunction();
    }
}
