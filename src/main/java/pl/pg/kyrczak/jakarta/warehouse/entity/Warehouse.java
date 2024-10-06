package pl.pg.kyrczak.jakarta.warehouse.entity;

import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class Warehouse implements Serializable {
    private UUID uuid;
    private String name;
    private String location;
    private LocalDate establishedDate;
}
