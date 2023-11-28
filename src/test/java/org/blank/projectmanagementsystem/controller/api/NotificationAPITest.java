package org.blank.projectmanagementsystem.controller.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.blank.projectmanagementsystem.domain.Enum.NotificationType;
import org.blank.projectmanagementsystem.domain.entity.Notification;
import org.blank.projectmanagementsystem.repository.ArchitectureRepository;
import org.blank.projectmanagementsystem.repository.ClientRepository;
import org.blank.projectmanagementsystem.repository.DeliverableRepository;
import org.blank.projectmanagementsystem.repository.SystemOutlineRepository;
import org.blank.projectmanagementsystem.service.NotificationService;
import org.blank.projectmanagementsystem.service.ReportService;
import org.blank.projectmanagementsystem.service.UserService;
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

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@WebMvcTest(NotificationAPI.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
class NotificationAPITest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private NotificationService notificationService;
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
    @Autowired
    private ObjectMapper objectMapper;

    private Notification notification;

    @BeforeEach
    void setUp() {
        notification = Notification.builder()
                .id(1L)
                .message("test")
                .type(NotificationType.TASK)
                .isRead(false)
                .build();
    }

    @Test
    public void notificationApi_getNotificationIsOk() throws Exception {
        when(notificationService.getNotifications()).thenReturn(List.of(notification));
        ResultActions response = mockMvc.perform(get("/notifications")
                .contentType(MediaType.APPLICATION_JSON)
                .param("id", "1")
                .param("message", "test")
                .param("type", "TASK")
                .param("isRead", "false"));


        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(1L))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].message").value("test"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].type").value("TASK"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].isRead").value(false))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void testDeleteNotifications() throws Exception {
        List<Long> notificationIds = Arrays.asList(1L, 2L, 3L);

        ResultActions response = mockMvc.perform(post("/delete-notifications")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(notificationIds)));

        for (Long notificationId : notificationIds) {
            verify(notificationService).deleteNotificationById(notificationId);
        }

        response.andExpect(MockMvcResultMatchers.status().isOk());

        response.andExpect(MockMvcResultMatchers.content().string("Notifications deleted successfully"));
    }
    @Test
    public void testMarkNotificationAsRead() throws Exception {
        Long notificationId = 1L;

        ResultActions response = mockMvc.perform(post("/mark-as-read/" + notificationId)
                .contentType(MediaType.APPLICATION_JSON));

        verify(notificationService).setNotificationIsRead(notificationId);

        response.andExpect(MockMvcResultMatchers.status().isOk());

        response.andExpect(MockMvcResultMatchers.content().string("Notification marked as read successfully"));
    }
}