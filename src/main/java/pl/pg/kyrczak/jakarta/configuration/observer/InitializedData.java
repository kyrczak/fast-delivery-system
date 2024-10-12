package pl.pg.kyrczak.jakarta.configuration.observer;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.Initialized;
import jakarta.enterprise.context.control.RequestContextController;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import lombok.SneakyThrows;
import pl.pg.kyrczak.jakarta.client.entity.Client;
import pl.pg.kyrczak.jakarta.client.service.ClientService;
import pl.pg.kyrczak.jakarta.parcel.entity.Parcel;
import pl.pg.kyrczak.jakarta.parcel.entity.ParcelStatus;
import pl.pg.kyrczak.jakarta.parcel.service.ParcelService;
import pl.pg.kyrczak.jakarta.warehouse.entity.Warehouse;
import pl.pg.kyrczak.jakarta.warehouse.service.WarehouseService;

import java.io.InputStream;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.UUID;

@ApplicationScoped
public class InitializedData {
    private final ParcelService parcelService;

    /**
     * User service.
     */
    private final ClientService clientService;

    /**
     * Profession service.
     */
    private final WarehouseService warehouseService;

    private final RequestContextController requestContextController;

    @Inject
    public InitializedData(
            ParcelService parcelService,
            ClientService clientService,
            WarehouseService warehouseService,
            RequestContextController requestContextController
    ) {
        this.parcelService = parcelService;
        this.clientService = clientService;
        this.warehouseService = warehouseService;
        this.requestContextController = requestContextController;
    }
    public void contextInitialized(@Observes @Initialized(ApplicationScoped.class) Object init) {
        init();
    }

    @SneakyThrows
    private void init() {
        requestContextController.activate();

        Client patryk = Client.builder()
                .uuid(UUID.fromString("12f2776c-f823-4aab-8eb3-0e4fd5ae32f9"))
                .login("patrykk")
                .name("Patryk")
                .surname("Korczak")
                .registrationDate(LocalDate.of(2022,12,20))
                .email("patrykk@student.pg")
                .password("useruser")
                .parcels(new ArrayList<>())
                .build();

        Client jarek = Client.builder()
                .uuid(UUID.fromString("fbdedd35-c87c-45b1-8898-87f265edaf53"))
                .login("jareks")
                .name("Jarek")
                .surname("Stoch")
                .registrationDate(LocalDate.of(2023,3,5))
                .email("jareks@student.pg")
                .password("12345678")
                .parcels(new ArrayList<>())
                .build();

        Client maria = Client.builder()
                .uuid(UUID.fromString("b7453d56-fd08-4d78-825d-8a4b71cd1514"))
                .login("mariao")
                .name("Maria")
                .surname("Orłowska")
                .registrationDate(LocalDate.of(2024,9,3))
                .email("mariao@student.pg")
                .password("marioluigi")
                .parcels(new ArrayList<>())
                .build();

        Warehouse aviationGdansk = Warehouse.builder()
                .uuid(UUID.fromString("08cb187c-6c30-47bd-9931-2a8a7b907fec"))
                .name("Lotnisko Trojmiejskie")
                .location("Gdańsk")
                .establishedDate(LocalDate.of(2000,5,13))
                .parcels(new ArrayList<>())
                .build();
        Warehouse dockGdansk = Warehouse.builder()
                .uuid(UUID.fromString("baf6561e-f028-49ff-8b59-de9132b4e3dd"))
                .name("Port w Gdansku")
                .location("Gdańsk")
                .establishedDate(LocalDate.of(1969,3,3))
                .parcels(new ArrayList<>())
                .build();
        Warehouse dockGdynia = Warehouse.builder()
                .uuid(UUID.fromString("32faaa8e-dde2-4355-8b8c-9dd252fbc3b2"))
                .name("Port w Gdyni")
                .location("Gdynia")
                .establishedDate(LocalDate.of(1972,7,19))
                .parcels(new ArrayList<>())
                .build();

        Parcel paczka1 = Parcel.builder()
                .uuid(UUID.fromString("cc5dae57-9f0f-4d75-827f-879acd1a5c62"))
                .weight(13.35F)
                .status(ParcelStatus.IN_WAREHOUSE)
                .client(patryk)
                .deliveryDate(LocalDate.of(2024,10,9))
                .warehouse(dockGdynia)
                .build();
        dockGdynia.getParcels().add(paczka1);
        patryk.getParcels().add(paczka1);
        Parcel paczka2 = Parcel.builder()
                .uuid(UUID.fromString("9e9a4970-fcb0-455c-bdc2-da55ffec7366"))
                .weight(4.2F)
                .status(ParcelStatus.IN_DELIVERY)
                .client(patryk)
                .deliveryDate(LocalDate.of(2024,10,3))
                .warehouse(dockGdansk)
                .build();
        dockGdansk.getParcels().add(paczka2);
        patryk.getParcels().add(paczka2);
        Parcel paczka3 = Parcel.builder()
                .uuid(UUID.fromString("76e28a62-a12b-4ec6-9b92-d89b37df3d15"))
                .weight(1.2F)
                .status(ParcelStatus.DELIVERED)
                .client(jarek)
                .deliveryDate(LocalDate.of(2024,10,5))
                .warehouse(dockGdansk)
                .build();
        dockGdansk.getParcels().add(paczka3);
        jarek.getParcels().add(paczka3);
        Parcel paczka4 = Parcel.builder()
                .uuid(UUID.fromString("a3449c87-ac60-46b0-8341-9cacab383475"))
                .weight(20F)
                .status(ParcelStatus.IN_DELIVERY)
                .client(maria)
                .deliveryDate(LocalDate.of(2024,10,23))
                .warehouse(aviationGdansk)
                .build();
        aviationGdansk.getParcels().add(paczka4);
        maria.getParcels().add(paczka4);
        Parcel paczka5 = Parcel.builder()
                .uuid(UUID.fromString("1c023543-5bd1-4b02-9941-27aab5b2ecdb"))
                .weight(34F)
                .status(ParcelStatus.IN_WAREHOUSE)
                .client(jarek)
                .deliveryDate(LocalDate.of(2024,10,12))
                .warehouse(aviationGdansk)
                .build();
        aviationGdansk.getParcels().add(paczka5);
        jarek.getParcels().add(paczka5);

        clientService.create(patryk);
        clientService.create(jarek);
        clientService.create(maria);


        warehouseService.create(aviationGdansk);
        warehouseService.create(dockGdansk);
        warehouseService.create(dockGdynia);

        parcelService.create(paczka1);
        parcelService.create(paczka2);
        parcelService.create(paczka3);
        parcelService.create(paczka4);
        parcelService.create(paczka5);

        requestContextController.deactivate();

    }
    @SneakyThrows
    private byte[] getResourceAsByteArray(String name) {
        try (InputStream is = this.getClass().getResourceAsStream(name)) {
            if (is != null) {
                return is.readAllBytes();
            } else {
                throw new IllegalStateException("Unable to get resource %s".formatted(name));
            }
        }
    }

}
