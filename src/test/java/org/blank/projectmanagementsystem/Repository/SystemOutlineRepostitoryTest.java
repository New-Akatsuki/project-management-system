package org.blank.projectmanagementsystem.Repository;

import org.blank.projectmanagementsystem.domain.entity.Deliverable;
import org.blank.projectmanagementsystem.domain.entity.SystemOutline;
import org.blank.projectmanagementsystem.repository.SystemOutlineRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class SystemOutlineRepostitoryTest {
    @Mock
    private SystemOutlineRepository systemOutlineRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testSaveSystemOutline() {
        // Given
        SystemOutline systemOutlineToSave = new SystemOutline();
        systemOutlineToSave.setId(1L);
        systemOutlineToSave.setName("SystemOutline 1");
        systemOutlineToSave.setStatus(true);

        // When
        when(systemOutlineRepository.save(systemOutlineToSave)).thenReturn(systemOutlineToSave);
        SystemOutline savedSystemoutline = systemOutlineRepository.save(systemOutlineToSave);

        // Then
        assertEquals(systemOutlineToSave, savedSystemoutline);
    }

    @Test
    public void testGetSystemOutlineById() {
        // Given
        Long systemOutlineId = 1L;
        SystemOutline systemOutline = new SystemOutline();
        systemOutline.setId(systemOutlineId);
        systemOutline.setName("Test SystemOutline");
        systemOutline.setStatus(true);

        // When
        when(systemOutlineRepository.findById(systemOutlineId)).thenReturn(java.util.Optional.of(systemOutline));
        SystemOutline retrievedSystemOutline = systemOutlineRepository.findById(systemOutlineId).orElse(null);

        // Then
        assertEquals(systemOutline, retrievedSystemOutline);
    }

    @Test
    public void testGetAllSystemOutlines() {
        // Given
        SystemOutline systemOutline1 = new SystemOutline();
        systemOutline1.setId(1L);
        systemOutline1.setName("Detail Design");
        systemOutline1.setStatus(true);

        SystemOutline systemOutline2 = new SystemOutline();
        systemOutline2.setId(2L);
        systemOutline2.setName("Jane Smith");
        systemOutline2.setStatus(true);

        List<SystemOutline> expectedSystemOutllines = Arrays.asList(systemOutline1, systemOutline2);

        // When
        when(systemOutlineRepository.findAll()).thenReturn(expectedSystemOutllines);
        List<SystemOutline> retrievedSystemOutlines = systemOutlineRepository.findAll();
        // Then
        assertEquals(expectedSystemOutllines, retrievedSystemOutlines);
    }
}
