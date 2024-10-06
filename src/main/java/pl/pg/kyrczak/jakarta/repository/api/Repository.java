package pl.pg.kyrczak.jakarta.repository.api;

import java.util.List;
import java.util.Optional;

public interface Repository<E,K> {
    Optional<E> find(K uuid);
    List<E> findAll();
    void create(E entity);
    void delete(E entity);
    void update(E entity);
}
