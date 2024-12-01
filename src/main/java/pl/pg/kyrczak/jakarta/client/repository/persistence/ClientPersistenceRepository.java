package pl.pg.kyrczak.jakarta.client.repository.persistence;

import jakarta.enterprise.context.Dependent;
import jakarta.enterprise.context.RequestScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import pl.pg.kyrczak.jakarta.client.entity.Client;
import pl.pg.kyrczak.jakarta.client.entity.Client_;
import pl.pg.kyrczak.jakarta.client.repository.api.ClientRepository;
import pl.pg.kyrczak.jakarta.parcel.entity.Parcel;
import pl.pg.kyrczak.jakarta.parcel.entity.Parcel_;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Dependent
public class ClientPersistenceRepository implements ClientRepository {
    private EntityManager em;

    @PersistenceContext
    public void setEm(EntityManager em) {
        this.em = em;
    }
    @Override
    public Optional<Client> findByLogin(String login) {
        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Client> query = cb.createQuery(Client.class);
            Root<Client> root = query.from(Client.class);
            query.select(root)
                    .where(cb.equal(root.get(Client_.login),login));
            return Optional.of(em.createQuery(query).getSingleResult());
        } catch (NoResultException ex) {
            return Optional.empty();
        }

    }

    @Override
    public Optional<Client> find(UUID id) {
        return Optional.ofNullable(em.find(Client.class,id));
    }

    @Override
    public List<Client> findAll() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Client> query = cb.createQuery(Client.class);
        Root<Client> root = query.from(Client.class);
        query.select(root);
        return em.createQuery(query).getResultList();
    }

    @Override
    public void create(Client entity) {
        if (!em.isJoinedToTransaction()) {
            em.joinTransaction();
        }
        em.persist(entity);
    }

    @Override
    public void delete(Client entity) {
        if (!em.isJoinedToTransaction()) {
            em.joinTransaction();
        }
        em.remove(em.find(Client.class, entity.getUuid()));
    }

    @Override
    public void update(Client entity) {
        if (!em.isJoinedToTransaction()) {
            em.joinTransaction();
        }
        em.merge(entity);
    }
}
