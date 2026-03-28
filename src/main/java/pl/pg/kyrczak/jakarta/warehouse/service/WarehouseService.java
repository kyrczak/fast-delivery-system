package pl.pg.kyrczak.jakarta.warehouse.service;

import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.ejb.LocalBean;
import jakarta.ejb.Stateless;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import lombok.NoArgsConstructor;
import lombok.extern.java.Log;
import pl.pg.kyrczak.jakarta.authorization.interceptor.binding.AllowRoles;
import pl.pg.kyrczak.jakarta.client.entity.ClientRoles;
import pl.pg.kyrczak.jakarta.warehouse.entity.Warehouse;
import pl.pg.kyrczak.jakarta.warehouse.repository.api.WarehouseRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@LocalBean
@Stateless
@NoArgsConstructor(force = true)
@Log
public class WarehouseService {
    private final WarehouseRepository warehouseRepository;

    @Inject
    public WarehouseService(WarehouseRepository warehouseRepository) {
        this.warehouseRepository = warehouseRepository;
    }

    public Optional<Warehouse> find(UUID uuid) {
        Optional<Warehouse> warehouse = warehouseRepository.find(uuid);
        return warehouse;
    }

    @PermitAll
    public List<Warehouse> findAll() {
        return warehouseRepository.findAll();
    }

    @AllowRoles(ClientRoles.ADMIN)
    public void create(Warehouse warehouse) {
        warehouseRepository.create(warehouse);
    }

    @AllowRoles(ClientRoles.ADMIN)
    public void update(Warehouse warehouse) {
        warehouseRepository.update(warehouse);
    }

    @AllowRoles(ClientRoles.ADMIN)
    public void delete(UUID uuid) {
        warehouseRepository.delete(warehouseRepository.find(uuid).orElseThrow());
    }
}
