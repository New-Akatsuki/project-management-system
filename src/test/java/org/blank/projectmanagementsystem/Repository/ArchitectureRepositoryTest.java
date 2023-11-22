package org.blank.projectmanagementsystem.Repository;

import org.blank.projectmanagementsystem.domain.Enum.ArchitectureType;
import org.blank.projectmanagementsystem.domain.entity.Architecture;
import org.blank.projectmanagementsystem.domain.entity.Client;
import org.blank.projectmanagementsystem.domain.entity.Deliverable;
import org.blank.projectmanagementsystem.repository.ArchitectureRepository;
import org.blank.projectmanagementsystem.repository.ClientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ArchitectureRepositoryTest {
    @Mock
    private ArchitectureRepository architectureRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }
    @Test
    public void testSaveArchitecture() {
        // Given
        Architecture architectureToSave = new Architecture();
        architectureToSave.setId(1L);
        architectureToSave.setName("Test Client");
        architectureToSave.setStatus(true);

        // When
        when(architectureRepository.save(architectureToSave)).thenReturn(architectureToSave);
        Architecture savedArchitecture = architectureRepository.save(architectureToSave);

        // Then
        assertEquals(architectureToSave, savedArchitecture);
    }

    @Test
    public void testGetArchitectureById() {
        // Given
        Long architectureId = 1L;
        Architecture architecture = new Architecture();
        architecture.setId(architectureId);
        architecture.setName("Test Architecture");
        architecture.setStatus(true);

        // When
        when(architectureRepository.findById(architectureId)).thenReturn(java.util.Optional.of(architecture));
        Architecture retrievedArchitecture = architectureRepository.findById(architectureId).orElse(null);

        // Then
        assertEquals(architecture, retrievedArchitecture);
        }
    @Test
    public void testGetAllArchitectures() {
        // Given
        Architecture architecture1 = new Architecture();
        architecture1.setId(1L);
        architecture1.setName("Java");
        architecture1.setStatus(true);

        Architecture architecture2 = new Architecture();
        architecture2.setId(2L);
        architecture2.setName("PHP");
        architecture2.setStatus(true);

        List<Architecture> expectedArchitectures = Arrays.asList(architecture1, architecture2);

        // When
        when(architectureRepository.findAll()).thenReturn(expectedArchitectures);
        List<Architecture> retrievedArchitectures = architectureRepository.findAll();
        // Then
        assertEquals(expectedArchitectures, retrievedArchitectures);
    }

}