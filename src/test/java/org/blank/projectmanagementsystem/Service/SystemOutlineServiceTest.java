package org.blank.projectmanagementsystem.Service;

import org.blank.projectmanagementsystem.domain.entity.SystemOutline;
import org.blank.projectmanagementsystem.repository.SystemOutlineRepository;
import org.blank.projectmanagementsystem.service.impl.SystemOutlineServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class SystemOutlineServiceTest {

    @InjectMocks
    private SystemOutlineServiceImpl systemOutlineService;

    @Mock
    private SystemOutlineRepository systemOutlineRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetAllSystemOutlines() {
        // Arrange
        List<SystemOutline> mockSystemOutlines = new ArrayList<>();
        // Add some mock SystemOutlines to the list

        when(systemOutlineRepository.findAll()).thenReturn(mockSystemOutlines);

        // Act
        List<SystemOutline> result = systemOutlineService.getAllSystemOutlines();

        // Assert
        assertEquals(mockSystemOutlines, result);
    }

    @Test
    public void testSaveSystemOutline() {
        // Arrange
        SystemOutline systemOutlineToSave = new SystemOutline();
        // Set up other necessary data

        when(systemOutlineRepository.save(systemOutlineToSave)).thenReturn(systemOutlineToSave);

        // Act
        SystemOutline result = systemOutlineService.save(systemOutlineToSave);

        // Assert
        assertEquals(systemOutlineToSave, result);
    }

    @Test
    public void testGetSystemOutlineById() {
        // Arrange
        Long outlineId = 1L;
        SystemOutline mockSystemOutline = new SystemOutline();
        // Set up other necessary data

        when(systemOutlineRepository.findById(outlineId)).thenReturn(java.util.Optional.of(mockSystemOutline));

        // Act
        SystemOutline result = systemOutlineService.getSystemOutlineById(outlineId);

        // Assert
        assertEquals(mockSystemOutline, result);
    }
}
