package pl.pg.kyrczak.jakarta.warehouse.repository.persistence;

import jakarta.enterprise.context.Dependent;
import jakarta.enterprise.context.RequestScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import pl.pg.kyrczak.jakarta.parcel.entity.Parcel;
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
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Warehouse> query = cb.createQuery(Warehouse.class);
        Root<Warehouse> root = query.from(Warehouse.class);
        query.select(root);
        return em.createQuery(query).getResultList();
    }

    @Override
    public void create(Warehouse entity) {
        if (!em.isJoinedToTransaction()) {
            em.joinTransaction();
        }
        em.persist(entity);
    }

    @Override
    public void delete(Warehouse entity) {
        if (!em.isJoinedToTransaction()) {
            em.joinTransaction();
        }
        em.refresh(em.find(Warehouse.class, entity.getUuid()));
        em.remove(em.find(Warehouse.class, entity.getUuid()));
    }

    @Override
    public void update(Warehouse entity) {
        if (!em.isJoinedToTransaction()) {
            em.joinTransaction();
        }
        em.merge(entity);
    }
}
