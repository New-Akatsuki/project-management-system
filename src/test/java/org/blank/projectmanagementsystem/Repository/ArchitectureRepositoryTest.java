package org.blank.projectmanagementsystem.Repository;

import org.blank.projectmanagementsystem.domain.entity.Architecture;
import org.blank.projectmanagementsystem.repository.ArchitectureRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest

public class ArchitectureRepositoryTest {

    @Autowired
    private ArchitectureRepository architectureRepository;

    @Test
    public void testFindByName() {
        // Arrange
        Architecture architecture = new Architecture();
        architecture.setName("TestArchitecture");
        architectureRepository.save(architecture);

        // Act
        Optional<Architecture> foundArchitectureOptional = architectureRepository.findByName("TestArchitecture");

        // Assert
        assertTrue(foundArchitectureOptional.isPresent());
        assertEquals("TestArchitecture", foundArchitectureOptional.orElseThrow().getName());
    }
}
