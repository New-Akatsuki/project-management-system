package org.blank.projectmanagementsystem.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.blank.projectmanagementsystem.domain.entity.*;
import org.blank.projectmanagementsystem.domain.formInput.AddUserFormInput;
import org.blank.projectmanagementsystem.domain.formInput.ProjectFormInput;
import org.blank.projectmanagementsystem.domain.viewobject.UserViewObject;
import org.blank.projectmanagementsystem.service.*;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

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
    private final ProjectService projectService;
    private final DepartmentService departmentService;


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


    @DeleteMapping("/pm/system-outline/delete/{id}")
    public ResponseEntity<Long> deleteSystemOutline(@PathVariable Long id) {
        systemOutlineService.deleteSystemOutline(id);
        //return 2xx if successful
        return ResponseEntity.ok(id);
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
            // Update the existing deliverable's information
            existingDeliverable.setName(deliverable.getName());

            Deliverable updatedDeliverable = deliverableService.save(existingDeliverable);
            return ResponseEntity.ok(updatedDeliverable); // Return the updated deliverable
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    @PostMapping("/pm/deliverable/active/{id}")
    public ResponseEntity<Deliverable> activateDeliverable(@PathVariable Long id) {
        Deliverable deliverable = deliverableService.getDeliverableById(id);
        if (deliverable != null) {
            deliverable.setStatus(true);
            Deliverable updatedDeliverable = deliverableService.save(deliverable);
            return ResponseEntity.ok(updatedDeliverable);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
  
    @PostMapping("/pm/deliverable/disable/{id}")
    public ResponseEntity<Deliverable> disableDeliverable(@PathVariable Long id) {
        Deliverable deliverable = deliverableService.getDeliverableById(id);
        if (deliverable != null) {
            deliverable.setStatus(false);
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


    @PostMapping("/pm/client/active/{id}")
    public ResponseEntity<Client> activateClient(@PathVariable Long id) {
        Client client = clientService.getClientById(id);
        if (client != null) {
            client.setStatus(true);
            Client updatedClient = clientService.save(client);
            return ResponseEntity.ok(updatedClient);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @PostMapping("/pm/client/disable/{id}")
    public ResponseEntity<Client> disableClient(@PathVariable Long id) {
        Client client = clientService.getClientById(id);
        if (client != null) {
            client.setStatus(false);
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
    @DeleteMapping("/pm/architecture/delete/{id}")
    public ResponseEntity<Long> deleteArchitecture(@PathVariable Long id) {
        architectureService.deleteArchitecture(id);
        //return 2xx if successful
        return ResponseEntity.ok(id);
    }

    @PostMapping("/pm/add-users")
    public ResponseEntity<AddUserFormInput> addUser(@RequestBody AddUserFormInput addUserFormInput) {
        User newUser = userService.createMember(addUserFormInput);
        return ResponseEntity.ok(addUserFormInput);
    }


    @PreAuthorize("hasAuthority('PMO')")
    @GetMapping("/pm/users")
    public ResponseEntity<List<UserViewObject>> getUserList() {
        List<UserViewObject> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @PostMapping("/pm/create-proj")
    public ResponseEntity<Project> createProject(@RequestBody ProjectFormInput projectFormInput) {
        log.info("create project {} \n\n", projectFormInput);
        return ResponseEntity.ok(projectService.saveProject(projectFormInput, projectFormInput.getName()));
    }

    @GetMapping("/get-department")
    public List<Department> getDepartment() {
        List<Department> department = departmentService.getAllDepartments();
        log.info("get department {} \n\n", department);
        return department;
    }

    @GetMapping("/get-member")
    public List<UserViewObject> getMember() {
        List<UserViewObject> member = userService.getAllUsers();
        log.info("get user{} \n\n", member);
        return member;
    }

    @GetMapping("/get-deliverables")
    public List<Deliverable> getDeli() {
        List<Deliverable> deli= deliverableService.getAllDeliverables();
        log.info("get deli {} \n\n", deli);
        return deli;
    }

    @GetMapping("/get-architecture-outlines")
    public List<Architecture> getArchi() {
        List<Architecture> archi= architectureService.getAllArchitectures();
        log.info("get archi{} \n\n", archi);
        return archi;
    }

    @GetMapping("/get-systemoutlines")
    public List<SystemOutline> getSystem() {
        List<SystemOutline> system= systemOutlineService.getAllSystemOutlines();
        log.info("get system{} \n\n", system);
        return system;
    }




}




