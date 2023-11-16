package org.blank.projectmanagementsystem.controller.api;

import lombok.RequiredArgsConstructor;
import org.blank.projectmanagementsystem.domain.entity.Client;
import org.blank.projectmanagementsystem.service.ClientService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor

public class ClientAPI {
    private final ClientService clientService;

    @GetMapping("/pm/clients")
    public ResponseEntity<List<Client>> getClients() {
        List<Client> clients = clientService.getAllClients();
        return ResponseEntity.ok(clients);
    }

}
