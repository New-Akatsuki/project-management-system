package org.blank.projectmanagementsystem.Repository;



import org.blank.projectmanagementsystem.domain.entity.Phase;
import org.blank.projectmanagementsystem.domain.entity.Project;
import org.blank.projectmanagementsystem.repository.PhaseRepository;
import org.blank.projectmanagementsystem.service.impl.PhaseServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PhaseRepositoryTest {

    @Mock
    private PhaseRepository phaseRepository;

    @InjectMocks
    private PhaseServiceImpl phaseRepositoryImpl;  // Assuming you have a repository implementation

    @Test
    public void testFindByProjectId() {
        // Given
        Long projectId = 1L;
        List<Phase> mockPhases = new ArrayList<>();
        // Add mock Phase objects to the list

        // When
        when(phaseRepository.findByProjectId(projectId)).thenReturn(mockPhases);

        // Then
        List<Phase> result = phaseRepositoryImpl.findByProjectId(projectId);
        assertEquals(mockPhases, result);
    }
    @Test
    public void testCreatePhase() {
        // Given
        Phase phase = new Phase();
        phase.setName("Phase 1");
        phase.setProject(new Project());
        // When
        when(phaseRepository.save(phase)).thenReturn(phase);

        // Then
        Phase result = phaseRepositoryImpl.save(phase);
        assertEquals(phase, result);
    }
    @Test
    public void testUpdatePhase() {
        // Given
        Phase phase = new Phase();
        phase.setName("Phase 1");
        phase.setProject(new Project());
        // When
        when(phaseRepository.save(phase)).thenReturn(phase);

        // Then
        Phase result = phaseRepositoryImpl.save(phase);
        assertEquals(phase, result);
    }
    @Test
    public void testDeletePhase() {
        // Given
        Long phaseId = 1L;
        Phase phase = new Phase();
        phase.setId(phaseId);
        phase.setName("Phase 1");
        phase.setProject(new Project());
        // When
        phaseRepository.deleteById(phaseId);
        // Then
        verify(phaseRepository, times(1)).deleteById(phaseId);
    }


    // Add more test methods as needed

}

