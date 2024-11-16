package pl.pg.kyrczak.jakarta.parcel.service;

import jakarta.ejb.LocalBean;
import jakarta.ejb.Stateless;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;
import lombok.NoArgsConstructor;
import pl.pg.kyrczak.jakarta.client.repository.api.ClientRepository;
import pl.pg.kyrczak.jakarta.parcel.entity.Parcel;
import pl.pg.kyrczak.jakarta.parcel.entity.ParcelStatus;
import pl.pg.kyrczak.jakarta.parcel.repository.api.ParcelRepository;
import pl.pg.kyrczak.jakarta.warehouse.repository.api.WarehouseRepository;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.*;
import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

@LocalBean
@Stateless
@NoArgsConstructor(force = true)
public class ParcelService {
    private final ParcelRepository parcelRepository;
    private final ClientRepository clientRepository;
    private final WarehouseRepository warehouseRepository;

    private final String imageDirectory;

    @Inject
    public ParcelService(ParcelRepository parcelRepository,
                         ClientRepository clientRepository,
                         WarehouseRepository warehouseRepository) {
        this.parcelRepository = parcelRepository;
        this.clientRepository = clientRepository;
        this.warehouseRepository = warehouseRepository;
        this.imageDirectory = "../../../../../../src/images";
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
        if(parcelRepository.find(parcel.getUuid()).isPresent()) {
            throw new IllegalArgumentException("Parcel already exists.");
        }
        if(warehouseRepository.find(parcel.getWarehouse().getUuid()).isEmpty()) {
            throw new IllegalArgumentException("Warehouse does not exist.");
        }
        parcelRepository.create(parcel);
        warehouseRepository.find(parcel.getWarehouse().getUuid())
                .ifPresent(warehouse -> warehouse.getParcels().add(parcel));
    }

    public void update(Parcel parcel) {
        parcelRepository.update(parcel);
    }

    public void delete(UUID uuid) {
        parcelRepository.find(uuid).ifPresent(parcel -> warehouseRepository.find(
                parcel.getWarehouse().getUuid()).ifPresent(
                        warehouse -> warehouse.getParcels().remove(parcel)));
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
        parcelRepository.find(uuid).ifPresent(parcel -> {
            try {
                String path = this.imageDirectory + File.separator + uuid.toString() + ".png";
                if(parcel.getImage() == null) {
                    Files.copy(inputStream,Paths.get(path));
                }
                else {
                    throw new NullPointerException();
                }
                parcel.setImage(uuid.toString()+".png");
                parcelRepository.update(parcel);
            }
            catch (IOException e) {
                throw new IllegalStateException(e);
            }
        });
    }

    public void overwriteImage(UUID uuid, InputStream imageStream) throws IOException {
        parcelRepository.find(uuid).ifPresent(parcel -> {
            try {
                String path = this.imageDirectory + File.separator +uuid.toString() +".png";
                if(parcel.getImage() == null) {
                    throw new IllegalStateException("Parcel does not have an image to update");
                }
                else {
                    Files.copy(imageStream,Paths.get(path), StandardCopyOption.REPLACE_EXISTING);
                }
                parcel.setImage(uuid.toString()+".png");
                parcelRepository.update(parcel);
            }
            catch (IOException e) {
                throw new IllegalStateException(e);
            }
        });
    }

    public byte[] downloadImage(UUID uuid) throws IOException {
        return parcelRepository.find(uuid).map(parcel -> {
            try {
                String image = parcel.getImage();
                if(image != null) {
                    String path = this.imageDirectory + File.separator + image;
                    return Files.readAllBytes(Paths.get(path));
                } else {
                    throw new NotFoundException("Image is not set");
                }
            } catch (IOException e) {
                throw new IllegalStateException(e);
            }
        }).orElseThrow(() -> new NotFoundException(("Parcel does not exist")));
    }

    public void deleteImage(UUID uuid) throws IOException {
        parcelRepository.find(uuid).ifPresent(parcel -> {
            String path = this.imageDirectory + File.separator + parcel.getImage();
            if (parcel.getImage() != null) {
                try {
                    Path filePath = Paths.get(path);
                    Files.deleteIfExists(filePath);
                } catch (IOException e) {
                    throw new IllegalStateException(e);
                }
                parcel.setImage(null);
                parcelRepository.update(parcel);
            }
            else {
                throw new NotFoundException("Parcel does not exists");
            }
        });
    }
}
