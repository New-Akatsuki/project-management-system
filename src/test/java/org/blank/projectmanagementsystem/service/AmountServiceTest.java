package org.blank.projectmanagementsystem.service;
import org.blank.projectmanagementsystem.domain.Enum.DevelopmentPhase;
import org.blank.projectmanagementsystem.domain.entity.Amount;
import org.blank.projectmanagementsystem.domain.entity.Project;
import org.blank.projectmanagementsystem.repository.AmountRepository;
import org.blank.projectmanagementsystem.service.impl.AmountServiceImp;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AmountServiceTest {

    @Mock
    private AmountRepository amountRepository;

    @InjectMocks
    private AmountServiceImp amountService;

    @Test
    public void testGetAllAmount() {
        // Given
        Amount amount1 = new Amount();
        Amount amount2 = new Amount();
        List<Amount> amounts = Arrays.asList(amount1, amount2);

        // When
        when(amountRepository.findAll()).thenReturn(amounts);

        // Then
        List<Amount> foundAmounts = amountService.getAllAmount();
        assertThat(foundAmounts).isEqualTo(amounts);
    }

    @Test
    public void testFindByProjectId() {
        // Given
        Long projectId = 1L;
        Amount amount1 = new Amount();
        Amount amount2 = new Amount();
        List<Amount> amounts = Arrays.asList(amount1, amount2);

        // When
        when(amountRepository.findByProjectId(projectId)).thenReturn(amounts);

        // Then
        List<Amount> foundAmounts = amountService.findByProjectId(projectId);
        assertThat(foundAmounts).isEqualTo(amounts);
    }

    @Test
    public void testFindByProjectIdAndDevelopmentPhase() {
        // Given
        Long projectId = 1L;
        DevelopmentPhase developmentPhase = DevelopmentPhase.BASIC_DESIGN;

        // Create a Project entity
        Project project = new Project();
        project.setId(projectId);

        Amount amount = new Amount();
        amount.setProject(project); // Set the entire Project object
        amount.setDevelopmentPhase(developmentPhase);

        // When
        when(amountRepository.findByProjectIdAndDevelopmentPhase(projectId, developmentPhase)).thenReturn(amount);

        // Then
        Amount foundAmount = amountService.findByProjectIdAndDevelopmentPhase(projectId, developmentPhase);
        assertThat(foundAmount).isEqualTo(amount);
    }

}
