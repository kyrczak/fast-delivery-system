package pl.pg.kyrczak.jakarta.parcel.repository.api;

import pl.pg.kyrczak.jakarta.client.entity.Client;
import pl.pg.kyrczak.jakarta.parcel.entity.Parcel;
import pl.pg.kyrczak.jakarta.parcel.entity.ParcelStatus;
import pl.pg.kyrczak.jakarta.repository.api.Repository;
import pl.pg.kyrczak.jakarta.warehouse.entity.Warehouse;

import javax.swing.text.html.Option;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ParcelRepository extends Repository<Parcel, UUID> {
    Optional<Parcel> findByUuidAndClient(UUID uuid, Client client);
    List<Parcel> findAllByDeliveryDate(LocalDate deliveryDate);
    List<Parcel> findAllByStatus(ParcelStatus status);

    List<Parcel> findAllByWarehouse(Warehouse warehouse);
    List<Parcel> findAllByClient(Client client);

}
