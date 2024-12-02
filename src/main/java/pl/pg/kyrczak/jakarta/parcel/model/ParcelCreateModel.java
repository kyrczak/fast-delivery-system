package pl.pg.kyrczak.jakarta.parcel.model;

import jakarta.servlet.http.Part;
import jakarta.validation.constraints.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import pl.pg.kyrczak.jakarta.parcel.entity.ParcelStatus;
import pl.pg.kyrczak.jakarta.warehouse.model.WarehouseModel;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class ParcelCreateModel {
    private UUID uuid;
    private ParcelStatus status;

    //custom validation
    private LocalDate deliveryDate;

    @NotNull
    @DecimalMin("0.1")
    @DecimalMax("100.5")
    private Float weight;

    @NotNull
    private Part image;
    private String imagePath;

    @NotNull
    private WarehouseModel warehouse;
}
