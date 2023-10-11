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
    private User userWithBuilder;

    @BeforeEach
    public void setUp() {


        user = new User();
        user.setId(1L);
        user.setName("John Doe");
        user.setUsername("john.doe");
        user.setRole(new Role(1, "ADMIN"));
        user.setDepartment(user.getDepartment());
        user.setActive(true);
        user.setPassword("password123");

        userWithBuilder = User.builder()
                .id(2L)
                .name("JSON Doe")
                .username("json.doe")
                .password("password456")
                .role(user.getRole())
                .department(user.getDepartment())
                .active(true)
                .build();
    }

    @Test
    public void testGetActive() {
        assertTrue(user.isActive());
        assertTrue(userWithBuilder.isActive());
    }


    @Test
    public void testGetId() {
        assertEquals(1L, user.getId());
        assertEquals(2L, userWithBuilder.getId());
    }

    @Test
    public void testGetName() {
        assertEquals("John Doe", user.getName());
        assertEquals("JSON Doe", userWithBuilder.getName());
    }

    @Test
    public void testGetUsername() {
        assertEquals("john.doe", user.getUsername());
        assertEquals("json.doe", userWithBuilder.getUsername());
    }

    @Test
    public void testGetPassword() {
        assertEquals("password123", user.getPassword());
        assertEquals("password123", userWithBuilder.getPassword());
    }

    @Test
    public void testGetRole() {
        assertEquals(user.getRole(), user.getRole());
        assertEquals(userWithBuilder.getRole(), userWithBuilder.getRole());
    }

    @Test
    public void testGetDepartment() {
        assertEquals(user.getDepartment(), user.getDepartment());
        assertEquals(userWithBuilder.getDepartment(), userWithBuilder.getDepartment());
    }


    @Test
    public void testEquals() {
        User anotherUser = new User();
        anotherUser.setId(1L);
        anotherUser.setName("John Doe");
        anotherUser.setUsername("john.doe");
        anotherUser.setRole(user.getRole());
        anotherUser.setDepartment(user.getDepartment());
        anotherUser.setActive(true);
        anotherUser.setPassword("password123");

        User anotherUserWithBuilder = User.builder()
                .id(1L)
                .name("John Doe")
                .username("john.doe")
                .password("password123")
                .role(user.getRole())
                .department(user.getDepartment())
                .active(true)
                .build();

        assertEquals(user, anotherUser);
        assertEquals(userWithBuilder, anotherUserWithBuilder);
    }

    @Test
    public void testNotEquals() {
        User anotherUser = new User();
        anotherUser.setId(1L);
        anotherUser.setName("John Doe");
        anotherUser.setUsername("john.doe");
        anotherUser.setRole(user.getRole());
        anotherUser.setDepartment(user.getDepartment());
        anotherUser.setActive(true);
        anotherUser.setPassword("password123");

        User anotherUserWithBuilder = User.builder()
                .id(1L)
                .name("John Doe")
                .username("john.doe")
                .password("password123")
                .role(user.getRole())
                .department(user.getDepartment())
                .active(true)
                .build();

        assertEquals(user, anotherUser);
        assertEquals(userWithBuilder, anotherUserWithBuilder);
    }

    @Test
    public void testGetAuthorities() {
        Collection<? extends GrantedAuthority> authorities = user.getAuthorities();
        assertEquals(1, authorities.size());
        assertEquals(new SimpleGrantedAuthority("ADMIN"), authorities.iterator().next());
    }

}

