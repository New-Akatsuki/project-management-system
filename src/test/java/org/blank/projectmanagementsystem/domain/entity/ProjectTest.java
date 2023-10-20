package org.blank.projectmanagementsystem.domain.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import static org.junit.jupiter.api.Assertions.*;

public class ProjectTest {

    private Project project;
    private Project projectWithBuilder;



    @BeforeEach
    public void setUp() {
        project=new Project();
        project.setId(1L);
        project.setName("Testing Name");
        project.setBackground("Testing background description");
        project.setObjective("Testing objective description");
        project.setDuration(30.5f);
        project.setStartDate(new Date());
        project.setEndDate(new Date());



        project = Project.builder()
                .id(1L)
                .name("Testing Project")
                .background("Testing background description")
                .objective("Testing objective description")
                .duration(30.5f)
                .startDate(new Date())
                .endDate(new Date())
                /*.projectManager(projectManager)
                .contractMember(contractMembers)
                .focMember(focMembers)
                .architectures(architectures)
                .systemOutlines(systemOutlines)
                .deliverables(deliverables)
                .client(client)*/
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
       /* assertEquals(projectManager, project.getProjectManager());
        assertEquals(contractMembers, project.getContractMember());
        assertEquals(focMembers, project.getFocMember());
        assertEquals(architectures, project.getArchitectures());
        assertEquals(systemOutlines, project.getSystemOutlines());
        assertEquals(deliverables, project.getDeliverables());
        assertEquals(client, project.getClient());*/
    }

   @Test
    public void testEquals() {
        Project sameProject = Project.builder()
                .id(1L)
                .name("Testing Project")
                .background("Testing background description")
                .objective("Testing objective description")
                .duration(30.5f)
                .startDate(new Date())
                .endDate(new Date())
               /* .projectManager(projectManager)
                .contractMember(contractMembers)
                .focMember(focMembers)
                .architectures(architectures)
                .systemOutlines(systemOutlines)
                .deliverables(deliverables)
                .client(client)*/
                .build();

        assertEquals(project, sameProject);
    }

    @Test
    public void testNotEquals() {
        Project differentProject = Project.builder()
                .id(2L)
                .name("Testing Project")
                .background("Testing background description")
                .objective("Testing objective description")
                .duration(30.5f)
                .startDate(new Date())
                .endDate(new Date())
                /*.projectManager(projectManager)
                .contractMember(contractMembers)
                .focMember(focMembers)
                .architectures(architectures)
                .systemOutlines(systemOutlines)
                .deliverables(deliverables)
                .client(client)*/
                .build();

        assertNotEquals(project, differentProject);
    }

    @Test
        public void testHashCode() {
        Project sameProject = Project.builder()
                .id(1L)
                .name("Testing Project")
                .background("Testing background description")
                .objective("Testing objective description")
                .duration(30.5f)
                .startDate(new Date())
                .endDate(new Date())
               /* .projectManager(projectManager)
                .contractMember(contractMembers)
                .focMember(focMembers)
                .architectures(architectures)
                .systemOutlines(systemOutlines)
                .deliverables(deliverables)
                .client(client)*/
                .build();

        assertEquals(project.hashCode(), sameProject.hashCode());
    }


}