package org.blank.projectmanagementsystem.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.blank.projectmanagementsystem.domain.Enum.Role;
import org.blank.projectmanagementsystem.domain.entity.Department;
import org.blank.projectmanagementsystem.domain.entity.User;
import org.blank.projectmanagementsystem.domain.formInput.AddUserFormInput;
import org.blank.projectmanagementsystem.domain.formInput.ChangePasswordFormInput;
import org.blank.projectmanagementsystem.domain.viewobject.UserViewObject;
import org.blank.projectmanagementsystem.repository.DepartmentRepository;
import org.blank.projectmanagementsystem.repository.UserRepository;
import org.blank.projectmanagementsystem.service.UserService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.apache.commons.lang3.RandomStringUtils;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final DepartmentRepository departmentRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public User save(User user) {
        //create 8 number random password for user
        if(user.getPassword() == null) {
            String password = String.valueOf((int) (Math.random() * 100000000));
            user.setPassword(passwordEncoder.encode(password));
        }
        return userRepository.save(user);
    }

    @Override
    public void saveDepartment(Department department) {
        departmentRepository.save(department);
    }


    @Override
    public void changeDefaultPassword(String password) {
        //get current username from security context
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        //get user from database
        User user = userRepository.findByUsernameOrEmail(username, username).orElse(null);
        //change password
        if (user != null) {
            user.setPassword(passwordEncoder.encode(password));
            user.setDefaultPassword(false);
            userRepository.save(user);
        }
    }

    @Override
    public ChangePasswordFormInput changePassword(String currentPassword, String newPassword) {
        //get current username from security context
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        //get user from database

        User user = userRepository.findByUsernameOrEmail(username, username).orElse(null);
        log.info("User found: {}", user);
        //change password
        if (user != null) {
            if (passwordEncoder.matches(currentPassword, user.getPassword())) {
                log.info("Password matches {}\n\n\n", newPassword);
                user.setPassword(passwordEncoder.encode(newPassword));
                user.setDefaultPassword(false);
                userRepository.save(user);
            }
            return new ChangePasswordFormInput();
        }
        return new ChangePasswordFormInput();
    }


    private String generateDefaultPassword() {
        // Generate a random alphanumeric password with a length of 8 characters
        return RandomStringUtils.randomAlphanumeric(8);
    }

    @Override
    public User registerUser(AddUserFormInput addUserFormInput) {
//        // Check if the name is already taken
//        if (userRepository.findByName(addUserFormInput.getName()).isPresent()) {
//            // Handle username already exists
//            throw new RuntimeException("Name already exists");
//        }
        Long departmentId = addUserFormInput.getDepartment(); // Assuming getDepartment() returns the department ID
        Department department = (Department) departmentRepository.findById(departmentId)
                .orElseThrow(() -> new RuntimeException("Department not found"));

        // Create a new User object based on the addUserFormInput
        User user =  User.builder()
                .name(addUserFormInput.getName())
                .email(addUserFormInput.getEmail())
                .role(Role.valueOf(addUserFormInput.getRole()))
                .department(department)
                .build();

        User savedUser = userRepository.save(user);

        // Generate a default password for the user (you can modify this part)
        String defaultPassword = generateDefaultPassword();

        // Send the default password to the user's email using MailService
        sendDefaultPasswordEmail(savedUser, defaultPassword);
        // Set other user properties as needed
        // Save the user
        return userRepository.save(user);
    }

    private void sendDefaultPasswordEmail(User user, String password) {
        // Call the MailService to send the default password email
//        mailService.sendDefaultPassword(user, password);
    }

    @Override
    public List<UserViewObject> getAllUsers() {
        return userRepository.findAll().stream().map(UserViewObject::new).toList();
    }

    @Override
    public Boolean checkCurrentPassword(String currentPassword) {
        User user = getCurrentUser();
        return passwordEncoder.matches(currentPassword, user.getPassword());
    }


    @Override
    public User getCurrentUser() {
        var userName = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByUsernameOrEmail(userName, userName).orElseThrow();
    }
}
