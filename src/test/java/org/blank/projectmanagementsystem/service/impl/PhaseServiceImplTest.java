package org.blank.projectmanagementsystem.service.impl;

import org.blank.projectmanagementsystem.domain.entity.Phase;
import org.blank.projectmanagementsystem.domain.entity.Project;
import org.blank.projectmanagementsystem.domain.entity.Task;
import org.blank.projectmanagementsystem.dto.PhaseDto;
import org.blank.projectmanagementsystem.repository.PhaseRepository;
import org.blank.projectmanagementsystem.repository.ProjectRepository;
import org.blank.projectmanagementsystem.repository.TaskRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class PhaseServiceImplTest {

    @Mock
    private PhaseRepository phaseRepository;

    @Mock
    private ProjectRepository projectRepository;

    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private PhaseServiceImpl phaseService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getPhases() {
        long projectId = 1L;
        when(phaseRepository.findByProjectId(projectId))
                .thenReturn(Collections.singletonList(Phase.builder().project(Project.builder().id(1L).build()).build()));

        assertEquals(1, phaseService.getPhases(projectId).size());
    }

    @Test
    void createPhase() {
        PhaseDto phaseDto = new PhaseDto();
        phaseDto.setName("Test Phase");
        phaseDto.setProjectId(1L);

        Project project = new Project();
        project.setId(1L);
        when(projectRepository.findById(phaseDto.getProjectId())).thenReturn(Optional.of(project));

        Phase savedPhase = new Phase();
        savedPhase.setId(1L);
        savedPhase.setName("Test Phase");
        savedPhase.setProject(Project.builder().id(1L).build());
        when(phaseRepository.save(any(Phase.class))).thenReturn(savedPhase);

        PhaseDto result = phaseService.createPhase(phaseDto);

        assertEquals(savedPhase.getId(), result.getId());
        assertEquals(savedPhase.getName(), result.getName());
        assertEquals(savedPhase.getProject().getId(), result.getProjectId());
    }

    @Test
    void updatePhase() {
        PhaseDto phaseDto = new PhaseDto();
        phaseDto.setId(1L);
        phaseDto.setName("Updated Phase");

        Phase existingPhase = new Phase();
        existingPhase.setId(1L);
        existingPhase.setName("Old Phase");
        existingPhase.setProject(Project.builder().id(1L).build());
        when(phaseRepository.findById(phaseDto.getId())).thenReturn(Optional.of(existingPhase));
        when(phaseRepository.save(any(Phase.class))).thenAnswer(invocation -> invocation.getArgument(0));

        PhaseDto result = phaseService.updatePhase(phaseDto);

        assertEquals(phaseDto.getId(), result.getId());
        assertEquals(phaseDto.getName(), result.getName());
    }

    @Test
    void deletePhase() {
        long phaseId = 1L;
        Phase phase = new Phase();
        phase.setId(phaseId);

        when(phaseRepository.findById(phaseId)).thenReturn(Optional.of(phase));
        when(taskRepository.findAllByPhase(phase)).thenReturn(List.of(Task.builder().assignees(new HashSet<>()).build()));

        phaseService.deletePhase(phaseId);

        verify(taskRepository, times(1)).findAllByPhase(any(Phase.class));
        verify(taskRepository, times(1)).findAllByParentTask(any(Task.class));
        verify(phaseRepository, times(1)).deleteById(phaseId);
    }

    @Test
    void deletePhaseWhenSubtaskExist() {
        long phaseId = 1L;
        Phase phase = new Phase();
        phase.setId(phaseId);

        when(phaseRepository.findById(phaseId)).thenReturn(Optional.of(phase));
        when(taskRepository.findAllByParentTask(Task.builder().id(1L).assignees(new HashSet<>()).build())).thenReturn(List.of(Task.builder().assignees(new HashSet<>()).build()));
        when(taskRepository.findAllByPhase(phase)).thenReturn(List.of(Task.builder()
                        .id(1L)
                .assignees(new HashSet<>()).build()));

        phaseService.deletePhase(phaseId);

        verify(taskRepository, times(1)).findAllByPhase(any(Phase.class));
        verify(taskRepository, times(2)).findAllByParentTask(any(Task.class));
        verify(phaseRepository, times(1)).deleteById(phaseId);
    }
}
