package pl.pg.kyrczak.jakarta.parcel.controller.simple;

import pl.pg.kyrczak.jakarta.component.DtoFunctionFactory;
import pl.pg.kyrczak.jakarta.controller.servlet.exception.BadRequestException;
import pl.pg.kyrczak.jakarta.controller.servlet.exception.NotFoundException;
import pl.pg.kyrczak.jakarta.parcel.controller.api.ParcelController;
import pl.pg.kyrczak.jakarta.parcel.dto.GetParcelResponse;
import pl.pg.kyrczak.jakarta.parcel.dto.GetParcelsResponse;
import pl.pg.kyrczak.jakarta.parcel.dto.PatchParcelRequest;
import pl.pg.kyrczak.jakarta.parcel.dto.PutParcelRequest;
import pl.pg.kyrczak.jakarta.parcel.service.ParcelService;


import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

public class ParcelSimpleController implements ParcelController {

    private final ParcelService service;
    private final DtoFunctionFactory factory;

    public ParcelSimpleController(ParcelService service, DtoFunctionFactory factory) {
        this.service = service;
        this.factory = factory;
    }
    @Override
    public GetParcelsResponse getParcels() {
        return factory.parcelsToResponseFunction().apply(service.findAll());
    }

    @Override
    public GetParcelsResponse getWarehouseParcels(UUID uuid) {
        return service.findAllByWarehouse(uuid)
                .map(factory.parcelsToResponseFunction())
                .orElseThrow(NotFoundException::new);
    }

    @Override
    public GetParcelsResponse getClientParcels(UUID uuid) {
        return service.findAllByClient(uuid)
                .map(factory.parcelsToResponseFunction())
                .orElseThrow(NotFoundException::new);
    }

    @Override
    public GetParcelResponse getParcel(UUID uuid) {
        return service.find(uuid)
                .map(factory.parcelToResponseFunction())
                .orElseThrow(NotFoundException::new);
    }

    @Override
    public void putParcel(UUID uuid, PutParcelRequest request) {
        try {
            service.create(factory.requestToParcelFunction().apply(uuid,request));
        } catch (IllegalArgumentException ex) {
            throw new BadRequestException(ex);
        }
    }

    @Override
    public void patchParcel(UUID uuid, PatchParcelRequest request) {
        service.find(uuid).ifPresentOrElse(
                entity -> service.update(factory.updateParcelWithRequestFunction().apply(entity,request)),
                () -> {
                    throw new NotFoundException();
                }
        );
    }

    @Override
    public void deleteParcel(UUID uuid) {
        service.find(uuid).ifPresentOrElse(
                entity -> service.delete(uuid),
                () -> {
                    throw new NotFoundException();
                }
        );
    }

    @Override
    public byte[] getParcelImage(UUID uuid) {
        try {
            return service.downloadImage(uuid);
        } catch (IOException ex) {
            throw new NotFoundException();
        }
    }

    @Override
    public void putParcelImage(UUID uuid, InputStream image) {
        try {
            service.uploadImage(uuid, image);
        } catch (IOException e) {
            throw new BadRequestException();
        } catch (IllegalStateException ex) {
            throw new BadRequestException();
        }
    }

    @Override
    public void deleteParcelImage(UUID uuid) {
        try {
            service.deleteImage(uuid);
        } catch (IOException e) {
            throw new NotFoundException();
        }
    }

    @Override
    public void patchParcelImage(UUID uuid, InputStream image) {
        try {
            service.overwriteImage(uuid, image);
        } catch (IOException e) {
            throw new NotFoundException();
        }
    }

}
