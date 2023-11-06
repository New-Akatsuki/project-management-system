package org.blank.projectmanagementsystem.domain.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class SystemOutlineTest {

    private SystemOutline systemOutline;
    private SystemOutline systemOutlineWithBuilder;

    @BeforeEach
    public void setUp() {
        systemOutline = new SystemOutline();
        systemOutline.setId(1L);
        systemOutline.setName("Outline A");

        systemOutlineWithBuilder = SystemOutline.builder()
                .id(2L)
                .name("Outline C")
                .build();


    }

    @Test
    public void testSystemOutlineAttributes() {
        assertEquals(1, systemOutline.getId());
        assertEquals("Outline A", systemOutline.getName());

        assertEquals(2, systemOutlineWithBuilder.getId());
        assertEquals("Outline C", systemOutlineWithBuilder.getName());


    }

    @Test
    public void testEquals() {
        SystemOutline sameSystemOutline = new SystemOutline();
        sameSystemOutline.setId(1L);
        sameSystemOutline.setName("Outline A");

        SystemOutline anotherSystemOutlineWithBuilder =SystemOutline.builder()
                .id(2L)
                .name("Outline C")
                .build();



        assertEquals(systemOutline, sameSystemOutline);
        assertEquals(systemOutlineWithBuilder, anotherSystemOutlineWithBuilder);
    }

    @Test
    public void testNotEquals() {


        assertNotEquals(systemOutline, systemOutlineWithBuilder);
    }

    @Test
    public void testHashCode() {
        SystemOutline sameSystemOutline = new SystemOutline();
        sameSystemOutline.setId(1L);
        sameSystemOutline.setName("Outline A");


        assertEquals(systemOutline.hashCode(), sameSystemOutline.hashCode());
    }
}

