package pl.pg.kyrczak.jakarta.warehouse.repository.persistence;

import jakarta.enterprise.context.Dependent;
import jakarta.enterprise.context.RequestScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import pl.pg.kyrczak.jakarta.warehouse.entity.Warehouse;
import pl.pg.kyrczak.jakarta.warehouse.repository.api.WarehouseRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Dependent
public class WarehousePersistenceRepository implements WarehouseRepository {
    private EntityManager em;

    @PersistenceContext
    public void setEm(EntityManager em) {
        this.em = em;
    }

    @Override
    public Optional<Warehouse> find(UUID id) {
        return Optional.ofNullable(em.find(Warehouse.class, id));
    }

    @Override
    public List<Warehouse> findAll() {
        return em.createQuery("select u from Warehouse u", Warehouse.class).getResultList();
    }

    @Override
    public void create(Warehouse entity) {
        em.persist(entity);
    }

    @Override
    public void delete(Warehouse entity) {
        em.refresh(em.find(Warehouse.class, entity.getUuid()));
        em.remove(em.find(Warehouse.class, entity.getUuid()));
    }

    @Override
    public void update(Warehouse entity) {
        em.merge(entity);
    }
}
