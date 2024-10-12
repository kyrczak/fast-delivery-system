package pl.pg.kyrczak.jakarta.datastore.component;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lombok.NoArgsConstructor;
import lombok.extern.java.Log;
import pl.pg.kyrczak.jakarta.client.entity.Client;
import pl.pg.kyrczak.jakarta.parcel.entity.Parcel;
import pl.pg.kyrczak.jakarta.serialization.component.CloningUtility;
import pl.pg.kyrczak.jakarta.warehouse.entity.Warehouse;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Log
@ApplicationScoped
@NoArgsConstructor(force = true)
public class DataStore {
    private final Set<Warehouse> warehouses = new HashSet<>();
    private final Set<Parcel> parcels = new HashSet<>();
    private final Set<Client> clients = new HashSet<>();
    private final CloningUtility cloningUtility;

    @Inject
    public DataStore(CloningUtility cloningUtility) {
        this.cloningUtility = cloningUtility;
    }

    public synchronized List<Client> findAllClients() {
        return clients.stream()
                .map(cloningUtility::clone)
                .collect(Collectors.toList());
    }

    public synchronized void createClient(Client value) throws IllegalArgumentException {
        if (clients.stream().anyMatch(character -> character.getUuid().equals(value.getUuid()))) {
            throw new IllegalArgumentException("The user id \"%s\" is not unique".formatted(value.getUuid()));
        }
        clients.add(cloningUtility.clone(value));
    }

    public synchronized void updateClient(Client value) throws IllegalArgumentException {
        if (clients.removeIf(character -> character.getUuid().equals(value.getUuid()))) {
            clients.add(cloningUtility.clone(value));
        } else {
            throw new IllegalArgumentException("The user with id \"%s\" does not exist".formatted(value.getUuid()));
        }
    }

    public synchronized List<Parcel> findAllParcels() {
        return parcels.stream()
                .map(cloningUtility::clone)
                .collect(Collectors.toList());
    }

    public synchronized void createParcel(Parcel value) throws IllegalArgumentException {
        if (parcels.stream().anyMatch(parcel -> parcel.getUuid().equals(value.getUuid()))) {
            throw new IllegalArgumentException("The parcel id \"%s\" is not unique".formatted(value.getUuid()));
        }
        Parcel entity = cloneWithRelationships(value);
        parcels.add(entity);
    }

    public synchronized void updateParcel(Parcel value) throws IllegalArgumentException {
        Parcel entity = cloneWithRelationships(value);
        if (parcels.removeIf(parcel -> parcel.getUuid().equals(value.getUuid()))) {
            parcels.add(entity);
        } else {
            throw new IllegalArgumentException("The parcel with id \"%s\" does not exist".formatted(value.getUuid()));
        }
    }

    public synchronized void deleteParcel(UUID id) throws IllegalArgumentException {
        if (!parcels.removeIf(parcel -> parcel.getUuid().equals(id))) {
            throw new IllegalArgumentException("The parcel with id \"%s\" does not exist".formatted(id));
        }
    }

    private Parcel cloneWithRelationships(Parcel value) {
        Parcel entity = cloningUtility.clone(value);

        if (entity.getClient() != null) {
            entity.setClient(clients.stream()
                    .filter(client -> client.getUuid().equals(value.getClient().getUuid()))
                    .findFirst()
                    .orElseThrow(() -> new IllegalArgumentException("No client with id \"%s\".".formatted(value.getClient().getUuid()))));
        }

        if (entity.getWarehouse() != null) {
            entity.setWarehouse(warehouses.stream()
                    .filter(warehouse -> warehouse.getUuid().equals(value.getWarehouse().getUuid()))
                    .findFirst()
                    .orElseThrow(() -> new IllegalArgumentException("No warehouse with id \"%s\".".formatted(value.getWarehouse().getUuid()))));
        }

        return entity;
    }

    public synchronized List<Warehouse> findAllWarehouses() {
        return warehouses.stream()
                .map(cloningUtility::clone)
                .collect(Collectors.toList());
    }

    public synchronized void createWarehouse(Warehouse value) throws IllegalArgumentException {
        if (warehouses.stream().anyMatch(warehouse -> warehouse.getUuid().equals(value.getUuid()))) {
            throw new IllegalArgumentException("The warehouse id \"%s\" is not unique".formatted(value.getUuid()));
        }
        warehouses.add(cloningUtility.clone(value));
    }

    public synchronized void updateWarehouse(Warehouse value) throws IllegalArgumentException {
        if (warehouses.removeIf(warehouse -> warehouse.getUuid().equals(value.getUuid()))) {
            warehouses.add(cloningUtility.clone(value));
        } else {
            throw new IllegalArgumentException("The warehouse with id \"%s\" does not exist".formatted(value.getUuid()));
        }
    }

    public synchronized void deleteWarehouse(UUID id) throws IllegalArgumentException {
        if (!warehouses.removeIf(warehouse -> warehouse.getUuid().equals(id))) {
            throw new IllegalArgumentException("The parcel with id \"%s\" does not exist".formatted(id));
        }
    }
}
