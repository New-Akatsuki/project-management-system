package org.blank.projectmanagementsystem.service;
import org.blank.projectmanagementsystem.domain.entity.Client;

import java.util.List;
import java.util.Optional;

public interface ClientService {
    Client save(Client client);
    Optional<Client> findByName(String name);
    Optional<Optional<Client>> findByEmail(String email);
    Optional<Optional<Client>> findByPhoneNumber(String phoneNumber);

    List<Client> findAll();

    Client findById(Integer id);

    void delete(int id);
}
