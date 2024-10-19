package pl.pg.kyrczak.jakarta.parcel.view;

import jakarta.enterprise.context.Conversation;
import jakarta.enterprise.context.ConversationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.java.Log;
import pl.pg.kyrczak.jakarta.component.ModelFunctionFactory;
import pl.pg.kyrczak.jakarta.parcel.entity.ParcelStatus;
import pl.pg.kyrczak.jakarta.parcel.model.ParcelCreateModel;
import pl.pg.kyrczak.jakarta.parcel.service.ParcelService;
import pl.pg.kyrczak.jakarta.warehouse.model.WarehouseModel;
import pl.pg.kyrczak.jakarta.warehouse.service.WarehouseService;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@ConversationScoped
@Named
@Log
@NoArgsConstructor(force = true)
public class ParcelCreate implements Serializable {
    private final ParcelService parcelService;
    private final WarehouseService warehouseService;
    private final ModelFunctionFactory factory;

    @Getter
    private ParcelCreateModel parcel;

    @Getter
    private List<WarehouseModel> warehouses;

    private final Conversation conversation;


    @Inject
    public ParcelCreate(
            ParcelService parcelService,
            WarehouseService warehouseService,
            ModelFunctionFactory factory,
            Conversation conversation
    ) {
        this.parcelService = parcelService;
        this.warehouseService = warehouseService;
        this.factory = factory;
        this.conversation = conversation;
    }

    public void init() {
        if(conversation.isTransient()) {
            warehouses = warehouseService.findAll().stream()
                    .map(factory.warehouseToModelFunction())
                    .collect(Collectors.toList());
            parcel = ParcelCreateModel.builder()
                    .uuid(UUID.randomUUID())
                    .build();
            conversation.begin();
        }
    }

    public List<ParcelStatus> getStatusOptions() {
        return List.of(ParcelStatus.values());
    }
    public String goToWarehouseAction() {
        return "/parcel/parcel_create__warehouse.xhtml?faces-redirect=true";
    }
    public String goToImageAction() {
        return "/parcel/parcel_create__image.xhtml?faces-redirect=true";
    }

    public Object goToBasicAction() {
        return "/parcel/parcel_create__basic.xhtml?faces-redirect=true";
    }

    public String cancelAction() {
        conversation.end();
        return "/parcel/parcel_list.xhtml?faces-redirect=true";
    }

    public String goToConfirmAction() {
        return "/parcel/parcel_create__confirm.xhtml?faces-redirect=true";
    }

    public String saveAction() {
        parcelService.create(factory.modelToParcelFunction().apply(parcel));
        conversation.end();
        return "/parcel/parcel_list.xhtml?faces-redirect=true";
    }

}
