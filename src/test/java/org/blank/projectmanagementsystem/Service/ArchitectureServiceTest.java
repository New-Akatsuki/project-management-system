package org.blank.projectmanagementsystem.Service;
import org.blank.projectmanagementsystem.domain.entity.Architecture;
import org.blank.projectmanagementsystem.repository.ArchitectureRepository;
import org.blank.projectmanagementsystem.service.impl.ArchitectureServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ArchitectureServiceTest {

    @Mock
    private ArchitectureRepository architectureRepository;

    @InjectMocks
    private ArchitectureServiceImpl architectureService;

    @Test
    public void testSaveArchitecture() {
        // Given
        Architecture architectureToSave = new Architecture();
        architectureToSave.setId(1L);
        architectureToSave.setName("Test Architecture");

        // When
        when(architectureRepository.save(architectureToSave)).thenReturn(architectureToSave);

        Architecture savedArchitecture = architectureService.save(architectureToSave);

        // Then
        assertEquals("Test Architecture", savedArchitecture.getName());
        verify(architectureRepository, times(1)).save(architectureToSave);
    }

    @Test
    void testGetArchitectureById() {
        // Given
        Long architectureId = 1L;
        Architecture architecture = new Architecture();
        architecture.setId(architectureId);
        architecture.setName("Test Architecture");

        // When
        when(architectureRepository.findById(architectureId)).thenReturn(Optional.of(architecture));

        Architecture foundArchitecture = architectureService.getArchitectureById(architectureId);

        // Then
        assertEquals("Test Architecture", foundArchitecture.getName());
        verify(architectureRepository, times(1)).findById(architectureId);
    }

    @Test
    void testFindArchitectureById() {
        // Given
        Long architectureId = 1L;
        Architecture expectedArchitecture = new Architecture();
        expectedArchitecture.setId(architectureId);
        expectedArchitecture.setName("Test Architecture");

        // When
        when(architectureRepository.findById(architectureId)).thenReturn(Optional.of(expectedArchitecture));

        Architecture foundArchitecture = architectureService.findById(architectureId);

        // Then
        assertEquals("Test Architecture", foundArchitecture.getName());
        verify(architectureRepository, times(1)).findById(architectureId);
    }

    @Test
    void testGetAllArchitectures() {
        // Given
        Architecture architecture1 = new Architecture();
        architecture1.setId(1L);
        architecture1.setName("Architecture 1");

        Architecture architecture2 = new Architecture();
        architecture2.setId(2L);
        architecture2.setName("Architecture 2");

        // When
        when(architectureRepository.findAll()).thenReturn(Arrays.asList(architecture1, architecture2));

        List<Architecture> allArchitectures = architectureService.getAllArchitectures();

        // Then
        assertEquals(2, allArchitectures.size());
        assertEquals("Architecture 1", allArchitectures.get(0).getName());
        assertEquals("Architecture 2", allArchitectures.get(1).getName());
        verify(architectureRepository, times(1)).findAll();
    }


}
