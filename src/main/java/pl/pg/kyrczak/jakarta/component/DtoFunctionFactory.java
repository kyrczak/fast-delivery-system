package pl.pg.kyrczak.jakarta.component;

import pl.pg.kyrczak.jakarta.client.dto.function.*;
import pl.pg.kyrczak.jakarta.parcel.dto.function.ParcelToResponseFunction;
import pl.pg.kyrczak.jakarta.parcel.dto.function.ParcelsToResponseFunction;
import pl.pg.kyrczak.jakarta.parcel.dto.function.RequestToParcelFunction;
import pl.pg.kyrczak.jakarta.parcel.dto.function.UpdateParcelWithRequestFunction;
import pl.pg.kyrczak.jakarta.warehouse.dto.function.RequestToWarehouseFunction;
import pl.pg.kyrczak.jakarta.warehouse.dto.function.UpdateWarehouseWithRequestFunction;
import pl.pg.kyrczak.jakarta.warehouse.dto.function.WarehouseToResponseFunction;
import pl.pg.kyrczak.jakarta.warehouse.dto.function.WarehousesToResponseFunction;

public class DtoFunctionFactory {
    public ParcelToResponseFunction parcelToResponseFunction() {
        return new ParcelToResponseFunction();
    }
    public ParcelsToResponseFunction parcelsToResponseFunction() {
        return new ParcelsToResponseFunction();
    }
    public RequestToParcelFunction requestToParcelFunction() {
        return new RequestToParcelFunction();
    }

    public UpdateParcelWithRequestFunction updateParcelWithRequestFunction() {
        return new UpdateParcelWithRequestFunction();
    }

    public ClientToResponseFunction clientToResponseFunction() {
        return new ClientToResponseFunction();
    }

    public ClientsToResponseFunction clientsToResponseFunction() {
        return new ClientsToResponseFunction();
    }

    public RequestToClientFunction requestToClientFunction() {
        return new RequestToClientFunction();
    }
    public UpdateClientWithRequestFucntion updateClientWithRequestFucntion() {
        return new UpdateClientWithRequestFucntion();
    }
    public UpdateClientPasswordWithRequestFunction updateClientPasswordWithRequestFunction() {
        return new UpdateClientPasswordWithRequestFunction();
    }

    public WarehouseToResponseFunction warehouseToResponseFunction() {
        return new WarehouseToResponseFunction();
    }

    public WarehousesToResponseFunction warehousesToResponseFunction() {
        return new WarehousesToResponseFunction();
    }

    public RequestToWarehouseFunction requestToWarehouseFunction() {
        return new RequestToWarehouseFunction();
    }

    public UpdateWarehouseWithRequestFunction updateWarehouseWithRequestFunction() {
        return new UpdateWarehouseWithRequestFunction();
    }
}
