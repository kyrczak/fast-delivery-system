package pl.pg.kyrczak.jakarta.parcel.producer;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;
import jakarta.inject.Inject;
import jakarta.servlet.ServletContext;
import pl.pg.kyrczak.jakarta.parcel.producer.api.ImageDirectory;

import java.nio.file.Path;
import java.nio.file.Paths;

@ApplicationScoped
public class ImageDirectoryProducer {

    @Inject
    private ServletContext servletContext;

    @Produces
    @ImageDirectory // Custom qualifier to differentiate this bean if needed.
    public Path produceImageDirectory() {
        String directoryPath = servletContext.getInitParameter("imageDirectory");
        return Paths.get(directoryPath);
    }
}
