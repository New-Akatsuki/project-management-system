package org.blank.projectmanagementsystem.Repository;

import org.blank.projectmanagementsystem.domain.entity.SystemOutline;
import org.blank.projectmanagementsystem.repository.SystemOutlineRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

@DataJpaTest
public class SystemOutlineRepositoryTest {

    @Autowired
    private SystemOutlineRepository systemOutlineRepository;

    @Test
    public void testFindByName() {
        // Arrange
        SystemOutline systemOutline = new SystemOutline();
        systemOutline.setName("Test System Outline");
        systemOutlineRepository.save(systemOutline);

        // Act
        SystemOutline foundSystemOutline = systemOutlineRepository.findByName("Test System Outline");

        // Assert
        assertEquals("Test System Outline", foundSystemOutline.getName());
    }
}
