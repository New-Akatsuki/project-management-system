package org.blank.projectmanagementsystem.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.blank.projectmanagementsystem.domain.entity.*;
import org.blank.projectmanagementsystem.domain.formInput.AddUserFormInput;
import org.blank.projectmanagementsystem.domain.formInput.ChangePasswordFormInput;
import org.blank.projectmanagementsystem.domain.viewobject.UserViewObject;
import org.blank.projectmanagementsystem.service.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class PMRestController {
    private final UserService userService;
   /* private final SystemOutlineService systemOutlineService;
    private final ClientService clientService;
    private final ArchitectureService architectureService;
    private final DeliverableService deliverableService;


    @PreAuthorize("hasAuthority('PM')")
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
    @GetMapping("/pm/system-outline/{id}")
    public ResponseEntity<SystemOutline> getSystemOutlineById(@PathVariable Long id) {
        SystemOutline systemOutline = systemOutlineService.getSystemOutlineById(id);
        if (systemOutline != null) {
            return ResponseEntity.ok(systemOutline);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @PutMapping("/pm/system-outline/update")
    public ResponseEntity<SystemOutline> updateSystemOutline(@RequestBody SystemOutline systemOutline) {
        SystemOutline existingSystemOutline = systemOutlineService.getSystemOutlineById(systemOutline.getId());

        if (existingSystemOutline != null) {
            // Update the existing systemOutline's information
            existingSystemOutline.setName(systemOutline.getName());

            SystemOutline updatedSystemOutline = systemOutlineService.save(existingSystemOutline);
            return ResponseEntity.ok(updatedSystemOutline); // Return the updated systemOutline
        } else {
            return ResponseEntity.notFound().build();

        }
    }


    @PutMapping("/pm/system-outline/status/{id}")
    public ResponseEntity<SystemOutline> updateSystemOutlineStatus(@PathVariable Long id, @RequestParam boolean newStatus) {
        SystemOutline systemOutline = systemOutlineService.getSystemOutlineById(id);
        if (systemOutline != null) {
            systemOutline.setStatus(newStatus);
            SystemOutline updatedSystemOutline = systemOutlineService.save(systemOutline);

            return ResponseEntity.ok(updatedSystemOutline);
        } else {
            return ResponseEntity.notFound().build();
        }
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

    @GetMapping("/pm/deliverable/{id}")
    public ResponseEntity<Deliverable> getDeliverableById(@PathVariable Long id) {
        Deliverable deliverable = deliverableService.getDeliverableById(id);
        if (deliverable != null) {
            return ResponseEntity.ok(deliverable);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @PutMapping("/pm/deliverable/update")
    public ResponseEntity<Deliverable> updateDeliverable(@RequestBody Deliverable deliverable) {
        Deliverable existingDeliverable = deliverableService.getDeliverableById(deliverable.getId());

        if (existingDeliverable != null) {
            existingDeliverable.setName(deliverable.getName());

            Deliverable updatedDeliverable = deliverableService.save(existingDeliverable);
            return ResponseEntity.ok(updatedDeliverable); // Return the updated deliverable
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    @PutMapping("/pm/deliverable/status/{id}")
    public ResponseEntity<Deliverable> updateDeliverableStatus(@PathVariable Long id, @RequestParam boolean newStatus) {
        Deliverable deliverable = deliverableService.getDeliverableById(id);
        if (deliverable != null) {
            deliverable.setStatus(newStatus);
            Deliverable updatedDeliverable = deliverableService.save(deliverable);

            return ResponseEntity.ok(updatedDeliverable);
        } else {
            return ResponseEntity.notFound().build();
        }
    }


   @GetMapping("/pm/clients")
    public ResponseEntity<List<Client>> getClients() {
        List<Client> clients = clientService.getAllClients();
        return ResponseEntity.ok(clients);
    }

    @PostMapping("/pm/add-client")
    public ResponseEntity<Client> addClient(@RequestBody Client client) {
        log.info("Client: {}", client);
        Client newClient = clientService.save(client);
        log.info("New Client: {}", newClient);
        return ResponseEntity.ok(newClient);
    }

    @GetMapping("/pm/client/{id}")
    public ResponseEntity<Client> getClientById(@PathVariable Long id) {
        Client client = clientService.getClientById(id);
        if (client != null) {
            return ResponseEntity.ok(client);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/pm/client/update")
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

    @PutMapping("/pm/client/status/{id}")
    public ResponseEntity<Client> updateClientStatus(@PathVariable Long id, @RequestParam boolean newStatus) {
        Client client = clientService.getClientById(id);
        if (client != null) {
            client.setStatus(newStatus);
            Client updatedClient = clientService.save(client);
            return ResponseEntity.ok(updatedClient);
        } else {
            return ResponseEntity.notFound().build();
        }
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
    @GetMapping("/pm/architecture/{id}")
    public ResponseEntity<Architecture> getArchitectureById(@PathVariable Long id) {
        Architecture architecture = architectureService.getArchitectureById(id);
        //to get data from database
        log.info("Architecture: {}", architecture);

        if (architecture != null) {
            return ResponseEntity.ok(architecture);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @PutMapping("/pm/architecture/update")
    public ResponseEntity<Architecture> updateArchitecture(@RequestBody Architecture architecture) {
        Architecture existingArchitecture = architectureService.getArchitectureById(architecture.getId());

        if (existingArchitecture != null) {
            // Update the existing architecture's information
            existingArchitecture.setName(architecture.getName());
            existingArchitecture.setType(architecture.getType());


            Architecture updatedArchitecture = architectureService.save(existingArchitecture);
            return ResponseEntity.ok(updatedArchitecture); // Return the updated architecture
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @PutMapping("/pm/architecture/status/{id}")
    public ResponseEntity<Architecture> updateArchitectureStatus(@PathVariable Long id, @RequestParam boolean newStatus) {
        Architecture architecture = architectureService.getArchitectureById(id);
        if (architecture != null) {
            architecture.setStatus(newStatus);
            Architecture updatedArchitecture = architectureService.save(architecture);

            return ResponseEntity.ok(updatedArchitecture);
        } else {
            return ResponseEntity.notFound().build();
        }
    }*/

    @PostMapping("/pm/add-users")
    public ResponseEntity<AddUserFormInput> addUser(@RequestBody AddUserFormInput addUserFormInput) {
        User newUser = userService.registerUser(addUserFormInput);
        return ResponseEntity.ok(addUserFormInput);
    }

    @GetMapping("/pm/users")
    public ResponseEntity<List<UserViewObject>> getUserList() {
        List<UserViewObject> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }



    @PostMapping("/pm/change-password")
    public ResponseEntity<ChangePasswordFormInput> changePassword(@RequestBody ChangePasswordFormInput changePasswordFormInput) {
        ChangePasswordFormInput chpwd = userService.changePassword(
                changePasswordFormInput.getCurrentPassword(),
                changePasswordFormInput.getNewPassword());
        log.info("===========================================================================================");
        log.info("ChangePasswordFormInput: {}", chpwd);
        log.info("===========================================================================================");
        if (chpwd != null) {
            chpwd.setCurrentPassword(changePasswordFormInput.getCurrentPassword());
            chpwd.setNewPassword(changePasswordFormInput.getNewPassword());
            chpwd.setConfirmPassword(changePasswordFormInput.getConfirmPassword());
            return ResponseEntity.ok(chpwd);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/pm/check-current-password")
    public ResponseEntity<String> checkCurrentPassword(@RequestBody ChangePasswordFormInput changePasswordFormInput) {
        boolean chpwd = userService.checkCurrentPassword(changePasswordFormInput.getCurrentPassword());
        log.info("===========================================================================================");
        log.info("CurrentPassword: {}", chpwd);
        log.info("===========================================================================================");

        if (chpwd) {
            String cp = changePasswordFormInput.getCurrentPassword();
            return ResponseEntity.ok(cp);
        } else {
            return ResponseEntity.notFound().build();
        }

    }




}







