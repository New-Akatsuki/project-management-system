//package org.blank.projectmanagementsystem.service;
//
//import org.blank.projectmanagementsystem.domain.entity.Department;
//import org.blank.projectmanagementsystem.domain.entity.User;
//import org.blank.projectmanagementsystem.domain.formInput.AddUserFormInput;
//import org.blank.projectmanagementsystem.domain.formInput.ChangePasswordFormInput;
//import org.blank.projectmanagementsystem.domain.formInput.ProfileEditFormInput;
//import org.blank.projectmanagementsystem.domain.viewobject.UserViewObject;
//import org.blank.projectmanagementsystem.repository.UserRepository;
//import org.blank.projectmanagementsystem.service.impl.UserServiceImpl;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//
//import java.util.Arrays;
//import java.util.List;
//import java.util.Optional;
//
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.mockito.Mockito.*;
//
//@ExtendWith(MockitoExtension.class)
//public class UserServiceTest {
//
//    @Mock
//    private UserRepository userRepository;
//
//    @InjectMocks
//    private UserServiceImpl userService;
//
//
//
//    @Test
//    public void testUpdateUser() {
//        // Given
//        User user = new User();
//
//        // When
//        when(userRepository.save(user)).thenReturn(user);
//
//        // Then
//        User updatedUser = userService.updateUser(user);
//        assertThat(updatedUser).isEqualTo(user);
//    }
//
//
//
//
//
//    @Test
//    public void testGetUserById() {
//        // Given
//        Long userId = 1L;
//        User user = new User();
//        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
//
//        // When
//        User foundUser = userService.getUserById(userId);
//
//        // Then
//        assertThat(foundUser).isEqualTo(user);
//    }
//
//    @Test
//    public void testGetEmail() {
//        // Given
//        String email = "john.doe@example.com";
//        User user = new User();
//        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));
//
//        // When
//        Optional<User> foundUser = userService.getEmail(email);
//
//        // Then
//        assertThat(foundUser).isPresent();
//        assertThat(foundUser.get()).isEqualTo(user);
//    }
//
//
//    // Add more test cases as needed based on your service methods
//}
