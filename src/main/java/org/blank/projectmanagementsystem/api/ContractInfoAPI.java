package org.blank.projectmanagementsystem.api;

import lombok.RequiredArgsConstructor;
import org.blank.projectmanagementsystem.domain.entity.Architecture;
import org.blank.projectmanagementsystem.domain.entity.Client;
import org.blank.projectmanagementsystem.domain.entity.Deliverable;
import org.blank.projectmanagementsystem.domain.entity.SystemOutline;
import org.blank.projectmanagementsystem.service.ArchitectureService;
import org.blank.projectmanagementsystem.service.ClientService;
import org.blank.projectmanagementsystem.service.DeliverableService;
import org.blank.projectmanagementsystem.service.SystemOutlineService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class ContractInfoAPI {

    private final SystemOutlineService systemOutlineService;
    private final ClientService clientService;
    private final ArchitectureService architectureService;
    private final DeliverableService deliverableService;

    @GetMapping("/get-contract-info")
    public ResponseEntity<Map<String, List<? extends Serializable>>> getContractInfo(){
        List<SystemOutline> systems=systemOutlineService.getAllSystemOutlines();
        List<Client> clients = clientService.getAllClients();
        List<Architecture> architectures= architectureService.getAllArchitectures();
        List<Deliverable> deliverables=deliverableService.getAllDeliverables();
        return ResponseEntity.ok(Map.of("systems",systems,"clients",clients,"architectures",architectures,"deliverables",deliverables));
    }

}
