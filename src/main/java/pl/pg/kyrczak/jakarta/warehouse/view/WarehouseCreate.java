package pl.pg.kyrczak.jakarta.warehouse.view;

import jakarta.enterprise.context.Conversation;
import jakarta.enterprise.context.ConversationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.java.Log;
import pl.pg.kyrczak.jakarta.component.ModelFunctionFactory;
import pl.pg.kyrczak.jakarta.warehouse.model.WarehouseCreateModel;
import pl.pg.kyrczak.jakarta.warehouse.service.WarehouseService;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.UUID;

@ConversationScoped
@Named
@Log
@NoArgsConstructor(force = true)
public class WarehouseCreate implements Serializable {
    private final WarehouseService warehouseService;
    private final ModelFunctionFactory factory;

    @Getter
    private WarehouseCreateModel warehouse;

    private final Conversation conversation;

    @Inject
    public WarehouseCreate(
            WarehouseService warehouseService,
            ModelFunctionFactory factory,
            Conversation conversation
    ) {
        this.warehouseService = warehouseService;
        this.factory = factory;
        this.conversation = conversation;
    }

    public void init() {
        if(conversation.isTransient()) {
            warehouse = WarehouseCreateModel.builder()
                    .uuid(UUID.randomUUID())
                    .parcels(new ArrayList<>())
                    .build();
            conversation.begin();
        }
    }

    public String cancelAction() {
        conversation.end();
        return  "/warehouse/warehouse_list.xhtml?faces-redirect=true";
    }

    public String saveAction() {
        warehouseService.create(factory.modelToWarehouseFunction().apply(warehouse));
        conversation.end();
        return "/warehouse/warehouse_list.xhtml?faces-redirect=true";
    }
}
