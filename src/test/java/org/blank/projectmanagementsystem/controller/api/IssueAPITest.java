package org.blank.projectmanagementsystem.controller.api;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import static org.junit.jupiter.api.Assertions.*;
@WebMvcTest(controllers = IssueAPI.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
class IssueAPITest {

    @Test
    void getIssueCategory() {
    }

    @Test
    void getIssuePlace() {
    }

    @Test
    void getResponsibleParty() {
    }

    @Test
    void getPic() {
    }

    @Test
    void createIssue() {
    }

    @Test
    void createResParty() {
    }

    @Test
    void createIssuePlace() {
    }

    @Test
    void createIssueCategory() {
    }

    @Test
    void getAllIssue() {
    }

    @Test
    void getIssueDetailsById() {
    }

    @Test
    void getIssueSolutionById() {
    }

    @Test
    void getIssueByPic() {
    }

    @Test
    void addSolutionToIssue() {
    }

    @Test
    void getIssueByCreatedBy() {
    }
}