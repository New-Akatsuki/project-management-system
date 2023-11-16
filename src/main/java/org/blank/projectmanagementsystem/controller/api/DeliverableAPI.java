package org.blank.projectmanagementsystem.controller.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.blank.projectmanagementsystem.domain.entity.Deliverable;
import org.blank.projectmanagementsystem.service.DeliverableService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class DeliverableAPI {
    private final DeliverableService deliverableService;

    @GetMapping("/pm/deliverables") // Changed endpoint
    public ResponseEntity<List<Deliverable>> getDeliverables() {
        List<Deliverable> deliverables = deliverableService.getAllDeliverables();
        return ResponseEntity.ok(deliverables);
    }
}
