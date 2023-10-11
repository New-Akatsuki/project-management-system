package org.blank.projectmanagementsystem.domain.entity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

public class IssueTest {
    private Issue issue;

    @BeforeEach
    public void setUp() {
        issue = new Issue();
    }

    @Test
    public void testIdGetterAndSetter() {
        issue.setId(1L);
        assertEquals(1L, issue.getId());
    }

    @Test
    public void testTitleGetterAndSetter() {
        issue.setTitle("JunitTest Title"); // Set the initial value to match the expected value
        assertEquals("JunitTest Title", issue.getTitle());
    }


    @Test
    public void testContentGetterAndSetter() {
        issue.setContent("JunitTest Content");
        assertEquals("JunitTest Content", issue.getContent());
    }

    @Test
    public void testCreatedAtGetterAndSetter() {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        issue.setCreatedAt(timestamp);
        assertEquals(timestamp, issue.getCreatedAt());
    }

    @Test
    public void testSolvedGetterAndSetter() {
        issue.setSolved(true);
        assertTrue(issue.isSolved());
    }

    @Test
    public void testImpactGetterAndSetter() {
        issue.setImpact(3);
        assertEquals(3, issue.getImpact());
    }

    @Test
    public void testDirectCauseGetterAndSetter() {
        issue.setDirect_cause("JunitTest Direct Cause");
        assertEquals("JunitTest Direct Cause", issue.getDirect_cause());
    }

    @Test
    public void testRootCauseGetterAndSetter() {
        issue.setRoot_cause("JunitTest Root Cause");
        assertEquals("JunitTest Root Cause", issue.getRoot_cause());
    }

    @Test
    public void testCorrectiveActionGetterAndSetter() {
        issue.setCorrectiveAction("JunitTest Corrective Action");
        assertEquals("JunitTest Corrective Action", issue.getCorrectiveAction());
    }

    @Test
    public void testPreventiveActionGetterAndSetter() {
        issue.setPreventiveAction("JunitTest Preventive Action");
        assertEquals("JunitTest Preventive Action", issue.getPreventiveAction());
    }

    @Test
    public void testResponsiblePartyGetterAndSetter() {
        ResponsibleParty responsibleParty = new ResponsibleParty();
        issue.setResponsibleParty(responsibleParty);
        assertEquals(responsibleParty, issue.getResponsibleParty());
    }

    @Test
    public void testIssuePlaceGetterAndSetter() {
        IssuePlace issuePlace = new IssuePlace();
        issue.setIssue_place(issuePlace);
        assertEquals(issuePlace, issue.getIssue_place());
    }

    @Test
    public void testIssueCategoryGetterAndSetter() {
        Set<IssueCategory> issueCategories = new HashSet<>();
        issue.setIssue_category(issueCategories);
        assertEquals(issueCategories, issue.getIssue_category());
    }

    @Test
    public void testCreatedByGetterAndSetter() {
        User createdBy = new User();
        issue.setCreatedBy(createdBy);
        assertEquals(createdBy, issue.getCreatedBy());
    }
}
