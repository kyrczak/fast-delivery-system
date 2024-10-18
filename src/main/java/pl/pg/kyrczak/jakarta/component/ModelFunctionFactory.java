package pl.pg.kyrczak.jakarta.component;

import jakarta.enterprise.context.ApplicationScoped;
import pl.pg.kyrczak.jakarta.parcel.model.function.*;
import pl.pg.kyrczak.jakarta.warehouse.model.function.WarehouseToModelFunction;

@ApplicationScoped
public class ModelFunctionFactory {
    public ParcelToModelFunction parcelToModelFunction() {
        return new ParcelToModelFunction();
    }

    public ParcelsToModelFunction parcelsToModelFunction() {
        return new ParcelsToModelFunction();
    }

    public ParcelToEditModelFunction parcelToEditModelFunction() {
        return new ParcelToEditModelFunction();
    }

    public ModelToParcelFunction modelToParcelFunction() {
        return new ModelToParcelFunction();
    }

    public UpdateParcelWithModelFunction updateParcel() { return new UpdateParcelWithModelFunction();}

    public WarehouseToModelFunction warehouseToModelFunction() {return new WarehouseToModelFunction(); }
}
