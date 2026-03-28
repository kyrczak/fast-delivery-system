package pl.pg.kyrczak.jakarta.configuration.singleton;

import jakarta.annotation.PostConstruct;
import jakarta.ejb.Singleton;
import jakarta.ejb.Startup;
import jakarta.ejb.TransactionAttribute;
import jakarta.ejb.TransactionAttributeType;
import jakarta.inject.Inject;
import jakarta.security.enterprise.identitystore.Pbkdf2PasswordHash;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import pl.pg.kyrczak.jakarta.client.entity.Client;
import pl.pg.kyrczak.jakarta.client.entity.ClientRoles;
import pl.pg.kyrczak.jakarta.client.repository.api.ClientRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Singleton
@Startup
@TransactionAttribute(value = TransactionAttributeType.REQUIRED)
@NoArgsConstructor(force = true)
public class InitializeAdminService {
    private final ClientRepository clientRepository;
    private final Pbkdf2PasswordHash passwordHash;

    @Inject
    public InitializeAdminService(
            ClientRepository clientRepository,
            @SuppressWarnings("CdiInjectionPointsInspection") Pbkdf2PasswordHash passwordHash
    ) {
        this.clientRepository = clientRepository;
        this.passwordHash = passwordHash;
    }

    @PostConstruct
    @SneakyThrows
    private void init() {
        if(clientRepository.findByLogin("admin-service").isEmpty()) {
            Client admin = Client.builder()
                    .uuid(UUID.fromString("c96502ff-8c26-4af0-95eb-15fbb57844bf"))
                    .login("admin-service")
                    .name("Admin")
                    .surname("Service")
                    .registrationDate(LocalDate.of(2000,01,01))
                    .email("admin-service@fastdelivery.com")
                    .password(passwordHash.generate("adminadmin".toCharArray()))
                    .roles(List.of(ClientRoles.ADMIN, ClientRoles.USER))
                    .build();

            clientRepository.create(admin);
        }
    }
}
