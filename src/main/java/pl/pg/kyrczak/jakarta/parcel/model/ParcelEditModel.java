package pl.pg.kyrczak.jakarta.parcel.model;

import jakarta.servlet.http.Part;
import lombok.*;

import java.time.LocalDate;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class ParcelEditModel {
    private String status;
    private Float weight;
    private LocalDate deliveryDate;
    private Part image;
}
