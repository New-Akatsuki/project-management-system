package org.blank.projectmanagementsystem.service.impl;

import org.blank.projectmanagementsystem.domain.entity.SystemOutline;
import org.blank.projectmanagementsystem.repository.SystemOutlineRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;


public class SystemOutlineServiceImplTest {
    @Mock
    private SystemOutlineRepository systemOutlineRepository;
    @InjectMocks
    private SystemOutlineServiceImpl systemOutlineService;
    @Test
    void testGetAllSystemOutlines(){
        //Mocking repository behavior
        List<SystemOutline> systemOutlines = List.of(new SystemOutline(),new SystemOutline());
        when(systemOutlineRepository.findAll()).thenReturn(systemOutlines);
        List<SystemOutline> retrievedSystemOutlines = systemOutlineService.getAllSystemOutlines();
        assertEquals(systemOutlines.size(),retrievedSystemOutlines.size());
        verify(systemOutlineRepository,times(1)).findAll();

    }
    @Test
    void testSave(){
        SystemOutline systemOutline = new SystemOutline();
        when(systemOutlineRepository.save(systemOutline)).thenReturn(systemOutline);
        SystemOutline savedSystemOutline = systemOutlineService.save(systemOutline);
        assertEquals(systemOutline,savedSystemOutline);
        verify(systemOutlineRepository,times(1)).save(systemOutline);
    }
    @Test
    void testGetSystemOutlineById(){
        long id =1L;
        SystemOutline systemOutline = new SystemOutline();
        systemOutline.setId(id);
        when(systemOutlineRepository.findById(id)).thenReturn(java.util.Optional.of(systemOutline));
        SystemOutline retrievedSystemOutline = systemOutlineService.getSystemOutlineById(id);
        assertEquals(id,retrievedSystemOutline.getId());
        verify(systemOutlineRepository,times(1)).findById(id);
    }





}
