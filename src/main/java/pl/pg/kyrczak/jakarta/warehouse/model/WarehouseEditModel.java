package pl.pg.kyrczak.jakarta.warehouse.model;

import lombok.*;

import java.time.LocalDate;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class WarehouseEditModel {
    private String name;
    private String location;
    private LocalDate establishedDate;
}
