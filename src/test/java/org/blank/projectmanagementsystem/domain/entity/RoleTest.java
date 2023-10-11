package org.blank.projectmanagementsystem.domain.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class RoleTest {

    private Role role;
    private Role roleWithBuilder;


    @BeforeEach
    public void setUp() {
        role = new Role();
        role.setId(1);
        role.setName("ADMIN");

        role = new Role(1, "ADMIN");
    }

    @Test
    public void testRoleAttributes() {
        assertEquals(1, role.getId());
        assertEquals("ADMIN", role.getName());
    }

    @Test
    public void testEquals() {
        Role sameRole = new Role();
        sameRole.setId(1);
        sameRole.setName("ADMIN");

        assertEquals(role, sameRole);
    }

    @Test
    public void testNotEquals() {
        Role differentRole = new Role();
        differentRole.setId(2);
        differentRole.setName("USER");

        assertNotEquals(role, differentRole);
    }

    @Test
    public void testHashCode() {
        Role sameRole = new Role();
        sameRole.setId(1);
        sameRole.setName("ADMIN");

        assertEquals(role.hashCode(), sameRole.hashCode());
    }
    @Test
    public void testNotHashCode(){
        Role differentRole=new Role();
        differentRole.setId(2);
        differentRole.setName("USER");

    }
    @Test
    public void testToString() {
        String expected = "Role(id=1, name=ADMIN)";
        assertEquals(expected, role.toString());
    }
}
