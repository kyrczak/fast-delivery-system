package pl.pg.kyrczak.jakarta.configuration.listener;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import pl.pg.kyrczak.jakarta.client.repository.api.ClientRepository;
import pl.pg.kyrczak.jakarta.client.repository.memory.ClientInMemoryRepository;
import pl.pg.kyrczak.jakarta.client.service.ClientService;
import pl.pg.kyrczak.jakarta.crypto.component.Pbkdf2PasswordHash;
import pl.pg.kyrczak.jakarta.datastore.component.DataStore;
import pl.pg.kyrczak.jakarta.parcel.repository.api.ParcelRepository;
import pl.pg.kyrczak.jakarta.parcel.repository.memory.ParcelInMemoryRepository;
import pl.pg.kyrczak.jakarta.parcel.service.ParcelService;
import pl.pg.kyrczak.jakarta.warehouse.repository.api.WarehouseRepository;
import pl.pg.kyrczak.jakarta.warehouse.repository.memory.WarehouseInMemoryRepository;
import pl.pg.kyrczak.jakarta.warehouse.service.WarehouseService;

import java.nio.file.Path;
import java.nio.file.Paths;

@WebListener
public class CreateServices implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent event) {
        DataStore dataSource = (DataStore) event.getServletContext().getAttribute("datasource");
        Path imagePath = Paths.get(event.getServletContext().getInitParameter("imageDirectory"));

        ClientRepository clientRepository = new ClientInMemoryRepository(dataSource);
        WarehouseRepository warehouseRepository = new WarehouseInMemoryRepository(dataSource);
        ParcelRepository parcelRepository = new ParcelInMemoryRepository(dataSource);

        event.getServletContext().setAttribute("clientService",
                new ClientService(
                        clientRepository,
                        new Pbkdf2PasswordHash()));
        event.getServletContext().setAttribute("parcelService",
                new ParcelService(
                        parcelRepository,
                        clientRepository,
                        warehouseRepository,
                        imagePath));
        event.getServletContext().setAttribute("warehouseService",
                new WarehouseService(
                        warehouseRepository));
    }

}

