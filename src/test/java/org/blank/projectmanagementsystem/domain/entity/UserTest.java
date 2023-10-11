package org.blank.projectmanagementsystem.domain.entity;



import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import java.util.Collection;
import java.util.Collections;
import static org.junit.jupiter.api.Assertions.*;

public class UserTest {

    private User user;

    @BeforeEach
    public void setUp() {
        Role role = new Role(); // Initialize Role object appropriately
        Department department = new Department(); // Initialize Department object appropriately

        user = User.builder()
                .id(1L)
                .name("John Doe")
                .username("john.doe")
                .password("password123")
                .role(role)
                .department(department)
                .active(true)
                .build();
    }

    @Test
    public void testUserAttributes() {
        assertEquals(1L, user.getId());
        assertEquals("John Doe", user.getName());
        assertEquals("john.doe", user.getUsername());
        assertEquals("password123", user.getPassword());
        assertNotNull(user.getRole());
        assertNotNull(user.getDepartment());
        assertTrue(user.isActive());
    }



    @Test
    public void testIsAccountNonExpired() {
        assertTrue(user.isAccountNonExpired());
    }

    @Test
    public void testIsAccountNonLocked() {
        assertTrue(user.isAccountNonLocked());
    }

    @Test
    public void testIsCredentialsNonExpired() {
        assertTrue(user.isCredentialsNonExpired());
    }

    @Test
    public void testIsEnabled() {
        assertTrue(user.isEnabled());
    }
}

