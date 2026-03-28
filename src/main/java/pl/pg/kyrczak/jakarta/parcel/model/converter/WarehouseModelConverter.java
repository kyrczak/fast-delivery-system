package pl.pg.kyrczak.jakarta.parcel.model.converter;

import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.convert.Converter;
import jakarta.faces.convert.FacesConverter;
import jakarta.inject.Inject;
import pl.pg.kyrczak.jakarta.warehouse.model.WarehouseModel;
import pl.pg.kyrczak.jakarta.warehouse.entity.Warehouse;
import pl.pg.kyrczak.jakarta.warehouse.service.WarehouseService;
import pl.pg.kyrczak.jakarta.component.ModelFunctionFactory;

import java.util.Optional;
import java.util.UUID;

/**
 * Faces converter for {@link WarehouseModel}. Allows the conversion between the string value (UUID)
 * and the {@link WarehouseModel} object using CDI managed beans.
 */
@FacesConverter(value = "warehouseModelConverter", managed = true)
public class WarehouseModelConverter implements Converter<WarehouseModel> {

    private final WarehouseService service;
    private final ModelFunctionFactory factory;

    /**
     * @param service Service for warehouse management
     * @param factory Factory for model-entity conversion functions
     */
    @Inject
    public WarehouseModelConverter(WarehouseService service, ModelFunctionFactory factory) {
        this.service = service;
        this.factory = factory;
    }

    @Override
    public WarehouseModel getAsObject(FacesContext context, UIComponent component, String value) {
        if (value == null || value.isBlank()) {
            return null;
        }
        Optional<Warehouse> warehouse = service.find(UUID.fromString(value));
        return warehouse.map(factory.warehouseToModelFunction()).orElse(null);
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, WarehouseModel value) {
        return value == null ? "" : value.getUuid().toString();
    }
}
