package org.blank.projectmanagementsystem.service;
import org.blank.projectmanagementsystem.domain.entity.Client;

import java.util.List;
import java.util.Optional;

public interface ClientService {
    Client save(Client client);
    List<Client> getAllClients();
    List<Client> getClientsByStatusTrue();
    Client getClientById(Long id);
}
