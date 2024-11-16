package pl.pg.kyrczak.jakarta.client.repository.persistence;

import jakarta.enterprise.context.Dependent;
import jakarta.enterprise.context.RequestScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import pl.pg.kyrczak.jakarta.client.entity.Client;
import pl.pg.kyrczak.jakarta.client.repository.api.ClientRepository;

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
            return Optional.of(em.createQuery("select u from Client u where u.login = :login", Client.class)
                    .setParameter("login", login)
                    .getSingleResult());
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
        return em.createQuery("select u from Client u", Client.class).getResultList();
    }

    @Override
    public void create(Client entity) {
        em.persist(entity);
    }

    @Override
    public void delete(Client entity) {
        em.remove(em.find(Client.class, entity.getUuid()));
    }

    @Override
    public void update(Client entity) {
        em.merge(entity);
    }
}
