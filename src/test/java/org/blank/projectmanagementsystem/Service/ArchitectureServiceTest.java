package org.blank.projectmanagementsystem.Service;

import org.springframework.boot.test.context.SpringBootTest;
import org.blank.projectmanagementsystem.service.ArchitectureService;
import org.blank.projectmanagementsystem.domain.entity.Architecture;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest

public class ArchitectureServiceTest {
    @Autowired
    private ArchitectureService architectureService;

    @Test
    public void testGetById() {
        // Arrange
        Architecture architecture = new Architecture();
        architecture.setName("Test Architecture");
        architecture = architectureService.save(architecture);

        // Act
        Architecture retrievedArchitecture = architectureService.getById(architecture.getId());

        // Assert
        assertNotNull(retrievedArchitecture);
        assertEquals("Test Architecture", retrievedArchitecture.getName());
    }

    @Test
    public void testGetAllArchitectures() {
        // Arrange
        Architecture architecture1 = new Architecture();
        architecture1.setName("Architecture 1");
        architectureService.save(architecture1);

        Architecture architecture2 = new Architecture();
        architecture2.setName("Architecture 2");
        architectureService.save(architecture2);

        // Act
        Iterable<Architecture> architectures = architectureService.getAllArchitectures();

        // Assert
        assertNotNull(architectures);
        assertEquals(2, ((List<Architecture>) architectures).size());
    }

    @Test
    public void testUpdateArchitecture() {
        // Arrange
        Architecture architecture = new Architecture();
        architecture.setName("Initial Name");
        architecture = architectureService.save(architecture);

        // Act
        architecture.setName("Updated Name");
        Architecture updatedArchitecture = architectureService.updateArchitecture(architecture.getId(), architecture);

        // Assert
        assertNotNull(updatedArchitecture);
        assertEquals("Updated Name", updatedArchitecture.getName());
    }

    @Test
    public void testDeleteArchitecture() {
        // Arrange
        Architecture architecture = new Architecture();
        architecture.setName("Test Architecture");
        architecture = architectureService.save(architecture);

        // Act
        architectureService.deleteArchitecture(architecture.getId());

        // Assert
        assertNull(architectureService.getById(architecture.getId()));
    }



}
