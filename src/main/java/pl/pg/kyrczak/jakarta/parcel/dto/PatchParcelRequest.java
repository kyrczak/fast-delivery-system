package pl.pg.kyrczak.jakarta.parcel.dto;

import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class PatchParcelRequest {
    private String status;
    private LocalDate deliveryDate;
}
