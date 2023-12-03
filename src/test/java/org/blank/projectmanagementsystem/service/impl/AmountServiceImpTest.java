package org.blank.projectmanagementsystem.service.impl;

import org.blank.projectmanagementsystem.domain.Enum.DevelopmentPhase;
import org.blank.projectmanagementsystem.domain.entity.Amount;
import org.blank.projectmanagementsystem.domain.entity.Project;

import org.blank.projectmanagementsystem.domain.formInput.AmountDto;
import org.blank.projectmanagementsystem.repository.AmountRepository;
import org.blank.projectmanagementsystem.repository.ProjectRepository;
import org.blank.projectmanagementsystem.service.ProjectService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;


public class AmountServiceImpTest {
    @Mock
    public AmountRepository amountRepository;
    @Mock
    public ProjectService projectService;
    @Mock
    private ProjectRepository projectRepository;

    @InjectMocks
    private AmountServiceImp amountService;
    @BeforeEach
    void setUp(){
        MockitoAnnotations.initMocks(this);
    }
    @Test
    void testSaveOrUpdate(){
        // Create a mock AmountDto
        AmountDto amountDto = new AmountDto();
        amountDto.setProjectId(1L);
        amountDto.setDevelopmentPhase(DevelopmentPhase.BASIC_DESIGN);
        amountDto.setAmount(100);

        // Create mock objects for existing amount and project
        Amount existingAmount = new Amount();
        existingAmount.setProject(new Project());
        existingAmount.setDevelopmentPhase(DevelopmentPhase.BASIC_DESIGN);
        existingAmount.setAmount(50);
        Project project =new Project();
        // Mock the behavior of repositories
        when(amountRepository.findByProjectIdAndDevelopmentPhase(1L, DevelopmentPhase.BASIC_DESIGN
        )).thenReturn(existingAmount);
        when(projectRepository.findById(1L)).thenReturn(Optional.of(project));

        // Test saving an existing amount
        AmountDto savedAmount = amountService.saveOrUpdate(amountDto);
        // Verify that the amount was updated
        assertEquals(amountDto.getAmount(), savedAmount.getAmount());
        // Test saving a new amount
        when(amountRepository.findByProjectIdAndDevelopmentPhase(1L, DevelopmentPhase.BASIC_DESIGN))
                .thenReturn(null);
        AmountDto newSavedAmount = amountService.saveOrUpdate(amountDto);
        // Verify that the amount was saved
        assertEquals(amountDto.getAmount(), newSavedAmount.getAmount());


    }
    @Test
    void testGetAllAmount(){
        // Mock a list of amounts
        List<Amount> amounts = new ArrayList<>();
        amounts.add(new Amount());
        amounts.add(new Amount());
        // Mock the behavior of the repository
        when(amountRepository.findAll()).thenReturn(amounts);
        // Test the getAllAmount() method
        List<Amount> retrievedAmounts = amountService.getAllAmount();
        // Verify that the retrieved list size matches the mocked list size
        assertEquals(amounts.size(), retrievedAmounts.size());
    }
    @Test
    void testFindByProjectId(){
        // Mock a list of amounts for a specific project ID
        Long projectId = 1L;
        List<Amount> amounts = new ArrayList<>();
        amounts.add(new Amount());
        amounts.add(new Amount());
        // Mock the behavior of the repository
        when(amountRepository.findByProjectId(projectId)).thenReturn(amounts);
        // Test the findByProjectId() method
        List<Amount> retrievedAmounts = amountService.findByProjectId(projectId);
        // Verify that the retrieved list size matches the mocked list size
        assertEquals(amounts.size(), retrievedAmounts.size());
    }
    @Test
    void testFindByProjectIdAndDevelopmentPhase(){
        // Mock an amount for a specific project ID and development phase
        Long projectId = 1L;
        DevelopmentPhase developmentPhase = DevelopmentPhase.BASIC_DESIGN;
        Amount amount = new Amount();
        // Mock the behavior of the repository
        when(amountRepository.findByProjectIdAndDevelopmentPhase(projectId, developmentPhase)).thenReturn(amount);
        // Test the findByProjectIdAndDevelopmentPhase() method
        Amount retrievedAmount = amountService.findByProjectIdAndDevelopmentPhase(projectId, developmentPhase);
        // Verify that the retrieved amount matches the mocked amount
        assertEquals(amount, retrievedAmount);
    }





}

