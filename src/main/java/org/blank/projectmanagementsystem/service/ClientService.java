package org.blank.projectmanagementsystem.service;
import org.blank.projectmanagementsystem.domain.entity.Client;
import java.util.Optional;

public interface ClientService {
    Client save(Client client);
    Optional<Client> findByName(String name);
    Optional<Optional<Client>> findByEmail(String email);
    Optional<Optional<Client>> findByPhoneNumber(String phoneNumber);
}
