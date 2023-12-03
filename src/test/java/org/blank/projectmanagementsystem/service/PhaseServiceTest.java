//package org.blank.projectmanagementsystem.service;
//import org.blank.projectmanagementsystem.domain.entity.Phase;
//import org.blank.projectmanagementsystem.domain.formInput.PhaseDto;
//import org.blank.projectmanagementsystem.repository.PhaseRepository;
//import org.blank.projectmanagementsystem.service.impl.PhaseServiceImpl;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.mockito.Mockito.*;
//
//@ExtendWith(MockitoExtension.class)
//public class PhaseServiceTest {
//
//    @Mock
//    private PhaseRepository phaseRepository;
//
//    @InjectMocks
//    private PhaseServiceImpl phaseService;
//
//    @Test
//    public void testGetPhases() {
//        // Given
//        long projectId = 1L;
//        List<Phase> mockPhases = new ArrayList<>();
//        // Add mock Phase objects to the list
//
//        // When
//        when(phaseRepository.findByProjectId(projectId)).thenReturn(mockPhases);
//
//        // Then
//        List<PhaseDto> result = phaseService.getPhases(projectId);
//        // Add assertions based on your actual implementation and data
//
//    }
//
//    @Test
//    public void testCreatePhase() {
//        // Given
//        PhaseDto phaseDto = new PhaseDto();
//        // Add mock values to phaseDto
//
//        // When
//        when(phaseRepository.save(any(Phase.class))).thenReturn(new Phase());
//
//        // Then
//        PhaseDto result = phaseService.createPhase(phaseDto);
//        // Add assertions based on your actual implementation and data
//
//    }
//
//
//
//
//
//
//
//    // Add more test methods as needed
//
//}
//
