package pl.pg.kyrczak.jakarta.parcel.view;

import jakarta.ejb.EJB;
import jakarta.enterprise.context.Conversation;
import jakarta.enterprise.context.ConversationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.servlet.http.Part;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.java.Log;
import pl.pg.kyrczak.jakarta.component.ModelFunctionFactory;
import pl.pg.kyrczak.jakarta.parcel.entity.ParcelStatus;
import pl.pg.kyrczak.jakarta.parcel.model.ParcelCreateModel;
import pl.pg.kyrczak.jakarta.parcel.service.ParcelService;
import pl.pg.kyrczak.jakarta.warehouse.model.WarehouseModel;
import pl.pg.kyrczak.jakarta.warehouse.service.WarehouseService;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@ConversationScoped
@Named
@Log
@NoArgsConstructor(force = true)
public class ParcelCreate implements Serializable {
    private ParcelService parcelService;
    private WarehouseService warehouseService;
    private final ModelFunctionFactory factory;

    @Getter
    private ParcelCreateModel parcel;

    @Getter
    private List<WarehouseModel> warehouses;

    private final Conversation conversation;

    @Inject
    public ParcelCreate(
            ModelFunctionFactory factory,
            Conversation conversation
    ) {
        this.factory = factory;
        this.conversation = conversation;
    }

    @EJB
    public void setParcelService(ParcelService parcelService) {
        this.parcelService = parcelService;
    }

    @EJB
    public void setWarehouseService(WarehouseService warehouseService) {
        this.warehouseService = warehouseService;
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
        Part image = parcel.getImage();
        if (image != null) {
            try (InputStream inputStream = image.getInputStream()) {
                parcelService.uploadImage(parcel.getUuid(), inputStream);
            } catch (IOException e) {
                log.severe("Failed to upload image: " + e.getMessage());
                return null; // Stay on the page if there's an error
            }
        }
        conversation.end();
        return "/parcel/parcel_list.xhtml?faces-redirect=true";
    }

    public String getParcelImageUrl() {
        if (parcel.getImagePath() != null) {
            // Assuming the images are served from a folder under /resources or /uploads
            return "/uploads/" + parcel.getImagePath();
        }
        return "/resources/images/placeholder.png";  // Fallback if no image is uploaded
    }

    public void resetImageAction() {
        parcel.setImage(null);
    }
}
