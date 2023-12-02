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
import static org.mockito.Mockito.when;

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
    }}






