package pl.pg.kyrczak.jakarta.parcel.controller.api;

import pl.pg.kyrczak.jakarta.parcel.dto.GetParcelResponse;
import pl.pg.kyrczak.jakarta.parcel.dto.GetParcelsResponse;
import pl.pg.kyrczak.jakarta.parcel.dto.PatchParcelRequest;
import pl.pg.kyrczak.jakarta.parcel.dto.PutParcelRequest;

import java.io.InputStream;
import java.nio.file.Path;
import java.util.UUID;

public interface ParcelController {
    GetParcelsResponse getParcels();
    GetParcelsResponse getWarehouseParcels(UUID uuid);
    GetParcelsResponse getClientParcels(UUID uuid);

    GetParcelResponse getParcel(UUID uuid);
    void putParcel(UUID uuid, PutParcelRequest request);
    void patchParcel(UUID uuid, PatchParcelRequest request);
    void deleteParcel(UUID uuid);
    byte[] getParcelImage(UUID uuid);
    void putParcelImage(UUID uuid, InputStream image);

    void deleteParcelImage(UUID uuid);

    void patchParcelImage(UUID uuid, InputStream image);
}
