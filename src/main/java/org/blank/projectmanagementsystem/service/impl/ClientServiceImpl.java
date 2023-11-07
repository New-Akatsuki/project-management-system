package org.blank.projectmanagementsystem.service.impl;
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

    @Override
    @PreAuthorize("hasAnyAuthority('PMO','SDQC','DH','PM','MEMBER')")
    public List<Client> getallClients() {
        return  clientRepository.findAll();
    }

    @Override
    @PreAuthorize("hasAnyAuthority('PMO','SDQC','DH','PM','MEMBER')")
    public Optional<Client> findByName(String name) {
        return clientRepository.findByName(name);
    }

    @Override
    @PreAuthorize("hasAnyAuthority('PMO','SDQC','DH','PM','MEMBER')")
    public Optional<Optional<Client>> findByEmail(String email) {
        return Optional.ofNullable(clientRepository.findByEmail(email));
    }

    @Override
    @PreAuthorize("hasAnyAuthority('PMO','SDQC','DH','PM','MEMBER')")
    public Optional<Optional<Client>> findByPhoneNumber(String phoneNumber) {
        return Optional.ofNullable(clientRepository.findByPhoneNumber(phoneNumber));
    }

    @Override
    @PreAuthorize("hasAnyAuthority('PMO','SDQC','DH','PM','MEMBER')")
    public List<Client> findAll() {
        return null;
    }

    @Override
    @PreAuthorize("hasAnyAuthority('PMO','SDQC','DH','PM','MEMBER')")
    public Client findById(Integer id) {
        return null;
    }

    @Override
    @PreAuthorize("hasAnyAuthority('PMO','SDQC','DH','PM','MEMBER')")
    public void delete(int id) {

    }

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

    @Override
    @PreAuthorize("hasAnyAuthority('PMO','SDQC','DH','PM','MEMBER')")
    public void deleteClient(Long id) {
        clientRepository.deleteById(id);
    }
}
