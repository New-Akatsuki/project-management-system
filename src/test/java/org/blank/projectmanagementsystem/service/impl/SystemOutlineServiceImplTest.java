package org.blank.projectmanagementsystem.service.impl;

import org.blank.projectmanagementsystem.domain.entity.SystemOutline;
import org.blank.projectmanagementsystem.repository.SystemOutlineRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;

public class SystemOutlineServiceImplTest {
    @Mock
    private SystemOutlineRepository systemOutlineRepository;
    @InjectMocks
    private SystemOutlineServiceImpl systemOutlineService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllSystemOutlines(){
        //Mocking repository behavior
        List<SystemOutline> retrievedSystemOutlines = systemOutlineService.getAllSystemOutlines();
        assertEquals(0,retrievedSystemOutlines.size());
    }
    @Test
    void testSave(){
        SystemOutline systemOutline = new SystemOutline();
        SystemOutline savedSystemOutline = systemOutlineService.save(systemOutline);
        assertNull(savedSystemOutline);
    }
    @Test
    void testGetSystemOutlineById(){
        long id =1L;
        SystemOutline systemOutline = new SystemOutline();
        systemOutline.setId(id);
        when(systemOutlineRepository.findById(id)).thenReturn(java.util.Optional.of(systemOutline));
        SystemOutline retrievedSystemOutline = systemOutlineService.getSystemOutlineById(id);
        assertEquals(id,retrievedSystemOutline.getId());
    }

    @Test
    void testGetAllSystemOutlineByStatusTrue() {
        // Mock data
        SystemOutline systemOutline1 = new SystemOutline(/* fill in constructor parameters */);
        SystemOutline systemOutline2 = new SystemOutline(/* fill in constructor parameters */);

        List<SystemOutline> systemOutlineList = List.of(systemOutline1, systemOutline2);

        // Call the method to be tested
        List<SystemOutline> result = systemOutlineService.getAllSystemOutlineByStatusTrue();

        // Assertions
        assertEquals(0, result.size());
        // Add more assertions if needed
    }

}
