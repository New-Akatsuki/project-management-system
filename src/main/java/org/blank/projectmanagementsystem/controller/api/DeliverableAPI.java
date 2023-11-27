package org.blank.projectmanagementsystem.controller.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.blank.projectmanagementsystem.domain.entity.Deliverable;
import org.blank.projectmanagementsystem.service.DeliverableService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class DeliverableAPI {
    private final DeliverableService deliverableService;

    @GetMapping("/deliverables") // Changed endpoint
    public ResponseEntity<List<Deliverable>> getDeliverables() {
        List<Deliverable> deliverables = deliverableService.getDeliverablesByStatusTrue();
        return ResponseEntity.ok(deliverables);
    }

    @PostMapping("/add-deliverable") // Changed endpoint
    public ResponseEntity<Deliverable> addDeliverable(@RequestBody Deliverable deliverable) {
        deliverable.setStatus(true);
        Deliverable newDeliverable = deliverableService.save(deliverable);
        return ResponseEntity.ok(newDeliverable);
    }

    @GetMapping("/deliverable/{id}")
    public ResponseEntity<Deliverable> getDeliverableById(@PathVariable Long id) {
        Deliverable deliverable = deliverableService.getDeliverableById(id);
        if (deliverable != null) {
            return ResponseEntity.ok(deliverable);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @PutMapping("/deliverable/update")
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


    @PutMapping("/deliverable/status/{id}")
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

}
