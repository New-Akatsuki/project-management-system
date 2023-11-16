package org.blank.projectmanagementsystem.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.blank.projectmanagementsystem.domain.Enum.Role;
import org.blank.projectmanagementsystem.domain.entity.Department;
import org.blank.projectmanagementsystem.domain.entity.User;
import org.blank.projectmanagementsystem.domain.formInput.AddUserFormInput;
import org.blank.projectmanagementsystem.domain.formInput.ChangePasswordFormInput;
import org.blank.projectmanagementsystem.domain.formInput.EditUserFormInput;
import org.blank.projectmanagementsystem.domain.formInput.ProfileEditFormInput;
import org.blank.projectmanagementsystem.domain.viewobject.UserViewObject;
import org.blank.projectmanagementsystem.repository.DepartmentRepository;
import org.blank.projectmanagementsystem.repository.UserRepository;
import org.blank.projectmanagementsystem.service.MailService;
import org.blank.projectmanagementsystem.service.UserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final DepartmentRepository departmentRepository;
    private final PasswordEncoder passwordEncoder;
    private final MailService mailService;

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

    @Override
    public void updatePassword(String newPassword) {
        //get user from database
        User user = userRepository.findByUsernameOrEmail(getUsername(), getUsername()).orElse(null);
        //change password
        if (user != null) {
            user.setPassword(passwordEncoder.encode(newPassword));
            user.setDefaultPassword(false);
            userRepository.save(user);
        }
    }

    private String generateDefaultPassword() {
        String password = String.valueOf((int) (Math.random() * 100000000));
        log.info("generateDefaultPassword: {} \n\n\n\n\n", password);
        // Generate a random alphanumeric password with a length of 8 characters
        return password;
    }

    @Override
    @PreAuthorize("hasAnyAuthority('PMO','DH','PM')")
    public User createMember(AddUserFormInput addUserFormInput) {
        Long departmentId = addUserFormInput.getDepartment(); // Assuming getDepartment() returns the department ID
        Department department = (Department) departmentRepository.findById(departmentId)
                .orElseThrow(() -> new RuntimeException("Department not found"));

        log.info("addUserFormInput: {}\n\n\n\n\n", addUserFormInput);
        // Create a new User object based on the addUserFormInput
        User user =  User.builder()
                .name(addUserFormInput.getName())
                .email(addUserFormInput.getEmail())
                .role(Role.valueOf(addUserFormInput.getRole()))
                .department(department)
                .defaultPassword(true)
                .active(true)
                .build();

        // Generate a default password for the user (you can modify this part)
        String defaultPassword = generateDefaultPassword();
        user.setPassword(passwordEncoder.encode(defaultPassword));

        User savedUser = userRepository.save(user);
        // Send the default password to the user's email using MailService
        sendDefaultPasswordEmail(savedUser, defaultPassword);
        // Set other user properties as needed
        // Save the user
        return savedUser;
    }

    private void sendDefaultPasswordEmail(User user, String password) {
        // Call the MailService to send the default password email
        mailService.sendDefaultPassword(user, password);
    }

    @Override
    @PreAuthorize("hasAuthority('PMO')")
    public List<UserViewObject> getAllUsers() {
        return userRepository.findAll().stream().map(UserViewObject::new).toList();
    }

    @Override
    public Boolean checkCurrentPassword(String currentPassword) {
        User user = getCurrentUser();
        return passwordEncoder.matches(currentPassword, user.getPassword());
    }

    private String getUsername(){
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }
    @Override
    public User getLoginUser() {
        return userRepository.findByUsernameOrEmail(getUsername(),getUsername()).orElse(null);
    }

    @Override
    public Optional<User> getEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public User getCurrentUser() {
        var userName = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByUsernameOrEmail(userName, userName).orElseThrow();
    }

    @Override
    public User editUserProfile(ProfileEditFormInput profileEditFormInput) {
        var user = getCurrentUser();
        try{
            user.setPhotoData(profileEditFormInput.getPhotoData());
            user.setName(profileEditFormInput.getFullName());
            user.setUsername(profileEditFormInput.getUserName());
            user.setUserRole(profileEditFormInput.getUserRole());
            user.setPhone(profileEditFormInput.getPhone());
            user.setEmail(profileEditFormInput.getEmail());
            return userRepository.save(user);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

}
