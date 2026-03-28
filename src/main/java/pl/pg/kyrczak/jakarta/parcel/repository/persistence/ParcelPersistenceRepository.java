package pl.pg.kyrczak.jakarta.parcel.repository.persistence;

import jakarta.enterprise.context.Dependent;
import jakarta.enterprise.context.RequestScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import pl.pg.kyrczak.jakarta.client.entity.Client;
import pl.pg.kyrczak.jakarta.parcel.entity.Parcel;
import pl.pg.kyrczak.jakarta.parcel.entity.Parcel_;
import pl.pg.kyrczak.jakarta.parcel.entity.ParcelStatus;
import pl.pg.kyrczak.jakarta.parcel.repository.api.ParcelRepository;
import pl.pg.kyrczak.jakarta.warehouse.entity.Warehouse;

import javax.swing.text.html.Option;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Dependent
public class ParcelPersistenceRepository implements ParcelRepository {
    private EntityManager em;

    @PersistenceContext
    public void setEm(EntityManager em) {
        this.em = em;
    }

    @Override
    public Optional<Parcel> findByUuidAndClient(UUID uuid, Client client) {
        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Parcel> query = cb.createQuery(Parcel.class);
            Root<Parcel> root = query.from(Parcel.class);
            query.select(root)
                    .where(cb.and(
                            cb.equal(root.get(Parcel_.client),client),
                            cb.equal(root.get(Parcel_.uuid),uuid)
                    ));
            return Optional.of(em.createQuery(query).getSingleResult());
        } catch (NoResultException ex) {
            return Optional.empty();
        }
    }

    @Override
    public List<Parcel> findAllByDeliveryDate(LocalDate deliveryDate) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Parcel> query = cb.createQuery(Parcel.class);
        Root<Parcel> root = query.from(Parcel.class);
        query.select(root)
                .where(cb.equal(root.get(Parcel_.deliveryDate),deliveryDate));
        return em.createQuery(query).getResultList();
    }

    @Override
    public List<Parcel> findAllByStatus(ParcelStatus status) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Parcel> query = cb.createQuery(Parcel.class);
        Root<Parcel> root = query.from(Parcel.class);
        query.select(root)
                .where(cb.equal(root.get(Parcel_.status),status));
        return em.createQuery(query).getResultList();
    }

    @Override
    public List<Parcel> findAllByWarehouse(UUID warehouse) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Parcel> query = cb.createQuery(Parcel.class);
        Root<Parcel> root = query.from(Parcel.class);
        query.select(root)
                .where(cb.equal(root.get(Parcel_.warehouse),warehouse));
        return em.createQuery(query).getResultList();
    }

    @Override
    public List<Parcel> findAllByClient(Client client) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Parcel> query = cb.createQuery(Parcel.class);
        Root<Parcel> root = query.from(Parcel.class);
        query.select(root)
                .where(cb.equal(root.get(Parcel_.client),client));
        return em.createQuery(query).getResultList();
    }

    @Override
    public List<Parcel> findAllByWarehouseAndClient(Warehouse warehouse, Client client) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Parcel> query = cb.createQuery(Parcel.class);
        Root<Parcel> root = query.from(Parcel.class);
        query.select(root)
                .where(cb.and(
                        cb.equal(root.get(Parcel_.client),client),
                        cb.equal(root.get(Parcel_.warehouse),warehouse)
                ));
        return em.createQuery(query).getResultList();
    }

    @Override
    public Optional<Parcel> find(UUID id) {
        return Optional.ofNullable(em.find(Parcel.class, id));
    }

    @Override
    public List<Parcel> findAll() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Parcel> query = cb.createQuery(Parcel.class);
        Root<Parcel> root = query.from(Parcel.class);
        query.select(root);
        return em.createQuery(query).getResultList();
    }

    @Override
    public void create(Parcel entity) {
//        if (!em.isJoinedToTransaction()) {
//            em.joinTransaction();
//        }
        em.persist(entity);
//        em.refresh(em.find(Warehouse.class, entity.getWarehouse().getUuid()));
    }

    @Override
    public void delete(Parcel entity) {
        if (!em.isJoinedToTransaction()) {
            em.joinTransaction();
        }
        em.remove(em.find(Parcel.class, entity.getUuid()));
    }

    @Override
    public void update(Parcel entity) {
        if (!em.isJoinedToTransaction()) {
            em.joinTransaction();
        }
        em.merge(entity);
    }
}
