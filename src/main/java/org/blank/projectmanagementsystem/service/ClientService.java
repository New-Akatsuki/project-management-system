package org.blank.projectmanagementsystem.service;
import org.blank.projectmanagementsystem.domain.entity.Client;

import java.util.List;
import java.util.Optional;

public interface ClientService {
    Client save(Client client);





    void delete(int id);

    List<Client> getAllClients();

    Client getClientById(Long id);

    Client updateClientStatus(Long id, boolean status);

}
