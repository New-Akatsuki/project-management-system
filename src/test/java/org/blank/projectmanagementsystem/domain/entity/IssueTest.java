package org.blank.projectmanagementsystem.domain.entity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

public class IssueTest {
    private Issue issue;

    private Issue issueWithBuilder;

    @BeforeEach
    public void setUp() {
        // Setting up the issue object using setters
        issue = new Issue();
        issue.setId(1L);
        issue.setTitle("Junit Test");
        issue.setContent("Sample content for JUnit Test");
        issue.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        issue.setSolved(false);
        issue.setImpact(2);
        issue.setDirectCause("Testing direct cause");
        issue.setRootCause("Testing root cause");
        issue.setCorrectiveAction("Testing corrective action");
        issue.setPreventiveAction("Testing preventive action");


       /* ResponsibleParty = new ResponsibleParty();
        responsibleParty.setId(1L);
       // responsibleParty.setProvider("Testing Responsible Party");

        IssuePlace issuePlace = new IssuePlace();
        issuePlace.setId(3L);
        issuePlace.setName("Sample Issue Place");

        IssueCategory = new IssueCategory();
        issueCategory.setId(1L);
        issueCategory.setName("Sample Issue Category");

        User createdBy = new User();
        createdBy.setId(1L);
        createdBy.setUsername("sample_user");

        issue.setResponsibleParty(responsibleParty);
        issue.setIssue_place(issuePlace);
        Set<IssueCategory> issueCategories = new HashSet<>();
        issueCategories.add(issueCategory);
        issue.setIssue_category(issueCategories);
        issue.setCreatedBy(createdBy);*/

        // Setting up the issueWithBuilder object using builder pattern
        issueWithBuilder = Issue.builder()
                .id(2L)
                .title("Entity Test")
                .content("Entity for JUnit Test")
                .createdAt(new Timestamp(System.currentTimeMillis()))
                .solved(true)
                .impact(3)
                .directCause("Another sample direct cause")
                .rootCause("Another sample root cause")
                .correctiveAction("Another sample corrective action")
                .preventiveAction("Another sample preventive action")
                /*.responsibleParty(responsibleParty)
                .issue_place(issuePlace)
                .issue_category(new HashSet<>(Set.of(issueCategory)))
                .createdBy(createdBy)*/
                .build();
    }


    @Test
    public void testIdGetterAndSetter() {
        issue.setId(1L);
        assertEquals(1L, issue.getId());

        issueWithBuilder.setId(2L);
        assertEquals(2L, issueWithBuilder.getId());

    }

    @Test
    public void testTitleGetterAndSetter() {
        issue.setTitle("JunitTest Title"); // Set the initial value to match the expected value
        assertEquals("JunitTest Title", issue.getTitle());

        issue.setTitle("Entity Test"); // Set the initial value to match the expected value
        assertEquals("Entity Test", issue.getTitle());
    }


    @Test
    public void testContentGetterAndSetter() {
        issue.setContent("JunitTest Content");
        assertEquals("JunitTest Content", issue.getContent());

        issue.setContent("Entity Content");
        assertEquals("Entity Content", issue.getContent());


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

        issueWithBuilder.setSolved(true);
        assertTrue(issueWithBuilder.isSolved());


    }

    @Test
    public void testImpactGetterAndSetter() {
        issue.setImpact(3);
        assertEquals(3, issue.getImpact());

        issueWithBuilder.setImpact(3);
        assertEquals(3, issueWithBuilder.getImpact());


    }

    @Test
    public void testDirectCauseGetterAndSetter() {
        issue.setDirectCause("JunitTest Direct Cause");
        assertEquals("JunitTest Direct Cause", issue.getDirectCause());

        issueWithBuilder.setDirectCause("JunitTest Direct Cause");
        assertEquals("JunitTest Direct Cause", issueWithBuilder.getDirectCause());


    }

    @Test
    public void testRootCauseGetterAndSetter() {
        issue.setRootCause("JunitTest Root Cause");
        assertEquals("JunitTest Root Cause", issue.getRootCause());

        issueWithBuilder.setRootCause("JunitTest Root Cause");
        assertEquals("JunitTest Root Cause", issueWithBuilder.getRootCause());


    }

