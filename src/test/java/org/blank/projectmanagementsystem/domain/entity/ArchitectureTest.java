package org.blank.projectmanagementsystem.domain.entity;

import org.blank.projectmanagementsystem.domain.Enum.ArchitectureType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ArchitectureTest {

    private Architecture architecture;
    private Architecture architectureWithBuilder;

    @BeforeEach
    public void setUp() {
        architecture = new Architecture();
        architecture.setId(1L);
        architecture.setName("SpringBoot");
        architecture.setType(ArchitectureType.FRAMEWORK); // Set an appropriate ArchitectureType enum value

        architectureWithBuilder = Architecture.builder()
                .id(2L)
                .name("Java")
                .type(ArchitectureType.PROGRAMMING_LANGUAGE) // Set an appropriate ArchitectureType enum value
                .build();
    }

    @Test
    public void testGetId() {
        assertEquals(1, architecture.getId());
        assertEquals(2, architectureWithBuilder.getId());
    }

    @Test
    public void testGetName() {
        assertEquals("SpringBoot", architecture.getName());
        assertEquals("Java", architectureWithBuilder.getName());
    }

    @Test
    public void testGetType() {
        assertEquals(ArchitectureType.FRAMEWORK, architecture.getType());
        assertEquals(ArchitectureType.PROGRAMMING_LANGUAGE, architectureWithBuilder.getType());
    }

    @Test
    public void testEquals() {
        Architecture anotherArchitecture = new Architecture();
        anotherArchitecture.setId(1L);
        anotherArchitecture.setName("SpringBoot");
        anotherArchitecture.setType(ArchitectureType.FRAMEWORK);

        Architecture anotherArchitectureWithBuilder = Architecture.builder()
                .id(2L)
                .name("Java")
                .type(ArchitectureType.PROGRAMMING_LANGUAGE)
                .build();

        assertEquals(architecture, anotherArchitecture);
        assertEquals(architectureWithBuilder, anotherArchitectureWithBuilder);
    }

    @Test
    public void testNotEquals() {
        assertNotEquals(architecture, architectureWithBuilder);
    }
}
