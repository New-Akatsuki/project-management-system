package org.blank.projectmanagementsystem.service.impl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.blank.projectmanagementsystem.domain.entity.Client;
import org.blank.projectmanagementsystem.repository.ClientRepository;
import org.blank.projectmanagementsystem.service.ClientService;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;

    @Override
    public Client save(Client client) {
        return clientRepository.save(client);
    }

    @Override
    public Optional<Client> findByName(String name) {
        return clientRepository.findByName(name);
    }

    @Override
    public Optional<Optional<Client>> findByEmail(String email) {
        return Optional.ofNullable(clientRepository.findByEmail(email));
    }

    @Override
    public Optional<Optional<Client>> findByPhoneNumber(String phoneNumber) {
        return Optional.ofNullable(clientRepository.findByPhoneNumber(phoneNumber));
    }

    @Override
    public List<Client> findAll() {
        return null;
    }

    @Override
    public Client findById(Integer id) {
        return null;
    }

    @Override
    public void delete(int id) {

    }

    @Override
    public List<Client> getAllClients() {
        return clientRepository.findAll();
    }

    @Override
    public Client getClientById(Long id) {

        return clientRepository.findById(id).orElse(null);

    }
}