    @Test
    public void testCorrectiveActionGetterAndSetter() {
        issue.setCorrectiveAction("JunitTest Corrective Action");
        assertEquals("JunitTest Corrective Action", issue.getCorrectiveAction());

        issueWithBuilder.setCorrectiveAction("JunitTest Corrective Action");
        assertEquals("JunitTest Corrective Action", issueWithBuilder.getCorrectiveAction());
    }

    @Test
    public void testPreventiveActionGetterAndSetter() {
        issue.setPreventiveAction("JunitTest Preventive Action");
        assertEquals("JunitTest Preventive Action", issue.getPreventiveAction());

        issueWithBuilder.setPreventiveAction("JunitTest Preventive Action");
        assertEquals("JunitTest Preventive Action", issueWithBuilder.getPreventiveAction());
    }

    @Test
    public void testResponsiblePartyGetterAndSetter() {
        ResponsibleParty responsibleParty = new ResponsibleParty();
        issue.setResponsibleParty(responsibleParty);
        assertEquals(responsibleParty, issue.getResponsibleParty());

        issueWithBuilder.setResponsibleParty(responsibleParty);
        assertEquals(responsibleParty, issueWithBuilder.getResponsibleParty());


    }

    @Test
    public void testIssuePlaceGetterAndSetter() {
        IssuePlace issuePlace = new IssuePlace();
        issue.setIssuePlace(issuePlace);
        assertEquals(issuePlace, issue.getIssuePlace());

        issueWithBuilder.setIssuePlace(issuePlace);
        assertEquals(issuePlace, issueWithBuilder.getIssuePlace());
    }

//    @Test
//    public void testIssueCategoryGetterAndSetter() {
//        Set<IssueCategory> issueCategories = new HashSet<>();
//        issue.setIssue_category(issueCategories);
//        assertEquals(issueCategories, issue.getIssue_category());
//
//        issueWithBuilder.setIssue_category(issueCategories);
//        assertEquals(issueCategories, issueWithBuilder.getIssue_category());
//    }

    @Test
    public void testCreatedByGetterAndSetter() {
        User createdBy = new User();
        issue.setCreatedBy(createdBy);
        assertEquals(createdBy, issue.getCreatedBy());

        issueWithBuilder.setCreatedBy(createdBy);
        assertEquals(createdBy, issueWithBuilder.getCreatedBy());
    }

    @Test
    public void testEquals() {
        Issue anotherIssue = new Issue();
        anotherIssue.setId(1L);
        anotherIssue.setTitle("Junit Test");
        anotherIssue.setContent("Sample content for JUnit Test");
        anotherIssue.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        anotherIssue.setSolved(false);
        anotherIssue.setImpact(2);
        anotherIssue.setDirectCause("Testing direct cause");
        anotherIssue.setRootCause("Testing root cause");
        anotherIssue.setCorrectiveAction("Testing corrective action");
        anotherIssue.setPreventiveAction("Testing preventive action");

        /*anotherIssue.setResponsibleParty(responsibleParty);
        anotherIssue.setIssue_place(issuePlace);
        Set<IssueCategory> issueCategories = new HashSet<>();
        issueCategories.add(issueCategory);
        anotherIssue.setIssue_category(issueCategories);
        anotherIssue.setCreatedBy(createdBy);*/

        // Setting up the anotherIssueWithBuilder object using builder pattern
        Issue anotherIssueWithBuilder = Issue.builder()
                .id(2L)
                .title("Entity Test")
                .content("Entity for JUnit Test")
                .createdAt(new Timestamp(System.currentTimeMillis()))
                .solved(true)
                .impact(3)
                .directCause("Another sample direct cause")
                .rootCause("Another sample root cause")
                .correctiveAction("Another sample corrective action")
                .preventiveAction("Another sample preventive action")
               /* .responsibleParty(responsibleParty)
                .issue_place(issue_place)
                .issue_category(new HashSet<>(Set.of(issueCategory)))
                .createdBy(createdBy)*/
                .build();

        assertEquals(issue, anotherIssue);
        assertEquals(issueWithBuilder, anotherIssueWithBuilder);
    }




}
