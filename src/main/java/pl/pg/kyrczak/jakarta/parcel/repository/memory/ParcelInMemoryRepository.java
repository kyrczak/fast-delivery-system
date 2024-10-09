package pl.pg.kyrczak.jakarta.parcel.repository.memory;

import pl.pg.kyrczak.jakarta.client.entity.Client;
import pl.pg.kyrczak.jakarta.datastore.component.DataStore;
import pl.pg.kyrczak.jakarta.parcel.entity.Parcel;
import pl.pg.kyrczak.jakarta.parcel.entity.ParcelStatus;
import pl.pg.kyrczak.jakarta.parcel.repository.api.ParcelRepository;
import pl.pg.kyrczak.jakarta.warehouse.entity.Warehouse;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

public class ParcelInMemoryRepository implements ParcelRepository {

    private final DataStore store;

    public ParcelInMemoryRepository(DataStore store) {
        this.store = store;
    }
    @Override
    public List<Parcel> findAllByDeliveryDate(LocalDate deliveryDate) {
        return store.findAllParcels().stream()
                .filter(parcel -> parcel.getDeliveryDate().isEqual(deliveryDate))
                .collect(Collectors.toList());
    }

    @Override
    public List<Parcel> findAllByStatus(ParcelStatus status) {
        return store.findAllParcels().stream()
                .filter(parcel -> parcel.getStatus().equals(status))
                .collect(Collectors.toList());
    }

    @Override
    public List<Parcel> findAllByWarehouse(Warehouse warehouse) {
        return store.findAllParcels().stream()
                .filter(parcel -> warehouse.equals(parcel.getWarehouse()))
                .collect(Collectors.toList());
    }

    @Override
    public List<Parcel> findAllByClient(Client client) {
        return store.findAllParcels().stream()
                .filter(parcel -> client.equals(parcel.getClient()))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Parcel> find(UUID uuid) {
        return store.findAllParcels().stream()
                .filter(parcel -> parcel.getUuid().equals(uuid))
                .findFirst();
    }

    @Override
    public List<Parcel> findAll() {
        return store.findAllParcels();
    }

    @Override
    public void create(Parcel entity) {
        store.createParcel(entity);
    }

    @Override
    public void delete(Parcel entity) {
        store.deleteParcel(entity.getUuid());
    }

    @Override
    public void update(Parcel entity) {
        store.updateParcel(entity);
    }
}
