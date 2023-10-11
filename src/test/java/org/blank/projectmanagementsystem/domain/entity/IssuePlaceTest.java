package org.blank.projectmanagementsystem.domain.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class IssuePlaceTest {

    private IssuePlace issuePlace;

    @BeforeEach
    public void setUp() {
        issuePlace = new IssuePlace();
        issuePlace.setId(1);
        issuePlace.setName("EntityTest");
    }

    @Test
    public void testIssuePlaceId() {
        assertEquals(1, issuePlace.getId());
    }

    @Test
    public void testIssuePlaceName() {
        assertEquals("EntityTest", issuePlace.getName());
    }

    @Test
    public void testIssuePlaceIdSetter() {
        issuePlace.setId(2);
        assertEquals(2, issuePlace.getId());
    }

    @Test
    public void testIssuePlaceNameSetter() {
        issuePlace.setName("New Place");
        assertEquals("New Place", issuePlace.getName());
    }

    @Test
    public void testIssuePlaceEquality() {
        IssuePlace samePlace = new IssuePlace();
        samePlace.setId(1);
        samePlace.setName("EntityTest");

        assertEquals(issuePlace, samePlace);
    }

    @Test
    public void testIssuePlaceInequality() {
        IssuePlace differentPlace = new IssuePlace();
        differentPlace.setId(2);
        differentPlace.setName("Different Place");

        assertNotEquals(issuePlace, differentPlace);
    }


}

