package org.blank.projectmanagementsystem.Repository;

import org.blank.projectmanagementsystem.domain.Enum.Role;
import org.blank.projectmanagementsystem.domain.entity.Department;
import org.blank.projectmanagementsystem.domain.entity.User;
import org.blank.projectmanagementsystem.repository.UserRepository;
import org.blank.projectmanagementsystem.service.impl.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserRepositoryTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    public void testFindByUsername() {
        // Given
        String username = "john_doe";
        User user = new User();
        when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));

        // When
        Optional<User> foundUser = userService.findByUsername(username);

        // Then
        assertThat(foundUser).isPresent();
        assertThat(foundUser.get()).isEqualTo(user);
    }

    @Test
    public void testFindByEmail() {
        // Given
        String email = "john.doe@example.com";
        User user = new User();
        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));

        // When
        Optional<User> foundUser = userService.findByEmail(email);

        // Then
        assertThat(foundUser).isPresent();
        assertThat(foundUser.get()).isEqualTo(user);
    }

    @Test
    public void testFindByUsernameOrEmail() {
        // Given
        String username = "john_doe";
        String email = "john.doe@example.com";
        User user = new User();
        when(userRepository.findByUsernameOrEmail(username, email)).thenReturn(Optional.of(user));

        // When
        Optional<User> foundUser = userService.findByUsernameOrEmail(username, email);

        // Then
        assertThat(foundUser).isPresent();
        assertThat(foundUser.get()).isEqualTo(user);
    }



    @Test
    public void testCountByDepartment() {
        // Given
        Department department = new Department();
        when(userRepository.countByDepartment(department)).thenReturn(5L);

        // When
        Long count = userService.countByDepartment(department);

        // Then
        assertThat(count).isEqualTo(5L);
    }

    @Test
    public void testFindAllByDepartment() {
        // Given
        Department department = new Department();
        User user1 = new User();
        User user2 = new User();
        List<User> users = Arrays.asList(user1, user2);

        // When
        when(userRepository.findAllByDepartment(department)).thenReturn(users);

        // Then
        List<User> foundUsers = userService.findAllByDepartment(department);
        assertThat(foundUsers).isEqualTo(users);
    }

    @Test
    public void testFindAllByDepartmentAndRole() {
        // Given
        Department department = new Department();
        Role role = Role.ADMIN;
        User user1 = new User();
        User user2 = new User();
        List<User> users = Arrays.asList(user1, user2);

        // When
        when(userRepository.findAllByDepartmentAndRole(department, role)).thenReturn(users);

        // Then
        List<User> foundUsers = userService.findAllByDepartmentAndRole(department, role);
        assertThat(foundUsers).isEqualTo(users);
    }

    // Add more test cases as needed based on your repository methods
}

