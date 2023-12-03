package org.blank.projectmanagementsystem.service.impl;

import org.blank.projectmanagementsystem.domain.entity.Architecture;


import org.blank.projectmanagementsystem.repository.ArchitectureRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;

public class ArchitectureServiceImplTest {
    @Mock
    private ArchitectureRepository architectureRepository;

    @InjectMocks
    private ArchitectureServiceImpl architectureService;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.initMocks(this);
    }
    @Test
    void testGetAllArchitectures(){
        // Mock a list of architectures
        List<Architecture> architectures = new ArrayList<>();
        architectures.add(new Architecture());
        architectures.add(new Architecture());

        // Mock the behavior of the repository
        when(architectureRepository.findAll()).thenReturn(architectures);

        //Test the getAllArchitectures method
        List<Architecture> retrievedArchitectures = architectureService.getAllArchitectures();

        // Verify that the retrieved list size matches the mocked list size
        assertEquals(architectures.size(),retrievedArchitectures.size());
    }
    @Test
    void testUpdateArchitecture(){
        // Create a mock architecture
        Long id=1L;
        Architecture architecture = new Architecture();
        architecture.setId(id);


        // Mock the behavior of the repository
        when(architectureRepository.existsById(id)).thenReturn(true);
        when(architectureRepository.save(architecture)).thenReturn(architecture);

        // Test the updateArchitecture method
        Architecture updatedArchitecture = architectureService.updateArchitecture(id,architecture);

        // Verify that the updated architecture has the same id as the mocked architecture
        assertEquals(id,updatedArchitecture.getId());
    }

    @Test
    void testUpdateArchitectureReturnNull(){
        // Create a mock architecture
        Long id=1L;
        Architecture architecture = new Architecture();
        architecture.setId(id);

        // Mock the behavior of the repository
        when(architectureRepository.existsById(id)).thenReturn(false);
        when(architectureRepository.save(architecture)).thenReturn(architecture);

        // Test the updateArchitecture method
        Architecture updatedArchitecture = architectureService.updateArchitecture(id,architecture);

        // Verify that the updated architecture has the same id as the mocked architecture
        assertNull(updatedArchitecture);
    }

    @Test
    void testSaveArchitecture(){
        // Create a mock architecture
        Architecture architecture = new Architecture();

        // Mock the behavior of the repository
        when(architectureRepository.save(architecture)).thenReturn(architecture);

        // Test the saveArchitecture method
        Architecture savedArchitecture = architectureService.save(architecture);

        // Verify that the saved architecture has the same id as the mocked architecture
        assertEquals(architecture,savedArchitecture);
    }
    @Test
    void testGetArchitectureById(){
        // Create a mock architecture
        Long id=1L;
        Architecture architecture = new Architecture();
        architecture.setId(id);

        // Mock the behavior of the repository
        when(architectureRepository.findById(id)).thenReturn(java.util.Optional.of(architecture));

        // Test the getArchitectureById method
        Architecture retrievedArchitecture = architectureService.getArchitectureById(id);

        // Verify that the retrieved architecture has the same id as the mocked architecture
        assertEquals(id,retrievedArchitecture.getId());
    }

    @Test
    public void testGetArchitecturesByStatusTrue() {
        // Arrange
        Architecture architecture1 = new Architecture(/* construct your object here */);
        Architecture architecture2 = new Architecture(/* construct your object here */);

        List<Architecture> expectedArchitectures =List.of(architecture1, architecture2);

        // Mocking the behavior of the repository
        when(architectureRepository.findByStatusIsTrue()).thenReturn(expectedArchitectures);

        // Act
        List<Architecture> actualArchitectures = architectureService.getArchitecturesByStatusTrue();

        // Assert
        // Add assertions based on your specific requirements
        assertEquals(expectedArchitectures.size(), actualArchitectures.size());
        assertEquals(expectedArchitectures, actualArchitectures);

        // Verify that the repository method was called
        verify(architectureRepository, times(1)).findByStatusIsTrue();
    }

}






