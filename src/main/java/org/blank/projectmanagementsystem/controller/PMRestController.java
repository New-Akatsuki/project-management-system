package org.blank.projectmanagementsystem.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.blank.projectmanagementsystem.domain.entity.Architecture;
import org.blank.projectmanagementsystem.domain.entity.Client;
import org.blank.projectmanagementsystem.domain.entity.Deliverable;
import org.blank.projectmanagementsystem.domain.entity.SystemOutline;
import org.blank.projectmanagementsystem.service.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class PMRestController {
    private final UserService userService;
    private final SystemOutlineService systemOutlineService;
    private final ClientService clientService;
    private final ArchitectureService architectureService;
    private final DeliverableService deliverableService;

    @GetMapping("/pm/system-outlines") // Changed endpoint
    public ResponseEntity<List<SystemOutline>> getSystemOutlines() {
        List<SystemOutline> systemOutlines = systemOutlineService.getAllSystemOutlines();
        return ResponseEntity.ok(systemOutlines);
    }

    @PostMapping("/pm/add-system-outline") // Changed endpoint
    public ResponseEntity<SystemOutline> addSystemOutline(@RequestBody SystemOutline systemOutline) {
        SystemOutline newSystemOutline = systemOutlineService.save(systemOutline);
        return ResponseEntity.ok(newSystemOutline);
    }

    @GetMapping("/pm/deliverables") // Changed endpoint
    public ResponseEntity<List<Deliverable>> getDeliverables() {
        List<Deliverable> deliverables = deliverableService.getAllDeliverables();
        return ResponseEntity.ok(deliverables);
    }

    @PostMapping("/pm/add-deliverable") // Changed endpoint
    public ResponseEntity<Deliverable> addDeliverable(@RequestBody Deliverable deliverable) {
        Deliverable newDeliverable = deliverableService.save(deliverable);
        return ResponseEntity.ok(newDeliverable);
    }

    @GetMapping("/pm/clients")
    public ResponseEntity<List<Client>> getClients() {
        List<Client> clients = clientService.getAllClients();
        return ResponseEntity.ok(clients);
    }

    @PostMapping("/pm/add-client")
    public ResponseEntity<Client> addClient(@RequestBody Client client) {
        Client newClient = clientService.save(client);
        return ResponseEntity.ok(newClient);
    }

    @GetMapping("/pm/architectures")
    public ResponseEntity<List<Architecture>> getArchitectures() {
        List<Architecture> architectures = architectureService.getAllArchitectures();

        return ResponseEntity.ok(architectures);
    }

    @PostMapping("/pm/add-architecture")
    public ResponseEntity<Architecture> addArchitecture(@RequestBody Architecture architecture) {
        Architecture newArchitecture = architectureService.save(architecture);
        return ResponseEntity.ok(newArchitecture);
    }

//    @PostMapping("/pm/add-architecture")
//    public ResponseEntity<A> addArchitecture(@RequestBody Architecture architecture) {
//        try {
//            Architecture newArchitecture = architectureService.save(architecture);
//            // You can access the architecture data using architecture.getName() and architecture.getType()
//
//            // For simplicity, let's just return a success message
//            return new ResponseEntity<>("Architecture added successfully", HttpStatus.OK);
//        } catch (Exception e) {
//            // Log the exception for debugging purposes
//            e.printStackTrace();
//            return new ResponseEntity<>("Error adding architecture", HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }
   }



