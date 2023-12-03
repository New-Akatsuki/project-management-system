package org.blank.projectmanagementsystem.service.impl;

import org.blank.projectmanagementsystem.domain.entity.Department;
import org.blank.projectmanagementsystem.domain.entity.User;
import org.blank.projectmanagementsystem.domain.formInput.AddUserFormInput;
import org.blank.projectmanagementsystem.repository.DepartmentRepository;
import org.blank.projectmanagementsystem.repository.UserRepository;
import org.blank.projectmanagementsystem.service.UserService;
import org.blank.projectmanagementsystem.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private DepartmentRepository departmentRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService = new UserServiceImpl(userRepository, departmentRepository, passwordEncoder);

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSaveUser() {
        User user = new User(); // Create a user object for testing

        // Mocking repository behavior
        when(userRepository.save(user)).thenReturn(user);

        // Test userService.save() method
        User savedUser = userService.save(user);

        // Verify that the userRepository.save() method was called
        verify(userRepository, times(1)).save(user);

        // Check if the saved user matches the user returned by the service method
        assertEquals(user, savedUser);
    }

    @Test
    void testSaveDepartment() {
        Department department = new Department(); // Create a department object for testing

        // Test userService.saveDepartment() method
        userService.saveDepartment(department);

        // Verify that the departmentRepository.save() method was called
        verify(departmentRepository, times(1)).save(department);
    }

    @Test
    void testChangePassword(){
        String currentPassword = "currentPassword";
        String newPassword = "newPassword";
        String username = "username";
        User user = new User();
        user.setPassword(currentPassword);

        // Mocking repository behavior
        when(userRepository.findByUsernameOrEmail(username, username)).thenReturn(java.util.Optional.of(user));
        when(passwordEncoder.matches(currentPassword, user.getPassword())).thenReturn(true);

        // Test userService.changePassword() method
        userService.changePassword(currentPassword, newPassword);

        // Verify that the userRepository.save() method was called
        verify(userRepository, times(1)).save(user);
    }
    @Test
    void updatePassword(){
        String newPassword = "newPassword";
        String username = "username";
        User user = new User();

        // Mocking repository behavior
        when(userRepository.findByUsernameOrEmail(username, username)).thenReturn(java.util.Optional.of(user));

        // Test userService.updatePassword() method
        userService.updatePassword(newPassword);

        // Verify that the userRepository.save() method was called
        verify(userRepository, times(1)).save(user);

    }
    @Test
    void checkUserExistOrNotWithUsername(){
        String username = "username";
        User user = new User();

        // Mocking repository behavior
        when(userRepository.findByUsername(username)).thenReturn(java.util.Optional.of(user));

        // Test userService.checkUserExistOrNotWithUsername() method
        userService.checkUserExistOrNotWithUsername(username);

        // Verify that the userRepository.findByUsername() method was called
        verify(userRepository, times(1)).findByUsername(username);
    }
    @Test
    void createMember(){
        AddUserFormInput addUserFormInput = new AddUserFormInput();
        String defaultPassword = "defaultPassword";
        User user = new User();
        Department department = new Department();

        // Mocking repository behavior
        when(userRepository.save(user)).thenReturn(user);
        when(departmentRepository.findById(1)).thenReturn(java.util.Optional.of(department));

        // Test userService.createMember() method
        userService.createMember(addUserFormInput, defaultPassword);

        // Verify that the userRepository.save() method was called
        verify(userRepository, times(1)).save(user);
    }
    @Test
    void getAllUsers(){
        User user = new User();

        // Mocking repository behavior
        when(userRepository.findAll()).thenReturn(java.util.List.of(user));

        // Test userService.getAllUsers() method
        userService.getAllUsers();

        // Verify that the userRepository.findAll() method was called
        verify(userRepository, times(1)).findAll();
    }

    @Test
    void getUserById(){
    User user = new User();

        // Mocking repository behavior
        when(userRepository.findById(1L)).thenReturn(java.util.Optional.of(user));

        // Test userService.getUserById() method
        userService.getUserById(1L);

        // Verify that the userRepository.findById() method was called
        verify(userRepository, times(1)).findById(1L);

    }
    @Test
    void getCurrentUser(){
        User user = new User();

        // Mocking repository behavior
        when(userRepository.findById(1L)).thenReturn(java.util.Optional.of(user));

        // Test userService.getCurrentUser() method
        userService.getCurrentUser();

        // Verify that the userRepository.findById() method was called
        verify(userRepository, times(1)).findById(1L);
    }
    @Test
    void checkCurrentPassword(){
        String currentPassword = "currentPassword";
        String username = "username";
        User user = new User();
        user.setPassword(currentPassword);

        // Mocking repository behavior
        when(userRepository.findByUsernameOrEmail(username, username)).thenReturn(java.util.Optional.of(user));
        when(passwordEncoder.matches(currentPassword, user.getPassword())).thenReturn(true);

        // Test userService.checkCurrentPassword() method
        userService.checkCurrentPassword(currentPassword);

        // Verify that the userRepository.findByUsernameOrEmail() method was called
        verify(userRepository, times(1)).findByUsernameOrEmail(username, username);
    }

    @Test
    void getEmail(){
        String email = "email";
        User user = new User();

        // Mocking repository behavior
        when(userRepository.findByEmail(email)).thenReturn(java.util.Optional.of(user));

        // Test userService.getEmail() method
        userService.getEmail(email);

        // Verify that the userRepository.findByEmail() method was called
        verify(userRepository, times(1)).findByEmail(email);
    }



}
