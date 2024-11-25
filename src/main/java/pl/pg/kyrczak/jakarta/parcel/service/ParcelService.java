package pl.pg.kyrczak.jakarta.parcel.service;

import jakarta.annotation.security.RolesAllowed;
import jakarta.ejb.EJBAccessException;
import jakarta.ejb.LocalBean;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.ws.rs.NotFoundException;
import jakarta.security.enterprise.SecurityContext;
import lombok.NoArgsConstructor;
import pl.pg.kyrczak.jakarta.authorization.interceptor.binding.AllowRoles;
import pl.pg.kyrczak.jakarta.client.entity.Client;
import pl.pg.kyrczak.jakarta.client.entity.ClientRoles;
import pl.pg.kyrczak.jakarta.client.repository.api.ClientRepository;
import pl.pg.kyrczak.jakarta.interceptor.binding.OperationLog;
import pl.pg.kyrczak.jakarta.parcel.entity.Parcel;
import pl.pg.kyrczak.jakarta.parcel.entity.ParcelStatus;
import pl.pg.kyrczak.jakarta.parcel.repository.api.ParcelRepository;
import pl.pg.kyrczak.jakarta.warehouse.entity.Warehouse;
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
    private final SecurityContext securityContext;
    private final String imageDirectory;

    @Inject
    public ParcelService(ParcelRepository parcelRepository,
                         ClientRepository clientRepository,
                         WarehouseRepository warehouseRepository,
                         @SuppressWarnings("CdiInjectionPointsInspection") SecurityContext securityContext) {
        this.parcelRepository = parcelRepository;
        this.clientRepository = clientRepository;
        this.warehouseRepository = warehouseRepository;
        this.securityContext = securityContext;
        this.imageDirectory = "../../../../../../src/images";
    }

    @AllowRoles(ClientRoles.USER)
    public Optional<Parcel> find(UUID uuid) {
        return parcelRepository.find(uuid);
    }

    @AllowRoles(ClientRoles.USER)
    public Optional<Parcel> find(Client client, UUID uuid) {
        return parcelRepository.findByUuidAndClient(uuid, client);
    }

    @AllowRoles(ClientRoles.USER)
    public Optional<Parcel> findForCallerPrincipal(UUID id) {
        if (securityContext.isCallerInRole(ClientRoles.ADMIN)) {
            return find(id);
        }
        Client client = clientRepository.findByLogin(securityContext.getCallerPrincipal().getName())
                .orElseThrow(IllegalStateException::new);
        return find(client, id);
    }

    @AllowRoles(ClientRoles.USER)
    public List<Parcel> findAll() {
        return parcelRepository.findAll();
    }

    @AllowRoles(ClientRoles.USER)
    public List<Parcel> findAll(Client client) {
        return parcelRepository.findAllByClient(client);
    }
    @AllowRoles(ClientRoles.USER)
    public List<Parcel> findAll(LocalDate date) {
        return parcelRepository.findAllByDeliveryDate(date);
    }

    @AllowRoles(ClientRoles.USER)
    public List<Parcel> findAll(ParcelStatus status) {
        return parcelRepository.findAllByStatus(status);
    }

    @AllowRoles(ClientRoles.USER)
    public List<Parcel> findAllForCallerPrincipal() {
        if (securityContext.isCallerInRole(ClientRoles.ADMIN)) {
            return findAll();
        }
        Client client = clientRepository.findByLogin(securityContext.getCallerPrincipal().getName())
                .orElseThrow(IllegalStateException::new);
        return findAll(client);
    }

    @AllowRoles(ClientRoles.USER)
    public List<Parcel> findAllForCallerPrincipalAndRepository(UUID warehouseUuid) {
        if (securityContext.isCallerInRole(ClientRoles.ADMIN)) {
            return findAllByWarehouse(warehouseUuid);
        }
        Client client = clientRepository.findByLogin(securityContext.getCallerPrincipal().getName())
                .orElseThrow(IllegalStateException::new);
        Warehouse warehouse = warehouseRepository.find(warehouseUuid).orElseThrow(IllegalStateException::new);
        return findAllByWarehouse(client, warehouse);
    }

    @AllowRoles(ClientRoles.ADMIN)
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

    @OperationLog
    @AllowRoles(ClientRoles.USER)
    public void createForCallerPrincipal(Parcel parcel) {
        Client client = clientRepository.findByLogin(securityContext.getCallerPrincipal().getName())
                .orElseThrow(IllegalStateException::new);

        parcel.setClient(client);
        create(parcel);
    }


    @OperationLog
    @AllowRoles(ClientRoles.USER)
    public void update(Parcel parcel) {
        checkAdminRoleOrOwner(parcelRepository.find(parcel.getUuid()));
        parcelRepository.update(parcel);
    }

    @OperationLog
    @AllowRoles(ClientRoles.USER)
    public void delete(UUID uuid) {
        checkAdminRoleOrOwner(parcelRepository.find(uuid));
        parcelRepository.find(uuid).ifPresent(parcel -> warehouseRepository.find(
                parcel.getWarehouse().getUuid()).ifPresent(
                        warehouse -> warehouse.getParcels().remove(parcel)));
        parcelRepository.delete(parcelRepository.find(uuid).orElseThrow());

    }

    @AllowRoles(ClientRoles.USER)
    public List<Parcel> findAllByWarehouse(UUID uuid) {
        return parcelRepository.findAllByWarehouse(uuid);
    }

    @AllowRoles(ClientRoles.USER)
    public List<Parcel> findAllByWarehouse(Client client, Warehouse warehouse) {
        return parcelRepository.findAllByWarehouseAndClient(warehouse,client);
    }
    @AllowRoles(ClientRoles.USER)
    public Optional<List<Parcel>> findAllByClient(UUID uuid) {
        return clientRepository.find(uuid)
                .map(parcelRepository::findAllByClient);
    }

    @AllowRoles(ClientRoles.USER)
    public void uploadImage(UUID uuid, InputStream inputStream) throws IOException{
        checkAdminRoleOrOwner(parcelRepository.find(uuid));
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

    @AllowRoles(ClientRoles.USER)
    public void overwriteImage(UUID uuid, InputStream imageStream) throws IOException {
        checkAdminRoleOrOwner(parcelRepository.find(uuid));
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

    @AllowRoles(ClientRoles.USER)
    public byte[] downloadImage(UUID uuid) throws IOException {
        checkAdminRoleOrOwner(parcelRepository.find(uuid));
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

    @AllowRoles(ClientRoles.USER)
    public void deleteImage(UUID uuid) throws IOException {
        checkAdminRoleOrOwner(parcelRepository.find(uuid));
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

    private void checkAdminRoleOrOwner(Optional<Parcel> parcel) throws EJBAccessException {
        if (securityContext.isCallerInRole(ClientRoles.ADMIN)) {
            return;
        }
        if (securityContext.isCallerInRole(ClientRoles.USER)
                && parcel.isPresent()
                && parcel.get().getClient().getLogin().equals(securityContext.getCallerPrincipal().getName())) {
            return;
        }
        throw new EJBAccessException("Caller not authorized.");
    }

}
