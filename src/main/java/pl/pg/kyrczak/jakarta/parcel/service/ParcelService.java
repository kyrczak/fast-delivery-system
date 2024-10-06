package pl.pg.kyrczak.jakarta.parcel.service;

import pl.pg.kyrczak.jakarta.client.repository.api.ClientRepository;
import pl.pg.kyrczak.jakarta.parcel.entity.Parcel;
import pl.pg.kyrczak.jakarta.parcel.entity.ParcelStatus;
import pl.pg.kyrczak.jakarta.parcel.repository.api.ParcelRepository;
import pl.pg.kyrczak.jakarta.warehouse.repository.api.WarehouseRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class ParcelService {
    private final ParcelRepository parcelRepository;
    private final ClientRepository clientRepository;
    private final WarehouseRepository warehouseRepository;

    public ParcelService(ParcelRepository parcelRepository,
                         ClientRepository clientRepository,
                         WarehouseRepository warehouseRepository) {
        this.parcelRepository = parcelRepository;
        this.clientRepository = clientRepository;
        this.warehouseRepository = warehouseRepository;
    }

    public Optional<Parcel> find(UUID uuid) {
        return parcelRepository.find(uuid);
    }

    public List<Parcel> findAll() {
        return parcelRepository.findAll();
    }

    public List<Parcel> findAll(LocalDate date) {
        return parcelRepository.findAllByDeliveryDate(date);
    }

    public List<Parcel> findAll(ParcelStatus status) {
        return parcelRepository.findAllByStatus(status);
    }

    public void create(Parcel parcel) {
        parcelRepository.create(parcel);
    }

    public void update(Parcel parcel) {
        parcelRepository.update(parcel);
    }

    public void delete(UUID uuid) {
        parcelRepository.delete(parcelRepository.find(uuid).orElseThrow());
    }

    public Optional<List<Parcel>> findAllByWarehouse(UUID uuid) {
        return warehouseRepository.find(uuid)
                .map(parcelRepository::findAllByWarehouse);
    }

    public Optional<List<Parcel>> findAllByClient(UUID uuid) {
        return clientRepository.find(uuid)
                .map(parcelRepository::findAllByClient);
    }


    //TODO - Implement portrait methods

}
