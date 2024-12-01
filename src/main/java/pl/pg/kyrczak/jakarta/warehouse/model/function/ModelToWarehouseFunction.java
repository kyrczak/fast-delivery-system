package pl.pg.kyrczak.jakarta.warehouse.model.function;

import pl.pg.kyrczak.jakarta.parcel.entity.Parcel;
import pl.pg.kyrczak.jakarta.warehouse.entity.Warehouse;
import pl.pg.kyrczak.jakarta.warehouse.model.WarehouseCreateModel;

import java.io.Serializable;
import java.util.List;
import java.util.function.Function;

public class ModelToWarehouseFunction implements Function <WarehouseCreateModel, Warehouse>, Serializable {
    @Override
    public Warehouse apply(WarehouseCreateModel warehouseCreateModel) {
        return Warehouse.builder()
                .uuid(warehouseCreateModel.getUuid())
                .name(warehouseCreateModel.getName())
                .location(warehouseCreateModel.getLocation())
                .establishedDate(warehouseCreateModel.getEstablishedDate())
                .parcels((List<Parcel>) warehouseCreateModel.getParcels().stream()
                        .map(parcel -> Parcel.builder()
                                .uuid(parcel.getUuid())
                                .build())
                        .toList())
                .build();
    }
}
