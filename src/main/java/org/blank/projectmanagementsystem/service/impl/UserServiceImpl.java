package org.blank.projectmanagementsystem.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.blank.projectmanagementsystem.domain.entity.Department;
import org.blank.projectmanagementsystem.domain.entity.User;
import org.blank.projectmanagementsystem.domain.formInput.AddUserFormInput;
import org.blank.projectmanagementsystem.mapper.UserMapper;
import org.blank.projectmanagementsystem.repository.DepartmentRepository;
import org.blank.projectmanagementsystem.repository.UserRepository;
import org.blank.projectmanagementsystem.service.UserService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final DepartmentRepository departmentRepository;
    private final PasswordEncoder passwordEncoder;
    private UserMapper userMapper;

    @Override
    public User save(User user) {
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
    public void changePassword(String currentPassword, String newPassword) {
        //get current username from security context
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        //get user from database
        User user = userRepository.findByUsernameOrEmail(username, username).orElse(null);
    }

    @Override
    public User registerUser(AddUserFormInput addUserFormInput) {
        // Check if the username is already taken
        if (userRepository.findByUsername(addUserFormInput.getUsername()).isPresent()) {
            // Handle username already exists
            return null;
        }
        Department department = departmentRepository.findByName(addUserFormInput.getDepartment()).orElse(null);

        // Create a new User object based on the addUserFormInput
        User user = userMapper.mapToUser(addUserFormInput);
        user.setDepartment(addUserFormInput.getDepartment());

        // Set other user properties as needed
        // Save the user
        return userRepository.save(user);
    }
}
