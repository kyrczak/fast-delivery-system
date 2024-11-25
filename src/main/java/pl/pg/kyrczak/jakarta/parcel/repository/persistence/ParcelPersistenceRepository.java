package pl.pg.kyrczak.jakarta.parcel.repository.persistence;

import jakarta.enterprise.context.Dependent;
import jakarta.enterprise.context.RequestScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import pl.pg.kyrczak.jakarta.client.entity.Client;
import pl.pg.kyrczak.jakarta.parcel.entity.Parcel;
import pl.pg.kyrczak.jakarta.parcel.entity.ParcelStatus;
import pl.pg.kyrczak.jakarta.parcel.repository.api.ParcelRepository;
import pl.pg.kyrczak.jakarta.warehouse.entity.Warehouse;

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
            return Optional.of(em.createQuery("select c from Parcel c where c.uuid = :uuid and c.client = :client",
                    Parcel.class)
                    .setParameter("client",client)
                    .setParameter("uuid",uuid)
                    .getSingleResult());
        } catch (NoResultException ex) {
            return Optional.empty();
        }
    }

    @Override
    public List<Parcel> findAllByDeliveryDate(LocalDate deliveryDate) {
        return em.createQuery("select c from Parcel c where c.deliveryDate = :deliveryDate",Parcel.class)
                .setParameter("deliveryDate",deliveryDate)
                .getResultList();
    }

    @Override
    public List<Parcel> findAllByStatus(ParcelStatus status) {
        return em.createQuery("select c from Parcel c where c.status = :status",Parcel.class)
                .setParameter("status",status)
                .getResultList();
    }

    @Override
    public List<Parcel> findAllByWarehouse(UUID warehouse) {
        return em.find(Warehouse.class,warehouse).getParcels();
    }

    @Override
    public List<Parcel> findAllByClient(Client client) {
        return em.createQuery("select c from Parcel c where c.client = :client",Parcel.class)
                .setParameter("client",client)
                .getResultList();
    }

    @Override
    public List<Parcel> findAllByWarehouseAndClient(Warehouse warehouse, Client client) {
        return em.createQuery("select c from Parcel c where c.client = :client and c.warehouse = :warehouse",Parcel.class)
                .setParameter("client",client)
                .setParameter("warehouse", warehouse)
                .getResultList();
    }

    @Override
    public Optional<Parcel> find(UUID id) {
        return Optional.ofNullable(em.find(Parcel.class, id));
    }

    @Override
    public List<Parcel> findAll() {
        return em.createQuery("select c from Parcel c", Parcel.class).getResultList();
    }

    @Override
    public void create(Parcel entity) {
        em.persist(entity);
        em.refresh(em.find(Warehouse.class, entity.getWarehouse().getUuid()));
    }

    @Override
    public void delete(Parcel entity) {
        em.remove(em.find(Parcel.class, entity.getUuid()));
    }

    @Override
    public void update(Parcel entity) {
        em.merge(entity);
    }
}
