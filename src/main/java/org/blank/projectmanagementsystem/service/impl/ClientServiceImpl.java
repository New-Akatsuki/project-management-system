package org.blank.projectmanagementsystem.service.impl;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.blank.projectmanagementsystem.domain.entity.Client;
import org.blank.projectmanagementsystem.repository.ClientRepository;
import org.blank.projectmanagementsystem.service.ClientService;

import java.util.List;
import java.util.Optional;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;

    @Override
    @PreAuthorize("hasAnyAuthority('PMO','SDQC','DH','PM','MEMBER')")
    public Client save(Client client) {
        return clientRepository.save(client);
    }


    /*@Override
    @Transactional
    public Client updateClientStatus(Long id, boolean status) {
        Client client = clientRepository.findById(id).orElse(null);
        if (client != null) {
            client.setStatus(status);
            clientRepository.save(client);
        }
        return client;
    }*/



    @Override
    @PreAuthorize("hasAnyAuthority('PMO','SDQC','DH','PM','MEMBER')")
    public List<Client> getAllClients() {
        return clientRepository.findAll();
    }

    @Override
    @PreAuthorize("hasAnyAuthority('PMO','SDQC','DH','PM','MEMBER')")
    public Client getClientById(Long id) {

        return clientRepository.findById(id).orElse(null);

    }


}
