package org.blank.projectmanagementsystem.Service;

import org.mockito.verification.VerificationMode;
import org.springframework.boot.test.context.SpringBootTest;
import org.blank.projectmanagementsystem.service.SystemOutlineService;
import org.blank.projectmanagementsystem.domain.entity.SystemOutline;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;


@SpringBootTest
public class SystemOutlineServiceTest {
    @Test
    public void testSaveSystemOutline() {
        // Arrange
        SystemOutlineService systemOutlineService = mock(SystemOutlineService.class);
        SystemOutline systemOutline = new SystemOutline();

        // Act
        when(systemOutlineService.save(systemOutline)).thenReturn(systemOutline);
        SystemOutline savedSystemOutline = systemOutlineService.save(systemOutline);

        // Assert
        assertNotNull(savedSystemOutline);
        assertEquals(systemOutline, savedSystemOutline);
        verify(systemOutlineService, times(1)).save(systemOutline);
    }

    @Test
    public void testSaveName() {
        // Arrange
        SystemOutlineService systemOutlineService = mock(SystemOutlineService.class);
        String name = "System Outline Name";

        // Act
        systemOutlineService.saveName(name);

        // Assert
        verify(systemOutlineService, times(1)).saveName(name);
    }

    @Test
    public void testUpdateSystemOutline() {
        // Arrange
        SystemOutlineService systemOutlineService = mock(SystemOutlineService.class);
        SystemOutline systemOutline = new SystemOutline();

        // Act
        systemOutlineService.updateSystemOutline(systemOutline);

        // Assert
        verify(systemOutlineService, times(1)).updateSystemOutline(systemOutline);
    }

    @Test
    public void testDeleteSystemOutline() {
        // Arrange
        SystemOutlineService systemOutlineService = mock(SystemOutlineService.class);

        // Act
        systemOutlineService.deleteSystemOutline();

        // Assert
        verify(systemOutlineService, times(1)).deleteSystemOutline();
    }

}
