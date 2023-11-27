package org.blank.projectmanagementsystem.controller.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.blank.projectmanagementsystem.domain.entity.Architecture;
import org.blank.projectmanagementsystem.service.ArchitectureService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class ArchitectureAPI {
    private final ArchitectureService architectureService;

    @GetMapping("/architectures")
    public ResponseEntity<List<Architecture>> getArchitectures() {
        List<Architecture> architectures = architectureService.getAllArchitectures();
        return ResponseEntity.ok(architectures);
    }

    @PostMapping("/add-architecture")
    public ResponseEntity<Architecture> addArchitecture(@RequestBody Architecture architecture) {
        architecture.setStatus(true);
        Architecture newArchitecture = architectureService.save(architecture);
        return ResponseEntity.ok(newArchitecture);
    }
    @GetMapping("/architecture/{id}")
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
    @PutMapping("/architecture/update")
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
    @PutMapping("/architecture/status/{id}")
    public ResponseEntity<Architecture> updateArchitectureStatus(@PathVariable Long id) {
        Architecture architecture = architectureService.getArchitectureById(id);
        if (architecture != null) {
            architecture.setStatus(!architecture.isStatus());
            Architecture updatedArchitecture = architectureService.save(architecture);
            return ResponseEntity.ok(updatedArchitecture);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
