package org.blank.projectmanagementsystem.Repository;

import org.blank.projectmanagementsystem.domain.Enum.DevelopmentPhase;
import org.blank.projectmanagementsystem.domain.entity.Amount;
import org.blank.projectmanagementsystem.repository.AmountRepository;
import org.blank.projectmanagementsystem.service.impl.AmountServiceImp;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AmountRepositoryTest {

    @Mock
    private AmountRepository amountRepository;

    @InjectMocks
    private AmountServiceImp amountService;

   @Test
    public void testfindByProjectId() {
        // Given
        long projectId = 1L;
        List<Amount> mockAmounts = Arrays.asList(new Amount(), new Amount());
        // Add mock Amount objects to the list

        // When
        when(amountRepository.findByProjectId(projectId)).thenReturn(mockAmounts);

        // Then
        List<Amount> result = amountService.findByProjectId(projectId);
        // Add assertions based on your actual implementation and data
        assertThat(result).isEqualTo(mockAmounts);
    }
    @Test
    public void testFindByProjectIdAndDevelopmentPhase() {
        // Given
        long projectId = 1L;
        DevelopmentPhase developmentPhase = DevelopmentPhase.INITIATION;
        Amount mockAmount = new Amount();
        // Add mock Amount objects to the list

        // When
        when(amountRepository.findByProjectIdAndDevelopmentPhase(projectId, developmentPhase)).thenReturn(mockAmount);

        // Then
        Amount result = amountService.findByProjectIdAndDevelopmentPhase(projectId, developmentPhase);
        // Add assertions based on your actual implementation and data
        assertThat(result).isEqualTo(mockAmount);
    }
}
