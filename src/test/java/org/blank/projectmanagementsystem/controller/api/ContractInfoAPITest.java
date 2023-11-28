package org.blank.projectmanagementsystem.controller.api;

import org.blank.projectmanagementsystem.domain.Enum.ArchitectureType;
import org.blank.projectmanagementsystem.domain.entity.Architecture;
import org.blank.projectmanagementsystem.domain.entity.Client;
import org.blank.projectmanagementsystem.domain.entity.Deliverable;
import org.blank.projectmanagementsystem.domain.entity.SystemOutline;
import org.blank.projectmanagementsystem.repository.ArchitectureRepository;
import org.blank.projectmanagementsystem.repository.ClientRepository;
import org.blank.projectmanagementsystem.repository.DeliverableRepository;
import org.blank.projectmanagementsystem.repository.SystemOutlineRepository;
import org.blank.projectmanagementsystem.service.*;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import javax.print.attribute.standard.Media;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@WebMvcTest(controllers = ContractInfoAPI.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
class ContractInfoAPITest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private SystemOutlineService systemOutlineService;
    @MockBean
    private ClientService clineService;
    @MockBean
    private ArchitectureService architectureService;
    @MockBean
    private DeliverableService deliverableService;
    @MockBean
    private ReportService reportService;
    @MockBean
    private UserService userService;
    @MockBean
    private PasswordEncoder passwordEncoder;
    @MockBean
    private ClientRepository clientRepository;
    @MockBean
    private SystemOutlineRepository systemOutlineRepository;
    @MockBean
    private ArchitectureRepository architectureRepository;
    @MockBean
    private DeliverableRepository deliverableRepository;

    private SystemOutline systemOutline;
    private Client client;
    private Architecture architecture;
    private Deliverable deliverable;

    @BeforeEach
    public void init() {
        systemOutline = SystemOutline.builder()
                .id(1L)
                .name("System Outline 1")
                .status(true)
                .build();
        client = Client.builder()
                .id(1L)
                .name("Yephone Aung")
                .email("Yephoneaung33002@gmail.com")
                .phoneNumber("09777777777")
                .status(true)
                .build();
        architecture = Architecture.builder()
                .id(1L)
                .name("Architecture 1")
                .type(ArchitectureType.API)
                .status(true)
                .build();
        deliverable = Deliverable.builder()
                .id(1L)
                .name("Deliverable 1")
                .status(true)
                .build();
    }

    @Test
    public void contractInfoApi_getContractInfo() throws Exception {
        when(systemOutlineService.getAllSystemOutlines()).thenReturn(List.of(systemOutline));
        when(clineService.getAllClients()).thenReturn(List.of(client));
        when(architectureService.getAllArchitectures()).thenReturn(List.of(architecture));
        when(deliverableService.getAllDeliverables()).thenReturn(List.of(deliverable));

        ResultActions response = mockMvc.perform(get("/get-contract-info")
                .contentType(MediaType.APPLICATION_JSON)
                .param("systemId", "1")
                .param("systemName", "System Outline 1")
                .param("systemStatus", "true")
                .param("clientId", "1")
                .param("clientName", "Yephone Aung")
                .param("clientEmail", "Yephoneaung33002@gmail.com")
                .param("clientPhoneNumber", "09777777777")
                .param("clientStatus", "true")
                .param("architectureId", "1")
                .param("architectureName", "Architecture 1")
                .param("architectureType", "API")
                .param("architectureStatus", "true")
                .param("deliverableId", "1")
                .param("deliverableName", "Deliverable 1")
                .param("deliverableStatus", "true"));

        // Perform assertions
        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.systems[0].id", CoreMatchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.systems[0].name", CoreMatchers.is("System Outline 1")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.systems[0].status", CoreMatchers.is(true)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.clients[0].id", CoreMatchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.clients[0].name", CoreMatchers.is("Yephone Aung")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.clients[0].email", CoreMatchers.is("Yephoneaung33002@gmail.com")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.clients[0].phoneNumber", CoreMatchers.is("09777777777")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.clients[0].status", CoreMatchers.is(true)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.architectures[0].id", CoreMatchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.architectures[0].name", CoreMatchers.is("Architecture 1")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.architectures[0].type", CoreMatchers.is("API")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.architectures[0].status", CoreMatchers.is(true)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.deliverables[0].id", CoreMatchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.deliverables[0].name", CoreMatchers.is("Deliverable 1")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.deliverables[0].status", CoreMatchers.is(true)))
                .andDo(MockMvcResultHandlers.print());
    }

}