package pl.pg.kyrczak.jakarta.configuration.listener;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import pl.pg.kyrczak.jakarta.client.controller.simple.ClientSimpleController;
import pl.pg.kyrczak.jakarta.client.service.ClientService;
import pl.pg.kyrczak.jakarta.component.DtoFunctionFactory;
import pl.pg.kyrczak.jakarta.parcel.controller.simple.ParcelSimpleController;
import pl.pg.kyrczak.jakarta.parcel.service.ParcelService;
import pl.pg.kyrczak.jakarta.warehouse.controller.simple.WarehouseSimpleController;
import pl.pg.kyrczak.jakarta.warehouse.service.WarehouseService;

/**
 * Listener started automatically on servlet context initialized. Creates an instance of controllers and puts them in
 * the application (servlet) context.
 */
@WebListener//using annotation does not allow configuring order
public class CreateControllers implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent event) {
        ParcelService parcelService = (ParcelService) event.getServletContext().getAttribute("parcelService");
        WarehouseService warehouseService = (WarehouseService) event.getServletContext().getAttribute("warehouseService");
        ClientService clientService = (ClientService) event.getServletContext().getAttribute("clientService");
        event.getServletContext().setAttribute("parcelController", new ParcelSimpleController(
                parcelService,
                new DtoFunctionFactory()
        ));

        event.getServletContext().setAttribute("warehouseController", new WarehouseSimpleController(
                warehouseService,
                new DtoFunctionFactory()
        ));

        event.getServletContext().setAttribute("clientController", new ClientSimpleController(
                clientService,
                new DtoFunctionFactory()
        ));
    }
}

