package org.blank.projectmanagementsystem.domain.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import static org.junit.jupiter.api.Assertions.*;

public class ProjectTest {

    private Project project;
    private User projectManager;
    private Set<User> contractMembers;
    private Set<User> focMembers;
    private Set<Architecture> architectures;
    private Set<SystemOutline> systemOutlines;
    private Set<Deliverable> deliverables;
    private Client client;

    @BeforeEach
    public void setUp() {
        projectManager = new User(/* initialize User object appropriately */);
        contractMembers = new HashSet<>();
        focMembers = new HashSet<>();
        architectures = new HashSet<>();
        systemOutlines = new HashSet<>();
        deliverables = new HashSet<>();
        client = new Client(/* initialize Client object appropriately */);

        project = Project.builder()
                .id(1L)
                .name("Testing Project")
                .background("Testing background description")
                .objective("Testing objective description")
                .duration(30.5f)
                .startDate(new Date())
                .endDate(new Date())
                .projectManager(projectManager)
                .contractMember(contractMembers)
                .focMember(focMembers)
                .architectures(architectures)
                .systemOutlines(systemOutlines)
                .deliverables(deliverables)
                .client(client)
                .build();
    }

    @Test
    public void testProjectAttributes() {
        assertEquals(1L, project.getId());
        assertEquals("Testing Project", project.getName());
        assertEquals("Testing background description", project.getBackground());
        assertEquals("Testing objective description", project.getObjective());
        assertEquals(30.5f, project.getDuration());
        assertNotNull(project.getStartDate());
        assertNotNull(project.getEndDate());
        assertEquals(projectManager, project.getProjectManager());
        assertEquals(contractMembers, project.getContractMember());
        assertEquals(focMembers, project.getFocMember());
        assertEquals(architectures, project.getArchitectures());
        assertEquals(systemOutlines, project.getSystemOutlines());
        assertEquals(deliverables, project.getDeliverables());
        assertEquals(client, project.getClient());
    }
}
