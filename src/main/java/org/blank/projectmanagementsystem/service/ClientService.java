package org.blank.projectmanagementsystem.service;
import org.blank.projectmanagementsystem.domain.entity.Client;

import java.util.List;

public interface ClientService {
    Client save(Client client);
    List<Client> getAllClients();
    Client getClientById(Long id);

    void updateClient(Long id, Client client);
}
