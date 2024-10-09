package pl.pg.kyrczak.jakarta.warehouse.service;

import pl.pg.kyrczak.jakarta.warehouse.entity.Warehouse;
import pl.pg.kyrczak.jakarta.warehouse.repository.api.WarehouseRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class WarehouseService {
    private final WarehouseRepository warehouseRepository;

    public WarehouseService(WarehouseRepository warehouseRepository) {
        this.warehouseRepository = warehouseRepository;
    }

    public Optional<Warehouse> find(UUID uuid) {
        return warehouseRepository.find(uuid);
    }

    public List<Warehouse> findAll() {
        return warehouseRepository.findAll();
    }

    public void create(Warehouse warehouse) {
        warehouseRepository.create(warehouse);
    }
    public void update(Warehouse warehouse) {
        warehouseRepository.update(warehouse);
    }

    public void delete(UUID uuid) {
        warehouseRepository.delete(warehouseRepository.find(uuid).orElseThrow());
    }
}
