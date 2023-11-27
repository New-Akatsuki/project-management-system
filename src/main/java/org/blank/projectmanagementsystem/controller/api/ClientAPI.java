package org.blank.projectmanagementsystem.controller.api;

import lombok.RequiredArgsConstructor;
import org.blank.projectmanagementsystem.domain.entity.Client;
import org.blank.projectmanagementsystem.service.ClientService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ClientAPI {
    private final ClientService clientService;

    @GetMapping("/clients")
    public ResponseEntity<List<Client>> getClients() {
        List<Client> clients = clientService.getAllClients();
        return ResponseEntity.ok(clients);
    }


    @PostMapping("/add-client")
    public ResponseEntity<Client> addClient(@RequestBody Client client) {
        client.setStatus(true);
        Client newClient = clientService.save(client);
        return ResponseEntity.ok(newClient);
    }

    @GetMapping("/client/{id}")
    public ResponseEntity<Client> getClientById(@PathVariable Long id) {
        Client client = clientService.getClientById(id);
        if (client != null) {
            return ResponseEntity.ok(client);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/client/update")
    public ResponseEntity<Client> updateClient(@RequestBody Client client) {
        Client existingClient = clientService.getClientById(client.getId());

        if (existingClient != null) {
            existingClient.setName(client.getName());
            existingClient.setEmail(client.getEmail());
            existingClient.setPhoneNumber(client.getPhoneNumber());

            Client updatedClient = clientService.save(existingClient);
            return ResponseEntity.ok(updatedClient);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/client/status/{id}")
    public ResponseEntity<Client> updateClientStatus(@PathVariable Long id) {
        Client client = clientService.getClientById(id);
        boolean status = client.isStatus();
        client.setStatus(!status);
        Client updatedClient = clientService.save(client);
        return ResponseEntity.ok(updatedClient);
    }

}
