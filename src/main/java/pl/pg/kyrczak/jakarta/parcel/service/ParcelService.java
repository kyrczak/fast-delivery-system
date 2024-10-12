package pl.pg.kyrczak.jakarta.parcel.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lombok.NoArgsConstructor;
import pl.pg.kyrczak.jakarta.client.repository.api.ClientRepository;
import pl.pg.kyrczak.jakarta.controller.servlet.exception.NotFoundException;
import pl.pg.kyrczak.jakarta.parcel.entity.Parcel;
import pl.pg.kyrczak.jakarta.parcel.entity.ParcelStatus;
import pl.pg.kyrczak.jakarta.parcel.producer.api.ImageDirectory;
import pl.pg.kyrczak.jakarta.parcel.repository.api.ParcelRepository;
import pl.pg.kyrczak.jakarta.warehouse.repository.api.WarehouseRepository;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@ApplicationScoped
@NoArgsConstructor(force = true)
public class ParcelService {
    private final ParcelRepository parcelRepository;
    private final ClientRepository clientRepository;
    private final WarehouseRepository warehouseRepository;

    private final Path imageDirectory;

    @Inject
    public ParcelService(ParcelRepository parcelRepository,
                         ClientRepository clientRepository,
                         WarehouseRepository warehouseRepository,
                         @ImageDirectory Path imageDirectory) {
        this.parcelRepository = parcelRepository;
        this.clientRepository = clientRepository;
        this.warehouseRepository = warehouseRepository;
        this.imageDirectory = imageDirectory;
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

    public void uploadImage(UUID uuid, InputStream inputStream) throws IOException{
        Path parcelImagePath = imageDirectory.resolve(uuid + ".png");
        if (Files.exists(parcelImagePath)) {
            throw new IllegalStateException();
        }
        Files.copy(inputStream, parcelImagePath);
    }

    public void overwriteImage(UUID uuid, InputStream imageStream) throws IOException {
        Parcel parcel = parcelRepository.find(uuid).orElseThrow(
                NotFoundException::new
        );
        Path parcelImagePath = imageDirectory.resolve(parcel.getUuid() + ".png");
        if (!Files.exists(parcelImagePath)) {
            throw new IllegalStateException();
        }
        Files.copy(imageStream, parcelImagePath, StandardCopyOption.REPLACE_EXISTING);
    }

    public byte[] downloadImage(UUID uuid) throws IOException {
        Path parcelImagePath = imageDirectory.resolve(uuid + ".png");
        if (!Files.exists(parcelImagePath)) {
            throw new NoSuchFileException("Avatar not found");
        }
        return Files.readAllBytes(parcelImagePath);
    }

    public void deleteImage(UUID uuid) throws IOException {
        Path parcelImagePath = imageDirectory.resolve(uuid + ".png");
        Files.deleteIfExists(parcelImagePath);
    }
}
