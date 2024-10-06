package pl.pg.kyrczak.jakarta.warehouse.dto;

import lombok.*;
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class PatchWarehouseRequest {
    private String name;
    private String location;
}
