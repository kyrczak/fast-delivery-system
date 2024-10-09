package pl.pg.kyrczak.jakarta.warehouse.repository.memory;

import pl.pg.kyrczak.jakarta.datastore.component.DataStore;
import pl.pg.kyrczak.jakarta.warehouse.entity.Warehouse;
import pl.pg.kyrczak.jakarta.warehouse.repository.api.WarehouseRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class WarehouseInMemoryRepository implements WarehouseRepository {

    private final DataStore store;

    public WarehouseInMemoryRepository(DataStore store) {
        this.store = store;
    }
    @Override
    public Optional<Warehouse> find(UUID uuid) {
        return store.findAllWarehouses().stream()
                .filter(warehouse -> warehouse.getUuid().equals(uuid))
                .findFirst();
    }

    @Override
    public List<Warehouse> findAll() {
        return store.findAllWarehouses();
    }

    @Override
    public void create(Warehouse entity) {
        store.createWarehouse(entity);
    }

    @Override
    public void delete(Warehouse entity) {
        store.deleteWarehouse(entity.getUuid());
    }

    @Override
    public void update(Warehouse entity) {
        store.updateWarehouse(entity);
    }
}
